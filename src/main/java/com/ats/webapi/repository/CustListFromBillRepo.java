package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.CrnHsnwiseExcelReport;
import com.ats.webapi.model.CustListFromBill;

public interface CustListFromBillRepo extends JpaRepository<CustListFromBill, Integer>{

	
	/*
	 * @Query(value="SELECT\r\n" +
	 * "    UUID() AS id, user_phone, user_name, user_gst_no\r\n" + "FROM\r\n" +
	 * "    t_sell_bill_header\r\n" + "WHERE\r\n" + "    fr_id = :frId \r\n" +
	 * "UNION ALL\r\n" + "SELECT\r\n" +
	 * "    UUID() AS id, sp_cust_mob_no AS user_phone, sp_cust_name AS user_name, cust_gstin AS user_gst_no\r\n"
	 * + "FROM\r\n" + "    t_sp_cake\r\n" + "WHERE\r\n" +
	 * "    fr_id = :frId ",nativeQuery=true) List<CustListFromBill>
	 * getCustomer(@Param("frId") int frId);
	 */

	
	@Query(value="SELECT\r\n" + 
			"    *\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        UUID() AS id, user_phone, user_name, user_gst_no\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header\r\n" + 
			"    WHERE\r\n" + 
			"        fr_id = :frId AND user_phone != ''\r\n" + 
			"    GROUP BY\r\n" + 
			"        user_phone\r\n" + 
			"    UNION ALL\r\n" + 
			"SELECT\r\n" + 
			"    UUID() AS id, sp_cust_mob_no AS user_phone, sp_cust_name AS user_name, cust_gstin AS user_gst_no\r\n" + 
			"FROM\r\n" + 
			"    t_sp_cake\r\n" + 
			"WHERE\r\n" + 
			"    fr_id = :frId AND sp_cust_mob_no != '' AND sp_cust_mob_no != '-' AND sp_cust_mob_no != '--'\r\n" + 
			"GROUP BY\r\n" + 
			"    sp_cust_mob_no) t1\r\n" + 
			"GROUP BY\r\n" + 
			"    t1.user_phone,\r\n" + 
			"    t1.user_name,\r\n" + 
			"    t1.user_gst_no\r\n" + 
			"ORDER BY\r\n" + 
			"    t1.user_name ",nativeQuery=true)
	List<CustListFromBill> getCustomer(@Param("frId") int frId);

}
