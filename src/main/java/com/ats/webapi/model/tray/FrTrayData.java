package com.ats.webapi.model.tray;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FrTrayData {
	

	@Id
	private String id;
	private int small;
	private int lead;
	private int big;
	private String trayDate;
	private int headerId;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSmall() {
		return small;
	}
	public void setSmall(int small) {
		this.small = small;
	}
	public int getLead() {
		return lead;
	}
	public void setLead(int lead) {
		this.lead = lead;
	}
	public int getBig() {
		return big;
	}
	public void setBig(int big) {
		this.big = big;
	}
	public String getTrayDate() {
		return trayDate;
	}
	public void setTrayDate(String trayDate) {
		this.trayDate = trayDate;
	}
	
	
	
	public int getHeaderId() {
		return headerId;
	}
	public void setHeaderId(int headerId) {
		this.headerId = headerId;
	}
	
	
	@Override
	public String toString() {
		return "FrTrayData [id=" + id + ", small=" + small + ", lead=" + lead + ", big=" + big + ", trayDate="
				+ trayDate + ", headerId=" + headerId + "]";
	}
	
	
	

}
