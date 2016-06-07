package com.tigersndragons.docbookmarks.controller;

import com.tigersndragons.docbookmarks.formactions.AcctFlowActions;
import com.tigersndragons.docbookmarks.formactions.LoginFlowActions;
import com.tigersndragons.docbookmarks.service.LoginService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by user on 6/6/16.
 */
public class AcctController {

    private LoginService loginService;


    @RequestMapping(value="/login", method= RequestMethod.GET)
    public ModelAndView showLogin(
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
}
