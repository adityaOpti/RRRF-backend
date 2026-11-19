package com.probity.echawdi.dao;

public class EpeekResultDao {

	private String cCode;

	private float epeekPahaniArea;

	private int khataNo;

	private int seasonCode;

	private String hissaNo;

	public String getcCode() {
		return cCode;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
	}

	public int getKhataNo() {
		return khataNo;
	}

	public void setKhataNo(int khataNo) {
		this.khataNo = khataNo;
	}

	public int getSeasonCode() {
		return seasonCode;
	}

	public void setSeasonCode(int seasonCode) {
		this.seasonCode = seasonCode;
	}

	public String getHissaNo() {
		return hissaNo;
	}

	public void setHissaNo(String hissaNo) {
		this.hissaNo = hissaNo;
	}

	public float getEpeekPahaniArea() {
		return epeekPahaniArea;
	}

	public void setEpeekPahaniArea(float epeekPahaniArea) {
		this.epeekPahaniArea = epeekPahaniArea;
	}

	public EpeekResultDao(String cCode, float epeekPahaniArea, int khataNo, int seasonCode, String hissaNo) {
		super();
		this.cCode = cCode;
		this.epeekPahaniArea = epeekPahaniArea;
		this.khataNo = khataNo;
		this.seasonCode = seasonCode;
		this.hissaNo = hissaNo;
	}

	public EpeekResultDao() {
		super();
	}
}
