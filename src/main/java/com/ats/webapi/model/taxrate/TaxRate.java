package com.ats.webapi.model.taxrate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="m_tax_rate")
public class TaxRate implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tax_rate_id")
	int taxRateId;
	
	int catId;
	
	int subCatId;
	
	String hsnCode;
	
	float cgstPer;
	
	float sgstPer;
	
	float igstPer;
	
	int uom;
	
	int delStatus;

	public int getTaxRateId() {
		return taxRateId;
	}

	public int getCatId() {
		return catId;
	}

	public int getSubCatId() {
		return subCatId;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public float getCgstPer() {
		return cgstPer;
	}

	public float getSgstPer() {
		return sgstPer;
	}

	public float getIgstPer() {
		return igstPer;
	}

	
	public int getDelStatus() {
		return delStatus;
	}

	public void setTaxRateId(int taxRateId) {
		this.taxRateId = taxRateId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public void setSubCatId(int subCatId) {
		this.subCatId = subCatId;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public void setCgstPer(float cgstPer) {
		this.cgstPer = cgstPer;
	}

	public void setSgstPer(float sgstPer) {
		this.sgstPer = sgstPer;
	}

	public void setIgstPer(float igstPer) {
		this.igstPer = igstPer;
	}

	
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getUom() {
		return uom;
	}

	public void setUom(int uom) {
		this.uom = uom;
	}

	@Override
	public String toString() {
		return "TaxRate [taxRateId=" + taxRateId + ", catId=" + catId + ", subCatId=" + subCatId + ", hsnCode="
				+ hsnCode + ", cgstPer=" + cgstPer + ", sgstPer=" + sgstPer + ", igstPer=" + igstPer + ", uom=" + uom
				+ ", delStatus=" + delStatus + "]";
	}
	
	
	
	
}
