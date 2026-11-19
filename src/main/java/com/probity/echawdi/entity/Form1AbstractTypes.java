//package com.probity.echawdi.entity;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//import javax.validation.constraints.NotNull;
//
//import com.probity.echawdi.audit.PlainAudit;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//
//@Data
//@EqualsAndHashCode(callSuper = true)
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(schema="mhror_master",name="m_form1abstract_land_type")
//public class Form1AbstractTypes extends PlainAudit{
//	
//	private static final long serialVersionUID = 1L;
//
//	 @Id
//	 @NotNull
//     @Column(name = "\"id\"", precision = 19)
//     @GeneratedValue(strategy=GenerationType.AUTO, generator="m_form1abstract_land_type_seq_gen")
//     @SequenceGenerator(name="m_form1abstract_land_type_seq_gen", sequenceName="m_form1abstract_land_type_seq", allocationSize=1)
//	private Long id;
//	
//	@Column(name="land_type_code")
//	private Integer landTypeCode;
//	
//	@Column(name="land_type")
//	private String landType;
//	
//	@Column(name="land_type_marathi")
//	private String landTypeMarathi;
//	
//	@Column(name="land_sub_type_1")
//	private String landSubType1;
//	
//	@Column(name="land_sub_type_1_marathi")
//	private String landSubType1Marathi;
//	
//	@Column(name="land_sub_type_2")
//	private String landSubType2;
//	
//	@Column(name="land_sub_type_2_marathi")
//	private String landSubType2Marathi;
//	
//	@Column(name="land_sub_type_3")
//	private String landSubType3;
//	
//	@Column(name="land_sub_type_3_marathi")
//	private String landSubType3Marathi;
//}