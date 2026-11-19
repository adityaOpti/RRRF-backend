package com.probity.echawdi.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.Form7ADao;
import com.probity.echawdi.dao.Form7Dao;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.repository.ApplicationDetailsOfUserRepository;
import com.probity.echawdi.service.SurveyHissaAndKhataNo;
import com.probity.echawdi.utils.CommonConstants;

import io.jsonwebtoken.lang.Collections;

@Service
public class SurveyHissaAndKhataNoImpl implements SurveyHissaAndKhataNo {

	@Autowired
	private ApplicationDetailsOfUserRepository applicationRepository;

	@Override
	public List<Form7Dao> getHissaNo(String mhrDbName, String mhrSchemaName, String mhrhost, String cCode, String pin,
			Character marked) {
		List<Object[]> lstHissanNoDtl = applicationRepository.getHissa(mhrhost, mhrDbName, mhrSchemaName,
				CommonConstants.mhrorUser, CommonConstants.mhrorPassword, cCode, pin, marked, CommonConstants.DefaultDataPort);
		DatabaseContextHolder.restoreCtx();
		if (Collections.isEmpty(lstHissanNoDtl)) {
		
			throw new ResourceNotFoundException("error.form.villageandsurveyno");
		} else {
			if (lstHissanNoDtl != null && lstHissanNoDtl.size() > 0) {
				List<Form7Dao> lstHissaNo = lstHissanNoDtl.stream().map((object) -> {
					Form7Dao form7Dao = new Form7Dao();
					form7Dao.setPin(object[0].toString());
					form7Dao.setHissaNo(object[1].toString());
					return form7Dao;
				}).collect(Collectors.toList());
				return lstHissaNo;
			}
			return null;
		}

	}

	@Override
	public List<Form7ADao> getPotHissa(String mhrDbName, String mhrSchemaName, String mhrhost, String cCode, String pin,
			String hissaNo) {

		List<Object[]> form7ADtl = applicationRepository.getPotHissa(mhrhost, mhrDbName, mhrSchemaName,
				CommonConstants.mhrorUser, CommonConstants.mhrorPassword, cCode, pin, hissaNo);
		DatabaseContextHolder.restoreCtx();
		if (Collections.isEmpty(form7ADtl)) {
			throw new ResourceNotFoundException("error.form.eferfar");
		} else {
			List<Form7ADao> lstForm7A = form7ADtl.stream().map((object) -> {
				Form7ADao form7ADao = new Form7ADao();
				form7ADao.setcCode(object[0].toString());
				form7ADao.setPin(object[1].toString());
				form7ADao.setLandlordName(object[2].toString());
				form7ADao.setTotalArea(object[3].toString());
				form7ADao.setAssessment(object[4].toString());
				form7ADao.setHissaNo(object[5].toString());
				form7ADao.setKhataNo(object[6].toString());
				return form7ADao;
			}).collect(Collectors.toList());
			return lstForm7A;
		}

	}

	@Override
	public List<Form7ADao> getKhataNo(String mhrDbName, String mhrSchemaName, String mhrhost, String ccode, String pin,
			String hissaNo, String khataNo) {

		List<Object[]> form7ADtl = applicationRepository.getKhataNo(mhrhost, mhrDbName, mhrSchemaName,
				CommonConstants.mhrorUser, CommonConstants.mhrorPassword, ccode, pin, hissaNo,khataNo);
		DatabaseContextHolder.restoreCtx();
		if (Collections.isEmpty(form7ADtl)) {
			throw new ResourceNotFoundException("error.form.eferfar");
		} else {	
			List<Form7ADao> lstForm7A = form7ADtl.stream().map((object) -> {
				Form7ADao form7ADao = new Form7ADao();
				form7ADao.setcCode(object[0].toString());
				form7ADao.setPin(object[1].toString());
				form7ADao.setLandlordName(object[2].toString());
				form7ADao.setTotalArea(object[3].toString());
				form7ADao.setAssessment(object[4].toString());
				form7ADao.setHissaNo(object[5].toString());
				form7ADao.setKhataNo(object[6].toString());
				return form7ADao;
			}).collect(Collectors.toList());
			return lstForm7A;
		}

	}

}
