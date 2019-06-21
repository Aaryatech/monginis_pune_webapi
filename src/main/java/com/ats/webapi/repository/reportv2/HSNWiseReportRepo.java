package com.ats.webapi.repository.reportv2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.reportv2.HSNWiseReport;

public interface HSNWiseReportRepo extends JpaRepository<HSNWiseReport, Integer> {

	@Query(value = "SELECT" + "            t_bill_detail.hsn_code as id,"
			+ "            t_bill_detail.hsn_code as item_hsncd," + "            t_bill_detail.sgst_per as item_tax1,"
			+ "             t_bill_detail.cgst_per as item_tax2,"
			+ "            SUM(t_bill_detail.bill_qty) as bill_qty,"
			+ "            SUM(t_bill_detail.taxable_amt) as taxable_amt,"
			+ "            SUM(t_bill_detail.cgst_rs) as cgst_rs," + "            SUM(t_bill_detail.sgst_rs) as sgst_rs"
			+ "        FROM t_bill_header," + "            t_bill_detail" + "        WHERE      t_bill_detail.cat_id!=5"
			+ "            AND t_bill_header.bill_no=t_bill_detail.bill_no"
			+ "            AND      t_bill_header.bill_date BETWEEN :fromDate AND :toDate" + "        GROUP BY"
			+ "            item_hsncd", nativeQuery = true)

	List<HSNWiseReport> getReport(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	@Query(value = "SELECT" + "            t_bill_detail.hsn_code as id,"
			+ "            t_bill_detail.hsn_code as item_hsncd," + "            t_bill_detail.sgst_per as item_tax1,"
			+ "             t_bill_detail.cgst_per as item_tax2,"
			+ "            SUM(t_bill_detail.bill_qty) as bill_qty,"
			+ "            SUM(t_bill_detail.taxable_amt) as taxable_amt,"
			+ "            SUM(t_bill_detail.cgst_rs) as cgst_rs," + "            SUM(t_bill_detail.sgst_rs) as sgst_rs"
			+ "        FROM t_bill_header," + "            t_bill_detail" + "        WHERE      t_bill_detail.cat_id!=5"
			+ "            AND t_bill_header.bill_no=t_bill_detail.bill_no"
			+ "            AND      t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.fr_id=:frId "
			+ "        GROUP BY" + "            item_hsncd", nativeQuery = true)

	List<HSNWiseReport> getReportByFrId(@Param("frId") int frId, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate);

	@Query(value = "SELECT" + "        t_credit_note_details.hsn_code AS id,"
			+ "           t_credit_note_details.hsn_code as item_hsncd,"
			+ "            t_credit_note_details.sgst_per as item_tax1,"
			+ "            t_credit_note_details.cgst_per as item_tax2,"
			+ "            SUM(t_credit_note_details.grn_gvn_qty) as bill_qty,"
			+ "            SUM(t_credit_note_details.taxable_amt) as taxable_amt,"
			+ "            SUM(t_credit_note_details.cgst_rs) as cgst_rs,"
			+ "            SUM(t_credit_note_details.sgst_rs) as sgst_rs" + "        FROM t_credit_note_details,"
			+ "            t_credit_note_header" + "        WHERE" + "              t_credit_note_details.cat_id!=5"
			+ "            and t_credit_note_header.crn_id=t_credit_note_details.crn_id"
			+ "            AND     t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate" + "        GROUP BY"
			+ "            item_hsncd", nativeQuery = true)

	List<HSNWiseReport> getReportHsn(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	@Query(value = "SELECT" + "        t_credit_note_details.hsn_code AS id,"
			+ "           t_credit_note_details.hsn_code as item_hsncd,"
			+ "            t_credit_note_details.sgst_per as item_tax1,"
			+ "            t_credit_note_details.cgst_per as item_tax2,"
			+ "            SUM(t_credit_note_details.grn_gvn_qty) as bill_qty,"
			+ "            SUM(t_credit_note_details.taxable_amt) as taxable_amt,"
			+ "            SUM(t_credit_note_details.cgst_rs) as cgst_rs,"
			+ "            SUM(t_credit_note_details.sgst_rs) as sgst_rs" + "        FROM t_credit_note_details,"
			+ "            t_credit_note_header" + "        WHERE" + "              t_credit_note_details.cat_id!=5"
			+ "            and t_credit_note_header.crn_id=t_credit_note_details.crn_id"
			+ "            AND     t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.fr_id=:frId "
			+ "        GROUP BY" + "            item_hsncd", nativeQuery = true)

	List<HSNWiseReport> getReportHsnByFrId(@Param("frId") int frId, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate);

}
