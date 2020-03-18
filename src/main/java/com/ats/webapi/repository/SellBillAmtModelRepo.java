package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.pettycash.SellBillAmtModel;

public interface SellBillAmtModelRepo extends JpaRepository<SellBillAmtModel, Integer>{
	
	@Query(value="SELECT\r\n" + 
			"    t1.payment_mode,\r\n" + 
			"    SUM(t1.paid_amt) AS amt\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        h.sell_bill_no,\r\n" + 
			"        h.paid_amt,\r\n" + 
			"        h.payment_mode\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header h,\r\n" + 
			"        t_sell_bill_detail d\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0 AND h.bill_date=:date AND h.sell_bill_no = d.sell_bill_no AND d.cat_id != 5 AND h.fr_id=:frId \r\n" + 
			"    GROUP BY\r\n" + 
			"        h.payment_mode,\r\n" + 
			"        h.sell_bill_no\r\n" + 
			") t1\r\n" + 
			"GROUP BY\r\n" + 
			"    t1.payment_mode" + 
			"       ",nativeQuery=true)
	public List<SellBillAmtModel> getSellBillAmtForPettyCash(@Param("frId") int frId, @Param("date") String date);

	
}
