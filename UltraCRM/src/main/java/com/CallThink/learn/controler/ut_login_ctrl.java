package com.CallThink.learn.controler;
//using
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.CallThink.learn.dao.chat_leave_msg_edit;
import com.CallThink.learn.dao.chat_leave_msg_list;
import com.CallThink.learn.login.agent;
import com.CallThink.learn.service.OrderList;


@Controller
public  class ut_login_ctrl {
/*
 * 注解 controller 控制器
 *   c# 访问页面 /a/aa/c.aspx
 *   java springmvc 所有的访问都是先访问控制器; 
 *   控制器:指向->呈现哪个界面
 * 
 * 在tomcat 类似iis 在启动时就会加载控制器
 * 注意事项:控制器不要冲突,否则tomcat起不来
 * */
	
	/**
	 * 修改
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "login2/agent.aspx")
	public String getAgent_edit(HttpServletRequest request, Model model) {
		
		agent myForm = new agent();
		myForm.Page_Load(this, model);
		return "login2/agent";
	}
//	@RequestMapping(value = "login2/chat_leave_msg_edit.aspx")
//	public String getAgent_edit(HttpServletRequest request, Model model) {
//		
//		chat_leave_msg_edit myForm = new chat_leave_msg_edit();
//		myForm.Page_Load(this, model);
//		return "login2/chat_leave_msg_edit";
//	}
	
	/**
	 *  列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "login2/chat_leave_msg_list.aspx")
	public String getAgent_list(HttpServletRequest request, Model model) {
		
		chat_leave_msg_list myForm = new chat_leave_msg_list();
		myForm.Page_Load(this, model);
		return "login2/chat_leave_msg_list";
		
	}
	

	@ResponseBody
	@RequestMapping(value = "login2/agent.aspx/submit", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public String getAgent_submit(HttpServletRequest request, Model model) {
		agent myForm = new agent();
		String strRet = myForm.doSubmit();
		return strRet;
	}

@ResponseBody
	@RequestMapping(value = "login2/agent.aspx/upload", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public String getAgent_upload(HttpServletRequest request, String sender, int sender_type) {
		agent myForm = new agent();
		String strRet = myForm.doUpload(request, sender, sender_type);
		return strRet;
	}

}
