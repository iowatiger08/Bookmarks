package com.tigersndragons.docbookmarks.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tigersndragons.docbookmarks.exception.EntityNotFoundException;
import com.tigersndragons.docbookmarks.formactions.AcctFlowActions;
import com.tigersndragons.docbookmarks.formactions.DocShellFlowActions;
import com.tigersndragons.docbookmarks.formactions.HomeFlowActions;
import com.tigersndragons.docbookmarks.formactions.LoginFlowActions;
import com.tigersndragons.docbookmarks.model.DocBookmark;
import com.tigersndragons.docbookmarks.model.DocShell;
import com.tigersndragons.docbookmarks.model.MappedDocBookmark;
import com.tigersndragons.docbookmarks.model.Tag;
import com.tigersndragons.docbookmarks.service.DocBookmarkService;
import com.tigersndragons.docbookmarks.service.LoginService;
import com.tigersndragons.docbookmarks.service.DocShellService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


@Transactional
@Controller
@SessionAttributes("employee")
public class HomeController {

	private LoginService loginService;
	
	private DocShellService docShellService;
	private DocBookmarkService docBookmarkService;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView  showLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout, 
	            HttpServletRequest request
	            ,ModelAndView model
	            ) {
		if (model == null){
			 model = new ModelAndView();
		}
		if (error != null) {
			model.addObject("error", 
	                       getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}
	
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");			
		//if (!model.containsAttribute("loginFlowActions"))
		model.addObject("loginFlowActions", new LoginFlowActions());
		return model;//"login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView doLogin(
			@ModelAttribute("loginFlowActions") LoginFlowActions loginFlowActions, 
			BindingResult result,
			HttpSession session,
			Model model){
			UserDetails uDetail = loginService.loadUserByUsername(loginFlowActions.getUsername(), loginFlowActions.getPasscode());
			if (uDetail != null
					&& uDetail.getPassword() !=null)		{
				result.getModel().put("employee",uDetail);
				ModelAndView mv = new ModelAndView("redirect:/home");
				session.setAttribute("employee", uDetail);
				mv.addObject("employee", uDetail);
				return mv;			
			}else{
				ModelAndView mv = new ModelAndView("login");
				int count = loginFlowActions.getCount().intValue();
				count++;
				if (count >3){				
				}else{
					loginFlowActions.setCount(new Integer(count));
				}
				result.getModel().put("loginFlowActions",loginFlowActions);	
				mv.addObject("error", "Username or password not found");
				mv.addObject("count", loginFlowActions.getCount());
				return mv;
			}

	}
	
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public ModelAndView showHome(			
			@RequestParam(value = "msg", required = false) String msg, 	
			HttpSession session,
			ModelAndView model){

		UserDetails uDetail = (UserDetails) session.getAttribute("employee");

		model.addObject("employee", uDetail);
		model.addObject("msg", msg);
		model.setViewName("home");
		model.addObject("homeFlowActions", getHomeFlowActions());
		return model;
	}
	
	@RequestMapping(value="/acct", method=RequestMethod.GET)
	public ModelAndView showAcct(		
			@RequestParam(value = "msg", required = false) String msg, 	
			HttpSession session,
			ModelAndView model){

		UserDetails uDetail = (UserDetails) session.getAttribute("employee");

		model.addObject("employee", uDetail);
		model.setViewName("acct");
		if (msg!=null){
			model.addObject("msg", "Updated information successfully");
		}
		model.addObject("acctFlowActions", new AcctFlowActions());
		return model;
	}
	@RequestMapping(value="/acct", method=RequestMethod.POST)
	public ModelAndView setPassword(
			@ModelAttribute("acctFlowActions")  AcctFlowActions acctFlowActions, 
			HttpSession session,
			BindingResult result,
			ModelMap model){

		UserDetails uDetail = (UserDetails) session.getAttribute("employee");
		ModelAndView mv = new ModelAndView("redirect:/acct");
		mv.addObject("employee", uDetail);
		try {
			loginService.updateSecurityUser(uDetail, acctFlowActions.getCredentials());
			mv.addObject("msg", "Updated information successfully");
		} catch (Exception e) {
			mv.addObject("error", e);			
		}
		return mv;
	}
	@RequestMapping(value="/home", method=RequestMethod.POST)
	public ModelAndView startDocShellHome(
			@ModelAttribute("homeFlowActions") HomeFlowActions homeFlowActions,
			@RequestParam ("selectedDocShell") String selectedDocShell,
			HttpSession session,
			ModelAndView model){

		UserDetails uDetail = (UserDetails) session.getAttribute("employee");
		model.addObject("employee", uDetail);
		try {
			DocShell selected = docShellService.getDocShellById(Long.parseLong(selectedDocShell));
			model.setViewName("redirect:/docshell/"+selectedDocShell);
			if (!model.getModelMap().containsAttribute("homeFlowActions")){
				model.addObject("homeFlowActions", getHomeFlowActions());
				model.addObject("docShell", selected);
			}
		} catch (Exception e) {
			model.addObject("error", e);
		}
		return model;
	}
	@RequestMapping(value="/docshell/{id}", method=RequestMethod.GET)
	public ModelAndView showDocShell(@PathVariable("id") String docShellId, 
			@RequestParam(value = "msg", required = false) String msg, 	
			HttpSession session,
			ModelAndView model){
		UserDetails uDetail = (UserDetails) session.getAttribute("employee");
		model.addObject("employee", uDetail);
		DocShell selected = null;
		try {
			selected = docShellService.getDocShellById(Long.parseLong(docShellId));
			model.addObject("docShell", selected);	
		} catch (Exception e) {
			model.addObject("error", e);
		}	
		DocShellFlowActions flow = new DocShellFlowActions();
		flow.setDocShellId(selected.getId()+"");
		if (uDetail!=null){
			flow.setEmployee(uDetail.getUsername());
		}
		flow.setIsDone(selected.getIsDone().intValue()==1);
		flow.setMappedDocBookmarks(new ArrayList<MappedDocBookmark>(selected.getMappedDocBookmarks()));
		flow.setNotSelectedBookmarks(docBookmarkService.filterOutMappedMarks(selected.getMappedDocBookmarks()));
		flow.setTags(simulateSearchResult("", selected));
		model.addObject("msg", msg);
		model.addObject("docShellFlowActions", flow);
		model.setViewName("docshell");
		if (!model.getModelMap().containsAttribute("homeFlowActions")){
			model.addObject("homeFlowActions", getHomeFlowActions());
		}
		return model;
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView showMappedDocShell( 
			@RequestParam(value = "msg", required = false) String msg, 	
			HttpSession session,
			ModelAndView model){
		UserDetails uDetail = (UserDetails) session.getAttribute("employee");
		model.addObject("employee", uDetail);
		List<DocShell> selected = null;
		try {
			selected = docShellService.getListOfPartialDoneDocShells();
			model.addObject("NotDoneDocShells", selected);	
		} catch (Exception e) {
			model.addObject("error", e);
		}	
		try{
			List<DocBookmark> orphanedDocBookmarks = docBookmarkService.getListOfUnMappedBookmarks();
			model.addObject("orphanedDocBookmarks", orphanedDocBookmarks);
		}catch(Exception e){
			model.addObject("error", e);		
		}
		model.addObject("msg", msg);
		model.setViewName("list");

		return model;
	}
	
	@RequestMapping(value="/docshell/{id}", 
			params="isDone", 
			method=RequestMethod.POST)
	public ModelAndView updateDocShell(
			@ModelAttribute("docShellFlowActions")  DocShellFlowActions docShellFlowActions,
			@PathVariable("id") String docShellId, 
			HttpSession session,
			ModelAndView model){
		UserDetails uDetail = (UserDetails) session.getAttribute("employee");
		model.addObject("employee", uDetail);
		DocShell selected = null;
		try {
			selected = docShellService.getDocShellById(new Long(docShellId));
			if (docShellFlowActions.getSelectedMark()!=null
					&& !docShellFlowActions.getSelectedMark().equalsIgnoreCase("0")){
				String msg = addMarkToDocShell(selected, docShellFlowActions.getSelectedMark(), uDetail.getUsername());
				if (msg!=null)
					model.addObject("msg", msg);	
			}
			selected = updateIsDone(selected, docShellFlowActions.getIsDone());

		} catch (Exception e) {
			model.addObject("error", e);
			model.setViewName("redirect:/docshell/"+docShellId);
			return model;
		}
		if (selected != null 
				&& selected.getIsDone().equals(new Integer(1))){
			model.addObject("msg", "Doc Shell "+docShellId+ " marked as Done");	
			model.setViewName("redirect:/home");
		}else{
			model.setViewName("redirect:/docshell/"+docShellId);
		}

		return model;
	}
	
	private DocShell updateIsDone(DocShell ds , Boolean isDone) throws EntityNotFoundException {
		ds.setIsDone(isDone?new Integer(1):new Integer(0));
		ds.setDoneDate(isDone?new DateTime():null);
		ds = docShellService.updateDocShell(ds);
		return ds;
	}
	
	private String addMarkToDocShell(DocShell docShell, String selectedMark, String user) throws Exception {
		docShellService.addMappedBookmark(
					docShell, 
					docBookmarkService.getDocBookmarkById(new Long(selectedMark)), //was ById
					loginService.getSecurityUser(user) );

		String message= "add Mark "+selectedMark+" to DocShell "+docShell.getId()+" ";	
		return message;
	}
	
	@RequestMapping(value="/docshell/{id}/r/{markId}")
	public ModelAndView removeMappedDocMark(@PathVariable("id") String docShellId, 
			@PathVariable("markId") String markId, 
			ModelAndView model){
		
		//DocShell selected = null;
		try {
			MappedDocBookmark mdb = new MappedDocBookmark();
			DocShell ds = docShellService.getDocShellById(new Long(docShellId));
			DocBookmark dm = docBookmarkService.getDocBookmarkById(new Long (markId));
			mdb.setDocBookmark(dm);
			mdb.setDocShell(ds);
			mdb = docShellService.removeMappedMarkFromShell(mdb);//getDocShellById(Long.parseLong(docShellId));
			model.addObject("docShell", ds);	
			model.addObject("msg", "Removed Mapped Mark Successfully");
		} catch (Exception e) {
			model.addObject("error", e);
		}	
		
		model.setViewName("redirect:/docshell/"+docShellId);
		if (!model.getModelMap().containsAttribute("homeFlowActions")){
			model.addObject("homeFlowActions", getHomeFlowActions());
		}
		return model;
	}
	
	@RequestMapping(value = "/docshell/{id}/getTags", method = RequestMethod.GET)
	public @ResponseBody
	List<Tag> getTags(@PathVariable("id") String docShellId,
					  @RequestParam String tagName) {
		DocShell ds = null;
		try {
			ds = docShellService.getDocShellById(new Long(docShellId));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return simulateSearchResult(tagName, ds);
 
	}
 
	private List<Tag> simulateSearchResult(String tagName, DocShell selected) {
 
		List<Tag> result = new ArrayList<Tag>();
		List<DocBookmark > unmapped = docBookmarkService.listOfNonMappedMarks(selected.getMappedDocBookmarks());
		// iterate a list and filter by tagName
		for (DocBookmark db : unmapped) {
			if (db.getInternalName().contains(tagName)){//tag.getTagName().contains(tagName)) {
				Tag tag = new Tag(db.getId(), db.getMarkName());
				result.add(tag);
			}
		}
 
		return result;
	}	
	
	public HomeFlowActions getHomeFlowActions() {
		HomeFlowActions homeFlowActions = new HomeFlowActions ();
		homeFlowActions.setNotDoneDocShells(docShellService.getListOfNotDoneDocShells());
		homeFlowActions.setDoneDocShells(docShellService.getListOfDoneDocShells());
		return homeFlowActions;
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {
 
		ModelAndView model = new ModelAndView();
 
		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);
 
			model.addObject("username", userDetail.getUsername());
 
		}
 
		model.setViewName("403");
		return model;
 
	}
	private String getErrorMessage(HttpServletRequest request, String key){
		 
		Exception exception = 
                   (Exception) request.getSession().getAttribute(key);
 
		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username or password!";
		}else if(exception instanceof LockedException) {
			error = exception.getMessage();
		}else{
			error = "Invalid username or password!";
		}
 
		return error;
	}
	@Required
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
	@Required
	public void setDocShellService (DocShellService docShellService){
		this.docShellService = docShellService;
	}
	@Required
	public void setDocBookmarkService (DocBookmarkService docBookmarkService){
		this.docBookmarkService = docBookmarkService;
	}

}
