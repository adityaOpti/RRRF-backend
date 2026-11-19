package com.probity.echawdi.config;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.probity.echawdi.EchawdiApplication;
import com.probity.echawdi.utils.DataSourceType;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

	private final Environment env;

	private final Map<String, List<DataSourceType>> dataSourceTypeGroupByDriver = new HashMap<>();

	@Bean
	public DataSource getDataSource() {
		final Map<Object, Object> dataSources = this.buildDataSources();
		final RoutingDataSource routingDataSource = new RoutingDataSource();
		routingDataSource.setTargetDataSources(dataSources);
		routingDataSource.setDefaultTargetDataSource(dataSources.get(DataSourceType.echmst));
		return routingDataSource;
	}

	/**
	 * Create a bean for {@link EntityManagerFactory}.
	 * Create different entity manager factory for every type of SQL driver that was provided.
	 *
	 * @param routingDataSource -
	 * @return - proxy and then dynamically choose the proper instance according to the required datasource.
	 */
	@Bean("entityManagerFactory")
	public EntityManagerFactory getEntityManagerFactory(DataSource routingDataSource) {
		final Map<DataSourceType, EntityManagerFactory> emfPerDataSource = new HashMap<>();
		this.dataSourceTypeGroupByDriver.forEach((driver, dataSourceGroup) -> {
			DatabaseContextHolder.setCtx(dataSourceGroup.get(0));
			final EntityManagerFactory emf = this.createEntityManagerFactory(String.format("EMF_%s", driver),routingDataSource);
			DatabaseContextHolder.restoreCtx();
			dataSourceGroup.forEach(dataSourceType -> emfPerDataSource.put(dataSourceType, emf));
		});

		final int hashcode = UUID.randomUUID().toString().hashCode();
		final Object emf = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				new Class[]{EntityManagerFactory.class},
				(proxy, method, args) -> {
					if (method.getName().equals("hashCode")) return hashcode;
					if (method.getName().equals("equals")) return proxy == args[0];
					Object instance = emfPerDataSource.get(DataSourceType.echmst);
					final Optional<DataSourceType> dataSourceType = DatabaseContextHolder.peekDataSource();
					if (dataSourceType.isPresent()) {
						instance = emfPerDataSource.get(dataSourceType.get());
					}
					return method.invoke(instance);
				}
				);
		return (EntityManagerFactory) emf;
	}

	private Map<Object, Object> buildDataSources() {
		final Map<Object, Object> result = new HashMap<>();
		for (DataSourceType sourceType : DataSourceType.values()) {
			result.put(sourceType, this.buildDataSource(sourceType));
		}
		return result;
	}

	private DataSource buildDataSource(DataSourceType sourceType) {
		final HikariConfig config = new HikariConfig();
		config.setJdbcUrl(this.env.getProperty(String.format("datasource.%s.url", sourceType.getName())));
		config.setUsername(this.env.getProperty(String.format("datasource.%s.username", sourceType.getName())));
		config.setPassword(this.env.getProperty(String.format("datasource.%s.password", sourceType.getName())));
		String driverClassName = this.env.getProperty(String.format("datasource.%s.driver-class-name", sourceType.getName()));
		config.setDriverClassName(driverClassName);
		config.setAutoCommit(false);
		config.setConnectionTimeout(1800000);
		config.setMinimumIdle(1);
		config.setIdleTimeout(600000);
		config.setMaxLifetime(1800000);
		this.dataSourceTypeGroupByDriver.putIfAbsent(driverClassName, new ArrayList<>());
		this.dataSourceTypeGroupByDriver.get(driverClassName).add(sourceType);
		return new HikariDataSource(config);
	}

	private EntityManagerFactory createEntityManagerFactory(String providerName,DataSource routingDataSource) {
		final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		final Properties properties = new Properties() {
			private static final long serialVersionUID = 1L;
			{
				put("hibernate.physical_naming_strategy", new CamelCaseToUnderscoresNamingStrategy());
				put("hibernate.implicit_naming_strategy", new SpringImplicitNamingStrategy());
			}
		};
		factory.setJpaProperties(properties);
		factory.setPackagesToScan(EchawdiApplication.class.getPackage().getName());
		factory.setDataSource(routingDataSource);
		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		//			 inject this from application.properties
		vendorAdapter.setShowSql(true);
//		vendorAdapter.setGenerateDdl(true);
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.afterPropertiesSet();
		factory.setPersistenceUnitName(providerName + "Unit");
		factory.setBeanName(providerName + "Bean");
		return factory.getObject();
	}
}