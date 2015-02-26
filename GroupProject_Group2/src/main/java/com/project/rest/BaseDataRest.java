package com.project.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.entities.BaseData;
import com.project.entities.EventCause;
import com.project.entities.FailureClass;
import com.project.entities.UE;
import com.project.service.BaseDataService;

@Path("/base_data")
public class BaseDataRest {
	@EJB
	private BaseDataService baseDataService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BaseData> getAllBaseData() {
		return (List) baseDataService.getAllBaseData();
	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void addAllBaseData(){		
	}
	
	@GET
	@Path("/testue")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<UE> addUEKeys() {
		return baseDataService.addUEKeys();
	}
	
	@GET
	@Path("/testfc")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<FailureClass> addFailureClassKeys() {
		return baseDataService.addFailureClassKeys();
	}
	
	@GET
	@Path("/testallue")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<UE> getUEs() {
		return baseDataService.getUEs();
	}
	
	@GET
	@Path("/testallfc")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<FailureClass> getFailureClasses() {
		return baseDataService.getFailureClasses();
	}
	
	@GET
	@Path("/eventcause")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<EventCause> getAllEventCause() {
		 Collection<BaseData> base = baseDataService.getAllBaseData();
		 List<EventCause> ec = new ArrayList<EventCause>();
		 for(BaseData bd : base){
			 ec.add(bd.getEventCauseFK());
		 }
		 
		 return ec;
	}
	
	@GET
	@Path("/eventcause/{IMSI}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<EventCause> getAllEventCause(@PathParam("IMSI") Long imsi) {
		 Collection<BaseData> base = baseDataService.getAllBaseData();
		 List<EventCause> ec = new ArrayList<EventCause>();
		 for(BaseData bd : base){
			 if(bd.getImsi().equals(imsi)){
				 ec.add(bd.getEventCauseFK());
			 }
		 }
		 return ec;
	}
	
	@GET
	@Path("/imsibetweendates/{dates}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Long> getImsiByDateRange(@PathParam("dates") String dates) throws ParseException{
		if(dates.length()==0){
			Collection<Long> emptyCollection = Collections.emptyList();
			return emptyCollection;
		}
		String s = dates.substring(0, 19);
		String e = dates.substring(19);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date start = sdf.parse(s);
		Date end = sdf.parse(e);
		return baseDataService.getImsiByDateRange(start, end);
	}

}
