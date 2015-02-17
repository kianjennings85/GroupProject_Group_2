package com.project.dao;

import java.util.Collection;

import com.project.entities.BaseData;

public interface BaseDataDAO {

	Collection<BaseData> getAllBaseData();
	
	@SuppressWarnings("rawtypes")
	void addAllBaseData(Collection baseDataList);

}