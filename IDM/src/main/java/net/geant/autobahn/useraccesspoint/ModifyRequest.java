package net.geant.autobahn.useraccesspoint;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents a request to modify an already existing reservation. The
 * modification possibilities are to change the start and end time of the
 * reservation.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ModifyRequest", namespace="useraccesspoint.autobahn.geant.net", propOrder={
		"resId", "startTime", "endTime"
})
public class ModifyRequest {
	private String resId;
	private Calendar startTime;
	private Calendar endTime;

	/**
	 * The ID of the reservation to be modified.
	 * 
	 * @return
	 */
	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	/**
	 * The new starting time of the reservation.
	 * 
	 * @return
	 */
	public Calendar getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	/**
	 * The new ending time of the reservation.
	 * 
	 * @return
	 */
	public Calendar getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}
}
