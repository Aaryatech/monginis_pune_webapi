package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.ServDetailForPdf;

public interface ServDetailForPdfRepo extends JpaRepository<ServDetailForPdf, Integer> {

	@Query(value = "select sd.*,sp.spr_name,pg.group_name from m_logis_serv_detail sd ,m_logis_sprpart sp,m_logis_group pg where sd.del_status=0  and sd.serv_id=:servId and sd.spr_id=sp.spr_id and pg.group_id=sd.group_id", nativeQuery = true)
	List<ServDetailForPdf> getServiceDetailByServIdForPdf(@Param("servId") int servId);

}
