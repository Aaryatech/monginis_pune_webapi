package com.ats.webapi.model.tray;

public class FrTrayReportData {
	
	private String dateStr;
	
	private int openingSmall;
	private int openingLead;
	private int openingBig;

	private int receivedSmall;
	private int receivedLead;
	private int receivedBig;

	private int returnSmall;
	private int returnLead;
	private int returnBig;

	private int balSmall;
	private int balLead;
	private int balBig;
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public int getOpeningSmall() {
		return openingSmall;
	}
	public void setOpeningSmall(int openingSmall) {
		this.openingSmall = openingSmall;
	}
	public int getOpeningLead() {
		return openingLead;
	}
	public void setOpeningLead(int openingLead) {
		this.openingLead = openingLead;
	}
	public int getOpeningBig() {
		return openingBig;
	}
	public void setOpeningBig(int openingBig) {
		this.openingBig = openingBig;
	}
	public int getReceivedSmall() {
		return receivedSmall;
	}
	public void setReceivedSmall(int receivedSmall) {
		this.receivedSmall = receivedSmall;
	}
	public int getReceivedLead() {
		return receivedLead;
	}
	public void setReceivedLead(int receivedLead) {
		this.receivedLead = receivedLead;
	}
	public int getReceivedBig() {
		return receivedBig;
	}
	public void setReceivedBig(int receivedBig) {
		this.receivedBig = receivedBig;
	}
	public int getReturnSmall() {
		return returnSmall;
	}
	public void setReturnSmall(int returnSmall) {
		this.returnSmall = returnSmall;
	}
	public int getReturnLead() {
		return returnLead;
	}
	public void setReturnLead(int returnLead) {
		this.returnLead = returnLead;
	}
	public int getReturnBig() {
		return returnBig;
	}
	public void setReturnBig(int returnBig) {
		this.returnBig = returnBig;
	}
	public int getBalSmall() {
		return balSmall;
	}
	public void setBalSmall(int balSmall) {
		this.balSmall = balSmall;
	}
	public int getBalLead() {
		return balLead;
	}
	public void setBalLead(int balLead) {
		this.balLead = balLead;
	}
	public int getBalBig() {
		return balBig;
	}
	public void setBalBig(int balBig) {
		this.balBig = balBig;
	}
	@Override
	public String toString() {
		return "FrTrayReportData [dateStr=" + dateStr + ", openingSmall=" + openingSmall + ", openingLead="
				+ openingLead + ", openingBig=" + openingBig + ", receivedSmall=" + receivedSmall + ", receivedLead="
				+ receivedLead + ", receivedBig=" + receivedBig + ", returnSmall=" + returnSmall + ", returnLead="
				+ returnLead + ", returnBig=" + returnBig + ", balSmall=" + balSmall + ", balLead=" + balLead
				+ ", balBig=" + balBig + "]";
	}

	
	

}
