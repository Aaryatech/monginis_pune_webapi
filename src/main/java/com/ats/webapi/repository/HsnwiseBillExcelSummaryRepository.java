package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.HsnwiseBillExcelSummary;
@Repository
public interface HsnwiseBillExcelSummaryRepository extends JpaRepository<HsnwiseBillExcelSummary, Integer> {

	@Query(value="select UUID() as id, \n" + 
			"						t_bill_header.bill_no,\n" + 
			"						t_bill_header.invoice_no,\n" + 
			"						t_bill_header.party_name, \n" + 
			"						t_bill_header.party_gstin, \n" + 
			"						t_bill_header.bill_date, \n" + 
			"						 (select round(SUM((t_bill_detail.sgst_rs+t_bill_detail.cgst_rs+round(t_bill_detail.taxable_amt,2))),2) from t_bill_detail where \n" + 
			"						        t_bill_detail.bill_no=t_bill_header.bill_no  group by t_bill_header.bill_no) as invoice_total, \n" + 
			"						s.item_hsncd, \n" + 
			"						SUM(t_bill_detail.bill_qty) as qty, \n" + 
			"						SUM(round(t_bill_detail.taxable_amt,2)) as taxable_amt, \n" + 
			"						t_bill_detail.sgst_per+t_bill_detail.cgst_per as tax_rate, \n" + 
			"						round(SUM(t_bill_detail.sgst_rs),2) as sgst_rs, \n" + 
			"						round(SUM(t_bill_detail.cgst_rs),2) as cgst_rs, \n" + 
			"						SUM(t_bill_detail.igst_rs) as igst_rs, \n" + 
			"						t_bill_detail.cess_per, \n" + 
			"						SUM(t_bill_detail.cess_rs)as cess_rs, \n" + 
			"						round(SUM(t_bill_detail.total_tax),2) as total_tax, \n" + 
			"						round(SUM((t_bill_detail.sgst_rs+t_bill_detail.cgst_rs+round(t_bill_detail.taxable_amt,2))),2) as grand_total, \n" + 
			"						'MAHARASHTRA' as state, \n" + 
			"						'INDIA' as country from t_bill_header,t_bill_detail,m_item_sup s where t_bill_header.bill_no in(:billNos) and t_bill_header.bill_no=t_bill_detail.bill_no and  s.item_id=t_bill_detail.item_id   group by t_bill_header.bill_no,item_hsncd \n" + 
			"                        \n" + 
			"    UNION ALL\n" + 
			"    select UUID() as id, \n" + 
			"    t_bill_header.bill_no,\n" + 
			"						t_bill_header.invoice_no,\n" + 
			"						t_bill_header.party_name, \n" + 
			"						t_bill_header.party_gstin, \n" + 
			"						t_bill_header.bill_date, \n" + 
			"						 (select round(SUM((t_bill_detail.sgst_rs+t_bill_detail.cgst_rs+round(t_bill_detail.taxable_amt,2))),2) from t_bill_detail where \n" + 
			"						        t_bill_detail.bill_no=t_bill_header.bill_no  group by t_bill_header.bill_no) as invoice_total, \n" + 
			"						m_spcake_sup.sp_hsncd, \n" + 
			"						SUM(t_bill_detail.bill_qty) as qty, \n" + 
			"						SUM(round(t_bill_detail.taxable_amt,2)) as taxable_amt, \n" + 
			"						t_bill_detail.sgst_per+t_bill_detail.cgst_per as tax_rate, \n" + 
			"						round(SUM(t_bill_detail.sgst_rs),2) as sgst_rs, \n" + 
			"						round(SUM(t_bill_detail.cgst_rs),2) as cgst_rs, \n" + 
			"						SUM(t_bill_detail.igst_rs) as igst_rs, \n" + 
			"						t_bill_detail.cess_per, \n" + 
			"						SUM(t_bill_detail.cess_rs)as cess_rs, \n" + 
			"						round(SUM(t_bill_detail.total_tax),2) as total_tax, \n" + 
			"						round(SUM((t_bill_detail.sgst_rs+t_bill_detail.cgst_rs+round(t_bill_detail.taxable_amt,2))),2) as grand_total, \n" + 
			"						'MAHARASHTRA' as state, \n" + 
			"						'INDIA' as country from t_bill_header,t_bill_detail,m_spcake_sup  where t_bill_header.bill_no in(:billNos) and t_bill_header.bill_no=t_bill_detail.bill_no and  t_bill_detail.item_id=m_spcake_sup.sp_id   group by t_bill_header.bill_no,m_spcake_sup.sp_hsncd",nativeQuery=true)
	List<HsnwiseBillExcelSummary> getHsnwiseBillDataForExcel(@Param("billNos")List<String> billNos);

/*	@Query(value="select UUID() as id,\n" + 
			"t_bill_header.bill_no,\n" + 
			"t_bill_header.invoice_no,\n" + 
			"t_bill_header.party_name,\n" + 
			"t_bill_header.party_gstin,\n" + 
			"t_bill_header.bill_date,\n" + 
			"(select SUM(t_bill_detail.grand_total) from t_bill_detail where  " + 
			" t_bill_detail.bill_no=t_bill_header.bill_no  group by t_bill_header.bill_no) as invoice_total,\n" + 
			"s.item_hsncd,\n" + 
			"SUM(t_bill_detail.bill_qty) as qty,\n" + 
			"SUM(t_bill_detail.taxable_amt) as taxable_amt,\n" + 
			"t_bill_detail.sgst_per+t_bill_detail.cgst_per as tax_rate,\n" + 
			"SUM(t_bill_detail.sgst_rs)as sgst_rs,\n" + 
			"SUM(t_bill_detail.cgst_rs)as cgst_rs,\n" + 
			"SUM(t_bill_detail.igst_rs)as igst_rs,\n" + 
			"t_bill_detail.cess_per,\n" + 
			"SUM(t_bill_detail.cess_rs)as cess_rs,\n" + 
			"SUM(t_bill_detail.total_tax) as total_tax,\n" + 
			"SUM(t_bill_detail.grand_total) as grand_total,\n" + 
			"'MAHARASHTRA' as state,\n" + 
			"'INDIA' as country from t_bill_header,t_bill_detail,m_item_sup s where (t_bill_header.bill_date between :fromDate and :toDate) and t_bill_header.bill_no=t_bill_detail.bill_no and  s.item_id=t_bill_detail.item_id group by  t_bill_header.bill_no,item_hsncd",nativeQuery=true)
*/
	
	@Query(value="select UUID() as id, \n" + 
			"						t_bill_header.bill_no,\n" + 
			"						t_bill_header.invoice_no,\n" + 
			"						t_bill_header.party_name, \n" + 
			"						t_bill_header.party_gstin, \n" + 
			"						t_bill_header.bill_date, \n" + 
			"						 (select round(SUM((t_bill_detail.sgst_rs+t_bill_detail.cgst_rs+round(t_bill_detail.taxable_amt,2))),2) from t_bill_detail where \n" + 
			"						        t_bill_detail.bill_no=t_bill_header.bill_no  group by t_bill_header.bill_no) as invoice_total, \n" + 
			"						s.item_hsncd, \n" + 
			"						SUM(t_bill_detail.bill_qty) as qty, \n" + 
			"						SUM(round(t_bill_detail.taxable_amt,2)) as taxable_amt, \n" + 
			"						t_bill_detail.sgst_per+t_bill_detail.cgst_per as tax_rate, \n" + 
			"						round(SUM(t_bill_detail.sgst_rs),2) as sgst_rs, \n" + 
			"						round(SUM(t_bill_detail.cgst_rs),2) as cgst_rs, \n" + 
			"						SUM(t_bill_detail.igst_rs) as igst_rs, \n" + 
			"						t_bill_detail.cess_per, \n" + 
			"						SUM(t_bill_detail.cess_rs)as cess_rs, \n" + 
			"						round(SUM(t_bill_detail.total_tax),2) as total_tax, \n" + 
			"						round(SUM((t_bill_detail.sgst_rs+t_bill_detail.cgst_rs+round(t_bill_detail.taxable_amt,2))),2) as grand_total, \n" + 
			"						'MAHARASHTRA' as state, \n" + 
			"						'INDIA' as country from t_bill_header,t_bill_detail,m_item_sup s where  (t_bill_header.bill_date between :fromDate and  :toDate ) and t_bill_header.bill_no=t_bill_detail.bill_no and  s.item_id=t_bill_detail.item_id   group by t_bill_header.bill_no,item_hsncd \n" + 
			"                        \n" + 
			"    UNION ALL\n" + 
			"    select UUID() as id, \n" + 
			"    t_bill_header.bill_no,\n" + 
			"						t_bill_header.invoice_no,\n" + 
			"						t_bill_header.party_name, \n" + 
			"						t_bill_header.party_gstin, \n" + 
			"						t_bill_header.bill_date, \n" + 
			"						 (select round(SUM((t_bill_detail.sgst_rs+t_bill_detail.cgst_rs+round(t_bill_detail.taxable_amt,2))),2) from t_bill_detail where \n" + 
			"						        t_bill_detail.bill_no=t_bill_header.bill_no  group by t_bill_header.bill_no) as invoice_total, \n" + 
			"						m_spcake_sup.sp_hsncd, \n" + 
			"						SUM(t_bill_detail.bill_qty) as qty, \n" + 
			"						SUM(round(t_bill_detail.taxable_amt,2)) as taxable_amt, \n" + 
			"						t_bill_detail.sgst_per+t_bill_detail.cgst_per as tax_rate, \n" + 
			"						round(SUM(t_bill_detail.sgst_rs),2) as sgst_rs, \n" + 
			"						round(SUM(t_bill_detail.cgst_rs),2) as cgst_rs, \n" + 
			"						SUM(t_bill_detail.igst_rs) as igst_rs, \n" + 
			"						t_bill_detail.cess_per, \n" + 
			"						SUM(t_bill_detail.cess_rs)as cess_rs, \n" + 
			"						round(SUM(t_bill_detail.total_tax),2) as total_tax, \n" + 
			"						round(SUM((t_bill_detail.sgst_rs+t_bill_detail.cgst_rs+round(t_bill_detail.taxable_amt,2))),2) as grand_total, \n" + 
			"						'MAHARASHTRA' as state, \n" + 
			"						'INDIA' as country from t_bill_header,t_bill_detail,m_spcake_sup  where  (t_bill_header.bill_date between :fromDate and  :toDate ) and t_bill_header.bill_no=t_bill_detail.bill_no and  t_bill_detail.item_id=m_spcake_sup.sp_id   group by t_bill_header.bill_no,m_spcake_sup.sp_hsncd",nativeQuery=true)
	List<HsnwiseBillExcelSummary> getHsnwiseBillDataForExcelAll(@Param("fromDate")String fromDate,@Param("toDate") String toDate);

}
