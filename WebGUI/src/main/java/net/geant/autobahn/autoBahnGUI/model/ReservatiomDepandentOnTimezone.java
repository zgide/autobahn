package net.geant.autobahn.autoBahnGUI.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;

import net.geant.autobahn.autoBahnGUI.manager.ManagerImpl;
import net.geant.autobahn.constraints.Range;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.useraccesspoint.ReservationRequest;

public class ReservatiomDepandentOnTimezone implements Serializable {

    private static Logger logger = Logger.getLogger("ReservatiomDepandentOnTimezone");
    
	private String timezone;
	private ReservationRequest request;

	public String getTimezone() {
		return timezone;
	}
	
	public ReservationRequest getRequest() {
		return request;
	}
	
	public void setTimezone(String timezone) {
	    logger.info("Reservatiom request timezone set to " + timezone + " from " + this.timezone);
        logger.info("Reservation start/end timezones are " + getRequest().getStartTime().getTimeZone());
        
        // Cut user-friendly text from timezone String
        String[] split_tz = timezone.split("\\) ");
		timezone = split_tz[split_tz.length-1];
		this.timezone = timezone;
	}
	
	public void setRequest(ReservationRequest request) {
		this.request = request;
	}
	
	public void validateAddReservation(ValidationContext context) {
		
		MessageContext messages = context.getMessageContext();
        Calendar now = Calendar.getInstance();
		Calendar startTime = this.getRequest().getStartTime();
        Calendar endTime = this.getRequest().getEndTime();
		
        // Shift start/end times according to the user-selected timezone
        TimeZone userZone = TimeZone.getTimeZone(timezone);
        
        logger.info("startTime.getTimeInMillis(): "+startTime.getTimeInMillis());
        logger.info("userZone.getRawOffset(): "+userZone.getRawOffset());
        logger.info("startTime.getTimeZone().getRawOffset(): "+startTime.getTimeZone().getRawOffset());
        
        startTime.setTimeInMillis(startTime.getTimeInMillis() - 
                    userZone.getRawOffset() + startTime.getTimeZone().getRawOffset());
        startTime.setTimeZone(userZone);
        logger.info("start time to set "+startTime.getTime());
        
        endTime.setTimeInMillis(endTime.getTimeInMillis() - 
                userZone.getRawOffset() + endTime.getTimeZone().getRawOffset());
        endTime.setTimeZone(userZone);
        
        if (!this.getRequest().isProcessNow()) {
    		if (startTime.compareTo(now) < 0) {
    			messages.addMessage(new MessageBuilder().error().source("request.startTime").
                    code("startTime.past").build());
    		}
    		
    		if (startTime.compareTo(endTime) >= 0) { 
    			messages.addMessage(new MessageBuilder().error().source("request.startTime").
    	                code("startTime.greater").build());
        	}
        }
		
		if (endTime.compareTo(now) < 0) {
			messages.addMessage(new MessageBuilder().error().source("request.endTime").
                code("endTime.past").build());
		}
		
		//check if start port is null
		if (this.getRequest().getStartPort() == null || this.getRequest().getStartPort().getAddress() == null) {
			messages.addMessage(new MessageBuilder().error().source("request.startPort.address").
				code("portCombo.null").build());
			return;
		}
		
		//check if end port is null
		if (this.getRequest().getEndPort() == null || this.getRequest().getEndPort().getAddress() == null) {
			messages.addMessage(new MessageBuilder().error().source("request.endPort.address").
				code("portCombo.null").build());
			return;
		}
		
		//validates if user selected different start and end ports
		if (this.getRequest().getStartPort().getAddress().equals(this.getRequest().getEndPort().getAddress())) {
			messages.addMessage(new MessageBuilder().error().source("request.startPort.address").
				code("ports.equal").build());
			return;
		}
		
        //check if user can request the specified capacity
		long maxCapacity = ManagerImpl.getInstance().getUserMaxCapacity();
		if (maxCapacity >= 0) {
		    if (maxCapacity < this.getRequest().getCapacity()) {
                messages.addMessage(new MessageBuilder().error()
                        .source("request.capacity")
                        .code("capacity.exceeds").build());
		    }
		}
		
		//check if user can request the specified vlan
		RangeConstraint vlansAllow = ManagerImpl.getInstance().getUserVlans("vlan.allow");
        RangeConstraint vlansDeny = ManagerImpl.getInstance().getUserVlans("vlan.deny");
        int startVlan = this.getRequest().getStartPort().getVlan();
        int endVlan = this.getRequest().getEndPort().getVlan();
		if (vlansAllow != null) {
		    List<Range> ranges = vlansAllow.getRanges();
		    if (ranges != null) {
	            boolean startAllowed = false;
	            boolean endAllowed = false;
		        for (Range r : ranges) {
		            if (r.getMin() <= startVlan && startVlan <= r.getMax()) {
		                startAllowed = true;
		            }
                    if (r.getMin() <= endVlan && endVlan <= r.getMax()) {
                        endAllowed = true;
                    }
		        }
		        if (!startAllowed) {
                    messages.addMessage(new MessageBuilder().error()
                            .source("request.startPort.vlan")
                            .code("vlan.start.notallowed").build());
		        }
		        if (!endAllowed) {
                    messages.addMessage(new MessageBuilder().error()
                            .source("request.endPort.vlan")
                            .code("vlan.end.notallowed").build());
		        }
		    }
		} else {
		    if (vlansDeny != null) {
	            List<Range> ranges = vlansDeny.getRanges();
	            if (ranges != null) {
	                boolean startDenied = false;
	                boolean endDenied = false;
	                for (Range r : ranges) {
	                    if (r.getMin() <= startVlan && startVlan <= r.getMax()) {
	                        startDenied = true;
	                    }
	                    if (r.getMin() <= endVlan && endVlan <= r.getMax()) {
	                        endDenied = true;
	                    }
	                }
	                if (startDenied) {
	                    messages.addMessage(new MessageBuilder().error()
	                            .source("request.startPort.vlan")
	                            .code("vlan.start.denied").build());
	                }
	                if (endDenied) {
	                    messages.addMessage(new MessageBuilder().error()
	                            .source("request.endPort.vlan")
	                            .code("vlan.end.denied").build());
	                }
	            }
		    }
		}

    }

}
