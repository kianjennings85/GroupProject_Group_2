package com.project.entities;

import java.io.Serializable;

import javax.persistence.*;
/*
@NamedQueries(
{@NamedQuery
	(name = "findEventCauseByIMSI", 
	query = "select b.eventId, b.causeCode from BaseData b where b.imsi = :IMSI "),
*/
@NamedQueries(
{@NamedQuery
	(name = "findEventCauseByIMSI", 
	query = "select b.eventId, b.causeCode from BaseData a, EventCause b where a.imsi = :IMSI and b.id = a.eventCauseFK.id"),
@NamedQuery
	(name = "findEventCause", 
	query = "select b from BaseData b ")})


@Entity
@Table( name = "Event_Cause")
public class EventCause implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	// add attributes for all the remaining properties
	@Column(name="cause_Code")
	private Integer causeCode;

	@Column(name="event_Id") 
	private Integer eventId;

	@Column(name="description")
	private String description;

	public EventCause() {

	}

	public EventCause(Integer causeCode, Integer eventId, String description) {
		super();
		this.causeCode = causeCode;
		this.eventId = eventId;
		this.description = description;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCauseCode() {
		return causeCode;
	}

	public void setCauseCode(Integer causeCode) {
		this.causeCode = causeCode;
	}

	public Integer getEventId() {
		return eventId;
	}
	
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}

