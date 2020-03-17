package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ats.webapi.model.Info;
import com.ats.webapi.model.Item;
import com.ats.webapi.model.ItemIdOnly;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	public Item save(Item item);

	public Item findOne(int id);

	public List<Item> findByItemGrp1AndDelStatusOrderByItemGrp2Asc(String itemGrp1, int i);

	@Query(value = "\n"
			+ "select i.id,i.item_id,s.short_name as item_name,i.item_grp1,i.item_grp2,i.item_grp3,i.item_rate1,i.item_rate2,i.item_rate3,i.item_mrp1,i.item_mrp2,i.item_mrp3,i.item_image,i.item_tax1,i.item_tax2,i.item_tax3,i.item_is_used,i.item_sort_id,i.grn_two,i.del_status,i.min_qty,i.item_shelf_life from m_item i,m_item_sup s where s.item_id=i.id and i.id IN (:itemList) AND i.del_status=0", nativeQuery = true)
	public List<Item> findByDelStatusAndItemIdIn(@Param("itemList") List<Integer> itemList);

	public List<Item> findByDelStatusOrderByItemGrp2(int i);// changed to order by subcatId 21/Apr

	@Query(value = "select MAX(CAST(SUBSTRING(item_id,1,LENGTH(item_id)-0) AS SIGNED))+1  from m_item\n"
			+ "", nativeQuery = true)
	public int findMaxId();

	public List<Item> findByItemGrp1AndDelStatusOrderByItemGrp1AscItemGrp2AscItemNameAsc(String itemGrp1, int i);
	
	public List<Item> findByDelStatusAndIdInOrderByItemGrp1AscItemGrp2AscItemNameAsc(int i,List<Integer> itemIds);

//coalesce((select m_item_sup.short_name from m_item_sup where m_item_sup.item_id=i.id),0) as
	// Sachin 25 FEB
	@Query(value = "SELECT m_item.* FROM m_item WHERE  FIND_IN_SET(m_item.id ,(SELECT "
			+ "        m_fr_configure.item_show " + "        FROM " + "        m_fr_configure     WHERE "
			+ "        m_fr_configure.cat_id=:catId AND m_fr_configure.is_del=0 "
			+ "        AND m_fr_configure.setting_id in ( " + "            select " + "                menu_id  "
			+ "            from " + "                m_fr_menu_configure " + "            where "
			+ "                fr_id=:frId" + "        ))  )", nativeQuery = true)
	public List<Item> getOtherItemsForFr(@Param("frId") int frId, @Param("catId") int catId);

	//
	@Query(value = "select i.id,i.item_id,i.item_grp1,i.item_grp2,i.item_grp3,i.item_rate1,i.item_rate2,"
			+ "i.item_rate3,i.item_mrp1,i.item_mrp2,i.item_mrp3,i.item_image,i.item_tax1,i.item_tax2,"
			+ "i.item_tax3,i.item_is_used,i.item_sort_id,i.grn_two,i.del_status,i.min_qty,i.item_shelf_life "
			+ " ,coalesce((select m_item_sup.short_name from m_item_sup where m_item_sup.item_id=i.id),0) "
			+ "as item_name from m_item i  where i.del_status=:delStatus order by i.item_grp2 asc ,i.item_sort_id asc ", nativeQuery = true)
	public List<Item> findByDelStatusOrderByItemGrp2AscItemSortIdAsc(@Param("delStatus") int i);

	@Query(value = "select * from m_item where m_item.id IN (Select m_item_sup.item_id from m_item_sup where m_item_sup.is_allow_bday=:isAllowBday) AND m_item.del_status=:delStatus", nativeQuery = true)
	public List<Item> findByIsAllowBirthayAndDelStatus(@Param("isAllowBday") int isAllowBday,
			@Param("delStatus") int delStatus);

	@Query(value = "select m_item.* from m_item where m_item.del_status=0 And m_item.item_grp1=:itemGrp1 And m_item.id not in(select m_item_sup.item_id from m_item_sup where m_item_sup.del_status=0) order by m_item.item_name ", nativeQuery = true)
	public List<Item> findByItemGrp1(@Param("itemGrp1") String itemGrp1);

	@Query(value = "select * from m_item where m_item.id IN (:itemList) Order By item_grp2", nativeQuery = true)
	public List<Item> findAllItems(@Param("itemList") List<Integer> itemList);

	/*
	 * public List<Item>
	 * findByItemGrp1InAndDelStatusOrderByItemGrp2AscItemSortIdAsc(List<String>
	 * catIdList, int i);
	 */
	public List<Item> findByItemGrp2AndDelStatusOrderByItemGrp2AscItemNameAsc(String subCatId, int delStatus);

	public List<Item> findByIdIn(List<String> id);

	@Transactional
	@Modifying
	@Query("UPDATE Item i SET i.delStatus=1  WHERE i.id IN (:idList)")
	public int deleteItems(@Param("idList") List<Integer> id);

	@Transactional
	@Modifying
	@Query("UPDATE Item i SET i.itemIsUsed=4  WHERE i.id IN (:idList)")
	public int inactivateItems(@Param("idList") List<Integer> id);

	@Transactional
	@Modifying
	@Query("UPDATE Item i SET itemTax1=:itemTax1,itemTax2=:itemTax2,itemTax3=:itemTax3  WHERE i.id=:id")
	public int updateItemHsnAndPerInItem(@Param("id") Integer id, @Param("itemTax1") double itemTax1,
			@Param("itemTax2") double itemTax2, @Param("itemTax3") double itemTax3);

	public List<Item> findByDelStatusOrderByItemGrp1AscItemGrp2AscItemNameAsc(int i);

	public List<Item> findByItemGrp1InAndDelStatusOrderByItemGrp2AscItemNameAsc(List<String> catIdList, int i);

	public List<Item> findByDelStatusAndIdIn(int i, List<Integer> itemids);

	@Query(value = "select item_mrp2 from m_item where del_status=:delStatus group by item_mrp2", nativeQuery = true)
	public List<Double> itemListGroupByStationNo(@Param("delStatus") int delStatus);

//	@Query(value="select * from m_item where m_item.item_grp2 IN (:catIdList) and m_item.del_status=:i",nativeQuery=true)
	public List<Item> findByItemGrp2InAndDelStatusOrderByItemGrp2AscItemNameAsc(List<String> catIdList, int delStatus);

	@Query(value = "\n"
			+ "select i.id,i.item_id,i.item_name,i.item_grp1,i.item_grp2,i.item_grp3,i.item_rate1,i.item_rate2,i.item_rate3,i.item_mrp1,i.item_mrp2,i.item_mrp3,i.item_image,i.item_tax1,i.item_tax2,i.item_tax3,i.item_is_used,i.item_sort_id,i.grn_two,i.del_status,i.min_qty,i.item_shelf_life from m_item i where  i.id IN (:itemList) AND i.del_status=0", nativeQuery = true)
	public List<Item> findItemsNameByItemId(@Param("itemList") List<Integer> itemList);

	//03-03-2020 Sachin
	@Query(value="select * from m_item,m_cat_sub where  m_item.del_status=0 and m_item.item_is_used!=4 "
			+ "and m_cat_sub.sub_cat_id=m_item.item_grp2  ORDER BY  m_cat_sub.prefix,m_item.item_name ASC",nativeQuery=true)
	public List<Item> getItemForProdSortBySubcatPrefix();

	
	@Transactional
	@Modifying
	@Query("UPDATE Item i SET i.itemGrp3=:lineId  WHERE i.id IN (:idList)")
	public int updateItemLineId(@Param("idList") List<Integer> id,@Param("lineId") String lineId);

	@Query(value = "select\n" + 
			"a.*,\n" + 
			"coalesce(b.item_id,0) as item_id\n" + 
			"from ( select\n" + 
			"       id, \n" + 
			"  item_name ,\n" + 
			"  item_grp1 ,\n" + 
			"  item_grp2 ,\n" + 
			"  item_grp3, \n" + 
			"  item_rate1,\n" + 
			"  item_rate2 , \n" + 
			"  item_rate3,\n" + 
			"  item_mrp1 ,\n" + 
			"  item_mrp2 ,\n" + 
			"  item_mrp3 ,\n" + 
			"  item_image , \n" + 
			"  item_tax1 ,\n" + 
			"  item_tax2 ,\n" + 
			"  item_tax3 ,\n" + 
			"  item_is_used ,\n" + 
			"  item_sort_id , \n" + 
			"  grn_two ,\n" + 
			"  del_status,\n" + 
			"  min_qty , \n" + 
			"  item_shelf_life\n" + 
			"    from\n" + 
			"        m_item \n" + 
			"    where\n" + 
			"        m_item.del_status=0 \n" + 
			"        And m_item.item_grp3=:itemGrp3" + 
			"        and m_item.id IN (\n" + 
			"            select\n" + 
			"                d.item_id \n" + 
			"            from\n" + 
			"                t_production_plan_detail d \n" + 
			"            where\n" + 
			"                d.production_header_id=:prodHeaderId\n" + 
			"            group by\n" + 
			"                d.item_id\n" + 
			"        ) \n" + 
			"    order by\n" + 
			"        m_item.item_name \n" + 
			") a LEFT JOIN\n" + 
			"(\n" + 
			"select id,1 as item_id from m_item where FIND_IN_SET(id,(select GROUP_CONCAT(ex_varchar2) from t_req_bom where production_id=:prodHeaderId  and is_manual=0  and from_dept_id=:fromDept and to_dept_id=:toDept and ex_int2=1))  and m_item.del_status=0\n" + 
			") b ON a.id=b.id", nativeQuery = true)
	public List<Item> findByItemGrp3(@Param("itemGrp3") String itemGrp3,@Param("prodHeaderId") int prodHeaderId,@Param("fromDept")int fromDept,@Param("toDept") int toDept);

	@Query(value = "select m_item.* from m_item where m_item.del_status=0 and m_item.item_grp1=(select cat_id from t_production_plan_header where production_header_id=:prodHeaderId)  ", nativeQuery = true)
	public List<Item> getItemsByProductionIdCatId(@Param("prodHeaderId")int prodHeaderId);
	
	
	
	@Query(value = "select m_item.* from m_item where m_item.del_status=0 and m_item.id In(select m_item_detail.item_id from m_item_detail where del_status=0 and m_item_detail.rm_id=:rmId and rm_type=:rmType)",nativeQuery=true)
	public List<Item> getItemsByRmIdAndRmType(@Param("rmId")int rmId,@Param("rmType") int rmType);

}
