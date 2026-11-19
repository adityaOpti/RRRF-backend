/**
 * 
 */
package com.probity.echawdi.entity;


import java.sql.Date;

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
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Rushikesh Durgude
 *
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="echawdi_form1c")
public class Form1C extends FormsEntity{
	
	private static final long serialVersionUID = 1L;

	@Id
    @NotNull
    @Column(name = "\"id\"", precision = 19)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_form1c_seq_gen")
    @SequenceGenerator(name="echawdi_form1c_seq_gen", sequenceName="echawdi_form1c_seq", allocationSize=1)
	private Long id;
    
    @Column(name="name_of_village_panchayat")
    private String nameOfVillagePanchayat;
    
    @Column(name="terms_of_grant")
    private String termsOfGrant;
    
    @Column(name="authority_competent_to_permit_transfer_of_land")
    public String authorityCompetentToPermitTransferOfLand ;
	
    @Column(name="details_of_Unclaimed_Amount")
	public String detailsOfUnclaimedAmount ;
	
    @Column(name="breach_of_condition")
	public String breachOfCondition;
	
    @Column(name="inspecting_Officer")
	public String inspectingOfficer ;
	
    @Column(name="inspection_date")
	public Date inspectionDate;
    
    @Column(name="aadesh_on_date")
    public String aadeshOnDate;
    
    @Column(name="purpose_of_land_intended")
    public String purposeOfLandIntended;

}
