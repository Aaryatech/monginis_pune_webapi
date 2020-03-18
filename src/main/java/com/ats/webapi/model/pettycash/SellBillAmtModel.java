package com.ats.webapi.model.pettycash;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SellBillAmtModel {

	@Id
	private int paymentMode;
	private float amt;

	public int getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(int paymentMode) {
		this.paymentMode = paymentMode;
	}

	public float getAmt() {
		return amt;
	}

	public void setAmt(float amt) {
		this.amt = amt;
	}

	@Override
	public String toString() {
		return "SellBillAmtModel [paymentMode=" + paymentMode + ", amt=" + amt + "]";
	}

}
