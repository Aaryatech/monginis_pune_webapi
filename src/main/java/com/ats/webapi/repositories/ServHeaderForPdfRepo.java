package com.ats.webapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.ServHeaderForPdf;

public interface ServHeaderForPdfRepo extends JpaRepository<ServHeaderForPdf, Integer>{

	@Query(value = "select s.*,t.type_name,d.dealer_name,\n" + 
			"case when s.serv_type2=1 then\n" + 
			"coalesce((select veh_no from m_logis_vehical where s.veh_id=m_logis_vehical.veh_id),0)\n" + 
			"else\n" + 
			"coalesce((select machine_name from m_logis_machine where s.veh_id=m_logis_machine.machine_id),0)\n" + 
			"end as veh_name,d.city,d.gstn_no \n" + 
			"from m_logis_serv s,m_logis_machtype t,m_logis_dealer d  where s.serv_id=:servId and s.type_id=t.type_id and s.dealer_id=d.dealer_id", nativeQuery = true)
	ServHeaderForPdf getServiceByServIdForPdf(@Param("servId") int servId);

}
