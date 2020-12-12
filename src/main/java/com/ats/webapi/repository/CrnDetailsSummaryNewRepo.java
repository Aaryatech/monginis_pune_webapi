package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.CrnDetailsSummaryNew;

public interface CrnDetailsSummaryNewRepo extends JpaRepository<CrnDetailsSummaryNew, Integer>{

	@Query(value="  SELECT\r\n" + 
			"	uuid() as id,\r\n" + 
			"	detail.crn_id,\r\n" + 
			"    detail.hsn_code AS item_hsncd,\r\n" + 
			"    CASE WHEN detail.cat_id = 5 THEN 'Special Cake' ELSE(\r\n" + 
			"    SELECT\r\n" + 
			"        m_category.cat_name\r\n" + 
			"    FROM\r\n" + 
			"        m_category\r\n" + 
			"    WHERE\r\n" + 
			"        m_category.cat_id = detail.cat_id\r\n" + 
			")\r\n" + 
			"END AS item_hsncdesc,\r\n" + 
			"SUM(detail.grn_gvn_qty) AS grn_gvn_qty,\r\n" + 
			"SUM(detail.taxable_amt) AS taxable_amt,\r\n" + 
			"detail.cgst_per AS cgst_per,\r\n" + 
			"SUM(detail.cgst_rs) AS cgst_rs,\r\n" + 
			"detail.sgst_per AS sgst_per,\r\n" + 
			"SUM(detail.sgst_rs) AS sgst_rs,\r\n" + 
			"detail.igst_per AS igst_per,\r\n" + 
			"SUM(detail.igst_rs) AS igst_rs\r\n" + 
			"FROM\r\n" + 
			"    t_credit_note_details detail\r\n" + 
			"WHERE\r\n" + 
			"    detail.crn_id IN(:crnId)\r\n" + 
			"GROUP BY\r\n" + 
			"    item_hsncd,crn_id ORDER BY crn_id",nativeQuery=true)
	List<CrnDetailsSummaryNew> getCrnDetailsSummaryById(@Param("crnId")List<String> crnId);
	

}
