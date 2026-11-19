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
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Rushikesh Durgude
 *
 */

@Data
@EqualsAndHashCode(callSuper =false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="revenue_year")
public class RevenueYear {
	

	@Id
    @NotNull
    @Column(name = "\"id\"", precision = 19)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="revenue_year_seq_gen")
    @SequenceGenerator(name="revenue_year_seq_gen", sequenceName="revenue_year_seq", allocationSize=1)
	private Long id; 
    
	@Column(name ="revenue_year")
	private String revenueYear;
	
	@Column(name="active_flag")
	private Character activeFlag;
}
