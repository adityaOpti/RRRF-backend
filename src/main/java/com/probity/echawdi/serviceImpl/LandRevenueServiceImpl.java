package com.probity.echawdi.serviceImpl;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.AkrushakRateDao;
import com.probity.echawdi.dao.ChallanDtlDao;
import com.probity.echawdi.dao.CombinedChallanLandRevenueViewDao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.dao.LandRevenueDemandDao;
import com.probity.echawdi.dao.LandRevenueDemandListDao;
import com.probity.echawdi.dao.OneTimeDataEntyRevenueYearCheckDTO;
import com.probity.echawdi.dao.VillageDropdownResponse;
import com.probity.echawdi.entity.AdditionalLandRevenue;
import com.probity.echawdi.entity.ChallanDtl;
import com.probity.echawdi.entity.CombinedChallanLandRevenueView;
import com.probity.echawdi.entity.CropEduCess;
import com.probity.echawdi.entity.District;
import com.probity.echawdi.entity.Division;
import com.probity.echawdi.entity.Form17;
import com.probity.echawdi.entity.Form17NoKaJaPa;
import com.probity.echawdi.entity.KhataMerge;
import com.probity.echawdi.entity.KhataMergeDtl;
import com.probity.echawdi.entity.LandRevenueDemand;
import com.probity.echawdi.entity.LandRevenueDemand2023;
import com.probity.echawdi.entity.LandRevenueDemand2024;
import com.probity.echawdi.entity.LandRevenueDemand2025;
import com.probity.echawdi.entity.LandRevenueDemandPre;
import com.probity.echawdi.entity.MVillageMapper;
import com.probity.echawdi.entity.OneTimeEntry;
import com.probity.echawdi.entity.ReceiptDtlLog;
import com.probity.echawdi.entity.Taluka;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.FieldValidationException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.NoContent;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.AdditionalLandRevenueRepository;
import com.probity.echawdi.repository.ChallanRepository;
import com.probity.echawdi.repository.CombinedChallanAndLandRevenue8BRepository;
import com.probity.echawdi.repository.CropEduCessRepository;
import com.probity.echawdi.repository.DistrictRepository;
import com.probity.echawdi.repository.DivisionRepository;
import com.probity.echawdi.repository.Form17NoKaJaPaRepository;
import com.probity.echawdi.repository.Form17Repository;
import com.probity.echawdi.repository.KhataMergeDtlRepository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.repository.LandRevenueDemandPreRepository;
import com.probity.echawdi.repository.LandRevenueDemandRepo2023;
import com.probity.echawdi.repository.LandRevenueDemandRepo2024;
import com.probity.echawdi.repository.LandRevenueDemandRepo2025;
import com.probity.echawdi.repository.LandRevenueDemandRepository;
import com.probity.echawdi.repository.LgdMapperRepository;
import com.probity.echawdi.repository.MVillageMapperRepository;
import com.probity.echawdi.repository.OneTimeEntryRepository;
import com.probity.echawdi.repository.ReceiptDtlLogRepository;
import com.probity.echawdi.repository.TalukaRepository;
import com.probity.echawdi.service.LandRevenueService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.CommonConstants.DateFormats;
import com.probity.echawdi.utils.CommonConstants.FileName;
import com.probity.echawdi.utils.DataSourceKeeper;
import com.probity.echawdi.utils.DataSourceType;
import com.probity.echawdi.utils.JwtUtils;
import com.probity.echawdi.utils.StaticPropertyFetcher;

@Service
public class LandRevenueServiceImpl implements LandRevenueService {

	@Autowired
	private LandRevenueDemandRepository landRevenueDemandRepository;
	
	//for DDE login
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private LandRevenueDemandPreRepository landRevenueDemandRepositoryPre;
	
	
	@Autowired
	private MVillageMapperRepository mVillageMapperRepository;
	@Autowired
	private TalukaRepository talukaRepository;
	@Autowired
	private DivisionRepository divisionRepository;
	@Autowired
	private DistrictRepository districtRepository;
	
	
	@Autowired
	private Form17Repository form17Repository;
	@Autowired
	private CropEduCessRepository cropEduCessRepository;
	@Autowired
	private ChallanRepository challanRepository;
	@Autowired
	private OneTimeEntryRepository oneTimeEntryRepository;
	@Autowired
	private AdditionalLandRevenueRepository additionalLandRevenueRepository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private Form17NoKaJaPaRepository form17NoKaJaPaRepository;
	@Autowired
	private KhataMergeRepository khataMergeRepository;
	@Autowired
	private KhataMergeDtlRepository khataMergeDtlRepository;

	@Autowired
	private LgdMapperRepository lgdMapperRepository;

	@Autowired
	private ReceiptDtlLogRepository dtlLogRepository;

	@Autowired
	private CombinedChallanAndLandRevenue8BRepository combinedChallanAndLandRevenue8BRepository;

	@Autowired
	private LandRevenueDemandRepo2023 demandRepo2023;
	
	
	@Autowired
	private LandRevenueDemandRepo2024 demandRepo2024;
	
	@Autowired
	private LandRevenueDemandRepo2025 demandRepo2025;
	
	

	// 8B and 8k
	private List<CombinedChallanLandRevenueView> combinedChallanLandRevenueView;

	// for thakabaki 11 aug
	private List<LandRevenueDemandDao> lstOneTimeEntrypenging;

// Method to check if the list contains an object with a specific field value - added on 11 aug

	private static boolean containsField(List<LandRevenueDemandDao> lstOneTimeEntrypenging, String field) {
		for (LandRevenueDemandDao obj : lstOneTimeEntrypenging) {
			if (obj.getKhataNo().equals(field)) {
				return true;
			}
		}
		return false;
	}

	public ChallanDtlDao toChallanDao(ChallanDtl challanDtl) {
		ChallanDtlDao challanDtlDao = new ChallanDtlDao();
		BeanUtils.copyProperties(challanDtl, challanDtlDao);
		challanDtlDao.setLstReceipt(challanDtl.getLstReceiptDtl().stream().map(lrdR -> {
			LandRevenueDemandDao lrd = new LandRevenueDemandDao();
			BeanUtils.copyProperties(lrdR, lrd);
			double assessment = Double.parseDouble(lrd.getAssessment().toString());
			lrd.setTotalJm((lrd.getJmBindumala()).add(lrd.getJmDumala()).add(lrd.getJmAkrushik())
					.add(lrd.getJmSankirn()).add(lrd.getPreYearPendingJm()));
			lrd.setTotalGp((lrd.getGpBindumala()).add(lrd.getGpDumala()).add(lrd.getGpAkrushik())
					.add(lrd.getGpSankirn()).add(lrd.getPreYearPendingGp()));
			lrd.setTotalZp((lrd.getZpBindumala()).add(lrd.getZpDumala()).add(lrd.getZpAkrushik())
					.add(lrd.getZpSankirn()).add(lrd.getPreYearPendingZp()));

			lrd.setTotalOf0045(
					(lrd.getAddlEducationalCess()).add(lrd.getEducationalCess()).add(lrd.getEmployeeGuaranteeScheme())
							.add(lrd.getPreYearPendingAddlEducationalCess()).add(lrd.getPreYearPendingEducationalCess())
							.add(lrd.getPreYearPendingEmployeeGuaranteeScheme()).setScale(0, BigDecimal.ROUND_HALF_UP));

			/*
			 * (Old conditons -->commented on 7th oct) if(assessment > 10) {
			 * lrd.setTotalOf0029((lrd.getTotalJm()).add(lrd.getTotalGp()).add(lrd.
			 * getTotalZp()).add(lrd.getPreYearSankirnJmWith()).add(lrd.
			 * getPreYearSankirnJmWithout())
			 * .add(lrd.getAddlLandRevenue()).add(lrd.getPreYearPendingAddlLandRevenue()).
			 * add(lrd.getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee()).setScale(0,
			 * BigDecimal.ROUND_HALF_UP)); } else if(assessment >= 5 && assessment <= 10 ) {
			 * lrd.setTotalOf0029((lrd.getJmAkrushik()).add(lrd.getJmSankirn()).add(lrd.
			 * getPreYearPendingJm()).add(lrd.getTotalGp()).add(lrd.getTotalZp()).add(lrd.
			 * getJmAkrushik())
			 * .add(lrd.getPreYearSankirnJmWith()).add(lrd.getPreYearSankirnJmWithout()).add
			 * (lrd.getAddlLandRevenue()).add(lrd.getPreYearPendingAddlLandRevenue()).add(
			 * lrd.getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee()).setScale(0,
			 * BigDecimal.ROUND_HALF_UP)); }else {
			 * lrd.setTotalOf0029((lrd.getAddlLandRevenue()).add(lrd.getJmSankirn()).add(lrd
			 * .getGpSankirn()).add(lrd.getZpSankirn()).add(lrd.
			 * getPreYearPendingAddlLandRevenue())
			 * .add(lrd.getJmAkrushik()).add(lrd.getGpAkrushik()).add(lrd.getZpAkrushik()).
			 * add(lrd.getPreYearSankirnJmWith()).add(lrd.getPreYearSankirnJmWithout()).add(
			 * lrd.getPreYearPendingJm())
			 * .add(lrd.getPreYearPendingGp()).add(lrd.getPreYearPendingZp()).add(lrd.
			 * getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee()).setScale(0,
			 * BigDecimal.ROUND_HALF_UP)); }
			 */

			// new conditions added by tushar at 7th Oct 2024 --> Tushar

			// new
			double area = Double.parseDouble(lrd.getArea().toString());

			if (assessment > 10 && area > 3) {

				// old conditon 20 dec 2024 -- tushar
				/*
				 * lrd.setTotalOf0029((lrd.getTotalJm()).add(lrd.getTotalGp()).add(lrd.
				 * getTotalZp())
				 * .add(lrd.getPreYearSankirnJmWith()).add(lrd.getPreYearSankirnJmWithout())
				 * .add(lrd.getAddlLandRevenue()).add(lrd.getPreYearPendingAddlLandRevenue())
				 * .add(lrd.getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee())
				 * .setScale(0, BigDecimal.ROUND_HALF_UP));
				 */

				// condition changed for vajsut 20 dec 2024 -- tushar
				lrd.setTotalOf0029((lrd.getTotalJm()).add(lrd.getTotalGp()).add(lrd.getTotalZp())
						.add(lrd.getPreYearSankirnJmWith()).add(lrd.getPreYearSankirnJmWithout())
						.add(lrd.getAddlLandRevenue()).add(lrd.getPreYearPendingAddlLandRevenue())
						.add(lrd.getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee()).subtract(lrd.getJmVajasut())
						.subtract(lrd.getGpVajasut()).subtract(lrd.getZpVajasut())
						.setScale(0, BigDecimal.ROUND_HALF_UP));

			} else if (assessment > 10 && area <= 3) {

				lrd.setTotalOf0029((lrd.getJmAkrushik()).add(lrd.getJmSankirn()).add(lrd.getPreYearPendingJm())
						.add(lrd.getTotalGp()).add(lrd.getTotalZp()).add(lrd.getPreYearSankirnJmWith())
						.add(lrd.getPreYearSankirnJmWithout()).add(lrd.getAddlLandRevenue())
						.add(lrd.getPreYearPendingAddlLandRevenue()).add(lrd.getPreYearPendingNaCess())
						.add(lrd.getPreYearNoticeFee()).subtract(lrd.getGpVajasut()).subtract(lrd.getZpVajasut())
						.setScale(0, BigDecimal.ROUND_HALF_UP));

			} else if (assessment >= 5 && assessment <= 10) {
				lrd.setTotalOf0029((lrd.getJmAkrushik()).add(lrd.getJmSankirn()).add(lrd.getPreYearPendingJm())
						.add(lrd.getTotalGp()).add(lrd.getTotalZp()).add(lrd.getPreYearSankirnJmWith())
						.add(lrd.getPreYearSankirnJmWithout()).add(lrd.getAddlLandRevenue())
						.add(lrd.getPreYearPendingAddlLandRevenue()).add(lrd.getPreYearPendingNaCess())
						.add(lrd.getPreYearNoticeFee()).subtract(lrd.getGpVajasut()).subtract(lrd.getZpVajasut())
						.setScale(0, BigDecimal.ROUND_HALF_UP));

			} else {
				lrd.setTotalOf0029((lrd.getAddlLandRevenue()).add(lrd.getJmSankirn()).add(lrd.getGpSankirn())
						.add(lrd.getZpSankirn()).add(lrd.getPreYearPendingAddlLandRevenue()).add(lrd.getJmAkrushik())
						.add(lrd.getGpAkrushik()).add(lrd.getZpAkrushik()).add(lrd.getPreYearSankirnJmWith())
						.add(lrd.getPreYearSankirnJmWithout()).add(lrd.getPreYearPendingJm())
						.add(lrd.getPreYearPendingGp()).add(lrd.getPreYearPendingZp())
						.add(lrd.getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee())
						.setScale(0, BigDecimal.ROUND_HALF_UP));
			}

			// new conditions end : 7th Oct 2024 --> Tushar

			return lrd;
		}
		// toDao(lrdR)
		).collect(Collectors.toList()));

		// System.out.println("challanDtlDao.getLstReceipt()"+challanDtlDao.getLstReceipt());
		return challanDtlDao;
	}

	public LandRevenueDemandDao toDao(LandRevenueDemand landRevenueDemand) {
		LandRevenueDemandDao landRevenueDemandDao = new LandRevenueDemandDao();
		BeanUtils.copyProperties(landRevenueDemand, landRevenueDemandDao);
		BigDecimal sankirn = landRevenueDemand.getZpSankirn().add(landRevenueDemand.getJmSankirn())
				.add(landRevenueDemand.getGpSankirn());
		BigDecimal localCess = landRevenueDemand.getZpSankirn().add(landRevenueDemand.getGpSankirn())
				.add(landRevenueDemand.getGpSankirn());
		BigDecimal otherLocalCess = sankirn.subtract(localCess);
		landRevenueDemandDao.setLocalCess(localCess);
		landRevenueDemandDao.setOtherLocalCess(otherLocalCess);
		landRevenueDemandDao.setSankirn(sankirn.toString());
		if (landRevenueDemandDao.getChallanId() != null) {
			ChallanDtl challanDtl = challanRepository.getById(landRevenueDemandDao.getChallanId());
			landRevenueDemandDao.setChallanNo(challanDtl.getChallanNo());
			landRevenueDemandDao.setChallanDate(challanDtl.getChallanDate());
		}
		return landRevenueDemandDao;
	}

//
//	Pre2023
	public LandRevenueDemandDao toDao2023(LandRevenueDemand2023 landRevenueDemand) {
		LandRevenueDemandDao landRevenueDemandDao = new LandRevenueDemandDao();
		BeanUtils.copyProperties(landRevenueDemand, landRevenueDemandDao);
		BigDecimal sankirn = landRevenueDemand.getZpSankirn().add(landRevenueDemand.getJmSankirn())
				.add(landRevenueDemand.getGpSankirn());
		BigDecimal localCess = landRevenueDemand.getZpSankirn().add(landRevenueDemand.getGpSankirn())
				.add(landRevenueDemand.getGpSankirn());
		BigDecimal otherLocalCess = sankirn.subtract(localCess);
		landRevenueDemandDao.setLocalCess(localCess);
		landRevenueDemandDao.setOtherLocalCess(otherLocalCess);
		landRevenueDemandDao.setSankirn(sankirn.toString());
		if (landRevenueDemandDao.getChallanId() != null) {
			ChallanDtl challanDtl = challanRepository.getById(landRevenueDemandDao.getChallanId());
			landRevenueDemandDao.setChallanNo(challanDtl.getChallanNo());
			landRevenueDemandDao.setChallanDate(challanDtl.getChallanDate());
		}
		return landRevenueDemandDao;
	}
	
	//for 2024
	public LandRevenueDemandDao toDao2024(LandRevenueDemand2024 landRevenueDemand) {
		LandRevenueDemandDao landRevenueDemandDao = new LandRevenueDemandDao();
		BeanUtils.copyProperties(landRevenueDemand, landRevenueDemandDao);
		BigDecimal sankirn = landRevenueDemand.getZpSankirn().add(landRevenueDemand.getJmSankirn())
				.add(landRevenueDemand.getGpSankirn());
		BigDecimal localCess = landRevenueDemand.getZpSankirn().add(landRevenueDemand.getGpSankirn())
				.add(landRevenueDemand.getGpSankirn());
		BigDecimal otherLocalCess = sankirn.subtract(localCess);
		landRevenueDemandDao.setLocalCess(localCess);
		landRevenueDemandDao.setOtherLocalCess(otherLocalCess);
		landRevenueDemandDao.setSankirn(sankirn.toString());
		if (landRevenueDemandDao.getChallanId() != null) {
			ChallanDtl challanDtl = challanRepository.getById(landRevenueDemandDao.getChallanId());
			landRevenueDemandDao.setChallanNo(challanDtl.getChallanNo());
			landRevenueDemandDao.setChallanDate(challanDtl.getChallanDate());
		}
		return landRevenueDemandDao;
	}
	
	
	//for 2025
		public LandRevenueDemandDao toDao2025(LandRevenueDemand2025 landRevenueDemand) {
			LandRevenueDemandDao landRevenueDemandDao = new LandRevenueDemandDao();
			BeanUtils.copyProperties(landRevenueDemand, landRevenueDemandDao);
			BigDecimal sankirn = landRevenueDemand.getZpSankirn().add(landRevenueDemand.getJmSankirn())
					.add(landRevenueDemand.getGpSankirn());
			BigDecimal localCess = landRevenueDemand.getZpSankirn().add(landRevenueDemand.getGpSankirn())
					.add(landRevenueDemand.getGpSankirn());
			BigDecimal otherLocalCess = sankirn.subtract(localCess);
			landRevenueDemandDao.setLocalCess(localCess);
			landRevenueDemandDao.setOtherLocalCess(otherLocalCess);
			landRevenueDemandDao.setSankirn(sankirn.toString());
			if (landRevenueDemandDao.getChallanId() != null) {
				ChallanDtl challanDtl = challanRepository.getById(landRevenueDemandDao.getChallanId());
				landRevenueDemandDao.setChallanNo(challanDtl.getChallanNo());
				landRevenueDemandDao.setChallanDate(challanDtl.getChallanDate());
			}
			return landRevenueDemandDao;
		}
	
	public LandRevenueDemandDao toDaoForMakhtakhata(LandRevenueDemandPre landRevenueDemand) {
		LandRevenueDemandDao landRevenueDemandDao = new LandRevenueDemandDao();
		BeanUtils.copyProperties(landRevenueDemand, landRevenueDemandDao);
		BigDecimal sankirn = landRevenueDemand.getZpSankirn().add(landRevenueDemand.getJmSankirn())
				.add(landRevenueDemand.getGpSankirn());
		BigDecimal localCess = landRevenueDemand.getZpSankirn().add(landRevenueDemand.getGpSankirn())
				.add(landRevenueDemand.getGpSankirn());
		BigDecimal otherLocalCess = sankirn.subtract(localCess);
		landRevenueDemandDao.setLocalCess(localCess);
		landRevenueDemandDao.setOtherLocalCess(otherLocalCess);
		landRevenueDemandDao.setSankirn(sankirn.toString());
		if (landRevenueDemandDao.getChallanId() != null) {
			ChallanDtl challanDtl = challanRepository.getById(landRevenueDemandDao.getChallanId());
			landRevenueDemandDao.setChallanNo(challanDtl.getChallanNo());
			landRevenueDemandDao.setChallanDate(challanDtl.getChallanDate());
		}
		return landRevenueDemandDao;
	}

	public LandRevenueDemand toEntity(LandRevenueDemandDao landRevenueDemandDao) {
		LandRevenueDemand landRevenueDemand = new LandRevenueDemand();
		BeanUtils.copyProperties(landRevenueDemandDao, landRevenueDemand);
		return landRevenueDemand;
	}

	@Override
	public List<LandRevenueDemandDao> generateRevenue(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {

		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));

		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(landRevenueDemandDao.getCCode())) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(landRevenueDemandDao.getCCode())) {

			List<LandRevenueDemandDao> finalList = null;

			if (landRevenueDemandDao != null && landRevenueDemandDao.getCCode() != null
					&& landRevenueDemandDao.getRevenueYear() != null) {

				List<KhataMergeDtl> kmKhataMergeDtlLst = getKhataMergeDtlDetails(landRevenueDemandDao, request);
				// landRevenueDemandDao.setKhataMergeDtlLst(getKhataMergeDtlDetails(landRevenueDemandDao,request));

				List<KhataMerge> kmKhataMergeLst = getKhataMergeDetails(landRevenueDemandDao, request);
				// landRevenueDemandDao.setKhataMergeLst(getKhataMergeDetails(landRevenueDemandDao,request));

				List<AdditionalLandRevenue> adlAdditionalLandRevenueLst = getAddlLandRevenueDetails(
						landRevenueDemandDao, request);
				// landRevenueDemandDao.setAdditionalLandRevenue(getAddlLandRevenueDetails(landRevenueDemandDao,request));

				List<Form17NoKaJaPa> form17NoKaJaPaLst = getForm17KaJaPaDetails(landRevenueDemandDao, request);
				// landRevenueDemandDao.setForm17NoKaJaPaLst(getForm17KaJaPaDetails(landRevenueDemandDao,request));

				

				//17 July 2026
				Response akrushikRateRural = getAkrushakRateRural(landRevenueDemandDao, request);
				Response akrushikRateNapaAndManapa = getAkrushakRateNapaAndManapa(landRevenueDemandDao, request);
				Response form2PrayojanType = getForm2PrayojanType(landRevenueDemandDao, request);
				List<LandRevenueDemandDao> lstLRAkrushik = getAkrushakDetails(landRevenueDemandDao, request,
						adlAdditionalLandRevenueLst, form17NoKaJaPaLst, akrushikRateNapaAndManapa, akrushikRateRural,
						form2PrayojanType); // Get AKRUSHIK Land records
				//17 July 2026 end

				// JM,ZP,GP
				List<LandRevenueDemandDao> lstLRBindumala = getBinDumalaDetails(landRevenueDemandDao, request,
						adlAdditionalLandRevenueLst, form17NoKaJaPaLst, kmKhataMergeDtlLst, kmKhataMergeLst); // Get																							// records
				List<LandRevenueDemandDao> lstLRDumala = getDumalaDetails(landRevenueDemandDao, request,
						adlAdditionalLandRevenueLst, form17NoKaJaPaLst); // Get dumala Land records
				
				
				
				
				//printing Akrushak list 13 Nov 2025
//				int count = 0;
//				for (LandRevenueDemandDao item : lstLRAkrushik) {
//					
//					count++;
//				    System.out.println("Akrushak List : "+item);
//				    
//				    System.out.println("AkrushakRecords Count--------------------:"+count++);
//				}

				
				
				List<LandRevenueDemandDao> lstLRAkrushikKaJaPa = getAkrushakDetailsKaJaPa(landRevenueDemandDao, request,
						adlAdditionalLandRevenueLst, form17NoKaJaPaLst); // Get AKRUSHIK Land records
				List<LandRevenueDemandDao> lstOneTimeEntry = getOneTimeEntryDetails(
						landRevenueDemandDao.getRevenueYear(), landRevenueDemandDao.getCCode(), request); // Get
																											// Previous
																											// Year
																											// Records(By
																											// one time
																											// entry)
				lstOneTimeEntrypenging = getPreYearCarryForwordPendingDetails(landRevenueDemandDao, request); // Get
																												// Previous
																												// Year
																												// Records(By
																												// one
																												// time
																												// entry)
				List<LandRevenueDemandDao> lstLRSankirn = getSankirnDetails(landRevenueDemandDao.getRevenueYear(),
						landRevenueDemandDao.getCCode(), request); // Get Sankirn Land records
				List<LandRevenueDemandDao> lstAddlLandRevenue = getAddlLandRevenueDetailsOld(landRevenueDemandDao,
						request); // Get dumala Land records

				// Get crop code and price details
				List<CropEduCess> lstCropCode = cropEduCessRepository.findAllByActiveFlag('Y');

				Map<Integer, Double> mpCropCode = lstCropCode.stream()
						.collect(Collectors.toMap(CropEduCess::getCropCode, CropEduCess::getCropPricePerHect));

//			Integer netCultiArea=Integer.valueOf(landRevenueDemandDao.getRevenueYear().substring(0,4));
				Integer netCultiArea = Integer.valueOf(CommonConstants.netCultiArea);

				List<Object[]> lstLRForEduCessConstIrr = landRevenueDemandRepository.getLRForEducationalCess(
						request.getHeader("mhrHost"), request.getHeader("mhrDbName"),
						request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser, CommonConstants.mhrorPassword,
						request.getHeader("echHost"), request.getHeader("echDbName"),
						request.getHeader("echSchemaName"), CommonConstants.echUser, CommonConstants.echPassword,
						netCultiArea, landRevenueDemandDao.getRevenueYear(), landRevenueDemandDao.getCCode());
				List<Object[]> lstLRForEduCessConstUnIrr = landRevenueDemandRepository
						.getLRForEducationalCessConstUnIrr(request.getHeader("mhrHost"), request.getHeader("mhrDbName"),
								request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
								CommonConstants.mhrorPassword, request.getHeader("echHost"),
								request.getHeader("echDbName"), request.getHeader("echSchemaName"),
								CommonConstants.echUser, CommonConstants.echPassword, netCultiArea,
								landRevenueDemandDao.getRevenueYear(), landRevenueDemandDao.getCCode());
				List<Object[]> lstLRForEduCessPureIrr = landRevenueDemandRepository.getLRForEducationalCessPureIrr(
						request.getHeader("mhrHost"), request.getHeader("mhrDbName"),
						request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser, CommonConstants.mhrorPassword,
						request.getHeader("echHost"), request.getHeader("echDbName"),
						request.getHeader("echSchemaName"), CommonConstants.echUser, CommonConstants.echPassword,
						netCultiArea, landRevenueDemandDao.getRevenueYear(), landRevenueDemandDao.getCCode());
				List<Object[]> lstLRForEduCessPureUnIrr = landRevenueDemandRepository.getLRForEducationalCessPureUnIrr(
						request.getHeader("mhrHost"), request.getHeader("mhrDbName"),
						request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser, CommonConstants.mhrorPassword,
						request.getHeader("echHost"), request.getHeader("echDbName"),
						request.getHeader("echSchemaName"), CommonConstants.echUser, CommonConstants.echPassword,
						netCultiArea, landRevenueDemandDao.getRevenueYear(), landRevenueDemandDao.getCCode());

				// System.out.println("Edu_cess MHRHOST ------------------- :
				// "+request.getHeader("mhrHost"));

				// EGS
				List<LandRevenueDemandDao> lstLRForEGS = getEmployeeGuaranteeSchemeDetails(landRevenueDemandDao,
						request);
				// List<LandRevenueDemandDao> lstLRForEGS =
				// getEmployeeGuaranteeSchemeDetails(landRevenueDemandDao,request,kmKhataMergeDtlLst,kmKhataMergeLst);

				// Educational Cess
				List<LandRevenueDemandDao> lstLREducationCessConstIrr = getEducationCess(landRevenueDemandDao,
						lstLRForEduCessConstIrr, mpCropCode); // Get Educational Cess Land records Const Irr
				List<LandRevenueDemandDao> lstLREducationCessConstUnIrr = getEducationCess(landRevenueDemandDao,
						lstLRForEduCessConstUnIrr, mpCropCode); // Get Educational Cess Land records Const UnIrr
				List<LandRevenueDemandDao> lstLREducationCessPureIrr = getEducationCess(landRevenueDemandDao,
						lstLRForEduCessPureIrr, mpCropCode); // Get Educational Cess Land records Pure Irr
				List<LandRevenueDemandDao> lstLREducationCessPureUnIrr = getEducationCess(landRevenueDemandDao,
						lstLRForEduCessPureUnIrr, mpCropCode); // Get Educational Cess Land records Pure UnIrr
				DatabaseContextHolder.restoreCtx();

				finalList = new ArrayList<>();
				finalList.addAll(lstLRBindumala);
				finalList.addAll(lstLRDumala);
				finalList.addAll(lstLRAkrushik);
				if (lstLRAkrushikKaJaPa != null) {
					finalList.addAll(lstLRAkrushikKaJaPa);
				}
				finalList.addAll(lstLREducationCessConstIrr);
				finalList.addAll(lstLREducationCessConstUnIrr);
				finalList.addAll(lstLREducationCessPureIrr);
				finalList.addAll(lstLREducationCessPureUnIrr);
				finalList.addAll(lstLRForEGS);
				finalList.addAll(lstLRSankirn);
				finalList.addAll(lstOneTimeEntry);

				/*
				 * for (int i = 0; i < Math.min(lstOneTimeEntrypenging.size(),
				 * finalList.size()); i++) { String khataNo1 =
				 * lstOneTimeEntrypenging.get(i).getKhataNo(); String khataNo2 =
				 * finalList.get(i).getKhataNo();
				 * 
				 * if (khataNo1.equals(khataNo2)) { finalList.addAll(lstOneTimeEntrypenging);
				 * break; } }
				 */

				List<LandRevenueDemandDao> commonElements = new ArrayList<>();

				// Iterate over each object in finalList
				/*for (LandRevenueDemandDao finalObj : finalList) {
					// Check if finalObj's field exists in pendingList
					if (containsField(lstOneTimeEntrypenging, finalObj.getKhataNo())) {
						commonElements.addAll(lstOneTimeEntrypenging);

						break;
					}
				}*/

				finalList.addAll(commonElements);

				finalList.addAll(lstAddlLandRevenue);
				finalList = mergeList(finalList);

				finalList = finalList.stream().sorted(Comparator.comparing(LandRevenueDemandDao::fetchKhataNumber))
						.collect(Collectors.toList());

				if (CollectionUtils.isEmpty(finalList)) {
					throw new ExchangeException("error.form.recordnotfound");
				} else {
					
					
					
//					int count = 0;
//					 for (LandRevenueDemandDao item : finalList) {
//			                System.out.println("YE HAI FINAL LIST Missing khateWali   : "+item);
//			                System.out.println("genre  : "+count++);// Assuming LandRevenueDemandDao has a proper toString() method
//			            }
				 
				 
				 //  Identify khataNo values where khataOwnerName is missing
//				 List<String> khataNosWithMissingNames = finalList.stream()
//						    .filter(record -> record.getKhataOwnerName() == null || record.getKhataOwnerName().trim().isEmpty())
//						    .map(LandRevenueDemandDao::getKhataNo)
//						    .filter(Objects::nonNull)
//						    .map(String::trim)
//						    .filter(khataNo -> !khataNo.isEmpty())
//						    .distinct()
//						    .collect(Collectors.toList());
//				 
//				 
//				  //  Fetch khataOwnerNames from DB (in one query)
//				    if (!khataNosWithMissingNames.isEmpty()) {
//
//				        List<LandRevenueDemandPre> dbRecords = landRevenueDemandRepositoryPre.findAllByKhataNoIn(khataNosWithMissingNames);
//
//				        // Map khataNo -> khataOwnerName
//				        Map<String, String> khataNoToOwnerNameMap = dbRecords.stream()
//				            .filter(db -> db.getKhataOwnerName() != null && !db.getKhataOwnerName().trim().isEmpty())
//				            .collect(Collectors.toMap(
//				                LandRevenueDemandPre::getKhataNo,
//				                LandRevenueDemandPre::getKhataOwnerName,
//				                (existing, replacement) -> existing // handle duplicates gracefully
//				            ));
//
//				        // Update the finalList with owner names
//				        for (LandRevenueDemandDao record : finalList) {
//				            if (record.getKhataOwnerName() == null || record.getKhataOwnerName().trim().isEmpty()) {
//				                String ownerNameFromDb = khataNoToOwnerNameMap.get(record.getKhataNo());
//				                if (ownerNameFromDb != null) {
//				                    record.setKhataOwnerName(ownerNameFromDb);
//				                }
//				            }
//				        }
//				    }
				    
//				    int count2 = 0;
//					 for (LandRevenueDemandDao item : finalList) {
//			                System.out.println("YE HAI FINAL LIST updated Wali------  : "+item);
//			                System.out.println("FinalList Record Counter---------------  : "+count++);
//					 }

					
					
					return finalList;
				}
			}
			throw new ResourceNotFoundException("error.form.select");
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	private List<LandRevenueDemandDao> mergeList(List<LandRevenueDemandDao> finalList) {

		Map<String, List<LandRevenueDemandDao>> grouped = finalList.stream()
				.collect(Collectors.groupingBy(obj -> obj.getKhataNo() + " " + obj.getCCode()));

		List<LandRevenueDemandDao> groupedAndSummedUp = grouped.values().stream().map(group -> {
			return group.stream().reduce((obj1, obj2) -> {

				// ternary condition ? true->statement1 : false -> statement2

				// actual 'merging' of objects is happening here

				obj1.setArea((obj1.getArea() == null ? BigDecimal.ZERO : obj1.getArea())
						.add((obj2.getArea() == null ? BigDecimal.ZERO : obj2.getArea())));
				obj1.setAssessment((obj1.getAssessment() == null ? BigDecimal.ZERO : obj1.getAssessment())
						.add(obj2.getAssessment() == null ? BigDecimal.ZERO : obj2.getAssessment()));

				obj1.setSurveyHissaNo(obj1.getSurveyHissaNo().concat(obj2.getSurveyHissaNo()));
				obj1.setUom(obj1.getUom().concat(obj2.getUom()));

				// bindumala
				obj1.setJmBindumala((obj1.getJmBindumala()).add((obj2.getJmBindumala())));
				obj1.setZpBindumala((obj1.getZpBindumala()).add((obj2.getZpBindumala())));
				obj1.setGpBindumala((obj1.getGpBindumala()).add((obj2.getGpBindumala())));

				// dumala
				obj1.setJmDumala(obj1.getJmDumala().add(obj2.getJmDumala()));
				obj1.setZpDumala(obj1.getZpDumala().add(obj2.getZpDumala()));
				obj1.setGpDumala(obj1.getGpDumala().add(obj2.getGpDumala()));

				// akrushik
				obj1.setJmAkrushik(obj1.getJmAkrushik().add(obj2.getJmAkrushik()));
				obj1.setZpAkrushik(obj1.getZpAkrushik().add(obj2.getZpAkrushik()));
				obj1.setGpAkrushik(obj1.getGpAkrushik().add(obj2.getGpAkrushik()));

				// educational cess
				obj1.setEducationalCess(obj1.getEducationalCess().add(obj2.getEducationalCess()));

				// egs
				obj1.setEmployeeGuaranteeScheme(
						obj1.getEmployeeGuaranteeScheme().add(obj2.getEmployeeGuaranteeScheme()));

				// addl landrevenue
				obj1.setAddlLandRevenue(obj1.getAddlLandRevenue().add(obj2.getAddlLandRevenue()));

				// addl educational cess
				obj1.setAddlEducationalCess(obj1.getAddlEducationalCess().add(obj2.getAddlEducationalCess()));

				// vajasut if(assessment>0 and assessment<5)then vajasut else 0
				obj1.setJmVajasut(obj1.getJmVajasut().add(obj2.getJmVajasut()));
				obj1.setZpVajasut(obj1.getZpVajasut().add(obj2.getZpVajasut()));
				obj1.setGpVajasut(obj1.getGpVajasut().add(obj2.getGpVajasut()));

				// one time entry
				obj1.setPreYearPendingGp(obj1.getPreYearPendingGp().add(obj2.getPreYearPendingGp()));
				obj1.setPreYearPendingJm(obj1.getPreYearPendingJm().add(obj2.getPreYearPendingJm()));
				obj1.setPreYearPendingZp(obj1.getPreYearPendingZp().add(obj2.getPreYearPendingZp()));
				obj1.setPreYearPendingAddlLandRevenue(
						obj1.getPreYearPendingAddlLandRevenue().add(obj2.getPreYearPendingAddlLandRevenue()));
				obj1.setPreYearPendingAddlEducationalCess(
						obj1.getPreYearPendingAddlEducationalCess().add(obj2.getPreYearPendingAddlEducationalCess()));
				obj1.setPreYearPendingEducationalCess(
						obj1.getPreYearPendingEducationalCess().add(obj2.getPreYearPendingEducationalCess()));
				obj1.setPreYearPendingEmployeeGuaranteeScheme(obj1.getPreYearPendingEmployeeGuaranteeScheme()
						.add(obj2.getPreYearPendingEmployeeGuaranteeScheme()));

				// obj1.setMiscellaneousAmount(obj1.getMiscellaneousAmount()==null ?
				// BigDecimal.ZERO:obj1.getMiscellaneousAmount().add(obj2.getMiscellaneousAmount()==null
				// ? BigDecimal.ZERO:obj2.getMiscellaneousAmount()));
				obj1.setPreYearNoticeFee(obj1.getPreYearNoticeFee().add(obj2.getPreYearNoticeFee()));
				obj1.setPreYearPendingNaCess(obj1.getPreYearPendingNaCess().add(obj2.getPreYearPendingNaCess()));
				obj1.setPreYearSankirnJmWith(obj1.getPreYearSankirnJmWith().add(obj2.getPreYearSankirnJmWith()));
				obj1.setPreYearSankirnJmWithout(
						obj1.getPreYearSankirnJmWithout().add(obj2.getPreYearSankirnJmWithout()));

				// sankirn
				obj1.setJmSankirn(obj1.getJmSankirn().add(obj2.getJmSankirn()));
				obj1.setZpSankirn(obj1.getZpSankirn().add(obj2.getZpSankirn()));
				obj1.setGpSankirn(obj1.getGpSankirn().add(obj2.getGpSankirn()));

				// total demand amount
				obj1.setNetAmount(obj1.getNetAmount() == null ? BigDecimal.ZERO
						: obj1.getNetAmount().add(obj2.getNetAmount() == null ? BigDecimal.ZERO : obj2.getNetAmount()));
				obj1.setNetPending(obj1.getNetPending() == null ? BigDecimal.ZERO
						: obj1.getNetPending()
								.add(obj2.getNetPending() == null ? BigDecimal.ZERO : obj2.getNetPending()));

				return obj1;
			}).get();
		}).collect(Collectors.toList());
		return groupedAndSummedUp;
	}

	private List<LandRevenueDemandDao> getEmployeeGuaranteeSchemeDetails(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request) {
		/*
		 * List<Object[]> lstLRForEGS =
		 * landRevenueDemandRepository.getLRForEmployeeGuaranteeScheme(
		 * CommonConstants.AugDatamhrHost,request.getHeader("mhrDbName"),request.
		 * getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.
		 * mhrorPassword,
		 * request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader
		 * ("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
		 * landRevenueDemandDao.getRevenueYear(),
		 * landRevenueDemandDao.getCCode(),CommonConstants.AugDataPort);
		 */
		
		List<LandRevenueDemandDao> lstEGS = null;
		
		try {
			
			int netCultiArea = Integer.valueOf(CommonConstants.netCultiArea);

			List<Object[]> lstLRForEGS = landRevenueDemandRepository.getLRForEmployeeGuaranteeScheme(
					request.getHeader("mhrHost"), request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"),
					CommonConstants.mhrorUser, CommonConstants.mhrorPassword, request.getHeader("echHost"),
					request.getHeader("echDbName"), request.getHeader("echSchemaName"), CommonConstants.echUser,
					CommonConstants.echPassword, landRevenueDemandDao.getRevenueYear(), landRevenueDemandDao.getCCode(),
					netCultiArea);
			
			
			for (Object[] arr : lstLRForEGS) {
			    System.out.println("EGS Query Data -------- : "+Arrays.toString(arr));
			}
			
			
			
			if (lstLRForEGS != null) {

				lstEGS = lstLRForEGS.stream().map(objects -> {

					double area = Double.parseDouble(objects[2].toString()) * 100; // Multiply 100 because - convert it into
																					// Gunta
					
					double addlEGS = 0;
					if (area > 40) {
						double addle = (area - 40) / 100;
						addlEGS = addle * 25;

						// System.out.println("For calc EGS : ------- "+addlEGS);

					}

					
					
					LandRevenueDemandDao lrDao = new LandRevenueDemandDao();

					lrDao.setRevenueYear(landRevenueDemandDao.getRevenueYear());

					lrDao.setDistrictCode(landRevenueDemandDao.getDistrictCode());
					lrDao.setTalukaCode(landRevenueDemandDao.getTalukaCode());

					lrDao.setCCode(landRevenueDemandDao.getCCode());

					lrDao.setArea(BigDecimal.ZERO);
					lrDao.setAssessment(BigDecimal.ZERO);

					lrDao.setSurveyHissaNo("");
					lrDao.setUom("");

					lrDao.setKhataNo(objects[0].toString());
					lrDao.setKhataOwnerName(objects[1].toString());

					lrDao.setJmBindumala(BigDecimal.ZERO);
					lrDao.setZpBindumala(BigDecimal.ZERO);
					lrDao.setGpBindumala(BigDecimal.ZERO);

					lrDao.setJmDumala(BigDecimal.ZERO);
					lrDao.setZpDumala(BigDecimal.ZERO);
					lrDao.setGpDumala(BigDecimal.ZERO);

					lrDao.setJmAkrushik(BigDecimal.ZERO);
					lrDao.setZpAkrushik(BigDecimal.ZERO);
					lrDao.setGpAkrushik(BigDecimal.ZERO);

					lrDao.setJmVajasut(BigDecimal.ZERO);
					lrDao.setZpVajasut(BigDecimal.ZERO);
					lrDao.setGpVajasut(BigDecimal.ZERO);

					lrDao.setEducationalCess(BigDecimal.ZERO);
					lrDao.setAddlEducationalCess(BigDecimal.ZERO);

					lrDao.setAddlLandRevenue(BigDecimal.ZERO);

					lrDao.setEmployeeGuaranteeScheme(BigDecimal.valueOf(addlEGS).setScale(0, BigDecimal.ROUND_HALF_UP));

					lrDao.setPreYearPendingGp(BigDecimal.ZERO);
					lrDao.setPreYearPendingJm(BigDecimal.ZERO);
					lrDao.setPreYearPendingZp(BigDecimal.ZERO);
					lrDao.setPreYearPendingAddlLandRevenue(BigDecimal.ZERO);
					lrDao.setPreYearPendingAddlEducationalCess(BigDecimal.ZERO);
					lrDao.setPreYearPendingEducationalCess(BigDecimal.ZERO);
					lrDao.setPreYearPendingEmployeeGuaranteeScheme(BigDecimal.ZERO);
					lrDao.setPreYearNoticeFee(BigDecimal.ZERO);
					lrDao.setPreYearPendingNaCess(BigDecimal.ZERO);
					lrDao.setPreYearSankirnJmWith(BigDecimal.ZERO);
					lrDao.setPreYearSankirnJmWithout(BigDecimal.ZERO);
					// lrDao.setMiscellaneousAmount(BigDecimal.ZERO);

					lrDao.setGpSankirn(BigDecimal.ZERO);
					lrDao.setZpSankirn(BigDecimal.ZERO);
					lrDao.setJmSankirn(BigDecimal.ZERO);

					lrDao.setNetAmount(BigDecimal.valueOf(addlEGS).setScale(0, BigDecimal.ROUND_HALF_UP));
					lrDao.setNetPending(BigDecimal.valueOf(addlEGS).setScale(0, BigDecimal.ROUND_HALF_UP));

					lrDao.setActiveFlag('Y');

					return lrDao;
				}).collect(Collectors.toList());
			} // EGS
			
			
		}catch(Exception e) {
			System.out.println("Exception at EGS if (lstLRForEGS != null) Condtion---------"+e.getMessage());
		}
		
		return lstEGS;
		
	}

	private List<LandRevenueDemandDao> getEducationCess(LandRevenueDemandDao landRevenueDemandDao,
			List<Object[]> lstLRForEduCess, Map<Integer, Double> mpCropCode) {

		List<LandRevenueDemandDao> lstLREducatnCess = null;

		if (lstLRForEduCess != null) {

			lstLREducatnCess = lstLRForEduCess.stream().map(objects -> {

				String khataNo = objects[0].toString();
				Integer cropCode = Integer.parseInt(objects[2].toString());
				Double cropHectorPrice = Double.parseDouble(objects[4].toString());

				// System.out.println("Khata_no in Edu_CEss : "+khataNo);
				// System.out.println("cropCode in Edu_CEss : "+cropCode);
				// System.out.println("cropHectorPrice in Edu_CEss : "+cropHectorPrice);

				if (mpCropCode != null && cropCode != null && mpCropCode.containsKey(cropCode)) {

					LandRevenueDemandDao lrDao = new LandRevenueDemandDao();

					lrDao.setRevenueYear(landRevenueDemandDao.getRevenueYear());
					lrDao.setDistrictCode(landRevenueDemandDao.getDistrictCode());
					lrDao.setTalukaCode(landRevenueDemandDao.getTalukaCode());
					lrDao.setCCode(landRevenueDemandDao.getCCode());

					lrDao.setKhataNo(khataNo);

					lrDao.setKhataOwnerName(objects[1].toString());

					lrDao.setSurveyHissaNo("");
					lrDao.setUom("");

					lrDao.setArea(BigDecimal.ZERO);
					lrDao.setAssessment(BigDecimal.ZERO);

					lrDao.setJmBindumala(BigDecimal.ZERO);
					lrDao.setZpBindumala(BigDecimal.ZERO);
					lrDao.setGpBindumala(BigDecimal.ZERO);

					lrDao.setJmDumala(BigDecimal.ZERO);
					lrDao.setZpDumala(BigDecimal.ZERO);
					lrDao.setGpDumala(BigDecimal.ZERO);

					lrDao.setJmAkrushik(BigDecimal.ZERO);
					lrDao.setZpAkrushik(BigDecimal.ZERO);
					lrDao.setGpAkrushik(BigDecimal.ZERO);

					lrDao.setAddlLandRevenue(BigDecimal.ZERO);
					lrDao.setEmployeeGuaranteeScheme(BigDecimal.ZERO);

					lrDao.setJmVajasut(BigDecimal.ZERO);
					lrDao.setZpVajasut(BigDecimal.ZERO);
					lrDao.setGpVajasut(BigDecimal.ZERO);

					lrDao.setPreYearPendingGp(BigDecimal.ZERO);
					lrDao.setPreYearPendingJm(BigDecimal.ZERO);
					lrDao.setPreYearPendingZp(BigDecimal.ZERO);
					lrDao.setPreYearPendingAddlLandRevenue(BigDecimal.ZERO);
					lrDao.setPreYearPendingAddlEducationalCess(BigDecimal.ZERO);
					lrDao.setPreYearPendingEducationalCess(BigDecimal.ZERO);
					lrDao.setPreYearPendingEmployeeGuaranteeScheme(BigDecimal.ZERO);
					// lrDao.setMiscellaneousAmount(BigDecimal.ZERO);
					lrDao.setPreYearNoticeFee(BigDecimal.ZERO);
					lrDao.setPreYearPendingNaCess(BigDecimal.ZERO);
					lrDao.setPreYearSankirnJmWith(BigDecimal.ZERO);
					lrDao.setPreYearSankirnJmWithout(BigDecimal.ZERO);

					lrDao.setGpSankirn(BigDecimal.ZERO);
					lrDao.setZpSankirn(BigDecimal.ZERO);
					lrDao.setJmSankirn(BigDecimal.ZERO);

					BigDecimal mstHecPrice = BigDecimal.valueOf(mpCropCode.get(cropCode) * cropHectorPrice);

					// System.out.println("mpCropCode.get(cropCode) edu_cess : ------
					// "+mpCropCode.get(cropCode) );

					// System.out.println("cropHectorPrice edu_cess : ------ "+cropHectorPrice );

					// System.out.println("mstHecPrice final result Edu_cess : ------
					// "+cropHectorPrice );

					lrDao.setEducationalCess(mstHecPrice.setScale(0, BigDecimal.ROUND_HALF_UP));
					return lrDao;
				}
				return null;

			}).collect(Collectors.toList());
		} // Education Cess
		lstLREducatnCess.removeAll(Collections.singleton(null));

		// Map<String, List<LandRevenueDemandDao>> map =
		// lstLREducatnCess.stream().collect(Collectors.groupingBy(p ->
		// getGroupingByKey(p)));

		List<LandRevenueDemandDao> summarized = new ArrayList<>();

		Map<LandRevenueDemandDao, List<LandRevenueDemandDao>> collect = lstLREducatnCess.stream()
				.collect(Collectors.groupingBy(new Function<LandRevenueDemandDao, LandRevenueDemandDao>() {

					@Override
					public LandRevenueDemandDao apply(LandRevenueDemandDao t) {
						Optional<LandRevenueDemandDao> fOpt = summarized.stream()
								.filter(e -> e.getKhataNo().equals(t.getKhataNo())).findFirst();
						LandRevenueDemandDao f;
						if (!fOpt.isPresent()) {
							f = new LandRevenueDemandDao(t.getKhataNo(), BigDecimal.ZERO, BigDecimal.ZERO);
							summarized.add(f);
						} else {
							f = fOpt.get();
						}
						BigDecimal bd = f.getEducationalCess().add(t.getEducationalCess());

						f.setArea(BigDecimal.ZERO);
						f.setAssessment(BigDecimal.ZERO);
						f.setSurveyHissaNo("");
						f.setUom("");

						f.setEducationalCess(bd);
						f.setCCode(t.getCCode());
						f.setKhataOwnerName(t.getKhataOwnerName());
						f.setRevenueYear(t.getRevenueYear());

						f.setNetAmount(bd);
						f.setNetPending(bd);

						f.setJmBindumala(BigDecimal.ZERO);
						f.setZpBindumala(BigDecimal.ZERO);
						f.setGpBindumala(BigDecimal.ZERO);

						f.setJmDumala(BigDecimal.ZERO);
						f.setZpDumala(BigDecimal.ZERO);
						f.setGpDumala(BigDecimal.ZERO);

						f.setJmAkrushik(BigDecimal.ZERO);
						f.setZpAkrushik(BigDecimal.ZERO);
						f.setGpAkrushik(BigDecimal.ZERO);

						f.setAddlLandRevenue(BigDecimal.ZERO);
						f.setEmployeeGuaranteeScheme(BigDecimal.ZERO);

						f.setJmVajasut(BigDecimal.ZERO);
						f.setZpVajasut(BigDecimal.ZERO);
						f.setGpVajasut(BigDecimal.ZERO);

						f.setPreYearPendingGp(BigDecimal.ZERO);
						f.setPreYearPendingJm(BigDecimal.ZERO);
						f.setPreYearPendingZp(BigDecimal.ZERO);
						f.setPreYearPendingAddlLandRevenue(BigDecimal.ZERO);
						f.setPreYearPendingAddlEducationalCess(BigDecimal.ZERO);
						f.setPreYearPendingEducationalCess(BigDecimal.ZERO);
						f.setPreYearPendingEmployeeGuaranteeScheme(BigDecimal.ZERO);
						f.setPreYearNoticeFee(BigDecimal.ZERO);
						f.setPreYearPendingNaCess(BigDecimal.ZERO);
						f.setPreYearSankirnJmWith(BigDecimal.ZERO);
						f.setPreYearSankirnJmWithout(BigDecimal.ZERO);
						// f.setMiscellaneousAmount(BigDecimal.ZERO);

						f.setGpSankirn(BigDecimal.ZERO);
						f.setZpSankirn(BigDecimal.ZERO);
						f.setJmSankirn(BigDecimal.ZERO);

						return f;
					}
				}));

		List<LandRevenueDemandDao> myList = collect.keySet().stream().collect(Collectors.toList());

		Set<LandRevenueDemandDao> lstFinal = new HashSet<>(myList);

		// Calculate Additional Educational Cess

		if (lstFinal != null & lstFinal.size() > 0) {
			for (LandRevenueDemandDao lrRevenueDao : lstFinal) {

				BigDecimal addlECess = BigDecimal.ZERO;
				BigDecimal edu200 = BigDecimal.valueOf(200);
				BigDecimal edu500 = BigDecimal.valueOf(500);
				BigDecimal edu1000 = BigDecimal.valueOf(1000);
				BigDecimal edu2000 = BigDecimal.valueOf(2000);

				BigDecimal eduCess = lrRevenueDao.getEducationalCess();

				if (eduCess.compareTo(edu200) <= 0) {
					addlECess = BigDecimal.ZERO;
				} else if (eduCess.compareTo(edu200) > 0 && eduCess.compareTo(edu500) <= 0) {
					BigDecimal amt = eduCess.subtract(edu200);
					addlECess = amt.multiply(BigDecimal.valueOf(0.25));
				} else if (eduCess.compareTo(edu500) > 0 && eduCess.compareTo(edu1000) <= 0) {
					BigDecimal amt = eduCess.subtract(edu500);
					addlECess = BigDecimal.valueOf(75).add(amt.multiply(BigDecimal.valueOf(0.50)));
				} else if (eduCess.compareTo(edu1000) > 0 && eduCess.compareTo(edu2000) <= 0) {
					BigDecimal amt = eduCess.subtract(edu1000);
					addlECess = BigDecimal.valueOf(325).add(amt.multiply(BigDecimal.valueOf(1)));
				} else if (eduCess.compareTo(edu2000) > 0) {
					BigDecimal amt = eduCess.subtract(edu2000);
					addlECess = BigDecimal.valueOf(1325).add(amt.multiply(BigDecimal.valueOf(1.50)));
				}

				lrRevenueDao.setAddlEducationalCess(addlECess.setScale(0, BigDecimal.ROUND_HALF_UP));
				lrRevenueDao.setNetAmount((lrRevenueDao.getEducationalCess().add(lrRevenueDao.getAddlEducationalCess()))
						.setScale(0, BigDecimal.ROUND_HALF_UP));
				lrRevenueDao
						.setNetPending((lrRevenueDao.getEducationalCess().add(lrRevenueDao.getAddlEducationalCess()))
								.setScale(0, BigDecimal.ROUND_HALF_UP));

			}
		}

		// lstLREducatnCess.stream().collect(Collectors.groupingBy(p ->
		// getGroupingByKey(p))).entrySet().stream().collect(Collectors.toMap(x -> {
		return new ArrayList<>(lstFinal);

	}

	private List<LandRevenueDemandDao> getAkrushakDetails(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, List<AdditionalLandRevenue> adlAdditionalLandRevenueLst,
			List<Form17NoKaJaPa> form17NoKaJaPaLst, Response akrushikRateNapaAndManapa, Response akrushikRateRural,
			Response form2PrayojanType) {
		List<LandRevenueDemandDao> lstLRAkrushik = null;

		List<Object[]> lstLRForAkrushik = landRevenueDemandRepository.getLRForAkrushik(CommonConstants.AugDatamhrHost,
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("echHost"), request.getHeader("echDbName"),
				request.getHeader("echSchemaName"), CommonConstants.echUser, CommonConstants.echPassword,
				landRevenueDemandDao.getRevenueYear(), landRevenueDemandDao.getCCode(), CommonConstants.AugDataPort);
		
		
		if (lstLRForAkrushik != null) {
			
			
			lstLRAkrushik = lstLRForAkrushik.stream().map(objects -> {

				Form17NoKaJaPa kaJaPaArea = form17NoKaJaPaLst.stream()
						.filter(f17NR -> objects[0].toString().equals(f17NR.getKhataNo())).findFirst().orElse(null);
				// Form17NoKaJaPa
				// kaJaPaArea=landRevenueDemandDao.getForm17NoKaJaPaLst().stream().filter(f17NR
				// -> khataNo.equals(f17NR.getKhataNo())).findFirst().orElse(null);

				double assessment = Double.parseDouble(objects[3].toString());

				double area = Double.parseDouble(objects[2].toString());

				String akrushakRate = null;
				String formPrayojanType = null;
				double area1 = 0;

				if (!akrushikRateNapaAndManapa.getAkrushakRate().isEmpty()
						&& akrushikRateNapaAndManapa.getAkrushakRate() != null) {
					for (AkrushakRateDao response : akrushikRateNapaAndManapa.getAkrushakRate()) {
						if (response.getKhataNo().equals(objects[0].toString())) {
							akrushakRate = response.getRate();
							break;
						}
					}
				} else {
					akrushakRate = (akrushikRateRural != null && akrushikRateRural.getAkrushakRate() != null
							&& akrushikRateRural.getAkrushakRate().size() > 0
									? akrushikRateRural.getAkrushakRate().get(0).getRate().toString()
									: "0.10");
				}

				double rate = Double.parseDouble(akrushakRate == null ? "0.10" : akrushakRate);

				if (!form2PrayojanType.getAkrushakRate().isEmpty() && form2PrayojanType.getAkrushakRate() != null) {
					for (AkrushakRateDao response : form2PrayojanType.getAkrushakRate()) {
						if (response.getKhataNo().equals(objects[0].toString())) {
							formPrayojanType = response.getRate();
							break;
						}
					}
				} else {
					formPrayojanType = "1";
				}
				
				double prayojanType = Double.parseDouble(formPrayojanType == null ? "1" : formPrayojanType);

				area1 = area * rate * prayojanType * 100; // convert into rqm to sqm

					assessment = area1
						+ Double.parseDouble(kaJaPaArea != null ? kaJaPaArea.getAssessment().toString() : "0");
				double assessment1 = BigDecimal.valueOf(assessment).setScale(0, BigDecimal.ROUND_HALF_DOWN)
						.doubleValue();
				area += Double.parseDouble(kaJaPaArea != null ? kaJaPaArea.getAreaAffected().toString() : "0");

			
				LandRevenueDemandDao lrDao = new LandRevenueDemandDao();
				
			

				lrDao.setRevenueYear(landRevenueDemandDao.getRevenueYear());

				lrDao.setDistrictCode(landRevenueDemandDao.getDistrictCode());
				lrDao.setTalukaCode(landRevenueDemandDao.getTalukaCode());

				lrDao.setCCode(landRevenueDemandDao.getCCode());

				lrDao.setKhataNo(objects[0].toString());
				lrDao.setKhataOwnerName(objects[1].toString());

				//removed below line for akrushkik 
				//lrDao.setArea(BigDecimal.valueOf(area));
				//lrDao.setAssessment(BigDecimal.valueOf(assessment1).setScale(0, BigDecimal.ROUND_HALF_DOWN));

				
				lrDao.setArea(BigDecimal.ZERO);
				lrDao.setAssessment(BigDecimal.ZERO);

				
				
				lrDao.setSurveyHissaNo(objects[4].toString());
				lrDao.setUom(objects[5].toString());

				lrDao.setJmBindumala(BigDecimal.ZERO);
				lrDao.setZpBindumala(BigDecimal.ZERO);
				lrDao.setGpBindumala(BigDecimal.ZERO);

				lrDao.setJmDumala(BigDecimal.ZERO);
				lrDao.setZpDumala(BigDecimal.ZERO);
				lrDao.setGpDumala(BigDecimal.ZERO);
				
				
				System.out.println("found the khata_no------------------- : ");
				System.out.println("Khata_no ------------------ : "+lrDao.getKhataNo());
				System.out.println("assessmentFor--------- : "+assessment);
				System.out.println("assessment1For--------- : "+assessment1);
				System.out.println("area1--------- : "+area1);
				System.out.println("area--------- : "+area);
				System.out.println("rate--------- : "+rate);
				System.out.println("proyojanType--------- : "+prayojanType);
				System.out.println("akrushakRate--------- : "+akrushakRate);
				
				
				
				System.out.println("Break------------------------------------------------End");

//				if(assessment >= 5) {
				if (!akrushikRateNapaAndManapa.getAkrushakRate().isEmpty()
						&& akrushikRateNapaAndManapa.getAkrushakRate() != null) {
					//oldcode before removing akrushik
					//lrDao.setJmAkrushik(BigDecimal.valueOf(assessment1 * 1).setScale(0, BigDecimal.ROUND_HALF_UP));
					lrDao.setJmAkrushik(BigDecimal.ZERO);
					lrDao.setZpAkrushik(BigDecimal.ZERO);
					lrDao.setGpAkrushik(BigDecimal.ZERO);
				} else {
					
					//oldcode before removing akrushik
//					lrDao.setJmAkrushik(BigDecimal.valueOf(assessment1 * 1).setScale(0, BigDecimal.ROUND_HALF_UP));
//					lrDao.setZpAkrushik(BigDecimal.valueOf(assessment1 * 7).setScale(0, BigDecimal.ROUND_HALF_UP));
//					lrDao.setGpAkrushik(BigDecimal.valueOf(assessment1 * 1).setScale(0, BigDecimal.ROUND_HALF_UP));
//					
					
					lrDao.setJmAkrushik(BigDecimal.ZERO);
					lrDao.setZpAkrushik(BigDecimal.ZERO);
					lrDao.setGpAkrushik(BigDecimal.ZERO);
					
				}

				lrDao.setJmVajasut(BigDecimal.ZERO);
				lrDao.setZpVajasut(BigDecimal.ZERO);
				lrDao.setGpVajasut(BigDecimal.ZERO);


				lrDao.setEducationalCess(BigDecimal.ZERO);
				lrDao.setAddlEducationalCess(BigDecimal.ZERO);
				lrDao.setEmployeeGuaranteeScheme(BigDecimal.ZERO);
				lrDao.setAddlLandRevenue(BigDecimal.ZERO);

			
				lrDao.setPreYearPendingGp(BigDecimal.ZERO);
				lrDao.setPreYearPendingJm(BigDecimal.ZERO);
				lrDao.setPreYearPendingZp(BigDecimal.ZERO);
				lrDao.setPreYearPendingAddlLandRevenue(BigDecimal.ZERO);
				lrDao.setPreYearPendingAddlEducationalCess(BigDecimal.ZERO);
				lrDao.setPreYearPendingEducationalCess(BigDecimal.ZERO);
				lrDao.setPreYearPendingEmployeeGuaranteeScheme(BigDecimal.ZERO);
				lrDao.setPreYearNoticeFee(BigDecimal.ZERO);
				lrDao.setPreYearPendingNaCess(BigDecimal.ZERO);
				lrDao.setPreYearSankirnJmWith(BigDecimal.ZERO);
				lrDao.setPreYearSankirnJmWithout(BigDecimal.ZERO);

				lrDao.setGpSankirn(BigDecimal.ZERO);
				lrDao.setZpSankirn(BigDecimal.ZERO);
				lrDao.setJmSankirn(BigDecimal.ZERO);


//				lrDao.setNetAmount(((lrDao.getJmAkrushik().add(lrDao.getZpAkrushik()).add(lrDao.getGpAkrushik())
//						.add(lrDao.getAddlLandRevenue())).setScale(0, BigDecimal.ROUND_HALF_UP)));
//				lrDao.setNetPending((lrDao.getJmAkrushik().add(lrDao.getZpAkrushik()).add(lrDao.getGpAkrushik())
//						.add(lrDao.getAddlLandRevenue())).setScale(0, BigDecimal.ROUND_HALF_UP));
				
				lrDao.setNetAmount(BigDecimal.ZERO);
				lrDao.setNetPending(BigDecimal.ZERO);


				lrDao.setActiveFlag('Y');

				return lrDao;
			}).collect(Collectors.toList());
			// AKRUSHIK
		}
		return lstLRAkrushik;
	}

	private List<LandRevenueDemandDao> getAkrushakDetailsKaJaPa(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, List<AdditionalLandRevenue> adlAdditionalLandRevenueLst,
			List<Form17NoKaJaPa> form17NoKaJaPaLst) {
		List<LandRevenueDemandDao> lstLRAkrushik = null;

		if (!form17NoKaJaPaLst.isEmpty()) {
			lstLRAkrushik = form17NoKaJaPaLst.stream().map(f17R -> {

				// AdditionalLandRevenue
				// additionalLandRevenue=adlAdditionalLandRevenueLst.stream().filter(f17NR ->
				// f17R.getKhataNo().equals(f17NR.getKhataNo())).findFirst().orElse(null);

				double assessment = Double.parseDouble(f17R.getAssessment().toString());

				assessment = Double.parseDouble(f17R.getAssessment().toString());

				LandRevenueDemandDao lrDao = new LandRevenueDemandDao();

				lrDao.setRevenueYear(landRevenueDemandDao.getRevenueYear());

				lrDao.setCCode(landRevenueDemandDao.getCCode());
				lrDao.setDistrictCode(landRevenueDemandDao.getDistrictCode());
				lrDao.setTalukaCode(landRevenueDemandDao.getTalukaCode());

				lrDao.setArea(f17R.getAreaAffected());

				lrDao.setAssessment(BigDecimal.ZERO);

				lrDao.setKhataNo(f17R.getKhataNo().toString());
				lrDao.setKhataOwnerName(f17R.getPersonLiable().toString());

				lrDao.setSurveyHissaNo(f17R.getSurveyHissaNo() != null ? f17R.getSurveyHissaNo() : "");
				lrDao.setUom("");
				// lrDao.setUom("R.sqm.");

				lrDao.setJmBindumala(BigDecimal.ZERO);
				lrDao.setZpBindumala(BigDecimal.ZERO);
				lrDao.setGpBindumala(BigDecimal.ZERO);

				lrDao.setJmDumala(BigDecimal.ZERO);
				lrDao.setZpDumala(BigDecimal.ZERO);
				lrDao.setGpDumala(BigDecimal.ZERO);

//				if(assessment >= 5) {
				lrDao.setJmAkrushik(f17R.getAmountOfJm().setScale(0, BigDecimal.ROUND_HALF_DOWN));
				lrDao.setZpAkrushik(f17R.getAmountOfZp().setScale(0, BigDecimal.ROUND_HALF_DOWN));
				lrDao.setGpAkrushik(f17R.getAmountOfGp().setScale(0, BigDecimal.ROUND_HALF_DOWN));

				lrDao.setJmVajasut(BigDecimal.ZERO);
				lrDao.setZpVajasut(BigDecimal.ZERO);
				lrDao.setGpVajasut(BigDecimal.ZERO);
//				} else {
//					lrDao.setJmAkrushik(BigDecimal.ZERO);
//					lrDao.setZpAkrushik(BigDecimal.ZERO);
//					lrDao.setGpAkrushik(BigDecimal.ZERO);
//
//					lrDao.setJmVajasut(f17R.getAmountOfJm());
//					lrDao.setZpVajasut(f17R.getAmountOfZp());
//					lrDao.setGpVajasut(f17R.getAmountOfGp());
//				}

				lrDao.setEducationalCess(BigDecimal.ZERO);
				lrDao.setAddlEducationalCess(BigDecimal.ZERO);
				lrDao.setEmployeeGuaranteeScheme(BigDecimal.ZERO);
				lrDao.setAddlLandRevenue(BigDecimal.ZERO);

				// lrDao.setAddlLandRevenue(
				// additionalLandRevenue !=null
				// && additionalLandRevenue.getApplicableForDemand()=='Y'
				// && additionalLandRevenue.getActiveFlag()=='Y'
				// && additionalLandRevenue.getAssessment()!=null
				// ?
				// additionalLandRevenue.getAssessment().setScale(0,BigDecimal.ROUND_HALF_DOWN):BigDecimal.ZERO);

				lrDao.setPreYearPendingGp(BigDecimal.ZERO);
				lrDao.setPreYearPendingJm(BigDecimal.ZERO);
				lrDao.setPreYearPendingZp(BigDecimal.ZERO);
				lrDao.setPreYearPendingAddlLandRevenue(BigDecimal.ZERO);
				lrDao.setPreYearPendingAddlEducationalCess(BigDecimal.ZERO);
				lrDao.setPreYearPendingEducationalCess(BigDecimal.ZERO);
				lrDao.setPreYearPendingEmployeeGuaranteeScheme(BigDecimal.ZERO);
				lrDao.setPreYearNoticeFee(BigDecimal.ZERO);
				lrDao.setPreYearPendingNaCess(BigDecimal.ZERO);
				lrDao.setPreYearSankirnJmWith(BigDecimal.ZERO);
				lrDao.setPreYearSankirnJmWithout(BigDecimal.ZERO);

				lrDao.setGpSankirn(BigDecimal.ZERO);
				lrDao.setZpSankirn(BigDecimal.ZERO);
				lrDao.setJmSankirn(BigDecimal.ZERO);

//				if(assessment >= 5 && assessment <=10) {
//					lrDao.setNetAmount((lrDao.getZpAkrushik()).add(lrDao.getGpAkrushik()).setScale(0,BigDecimal.ROUND_HALF_UP));
//					lrDao.setNetPending((lrDao.getZpAkrushik()).add(lrDao.getGpAkrushik()).setScale(0,BigDecimal.ROUND_HALF_UP));
//				}else if(assessment > 10) {
				lrDao.setNetAmount((lrDao.getJmAkrushik()).add(lrDao.getZpAkrushik()).add(lrDao.getGpAkrushik())
						.setScale(0, BigDecimal.ROUND_HALF_UP));
				lrDao.setNetPending((lrDao.getJmAkrushik()).add(lrDao.getZpAkrushik()).add(lrDao.getGpAkrushik())
						.setScale(0, BigDecimal.ROUND_HALF_UP));
//				}else{
//					lrDao.setNetAmount(BigDecimal.ZERO);
//					lrDao.setNetPending(BigDecimal.ZERO);
//				}
				lrDao.setActiveFlag('Y');
				return lrDao;
			}).collect(Collectors.toList());
		}
		return lstLRAkrushik;
	}

	
	private List<LandRevenueDemandDao> getDumalaDetails(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, List<AdditionalLandRevenue> adlAdditionalLandRevenueLst,
			List<Form17NoKaJaPa> form17NoKaJaPaLst) {
		List<LandRevenueDemandDao> lstLRDumala = null;
		List<Object[]> lstLRForDumala = landRevenueDemandRepository.getLRForDumala(CommonConstants.AugDatamhrHost,
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("echHost"), request.getHeader("echDbName"),
				request.getHeader("echSchemaName"), CommonConstants.echUser, CommonConstants.echPassword,
				landRevenueDemandDao.getRevenueYear(), landRevenueDemandDao.getCCode(), CommonConstants.AugDataPort);
		if (lstLRForDumala != null) {
			lstLRDumala = lstLRForDumala.stream().map(objects -> {

				// Form17NoKaJaPa
				// kaJaPaArea=landRevenueDemandDao.getForm17NoKaJaPaLst().stream().filter(f17NR
				// -> khataNo.equals(f17NR.getKhataNo())).findFirst().orElse(null);
				Form17NoKaJaPa kaJaPaArea = form17NoKaJaPaLst.stream()
						.filter(f17NR -> objects[0].toString().equals(f17NR.getKhataNo())).findFirst().orElse(null);
				double assessment = Double.parseDouble(objects[3].toString());

				double area = Double.parseDouble(objects[2].toString())
						- Double.parseDouble(kaJaPaArea != null ? kaJaPaArea.getAreaAffected().toString() : "0");

				// double addlLR = 0;
				// if(area >= 8 && area < 12 ) {
				// addlLR = assessment * 0.50; // 50%
				// } else if (area>= 12) {
				// addlLR = assessment * 1; // 100%
				// }

				LandRevenueDemandDao lrDao = new LandRevenueDemandDao();
				// AdditionalLandRevenue
				// additionalLandRevenue=adlAdditionalLandRevenueLst.stream().filter(f17NR ->
				// objects[0].toString().equals(f17NR.getKhataNo())).findFirst().orElse(null);

				lrDao.setRevenueYear(landRevenueDemandDao.getRevenueYear());
				lrDao.setDistrictCode(landRevenueDemandDao.getDistrictCode());
				lrDao.setTalukaCode(landRevenueDemandDao.getTalukaCode());
				lrDao.setCCode(landRevenueDemandDao.getCCode());

				lrDao.setKhataNo(objects[0].toString());
				lrDao.setKhataOwnerName(objects[1].toString());

				lrDao.setArea(BigDecimal.valueOf(area));
				lrDao.setAssessment(BigDecimal.valueOf(assessment).setScale(2, BigDecimal.ROUND_HALF_DOWN));

				double assessment1 = BigDecimal.valueOf(assessment).setScale(0, BigDecimal.ROUND_HALF_DOWN)
						.doubleValue();

				lrDao.setBagayatArea(objects[5] == null ? "0.0000" : objects[5].toString());
				lrDao.setJirayatArea(objects[6] == null ? "0.0000" : objects[6].toString());

				double bagayatArea = Double
						.parseDouble(objects[5].toString() == null ? "0.0000" : objects[5].toString());

				double jirayatArea = Double
						.parseDouble(objects[6].toString() == null ? "0.0000" : objects[6].toString());

				lrDao.setSurveyHissaNo(objects[4].toString());
				lrDao.setUom(objects[7].toString());

				lrDao.setJmBindumala(BigDecimal.ZERO);
				lrDao.setZpBindumala(BigDecimal.ZERO);
				lrDao.setGpBindumala(BigDecimal.ZERO);


				/*old conditions by probity -- dumalaVajasut
				 * if (assessment > 0 && assessment < 5) {
					lrDao.setJmDumala(BigDecimal.valueOf(assessment * 1).setScale(2, BigDecimal.ROUND_HALF_UP));
					lrDao.setZpDumala(BigDecimal.valueOf(assessment * 7).setScale(2, BigDecimal.ROUND_HALF_UP));
					lrDao.setGpDumala(BigDecimal.valueOf(assessment * 1).setScale(2, BigDecimal.ROUND_HALF_UP));

					lrDao.setJmVajasut(BigDecimal.valueOf(assessment * 1).setScale(2, BigDecimal.ROUND_HALF_UP));
					lrDao.setZpVajasut(BigDecimal.valueOf(assessment * 7).setScale(2, BigDecimal.ROUND_HALF_UP));
					lrDao.setGpVajasut(BigDecimal.valueOf(assessment * 1).setScale(2, BigDecimal.ROUND_HALF_UP));

				} else if (assessment >= 5) {

					lrDao.setJmDumala(BigDecimal.valueOf(assessment1 * 1).setScale(0, BigDecimal.ROUND_HALF_UP));
					lrDao.setZpDumala(BigDecimal.valueOf(assessment1 * 7).setScale(0, BigDecimal.ROUND_HALF_UP));
					lrDao.setGpDumala(BigDecimal.valueOf(assessment1 * 1).setScale(0, BigDecimal.ROUND_HALF_UP));

					lrDao.setJmVajasut(BigDecimal.ZERO);
					lrDao.setZpVajasut(BigDecimal.ZERO);
					lrDao.setGpVajasut(BigDecimal.ZERO);

				} else {

					lrDao.setJmDumala(BigDecimal.ZERO);
					lrDao.setZpDumala(BigDecimal.ZERO);
					lrDao.setGpDumala(BigDecimal.ZERO);

					lrDao.setJmVajasut(BigDecimal.ZERO);
					lrDao.setZpVajasut(BigDecimal.ZERO);
					lrDao.setGpVajasut(BigDecimal.ZERO);
				}*/
				
				
				   if (assessment > 0 && assessment < 5) {
		                lrDao.setJmDumala(BigDecimal.valueOf(assessment * 1).setScale(2, BigDecimal.ROUND_HALF_UP));
		                lrDao.setZpDumala(BigDecimal.valueOf(assessment * 7).setScale(2, BigDecimal.ROUND_HALF_UP));
		                lrDao.setGpDumala(BigDecimal.valueOf(assessment * 1).setScale(2, BigDecimal.ROUND_HALF_UP));

		                lrDao.setJmVajasut(BigDecimal.valueOf(assessment * 1).setScale(2, BigDecimal.ROUND_HALF_UP));
		                lrDao.setZpVajasut(BigDecimal.valueOf(assessment * 7).setScale(2, BigDecimal.ROUND_HALF_UP));
		                lrDao.setGpVajasut(BigDecimal.valueOf(assessment * 1).setScale(2, BigDecimal.ROUND_HALF_UP));
		//change by shubham 

		            } else if (assessment >= 5 && assessment <= 10) { 

		                lrDao.setJmDumala(BigDecimal.valueOf(assessment1 * 1).setScale(0, BigDecimal.ROUND_HALF_UP));
		                lrDao.setZpDumala(BigDecimal.valueOf(assessment1 * 7).setScale(0, BigDecimal.ROUND_HALF_UP));
		                lrDao.setGpDumala(BigDecimal.valueOf(assessment1 * 1).setScale(0, BigDecimal.ROUND_HALF_UP));

		                lrDao.setJmVajasut(lrDao.getJmDumala()); 
		                // lrDao.setJmVajasut(BigDecimal.ZERO);
		                lrDao.setZpVajasut(BigDecimal.ZERO);
		                lrDao.setGpVajasut(BigDecimal.ZERO);

		//change by shubham 
		            } else if (assessment > 10 && area <= 3) { 

		                lrDao.setJmDumala(BigDecimal.valueOf(assessment1 * 1).setScale(0, BigDecimal.ROUND_HALF_UP));
		                lrDao.setZpDumala(BigDecimal.valueOf(assessment1 * 7).setScale(0, BigDecimal.ROUND_HALF_UP));
		                lrDao.setGpDumala(BigDecimal.valueOf(assessment1 * 1).setScale(0, BigDecimal.ROUND_HALF_UP));

		                lrDao.setJmVajasut(lrDao.getJmDumala()); //chnage by shubham
		                // lrDao.setJmVajasut(BigDecimal.ZERO);
		                lrDao.setZpVajasut(BigDecimal.ZERO);
		                lrDao.setGpVajasut(BigDecimal.ZERO);

		            } else {

		                lrDao.setJmDumala(BigDecimal.ZERO);
		                lrDao.setZpDumala(BigDecimal.ZERO);
		                lrDao.setGpDumala(BigDecimal.ZERO);

		                lrDao.setJmVajasut(BigDecimal.ZERO);
		                lrDao.setZpVajasut(BigDecimal.ZERO);
		                lrDao.setGpVajasut(BigDecimal.ZERO);
		            }

		          

				lrDao.setJmAkrushik(BigDecimal.ZERO);
				lrDao.setZpAkrushik(BigDecimal.ZERO);
				lrDao.setGpAkrushik(BigDecimal.ZERO);

				lrDao.setEducationalCess(BigDecimal.ZERO);
				lrDao.setAddlEducationalCess(BigDecimal.ZERO);
				lrDao.setEmployeeGuaranteeScheme(BigDecimal.ZERO);

				lrDao.setAddlLandRevenue(BigDecimal.ZERO);
				
				lrDao.setPreYearPendingGp(BigDecimal.ZERO);
				lrDao.setPreYearPendingJm(BigDecimal.ZERO);
				lrDao.setPreYearPendingZp(BigDecimal.ZERO);
				lrDao.setPreYearPendingAddlLandRevenue(BigDecimal.ZERO);
				lrDao.setPreYearPendingAddlEducationalCess(BigDecimal.ZERO);
				lrDao.setPreYearPendingEducationalCess(BigDecimal.ZERO);
				lrDao.setPreYearPendingEmployeeGuaranteeScheme(BigDecimal.ZERO);
				lrDao.setPreYearNoticeFee(BigDecimal.ZERO);
				lrDao.setPreYearPendingNaCess(BigDecimal.ZERO);
				lrDao.setPreYearSankirnJmWith(BigDecimal.ZERO);
				lrDao.setPreYearSankirnJmWithout(BigDecimal.ZERO);

				lrDao.setGpSankirn(BigDecimal.ZERO);
				lrDao.setZpSankirn(BigDecimal.ZERO);
				lrDao.setJmSankirn(BigDecimal.ZERO);

				if (bagayatArea == 0 && jirayatArea <= 2.99) {
					if (assessment >= 5 && assessment <= 10) {
						lrDao.setNetAmount((lrDao.getZpDumala()).add(lrDao.getGpDumala())
								.add(lrDao.getAddlLandRevenue()).setScale(0, BigDecimal.ROUND_HALF_UP));
						lrDao.setNetPending((lrDao.getZpDumala()).add(lrDao.getGpDumala())
								.add(lrDao.getAddlLandRevenue()).setScale(0, BigDecimal.ROUND_HALF_UP));
					} else if (assessment > 10) {
						lrDao.setNetAmount((lrDao.getZpDumala()).add(lrDao.getGpDumala())
								.add(lrDao.getAddlLandRevenue()).setScale(0, BigDecimal.ROUND_HALF_UP));
						lrDao.setNetPending((lrDao.getZpDumala()).add(lrDao.getGpDumala())
								.add(lrDao.getAddlLandRevenue()).setScale(0, BigDecimal.ROUND_HALF_UP));
					} else {
						lrDao.setNetAmount(BigDecimal.ZERO);
						lrDao.setNetPending(BigDecimal.ZERO);
					}
				}

				if ((bagayatArea >= 0 && jirayatArea >= 3.00) || (bagayatArea > 0 && jirayatArea <= 2.99)) {
					if (assessment >= 5 && assessment <= 10) {
						lrDao.setNetAmount((lrDao.getZpDumala()).add(lrDao.getGpDumala())
								.add(lrDao.getAddlLandRevenue()).setScale(0, BigDecimal.ROUND_HALF_UP));
						lrDao.setNetPending((lrDao.getZpDumala()).add(lrDao.getGpDumala())
								.add(lrDao.getAddlLandRevenue()).setScale(0, BigDecimal.ROUND_HALF_UP));
					} else if (assessment > 10) {
						lrDao.setNetAmount((lrDao.getJmDumala()).add(lrDao.getZpDumala()).add(lrDao.getGpDumala())
								.add(lrDao.getAddlLandRevenue()).setScale(0, BigDecimal.ROUND_HALF_UP));
						lrDao.setNetPending((lrDao.getJmDumala()).add(lrDao.getZpDumala()).add(lrDao.getGpDumala())
								.add(lrDao.getAddlLandRevenue()).setScale(0, BigDecimal.ROUND_HALF_UP));
					} else {
						lrDao.setNetAmount(BigDecimal.ZERO);
						lrDao.setNetPending(BigDecimal.ZERO);
					}
				}

				lrDao.setActiveFlag('Y');

				return lrDao;
			}).collect(Collectors.toList());
		} // dumala
		return lstLRDumala;
		
	}
	
	

	private List<LandRevenueDemandDao> getBinDumalaDetails(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, List<AdditionalLandRevenue> adlAdditionalLandRevenueLst,
			List<Form17NoKaJaPa> form17NoKaJaPaLst, List<KhataMergeDtl> kmKhataMergeDtlLst,
			List<KhataMerge> kmKhataMergeLst) {

		List<Object[]> finalMergeLst = null;
		List<Object[]> listToRemove = new ArrayList<>();
		List<LandRevenueDemandDao> lstLRBindumala = null;

		List<Object[]> lstLandDetails = landRevenueDemandRepository.getLRForBinbumala(CommonConstants.AugDatamhrHost,
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("echHost"), request.getHeader("echDbName"),
				request.getHeader("echSchemaName"), CommonConstants.echUser, CommonConstants.echPassword,
				landRevenueDemandDao.getRevenueYear(), landRevenueDemandDao.getCCode(), CommonConstants.AugDataPort);
		// System.out.println("----------------------After getLRForBinbumala");

		if (lstLandDetails != null) {

			// System.out.println("----------------After getLRForBinbumala in if ");
			kmKhataMergeDtlLst = kmKhataMergeDtlLst.stream().map(r -> {
				Object[] object = lstLandDetails.stream().filter(o -> o[0].toString().equals(r.getKhataNo()))
						.findFirst().orElse(null);
				if (object != null) {
					// System.out.println("----------------After getLRForBinbumala in id of
					// kmKhataMergeDtlLst ");
					r.setArea(BigDecimal.valueOf(Double.valueOf(object[2].toString())));
					r.setAssessment(BigDecimal.valueOf(Double.valueOf(object[3].toString())));
					r.setSurveyHissaNo(object[4].toString());
					r.setBagayatArea(object[5].toString());
					r.setJirayatArea(object[6].toString());
					r.setUom(object[7].toString());
					listToRemove.add(object);
				}
				return r;
			}).collect(Collectors.toList());
			// System.out.println("----------------After lstLandDetails ");
			lstLandDetails.removeAll(listToRemove);

			// final List<KhataMergeDtl> khataMergeDtlLstUp
			// =kmKhataMergeDtlLst.stream().map(r->{return
			// r;}).collect(Collectors.toList());

			// kmKhataMergeLst=kmKhataMergeLst.stream().map(r->{
			// KhataMergeDtl kMDtl=khataMergeDtlLstUp.stream().filter(kmDtlR->
			// kmDtlR.getParentId().equals(r.getId())).findFirst().orElse(null);
			// if(kMDtl!=null) {
			// r.setKhataOwnerName(kMDtl.getKhataOwnerName()!=null ?
			// kMDtl.getKhataOwnerName() : " "+ "/" + r.getKhataOwnerName() !=null ?
			// r.getKhataOwnerName() : "");
			// r.setArea(kMDtl.getArea()!=null ? kMDtl.getArea() :
			// BigDecimal.ZERO.add(r.getArea()!=null ? r.getArea() : BigDecimal.ZERO));
			// r.setAssessment(kMDtl.getAssessment()!=null ? kMDtl.getAssessment() :
			// BigDecimal.ZERO.add(r.getAssessment()!=null ? r.getAssessment() :
			// BigDecimal.ZERO));
			// r.setSurveyHissaNo(kMDtl.getSurveyHissaNo()!=null ? kMDtl.getSurveyHissaNo()
			// : "" +","+ r.getSurveyHissaNo()!=null ? r.getSurveyHissaNo() : "" );
			// r.setUom(r.getUom()!=null ? r.getUom() : "");
			// }
			// khataMergeDtlLstUp.remove(kMDtl);
			// return r;
			// }).collect(Collectors.toList());
			//
			// System.out.println("----------------Before kmKhataMergeLst 11 ");
			if (!kmKhataMergeLst.isEmpty()) {
				finalMergeLst = kmKhataMergeLst.stream().map(r -> {
					Object[] objects = new Object[11];
					objects[0] = r.getKhataNo();
					objects[1] = r.getKhataOwnerName();
					objects[2] = r.getArea();
					objects[3] = r.getAssessment();
					objects[4] = r.getSurveyHissaNo();
					objects[5] = "0.0000";
					objects[6] = "0.0000";
					objects[7] = "H-R.Sq.M.";
					objects[8] = "0.0000";
					objects[9] = "0.0000";
					objects[10] = "0.0000";
					return objects;
				}).collect(Collectors.toList());
			}
			// System.out.println("----------------After kmKhataMergeLst 22 ");
			if (finalMergeLst != null) {
				lstLandDetails.addAll(finalMergeLst);
			}

			lstLRBindumala = lstLandDetails.stream().map(objects -> {

				// Form17NoKaJaPa
				// kaJaPaArea=landRevenueDemandDao.getForm17NoKaJaPaLst().stream().filter(f17NR
				// ->
				// objects[0].toString().equals(f17NR.getKhataNo())).findFirst().orElse(null);
				Form17NoKaJaPa kaJaPaArea = form17NoKaJaPaLst.stream()
						.filter(f17NR -> objects[0].toString().equals(f17NR.getKhataNo())).findFirst().orElse(null);

				double area = Double.parseDouble(objects[2].toString())
						- Double.parseDouble(kaJaPaArea != null ? kaJaPaArea.getAreaAffected().toString() : "0");

				double assessment = Double.parseDouble(objects[3].toString());

				assessment = BigDecimal.valueOf(assessment).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
				double assessment1 = BigDecimal.valueOf(assessment).setScale(0, BigDecimal.ROUND_HALF_DOWN)
						.doubleValue();

				// AdditionalLandRevenue
				// additionalLandRevenue=landRevenueDemandDao.getAdditionalLandRevenue().stream().filter(f17NR
				// ->
				// objects[0].toString().equals(f17NR.getKhataNo())).findFirst().orElse(null);

				// AdditionalLandRevenue
				// additionalLandRevenue=adlAdditionalLandRevenueLst.stream().filter(f17NR ->
				// objects[0].toString().equals(f17NR.getKhataNo())).findFirst().orElse(null);

				LandRevenueDemandDao lrDao = new LandRevenueDemandDao();

				lrDao.setRevenueYear(landRevenueDemandDao.getRevenueYear());
				lrDao.setCCode(landRevenueDemandDao.getCCode());

				lrDao.setKhataNo(objects[0].toString());
				lrDao.setKhataOwnerName(objects[1].toString());

				lrDao.setBagayatArea(objects[5].toString() == null ? "0.0000" : objects[5].toString());

				lrDao.setJirayatArea(objects[6].toString() == null ? "0.0000" : objects[6].toString());

				double bagayatArea = Double
						.parseDouble(objects[5].toString() == null ? "0.0000" : objects[5].toString());

				double jirayatArea = Double
						.parseDouble(objects[6].toString() == null ? "0.0000" : objects[6].toString());

				// added 10 sept 2024 by tushar
				double tariArea = Double.parseDouble(objects[8].toString() == null ? "0.0000" : objects[8].toString());

				double varkasArea = Double
						.parseDouble(objects[9].toString() == null ? "0.0000" : objects[9].toString());

				double itarArea = Double
						.parseDouble(objects[10].toString() == null ? "0.0000" : objects[10].toString());
				// added 10 sept 2024 by tushar end

//				double areaofBagayatandJirayat=Double.parseDouble(objects[5] == null ? "0.0000" : objects[5].toString()) + Double.parseDouble(objects[6] == null ? "0.0000" : objects[6].toString());
//				System.out.println(areaofBagayatandJirayat);

				lrDao.setArea(BigDecimal.valueOf(area));
				lrDao.setAssessment(BigDecimal.valueOf(assessment).setScale(2, BigDecimal.ROUND_HALF_DOWN));

				lrDao.setSurveyHissaNo(objects[4].toString());
				lrDao.setUom(objects[7].toString());

//				if(assessment >= 5) {
//					lrDao.setJmBindumala(BigDecimal.valueOf(assessment * 1).setScale(0,BigDecimal.ROUND_HALF_UP));
//					lrDao.setZpBindumala(BigDecimal.valueOf(assessment * 7).setScale(0,BigDecimal.ROUND_HALF_UP));
//					lrDao.setGpBindumala(BigDecimal.valueOf(assessment * 1).setScale(0,BigDecimal.ROUND_HALF_UP));
//
//					lrDao.setJmVajasut(BigDecimal.ZERO);
//					lrDao.setZpVajasut(BigDecimal.ZERO);
//					lrDao.setGpVajasut(BigDecimal.ZERO);
//
//				} else if(lrDao.getAssessment().longValue() > 0) {
//
//					lrDao.setJmBindumala(BigDecimal.valueOf(assessment * 1).setScale(0,BigDecimal.ROUND_HALF_UP));
//					lrDao.setZpBindumala(BigDecimal.valueOf(assessment * 7).setScale(0,BigDecimal.ROUND_HALF_UP));
//					lrDao.setGpBindumala(BigDecimal.valueOf(assessment * 1).setScale(0,BigDecimal.ROUND_HALF_UP));
//
//					lrDao.setJmVajasut(BigDecimal.valueOf(assessment * 1).setScale(0,BigDecimal.ROUND_HALF_UP));
//					lrDao.setZpVajasut(BigDecimal.valueOf(assessment * 7).setScale(0,BigDecimal.ROUND_HALF_UP));
//					lrDao.setGpVajasut(BigDecimal.valueOf(assessment * 1).setScale(0,BigDecimal.ROUND_HALF_UP));
//				} else {
//					lrDao.setJmBindumala(BigDecimal.ZERO);
//					lrDao.setZpBindumala(BigDecimal.ZERO);
//					lrDao.setGpBindumala(BigDecimal.ZERO);
//
//					lrDao.setJmVajasut(BigDecimal.ZERO);
//					lrDao.setZpVajasut(BigDecimal.ZERO);
//					lrDao.setGpVajasut(BigDecimal.ZERO);
//				}

				/*
				 * old one 11 aug commented code if(assessment > 0 && assessment < 5 ) {
				 * lrDao.setJmBindumala(BigDecimal.valueOf(assessment *
				 * 1).setScale(2,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setZpBindumala(BigDecimal.valueOf(assessment *
				 * 7).setScale(2,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setGpBindumala(BigDecimal.valueOf(assessment *
				 * 1).setScale(2,BigDecimal.ROUND_HALF_UP));
				 * 
				 * lrDao.setJmVajasut(BigDecimal.valueOf(assessment *
				 * 1).setScale(2,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setZpVajasut(BigDecimal.valueOf(assessment *
				 * 7).setScale(2,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setGpVajasut(BigDecimal.valueOf(assessment *
				 * 1).setScale(2,BigDecimal.ROUND_HALF_UP));
				 * 
				 * } else if(assessment >= 5 ) {
				 * 
				 * lrDao.setJmBindumala(BigDecimal.valueOf(assessment1 *
				 * 1).setScale(0,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setZpBindumala(BigDecimal.valueOf(assessment1 *
				 * 7).setScale(0,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setGpBindumala(BigDecimal.valueOf(assessment1 *
				 * 1).setScale(0,BigDecimal.ROUND_HALF_UP));
				 * 
				 * lrDao.setJmVajasut(BigDecimal.ZERO); lrDao.setZpVajasut(BigDecimal.ZERO);
				 * lrDao.setGpVajasut(BigDecimal.ZERO);
				 * 
				 * } else {
				 * 
				 * lrDao.setJmBindumala(BigDecimal.ZERO); lrDao.setZpBindumala(BigDecimal.ZERO);
				 * lrDao.setGpBindumala(BigDecimal.ZERO);
				 * 
				 * lrDao.setJmVajasut(BigDecimal.ZERO); lrDao.setZpVajasut(BigDecimal.ZERO);
				 * lrDao.setGpVajasut(BigDecimal.ZERO); }
				 */
				/*
				 * Commented on 10 Sept 2024 if(assessment > 0 && assessment < 5 ) {
				 * 
				 * lrDao.setJmBindumala(BigDecimal.valueOf(assessment *
				 * 1).setScale(2,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setZpBindumala(BigDecimal.valueOf(assessment *
				 * 7).setScale(2,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setGpBindumala(BigDecimal.valueOf(assessment *
				 * 1).setScale(2,BigDecimal.ROUND_HALF_UP));
				 * 
				 * lrDao.setJmVajasut(BigDecimal.valueOf(assessment *
				 * 1).setScale(2,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setZpVajasut(BigDecimal.valueOf(assessment *
				 * 7).setScale(2,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setGpVajasut(BigDecimal.valueOf(assessment *
				 * 1).setScale(2,BigDecimal.ROUND_HALF_UP));
				 * 
				 * } else if((assessment >= 5 && assessment <= 10) || area <= 3.00) {
				 * 
				 * lrDao.setJmBindumala(BigDecimal.valueOf(assessment1 *
				 * 1).setScale(0,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setZpBindumala(BigDecimal.valueOf(assessment1 *
				 * 7).setScale(0,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setGpBindumala(BigDecimal.valueOf(assessment1 *
				 * 1).setScale(0,BigDecimal.ROUND_HALF_UP));
				 * 
				 * lrDao.setJmVajasut(lrDao.getJmBindumala());
				 * //lrDao.setJmVajasut(BigDecimal.ZERO); lrDao.setZpVajasut(BigDecimal.ZERO);
				 * lrDao.setGpVajasut(BigDecimal.ZERO);
				 * 
				 * } else if (assessment > 10 && area <= 3 ){
				 * 
				 * 
				 * lrDao.setJmBindumala(BigDecimal.valueOf(assessment1 *
				 * 1).setScale(0,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setZpBindumala(BigDecimal.valueOf(assessment1 *
				 * 7).setScale(0,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setGpBindumala(BigDecimal.valueOf(assessment1 *
				 * 1).setScale(0,BigDecimal.ROUND_HALF_UP));
				 * 
				 * lrDao.setJmVajasut(lrDao.getJmBindumala());
				 * //lrDao.setJmVajasut(BigDecimal.ZERO); lrDao.setZpVajasut(BigDecimal.ZERO);
				 * lrDao.setGpVajasut(BigDecimal.ZERO);
				 * 
				 * }else {
				 * 
				 * 
				 * lrDao.setJmBindumala(BigDecimal.valueOf(assessment1 *
				 * 1).setScale(0,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setZpBindumala(BigDecimal.valueOf(assessment1 *
				 * 7).setScale(0,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setGpBindumala(BigDecimal.valueOf(assessment1 *
				 * 1).setScale(0,BigDecimal.ROUND_HALF_UP));
				 * 
				 * 
				 * lrDao.setJmVajasut(BigDecimal.ZERO); lrDao.setZpVajasut(BigDecimal.ZERO);
				 * lrDao.setGpVajasut(BigDecimal.ZERO);
				 * 
				 * // lrDao.setJmBindumala(BigDecimal.ZERO); //
				 * lrDao.setZpBindumala(BigDecimal.ZERO); //
				 * lrDao.setGpBindumala(BigDecimal.ZERO); // //
				 * lrDao.setJmVajasut(BigDecimal.ZERO); // lrDao.setZpVajasut(BigDecimal.ZERO);
				 * // lrDao.setGpVajasut(BigDecimal.ZERO);
				 * 
				 * } End Commented on 10 sept 2024
				 */

				// New Logic 10 sept 2024 : Vajasut By tushar
				if (assessment > 0 && assessment < 5) {

					lrDao.setJmBindumala(BigDecimal.valueOf(assessment * 1).setScale(2, BigDecimal.ROUND_HALF_UP));
					lrDao.setZpBindumala(BigDecimal.valueOf(assessment * 7).setScale(2, BigDecimal.ROUND_HALF_UP));
					lrDao.setGpBindumala(BigDecimal.valueOf(assessment * 1).setScale(2, BigDecimal.ROUND_HALF_UP));

					lrDao.setJmVajasut(BigDecimal.valueOf(assessment * 1).setScale(2, BigDecimal.ROUND_HALF_UP));
					lrDao.setZpVajasut(BigDecimal.valueOf(assessment * 7).setScale(2, BigDecimal.ROUND_HALF_UP));
					lrDao.setGpVajasut(BigDecimal.valueOf(assessment * 1).setScale(2, BigDecimal.ROUND_HALF_UP));

				} else if (assessment >= 5 && assessment <= 10)
				//else if ((assessment >= 5 && assessment <= 10) && jirayatArea > 0 && bagayatArea == 0 && tariArea == 0
						//&& varkasArea == 0 && itarArea == 0) // else if((assessment >= 5 && assessment <= 10) || area <=
																// 3.00)
				{

					lrDao.setJmBindumala(BigDecimal.valueOf(assessment1 * 1).setScale(0, BigDecimal.ROUND_HALF_UP));
					lrDao.setZpBindumala(BigDecimal.valueOf(assessment1 * 7).setScale(0, BigDecimal.ROUND_HALF_UP));
					lrDao.setGpBindumala(BigDecimal.valueOf(assessment1 * 1).setScale(0, BigDecimal.ROUND_HALF_UP));

					lrDao.setJmVajasut(lrDao.getJmBindumala());
					// lrDao.setJmVajasut(BigDecimal.ZERO);
					lrDao.setZpVajasut(BigDecimal.ZERO);
					lrDao.setGpVajasut(BigDecimal.ZERO);

				} else if (assessment > 10 && area <= 3)
				//else if (assessment > 10 && area <= 3 && bagayatArea == 0 && tariArea == 0 && varkasArea == 0
						//&& itarArea == 0)// else if (assessment > 10 && area <= 3 )
				{

					lrDao.setJmBindumala(BigDecimal.valueOf(assessment1 * 1).setScale(0, BigDecimal.ROUND_HALF_UP));
					lrDao.setZpBindumala(BigDecimal.valueOf(assessment1 * 7).setScale(0, BigDecimal.ROUND_HALF_UP));
					lrDao.setGpBindumala(BigDecimal.valueOf(assessment1 * 1).setScale(0, BigDecimal.ROUND_HALF_UP));

					lrDao.setJmVajasut(lrDao.getJmBindumala());
					// lrDao.setJmVajasut(BigDecimal.ZERO);
					lrDao.setZpVajasut(BigDecimal.ZERO);
					lrDao.setGpVajasut(BigDecimal.ZERO);

				} else {

					lrDao.setJmBindumala(BigDecimal.valueOf(assessment1 * 1).setScale(0, BigDecimal.ROUND_HALF_UP));
					lrDao.setZpBindumala(BigDecimal.valueOf(assessment1 * 7).setScale(0, BigDecimal.ROUND_HALF_UP));
					lrDao.setGpBindumala(BigDecimal.valueOf(assessment1 * 1).setScale(0, BigDecimal.ROUND_HALF_UP));

					lrDao.setJmVajasut(BigDecimal.ZERO);
					lrDao.setZpVajasut(BigDecimal.ZERO);
					lrDao.setGpVajasut(BigDecimal.ZERO);

					/*
					 * lrDao.setJmBindumala(BigDecimal.ZERO); lrDao.setZpBindumala(BigDecimal.ZERO);
					 * lrDao.setGpBindumala(BigDecimal.ZERO);
					 * 
					 * lrDao.setJmVajasut(BigDecimal.ZERO); lrDao.setZpVajasut(BigDecimal.ZERO);
					 * lrDao.setGpVajasut(BigDecimal.ZERO);
					 */

				}

				// New Logic 10 sept 2024 : Vajasut By tushar

				lrDao.setJmDumala(BigDecimal.ZERO);
				lrDao.setZpDumala(BigDecimal.ZERO);
				lrDao.setGpDumala(BigDecimal.ZERO);

				lrDao.setJmAkrushik(BigDecimal.ZERO);
				lrDao.setZpAkrushik(BigDecimal.ZERO);
				lrDao.setGpAkrushik(BigDecimal.ZERO);

				lrDao.setEducationalCess(BigDecimal.ZERO);
				lrDao.setAddlEducationalCess(BigDecimal.ZERO);
				lrDao.setEmployeeGuaranteeScheme(BigDecimal.ZERO);
				lrDao.setAddlLandRevenue(BigDecimal.ZERO);

				// lrDao.setAddlLandRevenue(
				// additionalLandRevenue !=null
				// && additionalLandRevenue.getApplicableForDemand()=='Y'
				// && additionalLandRevenue.getActiveFlag()=='Y'
				// && additionalLandRevenue.getAssessment()!=null
				// ?
				// additionalLandRevenue.getAssessment().setScale(0,BigDecimal.ROUND_HALF_DOWN):BigDecimal.ZERO);

				lrDao.setPreYearPendingGp(BigDecimal.ZERO);
				lrDao.setPreYearPendingJm(BigDecimal.ZERO);
				lrDao.setPreYearPendingZp(BigDecimal.ZERO);
				lrDao.setPreYearPendingAddlLandRevenue(BigDecimal.ZERO);
				lrDao.setPreYearPendingAddlEducationalCess(BigDecimal.ZERO);
				lrDao.setPreYearPendingEducationalCess(BigDecimal.ZERO);
				lrDao.setPreYearPendingEmployeeGuaranteeScheme(BigDecimal.ZERO);
				lrDao.setPreYearNoticeFee(BigDecimal.ZERO);
				lrDao.setPreYearPendingNaCess(BigDecimal.ZERO);
				lrDao.setPreYearSankirnJmWith(BigDecimal.ZERO);
				lrDao.setPreYearSankirnJmWithout(BigDecimal.ZERO);

				lrDao.setGpSankirn(BigDecimal.ZERO);
				lrDao.setZpSankirn(BigDecimal.ZERO);
				lrDao.setJmSankirn(BigDecimal.ZERO);

				/*
				 * 10 sept 2024 by tushar commented on this day if(bagayatArea==0 && jirayatArea
				 * <= 2.99 ) { if(assessment >= 5 && assessment <=10) {
				 * lrDao.setNetAmount((lrDao.getZpBindumala()==null ? BigDecimal.ZERO :
				 * lrDao.getZpBindumala()).add(lrDao.getGpBindumala()).add(lrDao.
				 * getAddlLandRevenue()).setScale(0,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setNetPending((lrDao.getZpBindumala()).add(lrDao.getGpBindumala()).add(
				 * lrDao.getAddlLandRevenue()).setScale(0,BigDecimal.ROUND_HALF_UP)); }else
				 * if(assessment > 10) {
				 * lrDao.setNetAmount((lrDao.getZpBindumala()).add(lrDao.getGpBindumala()).add(
				 * lrDao.getAddlLandRevenue()).setScale(0,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setNetPending((lrDao.getZpBindumala()).add(lrDao.getGpBindumala()).add(
				 * lrDao.getAddlLandRevenue()).setScale(0,BigDecimal.ROUND_HALF_UP)); }else{
				 * lrDao.setNetAmount(BigDecimal.ZERO); lrDao.setNetPending(BigDecimal.ZERO); }
				 * }
				 * 
				 * 
				 * if((bagayatArea >=0 && jirayatArea >= 3.00 ) || (bagayatArea >0 &&
				 * jirayatArea <= 2.99 )) { if(assessment >= 5 && assessment <=10) {
				 * lrDao.setNetAmount((lrDao.getZpBindumala()).add(lrDao.getGpBindumala()).add(
				 * lrDao.getAddlLandRevenue()).setScale(0,BigDecimal.ROUND_HALF_UP));
				 * lrDao.setNetPending((lrDao.getZpBindumala()).add(lrDao.getGpBindumala()).add(
				 * lrDao.getAddlLandRevenue()).setScale(0,BigDecimal.ROUND_HALF_UP)); }else
				 * if(assessment > 10) {
				 * lrDao.setNetAmount((lrDao.getJmBindumala()).add(lrDao.getZpBindumala()).add(
				 * lrDao.getGpBindumala()).add(lrDao.getAddlLandRevenue()).setScale(0,BigDecimal
				 * .ROUND_HALF_UP));
				 * lrDao.setNetPending((lrDao.getJmBindumala()).add(lrDao.getZpBindumala()).add(
				 * lrDao.getGpBindumala()).add(lrDao.getAddlLandRevenue()).setScale(0,BigDecimal
				 * .ROUND_HALF_UP)); }else{ lrDao.setNetAmount(BigDecimal.ZERO);
				 * lrDao.setNetPending(BigDecimal.ZERO); } }10 sept 2024 by tushar commented on
				 * this day end
				 */

				// 10 sept 2024 new logic of vajasut by tushar

				/*if (bagayatArea > 0 || tariArea > 0 || varkasArea > 0 || itarArea > 0) {

					if (assessment >= 5) {
						lrDao.setNetAmount(
								(lrDao.getJmBindumala()).add(lrDao.getZpBindumala()).add(lrDao.getGpBindumala())
										.add(lrDao.getAddlLandRevenue()).setScale(0, BigDecimal.ROUND_HALF_UP));
						lrDao.setNetPending(
								(lrDao.getJmBindumala()).add(lrDao.getZpBindumala()).add(lrDao.getGpBindumala())
										.add(lrDao.getAddlLandRevenue()).setScale(0, BigDecimal.ROUND_HALF_UP));
					} else {
						lrDao.setNetAmount(BigDecimal.ZERO);
						lrDao.setNetPending(BigDecimal.ZERO);
					}
				} else if (jirayatArea >= 0) // jm for jirayat area exmpt but for comparison total area should be less
												// than or= 3
				{*/
					if (assessment < 5) // if jm 5 then all exmpt
					{
						lrDao.setNetAmount(BigDecimal.ZERO);
						lrDao.setNetPending(BigDecimal.ZERO);

					} else if ((assessment >= 5 && assessment <= 10) || area < 3.00) // if jm greather than 5 and // lesthan or equal 10 or area 	// less than or equal 3 then jm
																					
					{
						lrDao.setNetAmount((lrDao.getZpBindumala()).add(lrDao.getGpBindumala())
								.add(lrDao.getAddlLandRevenue()).setScale(0, BigDecimal.ROUND_HALF_UP));
						lrDao.setNetPending((lrDao.getZpBindumala()).add(lrDao.getGpBindumala())
								.add(lrDao.getAddlLandRevenue()).setScale(0, BigDecimal.ROUND_HALF_UP));
					} else // if(assessment > 10) // if jm greater than 10 then all taxes applied.
					{
						lrDao.setNetAmount(
								(lrDao.getJmBindumala()).add(lrDao.getZpBindumala()).add(lrDao.getGpBindumala())
										.add(lrDao.getAddlLandRevenue()).setScale(0, BigDecimal.ROUND_HALF_UP));
						lrDao.setNetPending(
								(lrDao.getJmBindumala()).add(lrDao.getZpBindumala()).add(lrDao.getGpBindumala())
										.add(lrDao.getAddlLandRevenue()).setScale(0, BigDecimal.ROUND_HALF_UP));
					}
				//} // Need to add else of any other area than known area like bagayat, jirayat,
					// varkas ,tari and other
				/*---------------------------------------------------------*/

				// 10 sept 2024 new logic of vajasut by tushar end

				lrDao.setActiveFlag('Y');

				return lrDao;
			}).collect(Collectors.toList());
		} // end Binbumala
		return lstLRBindumala;
	}

//old 28 oct 2024
//	@Override
//	public List<LandRevenueDemandDao> getLandRevenueDemandDtl(LandRevenueDemandDao landRevenueDemandDao,HttpServletRequest request,Locale locale ) {
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//
//		List<Object[]> ccodeslist=khataMergeRepository.getccodeDetails(
//				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//				request.getHeader("servarthId"));
//		Response response =new  Response();
//		response.setKhataMergeData(ccodeslist.stream().map(r -> {
//			KhataMergeDao km=new KhataMergeDao();
//			km.setcCode(r[0].toString());
//			km.setServarthId(r[1].toString());
//			km.setVillageName(r[2].toString());
//			return km;
//		}).collect(Collectors.toList()));
//		
//		
//		String ccodes=null;
//		for(KhataMergeDao r : response.getKhataMergeData()) {
//			if(r.getcCode().equals(landRevenueDemandDao.getCCode())) {
//			ccodes=r.getcCode();
//			break;
//			}else{
//				ccodes="0";
//				}}
//		
//		if(ccodes.equals(landRevenueDemandDao.getCCode())) {	
//		if(landRevenueDemandDao != null && (landRevenueDemandDao.getKhataNo() != null || landRevenueDemandDao.getKhataOwnerName() !=null)) {
//			List<LandRevenueDemand> landRevenueDemand =new ArrayList<LandRevenueDemand>();
//
//			if(landRevenueDemandDao.getKhataOwnerName() !=null) {
//				//				landRevenueDemand = landRevenueDemandRepository.findAllForReciept(landRevenueDemandDao.getCCode(), "%"+landRevenueDemandDao.getKhataOwnerName()+"%");
//				landRevenueDemand = landRevenueDemandRepository.findBycCodeAndKhataOwnerNameContaining(landRevenueDemandDao.getCCode(),landRevenueDemandDao.getKhataOwnerName());
//			}else {
//				String [] khataNo = landRevenueDemandDao.getKhataNo().split("#");
//				List<String> lstKhataNo = Arrays.asList(khataNo);
//				landRevenueDemand = landRevenueDemandRepository.
//						findBycCodeAndKhataNoInAndRevenueYearAndActiveFlagAndLandReceiveForm17IdIsNull
//						(landRevenueDemandDao.getCCode(),lstKhataNo,landRevenueDemandDao.getRevenueYear(),'Y');
//
//				Boolean flag=false,flag1=true;
//				
//				for (LandRevenueDemand landRdetails:landRevenueDemand) {
//				if(landRevenueDemandDao.getNoticeFeesApplicable().equals('Y') && landRdetails.getReceiptNo() == null ) {
//					flag1=true;
//				}else {
//					flag1=false;
//				}
//				}
//				for(LandRevenueDemand lrd:landRevenueDemand) {
//					
//					double assessment = Double.parseDouble(lrd.getAssessment().toString());
//					double totarea = Double.parseDouble(lrd.getArea().toString());
//					
//					if((assessment >= 5 && assessment <= 10) || totarea <= 3.00) 
//					{
//						 lrd.setJmBindumala(BigDecimal.ZERO);
//					}else 
//					{
//						lrd.getJmBindumala();
//					}
//					
//					if(lrd.getNetAmount() != BigDecimal.ZERO) {
//						if(lrd.getDateOfNotice() ==null && lrd.getNoticeAmount() == null && lrd.getReceiptNo()==null) {
//							double netAmount = Double.parseDouble(lrd.getNetAmount().toString());
//							if(netAmount < 25 && flag1){
//								lrd.setNetAmount(lrd.getNetAmount().add(new BigDecimal(0.50)));
//								lrd.setNetPending(lrd.getNetPending().add(new BigDecimal(0.50)));
//								lrd.setNoticeAmount(new BigDecimal(0.50));
//							}else if (netAmount >= 25  && netAmount <= 100 && flag1){
//								lrd.setNetAmount(lrd.getNetAmount().add(new BigDecimal(1)));
//								lrd.setNetPending(lrd.getNetPending().add(new BigDecimal(1)));
//								lrd.setNoticeAmount(new BigDecimal(1));
//							}else if(netAmount >= 101 && flag1){
//								lrd.setNetAmount(lrd.getNetAmount().add(new BigDecimal(2)));
//								lrd.setNetPending(lrd.getNetPending().add(new BigDecimal(2)));
//								lrd.setNoticeAmount(new BigDecimal(2));
//							}
//
//							if(netAmount>0) {
//								lrd.setDateOfNotice(LocalDateTime.now());
//							}
//						}
//					}else {
//						DatabaseContextHolder.restoreCtx();
//						throw new NoContent("No Content");
//					}
//				}
//				if(flag1) {
//					landRevenueDemandRepository.saveAll(landRevenueDemand);
//					DatabaseContextHolder.restoreCtx();
//				}
//			}
//
//			DatabaseContextHolder.restoreCtx();
//			return landRevenueDemand.stream().map(lrdR -> toDao(lrdR)).collect(Collectors.toList());
//		}
//		DatabaseContextHolder.restoreCtx();
//		throw new NoContent("No Content");
//		}else {
//			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
//		}
//	}

	// new 28 -10 -2024

	@Override
	public List<LandRevenueDemandDao> getLandRevenueDemandDtl(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
//commented by me 24-10-2024
		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(landRevenueDemandDao.getCCode())) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}
		List<LandRevenueDemand> landRevenueDemand = new ArrayList<LandRevenueDemand>();
		Boolean flag = false, flag1 = true;
		System.out.println("-----------------1----" + landRevenueDemandDao.getKhataNo());
		System.out.println("-----------------2----" + landRevenueDemandDao);
		System.out.println("-----------------3----" + landRevenueDemandDao.getMakhtaKhataNo());
		if (ccodes.equals(landRevenueDemandDao.getCCode())) {
			if (landRevenueDemandDao != null && landRevenueDemandDao.getKhataNo() != null) {
				System.out.println("-----------------444 coming----");

				String[] khataNo = landRevenueDemandDao.getKhataNo().split("#");
				List<String> lstKhataNo = Arrays.asList(khataNo);
				landRevenueDemand = landRevenueDemandRepository
						.findBycCodeAndKhataNoInAndRevenueYearAndActiveFlagAndLandReceiveForm17IdIsNull(
								landRevenueDemandDao.getCCode(), lstKhataNo, landRevenueDemandDao.getRevenueYear(),
								'Y');
				System.out.println("-----------------Coming LandRevenue ----" + landRevenueDemand);

				if (landRevenueDemand == null || landRevenueDemand.isEmpty()) {
					throw new NoContent("No Content");
				}
				for (LandRevenueDemand landRdetails : landRevenueDemand) {
					if (landRevenueDemandDao.getNoticeFeesApplicable().equals('Y')
							&& landRdetails.getReceiptNo() == null) {
						flag1 = true;
					} else {
						flag1 = false;
					}
				}
				for (LandRevenueDemand lrd : landRevenueDemand) {

					double assessment = Double.parseDouble(lrd.getAssessment().toString());
					double totarea = Double.parseDouble(lrd.getArea().toString());

					if ((assessment >= 5 && assessment <= 10) || totarea <= 3.00) {
						lrd.setJmBindumala(BigDecimal.ZERO);
					} else {
						lrd.getJmBindumala();
					}

					if (lrd.getNetAmount() != BigDecimal.ZERO) {
						if (lrd.getDateOfNotice() == null && lrd.getNoticeAmount() == null
								&& lrd.getReceiptNo() == null) {
							double netAmount = Double.parseDouble(lrd.getNetAmount().toString());
							if (netAmount < 25 && flag1) {
								lrd.setNetAmount(lrd.getNetAmount().add(new BigDecimal(0.50)));
								lrd.setNetPending(lrd.getNetPending().add(new BigDecimal(0.50)));
								lrd.setNoticeAmount(new BigDecimal(0.50));
							} else if (netAmount >= 25 && netAmount <= 100 && flag1) {
								lrd.setNetAmount(lrd.getNetAmount().add(new BigDecimal(1)));
								lrd.setNetPending(lrd.getNetPending().add(new BigDecimal(1)));
								lrd.setNoticeAmount(new BigDecimal(1));
							} else if (netAmount >= 101 && flag1) {
								lrd.setNetAmount(lrd.getNetAmount().add(new BigDecimal(2)));
								lrd.setNetPending(lrd.getNetPending().add(new BigDecimal(2)));
								lrd.setNoticeAmount(new BigDecimal(2));
							}

							if (netAmount > 0) {
								lrd.setDateOfNotice(LocalDateTime.now());
							}
						}
					} else {
						DatabaseContextHolder.restoreCtx();
						throw new NoContent("No Content");
					}
				}
				if (flag1) {
					landRevenueDemandRepository.saveAll(landRevenueDemand);
					DatabaseContextHolder.restoreCtx();
				}
			} else if (landRevenueDemandDao != null && landRevenueDemandDao.getMakhtaKhataNo() != null) {

				System.out.println("-----------66666----------");
				String[] khataNo = landRevenueDemandDao.getMakhtaKhataNo().split("#");
				List<String> lstKhataNo = Arrays.asList(khataNo);
				landRevenueDemand = landRevenueDemandRepository
						.findBycCodeAndMakhtaKhataNoInAndRevenueYearAndActiveFlagAndLandReceiveForm17IdIsNull(
								landRevenueDemandDao.getCCode(), lstKhataNo, landRevenueDemandDao.getRevenueYear(),
								'Y');

				if (landRevenueDemand == null || landRevenueDemand.isEmpty()) {
					throw new NoContent("Khata No Does Not Found");
				}
				for (LandRevenueDemand landRdetails : landRevenueDemand) {
					if (landRevenueDemandDao.getNoticeFeesApplicable().equals('Y')
							&& landRdetails.getReceiptNo() == null) {
						flag1 = true;
					} else {
						flag1 = false;
					}
				}
				for (LandRevenueDemand lrd : landRevenueDemand) {

					double assessment = Double.parseDouble(lrd.getAssessment().toString());
					double totarea = Double.parseDouble(lrd.getArea().toString());

					if ((assessment >= 5 && assessment <= 10) || totarea <= 3.00) {
						lrd.setJmBindumala(BigDecimal.ZERO);
					} else {
						lrd.getJmBindumala();
					}

					if (lrd.getNetAmount() != BigDecimal.ZERO) {
						if (lrd.getDateOfNotice() == null && lrd.getNoticeAmount() == null
								&& lrd.getReceiptNo() == null) {
							double netAmount = Double.parseDouble(lrd.getNetAmount().toString());
							if (netAmount < 25 && flag1) {
								lrd.setNetAmount(lrd.getNetAmount().add(new BigDecimal(0.50)));
								lrd.setNetPending(lrd.getNetPending().add(new BigDecimal(0.50)));
								lrd.setNoticeAmount(new BigDecimal(0.50));
							} else if (netAmount >= 25 && netAmount <= 100 && flag1) {
								lrd.setNetAmount(lrd.getNetAmount().add(new BigDecimal(1)));
								lrd.setNetPending(lrd.getNetPending().add(new BigDecimal(1)));
								lrd.setNoticeAmount(new BigDecimal(1));
							} else if (netAmount >= 101 && flag1) {
								lrd.setNetAmount(lrd.getNetAmount().add(new BigDecimal(2)));
								lrd.setNetPending(lrd.getNetPending().add(new BigDecimal(2)));
								lrd.setNoticeAmount(new BigDecimal(2));
							}

							if (netAmount > 0) {
								lrd.setDateOfNotice(LocalDateTime.now());
							}
						}
					} else {
						DatabaseContextHolder.restoreCtx();
						throw new NoContent("No Content");
					}
				}
				if (flag1) {
					landRevenueDemandRepository.saveAll(landRevenueDemand);
					DatabaseContextHolder.restoreCtx();
				}

			}
			System.out.println("-----------5555----------");

			DatabaseContextHolder.restoreCtx();
			return landRevenueDemand.stream().map(lrdR -> toDao(lrdR)).collect(Collectors.toList());
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	@Override
	public LandRevenueDemandDao lrReceiptMoneyFromCustomer(LandRevenueDemandDao landRevenueDemandDao,
			BindingResult bindingResult, HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		
		LandRevenueDemand landRevenueDemand = landRevenueDemandRepository.getById(landRevenueDemandDao.getId());
		
		landRevenueDemand.setIsMoneyepositedBank('N');
		// 11/08/2024 ashlesh
		String modeOfPayment = "Cash";
		if (landRevenueDemandDao.getModeOfPayment().equals("DD")) {
			landRevenueDemand.setDdName(landRevenueDemandDao.getDdName());
			landRevenueDemand.setDdBranch(landRevenueDemandDao.getDdBranch());
			landRevenueDemand.setDdDate(landRevenueDemandDao.getDdDate());
			landRevenueDemand.setDdNumber(landRevenueDemandDao.getDdNumber());
			landRevenueDemand.setDdAmount(landRevenueDemandDao.getDdAmount());
			landRevenueDemand.setModeOfPayment("DD");
		} else if (landRevenueDemandDao.getModeOfPayment().equals("Cheque")) {
			landRevenueDemand.setChequeName(landRevenueDemandDao.getChequeName());
			landRevenueDemand.setChequeBranch(landRevenueDemandDao.getChequeBranch());
			landRevenueDemand.setChequeAmount(landRevenueDemandDao.getChequeAmount());
			landRevenueDemand.setChequeDate(landRevenueDemandDao.getChequeDate());
			landRevenueDemand.setChequeNumber(landRevenueDemandDao.getChequeNumber());
			landRevenueDemand.setModeOfPayment("Cheque");
		} else {
			landRevenueDemand.setModeOfPayment("Cash");
		}
		int uniqueDd = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

		Object ObjectlgdCode = new Object();
	
		System.out.println("before lgdCode Query ---------------------: ");
		
		try {
//			ObjectlgdCode = lgdMapperRepository.getCurrentLgdCodeForReciept(landRevenueDemandDao.getCCode(),
//					landRevenueDemand.getTalukaCode());
			
			ObjectlgdCode = "52343/11/28738728";
		}catch (Exception e) {
			System.out.println("Error at lgdCode Query : "+ e.getMessage());
		}
	 
		
		System.out.println("after lgdCode Query ---------------------: ");
		

		landRevenueDemand.setReceiptNo("RC/" + ObjectlgdCode + "/" + landRevenueDemandDao.getRevenueYear() + "/"
				+ landRevenueDemandDao.getKhataNo() + "/" + uniqueDd);
		landRevenueDemand.setReceiptDate(LocalDateTime.now());
		landRevenueDemand.setActiveFlag('Y');
		landRevenueDemand.setNetReceived(landRevenueDemandDao.getNetAmount());
		landRevenueDemandRepository.save(landRevenueDemand);

		ReceiptDtlLog dtlLog = new ReceiptDtlLog();
		dtlLog.setReceiptNo("RC/" + ObjectlgdCode + "/" + landRevenueDemandDao.getRevenueYear() + "/"
				+ landRevenueDemandDao.getKhataNo() + "/" + uniqueDd);
		dtlLog.setReceiptDate(LocalDateTime.now());
		dtlLog.setNetReceived(landRevenueDemandDao.getNetAmount());
		dtlLog.setUpdate_dt_tm(LocalDateTime.now());
		dtlLog.setIsMoneyepositedBank('N');
		dtlLog.setReceiptByUserid(request.getHeader("servarthId"));
		dtlLog.setModeOfPayment(modeOfPayment);
		BeanUtils.copyProperties(landRevenueDemand, dtlLog);
		dtlLogRepository.save(dtlLog);

		DatabaseContextHolder.restoreCtx();
		return toDao(landRevenueDemand);
	}

	private List<LandRevenueDemandDao> getOneTimeEntryDetails(String revenueYear, String cCode,
			HttpServletRequest request) {
		List<LandRevenueDemandDao> lstLROneTimeEntry = null;
		List<OneTimeEntry> lstLRForOneTimeEntry = oneTimeEntryRepository.findAllOneTimeEntry(
				request.getHeader("echHost"), request.getHeader("echDbName"), request.getHeader("echSchemaName"),
				CommonConstants.echUser, CommonConstants.echPassword, cCode, 'Y', revenueYear);
		if (lstLRForOneTimeEntry != null) {
			lstLROneTimeEntry = lstLRForOneTimeEntry.stream().map(oneTimeEntry -> {
				LandRevenueDemandDao lrDao = new LandRevenueDemandDao();
				if (oneTimeEntry.getcCode() != null && (oneTimeEntry.getKhataNo() != null)) {

					lrDao.setDistrictCode(oneTimeEntry.getDistrictCode());
					lrDao.setTalukaCode(oneTimeEntry.getTalukaCode());

					lrDao.setCCode(oneTimeEntry.getcCode());
					lrDao.setKhataNo(oneTimeEntry.getKhataNo());

					lrDao.setArea(BigDecimal.ZERO);
					lrDao.setAssessment(BigDecimal.ZERO);

					lrDao.setSurveyHissaNo("");
					lrDao.setUom("");

					lrDao.setJmBindumala(BigDecimal.ZERO);
					lrDao.setZpBindumala(BigDecimal.ZERO);
					lrDao.setGpBindumala(BigDecimal.ZERO);

					lrDao.setJmDumala(BigDecimal.ZERO);
					lrDao.setZpDumala(BigDecimal.ZERO);
					lrDao.setGpDumala(BigDecimal.ZERO);

					lrDao.setJmAkrushik(BigDecimal.ZERO);
					lrDao.setZpAkrushik(BigDecimal.ZERO);
					lrDao.setGpAkrushik(BigDecimal.ZERO);

					lrDao.setJmVajasut(BigDecimal.ZERO);
					lrDao.setZpVajasut(BigDecimal.ZERO);
					lrDao.setGpVajasut(BigDecimal.ZERO);

					lrDao.setAddlLandRevenue(BigDecimal.ZERO);
					lrDao.setAddlEducationalCess(BigDecimal.ZERO);
					lrDao.setEmployeeGuaranteeScheme(BigDecimal.ZERO);
					lrDao.setEducationalCess(BigDecimal.ZERO);

					lrDao.setPreYearPendingJm(oneTimeEntry.getPreYearPendingJm());
					lrDao.setPreYearPendingZp(oneTimeEntry.getPreYearPendingZp());
					lrDao.setPreYearPendingGp(oneTimeEntry.getPreYearPendingGp());
					lrDao.setPreYearPendingEducationalCess(oneTimeEntry.getPreYearPendingEducationalCess());
					lrDao.setPreYearPendingAddlEducationalCess(oneTimeEntry.getPreYearPendingAddlEducationalCess());
					lrDao.setPreYearPendingEmployeeGuaranteeScheme(
							oneTimeEntry.getPreYearPendingEmployeeGuaranteeScheme());
					lrDao.setPreYearPendingAddlLandRevenue(oneTimeEntry.getPreYearPendingAddlLandRevenue());
					// lrDao.setMiscellaneousAmount(oneTimeEntry.getMiscellaneousAmount());
					lrDao.setPreYearNoticeFee(oneTimeEntry.getPreYearNoticeFee());
					lrDao.setPreYearPendingNaCess(oneTimeEntry.getPreYearPendingNaCess());
					lrDao.setPreYearSankirnJmWith(oneTimeEntry.getPreYearSankirnJmWith());
					lrDao.setPreYearSankirnJmWithout(oneTimeEntry.getPreYearSankirnJmWithout());

					lrDao.setZpSankirn(BigDecimal.ZERO);
					lrDao.setJmSankirn(BigDecimal.ZERO);
					lrDao.setGpSankirn(BigDecimal.ZERO);

					BigDecimal netAmount = lrDao.getPreYearPendingJm().add(lrDao.getPreYearPendingZp())
							.add(lrDao.getPreYearPendingGp()).add(lrDao.getPreYearPendingEducationalCess())
							.add(lrDao.getPreYearPendingAddlEducationalCess())
							.add(lrDao.getPreYearPendingEmployeeGuaranteeScheme())
							.add(lrDao.getPreYearPendingAddlLandRevenue().add(lrDao.getPreYearNoticeFee())
									.add(lrDao.getPreYearPendingNaCess()).add(lrDao.getPreYearSankirnJmWith())
									.add(lrDao.getPreYearSankirnJmWithout()));
					// add(lrDao.getMiscellaneousAmount());

					lrDao.setNetAmount((netAmount).setScale(0, BigDecimal.ROUND_HALF_UP));
					lrDao.setNetPending((netAmount).setScale(0, BigDecimal.ROUND_HALF_UP));
				}
				return lrDao;
			}).collect(Collectors.toList());
		}
		return lstLROneTimeEntry;
	}

	private List<LandRevenueDemandDao> getPreYearCarryForwordPendingDetails(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request) {
		List<LandRevenueDemandDao> preYearPending = null;
		List<Object[]> lstpreYearPending = landRevenueDemandRepository.findPreYearPendingDetails(
				request.getHeader("echHost"), request.getHeader("echDbName"), request.getHeader("echSchemaName"),
				CommonConstants.echUser, CommonConstants.echPassword, landRevenueDemandDao.getCCode(),
				CommonConstants.CarryForwordPendingPreYearRevenueYear, 'Y');
		if (lstpreYearPending != null) {
			preYearPending = lstpreYearPending.stream().map(r -> {
				LandRevenueDemandDao lrDao = new LandRevenueDemandDao();
				if ((r[12].toString() != null) && (r[0].toString() != null)) {

					lrDao.setDistrictCode(landRevenueDemandDao.getDistrictCode());
					lrDao.setTalukaCode(landRevenueDemandDao.getTalukaCode());

					lrDao.setCCode(r[12].toString());
					lrDao.setKhataNo(r[0].toString());

					lrDao.setArea(BigDecimal.ZERO);
					lrDao.setAssessment(BigDecimal.ZERO);

					lrDao.setSurveyHissaNo("");
					lrDao.setUom("");

					lrDao.setJmBindumala(BigDecimal.ZERO);
					lrDao.setZpBindumala(BigDecimal.ZERO);
					lrDao.setGpBindumala(BigDecimal.ZERO);

					lrDao.setJmDumala(BigDecimal.ZERO);
					lrDao.setZpDumala(BigDecimal.ZERO);
					lrDao.setGpDumala(BigDecimal.ZERO);

					lrDao.setJmAkrushik(BigDecimal.ZERO);
					lrDao.setZpAkrushik(BigDecimal.ZERO);
					lrDao.setGpAkrushik(BigDecimal.ZERO);

					lrDao.setJmVajasut(BigDecimal.ZERO);
					lrDao.setZpVajasut(BigDecimal.ZERO);
					lrDao.setGpVajasut(BigDecimal.ZERO);

					lrDao.setAddlLandRevenue(BigDecimal.ZERO);
					lrDao.setAddlEducationalCess(BigDecimal.ZERO);
					lrDao.setEmployeeGuaranteeScheme(BigDecimal.ZERO);
					lrDao.setEducationalCess(BigDecimal.ZERO);

					lrDao.setPreYearPendingJm(
							r[1] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(r[1].toString())));
					lrDao.setPreYearPendingZp(
							r[2] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(r[2].toString())));
					lrDao.setPreYearPendingGp(
							r[3] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(r[3].toString())));
					lrDao.setPreYearPendingEducationalCess(
							r[5] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(r[5].toString())));
					lrDao.setPreYearPendingAddlEducationalCess(
							r[6] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(r[6].toString())));
					;
					lrDao.setPreYearPendingEmployeeGuaranteeScheme(
							r[7] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(r[7].toString())));
					lrDao.setPreYearPendingAddlLandRevenue(
							r[4] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(r[4].toString())));
					// lrDao.setMiscellaneousAmount(oneTimeEntry.getMiscellaneousAmount());
					lrDao.setPreYearNoticeFee(
							r[11] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(r[11].toString())));
					lrDao.setPreYearPendingNaCess(
							r[8] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(r[8].toString())));
					lrDao.setPreYearSankirnJmWith(
							r[9] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(r[9].toString())));
					lrDao.setPreYearSankirnJmWithout(
							r[10] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(r[10].toString())));

					lrDao.setZpSankirn(BigDecimal.ZERO);
					lrDao.setJmSankirn(BigDecimal.ZERO);
					lrDao.setGpSankirn(BigDecimal.ZERO);

					BigDecimal netAmount = lrDao.getPreYearPendingJm().add(lrDao.getPreYearPendingZp())
							.add(lrDao.getPreYearPendingGp()).add(lrDao.getPreYearPendingEducationalCess())
							.add(lrDao.getPreYearPendingAddlEducationalCess())
							.add(lrDao.getPreYearPendingEmployeeGuaranteeScheme())
							.add(lrDao.getPreYearPendingAddlLandRevenue().add(lrDao.getPreYearNoticeFee())
									.add(lrDao.getPreYearPendingNaCess()).add(lrDao.getPreYearSankirnJmWith())
									.add(lrDao.getPreYearSankirnJmWithout()));
					// add(lrDao.getMiscellaneousAmount());

					lrDao.setNetAmount((netAmount).setScale(0, BigDecimal.ROUND_HALF_UP));
					lrDao.setNetPending((netAmount).setScale(0, BigDecimal.ROUND_HALF_UP));
				}
				return lrDao;
			}).collect(Collectors.toList());
		}
		return preYearPending;
	}

	private List<LandRevenueDemandDao> getSankirnDetails(String revenueYear, String cCode, HttpServletRequest request) {

		List<LandRevenueDemandDao> lstLRSankirn = null;
		List<Form17> lstLRForSankirn = form17Repository.findAllForm17(request.getHeader("echHost"),
				request.getHeader("echDbName"), request.getHeader("echSchemaName"), CommonConstants.echUser,
				CommonConstants.echPassword, revenueYear, cCode, 'Y');
		DatabaseContextHolder.restoreCtx();

		if (lstLRForSankirn != null) {

			lstLRSankirn = lstLRForSankirn.stream().map(form17 -> {

				LandRevenueDemandDao lrDao = new LandRevenueDemandDao();
				if (form17.getcCode() != null && form17.getKhataNo() != null) {

					lrDao.setRevenueYear(form17.getRevenueYear());

					lrDao.setDistrictCode(form17.getDistrictCode());
					lrDao.setTalukaCode(form17.getTalukaCode());

					lrDao.setCCode(form17.getcCode());

					lrDao.setArea(form17.getAreaAffected());
					lrDao.setAssessment(BigDecimal.ZERO);

					lrDao.setKhataNo(form17.getKhataNo());

					lrDao.setSurveyHissaNo("");
					lrDao.setUom("");

					lrDao.setJmBindumala(BigDecimal.ZERO);
					lrDao.setZpBindumala(BigDecimal.ZERO);
					lrDao.setGpBindumala(BigDecimal.ZERO);

					lrDao.setJmDumala(BigDecimal.ZERO);
					lrDao.setZpDumala(BigDecimal.ZERO);
					lrDao.setGpDumala(BigDecimal.ZERO);

					lrDao.setJmAkrushik(BigDecimal.ZERO);
					lrDao.setZpAkrushik(BigDecimal.ZERO);
					lrDao.setGpAkrushik(BigDecimal.ZERO);

					lrDao.setJmVajasut(BigDecimal.ZERO);
					lrDao.setZpVajasut(BigDecimal.ZERO);
					lrDao.setGpVajasut(BigDecimal.ZERO);

					lrDao.setAddlLandRevenue(BigDecimal.ZERO);
					lrDao.setAddlEducationalCess(BigDecimal.ZERO);
					lrDao.setEmployeeGuaranteeScheme(BigDecimal.ZERO);
					lrDao.setEducationalCess(BigDecimal.ZERO);

					lrDao.setJmSankirn((form17.getAmountOfJm() == null ? BigDecimal.ZERO : form17.getAmountOfJm())
							.setScale(0, BigDecimal.ROUND_HALF_UP));
					lrDao.setZpSankirn((form17.getAmountOfZp() == null ? BigDecimal.ZERO : form17.getAmountOfZp())
							.setScale(0, BigDecimal.ROUND_HALF_UP));
					lrDao.setGpSankirn((form17.getAmountOfGp() == null ? BigDecimal.ZERO : form17.getAmountOfGp())
							.setScale(0, BigDecimal.ROUND_HALF_UP));

					lrDao.setPreYearPendingJm(BigDecimal.ZERO);
					lrDao.setPreYearPendingZp(BigDecimal.ZERO);
					lrDao.setPreYearPendingGp(BigDecimal.ZERO);
					lrDao.setPreYearPendingAddlLandRevenue(BigDecimal.ZERO);
					lrDao.setPreYearPendingEducationalCess(BigDecimal.ZERO);
					lrDao.setPreYearPendingAddlEducationalCess(BigDecimal.ZERO);
					lrDao.setPreYearPendingEmployeeGuaranteeScheme(BigDecimal.ZERO);
					// lrDao.setMiscellaneousAmount(BigDecimal.ZERO);
					lrDao.setPreYearNoticeFee(BigDecimal.ZERO);
					lrDao.setPreYearPendingNaCess(BigDecimal.ZERO);
					lrDao.setPreYearSankirnJmWith(BigDecimal.ZERO);
					lrDao.setPreYearSankirnJmWithout(BigDecimal.ZERO);

					lrDao.setNetAmount((lrDao.getJmSankirn() == null ? BigDecimal.ZERO
							: lrDao.getJmSankirn()
									.add(lrDao.getZpSankirn() == null ? BigDecimal.ZERO : lrDao.getZpSankirn())
									.add(lrDao.getGpSankirn() == null ? BigDecimal.ZERO : lrDao.getGpSankirn()))
											.setScale(0, BigDecimal.ROUND_HALF_UP));
					lrDao.setNetPending((lrDao.getJmSankirn() == null ? BigDecimal.ZERO
							: lrDao.getJmSankirn()
									.add(lrDao.getZpSankirn() == null ? BigDecimal.ZERO : lrDao.getZpSankirn())
									.add(lrDao.getGpSankirn() == null ? BigDecimal.ZERO : lrDao.getGpSankirn()))
											.setScale(0, BigDecimal.ROUND_HALF_UP));
				}
				return lrDao;
			}).collect(Collectors.toList());
		} // SANKIRN
		return lstLRSankirn;
	}

	@Override
	public ChallanDtlDao persistLandRevenueChallan(ChallanDtlDao challanDtlDao, HttpServletRequest request,
			Locale locale) {
		if (challanDtlDao != null && challanDtlDao.getLstReceipt() != null && challanDtlDao.getLstReceipt().size() > 0
				&& challanDtlDao.getCCode() != null) {
			dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
			ChallanDtl challanDtl = new ChallanDtl();
			int uniqueDd = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
			challanDtl.setRevenueYear(challanDtlDao.getRevenueYear());
			// challanDtl.setDistrictCode(challanDtlDao.getDistrictCode());
			// challanDtl.setTalukaCode(challanDtlDao.getTalukaCode());
			challanDtl.setCCode(challanDtlDao.getCCode());
			challanDtl.setChallanDate(LocalDateTime.now());
			challanDtl.setStatus("Not Deposited");
			challanDtl.setActiveFlag('Y');
			challanDtl.setModeOfPayment(challanDtlDao.getModeOfPayment());
			challanDtl.setTotalAmount(challanDtlDao.getTotalAmount());
			challanDtl.setTotalOfHead0029(challanDtlDao.getTotalOfHead0029());
			challanDtl.setTotalOfHead0045(challanDtlDao.getTotalOfHead0045());

			Object Object = new Object();
			Object = lgdMapperRepository.getCurrentLgdCodeForReciept(challanDtlDao.getCCode(),
					challanDtlDao.getTalukaCode());

			if (challanDtlDao.getTotalOfHead0029() > 0) {
				String challanNo = "CL/" + Object + "/" + challanDtlDao.getRevenueYear() + "/" + uniqueDd + "/0029";
				challanDtl.setChallanNo(challanNo);
			}

			if (challanDtlDao.getTotalOfHead0045() > 0) {
				String challanNo1 = "CL/" + Object + "/" + challanDtlDao.getRevenueYear() + "/" + uniqueDd + "/0045";
				challanDtl.setChallanNo0045(challanNo1);
			}
			challanRepository.save(challanDtl);
			DatabaseContextHolder.restoreCtx();

			List<LandRevenueDemand> lrDemand = new ArrayList<>();
			for (LandRevenueDemandDao landRevenueDemandDao : challanDtlDao.getLstReceipt()) {
				Optional<LandRevenueDemand> landRevenueDemand = landRevenueDemandRepository
						.findById(landRevenueDemandDao.getId());
				if (landRevenueDemand.isPresent()) {
					LandRevenueDemand lrc = landRevenueDemand.get();
					lrc.setChallanId(challanDtl.getId());
					landRevenueDemandRepository.save(lrc);
					lrDemand.add(lrc);

					if (landRevenueDemand.get().getKhataNo() != null) {
						List<ReceiptDtlLog> dtlLogList = dtlLogRepository
								.findByKhataNo(landRevenueDemand.get().getKhataNo());
						if (dtlLogList != null && !dtlLogList.isEmpty()) {
							for (ReceiptDtlLog dtlLog : dtlLogList) {
								dtlLog.setChallanId(challanDtl.getId());
								dtlLog.setChallanDate(challanDtl.getChallanDate());
								dtlLogRepository.save(dtlLog);
							}
						}
					}

					if (landRevenueDemand.get().getMakhtaKhataNo() != null) {
						List<ReceiptDtlLog> dtlLogList = dtlLogRepository
								.findByMakhtaKhataNo(landRevenueDemand.get().getMakhtaKhataNo());
						if (dtlLogList != null && !dtlLogList.isEmpty()) {
							for (ReceiptDtlLog dtlLog : dtlLogList) {
								dtlLog.setChallanId(challanDtl.getId());
								dtlLog.setChallanDate(challanDtl.getChallanDate());
								dtlLogRepository.save(dtlLog);
							}
						}
					}
				}
			}
			challanDtl.setLstReceiptDtl(lrDemand);

			return toChallanDao(challanDtl);
		}
		throw new ResourceNotFoundException("error.form.invalid");// invalid Input Please try again...
	}

	@Override
	public MessageResponse persistLRDemand(LandRevenueDemandListDao landRevenueDemandListDao,
			HttpServletRequest request, Locale locale) {
		if (landRevenueDemandListDao != null) {
			dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
			List<LandRevenueDemand> lrDemandEntity = new ArrayList<LandRevenueDemand>();
			List<LandRevenueDemandDao> lrDemandDaoLst = landRevenueDemandListDao.getLstLandRevenueDemandDao();
			if (lrDemandDaoLst != null && lrDemandDaoLst.size() > 0) {
				for (LandRevenueDemandDao lrDemandDao : lrDemandDaoLst) {
					LandRevenueDemand landRevenueDemand = new LandRevenueDemand();
					BeanUtils.copyProperties(lrDemandDao, landRevenueDemand, "id");
					landRevenueDemand.setSurveyHissaNo(landRevenueDemand.getSurveyHissaNo().replaceAll("//", ""));
					if (landRevenueDemand.getSurveyHissaNo().endsWith("//")) {
						StringBuilder b = new StringBuilder(landRevenueDemand.getSurveyHissaNo());
						b.replace(landRevenueDemand.getSurveyHissaNo().lastIndexOf("//"),
								landRevenueDemand.getSurveyHissaNo().lastIndexOf("//") + 1, "");
						landRevenueDemand.setSurveyHissaNo(b.toString());
					}
					landRevenueDemand.setDistrictCode(landRevenueDemandListDao.getDistrictCode());
					landRevenueDemand.setTalukaCode(landRevenueDemandListDao.getTalukaCode());
					landRevenueDemand.setcCode(landRevenueDemandListDao.getCCode());
					landRevenueDemand.setRevenueYear(landRevenueDemandListDao.getRevenueYear());
					landRevenueDemand.setActiveFlag(landRevenueDemandListDao.getActiveFlag());
					lrDemandEntity.add(landRevenueDemand);
				}
				landRevenueDemandRepository.saveAll(lrDemandEntity);
				DatabaseContextHolder.restoreCtx();
				return MessageResponse.builder().status("SUCCESS").message("error.form.savedData").build();
			}
		}
		DatabaseContextHolder.restoreCtx();
		throw new ExchangeException("error.form.errorwhilesave");
	}

	@Override
	public List<LandRevenueDemandDao> getLRDemandBycCodeAndStatus(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {
		
		
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		
		/*List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(landRevenueDemandDao.getCCode())) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}*/
		
		String ccodes = "270400070038080000";

		if (ccodes.equals(landRevenueDemandDao.getCCode())) {
			if (landRevenueDemandDao != null && landRevenueDemandDao.getCCode() != null) {
				List<LandRevenueDemand> landRevenueDemand = new ArrayList<>();
				List<Character> activeFlags = new ArrayList<Character>();
				activeFlags.add('N');
				activeFlags.add('E');
				if (landRevenueDemandDao.getKhataNo() == "" || landRevenueDemandDao.getKhataNo() == null
						|| landRevenueDemandDao.getKhataNo().equals("")
						|| landRevenueDemandDao.getKhataNo().equals(" ")) {
					landRevenueDemand = landRevenueDemandRepository.findBycCodeAndTalukaCodeAndRevenueYearAndActiveFlag(
							landRevenueDemandDao.getCCode(), landRevenueDemandDao.getTalukaCode(),
							landRevenueDemandDao.getRevenueYear(), 'M');
				} else {
					landRevenueDemand = landRevenueDemandRepository
							.findBycCodeAndRevenueYearAndKhataNoAndModeOfPaymentIsNullAndActiveFlagNotIn(
									landRevenueDemandDao.getCCode(), landRevenueDemandDao.getRevenueYear(),
									landRevenueDemandDao.getKhataNo(), activeFlags);
				}
				DatabaseContextHolder.restoreCtx();
				if (!landRevenueDemand.isEmpty()) {
					return landRevenueDemand.stream().map(lrdR -> toDao(lrdR)).collect(Collectors.toList());
				}
				throw new ResourceNotFoundException("Not Found");
			}
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
		throw new ResourceNotFoundException("Not Found");
	}

	@Override
	public void deleteLRDemandById(Long id, HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		LandRevenueDemand lrd = landRevenueDemandRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale,
						"LandRevenueDemand", id));
		lrd.setActiveFlag('N');
		landRevenueDemandRepository.save(lrd);
		DatabaseContextHolder.restoreCtx();
	}

	@Override
	public MessageResponse editLRDemandById(LandRevenueDemandDao landRevenueDemandDao, HttpServletRequest request,
			Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		
		
		
		/*LandRevenueDemand landRevenueDemand = landRevenueDemandRepository.findById(landRevenueDemandDao.getId())
				.orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale,
						"revenueYear", landRevenueDemandDao.getId()));*/
		
		
		
		LandRevenueDemand landRevenueDemand=landRevenueDemandRepository.findActiveRecord(

				request.getHeader("echHost"),
		        request.getHeader("echDbName"),
		        request.getHeader("echSchemaName"),
		        CommonConstants.echUser,
		        CommonConstants.echPassword,
		        landRevenueDemandDao.getCCode(),
		        landRevenueDemandDao.getKhataNo(),
		        landRevenueDemandDao.getRevenueYear()		       

		);
		
		
		
		
		LandRevenueDemand landRevenueDemand2 = new LandRevenueDemand();
		if (landRevenueDemandDao.getCCode() != null) {
			BeanUtils.copyProperties(landRevenueDemandDao, landRevenueDemand2, "id", "acitveFlag", "version");

			landRevenueDemand.setActiveFlag('E');

			landRevenueDemandRepository.save(landRevenueDemand);

			landRevenueDemand2.setJmBindumala(landRevenueDemand2.getJmBindumala() == null ? BigDecimal.ZERO
					: landRevenueDemand2.getJmBindumala());
			landRevenueDemand2.setZpBindumala(landRevenueDemand2.getZpBindumala() == null ? BigDecimal.ZERO
					: landRevenueDemand2.getZpBindumala());
			landRevenueDemand2.setGpBindumala(landRevenueDemand2.getGpBindumala() == null ? BigDecimal.ZERO
					: landRevenueDemand2.getGpBindumala());

			landRevenueDemand2.setJmDumala(
					landRevenueDemand2.getJmDumala() == null ? BigDecimal.ZERO : landRevenueDemand2.getJmDumala());
			landRevenueDemand2.setZpDumala(
					landRevenueDemand2.getZpDumala() == null ? BigDecimal.ZERO : landRevenueDemand2.getZpDumala());
			landRevenueDemand2.setGpDumala(
					landRevenueDemand2.getGpDumala() == null ? BigDecimal.ZERO : landRevenueDemand2.getGpDumala());

			landRevenueDemand2.setJmAkrushik(
					landRevenueDemand2.getJmAkrushik() == null ? BigDecimal.ZERO : landRevenueDemand2.getJmAkrushik());
			landRevenueDemand2.setZpAkrushik(
					landRevenueDemand2.getZpAkrushik() == null ? BigDecimal.ZERO : landRevenueDemand2.getZpAkrushik());
			landRevenueDemand2.setGpAkrushik(
					landRevenueDemand2.getGpAkrushik() == null ? BigDecimal.ZERO : landRevenueDemand2.getGpAkrushik());

			landRevenueDemand2.setJmVajasut(
					landRevenueDemand2.getJmVajasut() == null ? BigDecimal.ZERO : landRevenueDemand2.getJmVajasut());
			landRevenueDemand2.setZpVajasut(
					landRevenueDemand2.getZpVajasut() == null ? BigDecimal.ZERO : landRevenueDemand2.getZpVajasut());
			landRevenueDemand2.setGpVajasut(
					landRevenueDemand2.getGpVajasut() == null ? BigDecimal.ZERO : landRevenueDemand2.getGpVajasut());

			landRevenueDemand2.setAddlLandRevenue(landRevenueDemand2.getAddlLandRevenue() == null ? BigDecimal.ZERO
					: landRevenueDemand2.getAddlLandRevenue());
			landRevenueDemand2
					.setAddlEducationalCess(landRevenueDemand2.getAddlEducationalCess() == null ? BigDecimal.ZERO
							: landRevenueDemand2.getAddlEducationalCess());
			landRevenueDemand2.setEmployeeGuaranteeScheme(
					landRevenueDemand2.getEmployeeGuaranteeScheme() == null ? BigDecimal.ZERO
							: landRevenueDemand2.getEmployeeGuaranteeScheme());
			landRevenueDemand2.setEducationalCess(landRevenueDemand2.getEducationalCess() == null ? BigDecimal.ZERO
					: landRevenueDemand2.getEducationalCess());

			landRevenueDemand2.setJmSankirn(
					landRevenueDemand2.getJmSankirn() == null ? BigDecimal.ZERO : landRevenueDemand2.getJmSankirn());
			landRevenueDemand2.setZpSankirn(
					landRevenueDemand2.getZpSankirn() == null ? BigDecimal.ZERO : landRevenueDemand2.getZpSankirn());
			landRevenueDemand2.setGpSankirn(
					landRevenueDemand2.getGpSankirn() == null ? BigDecimal.ZERO : landRevenueDemand2.getGpSankirn());

			landRevenueDemand2.setAgauVasuliRemarks(
					landRevenueDemand2.getAgauVasuliRemarks() == null ? "" : landRevenueDemand2.getAgauVasuliRemarks());

			
			
			
			double assessment = Double.parseDouble(landRevenueDemand2.getAssessment().toString());

			// newly added 11 Aug 2024
						landRevenueDemand2.setNetAmount(
								landRevenueDemand2.getNetAmount() == null ? BigDecimal.ZERO : landRevenueDemand2.getNetAmount());
						landRevenueDemand2.setNetPending(
								landRevenueDemand2.getNetPending() == null ? BigDecimal.ZERO : landRevenueDemand2.getNetPending());

			/*if (assessment >= 5 && assessment <= 10) {
				landRevenueDemand2.setNetAmount((landRevenueDemand2.getZpBindumala())
						.add(landRevenueDemand2.getGpBindumala()).add(landRevenueDemand2.getZpDumala())
						.add(landRevenueDemand2.getGpDumala()).add(landRevenueDemand2.getJmAkrushik())
						.add(landRevenueDemand2.getZpAkrushik()).add(landRevenueDemand2.getGpAkrushik())
						.add(landRevenueDemand2.getJmSankirn()).add(landRevenueDemand2.getZpSankirn())
						.add(landRevenueDemand2.getGpSankirn()).add(landRevenueDemand2.getAddlLandRevenue())
						.add(landRevenueDemand2.getAddlEducationalCess())
						.add(landRevenueDemand2.getEmployeeGuaranteeScheme())
						.add(landRevenueDemand2.getEducationalCess()).add(landRevenueDemand2.getPreYearPendingJm())
						.add(landRevenueDemand2.getPreYearPendingZp()).add(landRevenueDemand2.getPreYearPendingGp())
						.add(landRevenueDemand2.getPreYearPendingAddlLandRevenue())
						.add(landRevenueDemand2.getPreYearPendingEducationalCess())
						.add(landRevenueDemand2.getPreYearPendingAddlEducationalCess())
						.add(landRevenueDemand2.getPreYearPendingEmployeeGuaranteeScheme())
						.add(landRevenueDemand2.getPreYearPendingNaCess()).add(landRevenueDemand2.getPreYearNoticeFee())
						.add(landRevenueDemand2.getPreYearSankirnJmWith())
						.add(landRevenueDemand2.getPreYearSankirnJmWithout()).setScale(0, BigDecimal.ROUND_HALF_UP));

				landRevenueDemand2.setNetPending((landRevenueDemand2.getZpBindumala())
						.add(landRevenueDemand2.getGpBindumala()).add(landRevenueDemand2.getZpDumala())
						.add(landRevenueDemand2.getGpDumala()).add(landRevenueDemand2.getJmAkrushik())
						.add(landRevenueDemand2.getZpAkrushik()).add(landRevenueDemand2.getGpAkrushik())
						.add(landRevenueDemand2.getJmSankirn()).add(landRevenueDemand2.getZpSankirn())
						.add(landRevenueDemand2.getGpSankirn()).add(landRevenueDemand2.getAddlLandRevenue())
						.add(landRevenueDemand2.getAddlEducationalCess())
						.add(landRevenueDemand2.getEmployeeGuaranteeScheme())
						.add(landRevenueDemand2.getEducationalCess()).add(landRevenueDemand2.getPreYearPendingJm())
						.add(landRevenueDemand2.getPreYearPendingZp()).add(landRevenueDemand2.getPreYearPendingGp())
						.add(landRevenueDemand2.getPreYearPendingAddlLandRevenue())
						.add(landRevenueDemand2.getPreYearPendingEducationalCess())
						.add(landRevenueDemand2.getPreYearPendingAddlEducationalCess())
						.add(landRevenueDemand2.getPreYearPendingEmployeeGuaranteeScheme())
						.add(landRevenueDemand2.getPreYearPendingNaCess()).add(landRevenueDemand2.getPreYearNoticeFee())
						.add(landRevenueDemand2.getPreYearSankirnJmWith())
						.add(landRevenueDemand2.getPreYearSankirnJmWithout()).setScale(0, BigDecimal.ROUND_HALF_UP));
			} else if (assessment > 10) {
				landRevenueDemand2.setNetAmount((landRevenueDemand2.getJmBindumala())
						.add(landRevenueDemand2.getZpBindumala()).add(landRevenueDemand2.getGpBindumala())
						.add(landRevenueDemand2.getJmDumala()).add(landRevenueDemand2.getZpDumala())
						.add(landRevenueDemand2.getGpDumala()).add(landRevenueDemand2.getJmAkrushik())
						.add(landRevenueDemand2.getZpAkrushik()).add(landRevenueDemand2.getGpAkrushik())
						.add(landRevenueDemand2.getJmSankirn()).add(landRevenueDemand2.getZpSankirn())
						.add(landRevenueDemand2.getGpSankirn()).add(landRevenueDemand2.getAddlLandRevenue())
						.add(landRevenueDemand2.getAddlEducationalCess())
						.add(landRevenueDemand2.getEmployeeGuaranteeScheme())
						.add(landRevenueDemand2.getEducationalCess()).add(landRevenueDemand2.getPreYearPendingJm())
						.add(landRevenueDemand2.getPreYearPendingZp()).add(landRevenueDemand2.getPreYearPendingGp())
						.add(landRevenueDemand2.getPreYearPendingAddlLandRevenue())
						.add(landRevenueDemand2.getPreYearPendingEducationalCess())
						.add(landRevenueDemand2.getPreYearPendingAddlEducationalCess())
						.add(landRevenueDemand2.getPreYearPendingEmployeeGuaranteeScheme())
						.add(landRevenueDemand2.getPreYearPendingNaCess()).add(landRevenueDemand2.getPreYearNoticeFee())
						.add(landRevenueDemand2.getPreYearSankirnJmWith())
						.add(landRevenueDemand2.getPreYearSankirnJmWithout()).setScale(0, BigDecimal.ROUND_HALF_UP));

				landRevenueDemand2.setNetPending((landRevenueDemand2.getJmBindumala())
						.add(landRevenueDemand2.getZpBindumala()).add(landRevenueDemand2.getGpBindumala())
						.add(landRevenueDemand2.getJmDumala()).add(landRevenueDemand2.getZpDumala())
						.add(landRevenueDemand2.getGpDumala()).add(landRevenueDemand2.getJmAkrushik())
						.add(landRevenueDemand2.getZpAkrushik()).add(landRevenueDemand2.getGpAkrushik())
						.add(landRevenueDemand2.getJmSankirn()).add(landRevenueDemand2.getZpSankirn())
						.add(landRevenueDemand2.getGpSankirn()).add(landRevenueDemand2.getAddlLandRevenue())
						.add(landRevenueDemand2.getAddlEducationalCess())
						.add(landRevenueDemand2.getEmployeeGuaranteeScheme())
						.add(landRevenueDemand2.getEducationalCess()).add(landRevenueDemand2.getPreYearPendingJm())
						.add(landRevenueDemand2.getPreYearPendingZp()).add(landRevenueDemand2.getPreYearPendingGp())
						.add(landRevenueDemand2.getPreYearPendingAddlLandRevenue())
						.add(landRevenueDemand2.getPreYearPendingEducationalCess())
						.add(landRevenueDemand2.getPreYearPendingAddlEducationalCess())
						.add(landRevenueDemand2.getPreYearPendingEmployeeGuaranteeScheme())
						.add(landRevenueDemand2.getPreYearPendingNaCess()).add(landRevenueDemand2.getPreYearNoticeFee())
						.add(landRevenueDemand2.getPreYearSankirnJmWith())
						.add(landRevenueDemand2.getPreYearSankirnJmWithout()).setScale(0, BigDecimal.ROUND_HALF_UP));
			} else {
				landRevenueDemand2.setNetAmount((landRevenueDemand2.getJmSankirn())
						.add(landRevenueDemand2.getZpSankirn()).add(landRevenueDemand2.getGpSankirn())
						.add(landRevenueDemand2.getJmAkrushik()).add(landRevenueDemand2.getZpAkrushik())
						.add(landRevenueDemand2.getGpAkrushik()).add(landRevenueDemand2.getAddlLandRevenue())
						.add(landRevenueDemand2.getAddlEducationalCess())
						.add(landRevenueDemand2.getEmployeeGuaranteeScheme())
						.add(landRevenueDemand2.getEducationalCess()).add(landRevenueDemand2.getPreYearPendingJm())
						.add(landRevenueDemand2.getPreYearPendingZp()).add(landRevenueDemand2.getPreYearPendingGp())
						.add(landRevenueDemand2.getPreYearPendingAddlLandRevenue())
						.add(landRevenueDemand2.getPreYearPendingEducationalCess())
						.add(landRevenueDemand2.getPreYearPendingAddlEducationalCess())
						.add(landRevenueDemand2.getPreYearPendingEmployeeGuaranteeScheme())
						.add(landRevenueDemand2.getPreYearPendingNaCess()).add(landRevenueDemand2.getPreYearNoticeFee())
						.add(landRevenueDemand2.getPreYearSankirnJmWith())
						.add(landRevenueDemand2.getPreYearSankirnJmWithout()).setScale(0, BigDecimal.ROUND_HALF_UP));

				landRevenueDemand2.setNetPending((landRevenueDemand2.getJmSankirn())
						.add(landRevenueDemand2.getZpSankirn()).add(landRevenueDemand2.getGpSankirn())
						.add(landRevenueDemand2.getJmAkrushik()).add(landRevenueDemand2.getZpAkrushik())
						.add(landRevenueDemand2.getGpAkrushik()).add(landRevenueDemand2.getAddlLandRevenue())
						.add(landRevenueDemand2.getAddlEducationalCess())
						.add(landRevenueDemand2.getEmployeeGuaranteeScheme())
						.add(landRevenueDemand2.getEducationalCess()).add(landRevenueDemand2.getPreYearPendingJm())
						.add(landRevenueDemand2.getPreYearPendingZp()).add(landRevenueDemand2.getPreYearPendingGp())
						.add(landRevenueDemand2.getPreYearPendingAddlLandRevenue())
						.add(landRevenueDemand2.getPreYearPendingEducationalCess())
						.add(landRevenueDemand2.getPreYearPendingAddlEducationalCess())
						.add(landRevenueDemand2.getPreYearPendingEmployeeGuaranteeScheme())
						.add(landRevenueDemand2.getPreYearPendingNaCess()).add(landRevenueDemand2.getPreYearNoticeFee())
						.add(landRevenueDemand2.getPreYearSankirnJmWith())
						.add(landRevenueDemand2.getPreYearSankirnJmWithout()).setScale(0, BigDecimal.ROUND_HALF_UP));
			}*/

			List<Object[]> lrdetails = landRevenueDemandRepository.getLrdetails(request.getHeader("echHost"),
					request.getHeader("echDbName"), request.getHeader("echSchemaName"), CommonConstants.echUser,
					CommonConstants.echPassword, landRevenueDemandDao.getCCode(), landRevenueDemandDao.getKhataNo(),
					landRevenueDemandDao.getRevenueYear());
			List<LandRevenueDemandDao> lst = lrdetails.stream().map(r -> {
				LandRevenueDemandDao lrDao = new LandRevenueDemandDao();
				landRevenueDemand2.setKhataNo(r[0].toString());
				landRevenueDemand2.setKhataNo(r[0].toString());
				landRevenueDemand2.setKhataOwnerName(r[1].toString());
				landRevenueDemand2.setArea(BigDecimal.valueOf(Double.valueOf(r[2].toString())));
				landRevenueDemand2.setAssessment(BigDecimal.valueOf(Double.valueOf(r[3].toString())));
				return lrDao;
			}).collect(Collectors.toList());

			landRevenueDemandRepository.save(landRevenueDemand2);

			DatabaseContextHolder.restoreCtx();
			return MessageResponse.builder().status("Success").message("error.form.savedd").build();
		}
		DatabaseContextHolder.restoreCtx();
		throw new ExchangeException("error.form.errorwhilesave");
	}

	@Override
	public LandRevenueDemandDao persistSankirn(LandRevenueDemandDao landRevenueDemandDao, HttpServletRequest request,
			Locale locale) {
		// LandRevenueDemand landRevenueDemand=new LandRevenueDemand();
		// BeanUtils.copyProperties(landRevenueDemandDao, landRevenueDemand);

		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		List<ReceiptDtlLog> receiptDtlLog = new ArrayList<>();
		LandRevenueDemand landRevenueDemand = null;
//		landRevenueDemand.setRevenueYear(landRevenueDemandDao.getRevenueYear());
//		//		landRevenueDemand.setDistrictCode(landRevenueDemandDao.getDistrictCode());
//		//		landRevenueDemand.setTalukaCode(landRevenueDemandDao.getTalukaCode());
//		landRevenueDemand.setcCode(landRevenueDemandDao.getCCode());
//		landRevenueDemand.setAssessment(landRevenueDemandDao.getAssessment()==null ? BigDecimal.ZERO:landRevenueDemand.getAssessment());
//		landRevenueDemand.setKhataNo(landRevenueDemandDao.getKhataNo());
//		landRevenueDemand.setMakhtaKhataNo(landRevenueDemandDao.getMakhtaKhataNo());
//		landRevenueDemand.setJmSankirn(landRevenueDemandDao.getJmSankirn());
//		landRevenueDemand.setKhataOwnerName(landRevenueDemandDao.getKhataOwnerName());
//		landRevenueDemand.setSurveyHissaNo("");
//		landRevenueDemand.setUom("");
//		
//		//set-area
//
//		landRevenueDemand.setArea(landRevenueDemandDao.getArea());
//
//		landRevenueDemand.setJmBindumala(landRevenueDemandDao.getJmBindumala()==null ? BigDecimal.ZERO:landRevenueDemandDao.getJmBindumala());
//		landRevenueDemand.setZpBindumala(landRevenueDemandDao.getZpBindumala()==null ? BigDecimal.ZERO:landRevenueDemandDao.getZpBindumala());
//		landRevenueDemand.setGpBindumala(landRevenueDemandDao.getGpBindumala()==null ? BigDecimal.ZERO:landRevenueDemandDao.getGpBindumala());
//
//		landRevenueDemand.setJmDumala(landRevenueDemandDao.getJmDumala()==null ? BigDecimal.ZERO:landRevenueDemandDao.getJmDumala());
//		landRevenueDemand.setZpDumala(landRevenueDemandDao.getZpDumala()==null ? BigDecimal.ZERO:landRevenueDemandDao.getZpDumala());
//		landRevenueDemand.setGpDumala(landRevenueDemandDao.getGpDumala()==null ? BigDecimal.ZERO:landRevenueDemandDao.getGpDumala());
//
//		landRevenueDemand.setJmAkrushik(landRevenueDemandDao.getJmAkrushik()==null ? BigDecimal.ZERO:landRevenueDemandDao.getJmAkrushik());
//		landRevenueDemand.setZpAkrushik(landRevenueDemandDao.getZpAkrushik()==null ? BigDecimal.ZERO:landRevenueDemandDao.getZpAkrushik());
//		landRevenueDemand.setGpAkrushik(landRevenueDemandDao.getGpAkrushik()==null ? BigDecimal.ZERO:landRevenueDemandDao.getGpAkrushik());
//
//		landRevenueDemand.setJmVajasut(landRevenueDemandDao.getJmVajasut()==null ? BigDecimal.ZERO:landRevenueDemandDao.getJmVajasut());
//		landRevenueDemand.setZpVajasut(landRevenueDemandDao.getZpVajasut()==null ? BigDecimal.ZERO:landRevenueDemandDao.getZpVajasut());
//		landRevenueDemand.setGpVajasut(landRevenueDemandDao.getGpVajasut()==null ? BigDecimal.ZERO:landRevenueDemandDao.getGpVajasut());
//
//		landRevenueDemand.setAddlLandRevenue(landRevenueDemandDao.getAddlLandRevenue()==null ? BigDecimal.ZERO:landRevenueDemandDao.getAddlLandRevenue());
//		landRevenueDemand.setAddlEducationalCess(landRevenueDemandDao.getAddlEducationalCess()==null ? BigDecimal.ZERO:landRevenueDemandDao.getAddlEducationalCess());
//		landRevenueDemand.setEmployeeGuaranteeScheme(landRevenueDemandDao.getEmployeeGuaranteeScheme()==null ? BigDecimal.ZERO:landRevenueDemandDao.getEmployeeGuaranteeScheme());
//		landRevenueDemand.setEducationalCess(landRevenueDemandDao.getEducationalCess()==null ? BigDecimal.ZERO:landRevenueDemandDao.getEducationalCess());
//
//		landRevenueDemand.setJmSankirn(landRevenueDemandDao.getJmSankirn()==null ? BigDecimal.ZERO:landRevenueDemandDao.getJmSankirn());
//		landRevenueDemand.setZpSankirn(landRevenueDemandDao.getZpSankirn()==null ? BigDecimal.ZERO:landRevenueDemandDao.getZpSankirn());
//		landRevenueDemand.setGpSankirn(landRevenueDemandDao.getGpSankirn()==null ? BigDecimal.ZERO:landRevenueDemandDao.getGpSankirn());
//
//		landRevenueDemand.setPreYearPendingJm(landRevenueDemandDao.getPreYearPendingJm()==null ? BigDecimal.ZERO:landRevenueDemandDao.getPreYearPendingJm());
//		landRevenueDemand.setPreYearPendingZp(landRevenueDemandDao.getPreYearPendingZp()==null ? BigDecimal.ZERO:landRevenueDemandDao.getPreYearPendingZp());
//		landRevenueDemand.setPreYearPendingGp(landRevenueDemandDao.getPreYearPendingGp()==null ? BigDecimal.ZERO:landRevenueDemandDao.getPreYearPendingGp());
//		landRevenueDemand.setPreYearPendingAddlLandRevenue(landRevenueDemandDao.getPreYearPendingAddlLandRevenue()==null ? BigDecimal.ZERO:landRevenueDemandDao.getPreYearPendingAddlLandRevenue());
//		landRevenueDemand.setPreYearPendingEducationalCess(landRevenueDemandDao.getPreYearPendingEducationalCess()==null ? BigDecimal.ZERO:landRevenueDemandDao.getPreYearPendingEducationalCess());
//		landRevenueDemand.setPreYearPendingAddlEducationalCess(landRevenueDemandDao.getPreYearPendingAddlEducationalCess()==null ? BigDecimal.ZERO:landRevenueDemandDao.getPreYearPendingAddlEducationalCess());
//		landRevenueDemand.setPreYearPendingEmployeeGuaranteeScheme(landRevenueDemandDao.getPreYearPendingEmployeeGuaranteeScheme()==null ? BigDecimal.ZERO:landRevenueDemandDao.getPreYearPendingEmployeeGuaranteeScheme());
//		//		landRevenueDemand.setMiscellaneousAmount(landRevenueDemandDao.getMiscellaneousAmount()==null ? BigDecimal.ZERO:landRevenueDemandDao.getMiscellaneousAmount());
//		landRevenueDemand.setPreYearNoticeFee(landRevenueDemandDao.getPreYearNoticeFee()==null ? BigDecimal.ZERO:landRevenueDemandDao.getPreYearNoticeFee());
//		landRevenueDemand.setPreYearPendingNaCess(landRevenueDemandDao.getPreYearPendingNaCess()==null ? BigDecimal.ZERO:landRevenueDemandDao.getPreYearPendingNaCess());
//		landRevenueDemand.setPreYearSankirnJmWith(landRevenueDemandDao.getPreYearSankirnJmWith()==null ? BigDecimal.ZERO:landRevenueDemandDao.getPreYearSankirnJmWith());
//		landRevenueDemand.setPreYearSankirnJmWithout(landRevenueDemandDao.getPreYearSankirnJmWithout()==null ? BigDecimal.ZERO:landRevenueDemandDao.getPreYearSankirnJmWithout());
//
//		//		landRevenueDemand.setNetAmount(form17Dao.getAmountOfJm());
//		landRevenueDemand.setNetAmount(landRevenueDemandDao.getNetAmount().setScale(0,BigDecimal.ROUND_HALF_UP));
//		landRevenueDemand.setNetPending(landRevenueDemandDao.getNetAmount().setScale(0,BigDecimal.ROUND_HALF_UP));
//
//		//		landRevenueDemand.setNetAmount(landRevenueDemandDao.getJmSankirn()==null ? BigDecimal.ZERO:landRevenueDemandDao.getJmSankirn().add(landRevenueDemandDao.getZpSankirn()==null ? BigDecimal.ZERO:landRevenueDemandDao.getZpSankirn()).add(landRevenueDemandDao.getGpSankirn()==null ? BigDecimal.ZERO:landRevenueDemandDao.getGpSankirn()));
//		//		landRevenueDemand.setNetPending(landRevenueDemandDao.getJmSankirn()==null ? BigDecimal.ZERO:landRevenueDemandDao.getJmSankirn().add(landRevenueDemandDao.getZpSankirn()==null ? BigDecimal.ZERO:landRevenueDemandDao.getZpSankirn()).add(landRevenueDemandDao.getGpSankirn()==null ? BigDecimal.ZERO:landRevenueDemandDao.getGpSankirn()));
//
//		landRevenueDemand.setIsMoneyepositedBank('N');
//		if(landRevenueDemandDao.getModeOfPayment()!=null && landRevenueDemandDao.getModeOfPayment().equals("DD")) {
//			landRevenueDemand.setDdName(landRevenueDemandDao.getDdName());
//			landRevenueDemand.setDdBranch(landRevenueDemandDao.getDdBranch());
//			landRevenueDemand.setDdDate(landRevenueDemandDao.getDdDate());
//			landRevenueDemand.setDdNumber(landRevenueDemandDao.getDdNumber());
//			landRevenueDemand.setDdAmount(landRevenueDemandDao.getDdAmount());
//			landRevenueDemand.setModeOfPayment("DD");
//		}else if(landRevenueDemandDao.getModeOfPayment().equals("Cheque")) {
//			landRevenueDemand.setChequeName(landRevenueDemandDao.getChequeName());
//			landRevenueDemand.setChequeBranch(landRevenueDemandDao.getChequeBranch());
//			landRevenueDemand.setChequeAmount(landRevenueDemandDao.getChequeAmount());
//			landRevenueDemand.setChequeDate(landRevenueDemandDao.getChequeDate());
//			landRevenueDemand.setChequeNumber(landRevenueDemandDao.getChequeNumber());
//			landRevenueDemand.setModeOfPayment("Cheque");
//		}
//		else {
//			landRevenueDemand.setModeOfPayment("Cash");
//		}
//
//		int uniqueDd= (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE); 
//
//		Object ObjectlgdCode = new Object();		
//		
//		ObjectlgdCode  = lgdMapperRepository.getCurrentLgdCodeForReciept(  landRevenueDemandDao.getCCode() , landRevenueDemand.getTalukaCode());
// 
//		
//		if(landRevenueDemand.getKhataNo()!= null) {
//			landRevenueDemand.setReceiptNo("RC/"+ObjectlgdCode+"/"+landRevenueDemandDao.getRevenueYear()+"/"+landRevenueDemandDao.getKhataNo() +"/"+uniqueDd);
//
//		}
//		else {
////			landRevenueDemand.setKhataNo("");
//			String [] khata=landRevenueDemandDao.getMakhtaKhataNo().split("/");
//			landRevenueDemand.setReceiptNo("RC/"+ObjectlgdCode+"/"+landRevenueDemandDao.getRevenueYear()+"/"+"M-"+ khata[2] +"/"+uniqueDd);	
//		}
//		landRevenueDemand.setReceiptDate(LocalDateTime.now());
//
//		Form17 form17=form17Repository.getById(landRevenueDemandDao.getLandReceiveForm17Id());
//		form17.setDemandGenerated('Y');
//		form17.setIsAmountPaid('Y');
//

//		landRevenueDemandRepository.save(landRevenueDemand);
//		form17Repository.save(form17);

		if (landRevenueDemandDao.getMakhtaKhataNo() != null) {
			landRevenueDemand = landRevenueDemandRepository.findBycCodeAndMakhtaKhataNoAndRevenueYear(
					landRevenueDemandDao.getCCode(), landRevenueDemandDao.getMakhtaKhataNo(),
					landRevenueDemandDao.getRevenueYear());
			landRevenueDemand.setMakhtaKhataNo(landRevenueDemandDao.getMakhtaKhataNo());
		} else if (landRevenueDemandDao.getKhataNo() != null) {
			landRevenueDemand = landRevenueDemandRepository.findBycCodeAndKhataNoAndRevenueYear(
					landRevenueDemandDao.getCCode(), landRevenueDemandDao.getKhataNo(),
					landRevenueDemandDao.getRevenueYear());
			landRevenueDemand.setKhataNo(landRevenueDemandDao.getKhataNo());
		}

		if (landRevenueDemand != null) {
			setCommonLandRevenueDemandFields(landRevenueDemand, landRevenueDemandDao);

			String receiptNo = generateReceiptNo(landRevenueDemandDao, landRevenueDemand);
			landRevenueDemand.setReceiptNo(receiptNo);
			landRevenueDemand.setReceiptDate(LocalDateTime.now());

			Form17 form17 = form17Repository.getById(landRevenueDemandDao.getLandReceiveForm17Id());
			form17.setDemandGenerated('Y');
			form17.setIsAmountPaid('Y');

			landRevenueDemandRepository.save(landRevenueDemand);
			form17Repository.save(form17);

			ReceiptDtlLog receiptDtl = new ReceiptDtlLog();
			BeanUtils.copyProperties(landRevenueDemand, receiptDtl);
			receiptDtl.setReceiptByUserid(request.getHeader("servarthId"));
			receiptDtlLog.add(receiptDtl);
			dtlLogRepository.saveAll(receiptDtlLog);
		}

		DatabaseContextHolder.restoreCtx();
		return toDao(landRevenueDemand);
	}

	private void setCommonLandRevenueDemandFields(LandRevenueDemand landRevenueDemand,
			LandRevenueDemandDao landRevenueDemandDao) {
		landRevenueDemand.setRevenueYear(landRevenueDemandDao.getRevenueYear());
		landRevenueDemand.setcCode(landRevenueDemandDao.getCCode());
		landRevenueDemand.setAssessment(
				landRevenueDemandDao.getAssessment() == null ? BigDecimal.ZERO : landRevenueDemandDao.getAssessment());
		landRevenueDemand.setJmSankirn(landRevenueDemandDao.getJmSankirn());
		landRevenueDemand.setKhataOwnerName(landRevenueDemandDao.getKhataOwnerName());
		landRevenueDemand.setSurveyHissaNo("");
		landRevenueDemand.setUom("");

		landRevenueDemand
				.setArea(landRevenueDemandDao.getArea() == null ? BigDecimal.ZERO : landRevenueDemandDao.getArea());
		landRevenueDemand.setJmBindumala(landRevenueDemandDao.getJmBindumala() == null ? BigDecimal.ZERO
				: landRevenueDemandDao.getJmBindumala());
		landRevenueDemand.setZpBindumala(landRevenueDemandDao.getZpBindumala() == null ? BigDecimal.ZERO
				: landRevenueDemandDao.getZpBindumala());
		landRevenueDemand.setGpBindumala(landRevenueDemandDao.getGpBindumala() == null ? BigDecimal.ZERO
				: landRevenueDemandDao.getGpBindumala());

		landRevenueDemand.setJmDumala(
				landRevenueDemandDao.getJmDumala() == null ? BigDecimal.ZERO : landRevenueDemandDao.getJmDumala());
		landRevenueDemand.setZpDumala(
				landRevenueDemandDao.getZpDumala() == null ? BigDecimal.ZERO : landRevenueDemandDao.getZpDumala());
		landRevenueDemand.setGpDumala(
				landRevenueDemandDao.getGpDumala() == null ? BigDecimal.ZERO : landRevenueDemandDao.getGpDumala());

		landRevenueDemand.setJmAkrushik(
				landRevenueDemandDao.getJmAkrushik() == null ? BigDecimal.ZERO : landRevenueDemandDao.getJmAkrushik());
		landRevenueDemand.setZpAkrushik(
				landRevenueDemandDao.getZpAkrushik() == null ? BigDecimal.ZERO : landRevenueDemandDao.getZpAkrushik());
		landRevenueDemand.setGpAkrushik(
				landRevenueDemandDao.getGpAkrushik() == null ? BigDecimal.ZERO : landRevenueDemandDao.getGpAkrushik());

		landRevenueDemand.setJmVajasut(
				landRevenueDemandDao.getJmVajasut() == null ? BigDecimal.ZERO : landRevenueDemandDao.getJmVajasut());
		landRevenueDemand.setZpVajasut(
				landRevenueDemandDao.getZpVajasut() == null ? BigDecimal.ZERO : landRevenueDemandDao.getZpVajasut());
		landRevenueDemand.setGpVajasut(
				landRevenueDemandDao.getGpVajasut() == null ? BigDecimal.ZERO : landRevenueDemandDao.getGpVajasut());

		landRevenueDemand.setAddlLandRevenue(landRevenueDemandDao.getAddlLandRevenue() == null ? BigDecimal.ZERO
				: landRevenueDemandDao.getAddlLandRevenue());
		landRevenueDemand.setAddlEducationalCess(landRevenueDemandDao.getAddlEducationalCess() == null ? BigDecimal.ZERO
				: landRevenueDemandDao.getAddlEducationalCess());
		landRevenueDemand
				.setEmployeeGuaranteeScheme(landRevenueDemandDao.getEmployeeGuaranteeScheme() == null ? BigDecimal.ZERO
						: landRevenueDemandDao.getEmployeeGuaranteeScheme());
		landRevenueDemand.setEducationalCess(landRevenueDemandDao.getEducationalCess() == null ? BigDecimal.ZERO
				: landRevenueDemandDao.getEducationalCess());

		landRevenueDemand.setPreYearPendingJm(landRevenueDemandDao.getPreYearPendingJm() == null ? BigDecimal.ZERO
				: landRevenueDemandDao.getPreYearPendingJm());
		landRevenueDemand.setPreYearPendingZp(landRevenueDemandDao.getPreYearPendingZp() == null ? BigDecimal.ZERO
				: landRevenueDemandDao.getPreYearPendingZp());
		landRevenueDemand.setPreYearPendingGp(landRevenueDemandDao.getPreYearPendingGp() == null ? BigDecimal.ZERO
				: landRevenueDemandDao.getPreYearPendingGp());
		landRevenueDemand.setPreYearPendingAddlLandRevenue(
				landRevenueDemandDao.getPreYearPendingAddlLandRevenue() == null ? BigDecimal.ZERO
						: landRevenueDemandDao.getPreYearPendingAddlLandRevenue());
		landRevenueDemand.setPreYearPendingEducationalCess(
				landRevenueDemandDao.getPreYearPendingEducationalCess() == null ? BigDecimal.ZERO
						: landRevenueDemandDao.getPreYearPendingEducationalCess());
		landRevenueDemand.setPreYearPendingAddlEducationalCess(
				landRevenueDemandDao.getPreYearPendingAddlEducationalCess() == null ? BigDecimal.ZERO
						: landRevenueDemandDao.getPreYearPendingAddlEducationalCess());
		landRevenueDemand.setPreYearPendingEmployeeGuaranteeScheme(
				landRevenueDemandDao.getPreYearPendingEmployeeGuaranteeScheme() == null ? BigDecimal.ZERO
						: landRevenueDemandDao.getPreYearPendingEmployeeGuaranteeScheme());

		landRevenueDemand.setPreYearNoticeFee(landRevenueDemandDao.getPreYearNoticeFee() == null ? BigDecimal.ZERO
				: landRevenueDemandDao.getPreYearNoticeFee());
		landRevenueDemand
				.setPreYearPendingNaCess(landRevenueDemandDao.getPreYearPendingNaCess() == null ? BigDecimal.ZERO
						: landRevenueDemandDao.getPreYearPendingNaCess());
		landRevenueDemand
				.setPreYearSankirnJmWith(landRevenueDemandDao.getPreYearSankirnJmWith() == null ? BigDecimal.ZERO
						: landRevenueDemandDao.getPreYearSankirnJmWith());
		landRevenueDemand
				.setPreYearSankirnJmWithout(landRevenueDemandDao.getPreYearSankirnJmWithout() == null ? BigDecimal.ZERO
						: landRevenueDemandDao.getPreYearSankirnJmWithout());

		landRevenueDemand.setNetAmount(landRevenueDemandDao.getNetAmount().setScale(0, BigDecimal.ROUND_HALF_UP));
		landRevenueDemand.setNetPending(landRevenueDemandDao.getNetAmount().setScale(0, BigDecimal.ROUND_HALF_UP));

		landRevenueDemand.setIsMoneyepositedBank('N');

		if (landRevenueDemandDao.getModeOfPayment() != null && landRevenueDemandDao.getModeOfPayment().equals("DD")) {
			landRevenueDemand.setDdName(landRevenueDemandDao.getDdName());
			landRevenueDemand.setDdBranch(landRevenueDemandDao.getDdBranch());
			landRevenueDemand.setDdDate(landRevenueDemandDao.getDdDate());
			landRevenueDemand.setDdNumber(landRevenueDemandDao.getDdNumber());
			landRevenueDemand.setDdAmount(landRevenueDemandDao.getDdAmount());
			landRevenueDemand.setModeOfPayment("DD");
		} else if (landRevenueDemandDao.getModeOfPayment().equals("Cheque")) {
			landRevenueDemand.setChequeName(landRevenueDemandDao.getChequeName());
			landRevenueDemand.setChequeBranch(landRevenueDemandDao.getChequeBranch());
			landRevenueDemand.setChequeAmount(landRevenueDemandDao.getChequeAmount());
			landRevenueDemand.setChequeDate(landRevenueDemandDao.getChequeDate());
			landRevenueDemand.setChequeNumber(landRevenueDemandDao.getChequeNumber());
			landRevenueDemand.setModeOfPayment("Cheque");
		} else {
			landRevenueDemand.setModeOfPayment("Cash");
		}
	}

	private String generateReceiptNo(LandRevenueDemandDao landRevenueDemandDao, LandRevenueDemand landRevenueDemand) {
		int uniqueDd = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

		Object ObjectlgdCode = new Object();

		ObjectlgdCode = lgdMapperRepository.getCurrentLgdCodeForReciept(landRevenueDemandDao.getCCode(),
				landRevenueDemandDao.getTalukaCode());

		if (landRevenueDemand.getKhataNo() != null) {
			return "RC/" + ObjectlgdCode + "/" + landRevenueDemandDao.getRevenueYear() + "/"
					+ landRevenueDemandDao.getKhataNo() + "/" + uniqueDd;
		} else {
			String[] khata = landRevenueDemandDao.getMakhtaKhataNo().split("/");
			return "RC/" + ObjectlgdCode + "/" + landRevenueDemandDao.getRevenueYear() + "/" + "M-" + khata[2] + "/"
					+ uniqueDd;
		}
	}

	@Override
	public LandRevenueDemandDao getReceiptDetailsByKhataNo(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		List<LandRevenueDemand> listlandRevenueDemand = landRevenueDemandRepository
				.findReceiptDetailsBycCodeAndKhataNoAndRevenueYearAndActiveFlagAndReceiptNoIsNotNull(
						landRevenueDemandDao.getCCode(), landRevenueDemandDao.getKhataNo(),
						landRevenueDemandDao.getRevenueYear(), 'Y');
		DatabaseContextHolder.restoreCtx();

		if (listlandRevenueDemand != null && listlandRevenueDemand.size() > 0) {
			return toDao(listlandRevenueDemand.get(0));
		}
		throw new ResourceNotFoundException("error.form.notfound");
	}

	@Override
	public LandRevenueDemandDao getReceiptDetailsByMakhtaKhataNo(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(landRevenueDemandDao.getCCode())) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(landRevenueDemandDao.getCCode())) {
			List<LandRevenueDemand> listlandRevenueDemand;
			if(landRevenueDemandDao.getRevenueYear().equals(CommonConstants.currentYear))
			{	
				//this will execute for the current year
				  listlandRevenueDemand = landRevenueDemandRepository
					.findReceiptDetailsBycCodeAndMakhtaKhataNoAndRevenueYearAndActiveFlagAndReceiptNoIsNotNull(
							landRevenueDemandDao.getCCode(), landRevenueDemandDao.getMakhtaKhataNo(),
							landRevenueDemandDao.getRevenueYear(), 'Y');
				  
				  DatabaseContextHolder.restoreCtx();
					
					if (listlandRevenueDemand != null && listlandRevenueDemand.size() > 0) {
						return toDao(listlandRevenueDemand.get(0));
					}
			}
			else{
				
				//This else block will excute for all previous year reciept of MakhtaKhata
				
				String rYear =  landRevenueDemandDao.getRevenueYear();
				String preYear = rYear.split("-")[0];
				System.out.println("actualyearfor the tablename : ------------"+preYear); // 2023
				
				
				if(preYear.equals("2024")) {
					 List<LandRevenueDemandPre> listlandRevenueDemandPre = landRevenueDemandRepositoryPre
					            .findReceiptDetailsBycCodeAndMakhtaKhataNoAndRevenueYearAndActiveFlagAndReceiptNoIsNotNull(
					                request.getHeader("echHost"),                      // eHost -> ?1
					                request.getHeader("echDbName"),                    // eDbName -> ?2
					                landRevenueDemandDao.getCCode(),                   // cCode -> ?3
					                CommonConstants.echUser,                      // eUser -> ?4
					                CommonConstants.echPassword,                  // ePassword -> ?5
					                landRevenueDemandDao.getMakhtaKhataNo(),           // makhtaKhataNo -> ?6
					                CommonConstants.DefaultDataPort,                      // port -> ?7
					                landRevenueDemandDao.getRevenueYear(),             // revenueYear -> ?8
					                'Y',                                               // activeFlag -> ?9
					                request.getHeader("echSchemaName")	,		// ?10
					                preYear			// ?11
					                
					            );
					 
					 DatabaseContextHolder.restoreCtx();
						
						if (listlandRevenueDemandPre != null && listlandRevenueDemandPre.size() > 0) {
							return toDaoForMakhtakhata(listlandRevenueDemandPre.get(0));
						}
					
				}else {
					 List<LandRevenueDemand2023> listlandRevenueDemand23 = demandRepo2023
					            .findReceiptDetailsBycCodeAndMakhtaKhataNoAndRevenueYearAndActiveFlagAndReceiptNoIsNotNull(
					                request.getHeader("echHost"),                      // eHost -> ?1
					                request.getHeader("echDbName"),                    // eDbName -> ?2
					                landRevenueDemandDao.getCCode(),                   // cCode -> ?3
					                CommonConstants.echUser,                      // eUser -> ?4
					                CommonConstants.echPassword,                  // ePassword -> ?5
					                landRevenueDemandDao.getMakhtaKhataNo(),           // makhtaKhataNo -> ?6
					                CommonConstants.DefaultDataPort,                      // port -> ?7
					                landRevenueDemandDao.getRevenueYear(),             // revenueYear -> ?8
					                'Y',                                               // activeFlag -> ?9
					                request.getHeader("echSchemaName")	,		// ?10
					                preYear			// ?11
					                
					            );
					 
					 DatabaseContextHolder.restoreCtx();
						
						if (listlandRevenueDemand23 != null && listlandRevenueDemand23.size() > 0) {
							return toDao2023(listlandRevenueDemand23.get(0));
						}
					
					
				}
				
				
				 
				
			}
			
			
			
			throw new ResourceNotFoundException("error.form.notfound");
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

//pre
	/*@Override
	public LandRevenueDemandDao getReceiptDetailsByMakhtaKhataNoPre(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {
		
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(landRevenueDemandDao.getCCode())) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(landRevenueDemandDao.getCCode())) {
			
			List<LandRevenueDemand2023> listlandRevenueDemand = demandRepo2023
					.findReceiptDetailsBycCodeAndMakhtaKhataNoAndRevenueYearAndActiveFlagAndReceiptNoIsNotNull(
							landRevenueDemandDao.getCCode(), landRevenueDemandDao.getMakhtaKhataNo(),
							landRevenueDemandDao.getRevenueYear(), 'Y');
			DatabaseContextHolder.restoreCtx();
			if (listlandRevenueDemand != null && listlandRevenueDemand.size() > 0) {
				return toDao2023(listlandRevenueDemand.get(0));
			}
			throw new ResourceNotFoundException("error.form.notfound");
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}*/

	@Override
	public List<LandRevenueDemandDao> getAllReceiptsByVillage(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(landRevenueDemandDao.getCCode())) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(landRevenueDemandDao.getCCode())) {
			List<LandRevenueDemand> listlandRevenueCollectn = new ArrayList<>();
			if (landRevenueDemandDao.getIsMoneyepositedBank() == 'Y') {
				listlandRevenueCollectn = landRevenueDemandRepository
						.findReceiptDetailsBycCodeAndIsMoneyepositedBankAndRevenueYearAndActiveFlag(
								landRevenueDemandDao.getCCode(), landRevenueDemandDao.getIsMoneyepositedBank(),
								landRevenueDemandDao.getRevenueYear(), 'Y');
			} else {
				listlandRevenueCollectn = landRevenueDemandRepository
						.findReceiptDetailsBycCodeAndIsMoneyepositedBankAndRevenueYearAndModeOfPaymentAndActiveFlagAndChallanIdIsNull(
								landRevenueDemandDao.getCCode(), landRevenueDemandDao.getIsMoneyepositedBank(),
								landRevenueDemandDao.getRevenueYear(), landRevenueDemandDao.getModeOfPayment(), 'Y');

			}

			for (LandRevenueDemand lrd : listlandRevenueCollectn) {

				double assessment = Double.parseDouble(lrd.getAssessment().toString());

				double jmAkrushik = Double.parseDouble(lrd.getJmAkrushik().toString());

//			if(lrd.getKhataNo()!=null && assessment > 10 && jmAkrushik == 0 ) {
//			List<Object[]> lrdetails = landRevenueDemandRepository.getBagayatJirayatDetails(
//					request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//					lrd.getKhataNo(),lrd.getcCode());
//			List<LandRevenueDemandDao> lst = lrdetails.stream().map(r -> {
//				LandRevenueDemandDao lrDao = new LandRevenueDemandDao();
//				lrd.setBagayatArea(r[1] == null ? "0" : r[1].toString());
//				lrd.setJirayatArea(r[2]== null ? "0" : r[2].toString());
//				return lrDao;
//			}).collect(Collectors.toList());
//			
//			if((lrd.getBagayatArea()!=null)&&(lrd.getJirayatArea()!=null)) {
//			double bagayatArea = Double.parseDouble(lrd.getBagayatArea().toString());
//			double jirayatArea = Double.parseDouble(lrd.getJirayatArea().toString());
//				if(bagayatArea==0 && jirayatArea <= 2.99 ) {
//						lrd.setJmBindumala(BigDecimal.ZERO);
//						lrd.setJmDumala(BigDecimal.ZERO);
//					}
//				
//				
//				if((bagayatArea >=0 && jirayatArea >= 3.00 ) || (bagayatArea >0 && jirayatArea <= 2.99 )) {
//					if(assessment >= 5 && assessment <=10) {
//						lrd.setJmBindumala(BigDecimal.ZERO);
//						lrd.setJmDumala(BigDecimal.ZERO);
//					}else if(assessment > 10) {
//						lrd.setJmBindumala(lrd.getJmBindumala());
//						lrd.setJmDumala(lrd.getJmDumala());	
//					}else{
//						lrd.setJmBindumala(BigDecimal.ZERO);
//						lrd.setJmDumala(BigDecimal.ZERO);	
//					}
//					}
//			}}

				lrd.setTotalJM((lrd.getJmBindumala()).add(lrd.getJmDumala()).add(lrd.getJmAkrushik())
						.add(lrd.getJmSankirn()).add(lrd.getPreYearPendingJm()));
				lrd.setTotalGP((lrd.getGpBindumala()).add(lrd.getGpDumala()).add(lrd.getGpAkrushik())
						.add(lrd.getGpSankirn()).add(lrd.getPreYearPendingGp()));
				lrd.setTotalZP((lrd.getZpBindumala()).add(lrd.getZpDumala()).add(lrd.getZpAkrushik())
						.add(lrd.getZpSankirn()).add(lrd.getPreYearPendingZp()));

				lrd.setTotalOf0045((lrd.getAddlEducationalCess()).add(lrd.getEducationalCess())
						.add(lrd.getEmployeeGuaranteeScheme()).add(lrd.getPreYearPendingAddlEducationalCess())
						.add(lrd.getPreYearPendingEducationalCess()).add(lrd.getPreYearPendingEmployeeGuaranteeScheme())
						.setScale(0, BigDecimal.ROUND_HALF_UP));

				/*
				 * lrd.setTotalOf0029((lrd.getTotalJM()).subtract(lrd.getJmVajasut())
				 * .add(lrd.getTotalGP()).subtract(lrd.getGpVajasut())
				 * .add(lrd.getTotalZP()).subtract(lrd.getZpVajasut())
				 * .add(lrd.getPreYearSankirnJmWith()) .add(lrd.getPreYearSankirnJmWithout())
				 * .add(lrd.getAddlLandRevenue()) .add(lrd.getPreYearPendingAddlLandRevenue())
				 * .add(lrd.getPreYearPendingNaCess())
				 * .add(lrd.getPreYearNoticeFee()).setScale(0,BigDecimal.ROUND_HALF_UP));
				 */

				/*
				 * if(assessment > 10) {
				 * lrd.setTotalOf0029((lrd.getTotalJM()).add(lrd.getTotalGP()).add(lrd.
				 * getTotalZP()).add(lrd.getPreYearSankirnJmWith()).add(lrd.
				 * getPreYearSankirnJmWithout())
				 * .add(lrd.getAddlLandRevenue()).add(lrd.getPreYearPendingAddlLandRevenue()).
				 * add(lrd.getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee()).setScale(0,
				 * BigDecimal.ROUND_HALF_UP)); } else if(assessment >= 5 && assessment <= 10 ) {
				 * lrd.setTotalOf0029((lrd.getJmAkrushik()).add(lrd.getJmSankirn()).add(lrd.
				 * getPreYearPendingJm()).add(lrd.getTotalGP()).add(lrd.getTotalZP())
				 * .add(lrd.getPreYearSankirnJmWith()).add(lrd.getPreYearSankirnJmWithout()).add
				 * (lrd.getAddlLandRevenue()).add(lrd.getPreYearPendingAddlLandRevenue()).add(
				 * lrd.getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee()).setScale(0,
				 * BigDecimal.ROUND_HALF_UP)); }else {
				 * lrd.setTotalOf0029((lrd.getAddlLandRevenue()).add(lrd.getJmSankirn()).add(lrd
				 * .getGpSankirn()).add(lrd.getZpSankirn()).add(lrd.
				 * getPreYearPendingAddlLandRevenue())
				 * .add(lrd.getJmAkrushik()).add(lrd.getGpAkrushik()).add(lrd.getZpAkrushik()).
				 * add(lrd.getPreYearSankirnJmWith()).add(lrd.getPreYearSankirnJmWithout()).add(
				 * lrd.getPreYearPendingJm())
				 * .add(lrd.getPreYearPendingGp()).add(lrd.getPreYearPendingZp()).add(lrd.
				 * getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee()).setScale(0,
				 * BigDecimal.ROUND_HALF_UP)); }
				 */

				// new conditions added by tushar 7th oct 2024

				double area = Double.parseDouble(lrd.getArea().toString());

				if (assessment > 10 && area > 3) {

					// old condition 20 Dec 2024 -- tushar
					/*
					 * lrd.setTotalOf0029((lrd.getTotalJM()) .add(lrd.getTotalGP())
					 * .add(lrd.getTotalZP()) .add(lrd.getPreYearSankirnJmWith())
					 * .add(lrd.getPreYearSankirnJmWithout()) .add(lrd.getAddlLandRevenue())
					 * .add(lrd.getPreYearPendingAddlLandRevenue())
					 * .add(lrd.getPreYearPendingNaCess())
					 * .add(lrd.getPreYearNoticeFee()).setScale(0,BigDecimal.ROUND_HALF_UP));
					 */

					// new vajasut conditions 20 Dec 2024 -- Tushar
					lrd.setTotalOf0029((lrd.getTotalJM()).add(lrd.getTotalGP()).add(lrd.getTotalZP())
							.add(lrd.getPreYearSankirnJmWith()).add(lrd.getPreYearSankirnJmWithout())
							.add(lrd.getAddlLandRevenue()).add(lrd.getPreYearPendingAddlLandRevenue())
							.add(lrd.getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee())
							.subtract(lrd.getJmVajasut()).subtract(lrd.getGpVajasut()).subtract(lrd.getZpVajasut())
							.setScale(0, BigDecimal.ROUND_HALF_UP));

				} else if (assessment > 10 && area <= 3) {

					lrd.setTotalOf0029((lrd.getJmAkrushik()).add(lrd.getJmSankirn()).add(lrd.getPreYearPendingJm())
							.add(lrd.getTotalGP()).add(lrd.getTotalZP()).add(lrd.getPreYearSankirnJmWith())
							.add(lrd.getPreYearSankirnJmWithout()).add(lrd.getAddlLandRevenue())
							.add(lrd.getPreYearPendingAddlLandRevenue()).add(lrd.getPreYearPendingNaCess())
							.add(lrd.getPreYearNoticeFee()).subtract(lrd.getGpVajasut()).subtract(lrd.getZpVajasut())
							.setScale(0, BigDecimal.ROUND_HALF_UP));

				} else if (assessment >= 5 && assessment <= 10) {

					lrd.setTotalOf0029((lrd.getJmAkrushik()).add(lrd.getJmSankirn()).add(lrd.getPreYearPendingJm())
							.add(lrd.getTotalGP()).add(lrd.getTotalZP()).add(lrd.getPreYearSankirnJmWith())
							.add(lrd.getPreYearSankirnJmWithout()).add(lrd.getAddlLandRevenue())
							.add(lrd.getPreYearPendingAddlLandRevenue()).add(lrd.getPreYearPendingNaCess())
							.add(lrd.getPreYearNoticeFee()).subtract(lrd.getGpVajasut()).subtract(lrd.getZpVajasut())
							.setScale(0, BigDecimal.ROUND_HALF_UP));

				} else {
					lrd.setTotalOf0029((lrd.getAddlLandRevenue()).add(lrd.getJmSankirn()).add(lrd.getGpSankirn())
							.add(lrd.getZpSankirn()).add(lrd.getPreYearPendingAddlLandRevenue())
							.add(lrd.getJmAkrushik()).add(lrd.getGpAkrushik()).add(lrd.getZpAkrushik())
							.add(lrd.getPreYearSankirnJmWith()).add(lrd.getPreYearSankirnJmWithout())
							.add(lrd.getPreYearPendingJm()).add(lrd.getPreYearPendingGp())
							.add(lrd.getPreYearPendingZp()).add(lrd.getPreYearPendingNaCess())
							//.add(lrd.getPreYearNoticeFee()).setScale(0, BigDecimal.ROUND_HALF_UP));
							
					//chnage in below line of code for issue for dumala + thkabaki 14/11/2025
							.add(lrd.getPreYearNoticeFee()).add(lrd.getJmDumala())

					.add(lrd.getGpDumala()).add(lrd.getZpDumala()).setScale(0, BigDecimal.ROUND_HALF_UP));
				}

				// new conditions added by tushar 7th oct 2024 end

			}
			DatabaseContextHolder.restoreCtx();
			if (listlandRevenueCollectn != null && listlandRevenueCollectn.size() > 0) {
				return listlandRevenueCollectn.stream().map(lrdR -> toDao(lrdR)).collect(Collectors.toList());
			}
			throw new NoContent("error.form.recordnotfound", messageSource, locale);
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

//pre

	@Override
	public List<LandRevenueDemandDao> getAllReceiptsByVillagePre2023(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(landRevenueDemandDao.getCCode())) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(landRevenueDemandDao.getCCode())) {
			List<LandRevenueDemand2023> listlandRevenueCollectn = new ArrayList<>();
			if (landRevenueDemandDao.getIsMoneyepositedBank() == 'Y') {
				listlandRevenueCollectn = demandRepo2023
						.findReceiptDetailsBycCodeAndIsMoneyepositedBankAndRevenueYearAndActiveFlag(
								landRevenueDemandDao.getCCode(), landRevenueDemandDao.getIsMoneyepositedBank(),
								landRevenueDemandDao.getRevenueYear(), 'Y');
			} else {
				listlandRevenueCollectn = demandRepo2023
						.findReceiptDetailsBycCodeAndIsMoneyepositedBankAndRevenueYearAndModeOfPaymentAndActiveFlagAndChallanIdIsNull(
								landRevenueDemandDao.getCCode(), landRevenueDemandDao.getIsMoneyepositedBank(),
								landRevenueDemandDao.getRevenueYear(), landRevenueDemandDao.getModeOfPayment(), 'Y');

			}

			for (LandRevenueDemand2023 lrd : listlandRevenueCollectn) {

				double assessment = Double.parseDouble(lrd.getAssessment().toString());

				double jmAkrushik = Double.parseDouble(lrd.getJmAkrushik().toString());

//			if(lrd.getKhataNo()!=null && assessment > 10 && jmAkrushik == 0 ) {
//			List<Object[]> lrdetails = landRevenueDemandRepository.getBagayatJirayatDetails(
//					request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//					lrd.getKhataNo(),lrd.getcCode());
//			List<LandRevenueDemandDao> lst = lrdetails.stream().map(r -> {
//				LandRevenueDemandDao lrDao = new LandRevenueDemandDao();
//				lrd.setBagayatArea(r[1] == null ? "0" : r[1].toString());
//				lrd.setJirayatArea(r[2]== null ? "0" : r[2].toString());
//				return lrDao;
//			}).collect(Collectors.toList());
//			
//			if((lrd.getBagayatArea()!=null)&&(lrd.getJirayatArea()!=null)) {
//			double bagayatArea = Double.parseDouble(lrd.getBagayatArea().toString());
//			double jirayatArea = Double.parseDouble(lrd.getJirayatArea().toString());
//				if(bagayatArea==0 && jirayatArea <= 2.99 ) {
//						lrd.setJmBindumala(BigDecimal.ZERO);
//						lrd.setJmDumala(BigDecimal.ZERO);
//					}
//				
//				
//				if((bagayatArea >=0 && jirayatArea >= 3.00 ) || (bagayatArea >0 && jirayatArea <= 2.99 )) {
//					if(assessment >= 5 && assessment <=10) {
//						lrd.setJmBindumala(BigDecimal.ZERO);
//						lrd.setJmDumala(BigDecimal.ZERO);
//					}else if(assessment > 10) {
//						lrd.setJmBindumala(lrd.getJmBindumala());
//						lrd.setJmDumala(lrd.getJmDumala());	
//					}else{
//						lrd.setJmBindumala(BigDecimal.ZERO);
//						lrd.setJmDumala(BigDecimal.ZERO);	
//					}
//					}
//			}}

				lrd.setTotalJM((lrd.getJmBindumala()).add(lrd.getJmDumala()).add(lrd.getJmAkrushik())
						.add(lrd.getJmSankirn()).add(lrd.getPreYearPendingJm()));
				lrd.setTotalGP((lrd.getGpBindumala()).add(lrd.getGpDumala()).add(lrd.getGpAkrushik())
						.add(lrd.getGpSankirn()).add(lrd.getPreYearPendingGp()));
				lrd.setTotalZP((lrd.getZpBindumala()).add(lrd.getZpDumala()).add(lrd.getZpAkrushik())
						.add(lrd.getZpSankirn()).add(lrd.getPreYearPendingZp()));

				lrd.setTotalOf0045((lrd.getAddlEducationalCess()).add(lrd.getEducationalCess())
						.add(lrd.getEmployeeGuaranteeScheme()).add(lrd.getPreYearPendingAddlEducationalCess())
						.add(lrd.getPreYearPendingEducationalCess()).add(lrd.getPreYearPendingEmployeeGuaranteeScheme())
						.setScale(0, BigDecimal.ROUND_HALF_UP));

				lrd.setTotalOf0029((lrd.getTotalJM()).subtract(lrd.getJmVajasut()).add(lrd.getTotalGP())
						.subtract(lrd.getGpVajasut()).add(lrd.getTotalZP()).subtract(lrd.getZpVajasut())
						.add(lrd.getPreYearSankirnJmWith()).add(lrd.getPreYearSankirnJmWithout())
						.add(lrd.getAddlLandRevenue()).add(lrd.getPreYearPendingAddlLandRevenue())
						.add(lrd.getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee())
						.setScale(0, BigDecimal.ROUND_HALF_UP));

				/*
				 * if(assessment > 10) {
				 * lrd.setTotalOf0029((lrd.getTotalJM()).add(lrd.getTotalGP()).add(lrd.
				 * getTotalZP()).add(lrd.getPreYearSankirnJmWith()).add(lrd.
				 * getPreYearSankirnJmWithout())
				 * .add(lrd.getAddlLandRevenue()).add(lrd.getPreYearPendingAddlLandRevenue()).
				 * add(lrd.getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee()).setScale(0,
				 * BigDecimal.ROUND_HALF_UP)); } else if(assessment >= 5 && assessment <= 10 ) {
				 * lrd.setTotalOf0029((lrd.getJmAkrushik()).add(lrd.getJmSankirn()).add(lrd.
				 * getPreYearPendingJm()).add(lrd.getTotalGP()).add(lrd.getTotalZP())
				 * .add(lrd.getPreYearSankirnJmWith()).add(lrd.getPreYearSankirnJmWithout()).add
				 * (lrd.getAddlLandRevenue()).add(lrd.getPreYearPendingAddlLandRevenue()).add(
				 * lrd.getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee()).setScale(0,
				 * BigDecimal.ROUND_HALF_UP)); }else {
				 * lrd.setTotalOf0029((lrd.getAddlLandRevenue()).add(lrd.getJmSankirn()).add(lrd
				 * .getGpSankirn()).add(lrd.getZpSankirn()).add(lrd.
				 * getPreYearPendingAddlLandRevenue())
				 * .add(lrd.getJmAkrushik()).add(lrd.getGpAkrushik()).add(lrd.getZpAkrushik()).
				 * add(lrd.getPreYearSankirnJmWith()).add(lrd.getPreYearSankirnJmWithout()).add(
				 * lrd.getPreYearPendingJm())
				 * .add(lrd.getPreYearPendingGp()).add(lrd.getPreYearPendingZp()).add(lrd.
				 * getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee()).setScale(0,
				 * BigDecimal.ROUND_HALF_UP)); }
				 */
			}
			DatabaseContextHolder.restoreCtx();
			if (listlandRevenueCollectn != null && listlandRevenueCollectn.size() > 0) {
				return listlandRevenueCollectn.stream().map(lrdR -> toDao2023(lrdR)).collect(Collectors.toList());
			}
			throw new NoContent("error.form.recordnotfound", messageSource, locale);
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	
	
	
	//pre
	public List<LandRevenueDemandDao> getAllReceiptsByVillagePre2024(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {
			dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
			List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
					request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
					CommonConstants.mhrorPassword, request.getHeader("servarthId"));
			Response response = new Response();
			response.setKhataMergeData(ccodeslist.stream().map(r -> {
				KhataMergeDao km = new KhataMergeDao();
				km.setcCode(r[0].toString());
				km.setServarthId(r[1].toString());
				km.setVillageName(r[2].toString());
				return km;
			}).collect(Collectors.toList()));

			String ccodes = null;
			for (KhataMergeDao r : response.getKhataMergeData()) {
				if (r.getcCode().equals(landRevenueDemandDao.getCCode())) {
					ccodes = r.getcCode();
					break;
				} else {
					ccodes = "0";
				}
			}

			if (ccodes.equals(landRevenueDemandDao.getCCode())) {
				List<LandRevenueDemand2024> listlandRevenueCollectn = new ArrayList<>();
				if (landRevenueDemandDao.getIsMoneyepositedBank() == 'Y') {
					listlandRevenueCollectn = demandRepo2024
							.findReceiptDetailsBycCodeAndIsMoneyepositedBankAndRevenueYearAndActiveFlag(
									landRevenueDemandDao.getCCode(), landRevenueDemandDao.getIsMoneyepositedBank(),
									landRevenueDemandDao.getRevenueYear(), 'Y');
				} else {
					listlandRevenueCollectn = demandRepo2024
							.findReceiptDetailsBycCodeAndIsMoneyepositedBankAndRevenueYearAndModeOfPaymentAndActiveFlagAndChallanIdIsNull(
									landRevenueDemandDao.getCCode(), landRevenueDemandDao.getIsMoneyepositedBank(),
									landRevenueDemandDao.getRevenueYear(), landRevenueDemandDao.getModeOfPayment(), 'Y');

				}

				for (LandRevenueDemand2024 lrd : listlandRevenueCollectn) {

					double assessment = Double.parseDouble(lrd.getAssessment().toString());

					double jmAkrushik = Double.parseDouble(lrd.getJmAkrushik().toString());

//				if(lrd.getKhataNo()!=null && assessment > 10 && jmAkrushik == 0 ) {
//				List<Object[]> lrdetails = landRevenueDemandRepository.getBagayatJirayatDetails(
//						request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//						lrd.getKhataNo(),lrd.getcCode());
//				List<LandRevenueDemandDao> lst = lrdetails.stream().map(r -> {
//					LandRevenueDemandDao lrDao = new LandRevenueDemandDao();
//					lrd.setBagayatArea(r[1] == null ? "0" : r[1].toString());
//					lrd.setJirayatArea(r[2]== null ? "0" : r[2].toString());
//					return lrDao;
//				}).collect(Collectors.toList());
//				
//				if((lrd.getBagayatArea()!=null)&&(lrd.getJirayatArea()!=null)) {
//				double bagayatArea = Double.parseDouble(lrd.getBagayatArea().toString());
//				double jirayatArea = Double.parseDouble(lrd.getJirayatArea().toString());
//					if(bagayatArea==0 && jirayatArea <= 2.99 ) {
//							lrd.setJmBindumala(BigDecimal.ZERO);
//							lrd.setJmDumala(BigDecimal.ZERO);
//						}
//					
//					
//					if((bagayatArea >=0 && jirayatArea >= 3.00 ) || (bagayatArea >0 && jirayatArea <= 2.99 )) {
//						if(assessment >= 5 && assessment <=10) {
//							lrd.setJmBindumala(BigDecimal.ZERO);
//							lrd.setJmDumala(BigDecimal.ZERO);
//						}else if(assessment > 10) {
//							lrd.setJmBindumala(lrd.getJmBindumala());
//							lrd.setJmDumala(lrd.getJmDumala());	
//						}else{
//							lrd.setJmBindumala(BigDecimal.ZERO);
//							lrd.setJmDumala(BigDecimal.ZERO);	
//						}
//						}
//				}}

					lrd.setTotalJM((lrd.getJmBindumala()).add(lrd.getJmDumala()).add(lrd.getJmAkrushik())
							.add(lrd.getJmSankirn()).add(lrd.getPreYearPendingJm()));
					lrd.setTotalGP((lrd.getGpBindumala()).add(lrd.getGpDumala()).add(lrd.getGpAkrushik())
							.add(lrd.getGpSankirn()).add(lrd.getPreYearPendingGp()));
					lrd.setTotalZP((lrd.getZpBindumala()).add(lrd.getZpDumala()).add(lrd.getZpAkrushik())
							.add(lrd.getZpSankirn()).add(lrd.getPreYearPendingZp()));

					lrd.setTotalOf0045((lrd.getAddlEducationalCess()).add(lrd.getEducationalCess())
							.add(lrd.getEmployeeGuaranteeScheme()).add(lrd.getPreYearPendingAddlEducationalCess())
							.add(lrd.getPreYearPendingEducationalCess()).add(lrd.getPreYearPendingEmployeeGuaranteeScheme())
							.setScale(0, BigDecimal.ROUND_HALF_UP));

					lrd.setTotalOf0029((lrd.getTotalJM()).subtract(lrd.getJmVajasut()).add(lrd.getTotalGP())
							.subtract(lrd.getGpVajasut()).add(lrd.getTotalZP()).subtract(lrd.getZpVajasut())
							.add(lrd.getPreYearSankirnJmWith()).add(lrd.getPreYearSankirnJmWithout())
							.add(lrd.getAddlLandRevenue()).add(lrd.getPreYearPendingAddlLandRevenue())
							.add(lrd.getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee())
							.setScale(0, BigDecimal.ROUND_HALF_UP));

					/*
					 * if(assessment > 10) {
					 * lrd.setTotalOf0029((lrd.getTotalJM()).add(lrd.getTotalGP()).add(lrd.
					 * getTotalZP()).add(lrd.getPreYearSankirnJmWith()).add(lrd.
					 * getPreYearSankirnJmWithout())
					 * .add(lrd.getAddlLandRevenue()).add(lrd.getPreYearPendingAddlLandRevenue()).
					 * add(lrd.getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee()).setScale(0,
					 * BigDecimal.ROUND_HALF_UP)); } else if(assessment >= 5 && assessment <= 10 ) {
					 * lrd.setTotalOf0029((lrd.getJmAkrushik()).add(lrd.getJmSankirn()).add(lrd.
					 * getPreYearPendingJm()).add(lrd.getTotalGP()).add(lrd.getTotalZP())
					 * .add(lrd.getPreYearSankirnJmWith()).add(lrd.getPreYearSankirnJmWithout()).add
					 * (lrd.getAddlLandRevenue()).add(lrd.getPreYearPendingAddlLandRevenue()).add(
					 * lrd.getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee()).setScale(0,
					 * BigDecimal.ROUND_HALF_UP)); }else {
					 * lrd.setTotalOf0029((lrd.getAddlLandRevenue()).add(lrd.getJmSankirn()).add(lrd
					 * .getGpSankirn()).add(lrd.getZpSankirn()).add(lrd.
					 * getPreYearPendingAddlLandRevenue())
					 * .add(lrd.getJmAkrushik()).add(lrd.getGpAkrushik()).add(lrd.getZpAkrushik()).
					 * add(lrd.getPreYearSankirnJmWith()).add(lrd.getPreYearSankirnJmWithout()).add(
					 * lrd.getPreYearPendingJm())
					 * .add(lrd.getPreYearPendingGp()).add(lrd.getPreYearPendingZp()).add(lrd.
					 * getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee()).setScale(0,
					 * BigDecimal.ROUND_HALF_UP)); }
					 */
				}
				
				
				DatabaseContextHolder.restoreCtx();
				if (listlandRevenueCollectn != null && listlandRevenueCollectn.size() > 0) {
					return listlandRevenueCollectn.stream().map(lrdR -> toDao2024(lrdR)).collect(Collectors.toList());
				}
				throw new NoContent("error.form.recordnotfound", messageSource, locale);
			} else {
				throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
			}
		}
		
	
	
	//pre 2025-26
	//pre
		public List<LandRevenueDemandDao> getAllReceiptsByVillagePre(LandRevenueDemandDao landRevenueDemandDao,
				HttpServletRequest request, Locale locale) {
				dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
				List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
						request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
						CommonConstants.mhrorPassword, request.getHeader("servarthId"));
				Response response = new Response();
				response.setKhataMergeData(ccodeslist.stream().map(r -> {
					KhataMergeDao km = new KhataMergeDao();
					km.setcCode(r[0].toString());
					km.setServarthId(r[1].toString());
					km.setVillageName(r[2].toString());
					return km;
				}).collect(Collectors.toList()));

				String ccodes = null;
				for (KhataMergeDao r : response.getKhataMergeData()) {
					if (r.getcCode().equals(landRevenueDemandDao.getCCode())) {
						ccodes = r.getcCode();
						break;
					} else {
						ccodes = "0";
					}
				}

				if (ccodes.equals(landRevenueDemandDao.getCCode())) {
					List<LandRevenueDemand2025> listlandRevenueCollectn = new ArrayList<>();
					if (landRevenueDemandDao.getIsMoneyepositedBank() == 'Y') {
						listlandRevenueCollectn = demandRepo2025
								.findReceiptDetailsBycCodeAndIsMoneyepositedBankAndRevenueYearAndActiveFlag(
										landRevenueDemandDao.getCCode(), landRevenueDemandDao.getIsMoneyepositedBank(),
										landRevenueDemandDao.getRevenueYear(), 'Y');
					} else {
						listlandRevenueCollectn = demandRepo2025
								.findReceiptDetailsBycCodeAndIsMoneyepositedBankAndRevenueYearAndModeOfPaymentAndActiveFlagAndChallanIdIsNull(
										landRevenueDemandDao.getCCode(), landRevenueDemandDao.getIsMoneyepositedBank(),
										landRevenueDemandDao.getRevenueYear(), landRevenueDemandDao.getModeOfPayment(), 'Y');

					}

					for (LandRevenueDemand2025 lrd : listlandRevenueCollectn) {

						double assessment = Double.parseDouble(lrd.getAssessment().toString());

						double jmAkrushik = Double.parseDouble(lrd.getJmAkrushik().toString());

//					if(lrd.getKhataNo()!=null && assessment > 10 && jmAkrushik == 0 ) {
//					List<Object[]> lrdetails = landRevenueDemandRepository.getBagayatJirayatDetails(
//							request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//							lrd.getKhataNo(),lrd.getcCode());
//					List<LandRevenueDemandDao> lst = lrdetails.stream().map(r -> {
//						LandRevenueDemandDao lrDao = new LandRevenueDemandDao();
//						lrd.setBagayatArea(r[1] == null ? "0" : r[1].toString());
//						lrd.setJirayatArea(r[2]== null ? "0" : r[2].toString());
//						return lrDao;
//					}).collect(Collectors.toList());
//					
//					if((lrd.getBagayatArea()!=null)&&(lrd.getJirayatArea()!=null)) {
//					double bagayatArea = Double.parseDouble(lrd.getBagayatArea().toString());
//					double jirayatArea = Double.parseDouble(lrd.getJirayatArea().toString());
//						if(bagayatArea==0 && jirayatArea <= 2.99 ) {
//								lrd.setJmBindumala(BigDecimal.ZERO);
//								lrd.setJmDumala(BigDecimal.ZERO);
//							}
//						
//						
//						if((bagayatArea >=0 && jirayatArea >= 3.00 ) || (bagayatArea >0 && jirayatArea <= 2.99 )) {
//							if(assessment >= 5 && assessment <=10) {
//								lrd.setJmBindumala(BigDecimal.ZERO);
//								lrd.setJmDumala(BigDecimal.ZERO);
//							}else if(assessment > 10) {
//								lrd.setJmBindumala(lrd.getJmBindumala());
//								lrd.setJmDumala(lrd.getJmDumala());	
//							}else{
//								lrd.setJmBindumala(BigDecimal.ZERO);
//								lrd.setJmDumala(BigDecimal.ZERO);	
//							}
//							}
//					}}

						lrd.setTotalJM((lrd.getJmBindumala()).add(lrd.getJmDumala()).add(lrd.getJmAkrushik())
								.add(lrd.getJmSankirn()).add(lrd.getPreYearPendingJm()));
						lrd.setTotalGP((lrd.getGpBindumala()).add(lrd.getGpDumala()).add(lrd.getGpAkrushik())
								.add(lrd.getGpSankirn()).add(lrd.getPreYearPendingGp()));
						lrd.setTotalZP((lrd.getZpBindumala()).add(lrd.getZpDumala()).add(lrd.getZpAkrushik())
								.add(lrd.getZpSankirn()).add(lrd.getPreYearPendingZp()));

						lrd.setTotalOf0045((lrd.getAddlEducationalCess()).add(lrd.getEducationalCess())
								.add(lrd.getEmployeeGuaranteeScheme()).add(lrd.getPreYearPendingAddlEducationalCess())
								.add(lrd.getPreYearPendingEducationalCess()).add(lrd.getPreYearPendingEmployeeGuaranteeScheme())
								.setScale(0, BigDecimal.ROUND_HALF_UP));

						lrd.setTotalOf0029((lrd.getTotalJM()).subtract(lrd.getJmVajasut()).add(lrd.getTotalGP())
								.subtract(lrd.getGpVajasut()).add(lrd.getTotalZP()).subtract(lrd.getZpVajasut())
								.add(lrd.getPreYearSankirnJmWith()).add(lrd.getPreYearSankirnJmWithout())
								.add(lrd.getAddlLandRevenue()).add(lrd.getPreYearPendingAddlLandRevenue())
								.add(lrd.getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee())
								.setScale(0, BigDecimal.ROUND_HALF_UP));

						/*
						 * if(assessment > 10) {
						 * lrd.setTotalOf0029((lrd.getTotalJM()).add(lrd.getTotalGP()).add(lrd.
						 * getTotalZP()).add(lrd.getPreYearSankirnJmWith()).add(lrd.
						 * getPreYearSankirnJmWithout())
						 * .add(lrd.getAddlLandRevenue()).add(lrd.getPreYearPendingAddlLandRevenue()).
						 * add(lrd.getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee()).setScale(0,
						 * BigDecimal.ROUND_HALF_UP)); } else if(assessment >= 5 && assessment <= 10 ) {
						 * lrd.setTotalOf0029((lrd.getJmAkrushik()).add(lrd.getJmSankirn()).add(lrd.
						 * getPreYearPendingJm()).add(lrd.getTotalGP()).add(lrd.getTotalZP())
						 * .add(lrd.getPreYearSankirnJmWith()).add(lrd.getPreYearSankirnJmWithout()).add
						 * (lrd.getAddlLandRevenue()).add(lrd.getPreYearPendingAddlLandRevenue()).add(
						 * lrd.getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee()).setScale(0,
						 * BigDecimal.ROUND_HALF_UP)); }else {
						 * lrd.setTotalOf0029((lrd.getAddlLandRevenue()).add(lrd.getJmSankirn()).add(lrd
						 * .getGpSankirn()).add(lrd.getZpSankirn()).add(lrd.
						 * getPreYearPendingAddlLandRevenue())
						 * .add(lrd.getJmAkrushik()).add(lrd.getGpAkrushik()).add(lrd.getZpAkrushik()).
						 * add(lrd.getPreYearSankirnJmWith()).add(lrd.getPreYearSankirnJmWithout()).add(
						 * lrd.getPreYearPendingJm())
						 * .add(lrd.getPreYearPendingGp()).add(lrd.getPreYearPendingZp()).add(lrd.
						 * getPreYearPendingNaCess()).add(lrd.getPreYearNoticeFee()).setScale(0,
						 * BigDecimal.ROUND_HALF_UP)); }
						 */
					}
					
					
					DatabaseContextHolder.restoreCtx();
					if (listlandRevenueCollectn != null && listlandRevenueCollectn.size() > 0) {
						return listlandRevenueCollectn.stream().map(lrdR -> toDao2025(lrdR)).collect(Collectors.toList());
					}
					throw new NoContent("error.form.recordnotfound", messageSource, locale);
				} else {
					throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
				}
			}
			
		
	
	
	
	
	@Override
	public List<ChallanDtlDao> getChallanDetails(ChallanDtlDao challanDtlDao, HttpServletRequest request,
			Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(challanDtlDao.getCCode())) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(challanDtlDao.getCCode())) {
			if (challanDtlDao != null && challanDtlDao.getCCode() != null && challanDtlDao.getStatus() != null) {
				
				
				try {
					
					List<ChallanDtl> lstChallanDtl = challanRepository.findBycCodeAndRevenueYearAndStatusAndActiveFlag(
							challanDtlDao.getCCode(), challanDtlDao.getRevenueYear(), challanDtlDao.getStatus(), 'Y');
					
					//printing the list :
					for (ChallanDtl cd : lstChallanDtl) {
					    System.out.println("List of Challan Details-------- : "+cd);
					}

					
					DatabaseContextHolder.restoreCtx();
					if (lstChallanDtl.isEmpty()) {
						throw new NoContent("error.form.recordnotfound", messageSource, locale);
					}
					return lstChallanDtl.stream().map(lrdR -> toChallanDao(lrdR)).collect(Collectors.toList());
					
				}catch(Exception e) {
					System.out.println("Challan Details Excetion is Here -------- : "+e.getMessage());
				}
				

			}
			throw new ResourceNotFoundException("error.form.recordnotfound", messageSource, locale);
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	@Override
	public Response getChallanDetailsAll(ChallanDtlDao challanDtlDao, HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(challanDtlDao.getCCode())) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(challanDtlDao.getCCode())) {
			if (challanDtlDao != null && challanDtlDao.getCCode() != null && challanDtlDao.getStatus() != null) {
				List<Object[]> lstChallanDtl = challanRepository.getChallanDetailsAll(request.getHeader("echHost"),
						request.getHeader("echDbName"), request.getHeader("echSchemaName"), CommonConstants.echUser,
						CommonConstants.echPassword, challanDtlDao.getCCode(), challanDtlDao.getRevenueYear(),
						challanDtlDao.getStatus(), 'Y');
				DatabaseContextHolder.restoreCtx();
				if (lstChallanDtl.isEmpty()) {
					throw new NoContent("error.form.recordnotfound", messageSource, locale);
				}
//			return lstChallanDtl.stream().map(lrdR -> toChallanDao(lrdR)).collect(Collectors.toList());

				Response lstChallanDtl1 = new Response();
				lstChallanDtl1.setLandRevenueDemandData(lstChallanDtl.stream().map((object) -> {
					LandRevenueDemandDao cdd = new LandRevenueDemandDao();
					cdd.setChallanId(Long.valueOf(object[0] == null ? "" : object[0].toString()));
					cdd.setId(Long.valueOf(object[1] == null ? "" : object[1].toString()));
					cdd.setCCode(object[2].toString());
					cdd.setBankReceiptNumber0045(object[3] == null ? "" : object[3].toString());
					cdd.setBankReceiptPath(object[4] == null ? "" : object[4].toString());
					cdd.setBankReceiptPath0045(object[5] == null ? "" : object[5].toString());
					cdd.setChallanNo(object[6] == null ? "" : object[6].toString());
					cdd.setChallanNo0045(object[7] == null ? "" : object[7].toString());
					cdd.setModeOfPayment(object[8] == null ? "" : object[8].toString());
					cdd.setTotalAmount(
							BigDecimal.valueOf(Double.valueOf(object[9] == null ? "" : object[9].toString())));
					cdd.setTotalOfHead0029(object[10] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[10].toString())));
					cdd.setTotalOfHead0045(object[11] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[11].toString())));
					cdd.setBankName(object[12] == null ? "" : object[12].toString());
					cdd.setBankAddress(object[13] == null ? "" : object[13].toString());
					cdd.setActualFileName(object[14] == null ? "" : object[14].toString());
					cdd.setBankReceiptNumber(object[15] == null ? "" : object[15].toString());
					cdd.setAddlLandRevenue(object[16] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[16].toString())));
					cdd.setAddlEducationalCess(object[17] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[17].toString())));
					cdd.setEducationalCess(object[18] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[18].toString())));
					cdd.setEmployeeGuaranteeScheme(object[19] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[19].toString())));
					cdd.setGpAkrushik(object[20] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[20].toString())));
					cdd.setGpBindumala(object[21] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[21].toString())));
					cdd.setGpDumala(object[22] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[22].toString())));
					cdd.setGpSankirn(object[23] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[23].toString())));
					cdd.setGpVajasut(object[24] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[24].toString())));
					cdd.setJmAkrushik(object[25] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[25].toString())));
					cdd.setJmBindumala(object[26] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[26].toString())));
					cdd.setJmDumala(object[27] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[27].toString())));
					cdd.setJmSankirn(object[28] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[28].toString())));
					cdd.setJmVajasut(object[29] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[29].toString())));
					cdd.setNetAmount(object[30] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[30].toString())));
					cdd.setNetPending(object[31] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[31].toString())));
					cdd.setNetReceived(object[32] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[32].toString())));
					cdd.setNoticeAmount(object[33] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[33].toString())));
					cdd.setPreYearNoticeFee(object[34] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[34].toString())));
					cdd.setPreYearPendingAddlEducationalCess(object[35] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[35].toString())));
					cdd.setPreYearPendingAddlLandRevenue(object[36] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[36].toString())));
					cdd.setPreYearPendingEducationalCess(object[37] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[37].toString())));
					cdd.setPreYearPendingEmployeeGuaranteeScheme(object[38] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[38].toString())));
					cdd.setPreYearPendingGp(object[39] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[39].toString())));
					cdd.setPreYearPendingJm(object[40] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[40].toString())));
					cdd.setPreYearPendingNaCess(object[41] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[41].toString())));
					cdd.setPreYearPendingZp(object[42] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[42].toString())));
					cdd.setPreYearSankirnJmWith(object[43] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[43].toString())));
					cdd.setPreYearSankirnJmWithout(object[44] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[44].toString())));
					cdd.setZpAkrushik(object[45] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[45].toString())));
					cdd.setZpBindumala(object[46] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[46].toString())));
					cdd.setZpDumala(object[47] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[47].toString())));
					cdd.setZpSankirn(object[48] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[48].toString())));
					cdd.setZpVajasut(object[49] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[49].toString())));
					cdd.setReceiptNo(object[50] == null ? "" : object[50].toString());
					cdd.setStatus(object[51] == null ? "" : object[51].toString());
					cdd.setChallanDateForReport(object[52] == null ? "" : object[52].toString());
					cdd.setCountOfReceipts(object[53] == null ? "" : object[53].toString());
					return cdd;
				}).collect(Collectors.toList()));
				return lstChallanDtl1;
			}
			throw new ResourceNotFoundException("error.form.recordnotfound", messageSource, locale);
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	// pre
	@Override
	public Response getChallanDetailsAllPre(ChallanDtlDao challanDtlDao, HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(challanDtlDao.getCCode())) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(challanDtlDao.getCCode())) {
			if (challanDtlDao != null && challanDtlDao.getCCode() != null && challanDtlDao.getStatus() != null) {
				List<Object[]> lstChallanDtl = challanRepository.getChallanDetailsAllPre(request.getHeader("echHost"),
						request.getHeader("echDbName"), request.getHeader("echSchemaName"), CommonConstants.echUser,
						CommonConstants.echPassword, challanDtlDao.getCCode(), challanDtlDao.getRevenueYear(),
						challanDtlDao.getStatus(), 'Y');
				DatabaseContextHolder.restoreCtx();
				if (lstChallanDtl.isEmpty()) {
					throw new NoContent("error.form.recordnotfound", messageSource, locale);
				}
//			return lstChallanDtl.stream().map(lrdR -> toChallanDao(lrdR)).collect(Collectors.toList());

				Response lstChallanDtl1 = new Response();
				lstChallanDtl1.setLandRevenueDemandData(lstChallanDtl.stream().map((object) -> {
					LandRevenueDemandDao cdd = new LandRevenueDemandDao();
					cdd.setChallanId(Long.valueOf(object[0] == null ? "" : object[0].toString()));
					cdd.setId(Long.valueOf(object[1] == null ? "" : object[1].toString()));
					cdd.setCCode(object[2].toString());
					cdd.setBankReceiptNumber0045(object[3] == null ? "" : object[3].toString());
					cdd.setBankReceiptPath(object[4] == null ? "" : object[4].toString());
					cdd.setBankReceiptPath0045(object[5] == null ? "" : object[5].toString());
					cdd.setChallanNo(object[6] == null ? "" : object[6].toString());
					cdd.setChallanNo0045(object[7] == null ? "" : object[7].toString());
					cdd.setModeOfPayment(object[8] == null ? "" : object[8].toString());
					cdd.setTotalAmount(
							BigDecimal.valueOf(Double.valueOf(object[9] == null ? "" : object[9].toString())));
					cdd.setTotalOfHead0029(object[10] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[10].toString())));
					cdd.setTotalOfHead0045(object[11] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[11].toString())));
					cdd.setBankName(object[12] == null ? "" : object[12].toString());
					cdd.setBankAddress(object[13] == null ? "" : object[13].toString());
					cdd.setActualFileName(object[14] == null ? "" : object[14].toString());
					cdd.setBankReceiptNumber(object[15] == null ? "" : object[15].toString());
					cdd.setAddlLandRevenue(object[16] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[16].toString())));
					cdd.setAddlEducationalCess(object[17] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[17].toString())));
					cdd.setEducationalCess(object[18] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[18].toString())));
					cdd.setEmployeeGuaranteeScheme(object[19] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[19].toString())));
					cdd.setGpAkrushik(object[20] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[20].toString())));
					cdd.setGpBindumala(object[21] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[21].toString())));
					cdd.setGpDumala(object[22] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[22].toString())));
					cdd.setGpSankirn(object[23] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[23].toString())));
					cdd.setGpVajasut(object[24] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[24].toString())));
					cdd.setJmAkrushik(object[25] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[25].toString())));
					cdd.setJmBindumala(object[26] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[26].toString())));
					cdd.setJmDumala(object[27] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[27].toString())));
					cdd.setJmSankirn(object[28] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[28].toString())));
					cdd.setJmVajasut(object[29] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[29].toString())));
					cdd.setNetAmount(object[30] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[30].toString())));
					cdd.setNetPending(object[31] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[31].toString())));
					cdd.setNetReceived(object[32] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[32].toString())));
					cdd.setNoticeAmount(object[33] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[33].toString())));
					cdd.setPreYearNoticeFee(object[34] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[34].toString())));
					cdd.setPreYearPendingAddlEducationalCess(object[35] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[35].toString())));
					cdd.setPreYearPendingAddlLandRevenue(object[36] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[36].toString())));
					cdd.setPreYearPendingEducationalCess(object[37] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[37].toString())));
					cdd.setPreYearPendingEmployeeGuaranteeScheme(object[38] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[38].toString())));
					cdd.setPreYearPendingGp(object[39] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[39].toString())));
					cdd.setPreYearPendingJm(object[40] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[40].toString())));
					cdd.setPreYearPendingNaCess(object[41] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[41].toString())));
					cdd.setPreYearPendingZp(object[42] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[42].toString())));
					cdd.setPreYearSankirnJmWith(object[43] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[43].toString())));
					cdd.setPreYearSankirnJmWithout(object[44] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[44].toString())));
					cdd.setZpAkrushik(object[45] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[45].toString())));
					cdd.setZpBindumala(object[46] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[46].toString())));
					cdd.setZpDumala(object[47] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[47].toString())));
					cdd.setZpSankirn(object[48] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[48].toString())));
					cdd.setZpVajasut(object[49] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[49].toString())));
					cdd.setReceiptNo(object[50] == null ? "" : object[50].toString());
					cdd.setStatus(object[51] == null ? "" : object[51].toString());
					cdd.setChallanDateForReport(object[52] == null ? "" : object[52].toString());
					cdd.setCountOfReceipts(object[53] == null ? "" : object[53].toString());
					return cdd;
				}).collect(Collectors.toList()));
				return lstChallanDtl1;
			}
			throw new ResourceNotFoundException("error.form.recordnotfound", messageSource, locale);
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	@Override
	public MessageResponse updateChallanDetails(MultipartFile file,
			MultipartFile file0045,
			Long id,
			@Valid String bankAddress,
			@Valid String bankName,
			String bankReceiptNumber,
			String bankReceiptNumber0045,
			HttpServletRequest request,
			Locale locale,
			String ccode,
			String talukaCode,
			String districtCode,
			String revenueYear) {

		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		ChallanDtl challanDtl = challanRepository.getById(id);
		List<LandRevenueDemand> landReveneuDemand = landRevenueDemandRepository.findByChallanId(id);
		List<ReceiptDtlLog> receiptDtlList = dtlLogRepository.findByChallanId(id);

		if (file != null || file0045 != null) {

			if (file0045 != null) {
				String file00455 = getFilename(file0045, ccode,
						talukaCode,
						districtCode,
						revenueYear);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				challanDtl.setBankReceiptPath0045(file00455);
				challanDtl.setActualFileName0045(file0045.getOriginalFilename());
				challanDtl.setBankReceiptNumber0045(bankReceiptNumber0045);
				challanDtl.setBankName(bankName);
				challanDtl.setBankAddress(bankAddress);
				challanDtl.setBankDepositeDate(LocalDateTime.now());
				challanDtl.setStatus("Deposited");
			}

			if (file != null) {
				String file0029 = getFilename(file, ccode,
						talukaCode,
						districtCode,
						revenueYear);
				challanDtl.setBankReceiptPath(file0029);
				challanDtl.setActualFileName(file.getOriginalFilename());
				challanDtl.setBankReceiptNumber(bankReceiptNumber);
				challanDtl.setBankName(bankName);
				challanDtl.setBankAddress(bankAddress);
				challanDtl.setBankDepositeDate(LocalDateTime.now());
				challanDtl.setStatus("Deposited");
			}

			for (LandRevenueDemand lrd : landReveneuDemand) {
				lrd.setIsMoneyepositedBank('Y');
				landRevenueDemandRepository.save(lrd);
			}

			for (ReceiptDtlLog receiptDtl : receiptDtlList) {
				receiptDtl.setIsMoneyepositedBank('Y');
				dtlLogRepository.save(receiptDtl);
			}
			challanRepository.save(challanDtl);
			DatabaseContextHolder.restoreCtx();
			return MessageResponse.builder().status("SUCCESS").message("Record Updated Successfully.").build();
		} else {
			throw new FieldValidationException("error.form.errorwhilesave");
		}
	}

	public String getFilename(MultipartFile file,
			String ccode,
			String talukaCode,
			String districtCode,
			String revenueYear) {
		String newFileName = new SimpleDateFormat(DateFormats.FILE_NAME_DATE_FORMAT).format(new Date());
		String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'),
				file.getOriginalFilename().length());
		boolean flag = false;
		if (!file.getOriginalFilename().isEmpty()
				&& extension.toLowerCase().matches(CommonConstants.Matcher.validFileMatcher)) {
			if (extension.toLowerCase().matches(CommonConstants.Matcher.imageMatcher)) {
				try {
					BufferedImage image = ImageIO.read(file.getInputStream());
					image.getData();
					flag = true;
				} catch (Exception e) {
					e.printStackTrace();
					DatabaseContextHolder.restoreCtx();
					return null;
				}
			} else if (extension.toLowerCase().matches(CommonConstants.Matcher.pdfMather)) {
				try {
					PDDocument document = PDDocument.load(file.getInputStream());
					document.getDocument();
					flag = true;
				} catch (Exception e) {
					e.printStackTrace();
					DatabaseContextHolder.restoreCtx();
					return null;
				}
			} else {
				DatabaseContextHolder.restoreCtx();
				return null;
			}
			if (flag) {
				try {
					
				String basePath = StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "BANK_CHALLAN");
					
				String fullPath = Paths.get(basePath,revenueYear, districtCode,talukaCode,ccode).toString();
					 
			    // Create directory if not exists
				File dir = new File(fullPath);
					
					//File dir = new File(
					//		StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "BANK_RECEIPTS"));
					
					
					if (!dir.exists()) {
						dir.mkdirs();
					}
					BufferedOutputStream outputStream = new BufferedOutputStream(
							new FileOutputStream(new File(dir, newFileName + extension)));
					outputStream.write(file.getBytes());
					outputStream.flush();
					outputStream.close();
					outputStream.close();
					return newFileName + extension;
				} catch (FileNotFoundException e) {
					DatabaseContextHolder.restoreCtx();
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					DatabaseContextHolder.restoreCtx();
					e.printStackTrace();
					return null;
				}
			}
		}
		return null;
	}

	public MessageResponse deleteAttachment(Long id, HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		ChallanDtl challanDtl = challanRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Challan", id));
		challanDtl.setBankReceiptPath(null);
		challanDtl.setStatus("Not Deposited");
		challanDtl.setActualFileName(null);
		challanRepository.save(challanDtl);
		DatabaseContextHolder.restoreCtx();
		return MessageResponse.builder().status("SUCCESS")
				.message(messageSource.getMessage("error.form.deletedattchement", null, locale)).build();
	}

	
	//both path fetching logic implemented 14 Aug 2025
	@Override
	public ChallanDtl download(ChallanDtlDao challanDtlDao, HttpServletRequest request, 
			Locale locale,
			String revenueYear,
			String districtCode,
			String talukaCode,
			String cCode)
			throws IOException {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));

		ChallanDtl challanDtl = challanRepository.findById(challanDtlDao.getId())
				.orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale,
						"Challan", challanDtlDao.getId()));

		if (challanDtlDao.getCCode().equals(challanDtl.getCCode())) {
			if (challanDtl.getBankReceiptPath() != null) {
				
				
				
				try {
					
					System.out.println("Revenue Year: " + revenueYear);
					System.out.println("District Code: " + districtCode);
					System.out.println("Taluka Code: " + talukaCode);
					System.out.println("CCode: " + cCode);
					
					
					 // Build dynamic path: /BANK_RECEIPTS/revenue_year/districtCode/talukaCode/ccode/filename
				    String basePath = StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "BANK_CHALLAN");
				    String dynamicPath = basePath
				            + revenueYear + "/"
				            + districtCode + "/"
				            + talukaCode + "/"
				            + cCode + "/"
				            + challanDtl.getBankReceiptPath();
				    
				    
				    
				    String alternatePath = StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "BANK_RECEIPTS") + challanDtl.getBankReceiptPath();

				    File file = new File(dynamicPath);
				    
				    if(!file.exists()) {
				    	System.out.println("File not found in dynamic path: " + dynamicPath);
				        System.out.println("Checking BANK_RECEIPTS path: " + alternatePath);
				        file = new File(alternatePath);
				    }
				    
				    
					byte[] fileBytes = null;
					try {
						fileBytes = Files.readAllBytes(file.toPath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String base64Encoded = Base64.encodeBase64String(fileBytes);

					challanDtl.setPath0029(base64Encoded);
					
				}catch(Exception e) {
					System.out.println("error at file :"+ e.getMessage());
				}
				
				/*File file = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "BANK_RECEIPTS")
						+ challanDtl.getBankReceiptPath());
				byte[] fileBytes = null;
				try {
					fileBytes = Files.readAllBytes(file.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String base64Encoded = Base64.encodeBase64String(fileBytes);

				challanDtl.setPath0029(base64Encoded);*/
			}
			
			

			if (challanDtl.getBankReceiptPath0045() != null) {
				
				
				
				
				try {
					
					
					System.out.println("Revenue Year0045: " + revenueYear);
					System.out.println("District Code: " + districtCode);
					System.out.println("Taluka Code: " + talukaCode);
					System.out.println("CCode: " + cCode);
					
					 // Build dynamic path: /BANK_RECEIPTS/revenue_year/districtCode/talukaCode/ccode/filename
				    String basePath = StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "BANK_CHALLAN");
				    String dynamicPath = basePath
				            + revenueYear + "/"
				            + districtCode + "/"
				            + talukaCode + "/"
				            + cCode + "/"
				            + challanDtl.getBankReceiptPath();
				    
				    
				    
				    String alternatePath = StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "BANK_RECEIPTS") + challanDtl.getBankReceiptPath();

				    File file1 = new File(dynamicPath);
				    
				    if(!file1.exists()) {
				    	System.out.println("File not found in dynamic path: " + dynamicPath);
				        System.out.println("Checking BANK_RECEIPTS path: " + alternatePath);
				        file1 = new File(alternatePath);
				    }
						
						byte[] fileBytes1 = null;
						try {
							fileBytes1 = Files.readAllBytes(file1.toPath());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String base64Encoded1 = Base64.encodeBase64String(fileBytes1);

						challanDtl.setPath0045(base64Encoded1);
					
				}catch(Exception e) {
					System.out.println("error at filepath ----: "+ e.getMessage());
				}
				
				/*File file1 = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "BANK_RECEIPTS")
						+ challanDtl.getBankReceiptPath0045());
				byte[] fileBytes1 = null;
				try {
					fileBytes1 = Files.readAllBytes(file1.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String base64Encoded1 = Base64.encodeBase64String(fileBytes1);

				challanDtl.setPath0045(base64Encoded1);*/
			}

			return challanDtl;
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}

	}

//	@Override
//	public ResponseEntity<Object>  download(Long id,HttpServletRequest request,Locale locale) throws IOException {
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//		ChallanDtl challanDtl=challanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale,"Challan",id));
//		File file = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "BANK_RECEIPTS")+challanDtl.getBankReceiptPath());
//		HashMap<String, byte[]> files =new HashMap<>();
//		if(challanDtl.getBankReceiptPath()!=null) {
//			files.put(challanDtl.getBankReceiptPath(),Base64.encodeBase64(Files.readAllBytes(file.toPath())));
//		}
//		DatabaseContextHolder.restoreCtx();
//		return ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + challanDtl.getBankReceiptPath() 
//						)
//				.body(Base64.encodeBase64(Files.readAllBytes(file.toPath())));
//	}

	@Override
	public ResponseEntity<Object> download0045(Long id, HttpServletRequest request, Locale locale) throws IOException {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		ChallanDtl challanDtl = challanRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Challan", id));
		File file0045 = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "BANK_RECEIPTS")
				+ challanDtl.getBankReceiptPath0045());
		HashMap<String, byte[]> files = new HashMap<>();
		if (challanDtl.getBankReceiptPath0045() != null) {
			files.put(challanDtl.getBankReceiptPath0045(), Base64.encodeBase64(Files.readAllBytes(file0045.toPath())));
		}
		DatabaseContextHolder.restoreCtx();
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + challanDtl.getBankReceiptPath0045() + "\"")
				.body(Base64.encodeBase64(Files.readAllBytes(file0045.toPath())));
	}

	@Override
	public List<LandRevenueDemandDao> getLandRevenueDemandDetails(String revenueYear, Integer districtCode,
			Integer talukaCode, String cCode, HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
//		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
//				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
//				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
//		Response response = new Response();
//		response.setKhataMergeData(ccodeslist.stream().map(r -> {
//			KhataMergeDao km = new KhataMergeDao();
//			km.setcCode(r[0].toString());
//			km.setServarthId(r[1].toString());
//			km.setVillageName(r[2].toString());
//			return km;
//		}).collect(Collectors.toList()));
//
//		String ccodes = null;
//		for (KhataMergeDao r : response.getKhataMergeData()) {
//			if (r.getcCode().equals(cCode)) {
//				ccodes = r.getcCode();
//				break;
//			} else {
//				ccodes = "0";
//			}
//		}

		//if (ccodes.equals(cCode)) {
		
			List<LandRevenueDemand> landRevenueDemand = landRevenueDemandRepository.getDetailByinps(
					request.getHeader("echHost"), request.getHeader("echDbName"), request.getHeader("echSchemaName"),
					CommonConstants.echUser, CommonConstants.echPassword, revenueYear, cCode, 'E');
			DatabaseContextHolder.restoreCtx();
			
			if(landRevenueDemand.isEmpty()) {
				throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
			} else {
			return landRevenueDemand.stream().map(lrdR -> toDao(lrdR)).collect(Collectors.toList());
			}
	}

//	@Override
//	public List<LandRevenueDemandDao> getAllForm9BReportDetails(Integer districtCode, Integer talukaCode, String cCode,
//			HttpServletRequest request, Locale locale) {
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
//		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
//				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
//				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
//		Response response = new Response();
//		response.setKhataMergeData(ccodeslist.stream().map(r -> {
//			KhataMergeDao km = new KhataMergeDao();
//			km.setcCode(r[0].toString());
//			km.setServarthId(r[1].toString());
//			km.setVillageName(r[2].toString());
//			return km;
//		}).collect(Collectors.toList()));
//
//		String ccodes = null;
//		for (KhataMergeDao r : response.getKhataMergeData()) {
//			if (r.getcCode().equals(cCode)) {
//				ccodes = r.getcCode();
//				break;
//			} else {
//				ccodes = "0";
//			}
//		}
//
//		if (ccodes.equals(cCode)) {
//			List<LandRevenueDemand> listlandRevenueDemand = landRevenueDemandRepository
//					.findReceiptDetailsBycCodeAndActiveFlagAndReceiptNoIsNotNull(cCode, 'Y');
//			DatabaseContextHolder.restoreCtx();
//
//			if (listlandRevenueDemand != null && listlandRevenueDemand.size() > 0) {
//				return listlandRevenueDemand.stream().map(r -> toDao(r)).collect(Collectors.toList());
//			}
//			throw new ResourceNotFoundException("error.form.notfound");
//		} else {
//			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
//		}
//	}
//
//	// pre
//	@Override
//	public List<LandRevenueDemandDao> getAllForm9BReportDetailsPre(Integer districtCode, Integer talukaCode,
//			String cCode, String revenueYear, HttpServletRequest request, Locale locale) {
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
//		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
//				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
//				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
//		Response response = new Response();
//		response.setKhataMergeData(ccodeslist.stream().map(r -> {
//			KhataMergeDao km = new KhataMergeDao();
//			km.setcCode(r[0].toString());
//			km.setServarthId(r[1].toString());
//			km.setVillageName(r[2].toString());
//			return km;
//		}).collect(Collectors.toList()));
//
//		String ccodes = null;
//		for (KhataMergeDao r : response.getKhataMergeData()) {
//			if (r.getcCode().equals(cCode)) {
//				ccodes = r.getcCode();
//				break;
//			} else {
//				ccodes = "0";
//			}
//		}
//
//		if (ccodes.equals(cCode)) {
//			List<LandRevenueDemand2023> listlandRevenueDemand = demandRepo2023
//					.findReceiptDetailsBycCodeAndActiveFlagAndRevenueYearAndReceiptNoIsNotNull(cCode, 'Y', revenueYear);
//			DatabaseContextHolder.restoreCtx();
//
//			if (listlandRevenueDemand != null && listlandRevenueDemand.size() > 0) {
//				return listlandRevenueDemand.stream().map(r -> toDao2023(r)).collect(Collectors.toList());
//			}
//			throw new ResourceNotFoundException("error.form.notfound");
//		} else {
//			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
//		}
//	}

	
	
/*	@Override
	public List<LandRevenueDemandDao> getAllForm9BReportDetails(
	        Integer districtCode,
	        Integer talukaCode,
	        String cCode,
	        String revenueYear,
	        HttpServletRequest request,
	        Locale locale) {

	    if (cCode == null || cCode.trim().isEmpty()) {
	        throw new IllegalArgumentException("cCode cannot be null or empty");
	    }

	    try {
	        

	    	dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
	       
	        String currentYear = getCurrentFinancialYear();


	        List<Object[]> resultList; 
	        
	        
	        System.out.println("Condition : "+revenueYear == null || revenueYear.trim().isEmpty() ||
	                revenueYear.trim().equalsIgnoreCase(currentYear));

	        if (revenueYear == null || revenueYear.trim().isEmpty() ||
	                revenueYear.trim().equalsIgnoreCase(currentYear)) {

	        	
	        	
	        	System.out.println("EchHost -- :"+request.getHeader("echHost"));
	        	System.out.println("echDbName -- :"+ request.getHeader("echDbName"));
	        	System.out.println("echSchemaName -- :"+request.getHeader("echSchemaName"));
	        	
	            resultList = landRevenueDemandRepository.findCurrentYearData(
	                    request.getHeader("echHost"),
	                    request.getHeader("echDbName"),
	                    request.getHeader("echSchemaName"),
	                    CommonConstants.echUser,
	                    CommonConstants.echPassword,
	                    cCode.trim(),
	                    revenueYear.trim());

	        } else {


	            String prevRevenueYear = revenueYear; 
	            String[] revSplit = prevRevenueYear.split("-");
	            String revSingleStr = revSplit[0]; 
	            System.out.println("Starting year: " + revSingleStr);  
	            

	        	System.out.println("EchHost -- :"+request.getHeader("echHost"));
	        	System.out.println("echDbName -- :"+ request.getHeader("echDbName"));
	        	System.out.println("echSchemaName -- :"+request.getHeader("echSchemaName"));

	            resultList = demandRepo2023.findPreviousYearData(
	                    request.getHeader("echHost"),
	                    request.getHeader("echDbName"),
	                    request.getHeader("echSchemaName"),
	                    CommonConstants.echUser,
	                    CommonConstants.echPassword,
	                    cCode.trim(),
	                    CommonConstants.DefaultDataPort,
	                    prevRevenueYear.trim(),
	                    revSingleStr
	            );
	        }

	        if (resultList == null || resultList.isEmpty()) {
	            throw new ResourceNotFoundException("error.form.notfound");
	        }

	        List<LandRevenueDemandDao> finalList = new ArrayList<>();

	        for (Object[] obj : resultList) {
	            LandRevenueDemandDao dao = new LandRevenueDemandDao();
	            dao.setKhataNo(getString(obj, 0));
	            dao.setKhataOwnerName(getString(obj, 1));
	            dao.setRevenueYear(getString(obj, 6));
	            dao.setReceiptNo(getString(obj, 50));

	            finalList.add(dao);
	        }

	        return finalList;

	    } catch (Exception ex) {
	        throw new RuntimeException("Error while fetching Form 9B Report", ex);
	    } finally {
	        DatabaseContextHolder.restoreCtx();
	    }
	}*/
	
	
	
	/* anushkas method @Override
	public List<LandRevenueDemandDao> getAllForm9BReportDetails(
	        Integer districtCode,
	        Integer talukaCode,
	        String cCode,
	        String revenueYear,
	        HttpServletRequest request,
	        Locale locale) {

		 System.out.println("I am into service before setDatasource 9B-------------");

		
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		
	    
	    
	    System.out.println("I am into service 9B-------------");

	    //Response response = new Response();

	 

	  

	    
	    		 String currentYear = getCurrentFinancialYear();
	    		 
	    		 
	    		 
	    		 
	    		 
	 	        String normalizedRevenueYear =
	 	                (revenueYear == null || revenueYear.trim().isEmpty())
	 	                        ? currentYear
	 	                        : revenueYear.trim();
	 	        
	 	        
	 	       System.out.println("after that condition-------------"+revenueYear == null || revenueYear.trim().isEmpty());

		        List<Object[]> resultList ;
		        
		        boolean var = normalizedRevenueYear.equalsIgnoreCase(currentYear);
		        System.out.println("normalizedRevenueYear.equalsIgnoreCase(currentYear) : "+var);

		        if (normalizedRevenueYear.equalsIgnoreCase(currentYear)) {
		        	
		        	  System.out.println("True part -----------  : ");
		        	
		        	System.out.println("echHost   : "+ request.getHeader("echHost"));
		        	System.out.println("echDbName   : "+ request.getHeader("echDbName"));
		        	System.out.println("echSchemaName   : "+ request.getHeader("echSchemaName"));
		        	

		            resultList = landRevenueDemandRepository.findCurrentYearData(
		                    request.getHeader("echHost"),
		                    request.getHeader("echDbName"),
		                    request.getHeader("echSchemaName"),
		                    CommonConstants.echUser,
		                    CommonConstants.echPassword,
		                    cCode.trim(),
		                    normalizedRevenueYear);

		        } else if(normalizedRevenueYear == "2024-25") {

		            String[] revSplit = normalizedRevenueYear.split("-");
		            String revSingleStr = revSplit[0];

		            System.out.println("Starting year: " + revSingleStr);

		            System.out.println("first Else part -----------  : ");
		            
		            System.out.println("echHost   : "+ request.getHeader("echHost"));
		        	System.out.println("echDbName   : "+ request.getHeader("echDbName"));
		        	System.out.println("echSchemaName   : "+ request.getHeader("echSchemaName"));
		        	
		            
		            resultList = demandRepo2023.findPreviousYearData(
		                    request.getHeader("echHost"),
		                    request.getHeader("echDbName"),
		                    request.getHeader("echSchemaName"),
		                    CommonConstants.echUser,
		                    CommonConstants.echPassword,
		                    cCode.trim(),
		                    normalizedRevenueYear,
		                    revSingleStr);
		            
		        } else  {

		            String[] revSplit = normalizedRevenueYear.split("-");
		            String revSingleStr = revSplit[0];

		            System.out.println("Starting year: " + revSingleStr);

		            System.out.println("second Else part -----------  : ");
		            
		            System.out.println("echHost   : "+ request.getHeader("echHost"));
		        	System.out.println("echDbName   : "+ request.getHeader("echDbName"));
		        	System.out.println("echSchemaName   : "+ request.getHeader("echSchemaName"));
		        	
		            
		            resultList = landRevenueDemandRepositoryPre.findPreviousYearData(
		                    request.getHeader("echHost"),
		                    request.getHeader("echDbName"),
		                    request.getHeader("echSchemaName"),
		                    CommonConstants.echUser,
		                    CommonConstants.echPassword,
		                    cCode.trim(),
		                    normalizedRevenueYear,
		                    revSingleStr);
		        }
		        
		        DatabaseContextHolder.restoreCtx();

		        if (resultList == null || resultList.isEmpty()) {
		            throw new ResourceNotFoundException("error.form.notfound");
		        }

		        List<LandRevenueDemandDao> finalList = new ArrayList<>();

		        for (Object[] obj : resultList) {

		            LandRevenueDemandDao dao = new LandRevenueDemandDao();

	           

		            dao.setKhataNo(Objects.toString(getString(obj, 0), ""));
		            dao.setKhataOwnerName(Objects.toString(getString(obj, 1), ""));
		            dao.setRevenueYear(Objects.toString(getString(obj, 6), ""));
		            dao.setReceiptNo(Objects.toString(getString(obj, 50), ""));

		            finalList.add(dao);
		        }
	     

	        return finalList;

	
	}*/
	
	
	
	
	
	
	
	@Override
	public List<LandRevenueDemandDao> getAllForm9BReportDetails(
	        Integer districtCode,
	        Integer talukaCode,
	        String cCode,
	        String revenueYear,
	        HttpServletRequest request,
	        Locale locale) {

	    System.out.println("I am into service before setDatasource 9B-------------");

	    dataSourceKeeper.setDataSourceEchwadi(
	            request.getHeader("echDbName"),
	            request.getHeader("echSchemaName"));

	    System.out.println("I am into service 9B-------------");

	    List<Object[]> resultList;
	    
	  

	

	    if (revenueYear.equals(CommonConstants.currentYear)) {

	        System.out.println("True part -----------");

	       

	        System.out.println("echHost   : " + request.getHeader("echHost"));
	        System.out.println("echDbName   : " + request.getHeader("echDbName"));
	        System.out.println("echSchemaName   : "
	                + request.getHeader("echSchemaName"));

	        resultList = landRevenueDemandRepository.findCurrentYearData(
	                request.getHeader("echHost"),
	                request.getHeader("echDbName"),
	                request.getHeader("echSchemaName"),
	                CommonConstants.echUser,
	                CommonConstants.echPassword,
	                cCode.trim(),
	                revenueYear);
	        
System.out.println("my current year data is -----------"+resultList);
for (Object[] row : resultList) {
    System.out.println(Arrays.toString(row));
}

	    } else if (revenueYear.equals("2024-25")) {

			String[] revSingleString1=revenueYear.split("-");
			String singString1=revSingleString1[0];

	        resultList = demandRepo2023.findPreviousYearData(
	                request.getHeader("echHost"),
	                request.getHeader("echDbName"),
	                request.getHeader("echSchemaName"),
	                CommonConstants.echUser,
	                CommonConstants.echPassword,
	                cCode.trim(),
	                revenueYear,
	                singString1);
	                
//	                normalizedRevenueYear,
//	                revSingleStr);
	        System.out.println("my previuos year data is ----"+resultList);

	    } else {

	      //  String revSingleStr = normalizedRevenueYear.split("-")[0];

	     //   System.out.println("Starting year last else part: " + revSingleStr);
	        System.out.println("second Else part -----------  : ");
	        
	        String[] revSingleString1=revenueYear.split("-");
			String singString1=revSingleString1[0];

	        resultList = landRevenueDemandRepositoryPre.findPreviousYearData(
	                request.getHeader("echHost"),
	                request.getHeader("echDbName"),
	                request.getHeader("echSchemaName"),
	                CommonConstants.echUser,
	                CommonConstants.echPassword,
	                cCode.trim(),
	                revenueYear,
	                singString1
	                );
	    }

	    DatabaseContextHolder.restoreCtx();

	    if (resultList == null || resultList.isEmpty()) {
	        throw new ResourceNotFoundException("error.form.notfound");
	    }

	    List<LandRevenueDemandDao> finalList = new ArrayList<>();

	    for (Object[] obj : resultList) {

	        LandRevenueDemandDao dao = new LandRevenueDemandDao();

             dao.setKhataNo(getString(obj, 0));
	        
	        
	        
	        dao.setKhataOwnerName(getString(obj, 1));
	        
	        dao.setArea(getBigDecimal(obj, 3));
	        System.out.println(obj[3]);
	        System.out.println(obj[3].getClass().getName());
	        dao.setRevenueYear(getString(obj, 6));
	        
	        System.out.println(dao.getRevenueYear());
	        
	        dao.setReceiptNo(getString(obj, 47));
	        

	        //new
	        dao.setReceiptDate(getLocalDateTime(obj, 48));

	        if (obj[48] != null) {
	            System.out.println(obj[48]);
	            System.out.println(obj[48].getClass().getName());
	        } else {
	            System.out.println("receipt_date is NULL");
	        }

	        dao.setModeOfPayment(getString(obj, 46));
	        dao.setNetAmount(getBigDecimal(obj, 35));
	        finalList.add(dao);
	    }

	    return finalList;
	}
	
	/*@Override
	public List<LandRevenueDemandDao> getAllForm9BReportDetails(
	        Integer districtCode,
	        Integer talukaCode,
	        String cCode,
	        String revenueYear,
	        HttpServletRequest request,
	        Locale locale) {

	    System.out.println("I am into service before setDatasource 9B-------------");

	    dataSourceKeeper.setDataSourceEchwadi(
	            request.getHeader("echDbName"),
	            request.getHeader("echSchemaName"));

	    System.out.println("I am into service 9B-------------");

	    LocalDate currentDate = LocalDate.now();

	    int year = currentDate.getYear();
	    int month = currentDate.getMonthValue();

	    int startYear;
	    int endYear;

	  

	    if (month >= 4) {
	        startYear = year - 1;
	        endYear = year;
	    } else {
	        startYear = year - 2;
	        endYear = year - 1;
	    }

	    String currentYear =
	            startYear + "-" + String.valueOf(endYear).substring(2);

	    String currentYearSingle = String.valueOf(startYear);

	    System.out.println("Current Financial Year : " + currentYear);
	    System.out.println("Current Financial Starting Year : " + currentYearSingle);

	    String normalizedRevenueYear =
	            (revenueYear == null || revenueYear.trim().isEmpty())
	                    ? currentYear
	                    : revenueYear.trim();

	    System.out.println("after that condition-------------"
	            + (revenueYear == null || revenueYear.trim().isEmpty()));

	    List<Object[]> resultList;
	    
	  

	    boolean var = normalizedRevenueYear.equalsIgnoreCase(currentYear);

	    System.out.println("normalizedRevenueYear.equalsIgnoreCase(currentYear) : " + var);

	    if (normalizedRevenueYear.equalsIgnoreCase(currentYear)) {

	        System.out.println("True part -----------");

	        System.out.println("Current Financial Starting Year : "
	                + currentYearSingle);

	        System.out.println("echHost   : " + request.getHeader("echHost"));
	        System.out.println("echDbName   : " + request.getHeader("echDbName"));
	        System.out.println("echSchemaName   : "
	                + request.getHeader("echSchemaName"));

	        resultList = landRevenueDemandRepository.findCurrentYearData(
	                request.getHeader("echHost"),
	                request.getHeader("echDbName"),
	                request.getHeader("echSchemaName"),
	                CommonConstants.echUser,
	                CommonConstants.echPassword,
	                cCode.trim(),
	                normalizedRevenueYear);
	        
System.out.println("my current year data is -----------"+resultList);
for (Object[] row : resultList) {
    System.out.println(Arrays.toString(row));
}

	    } else if (normalizedRevenueYear.equals("2024-25")) {

	        String revSingleStr = normalizedRevenueYear.split("-")[0];

	        System.out.println("Starting year  2024-25: " + revSingleStr);
	        System.out.println("first Else part -----------  : ");

	        resultList = demandRepo2023.findPreviousYearData(
	                request.getHeader("echHost"),
	                request.getHeader("echDbName"),
	                request.getHeader("echSchemaName"),
	                CommonConstants.echUser,
	                CommonConstants.echPassword,
	                cCode.trim(),
	                normalizedRevenueYear,
	                revSingleStr);
	        System.out.println("my previuos year data is ----"+resultList);

	    } else {

	        String revSingleStr = normalizedRevenueYear.split("-")[0];

	        System.out.println("Starting year last else part: " + revSingleStr);
	        System.out.println("second Else part -----------  : ");

	        resultList = landRevenueDemandRepositoryPre.findPreviousYearData(
	                request.getHeader("echHost"),
	                request.getHeader("echDbName"),
	                request.getHeader("echSchemaName"),
	                CommonConstants.echUser,
	                CommonConstants.echPassword,
	                cCode.trim(),
	                normalizedRevenueYear,
	                revSingleStr);
	    }

	    DatabaseContextHolder.restoreCtx();

	    if (resultList == null || resultList.isEmpty()) {
	        throw new ResourceNotFoundException("error.form.notfound");
	    }

	    List<LandRevenueDemandDao> finalList = new ArrayList<>();

	    for (Object[] obj : resultList) {

	        LandRevenueDemandDao dao = new LandRevenueDemandDao();

             dao.setKhataNo(getString(obj, 0));
	        
	        
	        
	        dao.setKhataOwnerName(getString(obj, 1));
	        
	        dao.setArea(getBigDecimal(obj, 3));
	        System.out.println(obj[3]);
	        System.out.println(obj[3].getClass().getName());
	        dao.setRevenueYear(getString(obj, 6));
	        
	        System.out.println(dao.getRevenueYear());
	        
	        dao.setReceiptNo(getString(obj, 47));
	        

	        //new
	        dao.setReceiptDate(getLocalDateTime(obj, 48));

	        if (obj[48] != null) {
	            System.out.println(obj[48]);
	            System.out.println(obj[48].getClass().getName());
	        } else {
	            System.out.println("receipt_date is NULL");
	        }

	        dao.setModeOfPayment(getString(obj, 46));
	        dao.setNetAmount(getBigDecimal(obj, 35));
	        finalList.add(dao);
	    }

	    return finalList;
	}*/



	private LocalDateTime getLocalDateTime(Object[] obj, int index) {

	    if (obj != null && obj.length > index && obj[index] != null) {

	        if (obj[index] instanceof java.sql.Date) {
	            return ((java.sql.Date) obj[index])
	                    .toLocalDate()
	                    .atStartOfDay();
	        }

	        if (obj[index] instanceof java.sql.Timestamp) {
	            return ((java.sql.Timestamp) obj[index])
	                    .toLocalDateTime();
	        }
	    }

	    return null;
	}
	private BigDecimal getBigDecimal(Object[] obj, int index) {
	    if (obj != null && obj.length > index && obj[index] != null) {
	        return new BigDecimal(obj[index].toString());
	    }
	    return BigDecimal.ZERO;
	}

	private String getString(Object[] obj, int index) {
	    if (obj != null && obj.length > index && obj[index] != null) {
	        return obj[index].toString();
	    }
	    return "";
	}
	
	@Override
	public List<LandRevenueDemandDao> getForm8BData(String cCode, String revenueYear, Integer districtCode,
			Integer talukaCode, HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));

		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(cCode)) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(cCode)) {
			List<LandRevenueDemand> landRevenueDemand = landRevenueDemandRepository
					.findBycCodeAndRevenueYearAndActiveFlag(cCode, revenueYear, 'Y');
			DatabaseContextHolder.restoreCtx();
			if (landRevenueDemand.isEmpty()) {
				throw new ResourceNotFoundException("error.form.reportdetails", messageSource, locale);
			} else {

				List<LandRevenueDemandDao> landRevenueDemandDaoLst = landRevenueDemand.stream().map(lrdR -> toDao(lrdR))
						.collect(Collectors.toList());

				LandRevenueDemandDao landRevnueDemandTotal = new LandRevenueDemandDao();
				landRevnueDemandTotal.setKhataOwnerName("एकूण");
				landRevnueDemandTotal.setArea(BigDecimal.ZERO);
				landRevnueDemandTotal.setAssessment(BigDecimal.ZERO);

				landRevnueDemandTotal.setJmBindumala(BigDecimal.ZERO);
				landRevnueDemandTotal.setZpBindumala(BigDecimal.ZERO);
				landRevnueDemandTotal.setGpBindumala(BigDecimal.ZERO);

				landRevnueDemandTotal.setJmDumala(BigDecimal.ZERO);
				landRevnueDemandTotal.setZpDumala(BigDecimal.ZERO);
				landRevnueDemandTotal.setGpDumala(BigDecimal.ZERO);

				landRevnueDemandTotal.setJmAkrushik(BigDecimal.ZERO);
				landRevnueDemandTotal.setZpAkrushik(BigDecimal.ZERO);
				landRevnueDemandTotal.setGpAkrushik(BigDecimal.ZERO);

				landRevnueDemandTotal.setJmVajasut(BigDecimal.ZERO);
				landRevnueDemandTotal.setZpVajasut(BigDecimal.ZERO);
				landRevnueDemandTotal.setGpVajasut(BigDecimal.ZERO);

				landRevnueDemandTotal.setEducationalCess(BigDecimal.ZERO);
				landRevnueDemandTotal.setAddlEducationalCess(BigDecimal.ZERO);
				landRevnueDemandTotal.setAddlLandRevenue(BigDecimal.ZERO);
				landRevnueDemandTotal.setEmployeeGuaranteeScheme(BigDecimal.ZERO);

				landRevnueDemandTotal.setPreYearPendingGp(BigDecimal.ZERO);
				landRevnueDemandTotal.setPreYearPendingJm(BigDecimal.ZERO);
				landRevnueDemandTotal.setPreYearPendingZp(BigDecimal.ZERO);
				landRevnueDemandTotal.setPreYearPendingAddlLandRevenue(BigDecimal.ZERO);
				landRevnueDemandTotal.setPreYearPendingAddlEducationalCess(BigDecimal.ZERO);
				landRevnueDemandTotal.setPreYearPendingEducationalCess(BigDecimal.ZERO);
				landRevnueDemandTotal.setPreYearPendingEmployeeGuaranteeScheme(BigDecimal.ZERO);
				landRevnueDemandTotal.setPreYearNoticeFee(BigDecimal.ZERO);
				landRevnueDemandTotal.setPreYearPendingNaCess(BigDecimal.ZERO);
				landRevnueDemandTotal.setPreYearSankirnJmWith(BigDecimal.ZERO);
				landRevnueDemandTotal.setPreYearSankirnJmWithout(BigDecimal.ZERO);

				landRevnueDemandTotal.setGpSankirn(BigDecimal.ZERO);
				landRevnueDemandTotal.setZpSankirn(BigDecimal.ZERO);
				landRevnueDemandTotal.setJmSankirn(BigDecimal.ZERO);

				landRevnueDemandTotal.setNetAmount(BigDecimal.ZERO);
				landRevnueDemandTotal.setNetPending(BigDecimal.ZERO);

				landRevnueDemandTotal.setTotalJm(BigDecimal.ZERO);
				landRevnueDemandTotal.setTotalGp(BigDecimal.ZERO);
				landRevnueDemandTotal.setTotalZp(BigDecimal.ZERO);

				for (LandRevenueDemandDao lrd : landRevenueDemandDaoLst) {

					landRevnueDemandTotal.setArea((landRevnueDemandTotal.getArea()
							.add(lrd.getArea() == null ? BigDecimal.ZERO : lrd.getArea())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setAssessment((landRevnueDemandTotal.getAssessment()
							.add(lrd.getAssessment() == null ? BigDecimal.ZERO : lrd.getAssessment())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					landRevnueDemandTotal.setZpBindumala((landRevnueDemandTotal.getZpBindumala()
							.add(lrd.getZpBindumala() == null ? BigDecimal.ZERO : lrd.getZpBindumala())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setGpBindumala((landRevnueDemandTotal.getGpBindumala()
							.add(lrd.getGpBindumala() == null ? BigDecimal.ZERO : lrd.getGpBindumala())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setJmBindumala((landRevnueDemandTotal.getJmBindumala()
							.add(lrd.getJmBindumala() == null ? BigDecimal.ZERO : lrd.getJmBindumala())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					landRevnueDemandTotal.setZpDumala((landRevnueDemandTotal.getZpDumala()
							.add(lrd.getZpDumala() == null ? BigDecimal.ZERO : lrd.getZpDumala())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setGpDumala((landRevnueDemandTotal.getZpDumala()
							.add(lrd.getGpDumala() == null ? BigDecimal.ZERO : lrd.getGpDumala())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setJmDumala((landRevnueDemandTotal.getJmDumala()
							.add(lrd.getJmDumala() == null ? BigDecimal.ZERO : lrd.getJmDumala())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					landRevnueDemandTotal.setZpAkrushik((landRevnueDemandTotal.getZpAkrushik()
							.add(lrd.getZpAkrushik() == null ? BigDecimal.ZERO : lrd.getZpAkrushik())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setGpAkrushik((landRevnueDemandTotal.getGpAkrushik()
							.add(lrd.getGpAkrushik() == null ? BigDecimal.ZERO : lrd.getGpAkrushik())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setJmAkrushik((landRevnueDemandTotal.getJmAkrushik()
							.add(lrd.getJmAkrushik() == null ? BigDecimal.ZERO : lrd.getJmAkrushik())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					landRevnueDemandTotal.setZpSankirn((landRevnueDemandTotal.getZpSankirn()
							.add(lrd.getZpSankirn() == null ? BigDecimal.ZERO : lrd.getZpSankirn())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setGpSankirn((landRevnueDemandTotal.getGpSankirn()
							.add(lrd.getGpSankirn() == null ? BigDecimal.ZERO : lrd.getGpSankirn())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setJmSankirn((landRevnueDemandTotal.getJmSankirn()
							.add(lrd.getJmSankirn() == null ? BigDecimal.ZERO : lrd.getJmSankirn())
							.add(lrd.getNoticeAmount() == null ? BigDecimal.ZERO : lrd.getNoticeAmount())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					landRevnueDemandTotal.setZpVajasut((landRevnueDemandTotal.getZpVajasut()
							.add(lrd.getZpVajasut() == null ? BigDecimal.ZERO : lrd.getZpVajasut())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setGpVajasut((landRevnueDemandTotal.getGpVajasut()
							.add(lrd.getGpVajasut() == null ? BigDecimal.ZERO : lrd.getGpVajasut())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setJmVajasut((landRevnueDemandTotal.getJmVajasut()
							.add(lrd.getJmVajasut() == null ? BigDecimal.ZERO : lrd.getJmVajasut())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					landRevnueDemandTotal.setEducationalCess((landRevnueDemandTotal.getEducationalCess()
							.add(lrd.getEducationalCess() == null ? BigDecimal.ZERO : lrd.getEducationalCess()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setAddlEducationalCess((landRevnueDemandTotal.getAddlEducationalCess()
							.add(lrd.getAddlEducationalCess() == null ? BigDecimal.ZERO : lrd.getAddlEducationalCess()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setAddlLandRevenue((landRevnueDemandTotal.getAddlLandRevenue()
							.add(lrd.getAddlLandRevenue() == null ? BigDecimal.ZERO : lrd.getAddlLandRevenue()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal
							.setEmployeeGuaranteeScheme(
									(landRevnueDemandTotal.getEmployeeGuaranteeScheme()
											.add(lrd.getEmployeeGuaranteeScheme() == null ? BigDecimal.ZERO
													: lrd.getEmployeeGuaranteeScheme())).setScale(2,
															BigDecimal.ROUND_HALF_UP));

					landRevnueDemandTotal.setPreYearPendingGp((landRevnueDemandTotal.getPreYearPendingGp()
							.add(lrd.getPreYearPendingGp() == null ? BigDecimal.ZERO : lrd.getPreYearPendingGp()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setPreYearPendingJm((landRevnueDemandTotal.getPreYearPendingJm()
							.add(lrd.getPreYearPendingJm() == null ? BigDecimal.ZERO : lrd.getPreYearPendingJm()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setPreYearPendingZp((landRevnueDemandTotal.getPreYearPendingZp()
							.add(lrd.getPreYearPendingZp() == null ? BigDecimal.ZERO : lrd.getPreYearPendingZp()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal
							.setPreYearPendingAddlLandRevenue((landRevnueDemandTotal.getPreYearPendingAddlLandRevenue()
									.add(lrd.getPreYearPendingAddlLandRevenue() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingAddlLandRevenue())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setPreYearPendingAddlEducationalCess(
							(landRevnueDemandTotal.getPreYearPendingAddlEducationalCess()
									.add(lrd.getPreYearPendingAddlEducationalCess() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingAddlEducationalCess())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal
							.setPreYearPendingEducationalCess((landRevnueDemandTotal.getPreYearPendingEducationalCess()
									.add(lrd.getPreYearPendingEducationalCess() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingEducationalCess())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setPreYearPendingEmployeeGuaranteeScheme(
							(landRevnueDemandTotal.getPreYearPendingEmployeeGuaranteeScheme()
									.add(lrd.getPreYearPendingEmployeeGuaranteeScheme() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingEmployeeGuaranteeScheme())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setPreYearNoticeFee((landRevnueDemandTotal.getPreYearNoticeFee()
							.add(lrd.getPreYearNoticeFee() == null ? BigDecimal.ZERO : lrd.getPreYearNoticeFee()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setPreYearPendingNaCess((landRevnueDemandTotal.getPreYearPendingNaCess().add(
							lrd.getPreYearPendingNaCess() == null ? BigDecimal.ZERO : lrd.getPreYearPendingNaCess()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setPreYearSankirnJmWith((landRevnueDemandTotal.getPreYearSankirnJmWith().add(
							lrd.getPreYearSankirnJmWith() == null ? BigDecimal.ZERO : lrd.getPreYearSankirnJmWith()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal
							.setPreYearSankirnJmWithout(
									(landRevnueDemandTotal.getPreYearSankirnJmWithout()
											.add(lrd.getPreYearSankirnJmWithout() == null ? BigDecimal.ZERO
													: lrd.getPreYearSankirnJmWithout())).setScale(2,
															BigDecimal.ROUND_HALF_UP));

					landRevnueDemandTotal.setNetAmount((landRevnueDemandTotal.getNetAmount()
							.add(lrd.getNetAmount() == null ? BigDecimal.ZERO : lrd.getNetAmount())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setNetPending((landRevnueDemandTotal.getNetPending()
							.add(lrd.getNetPending() == null ? BigDecimal.ZERO : lrd.getNetPending())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					double assessment = Double.parseDouble(lrd.getAssessment().toString());

					if (assessment < 10) {
						lrd.setTotalJm((lrd.getJmAkrushik()).add(lrd.getJmSankirn()));
					} else {
						lrd.setTotalJm((lrd.getJmBindumala()).add(lrd.getJmDumala()).add(lrd.getJmAkrushik())
								.add(lrd.getJmSankirn()));
					}

					if (assessment < 5) {
						lrd.setTotalGp((lrd.getGpAkrushik()).add(lrd.getGpSankirn()));
					} else {
						lrd.setTotalGp((lrd.getGpBindumala()).add(lrd.getGpDumala()).add(lrd.getGpAkrushik())
								.add(lrd.getGpSankirn()));
					}

					if (assessment < 5) {
						lrd.setTotalZp((lrd.getZpAkrushik()).add(lrd.getZpSankirn()));
					} else {
						lrd.setTotalZp((lrd.getZpBindumala()).add(lrd.getZpDumala()).add(lrd.getZpAkrushik())
								.add(lrd.getZpSankirn()));
					}

					landRevnueDemandTotal.setTotalJm((landRevnueDemandTotal.getTotalJm().add(lrd.getTotalJm()))
							.setScale(2, BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setTotalGp((landRevnueDemandTotal.getTotalGp().add(lrd.getTotalGp()))
							.setScale(2, BigDecimal.ROUND_HALF_UP));
					landRevnueDemandTotal.setTotalZp((landRevnueDemandTotal.getTotalZp().add(lrd.getTotalZp()))
							.setScale(2, BigDecimal.ROUND_HALF_UP));
				}

				landRevenueDemandDaoLst = landRevenueDemandDaoLst.stream()
						.sorted(Comparator.comparing(LandRevenueDemandDao::fetchKhataNumber))
						.collect(Collectors.toList());

				landRevenueDemandDaoLst.add(landRevnueDemandTotal);

				return landRevenueDemandDaoLst;

			}
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	// for 8BDataView Optimize
	@Override
	public List<CombinedChallanLandRevenueViewDao> getForm8BDataView(String cCode, String revenueYear,
			Integer districtCode, Integer talukaCode, HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));

		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(cCode)) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(cCode)) {

			String echslave = "";

			// live
			/*if (request.getHeader("echHost").equals("10.10.248.140")) {
				echslave = CommonConstants.KokAurSlave;
			} else if (request.getHeader("echHost").equals("10.10.248.194")) {
				echslave = CommonConstants.NasNagSlave;
			} else if (request.getHeader("echHost").equals("10.10.248.195")) {
				echslave = CommonConstants.PunAmtSlave;
			}*/
			
			
			
			if (request.getHeader("echHost").equals("10.48.65.110")) {
				echslave = CommonConstants.KokAurSlave;
			} else if (request.getHeader("echHost").equals("10.48.65.110")) {
				echslave = CommonConstants.NasNagSlave;
			} else if (request.getHeader("echHost").equals("10.48.65.110")) {
				echslave = CommonConstants.PunAmtSlave;
			}
			echslave = CommonConstants.PunAmtSlave;
			
			List<CombinedChallanLandRevenueView> combinedChallanLandRevenueView ;
			System.out.println("------------------Year -"+revenueYear);
			
			System.out.println("-----------------echhost -"+request.getHeader("echHost"));
			
			
			if(revenueYear.equals(CommonConstants.currentYear))
			{
				System.out.println("True Condition------updated------------Year -"+revenueYear);
				
				System.out.println("User "+CommonConstants.echUser+" Pass -"+CommonConstants.echPassword +" VM-" +echslave +" ");
			combinedChallanLandRevenueView = combinedChallanAndLandRevenue8BRepository
					.getAll8BReportData(echslave, request.getHeader("echDbName"), request.getHeader("echSchemaName"),
							CommonConstants.echUser, CommonConstants.echPassword, cCode,
							/* revenueYear, */CommonConstants.DefaultDataPort, revenueYear);
			}
			else
			{
				String[] revSingleString1=revenueYear.split("-");
				String singString1=revSingleString1[0];
				  combinedChallanLandRevenueView = combinedChallanAndLandRevenue8BRepository
						.getAll8BReportDataPre(echslave, request.getHeader("echDbName"), request.getHeader("echSchemaName"),
								CommonConstants.echUser, CommonConstants.echPassword, cCode,
								/* revenueYear, */CommonConstants.DefaultDataPort, revenueYear,singString1);
			}

			DatabaseContextHolder.restoreCtx();
			if (combinedChallanLandRevenueView.isEmpty()) {
				throw new ResourceNotFoundException("error.form.reportdetails", messageSource, locale);
			} else {

				List<CombinedChallanLandRevenueViewDao> combinedChallanLandRevenueViewDaoLst = combinedChallanLandRevenueView
						.stream().map(ldR -> toDao8B(ldR)).collect(Collectors.toList());

				CombinedChallanLandRevenueViewDao combinedChallanLandRevenueViewTotal = new CombinedChallanLandRevenueViewDao();
				combinedChallanLandRevenueViewTotal.setKhataOwnerName("एकूण");
				combinedChallanLandRevenueViewTotal.setArea(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setAssessment(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setJmBindumala(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setZpBindumala(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setGpBindumala(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setJmDumala(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setZpDumala(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setGpDumala(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setJmAkrushik(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setZpAkrushik(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setGpAkrushik(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setJmVajasut(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setZpVajasut(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setGpVajasut(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setEducationalCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setAddlEducationalCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setAddlLandRevenue(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setEmployeeGuaranteeScheme(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setPreYearPendingGp(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingJm(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingZp(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingAddlLandRevenue(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingAddlEducationalCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingEducationalCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingEmployeeGuaranteeScheme(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearNoticeFee(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingNaCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearSankirnJmWith(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearSankirnJmWithout(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setGpSankirn(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setZpSankirn(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setJmSankirn(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setNetAmount(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setNetPending(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setTotalJm(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setTotalGp(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setTotalZp(BigDecimal.ZERO);

				for (CombinedChallanLandRevenueViewDao lrd : combinedChallanLandRevenueViewDaoLst) {

					double assessment = Double.parseDouble(lrd.getAssessment().toString());
					double totarea = Double.parseDouble(lrd.getArea().toString());

					combinedChallanLandRevenueViewTotal.setArea((combinedChallanLandRevenueViewTotal.getArea()
							.add(lrd.getArea() == null ? BigDecimal.ZERO : lrd.getArea())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setAssessment((combinedChallanLandRevenueViewTotal
							.getAssessment().add(lrd.getAssessment() == null ? BigDecimal.ZERO : lrd.getAssessment()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					
					
					
					if(combinedChallanLandRevenueViewTotal.getKhataOwnerName() == "एकूण" ) {
						combinedChallanLandRevenueViewTotal.setChallanNo0045(" ");
					}else {
						//new change for challanno 0045
						combinedChallanLandRevenueViewTotal.setChallanNo0045(lrd.getChallanNo0045());

					}
					
					//newly adde for 0045 challan no in 8k report
					//combinedChallanLandRevenueViewTotal.setChallanNo0045(lrd.getChallanNo0045());

					combinedChallanLandRevenueViewTotal
							.setZpBindumala((combinedChallanLandRevenueViewTotal.getZpBindumala()
									.add(lrd.getZpBindumala() == null ? BigDecimal.ZERO : lrd.getZpBindumala()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setGpBindumala((combinedChallanLandRevenueViewTotal.getGpBindumala()
									.add(lrd.getGpBindumala() == null ? BigDecimal.ZERO : lrd.getGpBindumala()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setJmBindumala((combinedChallanLandRevenueViewTotal.getJmBindumala()
									.add(lrd.getJmBindumala() == null ? BigDecimal.ZERO : lrd.getJmBindumala()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal.setZpDumala((combinedChallanLandRevenueViewTotal.getZpDumala()
							.add(lrd.getZpDumala() == null ? BigDecimal.ZERO : lrd.getZpDumala())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					
					combinedChallanLandRevenueViewTotal.setGpDumala((combinedChallanLandRevenueViewTotal.getGpDumala()
							.add(lrd.getGpDumala() == null ? BigDecimal.ZERO : lrd.getGpDumala())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					
					combinedChallanLandRevenueViewTotal.setJmDumala((combinedChallanLandRevenueViewTotal.getJmDumala()
							.add(lrd.getJmDumala() == null ? BigDecimal.ZERO : lrd.getJmDumala())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal.setZpAkrushik((combinedChallanLandRevenueViewTotal
							.getZpAkrushik().add(lrd.getZpAkrushik() == null ? BigDecimal.ZERO : lrd.getZpAkrushik()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setGpAkrushik((combinedChallanLandRevenueViewTotal
							.getGpAkrushik().add(lrd.getGpAkrushik() == null ? BigDecimal.ZERO : lrd.getGpAkrushik()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setJmAkrushik((combinedChallanLandRevenueViewTotal
							.getJmAkrushik().add(lrd.getJmAkrushik() == null ? BigDecimal.ZERO : lrd.getJmAkrushik()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal.setZpSankirn((combinedChallanLandRevenueViewTotal.getZpSankirn()
							.add(lrd.getZpSankirn() == null ? BigDecimal.ZERO : lrd.getZpSankirn())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setGpSankirn((combinedChallanLandRevenueViewTotal.getGpSankirn()
							.add(lrd.getGpSankirn() == null ? BigDecimal.ZERO : lrd.getGpSankirn())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setJmSankirn((combinedChallanLandRevenueViewTotal.getJmSankirn()
							.add(lrd.getJmSankirn() == null ? BigDecimal.ZERO : lrd.getJmSankirn())
							.add(lrd.getNoticeAmount() == null ? BigDecimal.ZERO : lrd.getNoticeAmount())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					// commented on 24 Jan 2025 --t
					/*
					 * if((assessment >= 5 && assessment <= 10) || totarea <= 3.00) {
					 * //System.out.println("Here For vajasut-----"+lrd.getJmBindumala()+" ddddd- "
					 * +lrd.getJmDumala() );
					 * lrd.setJmVajasut((lrd.getJmBindumala()).add(lrd.getJmDumala()));
					 * 
					 * //combinedChallanLandRevenueViewTotal.setJmVajasut
					 * ((combinedChallanLandRevenueViewTotal.getJmVajasut().add(lrd.getJmVajasut()
					 * == null ? BigDecimal.ZERO :
					 * lrd.getJmVajasut())).setScale(2,BigDecimal.ROUND_HALF_UP).add(lrd.
					 * getJmBindumala()).add(lrd.getJmDumala()) );
					 * 
					 * } else { //combinedChallanLandRevenueViewTotal.setJmVajasut
					 * ((combinedChallanLandRevenueViewTotal.getJmVajasut().add(lrd.getJmVajasut()
					 * == null ? BigDecimal.ZERO :
					 * lrd.getJmVajasut())).setScale(2,BigDecimal.ROUND_HALF_UP));
					 * 
					 * } if(assessment == 5 ) {
					 * lrd.setZpVajasut((lrd.getZpBindumala()).add(lrd.getZpDumala()));
					 * lrd.setGpVajasut((lrd.getGpBindumala()).add(lrd.getGpDumala()));
					 * 
					 * }
					 */

					combinedChallanLandRevenueViewTotal.setZpVajasut((combinedChallanLandRevenueViewTotal.getZpVajasut()
							.add(lrd.getZpVajasut() == null ? BigDecimal.ZERO : lrd.getZpVajasut())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setGpVajasut((combinedChallanLandRevenueViewTotal.getGpVajasut()
							.add(lrd.getGpVajasut() == null ? BigDecimal.ZERO : lrd.getGpVajasut())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setJmVajasut((combinedChallanLandRevenueViewTotal.getJmVajasut()
							.add(lrd.getJmVajasut() == null ? BigDecimal.ZERO : lrd.getJmVajasut())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal
							.setEducationalCess((combinedChallanLandRevenueViewTotal.getEducationalCess()
									.add(lrd.getEducationalCess() == null ? BigDecimal.ZERO : lrd.getEducationalCess()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setAddlEducationalCess(
									(combinedChallanLandRevenueViewTotal.getAddlEducationalCess()
											.add(lrd.getAddlEducationalCess() == null ? BigDecimal.ZERO
													: lrd.getAddlEducationalCess())).setScale(2,
															BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setAddlLandRevenue((combinedChallanLandRevenueViewTotal.getAddlLandRevenue()
									.add(lrd.getAddlLandRevenue() == null ? BigDecimal.ZERO : lrd.getAddlLandRevenue()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setEmployeeGuaranteeScheme(
									(combinedChallanLandRevenueViewTotal.getEmployeeGuaranteeScheme()
											.add(lrd.getEmployeeGuaranteeScheme() == null ? BigDecimal.ZERO
													: lrd.getEmployeeGuaranteeScheme())).setScale(2,
															BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal
							.setPreYearPendingGp((combinedChallanLandRevenueViewTotal.getPreYearPendingGp().add(
									lrd.getPreYearPendingGp() == null ? BigDecimal.ZERO : lrd.getPreYearPendingGp()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearPendingJm((combinedChallanLandRevenueViewTotal.getPreYearPendingJm().add(
									lrd.getPreYearPendingJm() == null ? BigDecimal.ZERO : lrd.getPreYearPendingJm()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearPendingZp((combinedChallanLandRevenueViewTotal.getPreYearPendingZp().add(
									lrd.getPreYearPendingZp() == null ? BigDecimal.ZERO : lrd.getPreYearPendingZp()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setPreYearPendingAddlLandRevenue(
							(combinedChallanLandRevenueViewTotal.getPreYearPendingAddlLandRevenue()
									.add(lrd.getPreYearPendingAddlLandRevenue() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingAddlLandRevenue())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setPreYearPendingAddlEducationalCess(
							(combinedChallanLandRevenueViewTotal.getPreYearPendingAddlEducationalCess()
									.add(lrd.getPreYearPendingAddlEducationalCess() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingAddlEducationalCess())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setPreYearPendingEducationalCess(
							(combinedChallanLandRevenueViewTotal.getPreYearPendingEducationalCess()
									.add(lrd.getPreYearPendingEducationalCess() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingEducationalCess())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setPreYearPendingEmployeeGuaranteeScheme(
							(combinedChallanLandRevenueViewTotal.getPreYearPendingEmployeeGuaranteeScheme()
									.add(lrd.getPreYearPendingEmployeeGuaranteeScheme() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingEmployeeGuaranteeScheme())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearNoticeFee((combinedChallanLandRevenueViewTotal.getPreYearNoticeFee().add(
									lrd.getPreYearNoticeFee() == null ? BigDecimal.ZERO : lrd.getPreYearNoticeFee()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearPendingNaCess(
									(combinedChallanLandRevenueViewTotal.getPreYearPendingNaCess()
											.add(lrd.getPreYearPendingNaCess() == null ? BigDecimal.ZERO
													: lrd.getPreYearPendingNaCess())).setScale(2,
															BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearSankirnJmWith(
									(combinedChallanLandRevenueViewTotal.getPreYearSankirnJmWith()
											.add(lrd.getPreYearSankirnJmWith() == null ? BigDecimal.ZERO
													: lrd.getPreYearSankirnJmWith())).setScale(2,
															BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearSankirnJmWithout(
									(combinedChallanLandRevenueViewTotal.getPreYearSankirnJmWithout()
											.add(lrd.getPreYearSankirnJmWithout() == null ? BigDecimal.ZERO
													: lrd.getPreYearSankirnJmWithout())).setScale(2,
															BigDecimal.ROUND_HALF_UP));

					// combinedChallanLandRevenueViewTotal.setNetAmount((combinedChallanLandRevenueViewTotal.getNetAmount().add(lrd.getNetAmount()
					// == null ? BigDecimal.ZERO :
					// lrd.getNetAmount())).setScale(2,BigDecimal.ROUND_HALF_UP));
					if (assessment == 5) {
						combinedChallanLandRevenueViewTotal.setNetAmount((combinedChallanLandRevenueViewTotal
								.getNetAmount().add(lrd.getNetAmount() == null ? BigDecimal.ZERO : lrd.getNetAmount())
								.subtract(lrd.getZpBindumala()).subtract(lrd.getZpDumala())
								.subtract(lrd.getGpBindumala()).subtract(lrd.getGpDumala())).setScale(2,
										BigDecimal.ROUND_HALF_UP));
					} else {
						combinedChallanLandRevenueViewTotal.setNetAmount((combinedChallanLandRevenueViewTotal
								.getNetAmount().add(lrd.getNetAmount() == null ? BigDecimal.ZERO : lrd.getNetAmount()))
										.setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					//change by shubham for 8 k if receipt no is present then show nepending
					if (lrd.getReceiptNo() == null || lrd.getReceiptNo().trim().isEmpty()) {
					    lrd.setNetPending(BigDecimal.ZERO);
					}
					combinedChallanLandRevenueViewTotal.setNetPending((combinedChallanLandRevenueViewTotal
							.getNetPending().add(lrd.getNetPending() == null ? BigDecimal.ZERO : lrd.getNetPending()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));

					// double assessment = Double.parseDouble(lrd.getAssessment().toString());

					if (assessment <= 10 || totarea <= 3.00) {
						lrd.setTotalJm((lrd.getJmAkrushik()).add(lrd.getJmSankirn()));
					} else {
						lrd.setTotalJm((lrd.getJmBindumala()).add(lrd.getJmDumala()).add(lrd.getJmAkrushik())
								.add(lrd.getJmSankirn()));
					}

					if (assessment <= 5) {
						lrd.setTotalGp((lrd.getGpAkrushik()).add(lrd.getGpSankirn()));
					} else {
						lrd.setTotalGp((lrd.getGpBindumala()).add(lrd.getGpDumala()).add(lrd.getGpAkrushik())
								.add(lrd.getGpSankirn()));
					}

					if (assessment <= 5) {
						lrd.setTotalZp((lrd.getZpAkrushik()).add(lrd.getZpSankirn()));
					} else {
						lrd.setTotalZp((lrd.getZpBindumala()).add(lrd.getZpDumala()).add(lrd.getZpAkrushik())
								.add(lrd.getZpSankirn()));
					}

					combinedChallanLandRevenueViewTotal
							.setTotalJm((combinedChallanLandRevenueViewTotal.getTotalJm().add(lrd.getTotalJm()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setTotalGp((combinedChallanLandRevenueViewTotal.getTotalGp().add(lrd.getTotalGp()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setTotalZp((combinedChallanLandRevenueViewTotal.getTotalZp().add(lrd.getTotalZp()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
				}

				combinedChallanLandRevenueViewDaoLst = combinedChallanLandRevenueViewDaoLst.stream()
						.sorted(Comparator.comparing(CombinedChallanLandRevenueViewDao::fetchKhataNumber))
						.collect(Collectors.toList());

				combinedChallanLandRevenueViewDaoLst.add(combinedChallanLandRevenueViewTotal);

				return combinedChallanLandRevenueViewDaoLst;

			}
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	// 2023-24 8b View 14-08-24
	@Override
	public List<CombinedChallanLandRevenueViewDao> getForm8BDataViewPre(String cCode, String revenueYear,
			Integer districtCode, Integer talukaCode, HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));

		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(cCode)) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(cCode)) {

			String echslave = "";

			// live
			if (request.getHeader("echHost").equals("10.10.248.140")) {
				echslave = CommonConstants.KokAurSlave;
			} else if (request.getHeader("echHost").equals("10.10.248.194")) {
				echslave = CommonConstants.NasNagSlave;
			} else if (request.getHeader("echHost").equals("10.10.248.195")) {
				echslave = CommonConstants.PunAmtSlave;
			}
			String[] revSingleString1=revenueYear.split("-");
			String singString1=revSingleString1[1];
			List<CombinedChallanLandRevenueView> combinedChallanLandRevenueView = combinedChallanAndLandRevenue8BRepository
					.getAll8BReportDataPre(echslave, request.getHeader("echDbName"), request.getHeader("echSchemaName"),
							CommonConstants.echUser, CommonConstants.echPassword, cCode,
							/* revenueYear, */CommonConstants.DefaultDataPort, revenueYear,singString1);

			DatabaseContextHolder.restoreCtx();
			if (combinedChallanLandRevenueView.isEmpty()) {
				throw new ResourceNotFoundException("error.form.reportdetails", messageSource, locale);
			} else {

				List<CombinedChallanLandRevenueViewDao> combinedChallanLandRevenueViewDaoLst = combinedChallanLandRevenueView
						.stream().map(ldR -> toDao8B(ldR)).collect(Collectors.toList());

				CombinedChallanLandRevenueViewDao combinedChallanLandRevenueViewTotal = new CombinedChallanLandRevenueViewDao();
				combinedChallanLandRevenueViewTotal.setKhataOwnerName("एकूण");
				combinedChallanLandRevenueViewTotal.setArea(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setAssessment(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setJmBindumala(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setZpBindumala(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setGpBindumala(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setJmDumala(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setZpDumala(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setGpDumala(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setJmAkrushik(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setZpAkrushik(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setGpAkrushik(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setJmVajasut(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setZpVajasut(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setGpVajasut(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setEducationalCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setAddlEducationalCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setAddlLandRevenue(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setEmployeeGuaranteeScheme(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setPreYearPendingGp(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingJm(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingZp(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingAddlLandRevenue(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingAddlEducationalCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingEducationalCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingEmployeeGuaranteeScheme(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearNoticeFee(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingNaCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearSankirnJmWith(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearSankirnJmWithout(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setGpSankirn(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setZpSankirn(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setJmSankirn(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setNetAmount(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setNetPending(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setTotalJm(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setTotalGp(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setTotalZp(BigDecimal.ZERO);

				for (CombinedChallanLandRevenueViewDao lrd : combinedChallanLandRevenueViewDaoLst) {

					double assessment = Double.parseDouble(lrd.getAssessment().toString());
					double totarea = Double.parseDouble(lrd.getArea().toString());

					combinedChallanLandRevenueViewTotal.setArea((combinedChallanLandRevenueViewTotal.getArea()
							.add(lrd.getArea() == null ? BigDecimal.ZERO : lrd.getArea())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setAssessment((combinedChallanLandRevenueViewTotal
							.getAssessment().add(lrd.getAssessment() == null ? BigDecimal.ZERO : lrd.getAssessment()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal
							.setZpBindumala((combinedChallanLandRevenueViewTotal.getZpBindumala()
									.add(lrd.getZpBindumala() == null ? BigDecimal.ZERO : lrd.getZpBindumala()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setGpBindumala((combinedChallanLandRevenueViewTotal.getGpBindumala()
									.add(lrd.getGpBindumala() == null ? BigDecimal.ZERO : lrd.getGpBindumala()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setJmBindumala((combinedChallanLandRevenueViewTotal.getJmBindumala()
									.add(lrd.getJmBindumala() == null ? BigDecimal.ZERO : lrd.getJmBindumala()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal.setZpDumala((combinedChallanLandRevenueViewTotal.getZpDumala()
							.add(lrd.getZpDumala() == null ? BigDecimal.ZERO : lrd.getZpDumala())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setGpDumala((combinedChallanLandRevenueViewTotal.getZpDumala()
							.add(lrd.getGpDumala() == null ? BigDecimal.ZERO : lrd.getGpDumala())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setJmDumala((combinedChallanLandRevenueViewTotal.getJmDumala()
							.add(lrd.getJmDumala() == null ? BigDecimal.ZERO : lrd.getJmDumala())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal.setZpAkrushik((combinedChallanLandRevenueViewTotal
							.getZpAkrushik().add(lrd.getZpAkrushik() == null ? BigDecimal.ZERO : lrd.getZpAkrushik()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setGpAkrushik((combinedChallanLandRevenueViewTotal
							.getGpAkrushik().add(lrd.getGpAkrushik() == null ? BigDecimal.ZERO : lrd.getGpAkrushik()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setJmAkrushik((combinedChallanLandRevenueViewTotal
							.getJmAkrushik().add(lrd.getJmAkrushik() == null ? BigDecimal.ZERO : lrd.getJmAkrushik()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal.setZpSankirn((combinedChallanLandRevenueViewTotal.getZpSankirn()
							.add(lrd.getZpSankirn() == null ? BigDecimal.ZERO : lrd.getZpSankirn())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setGpSankirn((combinedChallanLandRevenueViewTotal.getGpSankirn()
							.add(lrd.getGpSankirn() == null ? BigDecimal.ZERO : lrd.getGpSankirn())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setJmSankirn((combinedChallanLandRevenueViewTotal.getJmSankirn()
							.add(lrd.getJmSankirn() == null ? BigDecimal.ZERO : lrd.getJmSankirn())
							.add(lrd.getNoticeAmount() == null ? BigDecimal.ZERO : lrd.getNoticeAmount())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					if ((assessment >= 5 && assessment <= 10) || totarea <= 3.00) {
						// System.out.println("Here For vajasut-----"+lrd.getJmBindumala()+" ddddd-
						// "+lrd.getJmDumala() );
						lrd.setJmVajasut((lrd.getJmBindumala()).add(lrd.getJmDumala()));

						// combinedChallanLandRevenueViewTotal.setJmVajasut
						// ((combinedChallanLandRevenueViewTotal.getJmVajasut().add(lrd.getJmVajasut()
						// == null ? BigDecimal.ZERO :
						// lrd.getJmVajasut())).setScale(2,BigDecimal.ROUND_HALF_UP).add(lrd.getJmBindumala()).add(lrd.getJmDumala())
						// );

					} else {
						// combinedChallanLandRevenueViewTotal.setJmVajasut
						// ((combinedChallanLandRevenueViewTotal.getJmVajasut().add(lrd.getJmVajasut()
						// == null ? BigDecimal.ZERO :
						// lrd.getJmVajasut())).setScale(2,BigDecimal.ROUND_HALF_UP));

					}
					if (assessment == 5) {
						lrd.setZpVajasut((lrd.getZpBindumala()).add(lrd.getZpDumala()));
						lrd.setGpVajasut((lrd.getGpBindumala()).add(lrd.getGpDumala()));

					}
					combinedChallanLandRevenueViewTotal.setZpVajasut((combinedChallanLandRevenueViewTotal.getZpVajasut()
							.add(lrd.getZpVajasut() == null ? BigDecimal.ZERO : lrd.getZpVajasut())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setGpVajasut((combinedChallanLandRevenueViewTotal.getGpVajasut()
							.add(lrd.getGpVajasut() == null ? BigDecimal.ZERO : lrd.getGpVajasut())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setJmVajasut((combinedChallanLandRevenueViewTotal.getJmVajasut()
							.add(lrd.getJmVajasut() == null ? BigDecimal.ZERO : lrd.getJmVajasut())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal
							.setEducationalCess((combinedChallanLandRevenueViewTotal.getEducationalCess()
									.add(lrd.getEducationalCess() == null ? BigDecimal.ZERO : lrd.getEducationalCess()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setAddlEducationalCess(
									(combinedChallanLandRevenueViewTotal.getAddlEducationalCess()
											.add(lrd.getAddlEducationalCess() == null ? BigDecimal.ZERO
													: lrd.getAddlEducationalCess())).setScale(2,
															BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setAddlLandRevenue((combinedChallanLandRevenueViewTotal.getAddlLandRevenue()
									.add(lrd.getAddlLandRevenue() == null ? BigDecimal.ZERO : lrd.getAddlLandRevenue()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setEmployeeGuaranteeScheme(
									(combinedChallanLandRevenueViewTotal.getEmployeeGuaranteeScheme()
											.add(lrd.getEmployeeGuaranteeScheme() == null ? BigDecimal.ZERO
													: lrd.getEmployeeGuaranteeScheme())).setScale(2,
															BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal
							.setPreYearPendingGp((combinedChallanLandRevenueViewTotal.getPreYearPendingGp().add(
									lrd.getPreYearPendingGp() == null ? BigDecimal.ZERO : lrd.getPreYearPendingGp()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearPendingJm((combinedChallanLandRevenueViewTotal.getPreYearPendingJm().add(
									lrd.getPreYearPendingJm() == null ? BigDecimal.ZERO : lrd.getPreYearPendingJm()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearPendingZp((combinedChallanLandRevenueViewTotal.getPreYearPendingZp().add(
									lrd.getPreYearPendingZp() == null ? BigDecimal.ZERO : lrd.getPreYearPendingZp()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setPreYearPendingAddlLandRevenue(
							(combinedChallanLandRevenueViewTotal.getPreYearPendingAddlLandRevenue()
									.add(lrd.getPreYearPendingAddlLandRevenue() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingAddlLandRevenue())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setPreYearPendingAddlEducationalCess(
							(combinedChallanLandRevenueViewTotal.getPreYearPendingAddlEducationalCess()
									.add(lrd.getPreYearPendingAddlEducationalCess() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingAddlEducationalCess())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setPreYearPendingEducationalCess(
							(combinedChallanLandRevenueViewTotal.getPreYearPendingEducationalCess()
									.add(lrd.getPreYearPendingEducationalCess() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingEducationalCess())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setPreYearPendingEmployeeGuaranteeScheme(
							(combinedChallanLandRevenueViewTotal.getPreYearPendingEmployeeGuaranteeScheme()
									.add(lrd.getPreYearPendingEmployeeGuaranteeScheme() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingEmployeeGuaranteeScheme())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearNoticeFee((combinedChallanLandRevenueViewTotal.getPreYearNoticeFee().add(
									lrd.getPreYearNoticeFee() == null ? BigDecimal.ZERO : lrd.getPreYearNoticeFee()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearPendingNaCess(
									(combinedChallanLandRevenueViewTotal.getPreYearPendingNaCess()
											.add(lrd.getPreYearPendingNaCess() == null ? BigDecimal.ZERO
													: lrd.getPreYearPendingNaCess())).setScale(2,
															BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearSankirnJmWith(
									(combinedChallanLandRevenueViewTotal.getPreYearSankirnJmWith()
											.add(lrd.getPreYearSankirnJmWith() == null ? BigDecimal.ZERO
													: lrd.getPreYearSankirnJmWith())).setScale(2,
															BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearSankirnJmWithout(
									(combinedChallanLandRevenueViewTotal.getPreYearSankirnJmWithout()
											.add(lrd.getPreYearSankirnJmWithout() == null ? BigDecimal.ZERO
													: lrd.getPreYearSankirnJmWithout())).setScale(2,
															BigDecimal.ROUND_HALF_UP));

					// combinedChallanLandRevenueViewTotal.setNetAmount((combinedChallanLandRevenueViewTotal.getNetAmount().add(lrd.getNetAmount()
					// == null ? BigDecimal.ZERO :
					// lrd.getNetAmount())).setScale(2,BigDecimal.ROUND_HALF_UP));
					if (assessment == 5) {
						combinedChallanLandRevenueViewTotal.setNetAmount((combinedChallanLandRevenueViewTotal
								.getNetAmount().add(lrd.getNetAmount() == null ? BigDecimal.ZERO : lrd.getNetAmount())
								.subtract(lrd.getZpBindumala()).subtract(lrd.getZpDumala())
								.subtract(lrd.getGpBindumala()).subtract(lrd.getGpDumala())).setScale(2,
										BigDecimal.ROUND_HALF_UP));
					} else {
						combinedChallanLandRevenueViewTotal.setNetAmount((combinedChallanLandRevenueViewTotal
								.getNetAmount().add(lrd.getNetAmount() == null ? BigDecimal.ZERO : lrd.getNetAmount()))
										.setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					combinedChallanLandRevenueViewTotal.setNetPending((combinedChallanLandRevenueViewTotal
							.getNetPending().add(lrd.getNetPending() == null ? BigDecimal.ZERO : lrd.getNetPending()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));

					// double assessment = Double.parseDouble(lrd.getAssessment().toString());

					if (assessment <= 10 || totarea <= 3.00) {
						lrd.setTotalJm((lrd.getJmAkrushik()).add(lrd.getJmSankirn()));
					} else {
						lrd.setTotalJm((lrd.getJmBindumala()).add(lrd.getJmDumala()).add(lrd.getJmAkrushik())
								.add(lrd.getJmSankirn()));
					}

					if (assessment <= 5) {
						lrd.setTotalGp((lrd.getGpAkrushik()).add(lrd.getGpSankirn()));
					} else {
						lrd.setTotalGp((lrd.getGpBindumala()).add(lrd.getGpDumala()).add(lrd.getGpAkrushik())
								.add(lrd.getGpSankirn()));
					}

					if (assessment <= 5) {
						lrd.setTotalZp((lrd.getZpAkrushik()).add(lrd.getZpSankirn()));
					} else {
						lrd.setTotalZp((lrd.getZpBindumala()).add(lrd.getZpDumala()).add(lrd.getZpAkrushik())
								.add(lrd.getZpSankirn()));
					}

					combinedChallanLandRevenueViewTotal
							.setTotalJm((combinedChallanLandRevenueViewTotal.getTotalJm().add(lrd.getTotalJm()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setTotalGp((combinedChallanLandRevenueViewTotal.getTotalGp().add(lrd.getTotalGp()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setTotalZp((combinedChallanLandRevenueViewTotal.getTotalZp().add(lrd.getTotalZp()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
				}

				combinedChallanLandRevenueViewDaoLst = combinedChallanLandRevenueViewDaoLst.stream()
						.sorted(Comparator.comparing(CombinedChallanLandRevenueViewDao::fetchKhataNumber))
						.collect(Collectors.toList());

				combinedChallanLandRevenueViewDaoLst.add(combinedChallanLandRevenueViewTotal);

				return combinedChallanLandRevenueViewDaoLst;

			}
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	/* ------------------ End -------------------- */

	/*
	 * -------**************************8B and 8K
	 * DataView********************************---------------
	 */

	@Override
	public List<CombinedChallanLandRevenueViewDao> getForm8B8kDataView(String cCode, String revenueYear,
			Integer districtCode, Integer talukaCode, Integer dropdownVal, HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));

		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(cCode)) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(cCode)) {

			// System.out.println("DropdownValue : "+dropdownVal);

			if (dropdownVal == 0) {
				combinedChallanLandRevenueView = combinedChallanAndLandRevenue8BRepository.getAll8BReportData(
						request.getHeader("echHost"), request.getHeader("echDbName"),
						request.getHeader("echSchemaName"), CommonConstants.echUser, CommonConstants.echPassword, cCode,
						/* revenueYear, */CommonConstants.DefaultDataPort, revenueYear);

				// System.out.println("IntoIfListOfResult : "+combinedChallanLandRevenueView);
			} else if (dropdownVal == 1) {
				combinedChallanLandRevenueView = combinedChallanAndLandRevenue8BRepository
						.getAll8BReportDataVasuliPatra(request.getHeader("echHost"), request.getHeader("echDbName"),
								request.getHeader("echSchemaName"), CommonConstants.echUser,
								CommonConstants.echPassword, cCode, /* revenueYear, */CommonConstants.DefaultDataPort,
								revenueYear);
			} else if (dropdownVal == 2) {
				combinedChallanLandRevenueView = combinedChallanAndLandRevenue8BRepository
						.getAll8BReportDataSutKhatedar(request.getHeader("echHost"), request.getHeader("echDbName"),
								request.getHeader("echSchemaName"), CommonConstants.echUser,
								CommonConstants.echPassword, cCode, /* revenueYear, */CommonConstants.DefaultDataPort,
								revenueYear);
			} else {
				combinedChallanLandRevenueView = combinedChallanAndLandRevenue8BRepository.getAll8BReportData(
						request.getHeader("echHost"), request.getHeader("echDbName"),
						request.getHeader("echSchemaName"), CommonConstants.echUser, CommonConstants.echPassword, cCode,
						/* revenueYear, */CommonConstants.DefaultDataPort, revenueYear);
			}

//				   System.out.println("ListOfResult  : "+combinedChallanLandRevenueView);
//			System.out.println("EchHost  :"+request.getHeader("echHost"));
//			System.out.println("EchSlave  :"+request.getHeader("echSlave"));
//			

			DatabaseContextHolder.restoreCtx();
			if (combinedChallanLandRevenueView.isEmpty()) {
				throw new ResourceNotFoundException("error.form.reportdetails", messageSource, locale);
			} else {

				// List<CombinedChallanLandRevenueViewDao> landRevenueDemandDaoLst=
				// CombinedChallanLandRevenueView.stream().map(lrdR ->
				// toDao8B(lrdR)).collect(Collectors.toList());
				List<CombinedChallanLandRevenueViewDao> combinedChallanLandRevenueViewDaoLst = combinedChallanLandRevenueView
						.stream().map(ldR -> toDao8B(ldR)).collect(Collectors.toList());

				// List<List<Object>> landRevenueDemandDaoLst=
				// landRevenueDemand.stream().map(lrdR ->
				// toDao8B(lrdR)).collect(Collectors.toList());

				CombinedChallanLandRevenueViewDao combinedChallanLandRevenueViewTotal = new CombinedChallanLandRevenueViewDao();
				combinedChallanLandRevenueViewTotal.setKhataOwnerName("एकूण");
				combinedChallanLandRevenueViewTotal.setArea(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setAssessment(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setJmBindumala(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setZpBindumala(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setGpBindumala(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setJmDumala(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setZpDumala(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setGpDumala(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setJmAkrushik(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setZpAkrushik(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setGpAkrushik(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setJmVajasut(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setZpVajasut(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setGpVajasut(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setEducationalCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setAddlEducationalCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setAddlLandRevenue(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setEmployeeGuaranteeScheme(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setPreYearPendingGp(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingJm(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingZp(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingAddlLandRevenue(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingAddlEducationalCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingEducationalCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingEmployeeGuaranteeScheme(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearNoticeFee(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingNaCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearSankirnJmWith(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearSankirnJmWithout(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setGpSankirn(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setZpSankirn(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setJmSankirn(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setNetAmount(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setNetPending(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setTotalJm(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setTotalGp(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setTotalZp(BigDecimal.ZERO);

				for (CombinedChallanLandRevenueViewDao lrd : combinedChallanLandRevenueViewDaoLst) {

					double assessment = Double.parseDouble(lrd.getAssessment().toString());
					double totarea = Double.parseDouble(lrd.getArea().toString());

					// System.out.println("challano___looop"+lrd.toString());
					// System.out.println("challano___looop"+landRevenueDemandDaoLst.toString());
					combinedChallanLandRevenueViewTotal.setArea((combinedChallanLandRevenueViewTotal.getArea()
							.add(lrd.getArea() == null ? BigDecimal.ZERO : lrd.getArea())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setAssessment((combinedChallanLandRevenueViewTotal
							.getAssessment().add(lrd.getAssessment() == null ? BigDecimal.ZERO : lrd.getAssessment()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					
					if(combinedChallanLandRevenueViewTotal.getKhataOwnerName() == "एकूण" ) {
						combinedChallanLandRevenueViewTotal.setChallanNo0045(" ");
					}else {
						//new change for challanno 0045
						combinedChallanLandRevenueViewTotal.setChallanNo0045(lrd.getChallanNo0045());

					}
					
					
					combinedChallanLandRevenueViewTotal
							.setZpBindumala((combinedChallanLandRevenueViewTotal.getZpBindumala()
									.add(lrd.getZpBindumala() == null ? BigDecimal.ZERO : lrd.getZpBindumala()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setGpBindumala((combinedChallanLandRevenueViewTotal.getGpBindumala()
									.add(lrd.getGpBindumala() == null ? BigDecimal.ZERO : lrd.getGpBindumala()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setJmBindumala((combinedChallanLandRevenueViewTotal.getJmBindumala()
									.add(lrd.getJmBindumala() == null ? BigDecimal.ZERO : lrd.getJmBindumala()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal.setZpDumala((combinedChallanLandRevenueViewTotal.getZpDumala()
							.add(lrd.getZpDumala() == null ? BigDecimal.ZERO : lrd.getZpDumala())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setGpDumala((combinedChallanLandRevenueViewTotal.getGpDumala()
							.add(lrd.getGpDumala() == null ? BigDecimal.ZERO : lrd.getGpDumala())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setJmDumala((combinedChallanLandRevenueViewTotal.getJmDumala()
							.add(lrd.getJmDumala() == null ? BigDecimal.ZERO : lrd.getJmDumala())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal.setZpAkrushik((combinedChallanLandRevenueViewTotal
							.getZpAkrushik().add(lrd.getZpAkrushik() == null ? BigDecimal.ZERO : lrd.getZpAkrushik()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setGpAkrushik((combinedChallanLandRevenueViewTotal
							.getGpAkrushik().add(lrd.getGpAkrushik() == null ? BigDecimal.ZERO : lrd.getGpAkrushik()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setJmAkrushik((combinedChallanLandRevenueViewTotal
							.getJmAkrushik().add(lrd.getJmAkrushik() == null ? BigDecimal.ZERO : lrd.getJmAkrushik()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal.setZpSankirn((combinedChallanLandRevenueViewTotal.getZpSankirn()
							.add(lrd.getZpSankirn() == null ? BigDecimal.ZERO : lrd.getZpSankirn())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setGpSankirn((combinedChallanLandRevenueViewTotal.getGpSankirn()
							.add(lrd.getGpSankirn() == null ? BigDecimal.ZERO : lrd.getGpSankirn())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setJmSankirn((combinedChallanLandRevenueViewTotal.getJmSankirn()
							.add(lrd.getJmSankirn() == null ? BigDecimal.ZERO : lrd.getJmSankirn())
							.add(lrd.getNoticeAmount() == null ? BigDecimal.ZERO : lrd.getNoticeAmount())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					////
					// combinedChallanLandRevenueViewTotal.setJmVajasut
					// ((combinedChallanLandRevenueViewTotal.getJmVajasut().add(lrd.getJmVajasut()
					// == null ? BigDecimal.ZERO :
					// lrd.getJmVajasut())).setScale(2,BigDecimal.ROUND_HALF_UP));

					//if ((assessment >= 5 && assessment <= 10) || totarea <= 3.00) {
						// System.out.println("Here For vajasut-----"+lrd.getJmBindumala()+" ddddd-
						// "+lrd.getJmDumala() );
					//	lrd.setJmVajasut((lrd.getJmBindumala()).add(lrd.getJmDumala()));

						// combinedChallanLandRevenueViewTotal.setJmVajasut
						// ((combinedChallanLandRevenueViewTotal.getJmVajasut().add(lrd.getJmVajasut()
						// == null ? BigDecimal.ZERO :
						// lrd.getJmVajasut())).setScale(2,BigDecimal.ROUND_HALF_UP).add(lrd.getJmBindumala()).add(lrd.getJmDumala())
						// );

					//}

				//	if (assessment == 5) {
				//		lrd.setZpVajasut((lrd.getZpBindumala()).add(lrd.getZpDumala()));
				//		lrd.setGpVajasut((lrd.getGpBindumala()).add(lrd.getGpDumala()));

				//	}

					combinedChallanLandRevenueViewTotal.setJmVajasut((combinedChallanLandRevenueViewTotal.getJmVajasut()
							.add(lrd.getJmVajasut() == null ? BigDecimal.ZERO : lrd.getJmVajasut())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					// combinedChallanLandRevenueViewTotal.setJmVajasut((combinedChallanLandRevenueViewTotal.getJmVajasut().add(lrd.getJmVajasut().add(lrd.getJmBindumala())
					// )).setScale(2,BigDecimal.ROUND_HALF_UP));
					// System.out.println((combinedChallanLandRevenueViewTotal.getJmVajasut().add(lrd.getJmVajasut().add(lrd.getJmBindumala())
					// )).setScale(2,BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal.setZpVajasut((combinedChallanLandRevenueViewTotal.getZpVajasut()
							.add(lrd.getZpVajasut() == null ? BigDecimal.ZERO : lrd.getZpVajasut())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setGpVajasut((combinedChallanLandRevenueViewTotal.getGpVajasut()
							.add(lrd.getGpVajasut() == null ? BigDecimal.ZERO : lrd.getGpVajasut())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal
							.setEducationalCess((combinedChallanLandRevenueViewTotal.getEducationalCess()
									.add(lrd.getEducationalCess() == null ? BigDecimal.ZERO : lrd.getEducationalCess()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setAddlEducationalCess(
									(combinedChallanLandRevenueViewTotal.getAddlEducationalCess()
											.add(lrd.getAddlEducationalCess() == null ? BigDecimal.ZERO
													: lrd.getAddlEducationalCess())).setScale(2,
															BigDecimal.ROUND_HALF_UP));

					// AdditionalLandRevenue
					combinedChallanLandRevenueViewTotal
							.setAddlLandRevenue((combinedChallanLandRevenueViewTotal.getAddlLandRevenue()
									.add(lrd.getAddlLandRevenue() == null ? BigDecimal.ZERO : lrd.getAddlLandRevenue()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal
							.setEmployeeGuaranteeScheme(
									(combinedChallanLandRevenueViewTotal.getEmployeeGuaranteeScheme()
											.add(lrd.getEmployeeGuaranteeScheme() == null ? BigDecimal.ZERO
													: lrd.getEmployeeGuaranteeScheme())).setScale(2,
															BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal
							.setPreYearPendingGp((combinedChallanLandRevenueViewTotal.getPreYearPendingGp().add(
									lrd.getPreYearPendingGp() == null ? BigDecimal.ZERO : lrd.getPreYearPendingGp()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearPendingJm((combinedChallanLandRevenueViewTotal.getPreYearPendingJm().add(
									lrd.getPreYearPendingJm() == null ? BigDecimal.ZERO : lrd.getPreYearPendingJm()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearPendingZp((combinedChallanLandRevenueViewTotal.getPreYearPendingZp().add(
									lrd.getPreYearPendingZp() == null ? BigDecimal.ZERO : lrd.getPreYearPendingZp()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setPreYearPendingAddlLandRevenue(
							(combinedChallanLandRevenueViewTotal.getPreYearPendingAddlLandRevenue()
									.add(lrd.getPreYearPendingAddlLandRevenue() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingAddlLandRevenue())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setPreYearPendingAddlEducationalCess(
							(combinedChallanLandRevenueViewTotal.getPreYearPendingAddlEducationalCess()
									.add(lrd.getPreYearPendingAddlEducationalCess() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingAddlEducationalCess())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setPreYearPendingEducationalCess(
							(combinedChallanLandRevenueViewTotal.getPreYearPendingEducationalCess()
									.add(lrd.getPreYearPendingEducationalCess() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingEducationalCess())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setPreYearPendingEmployeeGuaranteeScheme(
							(combinedChallanLandRevenueViewTotal.getPreYearPendingEmployeeGuaranteeScheme()
									.add(lrd.getPreYearPendingEmployeeGuaranteeScheme() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingEmployeeGuaranteeScheme())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearNoticeFee((combinedChallanLandRevenueViewTotal.getPreYearNoticeFee().add(
									lrd.getPreYearNoticeFee() == null ? BigDecimal.ZERO : lrd.getPreYearNoticeFee()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearPendingNaCess(
									(combinedChallanLandRevenueViewTotal.getPreYearPendingNaCess()
											.add(lrd.getPreYearPendingNaCess() == null ? BigDecimal.ZERO
													: lrd.getPreYearPendingNaCess())).setScale(2,
															BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearSankirnJmWith(
									(combinedChallanLandRevenueViewTotal.getPreYearSankirnJmWith()
											.add(lrd.getPreYearSankirnJmWith() == null ? BigDecimal.ZERO
													: lrd.getPreYearSankirnJmWith())).setScale(2,
															BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearSankirnJmWithout(
									(combinedChallanLandRevenueViewTotal.getPreYearSankirnJmWithout()
											.add(lrd.getPreYearSankirnJmWithout() == null ? BigDecimal.ZERO
													: lrd.getPreYearSankirnJmWithout())).setScale(2,
															BigDecimal.ROUND_HALF_UP));

//change by shubham for 8b v k if receipctno is present then set amount else set zero 20/11/2025
					
					
					if (lrd.getReceiptNo() == null || lrd.getReceiptNo().trim().isEmpty()) {
					    lrd.setNetPending(BigDecimal.ZERO);
					}
					
					combinedChallanLandRevenueViewTotal.setNetPending((combinedChallanLandRevenueViewTotal
							.getNetPending().add(lrd.getNetPending() == null ? BigDecimal.ZERO : lrd.getNetPending()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					
					//new netAmount change [ 2 Sept 2025 ]
					combinedChallanLandRevenueViewTotal.setNetAmount((combinedChallanLandRevenueViewTotal
							.getNetAmount().add(lrd.getNetAmount() == null ? BigDecimal.ZERO : lrd.getNetAmount()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));

					if (assessment <= 10 || totarea <= 3.00) {
						lrd.setTotalJm((lrd.getJmAkrushik()).add(lrd.getJmSankirn()));

					} else {
						lrd.setTotalJm((lrd.getJmBindumala()).add(lrd.getJmDumala()).add(lrd.getJmAkrushik())
								.add(lrd.getJmSankirn()));
					}

					if (assessment <= 5) {
						lrd.setTotalGp((lrd.getGpAkrushik()).add(lrd.getGpSankirn()));
					} else {
						lrd.setTotalGp((lrd.getGpBindumala()).add(lrd.getGpDumala()).add(lrd.getGpAkrushik())
								.add(lrd.getGpSankirn()));
					}

					if (assessment <= 5) {
						lrd.setTotalZp((lrd.getZpAkrushik()).add(lrd.getZpSankirn()));
					} else {
						lrd.setTotalZp((lrd.getZpBindumala()).add(lrd.getZpDumala()).add(lrd.getZpAkrushik())
								.add(lrd.getZpSankirn()));
					}

					combinedChallanLandRevenueViewTotal
							.setTotalJm((combinedChallanLandRevenueViewTotal.getTotalJm().add(lrd.getTotalJm()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal
							.setTotalGp((combinedChallanLandRevenueViewTotal.getTotalGp().add(lrd.getTotalGp()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setTotalZp((combinedChallanLandRevenueViewTotal.getTotalZp().add(lrd.getTotalZp()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
				}

				
				combinedChallanLandRevenueViewDaoLst = combinedChallanLandRevenueViewDaoLst.stream()
						.sorted(Comparator.comparing(CombinedChallanLandRevenueViewDao::fetchKhataNumber))
						.collect(Collectors.toList());

				combinedChallanLandRevenueViewDaoLst.add(combinedChallanLandRevenueViewTotal);

				return combinedChallanLandRevenueViewDaoLst;

			}
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	/// pre
	@Override
	public List<CombinedChallanLandRevenueViewDao> getForm8B8kDataViewPre(String cCode, String revenueYear,
			Integer districtCode, Integer talukaCode, Integer dropdownVal, HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));

		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(cCode)) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(cCode)) {

			// System.out.println("DropdownValue : "+dropdownVal);
			String[] revSingleString2=revenueYear.split("-");
			String singString2=revSingleString2[1];
			if (dropdownVal == 0) {
				combinedChallanLandRevenueView = combinedChallanAndLandRevenue8BRepository.getAll8BReportDataPre(
						request.getHeader("echHost"), request.getHeader("echDbName"),
						request.getHeader("echSchemaName"), CommonConstants.echUser, CommonConstants.echPassword, cCode,
						/* revenueYear, */CommonConstants.DefaultDataPort, revenueYear,singString2);

				// System.out.println("IntoIfListOfResult : "+combinedChallanLandRevenueView);
			} else if (dropdownVal == 1) {
				combinedChallanLandRevenueView = combinedChallanAndLandRevenue8BRepository
						.getAll8BReportDataVasuliPatraPre(request.getHeader("echHost"), request.getHeader("echDbName"),
								request.getHeader("echSchemaName"), CommonConstants.echUser,
								CommonConstants.echPassword, cCode, /* revenueYear, */CommonConstants.DefaultDataPort,
								revenueYear);
			} else if (dropdownVal == 2) {
				combinedChallanLandRevenueView = combinedChallanAndLandRevenue8BRepository
						.getAll8BReportDataSutKhatedarPre(request.getHeader("echHost"), request.getHeader("echDbName"),
								request.getHeader("echSchemaName"), CommonConstants.echUser,
								CommonConstants.echPassword, cCode, /* revenueYear, */CommonConstants.DefaultDataPort,
								revenueYear);
			} else {
				String[] revSingleString=revenueYear.split("-");
				String singString=revSingleString[1];
				combinedChallanLandRevenueView = combinedChallanAndLandRevenue8BRepository.getAll8BReportDataPre(
						request.getHeader("echHost"), request.getHeader("echDbName"),
						request.getHeader("echSchemaName"), CommonConstants.echUser, CommonConstants.echPassword, cCode,
						/* revenueYear, */CommonConstants.DefaultDataPort, revenueYear,singString);
			}

//				   System.out.println("ListOfResult  : "+combinedChallanLandRevenueView);
//			System.out.println("EchHost  :"+request.getHeader("echHost"));
//			System.out.println("EchSlave  :"+request.getHeader("echSlave"));
//			

			DatabaseContextHolder.restoreCtx();
			if (combinedChallanLandRevenueView.isEmpty()) {
				throw new ResourceNotFoundException("error.form.reportdetails", messageSource, locale);
			} else {

				// List<CombinedChallanLandRevenueViewDao> landRevenueDemandDaoLst=
				// CombinedChallanLandRevenueView.stream().map(lrdR ->
				// toDao8B(lrdR)).collect(Collectors.toList());
				List<CombinedChallanLandRevenueViewDao> combinedChallanLandRevenueViewDaoLst = combinedChallanLandRevenueView
						.stream().map(ldR -> toDao8B(ldR)).collect(Collectors.toList());

				// List<List<Object>> landRevenueDemandDaoLst=
				// landRevenueDemand.stream().map(lrdR ->
				// toDao8B(lrdR)).collect(Collectors.toList());

				CombinedChallanLandRevenueViewDao combinedChallanLandRevenueViewTotal = new CombinedChallanLandRevenueViewDao();
				combinedChallanLandRevenueViewTotal.setKhataOwnerName("एकूण");
				combinedChallanLandRevenueViewTotal.setArea(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setAssessment(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setJmBindumala(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setZpBindumala(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setGpBindumala(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setJmDumala(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setZpDumala(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setGpDumala(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setJmAkrushik(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setZpAkrushik(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setGpAkrushik(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setJmVajasut(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setZpVajasut(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setGpVajasut(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setEducationalCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setAddlEducationalCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setAddlLandRevenue(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setEmployeeGuaranteeScheme(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setPreYearPendingGp(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingJm(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingZp(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingAddlLandRevenue(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingAddlEducationalCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingEducationalCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingEmployeeGuaranteeScheme(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearNoticeFee(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearPendingNaCess(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearSankirnJmWith(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setPreYearSankirnJmWithout(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setGpSankirn(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setZpSankirn(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setJmSankirn(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setNetAmount(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setNetPending(BigDecimal.ZERO);

				combinedChallanLandRevenueViewTotal.setTotalJm(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setTotalGp(BigDecimal.ZERO);
				combinedChallanLandRevenueViewTotal.setTotalZp(BigDecimal.ZERO);

				for (CombinedChallanLandRevenueViewDao lrd : combinedChallanLandRevenueViewDaoLst) {

					double assessment = Double.parseDouble(lrd.getAssessment().toString());
					double totarea = Double.parseDouble(lrd.getArea().toString());

					// System.out.println("challano___looop"+lrd.toString());
					// System.out.println("challano___looop"+landRevenueDemandDaoLst.toString());
					combinedChallanLandRevenueViewTotal.setArea((combinedChallanLandRevenueViewTotal.getArea()
							.add(lrd.getArea() == null ? BigDecimal.ZERO : lrd.getArea())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setAssessment((combinedChallanLandRevenueViewTotal
							.getAssessment().add(lrd.getAssessment() == null ? BigDecimal.ZERO : lrd.getAssessment()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal
							.setZpBindumala((combinedChallanLandRevenueViewTotal.getZpBindumala()
									.add(lrd.getZpBindumala() == null ? BigDecimal.ZERO : lrd.getZpBindumala()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setGpBindumala((combinedChallanLandRevenueViewTotal.getGpBindumala()
									.add(lrd.getGpBindumala() == null ? BigDecimal.ZERO : lrd.getGpBindumala()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setJmBindumala((combinedChallanLandRevenueViewTotal.getJmBindumala()
									.add(lrd.getJmBindumala() == null ? BigDecimal.ZERO : lrd.getJmBindumala()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal.setZpDumala((combinedChallanLandRevenueViewTotal.getZpDumala()
							.add(lrd.getZpDumala() == null ? BigDecimal.ZERO : lrd.getZpDumala())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setGpDumala((combinedChallanLandRevenueViewTotal.getZpDumala()
							.add(lrd.getGpDumala() == null ? BigDecimal.ZERO : lrd.getGpDumala())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setJmDumala((combinedChallanLandRevenueViewTotal.getJmDumala()
							.add(lrd.getJmDumala() == null ? BigDecimal.ZERO : lrd.getJmDumala())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal.setZpAkrushik((combinedChallanLandRevenueViewTotal
							.getZpAkrushik().add(lrd.getZpAkrushik() == null ? BigDecimal.ZERO : lrd.getZpAkrushik()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setGpAkrushik((combinedChallanLandRevenueViewTotal
							.getGpAkrushik().add(lrd.getGpAkrushik() == null ? BigDecimal.ZERO : lrd.getGpAkrushik()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setJmAkrushik((combinedChallanLandRevenueViewTotal
							.getJmAkrushik().add(lrd.getJmAkrushik() == null ? BigDecimal.ZERO : lrd.getJmAkrushik()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal.setZpSankirn((combinedChallanLandRevenueViewTotal.getZpSankirn()
							.add(lrd.getZpSankirn() == null ? BigDecimal.ZERO : lrd.getZpSankirn())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setGpSankirn((combinedChallanLandRevenueViewTotal.getGpSankirn()
							.add(lrd.getGpSankirn() == null ? BigDecimal.ZERO : lrd.getGpSankirn())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setJmSankirn((combinedChallanLandRevenueViewTotal.getJmSankirn()
							.add(lrd.getJmSankirn() == null ? BigDecimal.ZERO : lrd.getJmSankirn())
							.add(lrd.getNoticeAmount() == null ? BigDecimal.ZERO : lrd.getNoticeAmount())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					////
					// combinedChallanLandRevenueViewTotal.setJmVajasut
					// ((combinedChallanLandRevenueViewTotal.getJmVajasut().add(lrd.getJmVajasut()
					// == null ? BigDecimal.ZERO :
					// lrd.getJmVajasut())).setScale(2,BigDecimal.ROUND_HALF_UP));

					if ((assessment >= 5 && assessment <= 10) || totarea <= 3.00) {
						// System.out.println("Here For vajasut-----"+lrd.getJmBindumala()+" ddddd-
						// "+lrd.getJmDumala() );
						lrd.setJmVajasut((lrd.getJmBindumala()).add(lrd.getJmDumala()));

						// combinedChallanLandRevenueViewTotal.setJmVajasut
						// ((combinedChallanLandRevenueViewTotal.getJmVajasut().add(lrd.getJmVajasut()
						// == null ? BigDecimal.ZERO :
						// lrd.getJmVajasut())).setScale(2,BigDecimal.ROUND_HALF_UP).add(lrd.getJmBindumala()).add(lrd.getJmDumala())
						// );

					}

					if (assessment == 5) {
						lrd.setZpVajasut((lrd.getZpBindumala()).add(lrd.getZpDumala()));
						lrd.setGpVajasut((lrd.getGpBindumala()).add(lrd.getGpDumala()));

					}

					combinedChallanLandRevenueViewTotal.setJmVajasut((combinedChallanLandRevenueViewTotal.getJmVajasut()
							.add(lrd.getJmVajasut() == null ? BigDecimal.ZERO : lrd.getJmVajasut())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					// combinedChallanLandRevenueViewTotal.setJmVajasut((combinedChallanLandRevenueViewTotal.getJmVajasut().add(lrd.getJmVajasut().add(lrd.getJmBindumala())
					// )).setScale(2,BigDecimal.ROUND_HALF_UP));
					// System.out.println((combinedChallanLandRevenueViewTotal.getJmVajasut().add(lrd.getJmVajasut().add(lrd.getJmBindumala())
					// )).setScale(2,BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal.setZpVajasut((combinedChallanLandRevenueViewTotal.getZpVajasut()
							.add(lrd.getZpVajasut() == null ? BigDecimal.ZERO : lrd.getZpVajasut())).setScale(2,
									BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setGpVajasut((combinedChallanLandRevenueViewTotal.getGpVajasut()
							.add(lrd.getGpVajasut() == null ? BigDecimal.ZERO : lrd.getGpVajasut())).setScale(2,
									BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal
							.setEducationalCess((combinedChallanLandRevenueViewTotal.getEducationalCess()
									.add(lrd.getEducationalCess() == null ? BigDecimal.ZERO : lrd.getEducationalCess()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setAddlEducationalCess(
									(combinedChallanLandRevenueViewTotal.getAddlEducationalCess()
											.add(lrd.getAddlEducationalCess() == null ? BigDecimal.ZERO
													: lrd.getAddlEducationalCess())).setScale(2,
															BigDecimal.ROUND_HALF_UP));

					// AdditionalLandRevenue
					combinedChallanLandRevenueViewTotal
							.setAddlLandRevenue((combinedChallanLandRevenueViewTotal.getAddlLandRevenue()
									.add(lrd.getAddlLandRevenue() == null ? BigDecimal.ZERO : lrd.getAddlLandRevenue()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal
							.setEmployeeGuaranteeScheme(
									(combinedChallanLandRevenueViewTotal.getEmployeeGuaranteeScheme()
											.add(lrd.getEmployeeGuaranteeScheme() == null ? BigDecimal.ZERO
													: lrd.getEmployeeGuaranteeScheme())).setScale(2,
															BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal
							.setPreYearPendingGp((combinedChallanLandRevenueViewTotal.getPreYearPendingGp().add(
									lrd.getPreYearPendingGp() == null ? BigDecimal.ZERO : lrd.getPreYearPendingGp()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearPendingJm((combinedChallanLandRevenueViewTotal.getPreYearPendingJm().add(
									lrd.getPreYearPendingJm() == null ? BigDecimal.ZERO : lrd.getPreYearPendingJm()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearPendingZp((combinedChallanLandRevenueViewTotal.getPreYearPendingZp().add(
									lrd.getPreYearPendingZp() == null ? BigDecimal.ZERO : lrd.getPreYearPendingZp()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setPreYearPendingAddlLandRevenue(
							(combinedChallanLandRevenueViewTotal.getPreYearPendingAddlLandRevenue()
									.add(lrd.getPreYearPendingAddlLandRevenue() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingAddlLandRevenue())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setPreYearPendingAddlEducationalCess(
							(combinedChallanLandRevenueViewTotal.getPreYearPendingAddlEducationalCess()
									.add(lrd.getPreYearPendingAddlEducationalCess() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingAddlEducationalCess())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setPreYearPendingEducationalCess(
							(combinedChallanLandRevenueViewTotal.getPreYearPendingEducationalCess()
									.add(lrd.getPreYearPendingEducationalCess() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingEducationalCess())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal.setPreYearPendingEmployeeGuaranteeScheme(
							(combinedChallanLandRevenueViewTotal.getPreYearPendingEmployeeGuaranteeScheme()
									.add(lrd.getPreYearPendingEmployeeGuaranteeScheme() == null ? BigDecimal.ZERO
											: lrd.getPreYearPendingEmployeeGuaranteeScheme())).setScale(2,
													BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearNoticeFee((combinedChallanLandRevenueViewTotal.getPreYearNoticeFee().add(
									lrd.getPreYearNoticeFee() == null ? BigDecimal.ZERO : lrd.getPreYearNoticeFee()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearPendingNaCess(
									(combinedChallanLandRevenueViewTotal.getPreYearPendingNaCess()
											.add(lrd.getPreYearPendingNaCess() == null ? BigDecimal.ZERO
													: lrd.getPreYearPendingNaCess())).setScale(2,
															BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearSankirnJmWith(
									(combinedChallanLandRevenueViewTotal.getPreYearSankirnJmWith()
											.add(lrd.getPreYearSankirnJmWith() == null ? BigDecimal.ZERO
													: lrd.getPreYearSankirnJmWith())).setScale(2,
															BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setPreYearSankirnJmWithout(
									(combinedChallanLandRevenueViewTotal.getPreYearSankirnJmWithout()
											.add(lrd.getPreYearSankirnJmWithout() == null ? BigDecimal.ZERO
													: lrd.getPreYearSankirnJmWithout())).setScale(2,
															BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal.setNetPending((combinedChallanLandRevenueViewTotal
							.getNetPending().add(lrd.getNetPending() == null ? BigDecimal.ZERO : lrd.getNetPending()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));

					if (assessment <= 10 || totarea <= 3.00) {
						lrd.setTotalJm((lrd.getJmAkrushik()).add(lrd.getJmSankirn()));

					} else {
						lrd.setTotalJm((lrd.getJmBindumala()).add(lrd.getJmDumala()).add(lrd.getJmAkrushik())
								.add(lrd.getJmSankirn()));
					}

					if (assessment <= 5) {
						lrd.setTotalGp((lrd.getGpAkrushik()).add(lrd.getGpSankirn()));
					} else {
						lrd.setTotalGp((lrd.getGpBindumala()).add(lrd.getGpDumala()).add(lrd.getGpAkrushik())
								.add(lrd.getGpSankirn()));
					}

					if (assessment <= 5) {
						lrd.setTotalZp((lrd.getZpAkrushik()).add(lrd.getZpSankirn()));
					} else {
						lrd.setTotalZp((lrd.getZpBindumala()).add(lrd.getZpDumala()).add(lrd.getZpAkrushik())
								.add(lrd.getZpSankirn()));
					}

					combinedChallanLandRevenueViewTotal
							.setTotalJm((combinedChallanLandRevenueViewTotal.getTotalJm().add(lrd.getTotalJm()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));

					combinedChallanLandRevenueViewTotal
							.setTotalGp((combinedChallanLandRevenueViewTotal.getTotalGp().add(lrd.getTotalGp()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					combinedChallanLandRevenueViewTotal
							.setTotalZp((combinedChallanLandRevenueViewTotal.getTotalZp().add(lrd.getTotalZp()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
				}

				combinedChallanLandRevenueViewDaoLst = combinedChallanLandRevenueViewDaoLst.stream()
						.sorted(Comparator.comparing(CombinedChallanLandRevenueViewDao::fetchKhataNumber))
						.collect(Collectors.toList());

				combinedChallanLandRevenueViewDaoLst.add(combinedChallanLandRevenueViewTotal);

				return combinedChallanLandRevenueViewDaoLst;

			}
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	/*
	 * -------**************************8B and 8K
	 * DataView********************************---------------
	 */

	// 8B toDao Method
	public CombinedChallanLandRevenueViewDao toDao8B(CombinedChallanLandRevenueView ldR) {
		CombinedChallanLandRevenueViewDao combinedChallanLandRevenueViewDao = new CombinedChallanLandRevenueViewDao();
		BeanUtils.copyProperties(ldR, combinedChallanLandRevenueViewDao);
		BigDecimal sankirn = ldR.getZpSankirn().add(ldR.getJmSankirn()).add(ldR.getGpSankirn());
		BigDecimal localCess = ldR.getZpSankirn().add(ldR.getGpSankirn()).add(ldR.getGpSankirn());
		BigDecimal otherLocalCess = sankirn.subtract(localCess);
		combinedChallanLandRevenueViewDao.setLocalCess(localCess);
		combinedChallanLandRevenueViewDao.setOtherLocalCess(otherLocalCess);
		combinedChallanLandRevenueViewDao.setSankirn(sankirn.toString());

		return combinedChallanLandRevenueViewDao;
	}

	/* ---------------End toDao 8B ------------------ */

	/* Report 8D old method 10 March 2025
	 * @Override
	public Response getAllForm8DReportDetails(Integer districtCode, Integer talukaCode, String cCode,
			String revenueYear, HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(cCode)) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(cCode)) {
			List<Object[]> listlandRevenueDemand = landRevenueDemandRepository.find8DBycCodeAndReceiptNoIsNotNull(
					request.getHeader("echHost"), request.getHeader("echDbName"), request.getHeader("echSchemaName"),
					CommonConstants.echUser, CommonConstants.echPassword, cCode, revenueYear);
			DatabaseContextHolder.restoreCtx();
			if (listlandRevenueDemand.isEmpty()) {
				throw new ResourceNotFoundException("error.form.reportdetails", messageSource, locale);
			} else {
				@SuppressWarnings("unused")
				DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
				Response form8DResponse = new Response();
				form8DResponse.setLandRevenueDemandData(listlandRevenueDemand.stream().map((object) -> {
					LandRevenueDemandDao landRevenueDemandDao = new LandRevenueDemandDao();
					// landRevenueDemandDao.setId(Long.valueOf(object[0].toString()));
					landRevenueDemandDao.setReceiptDateForReport(object[0].toString());
					landRevenueDemandDao.setReceiptNo(object[1] == null ? "" : object[1].toString());
					landRevenueDemandDao.setNetAmount(BigDecimal.valueOf(Double.valueOf(object[2].toString())));
					landRevenueDemandDao.setChallanDateForReport(object[3] == null ? "" : object[3].toString());
					landRevenueDemandDao.setChallanNo(object[4] == null ? "" : object[4].toString());
					landRevenueDemandDao.setTotalAmount(
							BigDecimal.valueOf(Double.valueOf(object[5] == null ? "0" : object[5].toString())));
					landRevenueDemandDao.setStatus(object[6] == null ? "" : object[6].toString());
					landRevenueDemandDao.setChallanNo0045(object[7] == null ? "" : object[7].toString());
					return landRevenueDemandDao;
				}).collect(Collectors.toList()));
				return form8DResponse;
			}
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}*/
	
	
	// Report 8D new method after update Online payment 10 March 2025
	@Override
	public Response getAllForm8DReportDetails(Integer districtCode, Integer talukaCode, String cCode,String revenueYear,HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<Object[]> ccodeslist=khataMergeRepository.getccodeDetails(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				request.getHeader("servarthId"));
		Response response =new  Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km=new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));
		
		
		String ccodes=null;
		for(KhataMergeDao r : response.getKhataMergeData()) {
			if(r.getcCode().equals(cCode)) {
			ccodes=r.getcCode();
			break;
			}else{
				ccodes="0";
				}}
		
		if(ccodes.equals(cCode)) {	
		List<Object[]> listlandRevenueDemand = landRevenueDemandRepository.
				find8DBycCodeAndReceiptNoIsNotNull
				(
						request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
						cCode,revenueYear);
		DatabaseContextHolder.restoreCtx();
		if(listlandRevenueDemand.isEmpty()) {
			throw new ResourceNotFoundException("error.form.reportdetails",messageSource,locale);
		}else {
			@SuppressWarnings("unused")
			DateTimeFormatter formatter= DateTimeFormatter.ISO_LOCAL_DATE_TIME;
			Response form8DResponse =new  Response();
			form8DResponse.setLandRevenueDemandData(listlandRevenueDemand.stream().map((object) -> {
				LandRevenueDemandDao landRevenueDemandDao=new LandRevenueDemandDao();
				//					     landRevenueDemandDao.setId(Long.valueOf(object[0].toString()));
				landRevenueDemandDao.setReceiptDateForReport(object[0].toString());
				landRevenueDemandDao.setReceiptNo(object[1] == null ? "" : object[1].toString() );
				landRevenueDemandDao.setNetAmount(BigDecimal.valueOf(Double.valueOf(object[2].toString())));
				landRevenueDemandDao.setChallanDateForReport(object[3] == null ? "" : object[3].toString());
				landRevenueDemandDao.setChallanNo(object[4] == null ? "" : object[4].toString() );
				landRevenueDemandDao.setTotalAmount(BigDecimal.valueOf(Double.valueOf(object[5] == null ? "0" : object[5].toString())));
				landRevenueDemandDao.setStatus(object[6]==null ? "" : object[6].toString());
				landRevenueDemandDao.setChallanNo0045(object[7]==null ? "" : object[7].toString());
				landRevenueDemandDao.setBankReceiptNumber(object[8]==null ? "" : object[8].toString());
				landRevenueDemandDao.setBankReceiptNumber0045(object[9]==null ? "" : object[9].toString());
				landRevenueDemandDao.setModeOfPayment(object[10]==null ? "" : object[10].toString());
				landRevenueDemandDao.setGrasStatus0029(object[11]==null ? "" : object[11].toString());
				landRevenueDemandDao.setGrasStatus0045(object[12]==null ? "" : object[12].toString());
				return landRevenueDemandDao;
			}).collect(Collectors.toList()));
			return form8DResponse;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}
	// Report 8D new method after update Online payment

	// pre
	@Override
	public Response getAllForm8DReportDetailsPre(Integer districtCode, Integer talukaCode, String cCode,
			String revenueYear, HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(cCode)) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(cCode)) {
			List<Object[]> listlandRevenueDemand = landRevenueDemandRepository.find8DBycCodeAndReceiptNoIsNotNullPre(
					request.getHeader("echHost"), request.getHeader("echDbName"), request.getHeader("echSchemaName"),
					CommonConstants.echUser, CommonConstants.echPassword, cCode, revenueYear);
			DatabaseContextHolder.restoreCtx();
			if (listlandRevenueDemand.isEmpty()) {
				throw new ResourceNotFoundException("error.form.reportdetails", messageSource, locale);
			} else {
				@SuppressWarnings("unused")
				DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
				Response form8DResponse = new Response();
				form8DResponse.setLandRevenueDemandData(listlandRevenueDemand.stream().map((object) -> {
					LandRevenueDemandDao landRevenueDemandDao = new LandRevenueDemandDao();
					// landRevenueDemandDao.setId(Long.valueOf(object[0].toString()));
					landRevenueDemandDao.setReceiptDateForReport(object[0].toString());
					landRevenueDemandDao.setReceiptNo(object[1] == null ? "" : object[1].toString());
					landRevenueDemandDao.setNetAmount(BigDecimal.valueOf(Double.valueOf(object[2].toString())));
					landRevenueDemandDao.setChallanDateForReport(object[3] == null ? "" : object[3].toString());
					landRevenueDemandDao.setChallanNo(object[4] == null ? "" : object[4].toString());
					landRevenueDemandDao.setTotalAmount(
							BigDecimal.valueOf(Double.valueOf(object[5] == null ? "0" : object[5].toString())));
					landRevenueDemandDao.setStatus(object[6] == null ? "" : object[6].toString());
					landRevenueDemandDao.setChallanNo0045(object[7] == null ? "" : object[7].toString());
					return landRevenueDemandDao;
				}).collect(Collectors.toList()));
				return form8DResponse;
			}
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	public List<Form17NoKaJaPa> getForm17KaJaPaDetails(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request) {
		List<Object[]> form17NoKaJaPaLst = form17NoKaJaPaRepository.findAllBycCodeAndRevenueYearAndActiveFlag(
				landRevenueDemandDao.getCCode(), landRevenueDemandDao.getRevenueYear(), 'Y');
		return form17NoKaJaPaLst.stream().map(r -> {
			Form17NoKaJaPa form17NoKaJaPa = new Form17NoKaJaPa();
			form17NoKaJaPa.setKhataNo(r[0].toString());
			form17NoKaJaPa.setPersonLiable(r[1].toString());
			form17NoKaJaPa.setAreaAffected(
					r[2] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(r[2].toString())));
			form17NoKaJaPa.setAssessment(
					r[3] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(r[3].toString())));
			form17NoKaJaPa.setAmountOfJm(
					r[4] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(r[4].toString())));
			form17NoKaJaPa.setAmountOfGp(
					r[5] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(r[5].toString())));
			form17NoKaJaPa.setAmountOfZp(
					r[6] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(r[6].toString())));
			return form17NoKaJaPa;
		}).collect(Collectors.toList());
	}

	public Response getAkrushakRateRural(LandRevenueDemandDao landRevenueDemandDao, HttpServletRequest request) {
		List<Object[]> AkrushakRateRural = landRevenueDemandRepository.findAkrushikRateforRural(
				request.getHeader("mhrHost"), request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"),
				CommonConstants.mhrorUser, CommonConstants.mhrorPassword, landRevenueDemandDao.getCCode());
		Response response = new Response();
		response.setAkrushakRate(AkrushakRateRural.stream().map(r -> {
			AkrushakRateDao akrushakRateDao = new AkrushakRateDao();
			akrushakRateDao.setCcode(r[0] == null ? "" : r[0].toString());
			akrushakRateDao.setRate(r[1] == null ? "" : r[1].toString());
			String rate = akrushakRateDao.getRate();
			String finalrate = rate.replace('p', ' ');
			double area = Double.parseDouble(finalrate);
			double areaInruppes = area * 0.01;
			akrushakRateDao.setRate(Double.valueOf(areaInruppes).toString().trim());
			return akrushakRateDao;
		}).collect(Collectors.toList()));
		return response;
	}

	public Response getAkrushakRateNapaAndManapa(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request) {
		List<Object[]> akrushakRateRural = landRevenueDemandRepository.findAkrushikRateforNaPaAndManapa(
				request.getHeader("mhrHost"), request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"),
				CommonConstants.mhrorUser, CommonConstants.mhrorPassword, landRevenueDemandDao.getCCode());
		Response response = new Response();
		response.setAkrushakRate(akrushakRateRural.stream().map(r -> {
			AkrushakRateDao akrushakRateDao = new AkrushakRateDao();
			akrushakRateDao.setKhataNo(r[0].toString());
			akrushakRateDao.setKhatedarName(r[1].toString());
			akrushakRateDao.setArea(r[2].toString());
			akrushakRateDao.setAssessment(r[3].toString());
			akrushakRateDao.setUnit("RSQM");
			akrushakRateDao.setRate(r[4] == null ? "0.10" : r[4].toString());
			return akrushakRateDao;
		}).collect(Collectors.toList()));
		return response;
	}

	public Response getForm2PrayojanType(LandRevenueDemandDao landRevenueDemandDao, HttpServletRequest request) {
		List<Object[]> form2PrayojanType = landRevenueDemandRepository.getForm2PrayojanType(
				request.getHeader("mhrHost"), request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"),
				CommonConstants.mhrorUser, CommonConstants.mhrorPassword, request.getHeader("echHost"),
				request.getHeader("echDbName"), request.getHeader("echSchemaName"), CommonConstants.echUser,
				CommonConstants.echPassword, landRevenueDemandDao.getCCode());
		Response response = new Response();
		response.setAkrushakRate(form2PrayojanType.stream().map(r -> {
			AkrushakRateDao akrushakRateDao = new AkrushakRateDao();
			akrushakRateDao.setCcode(r[1].toString());
			akrushakRateDao.setKhataNo(r[0] == null ? "0" : r[0].toString());
			akrushakRateDao.setSurveyHissaNo(r[2] == null ? "" : r[2].toString());
			akrushakRateDao.setPrayojanType(r[3] == null ? "" : r[3].toString());
			akrushakRateDao.setRate(r[4] == null ? "1" : r[4].toString());
			return akrushakRateDao;
		}).collect(Collectors.toList()));
		return response;
	}

	public List<KhataMerge> getKhataMergeDetails(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request) {
		System.out.println("RevenueYearKhataMergeDetail------"+landRevenueDemandDao.getRevenueYear());
		return khataMergeRepository.getMergeDetails(landRevenueDemandDao.getCCode(), 'Y',
				landRevenueDemandDao.getRevenueYear());
	}

	public List<KhataMergeDtl> getKhataMergeDtlDetails(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request) {
		System.out.println("KhataMergeDemandGeneration RevenueYear--------:"+landRevenueDemandDao.getRevenueYear());
		return khataMergeDtlRepository.getMergeDtlDetails(landRevenueDemandDao.getCCode(), 'Y',
				landRevenueDemandDao.getRevenueYear());
	}

	private List<AdditionalLandRevenue> getAddlLandRevenueDetails(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request) {
		return additionalLandRevenueRepository.findBycCodeAndRevenueYearAndActiveFlagAndApplicableForDemand(
				landRevenueDemandDao.getCCode(), landRevenueDemandDao.getRevenueYear(), 'Y', 'Y');
	}

	private List<LandRevenueDemandDao> getAddlLandRevenueDetailsOld(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request) {

		List<LandRevenueDemandDao> lstLRAddl = null;

		List<AdditionalLandRevenue> addlAdditionalLandRevenueLst = additionalLandRevenueRepository
				.findBycCodeAndRevenueYearAndActiveFlagAndApplicableForDemand(landRevenueDemandDao.getCCode(),
						landRevenueDemandDao.getRevenueYear(), 'Y', 'Y');

		if (addlAdditionalLandRevenueLst != null) {
			lstLRAddl = addlAdditionalLandRevenueLst.stream().map(addlR -> {

				LandRevenueDemandDao lrDao = new LandRevenueDemandDao();

				lrDao.setRevenueYear(landRevenueDemandDao.getRevenueYear());
				lrDao.setDistrictCode(landRevenueDemandDao.getDistrictCode());
				lrDao.setTalukaCode(landRevenueDemandDao.getTalukaCode());
				lrDao.setCCode(landRevenueDemandDao.getCCode());

				lrDao.setKhataNo(addlR.getKhataNo());
				lrDao.setKhataOwnerName(addlR.getKhataOwnerName());

				lrDao.setArea(landRevenueDemandDao.getArea());
				lrDao.setAssessment(landRevenueDemandDao.getAssessment());

				lrDao.setSurveyHissaNo("");
				lrDao.setUom("");

				lrDao.setJmBindumala(BigDecimal.ZERO);
				lrDao.setZpBindumala(BigDecimal.ZERO);
				lrDao.setGpBindumala(BigDecimal.ZERO);

				lrDao.setJmDumala(BigDecimal.ZERO);
				lrDao.setZpDumala(BigDecimal.ZERO);
				lrDao.setGpDumala(BigDecimal.ZERO);

				lrDao.setJmAkrushik(BigDecimal.ZERO);
				lrDao.setZpAkrushik(BigDecimal.ZERO);
				lrDao.setGpAkrushik(BigDecimal.ZERO);

				lrDao.setJmVajasut(BigDecimal.ZERO);
				lrDao.setZpVajasut(BigDecimal.ZERO);
				lrDao.setGpVajasut(BigDecimal.ZERO);

				lrDao.setEducationalCess(BigDecimal.ZERO);
				lrDao.setAddlEducationalCess(BigDecimal.ZERO);

				lrDao.setEmployeeGuaranteeScheme(BigDecimal.ZERO);

				lrDao.setAddlLandRevenue(addlR.getAssessment().setScale(0, BigDecimal.ROUND_HALF_UP));

				lrDao.setPreYearPendingGp(BigDecimal.ZERO);
				lrDao.setPreYearPendingJm(BigDecimal.ZERO);
				lrDao.setPreYearPendingZp(BigDecimal.ZERO);
				lrDao.setPreYearPendingAddlLandRevenue(BigDecimal.ZERO);
				lrDao.setPreYearPendingAddlEducationalCess(BigDecimal.ZERO);
				lrDao.setPreYearPendingEducationalCess(BigDecimal.ZERO);
				lrDao.setPreYearPendingEmployeeGuaranteeScheme(BigDecimal.ZERO);
				lrDao.setPreYearNoticeFee(BigDecimal.ZERO);
				lrDao.setPreYearPendingNaCess(BigDecimal.ZERO);
				lrDao.setPreYearSankirnJmWith(BigDecimal.ZERO);
				lrDao.setPreYearSankirnJmWithout(BigDecimal.ZERO);

				lrDao.setGpSankirn(BigDecimal.ZERO);
				lrDao.setZpSankirn(BigDecimal.ZERO);
				lrDao.setJmSankirn(BigDecimal.ZERO);

				lrDao.setNetAmount((lrDao.getAddlLandRevenue()).setScale(0, BigDecimal.ROUND_HALF_UP));
				lrDao.setNetPending((lrDao.getAddlLandRevenue()).setScale(0, BigDecimal.ROUND_HALF_UP));

				lrDao.setActiveFlag('Y');

				return lrDao;
			}).collect(Collectors.toList());
		} // Addl land Revenue
		return lstLRAddl;
	}

	@Override
	public Response getLrDtlByKhataNoAndKhataOwnerName(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {

		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(landRevenueDemandDao.getCCode())) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}
		String revYearVal=landRevenueDemandDao.getRevenueYear();
		String[] revSingleStringrep=revYearVal.split("-");
		String singStringrep=revSingleStringrep[0];
		System.out.println("revYearVal for reciept print----------------: "+revYearVal);
		if (ccodes.equals(landRevenueDemandDao.getCCode())) {
			List<Object[]> landRevenueDemand = new ArrayList<>();
			if (landRevenueDemandDao.getKhataOwnerName() != null) {
				if(revYearVal.equals(CommonConstants.currentYear))
				{
					
					System.out.println("-----------------echhost -"+request.getHeader("echHost"));
					
					
						
						
						System.out.println("User "+CommonConstants.echUser+" Pass -"+CommonConstants.echPassword);

					System.out.println("-----------------Current Year Namewise -: "+revYearVal);
					landRevenueDemand = landRevenueDemandRepository.findByKhataOwnerName(request.getHeader("echHost"),
						request.getHeader("echDbName"), request.getHeader("echSchemaName"), CommonConstants.echUser,
						CommonConstants.echPassword, landRevenueDemandDao.getCCode(),
						landRevenueDemandDao.getKhataOwnerName(), landRevenueDemandDao.getRevenueYear());
				}
				else 
				{
					System.out.println("-----------------Prev  Year Namewise -: "+revYearVal);
					landRevenueDemand = landRevenueDemandRepository.findByKhataOwnerNameDynamic(request.getHeader("echHost"),
							request.getHeader("echDbName"), request.getHeader("echSchemaName"), CommonConstants.echUser,
							CommonConstants.echPassword, landRevenueDemandDao.getCCode(),
							landRevenueDemandDao.getKhataOwnerName(), landRevenueDemandDao.getRevenueYear(), singStringrep );
				}
			} else {
				
				System.out.println("out side try ECHHOST for reciept print----------------: "+request.getHeader("echHost"));
				System.out.println("out side try  CCODE for reciept print----------------: "+landRevenueDemandDao.getCCode());
				System.out.println("out side try KHATA_NO for reciept print----------------: "+landRevenueDemandDao.getKhataNo());
				System.out.println("out side try REVENUE_YEAR for reciept print----------------: "+landRevenueDemandDao.getRevenueYear());
				
				
				try {
					
					System.out.println("ECHHOST for reciept print----------------: "+request.getHeader("echHost"));
					System.out.println("CCODE for reciept print----------------: "+landRevenueDemandDao.getCCode());
					System.out.println("KHATA_NO for reciept print----------------: "+landRevenueDemandDao.getKhataNo());
					System.out.println("REVENUE_YEAR for reciept print----------------: "+landRevenueDemandDao.getRevenueYear());

					if(revYearVal.equals(CommonConstants.currentYear))
					{
					landRevenueDemand = landRevenueDemandRepository.findByKhataNo(request.getHeader("echHost"),
							request.getHeader("echDbName"), request.getHeader("echSchemaName"), CommonConstants.echUser,
							CommonConstants.echPassword, landRevenueDemandDao.getCCode(), landRevenueDemandDao.getKhataNo(),
							landRevenueDemandDao.getRevenueYear());
					}
					else 
					{
						landRevenueDemand = landRevenueDemandRepository.findByKhataNoDynamic(request.getHeader("echHost"),
								request.getHeader("echDbName"), request.getHeader("echSchemaName"), CommonConstants.echUser,
								CommonConstants.echPassword, landRevenueDemandDao.getCCode(), landRevenueDemandDao.getKhataNo(),
								landRevenueDemandDao.getRevenueYear(),singStringrep);
					}
					
				}catch(Exception e){
					System.out.println("Error at Receipt/Receive Print :"+ e.getMessage());
				}
				
				
			}
			DatabaseContextHolder.restoreCtx();
			if (landRevenueDemand.isEmpty()) {
				throw new ResourceNotFoundException("error.form.getData", messageSource, locale);
			}
			
			Response landRevenueDemandResponse = new Response();
			
			try {
				
				landRevenueDemandResponse.setLandRevenueDemandData(landRevenueDemand.stream().map((object) -> {

					LandRevenueDemandDao lrd = new LandRevenueDemandDao();

					double assessment = (Double.valueOf(object[3].toString()));
					double totarea = (Double.valueOf(object[4].toString()));
					lrd.setKhataNo(object[0].toString());
					lrd.setKhataOwnerName(object[1].toString());
					lrd.setCCode(object[2].toString());
					lrd.setAssessment(BigDecimal.valueOf(Double.valueOf(object[3].toString())));
					lrd.setArea(BigDecimal.valueOf(Double.valueOf(object[4].toString())));
					lrd.setSurveyHissaNo(object[5] == null ? "" : object[5].toString());
					lrd.setUom(object[6] == null ? "" : object[6].toString());

					// old condition 20 dec 2024
					/*
					 * if((assessment >= 5 && assessment <= 10) || totarea <= 3.00) {
					 * lrd.setJmBindumala(BigDecimal.ZERO ); lrd.setJmDumala(BigDecimal.ZERO); }else
					 * {
					 * lrd.setJmBindumala(BigDecimal.valueOf(Double.valueOf(object[7].toString())));
					 * lrd.setJmDumala(BigDecimal.valueOf(Double.valueOf(object[10].toString()))); }
					 */

					// condition changed on 20 Dec 2024 -- tushar
					lrd.setJmBindumala(BigDecimal.valueOf(Double.valueOf(object[7].toString())));
					lrd.setJmDumala(BigDecimal.valueOf(Double.valueOf(object[10].toString())));

					// lrd.setJmBindumala(BigDecimal.valueOf(Double.valueOf(object[7].toString())));
					lrd.setZpBindumala(BigDecimal.valueOf(Double.valueOf(object[8].toString())));
					lrd.setGpBindumala(BigDecimal.valueOf(Double.valueOf(object[9].toString())));

					// lrd.setJmDumala(BigDecimal.valueOf(Double.valueOf(object[10].toString())));
					lrd.setZpDumala(BigDecimal.valueOf(Double.valueOf(object[11].toString())));
					lrd.setGpDumala(BigDecimal.valueOf(Double.valueOf(object[12].toString())));
					lrd.setJmAkrushik(BigDecimal.valueOf(Double.valueOf(object[13].toString())));
					lrd.setZpAkrushik(BigDecimal.valueOf(Double.valueOf(object[14].toString())));
					lrd.setGpAkrushik(BigDecimal.valueOf(Double.valueOf(object[15].toString())));
					lrd.setJmSankirn(BigDecimal.valueOf(Double.valueOf(object[16].toString())));
					lrd.setZpSankirn(BigDecimal.valueOf(Double.valueOf(object[17].toString())));
					lrd.setGpSankirn(BigDecimal.valueOf(Double.valueOf(object[18].toString())));
					lrd.setJmVajasut(BigDecimal.valueOf(Double.valueOf(object[19].toString())));
					lrd.setZpVajasut(BigDecimal.valueOf(Double.valueOf(object[20].toString())));
					lrd.setGpVajasut(BigDecimal.valueOf(Double.valueOf(object[21].toString())));
					lrd.setAddlLandRevenue(BigDecimal.valueOf(Double.valueOf(object[22].toString())));
					lrd.setEducationalCess(BigDecimal.valueOf(Double.valueOf(object[23].toString())));
					lrd.setAddlEducationalCess(BigDecimal.valueOf(Double.valueOf(object[24].toString())));
					lrd.setEmployeeGuaranteeScheme(BigDecimal.valueOf(Double.valueOf(object[25].toString())));
					lrd.setPreYearPendingJm(BigDecimal.valueOf(Double.valueOf(object[26].toString())));
					lrd.setPreYearPendingGp(BigDecimal.valueOf(Double.valueOf(object[27].toString())));
					lrd.setPreYearPendingZp(BigDecimal.valueOf(Double.valueOf(object[28].toString())));
					lrd.setPreYearPendingAddlLandRevenue(BigDecimal.valueOf(Double.valueOf(object[29].toString())));
					lrd.setPreYearPendingEducationalCess(BigDecimal.valueOf(Double.valueOf(object[30].toString())));
					lrd.setPreYearPendingAddlEducationalCess(BigDecimal.valueOf(Double.valueOf(object[31].toString())));
					lrd.setPreYearPendingEmployeeGuaranteeScheme(BigDecimal.valueOf(Double.valueOf(object[32].toString())));
					lrd.setNetAmount(BigDecimal.valueOf(Double.valueOf(object[33].toString())));
					lrd.setNetReceived(object[34] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[34].toString())));
					lrd.setNetPending(object[35] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[35].toString())));
					lrd.setDateOfForReceipt(object[36] == null ? "" : object[36].toString());
					lrd.setNoticeAmount(object[37] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[37].toString())));
					lrd.setSurveyHissaNo(object[38].toString());
					lrd.setPreYearNoticeFee(object[39] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[39].toString())));
					lrd.setPreYearPendingNaCess(object[40] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[40].toString())));
					lrd.setPreYearSankirnJmWith(object[41] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[41].toString())));
					lrd.setPreYearSankirnJmWithout(object[42] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[42].toString())));
					lrd.setMakhtaKhataNo(object[43] == null ? "" : object[43].toString());
					lrd.setNameofTenent(object[44] == null ? "" : object[44].toString());
					lrd.setId(Long.valueOf(object[45].toString()));
					BigDecimal sankirn = ((BigDecimal.valueOf(Double.valueOf(object[16].toString())))
							.add(BigDecimal.valueOf(Double.valueOf(object[17].toString())))
							.add(BigDecimal.valueOf(Double.valueOf(object[18].toString()))));
					lrd.setLocalCess((BigDecimal.valueOf(Double.valueOf(object[17].toString())))
							.add(BigDecimal.valueOf(Double.valueOf(object[18].toString())))
							.add(BigDecimal.valueOf(Double.valueOf(object[18].toString()))));
					BigDecimal localcess = ((BigDecimal.valueOf(Double.valueOf(object[17].toString())))
							.add(BigDecimal.valueOf(Double.valueOf(object[18].toString())))
							.add(BigDecimal.valueOf(Double.valueOf(object[18].toString()))));
					lrd.setOtherLocalCess(sankirn.subtract(localcess));
					lrd.setModeOfPayment(object[46] == null ? "" : object[46].toString());
					lrd.setReceiptNo(object[47] == null ? "" : object[47].toString());
					lrd.setReceiptDateForReceipt(object[48] == null ? "" : object[48].toString());
					lrd.setDdName(object[49] == null ? "" : object[49].toString());
					lrd.setChequeAmount(object[50] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[50].toString())));
					lrd.setDdAmount(object[51] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[51].toString())));
					lrd.setDdBranch(object[52] == null ? "" : object[52].toString());
					lrd.setChequeBranch(object[53] == null ? "" : object[53].toString());
					lrd.setDdDateForReceipt(object[54] == null ? "" : object[54].toString());
					lrd.setChequeDateForReceipt(object[55] == null ? "" : object[55].toString());
					lrd.setChequeNumber(object[56] == null ? "" : object[56].toString());
					lrd.setDdNumber(object[57] == null ? "" : object[57].toString());
					lrd.setChequeName(object[58] == null ? "" : object[58].toString());
					return lrd;
				}).collect(Collectors.toList()));
				
			}catch(Exception e){
				
				System.out.println("Error at Method in lamdaExpression Receipt Print : "+ e.getMessage());
				
			}
			

			
			return landRevenueDemandResponse;
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	// pre
	@Override
	public Response getLrDtlByKhataNoAndKhataOwnerNamePre(LandRevenueDemandDao landRevenueDemandDao,
			HttpServletRequest request, Locale locale) {

		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(landRevenueDemandDao.getCCode())) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}
		String revYearValPre=landRevenueDemandDao.getRevenueYear();
		String[] revSingleStringpre=revYearValPre.split("-");
		String singStringpre=revSingleStringpre[0];
		
		if (ccodes.equals(landRevenueDemandDao.getCCode())) {
			List<Object[]> landRevenueDemand = new ArrayList<>();
			if (landRevenueDemandDao.getKhataOwnerName() != null) {
				landRevenueDemand = demandRepo2023.findByKhataOwnerName(request.getHeader("echHost"),
						request.getHeader("echDbName"), request.getHeader("echSchemaName"), CommonConstants.echUser,
						CommonConstants.echPassword, landRevenueDemandDao.getCCode(),
						landRevenueDemandDao.getKhataOwnerName(), landRevenueDemandDao.getRevenueYear(),singStringpre);
			} else {
				landRevenueDemand = demandRepo2023.findByKhataNo(request.getHeader("echHost"),
						request.getHeader("echDbName"), request.getHeader("echSchemaName"), CommonConstants.echUser,
						CommonConstants.echPassword, landRevenueDemandDao.getCCode(), landRevenueDemandDao.getKhataNo(),
						landRevenueDemandDao.getRevenueYear(), singStringpre);
			}
			DatabaseContextHolder.restoreCtx();
			if (landRevenueDemand.isEmpty()) {
				throw new ResourceNotFoundException("error.form.getData", messageSource, locale);
			}
			Response landRevenueDemandResponse = new Response();
			landRevenueDemandResponse.setLandRevenueDemandData(landRevenueDemand.stream().map((object) -> {

				LandRevenueDemandDao lrd = new LandRevenueDemandDao();

				double assessment = (Double.valueOf(object[3].toString()));
				double totarea = (Double.valueOf(object[4].toString()));
				lrd.setKhataNo(object[0].toString());
				lrd.setKhataOwnerName(object[1].toString());
				lrd.setCCode(object[2].toString());
				lrd.setAssessment(BigDecimal.valueOf(Double.valueOf(object[3].toString())));
				lrd.setArea(BigDecimal.valueOf(Double.valueOf(object[4].toString())));
				lrd.setSurveyHissaNo(object[5] == null ? "" : object[5].toString());
				lrd.setUom(object[6] == null ? "" : object[6].toString());

				if ((assessment >= 5 && assessment <= 10) || totarea <= 3.00) {
					lrd.setJmBindumala(BigDecimal.ZERO);
					lrd.setJmDumala(BigDecimal.ZERO);
				} else {
					lrd.setJmBindumala(BigDecimal.valueOf(Double.valueOf(object[7].toString())));
					lrd.setJmDumala(BigDecimal.valueOf(Double.valueOf(object[10].toString())));
				}
				// lrd.setJmBindumala(BigDecimal.valueOf(Double.valueOf(object[7].toString())));
				lrd.setZpBindumala(BigDecimal.valueOf(Double.valueOf(object[8].toString())));
				lrd.setGpBindumala(BigDecimal.valueOf(Double.valueOf(object[9].toString())));

				// lrd.setJmDumala(BigDecimal.valueOf(Double.valueOf(object[10].toString())));
				lrd.setZpDumala(BigDecimal.valueOf(Double.valueOf(object[11].toString())));
				lrd.setGpDumala(BigDecimal.valueOf(Double.valueOf(object[12].toString())));
				lrd.setJmAkrushik(BigDecimal.valueOf(Double.valueOf(object[13].toString())));
				lrd.setZpAkrushik(BigDecimal.valueOf(Double.valueOf(object[14].toString())));
				lrd.setGpAkrushik(BigDecimal.valueOf(Double.valueOf(object[15].toString())));
				lrd.setJmSankirn(BigDecimal.valueOf(Double.valueOf(object[16].toString())));
				lrd.setZpSankirn(BigDecimal.valueOf(Double.valueOf(object[17].toString())));
				lrd.setGpSankirn(BigDecimal.valueOf(Double.valueOf(object[18].toString())));
				lrd.setJmVajasut(BigDecimal.valueOf(Double.valueOf(object[19].toString())));
				lrd.setZpVajasut(BigDecimal.valueOf(Double.valueOf(object[20].toString())));
				lrd.setGpVajasut(BigDecimal.valueOf(Double.valueOf(object[21].toString())));
				lrd.setAddlLandRevenue(BigDecimal.valueOf(Double.valueOf(object[22].toString())));
				lrd.setEducationalCess(BigDecimal.valueOf(Double.valueOf(object[23].toString())));
				lrd.setAddlEducationalCess(BigDecimal.valueOf(Double.valueOf(object[24].toString())));
				lrd.setEmployeeGuaranteeScheme(BigDecimal.valueOf(Double.valueOf(object[25].toString())));
				lrd.setPreYearPendingJm(BigDecimal.valueOf(Double.valueOf(object[26].toString())));
				lrd.setPreYearPendingGp(BigDecimal.valueOf(Double.valueOf(object[27].toString())));
				lrd.setPreYearPendingZp(BigDecimal.valueOf(Double.valueOf(object[28].toString())));
				lrd.setPreYearPendingAddlLandRevenue(BigDecimal.valueOf(Double.valueOf(object[29].toString())));
				lrd.setPreYearPendingEducationalCess(BigDecimal.valueOf(Double.valueOf(object[30].toString())));
				lrd.setPreYearPendingAddlEducationalCess(BigDecimal.valueOf(Double.valueOf(object[31].toString())));
				lrd.setPreYearPendingEmployeeGuaranteeScheme(BigDecimal.valueOf(Double.valueOf(object[32].toString())));
				lrd.setNetAmount(BigDecimal.valueOf(Double.valueOf(object[33].toString())));
				lrd.setNetReceived(object[34] == null ? BigDecimal.ZERO
						: BigDecimal.valueOf(Double.valueOf(object[34].toString())));
				lrd.setNetPending(object[35] == null ? BigDecimal.ZERO
						: BigDecimal.valueOf(Double.valueOf(object[35].toString())));
				lrd.setDateOfForReceipt(object[36] == null ? "" : object[36].toString());
				lrd.setNoticeAmount(object[37] == null ? BigDecimal.ZERO
						: BigDecimal.valueOf(Double.valueOf(object[37].toString())));
				lrd.setSurveyHissaNo(object[38].toString());
				lrd.setPreYearNoticeFee(object[39] == null ? BigDecimal.ZERO
						: BigDecimal.valueOf(Double.valueOf(object[39].toString())));
				lrd.setPreYearPendingNaCess(object[40] == null ? BigDecimal.ZERO
						: BigDecimal.valueOf(Double.valueOf(object[40].toString())));
				lrd.setPreYearSankirnJmWith(object[41] == null ? BigDecimal.ZERO
						: BigDecimal.valueOf(Double.valueOf(object[41].toString())));
				lrd.setPreYearSankirnJmWithout(object[42] == null ? BigDecimal.ZERO
						: BigDecimal.valueOf(Double.valueOf(object[42].toString())));
				lrd.setMakhtaKhataNo(object[43] == null ? "" : object[43].toString());
				lrd.setNameofTenent(object[44] == null ? "" : object[44].toString());
				lrd.setId(Long.valueOf(object[45].toString()));
				BigDecimal sankirn = ((BigDecimal.valueOf(Double.valueOf(object[16].toString())))
						.add(BigDecimal.valueOf(Double.valueOf(object[17].toString())))
						.add(BigDecimal.valueOf(Double.valueOf(object[18].toString()))));
				lrd.setLocalCess((BigDecimal.valueOf(Double.valueOf(object[17].toString())))
						.add(BigDecimal.valueOf(Double.valueOf(object[18].toString())))
						.add(BigDecimal.valueOf(Double.valueOf(object[18].toString()))));
				BigDecimal localcess = ((BigDecimal.valueOf(Double.valueOf(object[17].toString())))
						.add(BigDecimal.valueOf(Double.valueOf(object[18].toString())))
						.add(BigDecimal.valueOf(Double.valueOf(object[18].toString()))));
				lrd.setOtherLocalCess(sankirn.subtract(localcess));
				lrd.setModeOfPayment(object[46] == null ? "" : object[46].toString());
				lrd.setReceiptNo(object[47] == null ? "" : object[47].toString());
				lrd.setReceiptDateForReceipt(object[48] == null ? "" : object[48].toString());
				lrd.setDdName(object[49] == null ? "" : object[49].toString());
				lrd.setChequeAmount(object[50] == null ? BigDecimal.ZERO
						: BigDecimal.valueOf(Double.valueOf(object[50].toString())));
				lrd.setDdAmount(object[51] == null ? BigDecimal.ZERO
						: BigDecimal.valueOf(Double.valueOf(object[51].toString())));
				lrd.setDdBranch(object[52] == null ? "" : object[52].toString());
				lrd.setChequeBranch(object[53] == null ? "" : object[53].toString());
				lrd.setDdDateForReceipt(object[54] == null ? "" : object[54].toString());
				lrd.setChequeDateForReceipt(object[55] == null ? "" : object[55].toString());
				lrd.setChequeNumber(object[56] == null ? "" : object[56].toString());
				lrd.setDdNumber(object[57] == null ? "" : object[57].toString());
				lrd.setChequeName(object[58] == null ? "" : object[58].toString());
				return lrd;
			}).collect(Collectors.toList()));
			return landRevenueDemandResponse;
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	// rojgar Hami Report Live

	@Override
	public Response getListOfRojgarHamiTax(Integer districtCode, Integer talukaCode, String cCode, String revenueYear,
			HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, request.getHeader("servarthId"));
		Response response = new Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km = new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));

		String ccodes = null;
		for (KhataMergeDao r : response.getKhataMergeData()) {
			if (r.getcCode().equals(cCode)) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(cCode)) {
			List<Object[]> educationCessTax = landRevenueDemandRepository.getRojGarHami(request.getHeader("mhrHost"),
					request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
					CommonConstants.mhrorPassword, request.getHeader("echHost"), request.getHeader("echDbName"),
					request.getHeader("echSchemaName"), CommonConstants.echUser, CommonConstants.echPassword, cCode,
					revenueYear);
			DatabaseContextHolder.restoreCtx();
			if (educationCessTax.isEmpty()) {
				throw new ResourceNotFoundException("error.form.reportdetails", messageSource, locale);
			} else {

				Response rojgarTaxResponse = new Response();
				rojgarTaxResponse.setLandRevenueDemandData(educationCessTax.stream().map((object) -> {
					LandRevenueDemandDao landRevenueDemandDao = new LandRevenueDemandDao();
					landRevenueDemandDao.setKhataOwnerName(object[0].toString());
					landRevenueDemandDao.setKhataNo(object[1].toString());
					landRevenueDemandDao.setSurveyHissaNo(object[2].toString());
					landRevenueDemandDao.setSeasonCode(Integer.valueOf(object[3].toString()));
					landRevenueDemandDao.setArea(BigDecimal.valueOf(Double.valueOf(object[4].toString())));
					landRevenueDemandDao
							.setEmployeeGuaranteeScheme(BigDecimal.valueOf(Double.valueOf(object[5].toString())));
					return landRevenueDemandDao;
				}).collect(Collectors.toList()));
				return rojgarTaxResponse;
			}
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	@Override
	public Response getAkrushakRateforRural(String cCode, HttpServletRequest request, Locale locale) {
		List<Object[]> AkrushakRateRural = landRevenueDemandRepository.findAkrushikRateforRural(
				request.getHeader("mhrHost"), request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"),
				CommonConstants.mhrorUser, CommonConstants.mhrorPassword, cCode);
		Response response = new Response();
		AkrushakRateDao akrushakRateDao = new AkrushakRateDao();
		response.setAkrushakRate(AkrushakRateRural.stream().map((r) -> {
			akrushakRateDao.setCcode(r[0] == null ? "" : r[0].toString());
			akrushakRateDao.setRate(r[1] == null ? "" : r[1].toString());
			String rate = akrushakRateDao.getRate();
			String finalrate = rate.replace('p', ' ');
			double area = Double.parseDouble(finalrate);
			double areaInruppes = area * 0.01;
			akrushakRateDao.setRate(Double.valueOf(areaInruppes).toString().trim());
			return akrushakRateDao;
		}).collect(Collectors.toList()));

		return response;
	}
	
	
	// For RevenueYear OneTimeDataEntry
		@Override
		public OneTimeDataEntyRevenueYearCheckDTO checkRevenueYear(String ccode, HttpServletRequest request,
				Locale locale) {
			
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
			 
			        try {
			            boolean exists = landRevenueDemandRepository.isRemoteDemandYearAvailable(
			            		request.getHeader("echDbName"),
			            		request.getHeader("echHost"),
			            		CommonConstants.echUser,
			    				CommonConstants.echPassword,
			            		request.getHeader("echSchemaName"),
			                    ccode, "Y", CommonConstants.CarryForwordPendingPreYearRevenueYear);

			            return new OneTimeDataEntyRevenueYearCheckDTO(exists ? "Y" : "N");

			        } catch (Exception e) {
			            // Handle any unexpected issues (e.g., DB connection error)
			            return new OneTimeDataEntyRevenueYearCheckDTO("N");
			        }
			    
		}

		@Override
		public List<LandRevenueDemandDao> getListOfRojgarHamiTaxx(Integer districtCode, Integer talukaCode,
				String cCode, String revenueYear, HttpServletRequest request, Locale locale) {
			
			dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
				 
			List<Object[]> rawResult = landRevenueDemandRepository.getListOfRojgarHamiTaxx(
	                request.getHeader("echHost"),
					request.getHeader("echDbName"),
	                request.getHeader("echSchemaName"),
                    CommonConstants.echUser,
	                CommonConstants.echPassword,
	                cCode,
	                revenueYear,
	                districtCode,
	                talukaCode);

			 List<LandRevenueDemandDao> result = new ArrayList<>();

		        for (Object[] row : rawResult) {
		            LandRevenueDemandDao dao = new LandRevenueDemandDao();
		            dao.setKhataNo(row[0] != null ? row[0].toString() : null);
		            dao.setKhataOwnerName(row[1] != null ? row[1].toString() : null);
		           // dao.setEmployeeGuaranteeScheme(row[2] != null ? row[2].toString() : null);
		            dao.setEmployeeGuaranteeScheme(BigDecimal.valueOf(Double.valueOf(row[2].toString())));


		            // If EmployeeGuaranteeScheme is ZERO, don't display this record 17/08/2026
		            BigDecimal employeeGuaranteeScheme =
		                    row[2] != null
		                            ? new BigDecimal(row[2].toString())
		                            : BigDecimal.ZERO;

		            	if (employeeGuaranteeScheme.compareTo(BigDecimal.ZERO) == 0) {
		            	    continue;
		            	}
		            	
		            result.add(dao);
		        }

		        return result;
		    
		
		}

		@Override
		public List<LandRevenueDemandDao> getEduCessTaxx(Integer districtCode, Integer talukaCode, String cCode,
				String revenueYear, HttpServletRequest request, Locale locale) {
			dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
			 
			List<Object[]> rawResult = landRevenueDemandRepository.getEduCessTax(
	                request.getHeader("echHost"),
					request.getHeader("echDbName"),
	                request.getHeader("echSchemaName"),
                    CommonConstants.echUser,
	                CommonConstants.echPassword,
	                cCode,
	                revenueYear,
	                districtCode,
	                talukaCode);

			 List<LandRevenueDemandDao> result = new ArrayList<>();

		        for (Object[] row : rawResult) {
		            LandRevenueDemandDao dao = new LandRevenueDemandDao();
		            dao.setKhataNo(row[0] != null ? row[0].toString() : null);
		            dao.setKhataOwnerName(row[1] != null ? row[1].toString() : null);
		           // dao.setEmployeeGuaranteeScheme(row[2] != null ? row[2].toString() : null);
		            dao.setAddlEducationalCess(BigDecimal.valueOf(Double.valueOf(row[2].toString())));
		            dao.setEducationalCess(BigDecimal.valueOf(Double.valueOf(row[3].toString())));
		           dao.setTotalShikshanKar(BigDecimal.valueOf(Double.valueOf(row[4].toString())));
		           
		           
		            result.add(dao);
		        }

		        return result;
		}

		
		
//		@Override
//		public List<MVillageMapper> getDropDownsVillage(String districtCode ,String talukaCode){
//			
//			HashMap<String, String> infoMap = jwtUtils.getDbs(districtCode, talukaCode);
//			
//			DatabaseContextHolder.setCtx(DataSourceType.echmst);
//			List<Object[]> village =mVillageMapperRepository.getVillagesByTakukaCodeAndDistCode(districtCode,talukaCode);
//			DatabaseContextHolder.restoreCtx();
//			return village.stream().map(r->{
//				
//				MVillageMapper lrd=new MVillageMapper();
//				lrd.setCCode(r[0].toString().trim());
//				lrd.setVillageName(r[1].toString());
//				lrd.setDistrictCode(r[2].toString().trim());
//				lrd.setTalukaCode(r[3].toString().trim());
//
//				return lrd;
//			}).collect(Collectors.toList());
//		}
		
		@Override
		public VillageDropdownResponse getDropDownsVillage(
		        String districtCode, String talukaCode) {

		    HashMap<String, String> infoMap =
		            jwtUtils.getDbs(districtCode, talukaCode);

		    DatabaseContextHolder.setCtx(DataSourceType.echmst);

		    List<Object[]> village =
		            mVillageMapperRepository.getVillagesByTakukaCodeAndDistCode(
		                    districtCode, talukaCode);

		    DatabaseContextHolder.restoreCtx();

		    List<MVillageMapper> villageList = village.stream().map(r -> {

		        MVillageMapper lrd = new MVillageMapper();

		        lrd.setCCode(r[0].toString().trim());
		        lrd.setVillageName(r[1].toString());
		        lrd.setDistrictCode(r[2].toString().trim());
		        lrd.setTalukaCode(r[3].toString().trim());
		        lrd.setDistrictName(r[4].toString().trim());
		        lrd.setTalukaName(r[5].toString().trim());

		        return lrd;

		    }).collect(Collectors.toList());

		    VillageDropdownResponse response = new VillageDropdownResponse();

		    response.setEncryHeaders(infoMap);
		    response.setVillageList(villageList);

		    return response;
		}
		
		
		
		@Override
		public List<Taluka> getDropDownsTaluka(String districtCode ){
			DatabaseContextHolder.setCtx(DataSourceType.echmst);
			List<Object[]> taluka =talukaRepository.findByDistrictCode(districtCode);
			DatabaseContextHolder.restoreCtx();
			return taluka.stream().map(r->{
				
				///select distinct taluka_code,taluka_name_marathi,district_code,count(taluka_code) from e_lgd_mapper    "
				
				Taluka response =new Taluka();
				response.setTalukaCode(r[0].toString().trim());
				response.setTalukaName(r[1].toString());
				response.setDistrictCode(r[2].toString().trim());
				response.setCount(r[3].toString());
				
				
				return response;
			}).collect(Collectors.toList());

		}
	
//For 7B signup
		
		@Override
		public List<District> getDropDownsDistrict(String divisionCode){
			DatabaseContextHolder.setCtx(DataSourceType.echmst);
			List<Object[]> distirct =districtRepository.findByDivisionCode(divisionCode);
			DatabaseContextHolder.restoreCtx();
			return distirct.stream().map(r->{
				District response=new District();
				response.setDistrictCode(r[0].toString().trim());
				response.setDistrictName(r[1].toString());
				response.setCount(r[2].toString());
				return response;
			}).collect(Collectors.toList());
		}

		@Override
			public List<MVillageMapper> getDropDownsVillage7B(String districtCode ,String talukaCode){
				DatabaseContextHolder.setCtx(DataSourceType.echmst);
				List<Object[]> village =mVillageMapperRepository.findAllByDistrictCodeAndTalukaCode(districtCode,talukaCode);
				DatabaseContextHolder.restoreCtx();
				return village.stream().map(r->{
					MVillageMapper lrd=new MVillageMapper();
					lrd.setCCode(r[0].toString().trim());
					lrd.setVillageName(r[1].toString());
					lrd.setDistrictCode(r[2].toString().trim());
					lrd.setTalukaCode(r[3].toString().trim());
//					lrd.setCount(r[4].toString());
					return lrd;
				}).collect(Collectors.toList());
			}

	@Override
		public List<Division> getDropDownsDivision() {
			DatabaseContextHolder.setCtx(DataSourceType.echmst);
			List<Object[]> division =divisionRepository.findAllDivision();
			DatabaseContextHolder.restoreCtx();
			return division.stream().map(r->{
				Division response=new Division();
				response.setDivisionCode(r[0].toString().trim());
				response.setDivisionName(r[1].toString());
				return response;
			}).collect(Collectors.toList());
		}


}