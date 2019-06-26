package com.ats.webapi.repository.tray;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.webapi.model.tray.TrayMgtDetailInTray;

public interface TrayMgtDetailInTrayRepo extends JpaRepository<TrayMgtDetailInTray, Integer> {

	List<TrayMgtDetailInTray> findByFrIdAndIntrayDateAndDelStatus(int frId, String intrayDate, int i);

}
