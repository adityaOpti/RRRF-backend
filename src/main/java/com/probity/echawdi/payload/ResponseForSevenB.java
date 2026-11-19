package com.probity.echawdi.payload;

import java.util.List;

import com.probity.echawdi.dao.EpeekResultDao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseForSevenB {
	public List<EpeekResultDao> epeekResultData;
}