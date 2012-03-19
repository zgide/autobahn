package net.geant.autobahn.autoBahnGUI.web;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.io.BufferedReader;
import java.io.IOException;

import net.geant.autobahn.aai.AccessPolicy;
import net.geant.autobahn.administration.KeyValue;
import net.geant.autobahn.administration.ReservationType;
import net.geant.autobahn.administration.StatisticsType;
import net.geant.autobahn.autoBahnGUI.manager.Manager;
import net.geant.autobahn.autoBahnGUI.manager.ReservationHelper;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.Line;
import net.geant.autobahn.autoBahnGUI.model.googlemaps.Topology;
import net.geant.autobahn.autoBahnGUI.model.AccessPolicyFormModel;
import net.geant.autobahn.autoBahnGUI.model.IntraCalendarFormModel;
import net.geant.autobahn.autoBahnGUI.model.IntrasFormModel;
import net.geant.autobahn.autoBahnGUI.model.ServicesFormModel;
import net.geant.autobahn.autoBahnGUI.model.SettingsFormModel;
import net.geant.autobahn.autoBahnGUI.model.StatisticsFormModel;
import net.geant.autobahn.autoBahnGUI.topology.TopologyFinder;
import net.geant.autobahn.intradomain.IntradomainReservation;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONSerializer;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * Main  controller for Autobahn client portal 
 *
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 */

@Controller
public class AutobahnController {

    /**
     * Identifies the GUI manager module
     */
    @Autowired
    Manager  manager;

    /**
     * Identities topology finder module
     */
    @Autowired
    TopologyFinder topologyFinder;

    /**
     * Logs information
     */
    protected final Log logger = LogFactory.getLog(getClass());
    
    List<ReservationType> actualInterReservationList = new ArrayList<ReservationType>();
    
    List<IntradomainReservation> actualIntraDomainReservationList = new ArrayList<IntradomainReservation>();

    public AutobahnController(){
        logger.info("Creating controller");
    }

    @RequestMapping("/notfound.htm")
    public void notFound (){

    }

    /**
     * Custom handler for home.
     *
     * @param model model for response params
     */
    @RequestMapping("/home.htm")
    public void homeHandler(){
        Logger.getLogger("autoBAHN controler").info("In homeHandler");
    }

    /**
     * Custom handler for login.
     *
     * @param model model for response params
     */
    @RequestMapping("/login.htm")
    public void homeLogin() {
        Logger.getLogger("autoBAHN controler").info("In login");
    }

    /**
     * Custom handler for login error.
     *
     * @param model model for response params
     */
    @RequestMapping("/login_error.htm")
    public void homeLoginError(Map<String, Object> model) {
        Logger.getLogger("autoBAHN controler").info("In login error");
        model.put("login_error", 1);
    }

    @RequestMapping("/secure/noIDMRegistered.htm")
    public void noIdms(){
        Logger.getLogger("autoBAHN controler").info("In noIdms");
    }

    /**
     * Custom handler for map view.
     * @param request current HTTP request
     * @param response current HTTP response
     * @return a ModelAndView to render the response
     */
    @RequestMapping("/secure/services-map.htm")
    public void mapHandler (@RequestParam String  service,@RequestParam String  domain, Map<String, Object> model){

    	logger.info("Requesting map with params");
        String[] linkStatusColor = {Line.DEFAULT_COLOR_ACTIVE,Line.DEFAULT_COLOR_DEACTIVE};
        String[] linkStatusName = {"Up","Down"};
        model.put("linkColors",linkStatusColor);
        model.put("linkStates",linkStatusName);
        Map<String,String> services = manager.getServicesForAllInterDomainManagers();
        model.put ("services", services);
        if (service!=null && service.length()>0){
            model.put("reservationLinkColors",Line.reservationsStates);
            model.put("reservationStates",manager.getReservationStates());
        }
    }

    @RequestMapping("/secure/services-list.htm")
    public void mapServicesListHandler ( Map<String, Object> model){
        Map<String, String> services = manager.getServicesForAllInterDomainManagers();
        model.put ("services", services);
    }

    /*public ModelAndView handleMap(HttpServletRequest request, HttpServletResponse response) throws ServletException {
         ModelAndView modelAndView = new ModelAndView(reservationsMapView);
         String service=(String)request.getParameter("service");
         String[] linkStatusColor = {Line.DEFAULT_COLOR_ACTIVE,Line.DEFAULT_COLOR_DEACTIVE};
         String[] linkStatusName = {"Up","Down"};
         modelAndView.addObject("linkColors",linkStatusColor);
         modelAndView.addObject("linkStates",linkStatusName);
         List<Service> services = manager.getServicesForAllInterDomainManagers();
         modelAndView.addObject ("services", services);
         if (service!=null){
             modelAndView.addObject("reservationLinkColors",Line.reservationsStates);
             modelAndView.addObject("reservationStates",manager.getReservationStates());
         }
         return modelAndView;
     }*/
    /**
     * Custom handler for getting map topology in XML format
     * @param request current HTTP request
     * @param response current HTTP response
     * @return a ModelAndView to render the response
     */
    /*@RequestMapping("/secure/services-map.htm")
     public void mapHandlerXML (@RequestParam String domain, @RequestParam String  service, Map<String, Object> model){
         Topology topology=null;
         if (service !=null)
             topology=topologyFinder.getGoogleTopology(domain,service);
         else
             topology=topologyFinder.getGoogleTopology();
         model.put("topology", topology);
     }*/

    @RequestMapping("/secure/topology.xml")
    public void handleTopologyXML(@RequestParam String service,@RequestParam String domain, Map<String, Object> model){
        logger.debug("handle topology xml");
        Topology topology=null;
        if (service !=null && service.length()>0){
            topology=topologyFinder.getGoogleTopology(domain,service);}
        else
            topology=topologyFinder.getGoogleTopology();
        model.put("topology", topology);
    }

    @RequestMapping("/secure/restartAll.htm")
    public void handleTopologyChange(@RequestParam String domain) {
        logger.info("All IDMs will be restarted due to topology change at " + domain);
        manager.handleTopologyChange(domain, true);
    }

    @RequestMapping("/secure/servicesforidm.htm")
    public void handleServicesForIdm(@RequestParam String currentIdm,Map<String, Object> model){
        ServicesFormModel services = null;
        String[] reservationStates = null;
        String[] reservationDescriptions = null;

        logger.debug("getting services for idm, currentIdm:" + currentIdm);
        if (currentIdm != null) {
            services = manager.getSubmitedServicesInIDM(currentIdm);
            reservationStates = manager.getReservationStates();
            reservationDescriptions = manager.getReservationDescriptions();
        }
        //logger.debug("services:"+services);
        //logger.debug("reservationStates:"+reservationStates);
        //logger.debug("services.getServices():"+services.getServices());
        model.put("services", services);
        model.put("reservationStates", reservationStates);
        model.put("reservationDescriptions", reservationDescriptions);
    }

    @RequestMapping("/secure/settings.htm")
    public void handleSettingsChange(@RequestParam String currentIdm, Map<String, Object> model){

        Logger.getLogger("autoBAHN controler").info("handle settings change");
        List<KeyValue> properties=null;
        if (currentIdm  !=null){
            SettingsFormModel fm=manager.getSettingsForInterDomainManager(currentIdm);
            properties=fm.getProperties();

        }
        model.put("settings", properties);
        //Logger.getLogger("autoBAHN controler").info("PROPERTIES!?"+properties);
    }

    //no jsp because json request returns false
    @RequestMapping("/secure/settings_save.htm")
    public void handleSettingsChange(HttpServletRequest request, HttpServletResponse response){

        try {
            Logger.getLogger("autoBAHN controler").info("handle settings set change");
            //read json request
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line + "\n");
                line = reader.readLine();
            }
            reader.close();
            String data = sb.toString();
            
            JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(data);
            LinkedHashSet propkeys=new LinkedHashSet(jsonObject.keySet());

            //Logger.getLogger("autoBAHN controler").info(data);
            //parse
            List<KeyValue> properties=new ArrayList<KeyValue>();
            String currentIDm=null;

            for (Object entry:propkeys)
            {
                String key=entry.toString();
                String value=jsonObject.getString(key);
                Logger.getLogger("autoBAHN controler").info("prop "+key+" val "+value);
                if (key.equals("currentIdm") )
                {
                    currentIDm=value;
                } else {
                    properties.add(new KeyValue(key,value));
                }

            }

/*
            data=data.substring(1,data.length()-2);

            String[] entries=data.split(",");
            for (int i=0;i<entries.length;i++)
            {
                String entry=entries[i];
                //Logger.getLogger("autoBAHN controler").info("entry is "+entry);
                String[] keyvalue=entry.split(":");
                if (keyvalue.length==2)
                {

                    if (keyvalue[0].equals("currentIdm") )
                    {
                        currentIDm=keyvalue[1];
                    } else {
                        properties.add(new KeyValue(keyvalue[0],keyvalue[1]));
                    }
                }
            }*/

            //saving
            response.setContentType("text/x-json;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            JSONObject jsonRes;
            if (currentIDm!=null)
            {
                manager.setPropertiesForInterDomainManager(currentIDm,properties);
                jsonRes = new JSONObject()
                              .element( "result", "success" );
                //response.getWriter().write("{success:true}");
                Logger.getLogger("autoBAHN controler").info("success");
            }else {
                jsonRes = new JSONObject()
                              .element( "result", "success" );
                //response.getWriter().write("{error:true}");
                Logger.getLogger("autoBAHN controler").info("error");
            }
             jsonRes.write(response.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/secure/dataTable.htm")
    public void doGet(@RequestParam String resState, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 
    	 JQueryDataTableParamModel param = DataTablesParamUtility.getParam(request); 
    	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    	 List<ReservationHelper> resHelper = new ArrayList<ReservationHelper>();
    	 String sEcho = param.sEcho;
    	     	 
    	 int iTotalRecords;
    	 int iTotalDisplayRecords;
    	 
    	 JSONArray data = new JSONArray();
    	 
    	 IntrasFormModel intras = null;
    	 List<ReservationType> inter_reservations = null;
    	 
    	 if(resState.equals("ALL")){
    		 intras = manager.getIntraReservationsForInterDomainManager(null);
    		 inter_reservations = manager.getDomainReservations(null);   		 
    	 }	 
    	 else {	 
    		 intras = manager.getIntraReservationsForIDMWithSelectedReservationState(null, resState);
    		 inter_reservations = manager.getInterReservationsForIDMWithSelectedReservationState(null, resState);
    	 }
    		 
    	 resHelper = manager.getDomainReservations(intras, inter_reservations);
    	    	
    	 iTotalRecords  = resHelper.size();
    	 List<ReservationHelper> reservations = new ArrayList<ReservationHelper>();
    	 for(ReservationHelper res  : resHelper){
    		
    		 String resId = res.getBodID();
    		 String state = res.getState();
    		 String capacity = Long.toString(res.getCapacity());
    		 String start = res.getStartTime().toString();
    		 String end = res.getEndTime().toString();
    		 String prev = res.getPrevDomain();
    		 String last = res.getNextDomain();
    		 
    		 if(resId.toLowerCase().contains(param.sSearch.toLowerCase()) ||
    				 capacity.toLowerCase().contains(param.sSearch.toLowerCase()) ||
    				 	state.toLowerCase().contains(param.sSearch.toLowerCase()) ||
    				 		start.toLowerCase().contains(param.sSearch.toLowerCase()) ||
    				 			end.toLowerCase().contains(param.sSearch.toLowerCase()) ||
    				 				prev.toLowerCase().contains(param.sSearch.toLowerCase()) ||
    				 					last.toLowerCase().contains(param.sSearch.toLowerCase())){   										
    			 reservations.add(res);
    		 }
    	 }
         
		 iTotalDisplayRecords = reservations.size();
		 final int sortColumnIndex = param.iSortColumnIndex;
		 final int sortDirection = param.sSortDirection.equals("asc") ? -1 : 1;
		 
		 Collections.sort(reservations, new Comparator<ReservationHelper>(){
			 @Override
			 public int compare(ReservationHelper res1, ReservationHelper res2) {			 
				 switch(sortColumnIndex){
				 	case 0:
				 		return res1.getBodID().compareTo(res2.getBodID()) * sortDirection;
				 	case 1:
				 		return res1.getState().compareTo(res2.getState()) * sortDirection;
				 	case 2:
				 		return Long.toString(res1.getCapacity()).compareTo(Long.toString(
				 				res2.getCapacity())) * sortDirection;
				 	case 3:
				 		return res1.getStartTime().compareTo(res2.getStartTime()) * sortDirection;
				 	case 4:
				 		return res1.getEndTime().compareTo(res2.getEndTime()) * sortDirection;
				 	case 5:
				 		return res1.getPrevDomain().compareTo(res2.getPrevDomain()) * sortDirection;
				 	case 6:
				 		return res1.getNextDomain().compareTo(res2.getNextDomain()) * sortDirection;
				 }				 
				 return 0;
			 }
		 });
		 
		 if(reservations.size() < param.iDisplayStart + param.iDisplayLength)
			 reservations = reservations.subList(param.iDisplayStart, reservations.size());
		 else
			reservations = reservations.subList(param.iDisplayStart, param.iDisplayStart + param.iDisplayLength);
		 
		 try {
			
			 JSONObject jsonResponse = new JSONObject();
			 jsonResponse.put("sEcho", sEcho);
             jsonResponse.put("iTotalRecords", iTotalRecords);
             jsonResponse.put("iTotalDisplayRecords", iTotalDisplayRecords);
             
			 for(ReservationHelper res : reservations){
				 JSONArray row = new JSONArray();
				 StringBuffer buffer = new StringBuffer();
				 int end = res.getBodID().indexOf("@");
				 String res_idm = res.getBodID().substring(0, end);
				 buffer.append("<a href=\"intra_details.htm?idm=").append(res_idm).append("&resId=").append(res.getBodID()).append("\">").append(res.getBodID()).append("</a>");	
			 	 row.add(buffer.toString());
				 row.add(res.getState());
				 row.add(Long.toString(res.getCapacity() / 1000000));
				 row.add(formatter.format( res.getStartTime().getTime()).toString());
				 row.add(formatter.format(res.getEndTime().getTime()).toString());
				 row.add(res.getPrevDomain());
				 row.add(res.getNextDomain());
				 
				 data.add(row);       
			 }
			 jsonResponse.put("aaData", data);
			 response.setContentType("text/x-json;charset=UTF-8");
			 response.getWriter().print(jsonResponse.toString());
			 
		} catch (JSONException e) {
			   e.printStackTrace();
			   response.setContentType("text/html");
			   response.getWriter().print(e.getMessage());
		}		 
    }   
    /**
     * Check if a list of intradomain reservations contains the supplied Id
     * 
     * @param reservations - the list of intradomain reservations
     * @param resId - the reservation Id
     * @return true if rt is contained in reservations
     */
    @SuppressWarnings("unused")
	private boolean IntraListContainsReservation(
            List<IntradomainReservation> reservations, String resId) {

        if (reservations == null || resId == null) {
            return false;
        }
        
        for (IntradomainReservation intrares : reservations) {
            if (intrares.getReservationId().equals(resId)) {
                return true;
            }
        }
        return false;
    }
    
    //no jsp because json request returns false
    @SuppressWarnings("rawtypes")
	@RequestMapping("/secure/logs_request.htm")
    public void handleLogsRequest(HttpServletRequest request, HttpServletResponse response){

        try {
            Logger.getLogger("autoBAHN controler").info("handle log change");
            
            StringBuilder sb = new StringBuilder();
            
            @SuppressWarnings("unchecked")
			java.util.Enumeration<String> paramEnum = request.getParameterNames();
			
            	while(paramEnum.hasMoreElements())
            	{
            		String paramName = (String)paramEnum.nextElement();
            		sb.append(paramName + "\n");
            	}
		
            	String data = sb.toString();
            	JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(data);
            	
            	@SuppressWarnings("unchecked")
            	LinkedHashSet propkeys=new LinkedHashSet(jsonObject.keySet());

            Logger.getLogger("autoBAHN controler").info(data);

            String currentIDm=null;
            String resId = null;

            for (Object entry:propkeys)
            {
                String key=entry.toString();
                String value=jsonObject.getString(key);
                Logger.getLogger("autoBAHN controler").info("prop "+key+" val "+value);
                if (key.equals("currentIdm") ) {
                    currentIDm=value;
                }
                if (key.equals("resId") ) {
                    resId=value;
                }
            }

            //saving
            response.setContentType("text/x-json;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            JSONObject jsonRes;
            if (currentIDm!=null)
            {
                String log=manager.getLogsInterDomainManager(currentIDm, true, true, resId);
                jsonRes = new JSONObject().element("result", log);
                //response.getWriter().write("{success:true}");
                Logger.getLogger("autoBAHN controler").info("success");
            }else {
                jsonRes = new JSONObject().element("result", "error");
                //response.getWriter().write("{error:true}");
                Logger.getLogger("autoBAHN controler").info("error");
            }
             jsonRes.write(response.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }

     //commenting
     Logger.getLogger("autoBAHN controler").info("Logs request");
    }

    @RequestMapping("/secure/statistics.htm")
    public void handleStatisticsChange(@RequestParam String currentIdm, Map<String, Object> model){

        Logger.getLogger("autoBAHN controler").info("handle statistics change");
        StatisticsType st = null;
        if (currentIdm != null) {
            StatisticsFormModel fm = manager.getStatisticsForInterDomainManager(currentIdm);
            st = fm.getStatistics();
        }
        model.put("statistics", st);
    }
    
    
    @RequestMapping("/secure/calendar.htm")
    public void handleCalendarChange(@RequestParam String currentIdm, Map<String, Object> model) {

        Logger.getLogger("autoBAHN controller").info("handle calendar change");
        if (currentIdm != null) {
            Logger.getLogger("autoBAHN controler").info("putting in model: "+currentIdm);
            model.put("currIdm", currentIdm);
            Logger.getLogger("autoBAHN controler").info("put in model: "+currentIdm);
            
            IntraCalendarFormModel fmc = manager.getIntraCalendarForInterDomainManager(currentIdm);
            model.put("calendar", fmc);
        }
    }
    
    @RequestMapping("/secure/dmacl.htm")
    public void handleDmaclChange(@RequestParam String currentIdm, Map<String, Object> model) {

        Logger.getLogger("autoBAHN controler").info("handle Access Policy change");
        AccessPolicy dmacp = null;
        if (currentIdm != null) {
            AccessPolicyFormModel fm = manager.getAccessPolicyForInterDomainManager(currentIdm);
            dmacp = fm.getAccessPolicy();
        }
        model.put("dmacp", dmacp);
        model.put("selectedIdm", currentIdm);
    }
    
    public void doNothing()
    {

    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public TopologyFinder getTopologyFinder() {
        return topologyFinder;
    }

    public void setTopologyFinder(TopologyFinder topologyFinder) {
        this.topologyFinder = topologyFinder;
    }

}
