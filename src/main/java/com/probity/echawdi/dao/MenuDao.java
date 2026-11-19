package com.probity.echawdi.dao;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

/**
 * @author Kumar Harpude
 */

public class MenuDao {
	public Double id;
	public String name;
	public String path;
//	public Double routes;
	public String redirect;
	public Object icon;
	public String component;
	public List<MenuDao> routes;
}