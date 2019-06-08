package com.ats.webapi.model.route;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RouteMgmtRepo extends JpaRepository<RouteMgmt, Integer> {

	List<RouteMgmt> findByDelStatusAndIsActive(int i, int j);

	RouteMgmt findByRouteTrayIdAndDelStatus(int routeTrayId, int i);

	@Transactional
	@Modifying
	@Query("UPDATE RouteMgmt SET  delStatus=1 WHERE route_tray_id=:routeTrayId")
	int deleteRouteMgmt(@Param("routeTrayId") int routeTrayId);

}
