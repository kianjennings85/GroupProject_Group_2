package com.project.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({ 
	@NamedQuery(name = "Ue.getCallFailuresDateRange", query = "select COUNT(b), u.tac, u.marketingName from BaseData b, UE u where u.tac = :tac and b.date Between :startDate AND :endDate and b.ueFK.id = u.id"),
	
	@NamedQuery(name = "Ue.getAllModels", query = "select u from UE u")
})

@Entity
@Table(name = "ue")
public class UE implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "tac")
	private Integer tac;

	@Column(name = "marketingName")
	private String marketingName;

	@Column(name = "manufacturer")
	private String manufacturer;

	@Column(name = "accessCapability")
	private String accessCapability;


	public UE() {
	}

	public UE(Integer tac, String marketingName, String manufacturer,
			String accessCapability) {
		this.tac = tac;
		this.marketingName = marketingName;
		this.manufacturer = manufacturer;
		this.accessCapability = accessCapability;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTac() {
		return tac;
	}

	public void setTac(Integer tac) {
		this.tac = tac;
	}

	public String getMarketingName() {
		return marketingName;
	}

	public void setMarketingName(String marketingName) {
		this.marketingName = marketingName;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getAccessCapability() {
		return accessCapability;
	}

	public void setAccessCapability(String accessCapability) {
		this.accessCapability = accessCapability;
	}

}
