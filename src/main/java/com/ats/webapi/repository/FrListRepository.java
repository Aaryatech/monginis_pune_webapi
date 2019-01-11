package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.FrList;

public interface FrListRepository extends JpaRepository<FrList, Integer>{

	
	@Query(value="select @rownum \\:= @rownum + 1 AS id,f.fr_id,f.fr_name,r.abc_type from m_franchisee f,m_fr_route r, (SELECT @rownum \\:=:frIndex) b where fr_route_id in "
			+ "(select route_id from m_fr_route where del_status=0 and abc_type in (:abcTypeList)) and f.del_status=0 and f.fr_route_id=r.route_id",nativeQuery=true)
	List<FrList> findByAbcType(@Param("abcTypeList") List<Integer> abcTypeList, @Param("frIndex") int frIndex);

	@Query(value="select @rownum \\:= @rownum + 1 AS id,f.fr_id,f.fr_name,r.abc_type from m_franchisee f,m_fr_route r, (SELECT @rownum \\:=:frIndex) b where fr_route_id in "
			+ "(select route_id from m_fr_route where del_status=0 and abc_type in (:abcTypeList)) and f.del_status=0 and f.fr_route_id=r.route_id and r.route_id=:routId",nativeQuery=true)
	List<FrList> findByAbcType(@Param("abcTypeList") List<Integer> abcTypeList, @Param("frIndex") int frIndex,@Param("routId") int routId);

}
