package com.probity.echawdi.entity;

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
@Table(name="echawdi_form1_dyslr")
public class Form1Dyslr  extends FormsEntity{

    private static final long serialVersionUID = 1L;

      @Id
      @NotNull
      @Column(name = "\"id\"", precision = 19)
      @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_form1_dyslr_seq_gen")
      @SequenceGenerator(name="echawdi_form1_dyslr_seq_gen", sequenceName="echawdi_form1_dyslr_seq", allocationSize=1)
      private Long id;
      
      @Column(name="public_rights_of_way_and_easements")
      private String publicRightsOfWayAndEasements;
      
      @Column(name="particulars_of_alteration")
      private String particularsOfAlteration;
      
      @Column(name="designation")
      private String designation;
      
      @Column(name="designation_of_authority")
      private String designationOfAuthority;
      
      @Column(name="order_date")
      private LocalDate orderDate;
      
      @Column(name="order_no")
      private String orderNo;
      
      @Column(name="assessment")
      private String assessment;
      
      @Column(name="cultivable_area_h")
      private String cultivableAreaInt;
      
      @Column(name="net_cultivation_area")
      private String netCultiAreaH;
      
      @Column(name="potkharaba_type")
      private String potkharabaType;
      
      @Column(name="tenure")
      private String tenureName;
      
      @Column(name="total_area_h")
      private String totalAreaH;
      
      @Column(name="hissa_no")
      private String hissaNo;
      
      @Column(name="jirayat_area")
      private String jirayatArea;
      
      @Column(name="jirayat_assessment")
      private String jirayatAssessment;
      
      @Column(name="bagayat_area")
      private String bagayatArea;
      
      @Column(name="bagayat_assessment")
      private String bagayatAssessment;
      
      @Column(name="tari_area")
      private String tariArea;
      
      @Column(name="tari_assessment")
      private String tariAssessment;
      
      @Column(name="other_area")
      private String otherArea;
      
      @Column(name="other_assessment")
      private String otherAssessment;
      
}