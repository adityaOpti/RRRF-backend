/**
 * 
 */
package com.probity.echawdi.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.Form17Dao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.dao.LandRevenueDemandDao;
import com.probity.echawdi.entity.Form17;
import com.probity.echawdi.entity.LandRevenueDemand;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.FieldValidationException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form17Repository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.repository.LandRevenueDemandRepository;
import com.probity.echawdi.service.Form17Service;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;

/**
 * @author Bhagyashri Lolage
 *
 */
@Component
public class Form17ServiceImpl implements Form17Service {

	@Autowired
	private Form17Repository form17Repository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;
	@Autowired
	private LandRevenueDemandRepository landRevenueDemandRepository;

	@Override
	public MessageResponse save(Form17Dao form17Dao, HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		if (form17Dao.getcCode() != null) {
			Form17 form17 = toEntity(form17Dao);
			if (form17Dao.getKhataNo() != null) {
				form17.setKhataType("R");
			} else {
				form17.setKhataType("M");
			}
//new change for sarvey hissa no not show 
			String selectedHissaNo = form17Dao.getSurveyHissaNo();

			if (form17Dao.getKhataNo() != null) {
				List<Object[]> f17 = form17Repository.findByKhataNo(CommonConstants.AugDatamhrHost,
						request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
						CommonConstants.mhrorPassword, form17Dao.getcCode(), form17Dao.getKhataNo(),
						CommonConstants.AugDataPort);
				DatabaseContextHolder.restoreCtx();
				List<Form17Dao> lst = f17.stream().map(r -> {
					Form17Dao f17Dao = new Form17Dao();
					form17.setKhataNo(r[0].toString());
					form17.setPersonLiable(r[1].toString());
					form17.setTotalArea(BigDecimal.valueOf(Double.valueOf(r[2].toString())));
					//form17.setSurveyHissaNo(r[3].toString());
					form17.setSurveyHissaNo(selectedHissaNo);
					form17.setUomOfTotalArea(r[4].toString());
					return f17Dao;
				}).collect(Collectors.toList());
			}

			BigDecimal jmAmount = ((form17Dao.getLocalCessAmount() == null ? BigDecimal.ZERO
					: form17Dao.getLocalCessAmount())
							.add(form17Dao.getOtherLocalCessAmount() == null ? BigDecimal.ZERO
									: form17Dao.getOtherLocalCessAmount()));
			form17.setAmountOfJm(jmAmount);

			BigDecimal gpAmount = (form17Dao.getLocalCessAmount() == null ? BigDecimal.ZERO
					: form17Dao.getLocalCessAmount());
			form17.setAmountOfGp(gpAmount);

			BigDecimal zpAmount = ((form17Dao.getLocalCessAmount() == null ? BigDecimal.ZERO
					: form17Dao.getLocalCessAmount()).multiply(BigDecimal.valueOf(7)));
			form17.setAmountOfZp(zpAmount);

			form17.setAssessment((jmAmount).add(gpAmount).add(zpAmount));
			form17Repository.save(form17);

			if (form17Dao.getKhataNo() != null) {
			LandRevenueDemand landRevenueDemand2 = landRevenueDemandRepository.findBycCodeAndKhataNoAndRevenueYearAndActiveFlag(
					form17Dao.getcCode(), form17Dao.getKhataNo(), form17Dao.getRevenueYear(), 'Y');
			
				updateLandRevenueDemand(landRevenueDemand2, form17Dao, jmAmount, gpAmount, zpAmount);
				landRevenueDemandRepository.save(landRevenueDemand2);
			} else if (form17Dao.getMakhtaKhataNo() != null) {
				LandRevenueDemand landRevenueDemand2 = new LandRevenueDemand();

				createNewEntry(landRevenueDemand2, form17Dao, jmAmount, gpAmount, zpAmount);
				landRevenueDemandRepository.save(landRevenueDemand2);
			}
//			landRevenueDemandRepository.save(landRevenueDemand2);
			DatabaseContextHolder.restoreCtx();
			return MessageResponse.builder().status("SUCCESS")
					.message(messageSource.getMessage("error.form.savedata", null, locale)).build();
		}
		throw new FieldValidationException("error.form.errorwhilesave");
	}

	private void updateLandRevenueDemand(LandRevenueDemand landRevenueDemand, Form17Dao form17Dao, BigDecimal jmAmount,
			BigDecimal gpAmount, BigDecimal zpAmount) {

//		landRevenueDemand.setGpSankirn(gpAmount);
//		landRevenueDemand.setJmSankirn(jmAmount);
//		landRevenueDemand.setZpSankirn(zpAmount);
		//new change for jm sankirn value not updated according pin 19/08/2026
		
		landRevenueDemand.setGpSankirn((gpAmount).add(landRevenueDemand.getGpSankirn()));
		landRevenueDemand.setJmSankirn((jmAmount).add(landRevenueDemand.getJmSankirn()));
		landRevenueDemand.setZpSankirn((zpAmount).add(landRevenueDemand.getZpSankirn()));		
		System.out.println("----------------------------------------" + landRevenueDemand.getNetAmount());

		landRevenueDemand.setNetAmount((jmAmount).add(gpAmount).add(zpAmount).add(landRevenueDemand.getNetAmount()));
		
		landRevenueDemand.setNetPending((jmAmount).add(gpAmount).add(zpAmount).add(landRevenueDemand.getNetPending()));
		
		
		
//			landRevenueDemand.setNetPending((jmAmount).add(gpAmount).add(zpAmount));
//			landRevenueDemand.setKhataOwnerName(form17Dao.getPersonLiable());

	}

	private void createNewEntry(LandRevenueDemand landRevenueDemand, Form17Dao form17Dao, BigDecimal jmAmount,
			BigDecimal gpAmount, BigDecimal zpAmount) {
		LandRevenueDemandDao landRevenueDemandDao = new LandRevenueDemandDao();

		landRevenueDemand.setGpSankirn(gpAmount);
		landRevenueDemand.setJmSankirn(jmAmount);
		landRevenueDemand.setZpSankirn(zpAmount);
		landRevenueDemand.setNetAmount((jmAmount).add(gpAmount).add(zpAmount));
		landRevenueDemand.setNetPending((jmAmount).add(gpAmount).add(zpAmount));
		landRevenueDemand.setKhataOwnerName(form17Dao.getPersonLiable());
		landRevenueDemand.setRevenueYear(form17Dao.getRevenueYear());
		landRevenueDemand.setcCode(form17Dao.getcCode());
		landRevenueDemand.setTalukaCode(form17Dao.getTalukaCode());
		landRevenueDemand.setDistrictCode(form17Dao.getDistrictCode());
		landRevenueDemand.setKhataNo(form17Dao.getKhataNo());
		landRevenueDemand.setMakhtaKhataNo(form17Dao.getMakhtaKhataNo());
		landRevenueDemand.setArea(form17Dao.getTotalArea());
		landRevenueDemand.setSurveyHissaNo(form17Dao.getSurveyHissaNo());
		landRevenueDemand.setAssessment(
				landRevenueDemandDao.getAssessment() == null ? BigDecimal.ZERO : landRevenueDemand.getAssessment());

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
		landRevenueDemand.setPreYearPendingAddlEducationalCess(
				landRevenueDemandDao.getPreYearPendingAddlEducationalCess() == null ? BigDecimal.ZERO
						: landRevenueDemandDao.getPreYearPendingAddlEducationalCess());
		landRevenueDemand.setPreYearPendingEmployeeGuaranteeScheme(
				landRevenueDemandDao.getPreYearPendingEmployeeGuaranteeScheme() == null ? BigDecimal.ZERO
						: landRevenueDemandDao.getPreYearPendingEmployeeGuaranteeScheme());
		// landRevenueDemand.setMiscellaneousAmount(landRevenueDemandDao.getMiscellaneousAmount()==null
		// ? BigDecimal.ZERO:landRevenueDemandDao.getMiscellaneousAmount());
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
		landRevenueDemand.setPreYearPendingJm(landRevenueDemandDao.getPreYearPendingJm() == null ? BigDecimal.ZERO
				: landRevenueDemandDao.getPreYearPendingJm());
		landRevenueDemand.setPreYearPendingZp(landRevenueDemandDao.getPreYearPendingZp() == null ? BigDecimal.ZERO
				: landRevenueDemandDao.getPreYearPendingZp());
		landRevenueDemand.setPreYearPendingGp(landRevenueDemandDao.getPreYearPendingGp() == null ? BigDecimal.ZERO
				: landRevenueDemandDao.getPreYearPendingGp());
		landRevenueDemand.setPreYearPendingEducationalCess(
				landRevenueDemandDao.getPreYearPendingEducationalCess() == null ? BigDecimal.ZERO
						: landRevenueDemandDao.getPreYearPendingEducationalCess());
		landRevenueDemand.setPreYearPendingAddlEducationalCess(
				landRevenueDemandDao.getPreYearPendingAddlEducationalCess() == null ? BigDecimal.ZERO
						: landRevenueDemandDao.getPreYearPendingAddlEducationalCess());

		landRevenueDemand.setPreYearPendingAddlLandRevenue(
				landRevenueDemandDao.getPreYearPendingAddlLandRevenue() == null ? BigDecimal.ZERO
						: landRevenueDemandDao.getPreYearPendingAddlLandRevenue());

		landRevenueDemandRepository.save(landRevenueDemand);

	}

//				//11/08/24
//				LandRevenueDemandDao landRevenueDemandDao = new LandRevenueDemandDao ();
//				System.out.println("------5------------>"+landRevenueDemandDao);
//
//				LandRevenueDemand landRevenueDemand = new LandRevenueDemand();
//				landRevenueDemand.setGpSankirn(gpAmount);
//				landRevenueDemand.setJmSankirn(jmAmount);
//				landRevenueDemand.setZpSankirn(zpAmount);
//				landRevenueDemand.setNetAmount((jmAmount).add(gpAmount).add(zpAmount));
//				landRevenueDemand.setNetPending((jmAmount).add(gpAmount).add(zpAmount));
//				landRevenueDemand.setKhataOwnerName(form17Dao.getPersonLiable());
//				landRevenueDemand.setRevenueYear(form17Dao.getRevenueYear());
//				landRevenueDemand.setcCode(form17Dao.getcCode());
//				landRevenueDemand.setTalukaCode(form17Dao.getTalukaCode());	
//				landRevenueDemand.setDistrictCode(form17Dao.getDistrictCode());
//				landRevenueDemand.setKhataNo(form17Dao.getKhataNo());
//				
//				landRevenueDemand.setMakhtaKhataNo(form17Dao.getMakhtaKhataNo());
//				landRevenueDemand
//				.setArea(form17Dao.getTotalArea());
//				landRevenueDemand.setAssessment(
//						landRevenueDemandDao.getAssessment() == null ? BigDecimal.ZERO : landRevenueDemand.getAssessment());
//				
//
//		landRevenueDemand.setJmBindumala(landRevenueDemandDao.getJmBindumala() == null ? BigDecimal.ZERO
//				: landRevenueDemandDao.getJmBindumala());
//		landRevenueDemand.setZpBindumala(landRevenueDemandDao.getZpBindumala() == null ? BigDecimal.ZERO
//				: landRevenueDemandDao.getZpBindumala());
//		landRevenueDemand.setGpBindumala(landRevenueDemandDao.getGpBindumala() == null ? BigDecimal.ZERO
//				: landRevenueDemandDao.getGpBindumala());
//
//		landRevenueDemand.setJmDumala(
//				landRevenueDemandDao.getJmDumala() == null ? BigDecimal.ZERO : landRevenueDemandDao.getJmDumala());
//		landRevenueDemand.setZpDumala(
//				landRevenueDemandDao.getZpDumala() == null ? BigDecimal.ZERO : landRevenueDemandDao.getZpDumala());
//		landRevenueDemand.setGpDumala(
//				landRevenueDemandDao.getGpDumala() == null ? BigDecimal.ZERO : landRevenueDemandDao.getGpDumala());
//
//		landRevenueDemand.setJmAkrushik(
//				landRevenueDemandDao.getJmAkrushik() == null ? BigDecimal.ZERO : landRevenueDemandDao.getJmAkrushik());
//		landRevenueDemand.setZpAkrushik(
//				landRevenueDemandDao.getZpAkrushik() == null ? BigDecimal.ZERO : landRevenueDemandDao.getZpAkrushik());
//		landRevenueDemand.setGpAkrushik(
//				landRevenueDemandDao.getGpAkrushik() == null ? BigDecimal.ZERO : landRevenueDemandDao.getGpAkrushik());
//
//		landRevenueDemand.setJmVajasut(
//				landRevenueDemandDao.getJmVajasut() == null ? BigDecimal.ZERO : landRevenueDemandDao.getJmVajasut());
//		landRevenueDemand.setZpVajasut(
//				landRevenueDemandDao.getZpVajasut() == null ? BigDecimal.ZERO : landRevenueDemandDao.getZpVajasut());
//		landRevenueDemand.setGpVajasut(
//				landRevenueDemandDao.getGpVajasut() == null ? BigDecimal.ZERO : landRevenueDemandDao.getGpVajasut());
//
//		landRevenueDemand.setAddlLandRevenue(landRevenueDemandDao.getAddlLandRevenue() == null ? BigDecimal.ZERO
//				: landRevenueDemandDao.getAddlLandRevenue());
//		landRevenueDemand.setAddlEducationalCess(landRevenueDemandDao.getAddlEducationalCess() == null ? BigDecimal.ZERO
//				: landRevenueDemandDao.getAddlEducationalCess());
//		landRevenueDemand
//				.setEmployeeGuaranteeScheme(landRevenueDemandDao.getEmployeeGuaranteeScheme() == null ? BigDecimal.ZERO
//						: landRevenueDemandDao.getEmployeeGuaranteeScheme());
//		landRevenueDemand.setEducationalCess(landRevenueDemandDao.getEducationalCess() == null ? BigDecimal.ZERO
//				: landRevenueDemandDao.getEducationalCess());
//		landRevenueDemand.setPreYearPendingAddlEducationalCess(
//				landRevenueDemandDao.getPreYearPendingAddlEducationalCess() == null ? BigDecimal.ZERO
//						: landRevenueDemandDao.getPreYearPendingAddlEducationalCess());
//		landRevenueDemand.setPreYearPendingEmployeeGuaranteeScheme(
//				landRevenueDemandDao.getPreYearPendingEmployeeGuaranteeScheme() == null ? BigDecimal.ZERO
//						: landRevenueDemandDao.getPreYearPendingEmployeeGuaranteeScheme());
//		// landRevenueDemand.setMiscellaneousAmount(landRevenueDemandDao.getMiscellaneousAmount()==null
//		// ? BigDecimal.ZERO:landRevenueDemandDao.getMiscellaneousAmount());
//		landRevenueDemand.setPreYearNoticeFee(landRevenueDemandDao.getPreYearNoticeFee() == null ? BigDecimal.ZERO
//				: landRevenueDemandDao.getPreYearNoticeFee());
//		landRevenueDemand
//				.setPreYearPendingNaCess(landRevenueDemandDao.getPreYearPendingNaCess() == null ? BigDecimal.ZERO
//						: landRevenueDemandDao.getPreYearPendingNaCess());
//		landRevenueDemand
//				.setPreYearSankirnJmWith(landRevenueDemandDao.getPreYearSankirnJmWith() == null ? BigDecimal.ZERO
//						: landRevenueDemandDao.getPreYearSankirnJmWith());
//		landRevenueDemand
//				.setPreYearSankirnJmWithout(landRevenueDemandDao.getPreYearSankirnJmWithout() == null ? BigDecimal.ZERO
//						: landRevenueDemandDao.getPreYearSankirnJmWithout());
//		landRevenueDemand.setPreYearPendingJm(landRevenueDemandDao.getPreYearPendingJm() == null ? BigDecimal.ZERO
//				: landRevenueDemandDao.getPreYearPendingJm());
//		landRevenueDemand.setPreYearPendingZp(landRevenueDemandDao.getPreYearPendingZp() == null ? BigDecimal.ZERO
//				: landRevenueDemandDao.getPreYearPendingZp());
//		landRevenueDemand.setPreYearPendingGp(landRevenueDemandDao.getPreYearPendingGp() == null ? BigDecimal.ZERO
//				: landRevenueDemandDao.getPreYearPendingGp());
//		landRevenueDemand.setPreYearPendingEducationalCess(
//				landRevenueDemandDao.getPreYearPendingEducationalCess() == null ? BigDecimal.ZERO
//						: landRevenueDemandDao.getPreYearPendingEducationalCess());
//		landRevenueDemand.setPreYearPendingAddlEducationalCess(
//		landRevenueDemandDao.getPreYearPendingAddlEducationalCess() == null ? BigDecimal.ZERO
//				: landRevenueDemandDao.getPreYearPendingAddlEducationalCess());
//		
//		landRevenueDemand.setPreYearPendingAddlLandRevenue(
//				landRevenueDemandDao.getPreYearPendingAddlLandRevenue() == null ? BigDecimal.ZERO
//						: landRevenueDemandDao.getPreYearPendingAddlLandRevenue());
//		
//				landRevenueDemandRepository.save(landRevenueDemand);
//			
//			form17Repository.save(form17);
//			DatabaseContextHolder.restoreCtx();
//			return MessageResponse.builder().status("SUCCESS").message(
//					messageSource.getMessage("error.form.savedata", null, locale)).build();	
//		}
//		throw new FieldValidationException("error.form.errorwhilesave");
//	}

	@Override
	public String makhtakhataNumbers(String cCode, String lgdCode, String revenueYear, HttpServletRequest request) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		String form17 = form17Repository.getMaktaNo(request.getHeader("echHost"), request.getHeader("echDbName"),
				request.getHeader("echSchemaName"), CommonConstants.echUser, CommonConstants.echPassword, cCode,
				revenueYear);
		DatabaseContextHolder.restoreCtx();
		Integer mKhata = null;
		if (form17 == null || form17.isEmpty()) {
			mKhata = 1;
		} else {
			mKhata = Integer.valueOf(form17) + 1;
		}
		// System.out.println(mKhata);
		return (lgdCode + "/" + revenueYear + "/") + (String.valueOf(mKhata));
	}

	@Override
	public Response getAllForm17Data(String cCode, String revenueYear, String khataNo, HttpServletRequest request,
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
			if (r.getcCode().equals(cCode)) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(cCode)) {
			String makhtaKhataNo = "%" + khataNo + "%";
			List<Object[]> form17 = form17Repository.findAllForm17Data(request.getHeader("echHost"),
					request.getHeader("echDbName"), request.getHeader("echSchemaName"), CommonConstants.echUser,
					CommonConstants.echPassword, cCode, revenueYear, khataNo, 'Y', makhtaKhataNo);
			DatabaseContextHolder.restoreCtx();
			if (form17.isEmpty()) {
				throw new ResourceNotFoundException("error.form.getData", messageSource, locale);
			} else {
				Response form17Response = new Response();
				form17Response.setForm17Data(form17.stream().map((object) -> {
					Form17Dao form17Dao = new Form17Dao();
					form17Dao.setId(Long.valueOf(object[0].toString()));
					form17Dao.setRevenueYear(object[6].toString());
					form17Dao.setDistrictCode(Integer.valueOf(object[7].toString()));
					form17Dao.setTalukaCode(Integer.valueOf(object[8].toString()));
					form17Dao.setcCode(object[9].toString());
					form17Dao.setKhataNo(object[10] == null ? "" : object[10].toString());
					form17Dao.setPersonLiable(object[11].toString());
					form17Dao.setSurveyHissaNo(object[12] == null ? "" : object[12].toString());
					form17Dao.setTotalArea(object[13] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[13].toString())));
					form17Dao.setUomOfTotalArea(object[14] == null ? "" : object[14].toString());
					form17Dao.setCaseNo(object[15] == null ? "" : object[15].toString());
					form17Dao.setAreaAffected(object[16] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[16].toString())));
					form17Dao.setUomOfAreaAffected(object[17] == null ? "" : object[17].toString());
					form17Dao.setNatureOfCase(object[18] == null ? "" : object[18].toString());
					form17Dao.setPeriodIfMoreThanOneYear(object[19] == null ? "" : object[19].toString());
					if (object[20] != null) {
						form17Dao.setPeriodFromDate(LocalDate.parse(object[20].toString()));
					}

					if (object[21] != null) {
						form17Dao.setPeriodToDate(LocalDate.parse(object[21].toString()));
					}
					form17Dao.setLocalCess(object[22] == null ? "" : object[22].toString());
					form17Dao.setOtherLocalCess(object[23] == null ? "" : object[23].toString());
					form17Dao.setOtherLocalCessDesc(object[24] == null ? "" : object[24].toString());
					form17Dao.setLocalCessAmount(object[25] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[25].toString())));
					form17Dao.setOtherLocalCessAmount(object[26] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[26].toString())));
					form17Dao.setAmountOfJm(object[27] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[27].toString())));
					form17Dao.setAmountOfZp(object[28] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[28].toString())));
					form17Dao.setAmountOfGp(object[29] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[29].toString())));
					form17Dao.setAssessment(object[30] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[30].toString())));
					form17Dao.setRemarks(object[31] == null ? "" : object[31].toString());
					form17Dao.setMakhtaKhataNo(object[36] == null ? "" : object[36].toString());
					return form17Dao;
				}).collect(Collectors.toList()));
				return form17Response;
			}
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	@Override
	public Form17Dao getForm17ById(long id, HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		Form17 form17 = form17Repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Form17", id));
		DatabaseContextHolder.restoreCtx();
		return toDao(form17);
	}

	@Override
	public Form17Dao getForm17ByCCodeAndRevenueYearAndKhataNo(Form17Dao form17Dao, HttpServletRequest request,
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
			if (r.getcCode().equals(form17Dao.getcCode())) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(form17Dao.getcCode())) {
			Form17 form17 = form17Repository.findBycCodeAndRevenueYearAndKhataNoAndActiveFlagAndDemandGeneratedIsNull(
					form17Dao.getcCode(), form17Dao.getRevenueYear(), form17Dao.getKhataNo(), 'Y');
			DatabaseContextHolder.restoreCtx();
			if (form17 == null) {
				throw new ResourceNotFoundException("error.form.notfound", messageSource, locale);
			}
			Form17Dao form17Daos = new Form17Dao();
			BeanUtils.copyProperties(form17, form17Daos);
			form17Daos.setLocalCessAmount(form17.getAssessment().subtract(
					form17.getOtherLocalCessAmount() == null ? BigDecimal.ZERO : form17.getOtherLocalCessAmount()));
			form17Daos.setOtherLocalCessAmount(
					form17.getOtherLocalCessAmount() == null ? BigDecimal.ZERO : form17.getOtherLocalCessAmount());
			return form17Daos;
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	@Override
	public Form17Dao getForm17ByCCodeAndRevenueYearAndMakhtaKhataNo(Form17Dao form17Dao, HttpServletRequest request,
			Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		Form17 form17 = form17Repository.findBycCodeAndRevenueYearAndMakhtaKhataNoAndActiveFlagAndDemandGeneratedIsNull(
				form17Dao.getcCode(), form17Dao.getRevenueYear(), form17Dao.getMakhtaKhataNo(), 'Y');
		DatabaseContextHolder.restoreCtx();
		if (form17 == null) {
			throw new ResourceNotFoundException("error.form.notfound", messageSource, locale);
		}
		BeanUtils.copyProperties(form17, form17Dao);
		return form17Dao;
	}

/*	@Override
	public void updateForm17(Form17Dao form17Dao, HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		Form17 form17 = form17Repository.findById(form17Dao.getId())
				.orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale,
						"Form17", form17Dao.getId()));
		BeanUtils.copyProperties(form17Dao, form17);
		if (form17Dao.getPeriodFromDate() != null && form17Dao.getPeriodToDate() != null) {
			Period period = Period.between(form17Dao.getPeriodFromDate(), form17Dao.getPeriodToDate());
			String periodInFormat = (period.getYears() < 1 ? ""
					: (period.getYears() + (period.getYears() > 1 ? " वर्षे" : " वर्ष")))
					+ (period.getMonths() < 1 ? ""
							: (period.getMonths() + (period.getMonths() > 1 ? " महिने" : " महिना")))
					+ (period.getDays() < 1 ? "" : period.getDays() + 1 + " दिवस");
			form17.setPeriodIfMoreThanOneYear(periodInFormat);
			form17.setIsAmountPaid('N');
		}
		if (form17Dao.getKhataNo() != null) {
			form17.setKhataType("R");
		}
		if (form17Dao.getMakhtaKhataNo() != null) {
			form17.setKhataType("M");
		}

		BigDecimal jmAmount = ((form17Dao.getLocalCessAmount() == null ? BigDecimal.ZERO
				: form17Dao.getLocalCessAmount())
						.add(form17Dao.getOtherLocalCessAmount() == null ? BigDecimal.ZERO
								: form17Dao.getOtherLocalCessAmount()));
		form17.setAmountOfJm(jmAmount);

		BigDecimal gpAmount = (form17Dao.getLocalCessAmount() == null ? BigDecimal.ZERO
				: form17Dao.getLocalCessAmount());
		form17.setAmountOfGp(gpAmount);

		BigDecimal zpAmount = ((form17Dao.getLocalCessAmount() == null ? BigDecimal.ZERO
				: form17Dao.getLocalCessAmount()).multiply(BigDecimal.valueOf(7)));
		form17.setAmountOfZp(zpAmount);

		form17.setAssessment((jmAmount).add(gpAmount).add(zpAmount));

		if (form17Dao.getId() != null) {
			
			
			
			List<Object[]> lrdetails = form17Repository.getDetailsById(request.getHeader("echHost"),
					request.getHeader("echDbName"), request.getHeader("echSchemaName"), CommonConstants.echUser,
					CommonConstants.echPassword, form17Dao.getcCode(), form17Dao.getId());
			
			for (Object[] row : lrdetails) {
			    for (Object obj : row) {
			        System.out.print(obj + " ");
			    }
			    System.out.println();
			}
			
			try {
				List<Form17Dao> lst = lrdetails.stream().map(r -> {
					Form17Dao f17 = new Form17Dao();
//					form17.setKhataNo(r[2].toString());
//					form17.setPersonLiable(r[3].toString());
//					form17.setSurveyHissaNo(r[4].toString());
//					form17.setTotalArea(BigDecimal.valueOf(Double.valueOf(r[5].toString())));
//					form17.setUomOfTotalArea(r[6].toString());
//					form17.setUomOfAreaAffected(r[7].toString());
					
					form17.setKhataNo(r[2] != null ? r[2].toString() : "");
					form17.setPersonLiable(r[3] != null ? r[3].toString() : "");
					form17.setSurveyHissaNo(r[4] != null ? r[4].toString() : "");

					try {
					    form17.setTotalArea(r[5] != null 
					        ? BigDecimal.valueOf(Double.parseDouble(r[5].toString()))
					        : BigDecimal.ZERO);
					} catch (NumberFormatException e) {
					    form17.setTotalArea(BigDecimal.ZERO);
					}

					form17.setUomOfTotalArea(r[6] != null ? r[6].toString() : "");
					form17.setUomOfAreaAffected(r[7] != null ? r[7].toString() : "");
					
					
					return f17;
				}).collect(Collectors.toList());	
				
			}catch (Exception e) {
				
				System.out.println("Error at Makhtakhata update-----------:"+ e.getMessage());
			}
			
		}
		
		System.out.println("Before Data saving form17-----------------");
		
		form17Repository.save(form17);
		DatabaseContextHolder.restoreCtx();
	}*/
	
	
	@Override
	public void updateForm17(Form17Dao form17Dao, HttpServletRequest request, Locale locale) {
	    dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));

	   
	    Form17 form17 = form17Repository.findById(form17Dao.getId())
	            .orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale,
	                    "Form17", form17Dao.getId()));

	   
	    BeanUtils.copyProperties(form17Dao, form17, "personLiable", "khataType", "khataNo", "surveyHissaNo",
	            "totalArea", "uomOfTotalArea", "uomOfAreaAffected");

	   
	    if (form17Dao.getPeriodFromDate() != null && form17Dao.getPeriodToDate() != null) {
	        Period period = Period.between(form17Dao.getPeriodFromDate(), form17Dao.getPeriodToDate());
	        String periodInFormat = (period.getYears() < 1 ? ""
	                : (period.getYears() + (period.getYears() > 1 ? " वर्षे" : " वर्ष")))
	                + (period.getMonths() < 1 ? ""
	                        : (period.getMonths() + (period.getMonths() > 1 ? " महिने" : " महिना")))
	                + (period.getDays() < 1 ? "" : period.getDays() + 1 + " दिवस");
	        form17.setPeriodIfMoreThanOneYear(periodInFormat);
	        form17.setIsAmountPaid('N');
	    }

	   
	    if (form17Dao.getKhataNo() != null) {
	        form17.setKhataType("R");
	    }
	    if (form17Dao.getMakhtaKhataNo() != null) {
	        form17.setKhataType("M");
	    }

	  
	    BigDecimal jmAmount = (form17Dao.getLocalCessAmount() == null ? BigDecimal.ZERO : form17Dao.getLocalCessAmount())
	            .add(form17Dao.getOtherLocalCessAmount() == null ? BigDecimal.ZERO : form17Dao.getOtherLocalCessAmount());
	    form17.setAmountOfJm(jmAmount);

	    BigDecimal gpAmount = form17Dao.getLocalCessAmount() == null ? BigDecimal.ZERO : form17Dao.getLocalCessAmount();
	    form17.setAmountOfGp(gpAmount);

	    BigDecimal zpAmount = (form17Dao.getLocalCessAmount() == null ? BigDecimal.ZERO : form17Dao.getLocalCessAmount())
	            .multiply(BigDecimal.valueOf(7));
	    form17.setAmountOfZp(zpAmount);

	    form17.setAssessment(jmAmount.add(gpAmount).add(zpAmount));

	   
	    if (form17Dao.getId() != null) {
	        List<Object[]> lrdetails = form17Repository.getDetailsById(
	                request.getHeader("echHost"),
	                request.getHeader("echDbName"),
	                request.getHeader("echSchemaName"),
	                CommonConstants.echUser,
	                CommonConstants.echPassword,
	                form17Dao.getcCode(),
	                form17Dao.getId());

	        if (!lrdetails.isEmpty()) {
	            Object[] r = lrdetails.get(0); // take first record

	            // Safely set properties, handling nulls
	            form17.setKhataNo(r[2] != null ? r[2].toString() : "");

	            if ("R".equals(form17.getKhataType())) {
	                form17.setPersonLiable(r[3] != null ? r[3].toString() : "");
	            } else if ("M".equals(form17.getKhataType())) {
	                // Use personLiable from incoming DTO if present
	                form17.setPersonLiable(form17Dao.getPersonLiable() != null ? form17Dao.getPersonLiable() : "");
	            }

	            form17.setSurveyHissaNo(r[4] != null ? r[4].toString() : "");

	            try {
	            	
	            	 if ("R".equals(form17.getKhataType())) {
	            		 form17.setTotalArea(r[5] != null ? BigDecimal.valueOf(Double.parseDouble(r[5].toString())) : BigDecimal.ZERO);

	 	            } else if ("M".equals(form17.getKhataType())) {
	 	                // Use personLiable from incoming DTO if present
	 	            	
	 	            	form17.setTotalArea(form17Dao.getTotalArea() != null ? form17Dao.getTotalArea() : BigDecimal.ZERO);

	 	            	
	 	            }
	            	
	            	
	                	                
	            } catch (NumberFormatException e) {
	                form17.setTotalArea(BigDecimal.ZERO);
	            }

	            form17.setUomOfTotalArea(r[6] != null ? r[6].toString() : "");
	            form17.setUomOfAreaAffected(r[7] != null ? r[7].toString() : "");
	        } else {
	            throw new ExchangeException("Please Put Valid Records to Update");
	        }
	    }

	    // Save updated entity
	    form17Repository.save(form17);
	    DatabaseContextHolder.restoreCtx();
	}

	@Override
	public void deleteForm17ById(long id, HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		Form17 form17 = form17Repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Form17", id));
//		form17.setActiveFlag('N');
//		form17Repository.save(form17);
		// 1/04/24 -> receipt delete
		if ((form17.getIsAmountPaid()).equals('Y')) {
			System.out.println("____________INSIDE ");
			throw new ResourceNotFoundException("error.form17.delete");

		} else {
			System.out.println("____________OUTSIDE ");

			form17.setActiveFlag('N');
			form17Repository.save(form17);
		}

		if (form17.getMakhtaKhataNo() != null) {
			deleteLandRevenueDemand(form17);
		} else if (form17.getKhataNo() != null) {
			updateLandRevenueDemand(form17);
		}
		DatabaseContextHolder.restoreCtx();
	}

	private void deleteLandRevenueDemand(Form17 form17) {
		LandRevenueDemand demand = landRevenueDemandRepository.findBycCodeAndMakhtaKhataNoAndRevenueYear(
				form17.getcCode(), form17.getMakhtaKhataNo(), form17.getRevenueYear());

		//		if (demand != null) {
		
		if (demand == null) {
		    throw new ResourceNotFoundException("error.form17.demandIsNull");
		}

		System.out.println("Demand ------------{ } "+demand);
		
		boolean var = demand.getReceiptNo() != null && !demand.getReceiptNo().trim().isEmpty();
		boolean var2 = !demand.getReceiptNo().trim().isEmpty();
		
		System.out.println("var------------  : "+var+"---------var2---------------  : "+var2);
		
		if (demand.getReceiptNo() != null && !demand.getReceiptNo().trim().isEmpty()) {
			throw new ResourceNotFoundException("error.form17.delete");
		}
		
			landRevenueDemandRepository.delete(demand);
			
		
	}

	private void updateLandRevenueDemand(Form17 form17) {
		LandRevenueDemand demand = landRevenueDemandRepository.findBycCodeAndKhataNoAndRevenueYearAndActiveFlag(form17.getcCode(),
				form17.getKhataNo(), form17.getRevenueYear(), 'Y');
		
		

		if (demand == null) {
		    throw new ResourceNotFoundException("error.form17.demandIsNull");
		}
		
		System.out.println("Demand ------------{ } "+demand);
		
		boolean var = demand.getReceiptNo() != null && !demand.getReceiptNo().trim().isEmpty();
		boolean var2 = !demand.getReceiptNo().trim().isEmpty();
		
		System.out.println("var------------  : "+var+"---------var2---------------  : "+var2);
		
		if (demand.getReceiptNo() != null && !demand.getReceiptNo().trim().isEmpty()) {
			throw new ResourceNotFoundException("error.form17.delete");
		}
		

	//	if (demand != null) {
			// Update the financial fields
			BigDecimal zero = BigDecimal.ZERO;
			
			demand.setNetAmount(demand.getNetAmount()
					.subtract(demand.getGpSankirn().add(demand.getJmSankirn()).add(demand.getZpSankirn())));
			demand.setGpSankirn(zero);
			demand.setJmSankirn(zero);
			demand.setZpSankirn(zero);

			// Save the updated demand entity
			landRevenueDemandRepository.save(demand);
	//	}
			
}

	private Form17Dao toDao(Form17 form17) {
		Form17Dao form17Dao = new Form17Dao();
		BeanUtils.copyProperties(form17, form17Dao, "periodFromDate", "periodToDate");
		if (form17.getPeriodFromDate() != null) {
			form17Dao.setPeriodFromDate(form17.getPeriodFromDate());
		}
		if (form17.getPeriodToDate() != null) {
			form17Dao.setPeriodToDate(form17.getPeriodToDate());
		}
		return form17Dao;
	}

	private Form17 toEntity(Form17Dao form17Dao) {
		Form17 form17 = new Form17();
		BeanUtils.copyProperties(form17Dao, form17, "id");
		if (form17Dao.getPeriodFromDate() != null && form17Dao.getPeriodToDate() != null) {
			Period period = Period.between(form17Dao.getPeriodFromDate(), form17Dao.getPeriodToDate());
			String periodInFormat = (period.getYears() < 1 ? ""
					: (period.getYears() + (period.getYears() > 1 ? " वर्षे" : " वर्ष")))
					+ (period.getMonths() < 1 ? ""
							: (period.getMonths() + (period.getMonths() > 1 ? " महिने" : " महिना")))
					+ (period.getDays() < 1 ? "" : period.getDays() + 1 + " दिवस");
			form17.setPeriodIfMoreThanOneYear(periodInFormat);
			form17.setIsAmountPaid('N');
		}
		form17.setAmountOfGp(form17Dao.getAmountOfGp() == null ? BigDecimal.ZERO : form17Dao.getAmountOfGp());
		form17.setAmountOfZp(form17Dao.getAmountOfZp() == null ? BigDecimal.ZERO : form17Dao.getAmountOfZp());
		return form17;
	}

	// get Form17 and Form4 Report Details
	@Override
	public Response getForm17ReportDetails(String cCode, String revenueYear, HttpServletRequest request,
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
			if (r.getcCode().equals(cCode)) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(cCode)) {
			List<Form17> form17 = form17Repository.findBycCodeAndRevenueYearAndActiveFlagOrderByKhataNoAsc(cCode,
					revenueYear, 'Y');
			DatabaseContextHolder.restoreCtx();
			if (Collections.isEmpty(form17)) {
				throw new ResourceNotFoundException("error.form.reportdetails", messageSource, locale);
			}
			Response form17Response = new Response();
			form17Response.setForm17Data(form17.stream().map(form17AR -> toDao(form17AR)).collect(Collectors.toList()));
			return form17Response;
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	@Override
	public Response getReport4Details(String cCode, String revenueYear, HttpServletRequest request, Locale locale) {
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
			List<Object[]> form17 = new ArrayList<>();
			form17 = form17Repository.getForm4Report(cCode, revenueYear, 'Y');
			DatabaseContextHolder.restoreCtx();
			if (Collections.isEmpty(form17)) {
				throw new ResourceNotFoundException("error.form.reportdetails", messageSource, locale);
			}
			Response response1 = new Response();
			response1.setForm17Data(form17.stream().map((object) -> {
				Form17Dao form17Dao = new Form17Dao();
				form17Dao.setId(Long.valueOf(object[0].toString()));
				form17Dao.setRevenueYear(object[1].toString());
				form17Dao.setcCode(object[2].toString());
				form17Dao.setKhataNo(object[3] == null ? "" : object[3].toString());
				form17Dao.setPersonLiable(object[4].toString());
				form17Dao.setSurveyHissaNo(object[5] == null ? "" : object[5].toString());
				form17Dao.setTotalArea(
						object[6] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(object[6].toString())));
				form17Dao.setUomOfTotalArea(object[7] == null ? "" : object[7].toString());
				form17Dao.setCaseNo(object[8] == null ? "" : object[8].toString());
				form17Dao.setAreaAffected(
						object[9] == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(object[9].toString())));
				form17Dao.setUomOfAreaAffected(object[10] == null ? "" : object[10].toString());
				form17Dao.setNatureOfCase(object[11] == null ? "" : object[11].toString());
				form17Dao.setPeriodIfMoreThanOneYear(object[12] == null ? "" : object[12].toString());
				if (object[13] != null) {
					form17Dao.setPeriodFromDate(LocalDate.parse(object[13].toString()));
				}

				if (object[14] != null) {
					form17Dao.setPeriodToDate(LocalDate.parse(object[14].toString()));
				}
				form17Dao.setLocalCess(object[15] == null ? "" : object[15].toString());
				form17Dao.setOtherLocalCess(object[16] == null ? "" : object[16].toString());
				form17Dao.setLocalCessAmount(object[17] == null ? BigDecimal.ZERO
						: BigDecimal.valueOf(Double.valueOf(object[17].toString())));
				form17Dao.setOtherLocalCessAmount(object[18] == null ? BigDecimal.ZERO
						: BigDecimal.valueOf(Double.valueOf(object[18].toString())));
				form17Dao.setAmountOfJm(object[19] == null ? BigDecimal.ZERO
						: BigDecimal.valueOf(Double.valueOf(object[19].toString())));
				form17Dao.setAmountOfZp(object[20] == null ? BigDecimal.ZERO
						: BigDecimal.valueOf(Double.valueOf(object[20].toString())));
				form17Dao.setAmountOfGp(object[21] == null ? BigDecimal.ZERO
						: BigDecimal.valueOf(Double.valueOf(object[21].toString())));
				form17Dao.setAssessment(object[22] == null ? BigDecimal.ZERO
						: BigDecimal.valueOf(Double.valueOf(object[22].toString())));
				form17Dao.setRemarks(object[23] == null ? "" : object[23].toString());
				form17Dao.setMakhtaKhataNo(object[24] == null ? "" : object[24].toString());
				return form17Dao;
			}).collect(Collectors.toList()));
			return response1;
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	@Override
	public Response getReport4GhoshwaraDetails(String cCode, String revenueYear, HttpServletRequest request,
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
			if (r.getcCode().equals(cCode)) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(cCode)) {
			List<Object[]> form17Ghoshwara = new ArrayList<>();
			form17Ghoshwara = form17Repository.getForm4GhoshwaraReport(request.getHeader("echHost"),
					request.getHeader("echDbName"), request.getHeader("echSchemaName"), CommonConstants.echUser,
					CommonConstants.echPassword, cCode, revenueYear, 'Y');
			DatabaseContextHolder.restoreCtx();
			if (Collections.isEmpty(form17Ghoshwara)) {
				throw new ResourceNotFoundException("error.form.reportdetails", messageSource, locale);
			}
			Response response1 = new Response();
			response1.setForm17Data(form17Ghoshwara.stream().map((object) -> {
				Form17Dao form17Dao = new Form17Dao();
				form17Dao.setcCode(object[0].toString());
				form17Dao.setEkunSthanikUpkar(object[1] == null ? "0" : object[1].toString());
				form17Dao.setSthanikUpkarAslela(object[2] == null ? "0" : object[2].toString());
				form17Dao.setSthanikUpkarNaslela(object[3] == null ? "0" : object[3].toString());
				form17Dao.setJaminMahsul(object[4] == null ? "0" : object[4].toString());
				form17Dao.setEkunJaminMahsul(object[5] == null ? "0" : object[5].toString());
				form17Dao.setShillak(object[6] == null ? "0" : object[6].toString());
				form17Dao.setVasuli(object[7] == null ? "0" : object[7].toString());
				return form17Dao;
			}).collect(Collectors.toList()));
			return response1;
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

	@Override
	public Response getDetailBykhataNo(String cCode,String khataNo,HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
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
//			if(r.getcCode().equals(cCode)) {
//			ccodes=r.getcCode();
//			break;
//			}else{
//				ccodes="0";
//				}}
//		
//		if(ccodes.equals(cCode)) {	
		List<Object[]> form17=form17Repository.findByKhataNo(
				CommonConstants.AugDatamhrHost,request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				cCode,khataNo,CommonConstants.AugDataPort
				);
		LandRevenueDemand demand = landRevenueDemandRepository.findBycCodeAndKhataNoAndActiveFlag(cCode, khataNo,'Y');
		if (demand.getReceiptNo() != null) {
			throw new ResourceNotFoundException("error.form17.receiptIsGenerated", messageSource, locale);}
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(form17)) {
			throw new ResourceNotFoundException("error.form.khata",messageSource,locale);
		}else {
			if (demand != null) {
			Response form17Response =new  Response();
			form17Response.setForm17Data(form17.stream().map((object) -> {
				Form17Dao form17Dao = new Form17Dao();
				form17Dao.setKhataNo(object[0].toString());
				form17Dao.setPersonLiable(object[1].toString());
				form17Dao.setTotalArea(BigDecimal.valueOf(Double.valueOf(object[2].toString())));
				form17Dao.setSurveyHissaNo(object[3].toString());
				form17Dao.setUomOfTotalArea(object[4].toString());
				return form17Dao;
			}).collect(Collectors.toList()));
			return form17Response;
		}else {
			throw new ResourceNotFoundException("error.form17.demandIsNotGenerated", messageSource, locale);
		}
			}
//		}else {
//			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
//		}
	}

	@Override
	public List<Object> khataNmbers(String cCode, String revenueYear, HttpServletRequest request) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		List<Object> form17 = form17Repository.findKhataNo(request.getHeader("echHost"), request.getHeader("echDbName"),
				request.getHeader("echSchemaName"), CommonConstants.echUser, CommonConstants.echPassword, cCode,
				revenueYear, 'Y');
		DatabaseContextHolder.restoreCtx();
		return form17;
	}

	@Override
	public Response getReceiptDetailByMaktaKhataNoAndMaktakhataOwnerName(Form17Dao form17Dao,
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
			if (r.getcCode().equals(form17Dao.getcCode())) {
				ccodes = r.getcCode();
				break;
			} else {
				ccodes = "0";
			}
		}

		if (ccodes.equals(form17Dao.getcCode())) {
			List<Object[]> form17 = new ArrayList<>();
			if (form17Dao.getKhataOwnerName() != null) {
				form17 = form17Repository.findByMaktaKhataOwnerName(request.getHeader("echHost"),
						request.getHeader("echDbName"), request.getHeader("echSchemaName"), CommonConstants.echUser,
						CommonConstants.echPassword, form17Dao.getcCode(), form17Dao.getKhataOwnerName(),
						form17Dao.getRevenueYear(), 'Y');
			} else {
				form17 = form17Repository.findByMaktaKhataNo(request.getHeader("echHost"),
						request.getHeader("echDbName"), request.getHeader("echSchemaName"), CommonConstants.echUser,
						CommonConstants.echPassword, form17Dao.getcCode(), form17Dao.getMakhtaKhataNo(),
						form17Dao.getRevenueYear(), 'Y');
			}

			DatabaseContextHolder.restoreCtx();
			if (form17.isEmpty()) {
				throw new ResourceNotFoundException("error.form.getData", messageSource, locale);
			} else {
				Response form17Response = new Response();
				form17Response.setForm17Data(form17.stream().map((object) -> {
					Form17Dao form17D = new Form17Dao();
					form17D.setId(Long.valueOf(object[0].toString()));
					form17D.setRevenueYear(object[6].toString());
					form17D.setDistrictCode(Integer.valueOf(object[7].toString()));
					form17D.setTalukaCode(Integer.valueOf(object[8].toString()));
					form17D.setcCode(object[9].toString());
					form17D.setKhataNo(object[10] == null ? "" : object[10].toString());
					form17D.setPersonLiable(object[11].toString());
					form17D.setSurveyHissaNo(object[12] == null ? "" : object[12].toString());
					form17D.setTotalArea(object[13] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[13].toString())));
					form17D.setUomOfTotalArea(object[14] == null ? "" : object[14].toString());
					form17D.setCaseNo(object[15] == null ? "" : object[15].toString());
					form17D.setAreaAffected(object[16] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[16].toString())));
					form17D.setUomOfAreaAffected(object[17] == null ? "" : object[17].toString());
					form17D.setNatureOfCase(object[18] == null ? "" : object[18].toString());
					form17D.setPeriodIfMoreThanOneYear(object[19] == null ? "" : object[19].toString());
					if (object[20] != null) {
						form17D.setPeriodFromDate(LocalDate.parse(object[20].toString()));
					}

					if (object[21] != null) {
						form17D.setPeriodToDate(LocalDate.parse(object[21].toString()));
					}
					form17D.setLocalCess(object[22] == null ? "" : object[22].toString());
					form17D.setOtherLocalCess(object[23] == null ? "" : object[23].toString());
					form17D.setOtherLocalCessDesc(object[24] == null ? "" : object[24].toString());
					form17D.setLocalCessAmount(object[25] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[25].toString())));
					form17D.setOtherLocalCessAmount(object[26] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[26].toString())));
					form17D.setAmountOfJm(object[27] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[27].toString())));
					form17D.setAmountOfZp(object[28] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[28].toString())));
					form17D.setAmountOfGp(object[29] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[29].toString())));
					form17D.setAssessment(object[30] == null ? BigDecimal.ZERO
							: BigDecimal.valueOf(Double.valueOf(object[30].toString())));
					form17D.setRemarks(object[31] == null ? "" : object[31].toString());
					form17D.setMakhtaKhataNo(object[36] == null ? "" : object[36].toString());
					return form17D;
				}).collect(Collectors.toList()));
				return form17Response;
			}
		} else {
			throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
		}
	}

}