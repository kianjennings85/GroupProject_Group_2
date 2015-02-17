package com.project.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({ @NamedQuery(name = "BaseData.getAllBaseData", query = "select b from BaseData b"), })
@Entity
@Table(name = "base_data")
public class BaseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "date")
	private Date date;
	@Column(name = "event_id")
	private Integer eventId;
	@Column(name = "failure_class")
	private Integer failureClass;
	@Column(name = "tac")
	// UE Type
	private Integer tac;
	@Column(name = "mnc")
	// Market
	private Integer mnc;
	@Column(name = "mcc")
	// Operator
	private Integer mcc;
	@Column(name = "cell_id")
	private Integer cellId;
	@Column(name = "duration")
	private Integer duration;
	@Column(name = "cause_code")
	private Integer causeCode;
	@Column(name = "ne_version")
	private String neVersion;
	@Column(name = "imsi")
	private Long imsi;
	@Column(name = "hier3_id")
	private String hier3Id;
	@Column(name = "hier32_id")
	private String hier32Id;
	@Column(name = "hier321_id")
	private String hier321Id;

	public BaseData(Date date, Integer eventId, Integer failureClass,
			Integer tac, Integer mnc, Integer mcc, Integer cellId,
			Integer duration, Integer causeCode, String neVersion, Long imsi,
			String hier3Id, String hier32Id, String hier321Id) {
		this.date = date;
		this.eventId = eventId;
		this.failureClass = failureClass;
		this.tac = tac;
		this.mnc = mnc;
		this.mcc = mcc;
		this.cellId = cellId;
		this.duration = duration;
		this.causeCode = causeCode;
		this.neVersion = neVersion;
		this.imsi = imsi;
		this.hier3Id = hier3Id;
		this.hier32Id = hier32Id;
		this.hier321Id = hier321Id;
	}

	public BaseData() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getFailureClass() {
		return failureClass;
	}

	public void setFailureClass(Integer failureClass) {
		this.failureClass = failureClass;
	}

	public Integer getTac() {
		return tac;
	}

	public void setTac(Integer tac) {
		this.tac = tac;
	}

	public Integer getMnc() {
		return mnc;
	}

	public void setMnc(Integer mnc) {
		this.mnc = mnc;
	}

	public Integer getMcc() {
		return mcc;
	}

	public void setMcc(Integer mcc) {
		this.mcc = mcc;
	}

	public Integer getCellId() {
		return cellId;
	}

	public void setCellId(Integer cellId) {
		this.cellId = cellId;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getCauseCode() {
		return causeCode;
	}

	public void setCauseCode(Integer causeCode) {
		this.causeCode = causeCode;
	}

	public String getNeVersion() {
		return neVersion;
	}

	public void setNeVersion(String neVersion) {
		this.neVersion = neVersion;
	}

	public Long getImsi() {
		return imsi;
	}

	public void setImsi(Long imsi) {
		this.imsi = imsi;
	}

	public String getHier3Id() {
		return hier3Id;
	}

	public void setHier3Id(String hier3Id) {
		this.hier3Id = hier3Id;
	}

	public String getHier32Id() {
		return hier32Id;
	}

	public void setHier32Id(String hier32Id) {
		this.hier32Id = hier32Id;
	}

	public String getHier321Id() {
		return hier321Id;
	}

	public void setHier321Id(String hier321Id) {
		this.hier321Id = hier321Id;
	}

	@Override
	public String toString() {
		return "id: " + getId() + "\ndate: " + getDate() + "\neventId: "
				+ getEventId() + "\nfailureClass: " + getFailureClass()
				+ "\ntac: " + getTac() + "\nmnc: " + getMnc() + "\nmcc: "
				+ getMcc() + "\ncellId: " + getCellId() + "\nduration: "
				+ getDuration() + "\ncauseCode: " + getCauseCode()
				+ "\nneVersion: " + getNeVersion() + "\nimsi: " + getImsi()
				+ "\nhier3Id: " + getHier3Id() + "\nhier32Id: " + getHier32Id()
				+ "\nhier321Id: " + getHier321Id() + "\n";
	}
}