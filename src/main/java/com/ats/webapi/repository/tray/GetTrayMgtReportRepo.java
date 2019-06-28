package com.ats.webapi.repository.tray;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.tray.GetTrayMgtReport;

public interface GetTrayMgtReportRepo extends JpaRepository<GetTrayMgtReport, Integer> {

	@Query(value = "  SELECT d.fr_id,d.outtray_date,SUM(d.outtray_big) as outtray_big\n"
			+ " ,SUM(d.outtray_small) as outtray_small\n" + " ,SUM(d.outtray_lead) as outtray_lead\n"
			+ " ,0 as intray_big\n" + " ,0 as intray_small\n" + ",0 as intray_lead\n"
			+ " ,SUM(d.balance_big) as balance_big\n" + "  ,SUM(d.balance_small) as balance_small \n"
			+ "  ,SUM(d.balance_lead) as balance_lead\n" + "  ,f.fr_name,f.fr_code\n"
			+ " FROM t_tray_mgt_detail d,m_franchisee f WHERE d.del_status=0 AND d.fr_id IN(:frIdList) AND d.outtray_date  "
			+ "BETWEEN :fromDate AND :toDate  AND f.fr_id=d.fr_id GROUP BY d.fr_id", nativeQuery = true)
	List<GetTrayMgtReport> getTrayMgtDetail(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("frIdList") List<String> frIdList);

	@Query(value = "  SELECT d.fr_id,d.outtray_date,SUM(d.outtray_big) as outtray_big\n"
			+ " ,SUM(d.outtray_small) as outtray_small\n" + " ,SUM(d.outtray_lead) as outtray_lead\n"
			+ " ,0 as intray_big\n" + " ,0 as intray_small\n" + ",0 as intray_lead\n"
			+ " ,SUM(d.balance_big) as balance_big\n" + "  ,SUM(d.balance_small) as balance_small \n"
			+ "  ,SUM(d.balance_lead) as balance_lead\n" + "  ,f.fr_name,f.fr_code\n"
			+ " FROM t_tray_mgt_detail d,m_franchisee f WHERE d.del_status=0 AND d.outtray_date  "
			+ "BETWEEN :fromDate AND :toDate  AND f.fr_id=d.fr_id GROUP BY d.fr_id", nativeQuery = true)
	List<GetTrayMgtReport> getTrayMgtDetailBetDate(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

}
