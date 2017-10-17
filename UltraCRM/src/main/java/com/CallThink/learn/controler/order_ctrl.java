package com.CallThink.learn.controler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.CallThink.learn.service.OrderAdd;
import com.CallThink.learn.service.OrderEdit;
import com.CallThink.learn.service.OrderList;

@Controller
public class order_ctrl {
	@RequestMapping(value = "order/order_list.aspx")
	public String getOrder_list(HttpServletRequest request, Model model) {
		
		OrderList myForm = new OrderList();
		myForm.Page_Load(this, model);
		return "order/order_list";
	}
	/**
	 * 修改
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "order/order_edit.aspx" )
	public String getOrder_edit(HttpServletRequest request, Model model) {
		
		OrderEdit myForm = new OrderEdit();
		myForm.Page_Load(this, model);
		return "order/order_edit";
	}
	
	/**
	 * 增加
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "order/order_add.aspx" )
	public String getOrder_add(HttpServletRequest request, Model model) {
		
		OrderAdd myForm = new OrderAdd();
		myForm.Page_Load(this, model);
		return "order/order_add";
	}
	
	@ResponseBody
	@RequestMapping(value = "order/order_edit.aspx/submit", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public String getAgent_submit(HttpServletRequest request, Model model) {
		OrderEdit myForm = new OrderEdit();
		String strRet = myForm.doSubmit();
		return strRet;
	}

}
