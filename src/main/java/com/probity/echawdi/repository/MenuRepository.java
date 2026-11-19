package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.Menu;

/**
 * @author Kumar Harpude
 */

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
	
	Page<Menu> findAllByActiveFlag(Character activeFlag, Pageable pageable);
	
	List<Menu> getByIdInAndRoutesIsNull(List<Double> id);
	
	List<Menu> getByRoutes(Double routes);
}