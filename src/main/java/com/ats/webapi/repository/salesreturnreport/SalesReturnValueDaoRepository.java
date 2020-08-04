package com.ats.webapi.repository.salesreturnreport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.salesvaluereport.SalesReturnValueDao;

public interface SalesReturnValueDaoRepository extends JpaRepository<SalesReturnValueDao, Integer>{

	
	//Prev Query correct output but speed issue
	/*@Query(value="select CONCAT(:month,\"\",sub_cat_id) as id,sub_cat_id,coalesce((SELECT  SUM(t_bill_detail.grand_total) as grand_total   FROM    t_bill_detail,t_bill_header,m_item    WHERE   DATE_FORMAT(t_bill_header.bill_date,'%Y-%m')=:month AND t_bill_header.del_status=0 And t_bill_header.bill_no=t_bill_detail.bill_no and t_bill_detail.cat_id!=5 and m_item.item_grp2=m_cat_sub.sub_cat_id and m_item.id=t_bill_detail.item_id),0) as grand_total,\n" + 
			"\n" + 
			"coalesce((select SUM(grn_gvn_amt) as grn_qty from t_credit_note_header,t_credit_note_details,m_item where t_credit_note_header.crn_id=t_credit_note_details.crn_id and t_credit_note_header.is_grn=1 and DATE_FORMAT(t_credit_note_header.crn_date,'%Y-%m')=:month and t_credit_note_details.cat_id!=5 and m_item.id=t_credit_note_details.item_id and m_item.item_grp2=m_cat_sub.sub_cat_id),0) as grn_qty,\n" + 
			"\n" + 
			"coalesce((select SUM(grn_gvn_amt) as gvn_qty from t_credit_note_header,t_credit_note_details,m_item where t_credit_note_header.crn_id=t_credit_note_details.crn_id  and DATE_FORMAT(t_credit_note_header.crn_date,'%Y-%m')=:month and t_credit_note_header.is_grn=0 and t_credit_note_details.cat_id!=5 and m_item.id=t_credit_note_details.item_id and m_item.item_grp2=m_cat_sub.sub_cat_id),0) as gvn_qty\n" + 
			"\n" + 
			"from m_cat_sub where m_cat_sub.cat_id!=5 and m_cat_sub.del_status=0\n" + 
			"\n" + 
			"UNION ALL\n" + 
			"select  CONCAT(:month,\"\",sub_cat_id) as id,m_cat_sub.sub_cat_id,coalesce((SELECT\n" + 
			"        SUM(t_bill_detail.grand_total) as bill_qty\n" + 
			"          FROM\n" + 
			"        t_bill_detail,t_bill_header,m_sp_cake\n" + 
			"    WHERE\n" + 
			"        DATE_FORMAT(t_bill_header.bill_date,'%Y-%m')=:month\n" + 
			"        AND t_bill_header.del_status=0 And t_bill_header.bill_no=t_bill_detail.bill_no and t_bill_detail.cat_id=5 and  m_sp_cake.sp_id=t_bill_detail.item_id),0) as bill_qty,\n" + 
			"        0 as grn_qty,\n" + 
			"        coalesce((select SUM(grn_gvn_amt) as gvn_qty from t_credit_note_header,t_credit_note_details,m_sp_cake where t_credit_note_header.crn_id=t_credit_note_details.crn_id and t_credit_note_header.is_grn=0 and DATE_FORMAT(t_credit_note_header.crn_date,'%Y-%m')=:month and t_credit_note_details.cat_id=5 and m_sp_cake.sp_id=t_credit_note_details.item_id),0) as gvn_qty\n" + 
			"        from m_cat_sub where m_cat_sub.cat_id=5 and m_cat_sub.del_status=0",nativeQuery=true)
	List<SalesReturnValueDao> getSalesReturnValueReport(@Param("month")String month);

	*/
	
	//Latest Query correct output with fast data processing
	
	//Sachin 04-08-2020 changes query for left join 
	
	@Query(value=" SELECT\n" + 
			"    CONCAT(:month, \"\", item_sub_cat.sub_cat_id) AS id,\n" + 
			"    item_sub_cat.sub_cat_id,\n" + 
			"    COALESCE((bill_data.grand_total),\n" + 
			"    0) AS grand_total,\n" + 
			"    COALESCE((grn_data.grn_qty),\n" + 
			"    0) AS grn_qty,\n" + 
			"    COALESCE((gvn_data.gvn_qty),\n" + 
			"    0) AS gvn_qty\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        s.sub_cat_id\n" + 
			"    FROM\n" + 
			"        m_cat_sub s\n" + 
			"    WHERE\n" + 
			"        s.cat_id != 5 AND s.del_status = 0 \n" + 
			") item_sub_cat\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT SUM(t_bill_detail.grand_total) AS grand_total,\n" + 
			"        m_item.item_grp2\n" + 
			"    FROM\n" + 
			"        t_bill_detail,\n" + 
			"        t_bill_header,\n" + 
			"        m_item\n" + 
			"    WHERE\n" + 
			"        DATE_FORMAT(t_bill_header.bill_date, '%Y-%m') =:month AND t_bill_header.del_status = 0 AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id\n" + 
			"    GROUP BY\n" + 
			"        m_item.item_grp2\n" + 
			") bill_data\n" + 
			"ON\n" + 
			"    bill_data.item_grp2 =item_sub_cat.sub_cat_id\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT SUM(grn_gvn_amt) AS grn_qty,\n" + 
			"      \n" + 
			"        m_item.item_grp2\n" + 
			"    FROM\n" + 
			"        t_credit_note_header,\n" + 
			"        t_credit_note_details,\n" + 
			"        m_item\n" + 
			"    WHERE\n" + 
			"        t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.item_id = m_item.id AND DATE_FORMAT(\n" + 
			"            t_credit_note_header.crn_date,\n" + 
			"            '%Y-%m'\n" + 
			"        ) =:month\n" + 
			"    GROUP BY\n" + 
			"        m_item.item_grp2\n" + 
			") grn_data\n" + 
			"ON\n" + 
			"    item_sub_cat.sub_cat_id = grn_data.item_grp2\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT SUM(grn_gvn_amt) AS gvn_qty,\n" + 
			"        m_item.item_grp2\n" + 
			"    FROM\n" + 
			"        t_credit_note_header,\n" + 
			"        t_credit_note_details,m_item\n" + 
			"    WHERE\n" + 
			"        t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_details.item_id = m_item.id AND t_credit_note_header.is_grn = 0 AND DATE_FORMAT(\n" + 
			"            t_credit_note_header.crn_date,'%Y-%m') = :month\n" + 
			"    GROUP BY\n" + 
			"        m_item.item_grp2\n" + 
			") gvn_data\n" + 
			"ON\n" + 
			"    item_sub_cat.sub_cat_id = gvn_data.item_grp2\n" + 
			"UNION ALL\n" + 
			"SELECT\n" + 
			"    CONCAT(:month, \"\", item_sub_cat.sub_cat_id) AS id,\n" + 
			"    item_sub_cat.sub_cat_id,\n" + 
			"    COALESCE((bill_data.grand_total),\n" + 
			"    0) AS grand_total,\n" + 
			"    COALESCE((grn_data.grn_qty),\n" + 
			"    0) AS grn_qty,\n" + 
			"    COALESCE((gvn_data.gvn_qty),\n" + 
			"    0) AS gvn_qty\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        s.sub_cat_id,\n" + 
			"        s.cat_id\n" + 
			"    FROM\n" + 
			"        m_cat_sub s\n" + 
			"    WHERE\n" + 
			"        s.cat_id = 5 AND s.del_status = 0\n" + 
			") item_sub_cat\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT SUM(t_bill_detail.grand_total) AS grand_total,\n" + 
			"        t_bill_detail.cat_id\n" + 
			"    FROM\n" + 
			"        t_bill_detail,\n" + 
			"        t_bill_header\n" + 
			"    WHERE\n" + 
			"        DATE_FORMAT(t_bill_header.bill_date, '%Y-%m') = :month AND t_bill_header.del_status = 0 AND t_bill_header.bill_no = t_bill_detail.bill_no\n" + 
			"    GROUP BY\n" + 
			"        t_bill_detail.cat_id\n" + 
			") bill_data\n" + 
			"ON\n" + 
			"    item_sub_cat.cat_id = bill_data.cat_id\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT SUM(grn_gvn_amt) AS grn_qty,\n" + 
			"        t_credit_note_details.cat_id\n" + 
			"    FROM\n" + 
			"        t_credit_note_header,\n" + 
			"        t_credit_note_details\n" + 
			"    WHERE\n" + 
			"        t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.is_grn = 1 AND DATE_FORMAT(\n" + 
			"            t_credit_note_header.crn_date,\n" + 
			"            '%Y-%m'\n" + 
			"        ) = :month\n" + 
			"    GROUP BY\n" + 
			"        t_credit_note_details.cat_id\n" + 
			") grn_data\n" + 
			"ON\n" + 
			"    item_sub_cat.cat_id = grn_data.cat_id\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT SUM(grn_gvn_amt) AS gvn_qty,\n" + 
			"        t_credit_note_details.cat_id\n" + 
			"    FROM\n" + 
			"        t_credit_note_header,\n" + 
			"        t_credit_note_details\n" + 
			"    WHERE\n" + 
			"        t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.is_grn = 0 AND DATE_FORMAT(\n" + 
			"            t_credit_note_header.crn_date,\n" + 
			"            '%Y-%m'\n" + 
			"        ) =:month\n" + 
			"    GROUP BY\n" + 
			"        t_credit_note_details.cat_id\n" + 
			") gvn_data\n" + 
			"ON\n" + 
			"    item_sub_cat.cat_id = gvn_data.cat_id	",nativeQuery=true)
	List<SalesReturnValueDao> getSalesReturnValueReport(@Param("month")String month);
	




	  
	       
}
