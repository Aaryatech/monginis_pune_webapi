package com.ats.webapi.model.tray;

import java.util.List;

public class FrHomeData {
	
	private FrTrayData openingCount;
	
	private List<FrTrayData> receivedTrayList;
	
	private List<FrTrayData> returnTrayList;

	public FrTrayData getOpeningCount() {
		return openingCount;
	}

	public void setOpeningCount(FrTrayData openingCount) {
		this.openingCount = openingCount;
	}

	public List<FrTrayData> getReceivedTrayList() {
		return receivedTrayList;
	}

	public void setReceivedTrayList(List<FrTrayData> receivedTrayList) {
		this.receivedTrayList = receivedTrayList;
	}

	public List<FrTrayData> getReturnTrayList() {
		return returnTrayList;
	}

	public void setReturnTrayList(List<FrTrayData> returnTrayList) {
		this.returnTrayList = returnTrayList;
	}
	
	
	
	

}
