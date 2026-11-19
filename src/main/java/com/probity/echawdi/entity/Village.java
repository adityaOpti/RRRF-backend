/**
 * 
 */
package com.probity.echawdi.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Dahale
 *
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="m_village_census")
public class Village {
	 
	  @Id
	  @Column(name="ccode")
	  private String cCode;
	  
	  @Column(name="district_code")
	  private String districtCode;
	  
	  @Column(name="taluka_code")
	  private String talukaCode;
	  
	  @Column(name="village_code")
	  private String villageCode;
	  
	  @Column(name="village_name")
	  private String villageName;
	  
	  @Column(name="date_stamp")
	  private Date dateStamp;
	  
	  @Column(name="gutwari")
	  private Character gutwari;
	  
	  @Column(name="mut_no")
	  private Integer mutNo;
	  
	  @Column(name="zp_rate")
	  private BigDecimal zpRate;
	  
	  @Column(name="vp_rate")
	  private BigDecimal vpRate;
	  
	  @Column(name="village_select")
	  private Boolean villageSelect;
	  
	  @Column(name="i_mut_no")
	  private BigInteger iMutNo;
	  
	  @Column(name="pending_mut_no_cnt")
	  private BigInteger pendingMutNoCnt;
	  
	  @Column(name="circle_code")
	  private String circleCode;
	  
	  @Column(name="sajja_code")
	  private String sajjaCode;
	  
	  @Column(name="last_mut_no_flag")
	  private Boolean lastMutNoFlag;
}