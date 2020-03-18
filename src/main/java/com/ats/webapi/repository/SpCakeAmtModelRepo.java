package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.pettycash.SpCakeAmtModel;

public interface SpCakeAmtModelRepo extends JpaRepository<SpCakeAmtModel, Integer>{
	
	@Query(value="SELECT\r\n" + 
			"    UUID() AS id, t1.amt AS rem_amt, t2.amt AS adv_amt\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS flag,\r\n" + 
			"        SUM(t.rm_amount) AS amt\r\n" + 
			"    FROM\r\n" + 
			"        t_sp_cake t\r\n" + 
			"    WHERE\r\n" + 
			"        t.del_status = 0 AND t.sp_delivery_date =:date AND t.fr_id =:frId \r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT 1 AS flag,\r\n" + 
			"        SUM(t.sp_advance) AS amt\r\n" + 
			"    FROM\r\n" + 
			"        t_sp_cake t\r\n" + 
			"    WHERE\r\n" + 
			"        t.del_status = 0 AND t.order_date =:date AND t.fr_id =:frId \r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.flag = t2.flag" + 
			"       ",nativeQuery=true)
	public SpCakeAmtModel getSpCakeAmtForPettyCash(@Param("frId") int frId, @Param("date") String date);

}
