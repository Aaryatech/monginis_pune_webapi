package com.ats.webapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CrnDetailsSummaryNew {

	@Id
	private String id;

	private int crnId;

	private String itemHsncd;

	private String itemHsncdesc;

	private int grnGvnQty;

	private float taxableAmt;

	private float cgstPer;

	private float cgstRs;

	private float sgstPer;

	private float sgstRs;

	private float igstPer;

	private float igstRs;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCrnId() {
		return crnId;
	}

	public void setCrnId(int crnId) {
		this.crnId = crnId;
	}

	public String getItemHsncd() {
		return itemHsncd;
	}

	public void setItemHsncd(String itemHsncd) {
		this.itemHsncd = itemHsncd;
	}

	public String getItemHsncdesc() {
		return itemHsncdesc;
	}

	public void setItemHsncdesc(String itemHsncdesc) {
		this.itemHsncdesc = itemHsncdesc;
	}

	public int getGrnGvnQty() {
		return grnGvnQty;
	}

	public void setGrnGvnQty(int grnGvnQty) {
		this.grnGvnQty = grnGvnQty;
	}

	public float getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(float taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	public float getCgstPer() {
		return cgstPer;
	}

	public void setCgstPer(float cgstPer) {
		this.cgstPer = cgstPer;
	}

	public float getCgstRs() {
		return cgstRs;
	}

	public void setCgstRs(float cgstRs) {
		this.cgstRs = cgstRs;
	}

	public float getSgstPer() {
		return sgstPer;
	}

	public void setSgstPer(float sgstPer) {
		this.sgstPer = sgstPer;
	}

	public float getSgstRs() {
		return sgstRs;
	}

	public void setSgstRs(float sgstRs) {
		this.sgstRs = sgstRs;
	}

	public float getIgstPer() {
		return igstPer;
	}

	public void setIgstPer(float igstPer) {
		this.igstPer = igstPer;
	}

	public float getIgstRs() {
		return igstRs;
	}

	public void setIgstRs(float igstRs) {
		this.igstRs = igstRs;
	}

	@Override
	public String toString() {
		return "CrnDetailsSummaryNew [id=" + id + ", crnId=" + crnId + ", itemHsncd=" + itemHsncd + ", itemHsncdesc="
				+ itemHsncdesc + ", grnGvnQty=" + grnGvnQty + ", taxableAmt=" + taxableAmt + ", cgstPer=" + cgstPer
				+ ", cgstRs=" + cgstRs + ", sgstPer=" + sgstPer + ", sgstRs=" + sgstRs + ", igstPer=" + igstPer
				+ ", igstRs=" + igstRs + "]";
	}

}
