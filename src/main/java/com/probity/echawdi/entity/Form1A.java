package com.probity.echawdi.entity;

import java.math.BigDecimal;

/**
 * @author Rushikesh Durgude
 *
 */

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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="echawdi_form1a")
public class Form1A extends FormsEntity{

	  private static final long serialVersionUID = 1L;

	  @Id
      @NotNull
      @Column(name = "\"id\"", precision = 19)
      @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_form1a_seq_gen")
      @SequenceGenerator(name="echawdi_form1a_seq_gen", sequenceName="echawdi_form1a_seq", allocationSize=1)
	  private Long id;
      
//      @Column(name="type_of_land")
//      private String typeOfLand;
 
//      @Column(name="whether_village_or_protected_or_reserved_forest")
//      private String whetherVillageOrProtectedOrReservedForest;
      
      @Column(name="area")
      private BigDecimal area;
      
      @Column(name="rights_recorded_by_the_forest_officer")
      private String rightsRecordedByTheForestOfficer;
      
//      @Column(name="unit_of_measurement")
//      private String uom;
      
//      @Column(name="NirankType")
//      private String nirankType;
      
      @Column(name="village_Forest")
      private BigDecimal villageForest;
      
      @Column(name="protected_Forest")
      private BigDecimal protectedForest;
      
      @Column(name="reserved_Forest")
      private BigDecimal reservedForest;
      
      @Column(name="forest_No_If_any")
      private String forestNoIfAny;
      

}
