package com.ats.webapi.model;

import java.util.List;

public class StaionListWithFranchiseeList {
	
	private int stationNo;
	private int itemCount;
	private List<FrList> list;
	public int getStationNo() {
		return stationNo;
	}
	public void setStationNo(int stationNo) {
		this.stationNo = stationNo;
	}
	public List<FrList> getList() {
		return list;
	}
	public void setList(List<FrList> list) {
		this.list = list;
	}
	
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	@Override
	public String toString() {
		return "StaionListWithFranchiseeList [stationNo=" + stationNo + ", itemCount=" + itemCount + ", list=" + list
				+ "]";
	}
	
	

}
