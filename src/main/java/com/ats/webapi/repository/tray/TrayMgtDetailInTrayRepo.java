package com.ats.webapi.repository.tray;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.tray.GetTrayMgtReport;
import com.ats.webapi.model.tray.TrayMgtDetailInTray;

public interface TrayMgtDetailInTrayRepo extends JpaRepository<TrayMgtDetailInTray, Integer> {

	List<TrayMgtDetailInTray> findByFrIdAndIntrayDateAndDelStatus(int frId, String intrayDate, int i);

	// SELECT
	// dt.intray_id,dt.tran_id,dt.tran_detail_id,dt.fr_id,dt.intray_date,SUM(dt.intray_big)
	// AS intray_big,SUM(dt.intray_lead) AS intray_lead ,SUM(dt.intray_small) AS
	// intray_small,dt.ex_int1,dt.ex_int2,dt.ex_var1,dt.ex_var2,dt.del_status FROM
	// t_tray_mgmt_detail_intray dt WHERE dt.del_status=0 AND dt.fr_id=2 AND
	// dt.intray_date BETWEEN '2019-06-01' AND '2019-06-28' GROUP BY dt.fr_id

	@Query(value = " SELECT dt.intray_id,dt.tran_id,dt.tran_detail_id,dt.fr_id,dt.intray_date,SUM(dt.intray_big) AS intray_big,dt.tran_intray_id,"
			+ "SUM(dt.intray_lead) AS intray_lead ,SUM(dt.intray_small) AS intray_small,dt.ex_int1,dt.ex_int2,dt.ex_var1,dt.ex_var2,"
			+ "dt.del_status FROM t_tray_mgmt_detail_intray dt WHERE dt.del_status=0 AND dt.fr_id IN(:frIdList) AND dt.intray_date"
			+ " BETWEEN :fromDate AND :toDate " + "GROUP BY dt.fr_id", nativeQuery = true)
	List<TrayMgtDetailInTray> getTrayMgtDetail(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("frIdList") List<String> frIdList);

	@Query(value = "   SELECT dt.intray_id,dt.tran_id,dt.tran_detail_id,dt.fr_id,dt.intray_date,SUM(dt.intray_big) AS intray_big,dt.tran_intray_id,"
			+ "SUM(dt.intray_lead) AS intray_lead ,SUM(dt.intray_small) AS intray_small,dt.ex_int1,dt.ex_int2,dt.ex_var1,dt.ex_var2,"
			+ "dt.del_status FROM t_tray_mgmt_detail_intray dt WHERE dt.del_status=0 AND dt.intray_date BETWEEN :fromDate AND "
			+ ":toDate GROUP BY dt.fr_id", nativeQuery = true)
	List<TrayMgtDetailInTray> getTrayMgtDetailBetDate(@Param("fromDate") String fromDate,
			@Param("toDate") String toDate);
}
