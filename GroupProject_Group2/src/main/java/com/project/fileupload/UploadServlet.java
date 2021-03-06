package com.project.fileupload;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.project.dao.BaseDataDAO;
import com.project.dao.ErrorBaseDataDAO;
import com.project.dao.FileDAO;
import com.project.dao.LookUpDataDAO;
import com.project.reader.ReadBase;
import com.project.reader.ReadLookup;
import com.project.reader.excel.ExcelBaseDataRead;
import com.project.reader.excel.ExcelLookupDataRead;


//import resources.ExcellLoader;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2,	// 2MB 
				 maxFileSize=1024*1024*10,		// 10MB
				 maxRequestSize=1024*1024*50)	// 50MB
public class UploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of the directory where uploaded files will be saved, relative to
	 * the web application directory.
	 */
	private static final String SAVE_DIR = "uploadFiles";
	
	@EJB
	private BaseDataDAO dao;
	@EJB
	private ErrorBaseDataDAO errorDao;
	@EJB
	private LookUpDataDAO lookupDao;
	@EJB
	private FileDAO fileDao;
	
	ReadLookup lookupDataReader = new ExcelLookupDataRead();
	
	ReadBase baseDataReader = new ExcelBaseDataRead();
	
	/**
	 * handles file upload
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// gets absolute path of the web application
		String appPath = request.getServletContext().getRealPath("");
		// constructs path of the directory to save uploaded file
		String savePath = appPath + File.separator + SAVE_DIR;
		
		// creates the save directory if it does not exists
		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}
		
		String finalFilePath = "";
		String finalFileName = "";
		String fileExtension = "";
		Part part = request.getPart("file");
		String fileName = extractFileName(part);
		fileExtension = getFileExtension(fileName);
		if(fileExtension.equals(".xls")){
			long timeInMili = System.currentTimeMillis();
			Date t = new Date(timeInMili);
			timeInMili = timeInMili >> 4;
			finalFileName = t + "_" + timeInMili + fileExtension;
			finalFilePath = savePath + File.separator + finalFileName;
			part.write(finalFilePath);
			fileDao.addUploadedFilePath(finalFileName, finalFilePath, false);
		}
		
		String resp = "Upload Successful";
		response.sendRedirect("/GroupProject_Group2/upload.html#"+resp);
	}
	
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String finalFilePath = request.getParameter("fileSelection");
		
		int numOfInvalidRows = parse(finalFilePath);
		
		String resp = "Transfer to database completed successfully!"
				+ "<br>There were " + numOfInvalidRows + " invalid rows in the base data";
		response.sendRedirect("/GroupProject_Group2/upload.html#"+resp);
	}
	
	private int parse(String finalFilePath) throws IOException{
		lookupDataReader.setInputFile(finalFilePath);
		lookupDataReader.setLookUpDao(lookupDao);
		lookupDataReader.read();
		
		baseDataReader.setSheetNumber(0);
		baseDataReader.setInputFile(finalFilePath);
		baseDataReader.setBaseDataDao(dao);
		baseDataReader.setErrorBaseDataDao(errorDao);
		baseDataReader.read();
		return baseDataReader.getInvalidRowCount();
	}

	/**
	 * Extracts file name from HTTP header content-disposition
	 */
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length()-1);
			}
		}
		return "";
	}
	
	static String getFileExtension(String fileName){
		int location = 0;
		for(int i=fileName.length()-1; i>0; i--){
			if(fileName.charAt(i)=='.'){
				location = i;
				break;
			}
		}
		return fileName.substring(location);
	}

	public void setLookupDataReader(ReadLookup lookupDataReader) {
		this.lookupDataReader = lookupDataReader;
	}
	public void setBaseDataReader(ReadBase baseDataReader) {
		this.baseDataReader = baseDataReader;
	}
}
