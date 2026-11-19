package com.probity.echawdi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "season_code")
public class SeasonCode {

	@Id
	@Column(name = "season_code")
	private Integer Id;

	@Column(name = "season_name")
	private String seasonName;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	public SeasonCode(Integer id, String seasonName) {
		super();
		Id = id;
		this.seasonName = seasonName;
	}

	public SeasonCode() {
		super();
	}

}
