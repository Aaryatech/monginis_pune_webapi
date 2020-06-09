package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.GetCurrentStockDetails;

public interface GetCurrentStockDetailsRepo extends JpaRepository<GetCurrentStockDetails, Integer>{

	@Query(value = "SELECT\r\n" + 
			"    COALESCE(t2.opening_stock_detail_id,0) as opening_stock_detail_id,\r\n" + 
			"    COALESCE(t2.opening_stock_header_id,0) as opening_stock_header_id,\r\n" + 
			"    t1.item_id,\r\n" + 
			"    t1.item_name,\r\n" + 
			"    COALESCE(t2.reg_opening_stock, 0) AS reg_opening_stock,\r\n" + 
			"    COALESCE(t1.item_rate1, 0) AS sp_opening_stock,\r\n" + 
			"    COALESCE(t3.reg, 0) AS reg_total_purchase,\r\n" + 
			"    COALESCE(t1.item_mrp1, 0) AS sp_total_purchase,\r\n" + 
			"    COALESCE(t4.grngvn, 0) AS reg_total_grn_gvn,\r\n" + 
			"    COALESCE(t5.reg, 0) AS reg_total_sell,\r\n" + 
			"    COALESCE(t5.sp, 0) AS sp_total_sell,\r\n" + 
			"    t1.item_id AS id,\r\n" + 
			"    0 AS re_order_qty,\r\n" + 
			"    (\r\n" + 
			"        COALESCE(t2.reg_opening_stock, 0) + COALESCE(t3.reg, 0)\r\n" + 
			"    ) -(\r\n" + 
			"        COALESCE(t4.grngvn, 0) + COALESCE(t5.reg, 0)\r\n" + 
			"    ) AS current_reg_stock,\r\n" + 
			"    COALESCE(t1.item_rate1, 0) + COALESCE(t1.item_mrp1, 0) - COALESCE(t5.sp, 0) AS current_sp_stock\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        i.id AS item_id,\r\n" + 
			"        i.item_name,\r\n" + 
			"        i.item_rate1,\r\n" + 
			"        i.item_mrp1\r\n" + 
			"    FROM\r\n" + 
			"        m_item i\r\n" + 
			"    WHERE\r\n" + 
			"        i.del_status = 0 AND i.id IN(:itemIds) Order By i.item_grp2\r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT d.*,\r\n" + 
			"        h.fr_id\r\n" + 
			"    FROM\r\n" + 
			"        m_fr_opening_stock_detail d,\r\n" + 
			"        m_fr_opening_stock_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.opening_stock_header_id = d.opening_stock_header_id AND h.fr_id IN(:frId) AND h.cat_id =:catId AND h.month =:month AND h.year =:year AND d.item_id IN(:itemIds)\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.item_id = t2.item_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT d.bill_detail_no,\r\n" + 
			"        CASE WHEN d.grn_type != 3 THEN SUM(d.bill_qty) ELSE 0\r\n" + 
			"END AS reg,\r\n" + 
			"CASE WHEN d.grn_type = 3 THEN SUM(d.bill_qty) ELSE 0\r\n" + 
			"END AS sp,\r\n" + 
			"h.fr_id,\r\n" + 
			"d.item_id\r\n" + 
			"FROM\r\n" + 
			"    t_bill_header h,\r\n" + 
			"    t_bill_detail d\r\n" + 
			"WHERE\r\n" + 
			"    h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id =:frId AND h.status = 2 AND h.bill_no = d.bill_no AND d.item_id IN(:itemIds)\r\n" + 
			"GROUP BY\r\n" + 
			"    h.fr_id,\r\n" + 
			"    d.item_id\r\n" + 
			") t3\r\n" + 
			"ON\r\n" + 
			"    t1.item_id = t3.item_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT item_id,\r\n" + 
			"        fr_id,\r\n" + 
			"        SUM(t_grn_gvn.grn_gvn_qty) AS grngvn\r\n" + 
			"    FROM\r\n" + 
			"        t_grn_gvn\r\n" + 
			"    WHERE\r\n" + 
			"        fr_id =:frId AND item_id IN(:itemIds) AND grn_gvn_date BETWEEN :fromDate AND :toDate\r\n" + 
			"    GROUP BY\r\n" + 
			"        fr_id,\r\n" + 
			"        item_id\r\n" + 
			") t4\r\n" + 
			"ON\r\n" + 
			"    t1.item_id = t4.item_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT d.sell_bill_detail_no,\r\n" + 
			"        CASE WHEN d.bill_stock_type = 1 THEN SUM(d.qty) ELSE 0\r\n" + 
			"END AS reg,\r\n" + 
			"CASE WHEN d.bill_stock_type = 2 THEN SUM(d.qty) ELSE 0\r\n" + 
			"END AS sp,\r\n" + 
			"d.item_id,\r\n" + 
			"h.fr_id\r\n" + 
			"FROM\r\n" + 
			"    t_sell_bill_header h,\r\n" + 
			"    t_sell_bill_detail d\r\n" + 
			"WHERE\r\n" + 
			"    h.sell_bill_no = d.sell_bill_no AND h.del_status = 0 AND h.fr_id =:frId AND d.item_id IN(:itemIds) AND h.bill_date BETWEEN :fromDate AND :toDate\r\n" + 
			"GROUP BY\r\n" + 
			"    d.item_id,\r\n" + 
			"    h.fr_id\r\n" + 
			") t5\r\n" + 
			"ON\r\n" + 
			"    t1.item_id = t5.item_id", nativeQuery = true)
	List<GetCurrentStockDetails> getCurrStock(@Param("catId") int catId, @Param("month") int month, @Param("year") int year ,@Param("frId") int frId, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate, @Param("itemIds") List<Integer> itemIds);
	
}
