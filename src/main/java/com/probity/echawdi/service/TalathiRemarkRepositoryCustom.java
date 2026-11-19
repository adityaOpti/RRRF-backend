package com.probity.echawdi.service;

import java.util.List;

public interface TalathiRemarkRepositoryCustom {
	
	List<Object[]> getTalathiRemarkInspection(
            String host,
            String dbName,
            String schema,
            String user,
            String password,
            String ccode,
            String revenueYear);

}
