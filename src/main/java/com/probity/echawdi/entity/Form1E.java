/**
 * 
 */
package com.probity.echawdi.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.probity.echawdi.audit.FormsEntity;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Rushikesh Durgude
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="echawdi_form1e")
public class Form1E extends FormsEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
    @NotNull
    @Column(name = "\"id\"", precision = 19)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_form1e_seq_gen")
    @SequenceGenerator(name="echawdi_form1e_seq_gen", sequenceName="echawdi_form1e_seq", allocationSize=1)
	private Long id;
    
    @Column(name="name_of_encroacher")
    private String nameOfEncroacher;
    
    @Column(name="date_of_encroachment")
    private LocalDate dateOfEncroachment;
    
    @Column(name="purpose_of_encroachment_land")
    private String purposeOfEncroachmentLand;
    
    @Column(name="year_from_which_rent_recoverable")
    private Integer  yearFromWhichRentRecoverable;
	  
    @Column(name="year_of_encroachment_noted_if_removed")
    private String yearOfEncroachmentNotedIfRemoved;
    
    @Column(name="date_of_decision_on_encroachment")
    private LocalDate dateOfDecisionOnEncroachment;
    
    @Column(name="area_of_encroached")
    private BigDecimal areaOfEncroached;
    
    @Column(name="total_area")
    private  BigDecimal totalArea;

    @Column(name="encroached_path_location_image")
    private String  areaOfEncroachedPath;
}