package com.ats.webapi.repository.salesreport3;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.salesreport3.FrAndSubCatBillData;

public interface FrAndSubCatBillDataRepo extends JpaRepository<FrAndSubCatBillData, Integer> {

	@Query(value = "SELECT\r\n" + 
			"    bd.bill_detail_no,\r\n" + 
			"    sc.sub_cat_id,\r\n" + 
			"    f.fr_id,\r\n" + 
			"    SUM(bd.grand_total) AS sold_amt,\r\n" + 
			"    SUM(bd.bill_qty) AS sold_qty,\r\n" + 
			"    SUM(bd.taxable_amt) AS taxable_amt,\r\n" + 
			"    SUM(bd.total_tax) AS tax_amt,\r\n" + 
			"    f.fr_name,\r\n" + 
			"    sc.sub_cat_name,\r\n" + 
			"    MONTH(bh.bill_date) AS MONTH,\r\n" + 
			"    YEAR(bh.bill_date) AS YEAR\r\n" + 
			"FROM\r\n" + 
			"    t_bill_detail bd,\r\n" + 
			"    m_cat_sub sc,\r\n" + 
			"    t_bill_header bh,\r\n" + 
			"    m_item i,\r\n" + 
			"    m_franchisee f\r\n" + 
			"WHERE\r\n" + 
			"    bh.bill_no = bd.bill_no AND bd.item_id = i.id AND i.item_grp2 = sc.sub_cat_id AND bh.fr_id = f.fr_id AND bh.del_status = 0 AND bd.del_status = 0 AND sc.sub_cat_id IN(:subCatIdList) AND bh.fr_id IN(:frIdList) AND bh.bill_date BETWEEN :fromDate AND :toDate AND bd.cat_id != 5\r\n" + 
			"GROUP BY\r\n" + 
			"    bh.fr_id,\r\n" + 
			"    i.item_grp2,\r\n" + 
			"    MONTH(bh.bill_date),\r\n" + 
			"    YEAR(bh.bill_date)\r\n" + 
			"UNION\r\n" + 
			"SELECT\r\n" + 
			"    bd.bill_detail_no,\r\n" + 
			"    sc.sub_cat_id,\r\n" + 
			"    f.fr_id,\r\n" + 
			"    SUM(bd.grand_total) AS sold_amt,\r\n" + 
			"    SUM(bd.bill_qty) AS sold_qty,\r\n" + 
			"    SUM(bd.taxable_amt) AS taxable_amt,\r\n" + 
			"    SUM(bd.total_tax) AS tax_amt,\r\n" + 
			"    f.fr_name,\r\n" + 
			"    sc.sub_cat_name,\r\n" + 
			"    MONTH(bh.bill_date) AS MONTH,\r\n" + 
			"    YEAR(bh.bill_date) AS YEAR\r\n" + 
			"FROM\r\n" + 
			"    t_bill_detail bd,\r\n" + 
			"    m_cat_sub sc,\r\n" + 
			"    t_bill_header bh,\r\n" + 
			"    m_sp_cake sp,\r\n" + 
			"    m_franchisee f\r\n" + 
			"WHERE\r\n" + 
			"    bh.bill_no = bd.bill_no AND bd.item_id = sp.sp_id AND bd.cat_id = sc.cat_id AND bh.fr_id = f.fr_id AND bh.del_status = 0 AND bd.del_status = 0 AND sc.sub_cat_id IN(:subCatIdList) AND bh.fr_id IN(:frIdList) AND bh.bill_date BETWEEN :fromDate AND :toDate AND bd.cat_id = 5\r\n" + 
			"GROUP BY\r\n" + 
			"    bh.fr_id,\r\n" + 
			//"    sp.sp_id,\r\n" + 
			"sc.sub_cat_id,"+
			"    MONTH(bh.bill_date),\r\n" + 
			"    YEAR(bh.bill_date) ", nativeQuery = true)
	List<FrAndSubCatBillData> getBillData(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("frIdList") List<Integer> frIds, @Param("subCatIdList") List<Integer> subCatIds);

}
