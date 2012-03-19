package net.geant.autobahn.autoBahnGUI.manager;

import java.util.Calendar;

public class ReservationHelper {
	
	private String bodID;
	private String state;
	private long capacity;	
	private Calendar startTime;	
	private Calendar endTime;	
	private String prevDomain;	
	private String nextDomain;
	
	/**
	 * @return the bodID
	 */
	public String getBodID() {
		return bodID;
	}
	
	/**
	 * @return the state of reservation
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * @return the capacity
	 */
	public long getCapacity() {
		return capacity;
	}
	
	/**
	 * @return the start time of reservation
	 */
	public Calendar getStartTime() {
		return startTime;
	}
	
	/**
	 * @return the end time of reservation
	 */
	public Calendar getEndTime() {
		return endTime;
	}
	
	/**
	 * @return the previous domain name
	 */
	public String getPrevDomain() {
		return prevDomain;
	}
	
	/**
	 * @return the next domain name
	 */
	public String getNextDomain() {
		return nextDomain;
	}
	
	/**
	 * @param bodID the bodID to set
	 */
	public void setBodID(String bodID) {
		this.bodID = bodID;
	}
	
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}
	
	/**
	 * @param start time the start time to set
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * @param end time the end time to set
	 */
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * @param prevDomain the prevDomain to set
	 */
	public void setPrevDomain(String prevDomain) {
		this.prevDomain = prevDomain;
	}
	
	/**
	 * @param nextDomain the nextDomain to set
	 */
	public void setNextDomain(String nextDomain) {
		this.nextDomain = nextDomain;
	}	
	
	
	
	
}
