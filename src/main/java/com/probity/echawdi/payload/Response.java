package com.probity.echawdi.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.probity.echawdi.dao.AbstractOccupantResponse;
import com.probity.echawdi.dao.AdditionalLandRevenueDao;
import com.probity.echawdi.dao.AkrushakRateDao;
import com.probity.echawdi.dao.ChallanDtlDao;
import com.probity.echawdi.dao.EchawadiCessDao;
import com.probity.echawdi.dao.Form14Dao;
import com.probity.echawdi.dao.Form15Dao;
import com.probity.echawdi.dao.Form17Dao;
import com.probity.echawdi.dao.Form17NoKaJaPaDao;
import com.probity.echawdi.dao.Form18Dao;
import com.probity.echawdi.dao.Form19Dao;
import com.probity.echawdi.dao.Form1ADao;
import com.probity.echawdi.dao.Form1AbstractDao;
import com.probity.echawdi.dao.Form1BDao;
import com.probity.echawdi.dao.Form1CDao;
import com.probity.echawdi.dao.Form1DDao;
import com.probity.echawdi.dao.Form1Dao;
import com.probity.echawdi.dao.Form1DyslrDao;
import com.probity.echawdi.dao.Form1EDao;
import com.probity.echawdi.dao.Form21Dao;
import com.probity.echawdi.dao.Form2Dao;
import com.probity.echawdi.dao.Form2SrNoDao;
import com.probity.echawdi.dao.Form3Dao;
import com.probity.echawdi.dao.Form3SrNoDao;
import com.probity.echawdi.dao.Form6BDao;
import com.probity.echawdi.dao.Form6DDao;
import com.probity.echawdi.dao.Form7ADao;
import com.probity.echawdi.dao.Form7BDao;
import com.probity.echawdi.dao.Form7Dao;
import com.probity.echawdi.dao.Form7KhataDao;
import com.probity.echawdi.dao.Form8CDao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.dao.LandRevenueDemandDao;
import com.probity.echawdi.dao.MVillageDao;
import com.probity.echawdi.dao.MenuDao;
import com.probity.echawdi.dao.NirankDao;
import com.probity.echawdi.dao.OneTimeEntryDao;
import com.probity.echawdi.dao.RevenueYearDao;
import com.probity.echawdi.dao.RoleDao;
import com.probity.echawdi.dao.RoleMenuDao;
import com.probity.echawdi.dao.TalathiDashBoardDao;
import com.probity.echawdi.entity.DashboardSupervisoryDtl;
import com.probity.echawdi.entity.DivisionDistrictTalukaCount;
import com.probity.echawdi.entity.Form1;
import com.probity.echawdi.entity.Form14;
import com.probity.echawdi.entity.Form15;
import com.probity.echawdi.entity.Form19;
import com.probity.echawdi.entity.Form1A;
import com.probity.echawdi.entity.Form1B;
import com.probity.echawdi.entity.Form1C;
import com.probity.echawdi.entity.Form1D;
import com.probity.echawdi.entity.Form1E;
import com.probity.echawdi.entity.Form2;
import com.probity.echawdi.entity.Form3;
import com.probity.echawdi.entity.Form6B;
import com.probity.echawdi.entity.Form6D;
import com.probity.echawdi.entity.Form7A;
import com.probity.echawdi.entity.Form7B;
import com.probity.echawdi.entity.LGDMapper;
import com.probity.echawdi.entity.LgdMapperMis;
import com.probity.echawdi.entity.VillageEntryCountLive;
import com.probity.echawdi.entity.VillageFormMaster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bhagyashri Lolage
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
	
	public List<LandRevenueDemandDao> landRevenueDemandData;
	public List<LandRevenueDemandDao> demandGenerated;
	
	//public List <Object []> demandGenerated;
	
	public Long demandGeneratedd;
	
	public List<OneTimeEntryDao> oneTimeEntryData;
	public List<RevenueYearDao> revenueYearData;
	public List<Form1Dao> form1Data;
	public List<Form1AbstractDao> form1AbstractData;
	public List<AbstractOccupantResponse> abstractOccupantResponse;
	public List<Form1ADao> form1AData;
	public List<Form1DyslrDao> form1DyslrData;
	public List<Form1BDao> form1BData;
	public List<Form1CDao> form1CData;
	public List<Form1DDao> form1DData;
	public List<Form1EDao> form1EData;
	public List<Form2Dao>   form2Data;
	public List<Form3Dao>   form3Data;
	public List<Form7Dao>   form7Data;
	public List<Form6BDao> form6BData;
	public List<Form6DDao> form6DData;
	public List<Form8CDao> form8CData;
	public List<Form7ADao> form7AData;
	public List<Form7BDao> form7BData;
	public List<Form14Dao> form14Data;
	public List<Form15Dao> form15Data;
	public List<Form17Dao> form17Data;
	public List<Form17NoKaJaPaDao> form17NoKaJaPaData;
	public List<AdditionalLandRevenueDao> additionalLandRevenueData;
	public List<Form18Dao> form18Data;
	public List<Form19Dao> form19Data;
	public List<Form21Dao> form21Data;
	public List<MenuDao>     menuData;
	public List<RoleDao>     roleData;
	public List<RoleMenuDao>     roleMenuData;
	public List<EchawadiCessDao> echawadiCessData;
	public List<Form7KhataDao> form7KhataData;
	public List<Form7KhataDao> form7KhataDataAug;
	public List<Form7KhataDao> khatedarPatra;
	public List<MVillageDao> villageData;
	public List<LGDMapper> lgdMapperDao;
	public List<LgdMapperMis> lgdMapperMisDao;
	public List<DashboardSupervisoryDtl> dashboardSupervisoryDtl;
	public List<Form3SrNoDao> form3SrNo;
	public List<Form2SrNoDao> form2SrNoData;
	public List<DivisionDistrictTalukaCount> divisionDistrictTalukaCount;
	public List<DashboardSupervisoryDtl> dashboardSupervisoryDtlData;
	public List<VillageFormMaster> villageFormMaster;
	public List<ChallanDtlDao> chalanData;
	public  List<AkrushakRateDao> akrushakRate;
	public  List<KhataMergeDao> khataMergeData;

	public int pageNo;
	public int pageSize;
	public long totalElements;
	public int totalPages;
	public boolean last;
	public String message;
	public String status;
	public Long serialNoForForm3;
	
	
	public String cCode;
	public String districtName;
	public String districtCode;
	public String talukaCode;
	public String villageName;
	public String talukaName;
	public String divisionCode;
	public String divisionName;
	public String dbName;
	public String schemaName;
	public List<Form1> form1;
	public List<Form1A> form1A;
	public List<Form1B> form1B;
	public List<Form1C> form1C;
	public List<Form1D> form1D;
	public List<Form1E> form1E;
	public List<Form2> form2;
	public List<Form3> form3;
	public List<Form6B> form6B;
	public List<Form6D> form6D;
	public List<Form7A> form7A;
	public List<Form7B> form7B;
	public List<Form14> form14;
	public List<Form15> form15;
	public List<Form19> form19;
	public List<TalathiDashBoardDao> talathiDashBoardData;
	public List<VillageEntryCountLive> villageEntryCountLive;
	public List<NirankDao> nirank;
	
	//for target api 22 Aug 2025
	public  Integer target;
	
	// 8 sep 2025
	public  String annualVillageTarget;
	
	public String getcCode() {
		return cCode;
	}
	public void setcCode(String cCode) {
		this.cCode = cCode;
	}
}