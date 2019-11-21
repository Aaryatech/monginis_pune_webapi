package com.ats.webapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderDeliveryDate {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private int frId;
	private double ttlAmt;
	private String frName;
	public int getFrId() {
		return frId;
	}
	public void setFrId(int frId) {
		this.frId = frId;
	}
	public double getTtlAmt() {
		return ttlAmt;
	}
	public void setTtlAmt(double ttlAmt) {
		this.ttlAmt = ttlAmt;
	}
	public String getFrName() {
		return frName;
	}
	public void setFrName(String frName) {
		this.frName = frName;
	}
	@Override
	public String toString() {
		return "OrderDeliveryDate [frId=" + frId + ", ttlAmt=" + ttlAmt + ", frName=" + frName + "]";
	}
	
	
	
}
