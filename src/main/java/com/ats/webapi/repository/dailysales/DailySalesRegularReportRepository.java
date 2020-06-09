package com.ats.webapi.repository.dailysales;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.dailysales.DailySalesRegular;


@Repository
public interface DailySalesRegularReportRepository extends JpaRepository<DailySalesRegular, Integer> {

	@Query(value=" select cat_id,coalesce((SELECT COALESCE(SUM(t_bill_detail.bill_qty),0) FROM t_bill_detail \n" + 
			"    WHERE\n" + 
			"	t_bill_detail.cat_id=m_category.cat_id\n" + 
			"	AND t_bill_detail.bill_no IN(\n" + 
			"            SELECT\n" + 
			"                t_bill_header.bill_no \n" + 
			"            FROM\n" + 
			"                t_bill_header \n" + 
			"            WHERE\n" + 
			"                t_bill_header.fr_id=:frId\n" + 
			"                AND t_bill_header.status=2  \n" + 
			"                AND t_bill_header.bill_date=:date\n" + 
			"        )),0) as bill_qty,\n" + 
			"       coalesce((SELECT COALESCE(SUM(t_bill_detail.bill_qty*m_item.item_rate1),0) FROM t_bill_detail,m_item\n" + 
			"    WHERE\n" + 
			"	t_bill_detail.cat_id=m_category.cat_id and t_bill_detail.item_id=m_item.id\n" + 
			"	AND t_bill_detail.bill_no IN(\n" + 
			"            SELECT\n" + 
			"                t_bill_header.bill_no \n" + 
			"            FROM\n" + 
			"                t_bill_header \n" + 
			"            WHERE\n" + 
			"                t_bill_header.fr_id=:frId\n" + 
			"                AND t_bill_header.status=2  \n" + 
			"                AND t_bill_header.bill_date=:date\n" + 
			"        )),0) as bill_qty_rate,\n" + 
			" coalesce((SELECT COALESCE(SUM(t_bill_detail.bill_qty*m_item.item_mrp1),0) FROM t_bill_detail,m_item\n" + 
			"    WHERE\n" + 
			"	t_bill_detail.cat_id=m_category.cat_id and t_bill_detail.item_id=m_item.id\n" + 
			"	AND t_bill_detail.bill_no IN(\n" + 
			"            SELECT\n" + 
			"                t_bill_header.bill_no \n" + 
			"            FROM\n" + 
			"                t_bill_header \n" + 
			"            WHERE\n" + 
			"                t_bill_header.fr_id=:frId \n" + 
			"                AND t_bill_header.status=2  \n" + 
			"                AND t_bill_header.bill_date=:date\n" + 
			"        )),0) as bill_qty_mrp,\n" + 
			"coalesce((SELECT  COALESCE(SUM(t_grn_gvn.grn_gvn_qty),0) \n" + 
			"    FROM\n" + 
			"        t_grn_gvn \n" + 
			"    WHERE\n" + 
			"        t_grn_gvn.fr_id=1 and t_grn_gvn.cat_id=m_category.cat_id\n" + 
			"        AND t_grn_gvn.grn_gvn_date=:date),0) as grn_gvn_qty,\n" + 
			"coalesce((SELECT  COALESCE(SUM(t_grn_gvn.grn_gvn_amt),0) \n" + 
			"    FROM\n" + 
			"        t_grn_gvn \n" + 
			"    WHERE\n" + 
			"        t_grn_gvn.fr_id=1 and t_grn_gvn.cat_id=m_category.cat_id\n" + 
			"        AND t_grn_gvn.grn_gvn_date=:date),0) as grn_gvn_amt,\n" + 
			"\n" + 
			"coalesce((SELECT  COALESCE(SUM(t_sell_bill_detail.qty),0)\n" + 
			"    FROM\n" + 
			"        t_sell_bill_detail \n" + 
			"    WHERE\n" + 
			"	t_sell_bill_detail.cat_id=m_category.cat_id\n" + 
			"        AND t_sell_bill_detail.sell_bill_no IN (\n" + 
			"            SELECT\n" + 
			"                t_sell_bill_header.sell_bill_no \n" + 
			"            FROM\n" + 
			"                t_sell_bill_header \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.fr_id=:frId\n" + 
			"                AND t_sell_bill_header.bill_date=:date\n" + 
			"        )\n" + 
			"),0) as sell_qty,\n" + 
			"coalesce((SELECT  COALESCE(SUM(t_sell_bill_detail.qty*m_item.item_rate1),0)\n" + 
			"    FROM\n" + 
			"        t_sell_bill_detail,m_item\n" + 
			"    WHERE\n" + 
			"	t_sell_bill_detail.cat_id=m_category.cat_id and t_sell_bill_detail.item_id=m_item.id\n" + 
			"        AND t_sell_bill_detail.sell_bill_no IN (\n" + 
			"            SELECT\n" + 
			"                t_sell_bill_header.sell_bill_no \n" + 
			"            FROM\n" + 
			"                t_sell_bill_header \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.fr_id=:frId\n" + 
			"                AND t_sell_bill_header.bill_date=:date\n" + 
			"        )\n" + 
			"),0) as sell_qty_rate,\n" + 
			"coalesce((SELECT  COALESCE(SUM(t_sell_bill_detail.qty*m_item.item_mrp1),0)\n" + 
			"    FROM\n" + 
			"        t_sell_bill_detail,m_item\n" + 
			"    WHERE\n" + 
			"	t_sell_bill_detail.cat_id=m_category.cat_id and t_sell_bill_detail.item_id=m_item.id\n" + 
			"        AND t_sell_bill_detail.sell_bill_no IN (\n" + 
			"            SELECT\n" + 
			"                t_sell_bill_header.sell_bill_no \n" + 
			"            FROM\n" + 
			"                t_sell_bill_header \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.fr_id=:frId\n" + 
			"                AND t_sell_bill_header.bill_date=:date\n" + 
			"        )\n" + 
			"),0) as sell_qty_mrp,\n" + 
			"coalesce((\n" + 
			"SELECT\n" + 
			"        SUM(m_fr_opening_stock_detail.reg_opening_stock)\n" + 
			"    FROM\n" + 
			"        m_fr_opening_stock_detail  \n" + 
			"    WHERE\n" + 
			"         m_fr_opening_stock_detail.opening_stock_header_id  IN(\n" + 
			"            SELECT\n" + 
			"                m_fr_opening_stock_header.opening_stock_header_id \n" + 
			"            FROM\n" + 
			"                m_fr_opening_stock_header \n" + 
			"            WHERE\n" + 
			"                m_fr_opening_stock_header.fr_id=:frId \n" + 
			"                AND m_fr_opening_stock_header.month=:currentMonth \n" + 
			"                AND m_fr_opening_stock_header.year=:year \n" + 
			"                AND m_fr_opening_stock_header.cat_id=m_category.cat_id\n" + 
			"        )\n" + 
			"),0) as reg_opening_stock,\n" + 
			"coalesce((\n" + 
			"SELECT\n" + 
			"        SUM(m_fr_opening_stock_detail.reg_opening_stock*m_item.item_rate1)\n" + 
			"    FROM\n" + 
			"        m_fr_opening_stock_detail,m_item\n" + 
			"    WHERE\n" + 
			"        m_item.id=m_fr_opening_stock_detail.item_id\n" + 
			"        AND m_fr_opening_stock_detail.opening_stock_header_id  IN(\n" + 
			"            SELECT\n" + 
			"                m_fr_opening_stock_header.opening_stock_header_id \n" + 
			"            FROM\n" + 
			"                m_fr_opening_stock_header \n" + 
			"            WHERE\n" + 
			"                m_fr_opening_stock_header.fr_id=:frId \n" + 
			"                AND m_fr_opening_stock_header.month=:currentMonth  \n" + 
			"                AND m_fr_opening_stock_header.year=:year \n" + 
			"                AND m_fr_opening_stock_header.cat_id=m_category.cat_id\n" + 
			"        )\n" + 
			"),0) as reg_opening_stock_rate,\n" + 
			"coalesce((\n" + 
			"SELECT\n" + 
			"        SUM(m_fr_opening_stock_detail.reg_opening_stock*m_item.item_mrp1)\n" + 
			"    FROM\n" + 
			"        m_fr_opening_stock_detail,m_item\n" + 
			"    WHERE\n" + 
			"         m_item.id=m_fr_opening_stock_detail.item_id\n" + 
			"        AND m_fr_opening_stock_detail.opening_stock_header_id  IN(\n" + 
			"            SELECT\n" + 
			"                m_fr_opening_stock_header.opening_stock_header_id \n" + 
			"            FROM\n" + 
			"                m_fr_opening_stock_header \n" + 
			"            WHERE\n" + 
			"                m_fr_opening_stock_header.fr_id=:frId \n" + 
			"                AND m_fr_opening_stock_header.month=:currentMonth \n" + 
			"                AND m_fr_opening_stock_header.year=:year \n" + 
			"                AND m_fr_opening_stock_header.cat_id=m_category.cat_id\n" + 
			"        )\n" + 
			"),0) as reg_opening_stock_mrp\n" + 
			"\n" + 
			" from m_category where m_category.del_status=0 and m_category.cat_id!=5 and m_category.cat_id!=7",nativeQuery=true)
	List<DailySalesRegular> getDailySalesData(@Param("frId")int frId,@Param("date") String date,@Param("currentMonth") int currentMonth,@Param("year") int year);

	@Query(value=" select cat_id,coalesce((SELECT COALESCE(SUM(t_other_bill_detail.bill_qty),0) FROM t_other_bill_detail \n" + 
			"    WHERE\n" + 
			"	t_other_bill_detail.cat_id=m_category.cat_id\n" + 
			"	AND t_other_bill_detail.bill_no IN(\n" + 
			"            SELECT\n" + 
			"                t_other_bill_header.bill_no \n" + 
			"            FROM\n" + 
			"                t_other_bill_header \n" + 
			"            WHERE\n" + 
			"                t_other_bill_header.fr_id=:frId \n" + 
			"                AND t_other_bill_header.bill_date=:date\n" + 
			"        )),0) as bill_qty,\n" + 
			"       coalesce((SELECT COALESCE(SUM(t_other_bill_detail.bill_qty*m_item.item_rate1),0) FROM t_other_bill_detail,m_item\n" + 
			"    WHERE\n" + 
			"	t_other_bill_detail.cat_id=m_category.cat_id and t_other_bill_detail.item_id=m_item.id\n" + 
			"	AND t_other_bill_detail.bill_no IN(\n" + 
			"            SELECT\n" + 
			"                t_other_bill_header.bill_no \n" + 
			"            FROM\n" + 
			"                t_other_bill_header \n" + 
			"            WHERE\n" + 
			"                t_other_bill_header.fr_id=:frId\n" + 
			"                AND t_other_bill_header.bill_date=:date\n" + 
			"        )),0) as bill_qty_rate,\n" + 
			" coalesce((SELECT COALESCE(SUM(t_other_bill_detail.bill_qty*m_item.item_mrp1),0) FROM t_other_bill_detail,m_item\n" + 
			"    WHERE\n" + 
			"	t_other_bill_detail.cat_id=m_category.cat_id and t_other_bill_detail.item_id=m_item.id\n" + 
			"	AND t_other_bill_detail.bill_no IN(\n" + 
			"            SELECT\n" + 
			"                t_other_bill_header.bill_no \n" + 
			"            FROM\n" + 
			"                t_other_bill_header \n" + 
			"            WHERE\n" + 
			"                t_other_bill_header.fr_id=:frId \n" + 
			"                AND t_other_bill_header.bill_date=:date\n" + 
			"        )),0) as bill_qty_mrp,\n" + 
			"0 as grn_gvn_qty,\n" + 
			"0 as grn_gvn_amt,\n" + 
			"\n" + 
			"coalesce((SELECT  COALESCE(SUM(t_sell_bill_detail.qty),0)\n" + 
			"    FROM\n" + 
			"        t_sell_bill_detail \n" + 
			"    WHERE\n" + 
			"	t_sell_bill_detail.cat_id=m_category.cat_id\n" + 
			"        AND t_sell_bill_detail.sell_bill_no IN (\n" + 
			"            SELECT\n" + 
			"                t_sell_bill_header.sell_bill_no \n" + 
			"            FROM\n" + 
			"                t_sell_bill_header \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.fr_id=:frId\n" + 
			"                AND t_sell_bill_header.bill_date=:date\n" + 
			"        )\n" + 
			"),0) as sell_qty,\n" + 
			"coalesce((SELECT  COALESCE(SUM(t_sell_bill_detail.qty*m_item.item_rate1),0)\n" + 
			"    FROM\n" + 
			"        t_sell_bill_detail,m_item\n" + 
			"    WHERE\n" + 
			"	t_sell_bill_detail.cat_id=m_category.cat_id and t_sell_bill_detail.item_id=m_item.id\n" + 
			"        AND t_sell_bill_detail.sell_bill_no IN (\n" + 
			"            SELECT\n" + 
			"                t_sell_bill_header.sell_bill_no \n" + 
			"            FROM\n" + 
			"                t_sell_bill_header \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.fr_id=:frId\n" + 
			"                AND t_sell_bill_header.bill_date=:date\n" + 
			"        )\n" + 
			"),0) as sell_qty_rate,\n" + 
			"coalesce((SELECT  COALESCE(SUM(t_sell_bill_detail.qty*m_item.item_mrp1),0)\n" + 
			"    FROM\n" + 
			"        t_sell_bill_detail,m_item\n" + 
			"    WHERE\n" + 
			"	t_sell_bill_detail.cat_id=m_category.cat_id and t_sell_bill_detail.item_id=m_item.id\n" + 
			"        AND t_sell_bill_detail.sell_bill_no IN (\n" + 
			"            SELECT\n" + 
			"                t_sell_bill_header.sell_bill_no \n" + 
			"            FROM\n" + 
			"                t_sell_bill_header \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.fr_id=:frId\n" + 
			"                AND t_sell_bill_header.bill_date=:date\n" + 
			"        )\n" + 
			"),0) as sell_qty_mrp,\n" + 
			"coalesce((\n" + 
			"SELECT\n" + 
			"        SUM(other_item_stock_detail.opening_stock)\n" + 
			"    FROM\n" + 
			"        other_item_stock_detail  \n" + 
			"    WHERE\n" + 
			"         other_item_stock_detail.other_stock_header_id  IN(\n" + 
			"            SELECT\n" + 
			"                other_item_stock_header.other_stock_header_id \n" + 
			"            FROM\n" + 
			"                other_item_stock_header \n" + 
			"            WHERE\n" + 
			"                other_item_stock_header.fr_id=:frId \n" + 
			"                AND other_item_stock_header.month=:currentMonth \n" + 
			"                AND other_item_stock_header.year=:year \n" + 
			"        )\n" + 
			"),0) as reg_opening_stock,\n" + 
			"coalesce((\n" + 
			"SELECT\n" + 
			"        SUM(other_item_stock_detail.opening_stock*m_item.item_rate1)\n" + 
			"    FROM\n" + 
			"        other_item_stock_detail,m_item\n" + 
			"    WHERE\n" + 
			"        m_item.id=other_item_stock_detail.other_item_id\n" + 
			"        AND other_item_stock_detail.other_stock_header_id  IN(\n" + 
			"            SELECT\n" + 
			"                other_item_stock_header.other_stock_header_id \n" + 
			"            FROM\n" + 
			"                other_item_stock_header \n" + 
			"            WHERE\n" + 
			"                other_item_stock_header.fr_id=:frId \n" + 
			"                AND other_item_stock_header.month=:currentMonth \n" + 
			"                AND other_item_stock_header.year=:year  \n" + 
			"        )\n" + 
			"),0) as reg_opening_stock_rate,\n" + 
			"coalesce((\n" + 
			"SELECT\n" + 
			"        SUM(other_item_stock_detail.opening_stock*m_item.item_mrp1)\n" + 
			"    FROM\n" + 
			"        other_item_stock_detail,m_item\n" + 
			"    WHERE\n" + 
			"         m_item.id=other_item_stock_detail.other_item_id\n" + 
			"        AND other_item_stock_detail.other_stock_header_id  IN(\n" + 
			"            SELECT\n" + 
			"                other_item_stock_header.other_stock_header_id \n" + 
			"            FROM\n" + 
			"                other_item_stock_header \n" + 
			"            WHERE\n" + 
			"                other_item_stock_header.fr_id=:frId\n" + 
			"                AND other_item_stock_header.month=:currentMonth\n" + 
			"                AND other_item_stock_header.year=:year \n" + 
			"        )\n" + 
			"),0) as reg_opening_stock_mrp\n" + 
			"\n" + 
			" from m_category where m_category.del_status=0 and  m_category.cat_id=7",nativeQuery=true)
	DailySalesRegular getDailySalesOtherData(@Param("frId")int frId,@Param("date") String date,@Param("currentMonth") int currentMonth,@Param("year") int year);

	
	
	
	//Anmol
	
	@Query(value=" SELECT\n" + 
			"    t1.opening + t2.purchase - t3.grn_gvn - t4.sale AS opening_amt\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        1 AS flag,\n" + 
			"        SUM(a.tot) AS opening\n" + 
			"    FROM\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            h.*,\n" + 
			"            d.opening_stock_detail_id,\n" + 
			"            d.item_id,\n" + 
			"            d.reg_opening_stock,\n" + 
			"            CASE WHEN f.fr_rate_cat = 1 THEN i.item_mrp1 ELSE i.item_mrp3\n" + 
			"    END AS mrp,\n" + 
			"    CASE WHEN f.fr_rate_cat = 1 THEN(\n" + 
			"        d.reg_opening_stock * i.item_mrp1\n" + 
			"    ) ELSE(\n" + 
			"        d.reg_opening_stock * i.item_mrp3\n" + 
			"    )\n" + 
			"END AS tot\n" + 
			"FROM\n" + 
			"    m_fr_opening_stock_header h,\n" + 
			"    m_fr_opening_stock_detail d,\n" + 
			"    m_item i,\n" + 
			"    m_franchisee f\n" + 
			"WHERE\n" + 
			"    h.opening_stock_header_id = d.opening_stock_header_id AND h.is_month_closed = 0 AND h.fr_id =:frId AND h.fr_id = f.fr_id AND d.item_id = i.id\n" + 
			") a\n" + 
			") t1\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        1 AS flag,\n" + 
			"        CASE WHEN(\n" + 
			"        SELECT\n" + 
			"            h.month\n" + 
			"        FROM\n" + 
			"            m_fr_opening_stock_header h\n" + 
			"        WHERE\n" + 
			"            h.fr_id =:frId AND h.is_month_closed = 0\n" + 
			"        GROUP BY\n" + 
			"            h.fr_id\n" + 
			"    ) =(\n" + 
			"    SELECT\n" + 
			"        DATE_FORMAT(:date, '%m')\n" + 
			") THEN COALESCE(SUM(d.bill_qty*d.mrp),0) ELSE 0\n" + 
			"END AS purchase\n" + 
			"FROM\n" + 
			"    t_bill_header h,\n" + 
			"    t_bill_detail d\n" + 
			"WHERE\n" + 
			"    h.del_status = 0 AND h.bill_no=d.bill_no AND h.fr_id =:frId AND h.bill_date BETWEEN(\n" + 
			"    SELECT\n" + 
			"        CONCAT(\n" + 
			"            h.year,\n" + 
			"            '-',\n" + 
			"            LPAD(h.month, 2, 0),\n" + 
			"            '-01'\n" + 
			"        )\n" + 
			"    FROM\n" + 
			"        m_fr_opening_stock_header h\n" + 
			"    WHERE\n" + 
			"        h.fr_id =:frId AND h.is_month_closed = 0\n" + 
			"    GROUP BY\n" + 
			"        h.fr_id\n" + 
			") AND DATE_FORMAT(\n" + 
			"    SUBDATE(:date, INTERVAL 1 DAY),\n" + 
			"    '%Y-%m-%d'\n" + 
			")\n" + 
			") t2\n" + 
			"ON\n" + 
			"    t1.flag = t2.flag\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        1 AS flag,\n" + 
			"        CASE WHEN(\n" + 
			"        SELECT\n" + 
			"            h.month\n" + 
			"        FROM\n" + 
			"            m_fr_opening_stock_header h\n" + 
			"        WHERE\n" + 
			"            h.fr_id =:frId AND h.is_month_closed = 0\n" + 
			"        GROUP BY\n" + 
			"            h.fr_id\n" + 
			"    ) =(\n" + 
			"    SELECT\n" + 
			"        DATE_FORMAT(:date, '%m')\n" + 
			") THEN CASE WHEN\n" + 
			"    f.fr_rate_cat = 1 THEN COALESCE(SUM(d.grn_gvn_qty * i.item_mrp1),0) ELSE COALESCE(SUM(d.grn_gvn_qty * i.item_mrp3),0)\n" + 
			"END ELSE 0\n" + 
			"END AS grn_gvn\n" + 
			"FROM\n" + 
			"    t_grn_gvn_header h,\n" + 
			"    t_grn_gvn d,\n" + 
			"    m_item i,\n" + 
			"    m_franchisee f\n" + 
			"WHERE\n" + 
			"    h.grn_gvn_header_id = d.grn_gvn_header_id AND d.item_id = i.id AND h.fr_id = f.fr_id AND h.fr_id =:frId AND h.grngvn_date BETWEEN(\n" + 
			"    SELECT\n" + 
			"        CONCAT(\n" + 
			"            h.year,\n" + 
			"            '-',\n" + 
			"            LPAD(h.month, 2, 0),\n" + 
			"            '-01'\n" + 
			"        )\n" + 
			"    FROM\n" + 
			"        m_fr_opening_stock_header h\n" + 
			"    WHERE\n" + 
			"        h.fr_id =:frId AND h.is_month_closed = 0\n" + 
			"    GROUP BY\n" + 
			"        h.fr_id\n" + 
			") AND DATE_FORMAT(\n" + 
			"    SUBDATE(:date, INTERVAL 1 DAY),\n" + 
			"    '%Y-%m-%d'\n" + 
			")\n" + 
			") t3\n" + 
			"ON\n" + 
			"    t1.flag = t3.flag\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        1 AS flag,\n" + 
			"        CASE WHEN(\n" + 
			"        SELECT\n" + 
			"            h.month\n" + 
			"        FROM\n" + 
			"            m_fr_opening_stock_header h\n" + 
			"        WHERE\n" + 
			"            h.fr_id =:frId AND h.is_month_closed = 0\n" + 
			"        GROUP BY\n" + 
			"            h.fr_id\n" + 
			"    ) =(\n" + 
			"    SELECT\n" + 
			"        DATE_FORMAT(:date, '%m')\n" + 
			") THEN COALESCE(SUM(grand_total),0) ELSE 0\n" + 
			"END AS sale\n" + 
			"FROM\n" + 
			"    t_sell_bill_header h\n" + 
			"WHERE\n" + 
			"    h.del_status = 0 AND h.fr_id =:frId AND h.bill_date BETWEEN(\n" + 
			"    SELECT\n" + 
			"        CONCAT(\n" + 
			"            h.year,\n" + 
			"            '-',\n" + 
			"            LPAD(h.month, 2, 0),\n" + 
			"            '-01'\n" + 
			"        )\n" + 
			"    FROM\n" + 
			"        m_fr_opening_stock_header h\n" + 
			"    WHERE\n" + 
			"        h.fr_id =:frId AND h.is_month_closed = 0\n" + 
			"    GROUP BY\n" + 
			"        h.fr_id\n" + 
			") AND DATE_FORMAT(\n" + 
			"    SUBDATE(:date, INTERVAL 1 DAY),\n" + 
			"    '%Y-%m-%d'\n" + 
			")\n" + 
			") t4\n" + 
			"ON\n" + 
			"    t1.flag = t4.flag",nativeQuery=true)
	float getOpeningAmtForDSR(@Param("frId")int frId,@Param("date") String date);

	
	@Query(value=" SELECT COALESCE(SUM(d.bill_qty*d.mrp),0) as purchase FROM t_bill_header h, t_bill_detail d WHERE h.bill_no=d.bill_no AND h.del_status=0 AND h.bill_date=:date AND h.fr_id=:frId AND d.cat_id!=5",nativeQuery=true)
	float getPurchaseAmtForDSR(@Param("frId")int frId,@Param("date") String date);

	@Query(value=" SELECT CASE WHEN f.fr_rate_cat=1 THEN COALESCE(SUM(d.grn_gvn_qty*i.item_mrp1), 0) ELSE COALESCE(SUM(d.grn_gvn_qty*i.item_mrp3), 0) END AS grn_gvn FROM t_grn_gvn_header h, t_grn_gvn d, m_item i, m_franchisee f WHERE h.grngvn_date =:date AND h.fr_id =:frId AND h.grn_gvn_header_id = d.grn_gvn_header_id AND h.fr_id=f.fr_id AND d.item_id=i.id AND d.cat_id != 5  ",nativeQuery=true)
	float getGrnGvnAmtForDSR(@Param("frId")int frId,@Param("date") String date);

	@Query(value=" SELECT COALESCE(SUM(d.grand_total),0) as sale FROM t_sell_bill_header h, t_sell_bill_detail d WHERE h.sell_bill_no=d.sell_bill_no AND h.del_status=0 AND h.bill_date=:date AND h.fr_id=:frId AND d.cat_id!=5",nativeQuery=true)
	float getSaleAmtForDSR(@Param("frId")int frId,@Param("date") String date);
	
	
	@Query(value=" SELECT COALESCE(GROUP_CONCAT(h.bill_no),'') FROM t_bill_header h WHERE h.del_status=0 AND h.fr_id=:frId AND h.bill_date=:date",nativeQuery=true)
	String getPurchaseBillIds(@Param("frId")int frId,@Param("date") String date);

	@Query(value=" SELECT COALESCE(GROUP_CONCAT(exp_id),'') FROM m_expense WHERE fr_id=:frId AND exp_date=:date AND del_status=0",nativeQuery=true)
	String getExpenseIds(@Param("frId")int frId,@Param("date") String date);


	
}
