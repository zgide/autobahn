package net.geant.autobahn.autoBahnGUI.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.geant.autobahn.autoBahnGUI.manager.Manager;
import net.geant.autobahn.useraccesspoint.PortType;
import net.geant.autobahn.useraccesspoint.ReservationRequest;
import net.geant.autobahn.useraccesspoint.ServiceRequest;
import net.geant.autobahn.useraccesspoint.UserAccessPointException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;
/**
 * Controller is used for requesting services
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 * 
 */
public class RequestServiceFormController extends SimpleFormController {
	/**
	 * Identifies the manager module
	 */
	private Manager manager;	
	/**
	 * Identifies the chosen IDM name
	 */
	private String  selectedIDM;
	/**
	 * Logs information
	 */
	protected final Log logger = LogFactory.getLog(getClass());
	
	public RequestServiceFormController(){
	    setSessionForm(true);
	    setBindOnNewForm(true);
	    setCommandName("service");
	    setCommandClass(ServiceRequest.class);
	}
	/**
	 * Custom handler for GET method form
	 * @param request current HTTP request
	 * @return model map
	 */
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> data = new HashMap<String,Object> ();
		List<String> interDomainsManagers = manager.getAllInterdomainManagers();
		if (!interDomainsManagers.isEmpty())
			selectedIDM = interDomainsManagers.get(0);
		data.put("requestedIDM", selectedIDM);
		data.put("idms", interDomainsManagers);
		return data;
	}
	/**
	 * Custom handler for setting form command object
	 * @param request current HTTP request
	 * @return command object
	 */
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession();
		ServiceRequest service = (ServiceRequest)session.getAttribute("service");
		if (service == null){
			service= new ServiceRequest();
			service.setUserEmail("test.user@test.domain");
			service.setUserName("testUser");
			if (selectedIDM==null){
				List<String> interDomainsManagers = manager.getAllInterdomainManagers();
				if (!interDomainsManagers.isEmpty())
					selectedIDM = interDomainsManagers.get(0);
			}
			service.setUserHomeDomain(selectedIDM);
		}
		return service;
	}
	/**
	 * Custom handler for form submission
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @return a ModelAndView to render the response
	 */
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors){
		ModelAndView model = null;
		String action = ServletRequestUtils.getStringParameter(request,
				"action", null);
		ServiceRequest service = (ServiceRequest)command;
		if (action == null)
			try {
				return showForm(request, errors, "requestView");
			} catch (Exception e1) {
				logger.error (e1.getClass().getName()+":"+e1.getMessage());
			}
			if (action.equals("add")){
				List<PortType> domainPorts = manager.getInterDomainManagerPorts(service.getUserHomeDomain());
				if (domainPorts == null)
					try {
						errors.rejectValue("service.userHomeDomain", "service.userHomeDomain.error", null, "There is no way to contact with IDM, or client port list is empty.");
						return showForm(request, errors, "requestView");
					} catch (Exception e) {
						logger.error (e.getClass().getName()+":"+e.getMessage());
					}	
				
				List<PortType> allPorts=null;
				
				try {
                    allPorts = manager.getAllClientPorts();
                } catch (UserAccessPointException e1) {
                    // TODO Auto-generated catch block
                    logger.error (e1.getMessage());
                }

				// Add to allPorts also IDCP ports
                List<PortType> allIdcpPorts = manager.getAllIdcpPorts();
                if (allIdcpPorts != null) {
                    if (allPorts != null) {
                        allPorts.addAll(allIdcpPorts);
                    }
                    else {
                        allPorts = allIdcpPorts;
                    }
                }
                
				if (allPorts == null)
					allPorts = domainPorts;
				
                List<String> allDomains = manager.getAllDomains();
                if (allDomains==null) {
                    try {
                        errors.rejectValue("service.userHomeDomain", "service.userHomeDomain.error", null, "There is no way to contact with IDM, or domains list is empty.");
                        return showForm(request, errors, "requestView");
                    } catch (Exception e) {
                        logger.error (e.getClass().getName()+":"+e.getMessage());
                    }
                }
                
                List<String> allLinks = manager.getAllLinks();
                if (allLinks==null) {
                    try {
                        errors.rejectValue("service.userHomeDomain", "service.userHomeDomain.error", null, "There is no way to contact with IDM, or links list is empty.");
                        return showForm(request, errors, "requestView");
                    } catch (Exception e) {
                        logger.error (e.getClass().getName()+":"+e.getMessage());
                    }
                }

				model = new ModelAndView(new RedirectView("reservationForm.htm"));
				HttpSession session = request.getSession();
				session.setAttribute("idm", service.getUserHomeDomain());
				session.setAttribute("ports_all", allPorts);
                session.setAttribute("domains_all", allDomains);
                session.setAttribute("links_all", allLinks);
				session.setAttribute("ports_domain", domainPorts);
				session.setAttribute("service", service);
				return model;
			}else
			if (action.equals("remove")) {
				int id = ServletRequestUtils.getIntParameter(request, "id", 0);
				if (id < service.getReservations().size()) {
					service.getReservations().remove(id);
					try {
						return showForm(request, errors, "requestView");
					} catch (Exception e) {
						logger.error (e.getClass().getName()+":"+e.getMessage());
					}
				}
			} else if (action.equals("update")) {
				logger.info("On action update");
				try {
					return showForm(request, errors, "requestView");
				} catch (Exception e) {logger.error (e.getClass().getName()+":"+e.getMessage());}
			}else if (action.equals("save")){
						if (!manager.checkUserAccessPointConnection(service.getUserHomeDomain()))
							try {
								return showForm(request, errors, "requestView");
							} catch (Exception e) {
								logger.error (e.getClass().getName()+":"+e.getMessage());
							}
						ModelAndView successView=null;
						
						if (service.getReservations().isEmpty()){
							try {
								return showForm(request, errors, "requestView");
							} catch (Exception e) {
								logger.error (e.getClass().getName()+":"+e.getMessage());
							}
						}
						
						String serviceId=null;
							try {
								serviceId = manager.submitServiceAtInterDomainManager(service.getUserHomeDomain(), service);
								request.getSession().removeAttribute("service");
								successView = new ModelAndView(new RedirectView("reservations.htm#"+serviceId+"?domain="+service.getUserHomeDomain()));
							} catch (Exception e) {
								logger.error (e.getClass().getName()+":"+e.getMessage());
								try {
									return showForm(request, errors, "requestView");
								} catch (Exception e1) {
									logger.error (e1.getClass().getName()+":"+e1.getMessage());
								}
							}
		
							return successView;
			}else{
				try {
					return showForm(request, errors, "requestView");
				} catch (Exception e1) {
					logger.error (e1.getClass().getName()+":"+e1.getMessage());
				}
			}
		return model;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public String getSelectedIDM() {
		return selectedIDM;
	}

	public void setSelectedIDM(String selectedIDM) {
		this.selectedIDM = selectedIDM;
	}
}
