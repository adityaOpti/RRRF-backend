/**
 * 
 */
package com.probity.echawdi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Bhagyashri Lolage
 *
 */
	
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@EqualsAndHashCode(callSuper= false)
	@Entity
	@Table(name="echawdi_form7a")
	public class Form7A extends FormsEntity { 

	private static final long serialVersionUID = 1L;

		// Raw attributes
		@Id
		@NotNull
		@Column(name="\"id\"", precision = 19)
		@GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_form7a_seq_gen")
	    @SequenceGenerator(name="echawdi_form7a_seq_gen", sequenceName="echawdi_form7a_seq", allocationSize=1)
		private Long id ;
		
		@Column(name="khata_no")
		private String khataNo; 
	
		@Column(name="last_year_serial_no")
		private String lastYearSerialNo;
		
		@Column(name="name_of_tenant")
		private String nameOfTenant;
		
		@Column(name="rent")
		private String rent;
		
		@Column(name="serial_no_in_mutation_reg")
		private String serialNoInMutationReg;
		
		@Column(name="area")
		private String area;

}
