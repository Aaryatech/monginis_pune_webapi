package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.CrnHsnwiseExcelReport;
@Repository
public interface CrnHsnwiseExcelReportRepository extends JpaRepository<CrnHsnwiseExcelReport, Integer>{

	//@Query(value="select UUID() as id,t_credit_note_header.crn_id, t_credit_note_header.ex_varchar1 as supplier_invoice_no,t_credit_note_details.bill_date as supplier_invoice_date,t_credit_note_header.crn_no as invoice_no,t_credit_note_header.crn_date as invoice_date,t_credit_note_header.fr_id,m_franchisee.fr_name,m_item_sup.item_hsncd,SUM(t_credit_note_details.grn_gvn_qty) as qty,round(SUM(t_credit_note_details.taxable_amt),2) as taxable_amt,round(SUM(t_credit_note_details.cgst_rs),2) as cgst_rs,round(SUM(t_credit_note_details.sgst_rs),2) as sgst_rs,0 as igst_rs,round((t_credit_note_details.cgst_per+t_credit_note_details.sgst_per),2) as tax_rate,((select (SUM(d.taxable_amt)+SUM(d.cgst_rs)+SUM(d.sgst_rs)) from t_credit_note_details d where d.crn_id=t_credit_note_header.crn_id)) as document_amount,m_franchisee.fr_gst_no,'INDIA' as country,'MAHARASHTRA' as state from t_credit_note_header,t_credit_note_details,m_item_sup,m_franchisee  where m_item_sup.item_id=t_credit_note_details.item_id and t_credit_note_header.crn_id=t_credit_note_details.crn_id and t_credit_note_header.crn_id IN(:crnIdList) And m_franchisee.fr_id=t_credit_note_header.fr_id group by t_credit_note_header.crn_id,item_hsncd",nativeQuery=true)
	//@Query(value="select UUID() as id,t_credit_note_header.crn_id, t_credit_note_header.ex_varchar1 as supplier_invoice_no,t_credit_note_details.bill_date as supplier_invoice_date,t_credit_note_header.crn_no as invoice_no,t_credit_note_header.crn_date as invoice_date,t_credit_note_header.fr_id,m_franchisee.fr_name,m_item_sup.item_hsncd,SUM(t_credit_note_details.grn_gvn_qty) as qty,round(SUM(t_credit_note_details.taxable_amt),2) as taxable_amt,round(SUM(t_credit_note_details.cgst_rs),2) as cgst_rs,round(SUM(t_credit_note_details.sgst_rs),2) as sgst_rs,0 as igst_rs,round((t_credit_note_details.cgst_per+t_credit_note_details.sgst_per),2) as tax_rate,((select (SUM(d.taxable_amt)+SUM(d.cgst_rs)+SUM(d.sgst_rs)) from t_credit_note_details d where d.crn_id=t_credit_note_header.crn_id)) as document_amount,m_franchisee.fr_gst_no,'INDIA' as country,'MAHARASHTRA' as state from t_credit_note_header,t_credit_note_details,m_item_sup,m_franchisee  where m_item_sup.item_id=t_credit_note_details.item_id and t_credit_note_header.crn_id=t_credit_note_details.crn_id and t_credit_note_header.crn_id IN(:crnIdList) And m_franchisee.fr_id=t_credit_note_header.fr_id group by t_credit_note_header.crn_id,item_hsncd\n" + 
	//		"UNION ALL\n" + 
	//		"select UUID() as id,t_credit_note_header.crn_id, t_credit_note_header.ex_varchar1 as supplier_invoice_no,t_credit_note_details.bill_date as supplier_invoice_date,t_credit_note_header.crn_no as invoice_no,t_credit_note_header.crn_date as invoice_date,t_credit_note_header.fr_id,m_franchisee.fr_name,m_spcake_sup.sp_hsncd as item_hsncd,SUM(t_credit_note_details.grn_gvn_qty) as qty,round(SUM(t_credit_note_details.taxable_amt),2) as taxable_amt,round(SUM(t_credit_note_details.cgst_rs),2) as cgst_rs,round(SUM(t_credit_note_details.sgst_rs),2) as sgst_rs,0 as igst_rs,round((t_credit_note_details.cgst_per+t_credit_note_details.sgst_per),2) as tax_rate,((select (SUM(d.taxable_amt)+SUM(d.cgst_rs)+SUM(d.sgst_rs)) from t_credit_note_details d where d.crn_id=t_credit_note_header.crn_id)) as document_amount,m_franchisee.fr_gst_no,'INDIA' as country,'MAHARASHTRA' as state from t_credit_note_header,t_credit_note_details,m_spcake_sup,m_franchisee  where m_spcake_sup.sp_id=t_credit_note_details.item_id and t_credit_note_header.crn_id=t_credit_note_details.crn_id and t_credit_note_header.crn_id IN(:crnIdList) And m_franchisee.fr_id=t_credit_note_header.fr_id group by t_credit_note_header.crn_id,item_hsncd",nativeQuery=true)
	
	//@Query(value="select UUID() as id,t_credit_note_header.crn_id, t_credit_note_header.ex_varchar1 as supplier_invoice_no,t_credit_note_details.bill_date as supplier_invoice_date,t_credit_note_header.crn_no as invoice_no,t_credit_note_header.crn_date as invoice_date,t_credit_note_header.fr_id,m_franchisee.fr_name,t_credit_note_details.hsn_code as item_hsncd,SUM(t_credit_note_details.grn_gvn_qty) as qty,round(SUM(t_credit_note_details.taxable_amt),2) as taxable_amt,round(SUM(t_credit_note_details.cgst_rs),2) as cgst_rs,round(SUM(t_credit_note_details.sgst_rs),2) as sgst_rs,0 as igst_rs,round((t_credit_note_details.cgst_per+t_credit_note_details.sgst_per),2) as tax_rate,((select (SUM(d.taxable_amt)+SUM(d.cgst_rs)+SUM(d.sgst_rs)) from t_credit_note_details d where d.crn_id=t_credit_note_header.crn_id)) as document_amount,m_franchisee.fr_gst_no,'INDIA' as country,'MAHARASHTRA' as state from t_credit_note_header,t_credit_note_details,m_franchisee  where  t_credit_note_header.crn_id=t_credit_note_details.crn_id and t_credit_note_header.crn_id IN(:crnIdList) And m_franchisee.fr_id=t_credit_note_header.fr_id group by t_credit_note_header.crn_id,item_hsncd",nativeQuery=true)
	//List<CrnHsnwiseExcelReport> getCrnHsnwiseExcelReport(@Param("crnIdList")List<String> crnIdList);

	@Query(value="SELECT\n" + 
			"    t1.*,\n" + 
			"    t2.document_amount,\n" + 
			"    t3.fr_name,\n" + 
			"    t3.fr_gst_no\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        UUID() AS id, h.crn_id, h.ex_varchar1 AS supplier_invoice_no, d.bill_date AS supplier_invoice_date, h.crn_no AS invoice_no, h.crn_date AS invoice_date, h.fr_id, d.hsn_code AS item_hsncd, SUM(d.grn_gvn_qty) AS qty,\n" + 
			"        ROUND(SUM(d.taxable_amt),\n" + 
			"        2) AS taxable_amt,\n" + 
			"        ROUND(SUM(d.cgst_rs),\n" + 
			"        2) AS cgst_rs,\n" + 
			"        ROUND(SUM(d.sgst_rs),\n" + 
			"        2) AS sgst_rs,\n" + 
			"        0 AS igst_rs,\n" + 
			"        ROUND((d.cgst_per + d.sgst_per),\n" + 
			"        2) AS tax_rate,\n" + 
			"        'INDIA' AS country,\n" + 
			"        'MAHARASHTRA' AS state\n" + 
			"    FROM\n" + 
			"        t_credit_note_header h,\n" + 
			"        t_credit_note_details d\n" + 
			"    WHERE\n" + 
			"        h.crn_id = d.crn_id AND h.crn_id IN(:crnIdList)\n" + 
			"    GROUP BY\n" + 
			"        h.crn_id,\n" + 
			"        item_hsncd) t1\n" + 
			"    LEFT JOIN(\n" + 
			"        SELECT d.crn_id,\n" + 
			"            (\n" + 
			"                SUM(d.taxable_amt) + SUM(d.cgst_rs) + SUM(d.sgst_rs)\n" + 
			"            ) AS document_amount\n" + 
			"        FROM\n" + 
			"            t_credit_note_details d\n" + 
			"        GROUP BY\n" + 
			"            d.crn_id\n" + 
			"    ) t2\n" + 
			"ON\n" + 
			"    t1.crn_id = t2.crn_id\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT fr_name,\n" + 
			"        fr_gst_no,\n" + 
			"        fr_id\n" + 
			"    FROM\n" + 
			"        m_franchisee\n" + 
			") t3\n" + 
			"ON\n" + 
			"    t1.fr_id = t3.fr_id",nativeQuery=true)
	List<CrnHsnwiseExcelReport> getCrnHsnwiseExcelReport(@Param("crnIdList")List<String> crnIdList);

	
	
}
