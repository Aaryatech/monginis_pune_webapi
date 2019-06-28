package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.SpecialCake;

public interface SpecialCakeRepository extends JpaRepository<SpecialCake, Integer>{

	public SpecialCake save(SpecialCake specialcake);
	@Query(value="select * from m_sp_cake where m_sp_cake.sp_id=:spId",nativeQuery=true)
	public SpecialCake findOne(@Param("spId")int spId);
	@Query(value="select * from m_sp_cake where m_sp_cake.del_status=:delStatus",nativeQuery=true)
	public List<SpecialCake> findByDelStatus(@Param("delStatus")int i);
	@Query(value="select * from m_sp_cake where m_sp_cake.del_status=:delStatus order by sp_name Asc",nativeQuery=true)
	public List<SpecialCake> findByDelStatusOrderBySpNameAsc(@Param("delStatus")int i);
	
	@Query(value="select * from m_sp_cake where m_sp_cake.sp_id IN(:spId)",nativeQuery=true)
	public List<SpecialCake> findBySpIdIn(@Param("spId")List<Integer> spId);
	
	@Query(value="select * from m_sp_cake where m_sp_cake.del_status=:delStatus and m_sp_cake.sp_id IN(:itemids)",nativeQuery=true)
	public List<SpecialCake> findByDelStatusAndSpIdIn(@Param("delStatus")int i,@Param("itemids") List<Integer> itemids);
	
	@Query(value="select * from m_sp_cake where m_sp_cake.del_status=:delStatus order by sp_code asc",nativeQuery=true)
	public List<SpecialCake> findByDelStatusOrderBySpCodeAsc(@Param("delStatus")int i);
	
	
	
	
	

}
