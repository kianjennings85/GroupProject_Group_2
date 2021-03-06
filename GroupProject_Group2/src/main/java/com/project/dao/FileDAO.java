package com.project.dao;

import java.util.Collection;

import com.project.entities.FileInfo;

public interface FileDAO {
	
	Collection<FileInfo> getAllUploadedFilePaths();
	
	boolean addUploadedFilePath(String name, String path, boolean flush);

	void removeFileFromDatabase(String fileName);

	void removeFileFromServer(String fileName);

}
