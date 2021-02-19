package com.ats.webapi.repository.reportv2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.reportv2.SalesReport;

public interface SalesReportRepo extends JpaRepository<SalesReport, Integer> {

	/*@Query(value = " SELECT    m_franchisee.fr_id,m_franchisee.fr_code,"
			+ "        m_franchisee.fr_name,  m_franchisee.fr_city,"
			+ "COALESCE((SELECT SUM(t_bill_header.grand_total) FROM  t_bill_header  WHERE "
			+ " t_bill_header.bill_date BETWEEN :fromDate AND :toDate  AND t_bill_header.del_status=0 "
			+ "   AND m_franchisee.fr_id=t_bill_header.fr_id ), 0) AS  sale_value,"
			+ " COALESCE((SELECT SUM(t_credit_note_header.crn_grand_total) FROM "
			+ "    t_credit_note_header  WHERE "
			+ "            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate "
			+ "            AND t_credit_note_header.is_grn=1 "
			+ "            AND m_franchisee.fr_id=t_credit_note_header.fr_id), 0) AS  grn_value,"
			+ "        COALESCE((SELECT SUM(t_credit_note_header.crn_grand_total)  FROM "
			+ "            t_credit_note_header WHERE "
			+ "            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate "
			+ "            AND t_credit_note_header.is_grn=0 "
			+ "            AND m_franchisee.fr_id=t_credit_note_header.fr_id), 0) AS  gvn_value FROM "
			+ "        m_franchisee  ORDER BY  m_franchisee.fr_id ", nativeQuery = true)*/
	@Query(value = "SELECT a.*,ifnull(b.sale_value,0) as sale_value,ifnull(c.grn_value,0) as grn_value,ifnull(d.gvn_value,0) as gvn_value from ( select\n" + 
			"        m_franchisee.fr_id,\n" + 
			"        m_franchisee.fr_code,\n" + 
			"        m_franchisee.fr_name,\n" + 
			"        m_franchisee.fr_city\n" + 
			"    FROM\n" + 
			"        m_franchisee  \n" + 
			"    ORDER BY\n" + 
			"        m_franchisee.fr_id ) a\n" + 
			"        \n" + 
			"    left join \n" + 
			"    (SELECT\n" + 
			"            SUM(t_bill_header.grand_total) as sale_value,t_bill_header.fr_id \n" + 
			"        FROM\n" + 
			"            t_bill_header  \n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate  \n" + 
			"            AND t_bill_header.del_status=0  group by t_bill_header.fr_id) b on a.fr_id=b.fr_id\n" + 
			"    left join \n" + 
			"    (SELECT\n" + 
			"            SUM(t_credit_note_header.crn_grand_total) as grn_value,\n" + 
			"            t_credit_note_header.fr_id\n" + 
			"        FROM\n" + 
			"            t_credit_note_header  \n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate              \n" + 
			"            AND t_credit_note_header.is_grn=1 group by t_credit_note_header.fr_id) c on a.fr_id=c.fr_id\n" + 
			"    left join \n" + 
			"    (SELECT\n" + 
			"            SUM(t_credit_note_header.crn_grand_total) as gvn_value,\n" + 
			"            t_credit_note_header.fr_id\n" + 
			"        FROM\n" + 
			"            t_credit_note_header \n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate             \n" + 
			"            AND t_credit_note_header.is_grn=0             \n" + 
			"            group by t_credit_note_header.fr_id) d on a.fr_id=d.fr_id", nativeQuery = true)
	List<SalesReport> getSalesReportAllFr(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	/*@Query(value = " SELECT    m_franchisee.fr_id,m_franchisee.fr_code,"
			+ "        m_franchisee.fr_name,  m_franchisee.fr_city,"
			+ "COALESCE((SELECT SUM(t_bill_header.grand_total) FROM  t_bill_header  WHERE "
			+ " t_bill_header.bill_date BETWEEN :fromDate AND :toDate  AND t_bill_header.del_status=0 "
			+ "   AND m_franchisee.fr_id=t_bill_header.fr_id ), 0) AS  sale_value,"
			+ " COALESCE((SELECT SUM(t_credit_note_header.crn_grand_total) FROM "
			+ "    t_credit_note_header  WHERE "
			+ "  t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate "
			+ " AND t_credit_note_header.is_grn=1 "
			+ " AND m_franchisee.fr_id=t_credit_note_header.fr_id), 0) AS  grn_value,"
			+ "  COALESCE((SELECT SUM(t_credit_note_header.crn_grand_total)  FROM "
			+ "  t_credit_note_header WHERE "
			+ "  t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate "
			+ "  AND t_credit_note_header.is_grn=0 "
			+ " AND m_franchisee.fr_id=t_credit_note_header.fr_id), 0) AS  gvn_value FROM "
			+ " m_franchisee WHERE m_franchisee.fr_id IN (:frIdList)   ORDER BY  m_franchisee.fr_id ", nativeQuery = true)*/

	@Query(value = "SELECT a.*,ifnull(b.sale_value,0) as sale_value,ifnull(c.grn_value,0) as grn_value,ifnull(d.gvn_value,0) as gvn_value from ( select\n" + 
			"        m_franchisee.fr_id,\n" + 
			"        m_franchisee.fr_code,\n" + 
			"        m_franchisee.fr_name,\n" + 
			"        m_franchisee.fr_city\n" + 
			"    FROM\n" + 
			"        m_franchisee  \n" + 
			"    ORDER BY\n" + 
			"        m_franchisee.fr_id ) a\n" + 
			"        \n" + 
			"    left join \n" + 
			"    (SELECT\n" + 
			"            SUM(t_bill_header.grand_total) as sale_value,t_bill_header.fr_id \n" + 
			"        FROM\n" + 
			"            t_bill_header  \n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate  \n" + 
			"            AND t_bill_header.del_status=0  group by t_bill_header.fr_id) b on a.fr_id=b.fr_id\n" + 
			"    left join \n" + 
			"    (SELECT\n" + 
			"            SUM(t_credit_note_header.crn_grand_total) as grn_value,\n" + 
			"            t_credit_note_header.fr_id\n" + 
			"        FROM\n" + 
			"            t_credit_note_header  \n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate              \n" + 
			"            AND t_credit_note_header.is_grn=1 group by t_credit_note_header.fr_id) c on a.fr_id=c.fr_id\n" + 
			"    left join \n" + 
			"    (SELECT\n" + 
			"            SUM(t_credit_note_header.crn_grand_total) as gvn_value,\n" + 
			"            t_credit_note_header.fr_id\n" + 
			"        FROM\n" + 
			"            t_credit_note_header \n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate             \n" + 
			"            AND t_credit_note_header.is_grn=0             \n" + 
			"            group by t_credit_note_header.fr_id) d on a.fr_id=d.fr_id where a.fr_id in (:frIdList)", nativeQuery = true)
	List<SalesReport> getSalesReportSpecFr(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			 @Param("frIdList") List<String> frIdList);

}
