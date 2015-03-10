package com.project.dao;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

import com.project.entities.EventCause;


public interface EventCauseDAO {
	// methods for queries
	List<Object[]> getFailuresIdsByIMSI(Long imsi);
	List<Object[]> countUniqueEventCauseByModel(String phoneModel);
	List<Object[]> getCauseCodeByIMSI(Long imsi);
	Collection getFailures();
	List<Object[]> getImsiByCauseClass(int failureClass);
	Collection getAllEventCause();
	
}
