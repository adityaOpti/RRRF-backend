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

/**
 * @author Ashish Dahale
 *
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="echawdi_form1")
public class Form1  extends FormsEntity{

	private static final long serialVersionUID = 1L;

	  @Id
      @NotNull
      @Column(name = "\"id\"", precision = 19)
      @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_form1_seq_gen")
      @SequenceGenerator(name="echawdi_form1_seq_gen", sequenceName="echawdi_form1_seq", allocationSize=1)
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
}