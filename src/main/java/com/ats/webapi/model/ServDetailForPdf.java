package com.ats.webapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ServDetailForPdf {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "serv_detail_id")
	private int servDetailId; 
	
	@Column(name = "serv_id")
	private int servId;
	
	@Column(name = "serv_date")
	private Date servDate;

	@Column(name = "serv_type")
	private int servType;  

	@Column(name = "group_id")
	private int groupId;
	
	@Column(name = "spr_id")
	private int sprId;

	@Column(name = "spr_qty")
	private int sprQty;

	@Column(name = "spr_rate")
	private int sprRate;
	
	@Column(name = "spr_taxable_amt")
	private float sprTaxableAmt;

	@Column(name = "spr_tax_amt")
	private float sprTaxAmt;  

	@Column(name = "total")
	private float total;
	
	@Column(name = "disc")
	private float disc;

	@Column(name = "extra_charges")
	private float extraCharges;

	@Column(name = "del_status")
	private int delStatus;
	
	@Column(name = "varchar1")
	private String varchar1;
	
	@Column(name = "varchar2")
	private String varchar2;
	
	@Column(name = "spr_name")
	private String sprName;
	
	@Column(name = "group_name")
	private String groupName;

	public int getServDetailId() {
		return servDetailId;
	}

	public void setServDetailId(int servDetailId) {
		this.servDetailId = servDetailId;
	}

	public int getServId() {
		return servId;
	}

	public void setServId(int servId) {
		this.servId = servId;
	}

	public Date getServDate() {
		return servDate;
	}

	public void setServDate(Date servDate) {
		this.servDate = servDate;
	}

	public int getServType() {
		return servType;
	}

	public void setServType(int servType) {
		this.servType = servType;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getSprId() {
		return sprId;
	}

	public void setSprId(int sprId) {
		this.sprId = sprId;
	}

	public int getSprQty() {
		return sprQty;
	}

	public void setSprQty(int sprQty) {
		this.sprQty = sprQty;
	}

	public int getSprRate() {
		return sprRate;
	}

	public void setSprRate(int sprRate) {
		this.sprRate = sprRate;
	}

	public float getSprTaxableAmt() {
		return sprTaxableAmt;
	}

	public void setSprTaxableAmt(float sprTaxableAmt) {
		this.sprTaxableAmt = sprTaxableAmt;
	}

	public float getSprTaxAmt() {
		return sprTaxAmt;
	}

	public void setSprTaxAmt(float sprTaxAmt) {
		this.sprTaxAmt = sprTaxAmt;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getDisc() {
		return disc;
	}

	public void setDisc(float disc) {
		this.disc = disc;
	}

	public float getExtraCharges() {
		return extraCharges;
	}

	public void setExtraCharges(float extraCharges) {
		this.extraCharges = extraCharges;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public String getVarchar1() {
		return varchar1;
	}

	public void setVarchar1(String varchar1) {
		this.varchar1 = varchar1;
	}

	public String getVarchar2() {
		return varchar2;
	}

	public void setVarchar2(String varchar2) {
		this.varchar2 = varchar2;
	}

	public String getSprName() {
		return sprName;
	}

	public void setSprName(String sprName) {
		this.sprName = sprName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String toString() {
		return "ServDetailForPdf [servDetailId=" + servDetailId + ", servId=" + servId + ", servDate=" + servDate
				+ ", servType=" + servType + ", groupId=" + groupId + ", sprId=" + sprId + ", sprQty=" + sprQty
				+ ", sprRate=" + sprRate + ", sprTaxableAmt=" + sprTaxableAmt + ", sprTaxAmt=" + sprTaxAmt + ", total="
				+ total + ", disc=" + disc + ", extraCharges=" + extraCharges + ", delStatus=" + delStatus
				+ ", varchar1=" + varchar1 + ", varchar2=" + varchar2 + ", sprName=" + sprName + ", groupName="
				+ groupName + "]";
	}
	
	

}
