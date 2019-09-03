package com.ats.webapi.repository.tray;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.tray.FrTrayData;


public interface FrTrayDataRepo extends JpaRepository<FrTrayData, Integer>{

	/*@Query(value="SELECT UUID() as id ,COALESCE(SUM(d.`balance_small`),0) as small, COALESCE(SUM(d.`balance_lead`),0) as lead,"
			+ " COALESCE(SUM(d.`balance_big`),0) as big, d.outtray_date as tray_date, 0 as header_id"
			+ " FROM t_tray_mgt_detail d "
			+ "WHERE d.fr_id=:frId AND d.outtray_date < :trayDate AND d.del_status=0",nativeQuery=true)
	FrTrayData getOpeningCount(@Param("frId") int frId,@Param("trayDate")  String trayDate);*/
	
	
	/*@Query(value="SELECT UUID() AS id, COALESCE((SELECT SUM(intray_small) FROM t_tray_mgmt_detail_intray "
			+ "WHERE fr_id = :frId AND intray_date = :trayDate AND del_status = 0)+SUM(d.`balance_small`), 0) "
			+ "AS small, COALESCE((SELECT SUM(intray_lead) FROM t_tray_mgmt_detail_intray WHERE fr_id = :frId AND "
			+ "intray_date = :trayDate AND del_status = 0)+SUM(d.`balance_lead`), 0) AS lead, "
			+ "COALESCE((SELECT SUM(intray_big) FROM t_tray_mgmt_detail_intray WHERE fr_id = :frId AND "
			+ "intray_date = :trayDate AND del_status = 0)+SUM(d.`balance_big`), 0) AS big, "
			+ "d.outtray_date AS tray_date, 0 AS header_id FROM t_tray_mgt_detail d WHERE d.fr_id = :frId "
			+ "AND d.outtray_date < :trayDate AND d.del_status = 0",nativeQuery=true)
	FrTrayData getOpeningCount(@Param("frId") int frId,@Param("trayDate")  String trayDate);*/
	
	
	
	@Query(value="SELECT\r\n" + 
			"    UUID() AS id,((COALESCE ((SELECT\r\n" + 
			"        SUM(outtray_small)\r\n" + 
			"    FROM\r\n" + 
			"        t_tray_mgt_detail\r\n" + 
			"    WHERE\r\n" + 
			"        fr_id = :frId AND outtray_date < :trayDate AND del_status = 0),0))-(COALESCE ((SELECT\r\n" + 
			"        SUM(intray_small)\r\n" + 
			"    FROM\r\n" + 
			"        t_tray_mgmt_detail_intray\r\n" + 
			"    WHERE\r\n" + 
			"        fr_id = :frId AND intray_date < :trayDate AND del_status = 0),0))) as small,\r\n" + 
			"        ((COALESCE ((SELECT\r\n" + 
			"        SUM(outtray_lead)\r\n" + 
			"    FROM\r\n" + 
			"        t_tray_mgt_detail\r\n" + 
			"    WHERE\r\n" + 
			"        fr_id = :frId AND outtray_date < :trayDate AND del_status = 0),0))-(COALESCE ((SELECT\r\n" + 
			"        SUM(intray_lead)\r\n" + 
			"    FROM\r\n" + 
			"        t_tray_mgmt_detail_intray\r\n" + 
			"    WHERE\r\n" + 
			"        fr_id = :frId AND intray_date < :trayDate AND del_status = 0),0))) as lead,\r\n" + 
			"        \r\n" + 
			"         ((COALESCE ((SELECT\r\n" + 
			"        SUM(outtray_big)\r\n" + 
			"    FROM\r\n" + 
			"        t_tray_mgt_detail\r\n" + 
			"    WHERE\r\n" + 
			"        fr_id = :frId AND outtray_date < :trayDate AND del_status = 0),0))-(COALESCE ((SELECT\r\n" + 
			"        SUM(intray_big)\r\n" + 
			"    FROM\r\n" + 
			"        t_tray_mgmt_detail_intray\r\n" + 
			"    WHERE\r\n" + 
			"        fr_id = :frId AND intray_date < :trayDate AND del_status = 0),0))) as big\r\n" + 
			"        ,\r\n" + 
			"    0 AS tray_date,\r\n" + 
			"    0 AS header_id ",nativeQuery=true)
	FrTrayData getOpeningCount(@Param("frId") int frId,@Param("trayDate")  String trayDate);
	 
	
	
	
	@Query(value="SELECT UUID() as id ,d.`outtray_small` as small,d.`outtray_lead` as lead,d.`outtray_big` as big , "
			+ "d.`outtray_date` as tray_date, 0 as header_id"
			+ " from t_tray_mgt_detail d WHERE d.tray_status>1 AND "
			+ "d.fr_id=:frId AND d.outtray_date=:trayDate AND d.del_status=0",nativeQuery=true)
	List<FrTrayData> getReceivedTrays(@Param("frId") int frId,@Param("trayDate")  String trayDate);
	
	
	@Query(value="SELECT UUID() AS id, SUM(d.`outtray_small`) AS small, SUM(d.`outtray_lead`) AS lead, SUM(d.`outtray_big`) AS big,"
			+ " d.`outtray_date` AS tray_date, 0 AS header_id FROM t_tray_mgt_detail d WHERE d.tray_status > 1 "
			+ "AND d.fr_id = :frId AND d.outtray_date = :trayDate AND d.del_status = 0 GROUP BY d.outtray_date ",nativeQuery=true)
	FrTrayData getSumReceivedTrays(@Param("frId") int frId,@Param("trayDate")  String trayDate);
	
	

/*	@Query(value="SELECT UUID() as id ,it.intray_small as small, it.intray_lead as lead, it.`intray_big` as big, "
			+ "it.intray_date as tray_date, it.`tran_intray_id` as header_id FROM t_tray_mgmt_detail_intray it"
			+ " WHERE   it.fr_id=:frId AND it.intray_date = :trayDate AND it.del_status=0",nativeQuery=true)
	List<FrTrayData> getReturnTrays(@Param("frId") int frId,@Param("trayDate")  String trayDate);*/



	@Query(value="SELECT UUID() AS id, SUM(it.intray_small) AS small, SUM(it.intray_lead) AS lead, SUM(it.`intray_big`) "
			+ "AS big, it.intray_date AS tray_date, it.`tran_intray_id` AS header_id FROM t_tray_mgmt_detail_intray it "
			+ "WHERE it.fr_id = :frId AND it.intray_date = :trayDate AND it.del_status = 0 GROUP BY it.tran_intray_id ",nativeQuery=true)
	List<FrTrayData> getReturnTrays(@Param("frId") int frId,@Param("trayDate")  String trayDate);
	
	
	@Query(value="SELECT UUID() AS id, SUM(it.intray_small) AS small, SUM(it.intray_lead) AS lead, SUM(it.`intray_big`) "
			+ "AS big, it.intray_date AS tray_date, it.`tran_intray_id` AS header_id FROM t_tray_mgmt_detail_intray it "
			+ "WHERE it.fr_id = :frId AND it.intray_date = :trayDate AND it.del_status = 0 GROUP BY it.tran_intray_id, it.intray_date ",nativeQuery=true)
	FrTrayData getSumReturnTrays(@Param("frId") int frId,@Param("trayDate")  String trayDate);
	



}