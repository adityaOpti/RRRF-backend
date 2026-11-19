package com.probity.echawdi.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.probity.echawdi.dao.KhataMergeDao;

public interface KhataMergeService {

	KhataMergeDao getKhataMergeDtl(String cCode,String revenueYear, Locale locale,HttpServletRequest request);

	KhataMergeDao saveKhataMerge(KhataMergeDao khataMergeDao, Locale locale,HttpServletRequest request);

	KhataMergeDao deleteMergeDtlByParentId(long parentId, Locale locale,HttpServletRequest request);

	KhataMergeDao getKhataMergeDtlReport(String cCode, Locale locale,HttpServletRequest request);
}
