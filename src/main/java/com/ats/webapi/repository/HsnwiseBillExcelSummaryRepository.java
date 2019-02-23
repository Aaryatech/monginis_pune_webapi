package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.HsnwiseBillExcelSummary;
@Repository
public interface HsnwiseBillExcelSummaryRepository extends JpaRepository<HsnwiseBillExcelSummary, Integer> {

	@Query(value="select UUID() as id,\n" + 
			"t_bill_header.bill_no,\n" + 
			"t_bill_header.invoice_no,\n" + 
			"t_bill_header.party_name,\n" + 
			"t_bill_header.party_gstin,\n" + 
			"t_bill_header.bill_date,\n" + 
			"t_bill_header.grand_total as invoice_total,\n" + 
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
			"'INDIA' as country from t_bill_header,t_bill_detail,m_item_sup s where t_bill_header.bill_no in(:billNos) and t_bill_header.bill_no=t_bill_detail.bill_no and  s.item_id=t_bill_detail.item_id group by t_bill_header.bill_no,item_hsncd",nativeQuery=true)
	List<HsnwiseBillExcelSummary> getHsnwiseBillDataForExcel(@Param("billNos")List<String> billNos);

	@Query(value="select UUID() as id,\n" + 
			"t_bill_header.bill_no,\n" + 
			"t_bill_header.invoice_no,\n" + 
			"t_bill_header.party_name,\n" + 
			"t_bill_header.party_gstin,\n" + 
			"t_bill_header.bill_date,\n" + 
			"t_bill_header.grand_total as invoice_total,\n" + 
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
	List<HsnwiseBillExcelSummary> getHsnwiseBillDataForExcelAll(@Param("fromDate")String fromDate,@Param("toDate") String toDate);

}
