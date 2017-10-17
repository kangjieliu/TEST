package com.CallThink.learn.service;

import java.util.HashMap;

import org.springframework.ui.Model;

import com.CallThink.base.pmClass.fun_main;
import com.CallThink.base.pmClass.pmSys;
import com.CallThink.base.pmCode.UltraCRM_Page;
import com.ToneThink.ctsTools.WebUI.my_Field;
import com.ToneThink.ctsTools.WebUI.my_ToolStrip;
import com.ToneThink.ctsTools.dbHelper.my_odbc;
import com.ToneThink.ctsTools.myUtility.Functions;
import com.ToneThink.ctsTools.myUtility.myString;
import com.ToneThink.ctsTools.myUtility.pmMap;

public class OrderEdit extends UltraCRM_Page{
	private String pTableName = "CRM_CASE2";
	private String caseIda = "2017083012341";
    my_Field myFld = new my_Field(2);
    my_ToolStrip myToolBar = new my_ToolStrip();
 
   public void Page_Load(Object sender, Model model)
    {
//        if (IsPostBack == false)//正被首次加载和访问
//        {
//            pmMap res = fun_main.QuerySplit(Request);
//            int rc=res.nRet;
//            if(rc>0)
//            {
//                 HashMap htQuery = res.htRet;      
//            }
//        }
       
      	InitToolbar();
    	Fillin_Field();
    	FillText();
    	
     	myToolBar.render(model);
      	myFld.render(model);
    }
   /**
    * 数据提交
    * @return
    */
    public String doSubmit(){ 
    	
        pmMap res = fun_main.QuerySplit(Request);
        String strCmd = Functions.ht_Get_strValue("act", res.htRet) ;
      	Fillin_Field();
      	myToolBar_btnItemClick(null,strCmd,"",0); 
      	String jnRet = m_Submit_res;  
        return jnRet;
    }

    
    private void Fillin_Field()
    {
        myFld.SetMaxLabelLenth(150);
        
        myFld.fill_fld("订单编号", "CASEID", 30, 0);
        myFld.set_readonly("CASEID");
        myFld.fill_fld("工单名称", "CASENAME", 30, 0);
        myFld.fill_fld("工单状态", "STATUS", 30, 1, true, true);
        myFld.set_list("STATUS", "0-未处理,1-处理中,2-已完成");
        myFld.fill_fld("客户ID", "USERID", 30, 0);
        myFld.set_readonly("USERID");
        myFld.fill_fld("客户满意度", "SATISFY_RESULT", 30, 1, true, true);
        myFld.set_list("SATISFY_RESULT", "满意,基本满意,不满意");
        myFld.fill_fld("来电日期时间", "SDATE", 30, 0);
        myFld.set_readonly("SDATE");
        myFld.fill_fld("联系人", "PERSON", 30, 0);
        myFld.fill_fld("联系电话", "MOBILE", 30, 0);
        myFld.fill_fld("用户咨询问题", "INFO", 30, 0);
        myFld.fill_fld("处理结果", "CLOSE_RESULT", 30, 0);
        myFld.fill_fld("邮箱", "EMAIL", 30, 0);
        
        myFld.fill_Panel("gbEdit");
    }
    
    public void FillText()
    {
    	if(IsPostBack) return;
        my_odbc pOpidk = new my_odbc(pmSys.conn_crm);
        String strSql = myString.Format("SELECT * FROM {0} WHERE CASEID='{1}'", pTableName, caseIda);// TODO 获取工单编号 查询
        pmMap res = pOpidk.my_odbc_find(strSql, true);
        int rc = res.nRet;
        HashMap htRet = res.htRet;
       if (rc == 1)//数据填充  回显
        {
            myFld.Load(htRet);
            String fld_value;
            fld_value = Functions.ht_Get_strValue("CASENAME", htRet);//工单名称
            myFld.set_item_text("CASENAME", fld_value);
            fld_value = Functions.ht_Get_strValue("PERSON", htRet);//工单联系人
            myFld.set_item_text("PERSON", fld_value);
            fld_value = Functions.ht_Get_strValue("STATUS", htRet);//工单状态
            myFld.set_item_text("STATUS", fld_value);
            fld_value = Functions.ht_Get_strValue("SATISFY_RESULT", htRet);//客户满意度
            myFld.set_item_text("SATISFY_RESULT", fld_value);
            fld_value = Functions.ht_Get_strValue("MOBILE", htRet);//联系电话
            myFld.set_item_text("MOBILE", fld_value);
            
            fld_value = Functions.ht_Get_strValue("INFO", htRet);//用户咨询问题
            myFld.set_item_text("INFO", fld_value);
            fld_value = Functions.ht_Get_strValue("CLOSE_RESULT", htRet);//处理结果
            myFld.set_item_text("CLOSE_RESULT", fld_value);
            fld_value = Functions.ht_Get_strValue("EMAIL", htRet);//邮箱
            myFld.set_item_text("EMAIL", fld_value);
            
        }
        pOpidk.my_odbc_disconnect(); //断开数据库连接

    }
    
    private void InitToolbar()
    {
    	myToolBar.fill_fld(fun_main.Term("LBL_SAVE"), "Save", 0, 20);
    	myToolBar.fill_fld("关闭", "Close", 0, 10);
    	myToolBar.fill_toolStrip("plCommand");
        myToolBar.btnItemClick =this;
    }


 	public void myToolBar_btnItemClick(Object sender, String name, String parms, int nparms) 
     {
       String strResult = "FAIL";
 	   String strData = "";
        if (name.equals("Save"))
        {
            if (myFld.get_item_text("PERSON").length()<1)
            {
            	strData = "联系人不能为空!";
            	m_Submit_res = fun_main.getResult(name, strResult, strData);
             	return; 
            }
            
            my_odbc pOpidk = new my_odbc(pmSys.conn_crm);
            HashMap htOpidk = new HashMap();
           
            int ht_SaveEx = Functions.ht_SaveEx("CASENAME", myFld.get_item_text("CASENAME"),  htOpidk);
            ht_SaveEx = Functions.ht_SaveEx("PERSON", myFld.get_item_value("PERSON"),  htOpidk);
            ht_SaveEx = Functions.ht_SaveEx("STATUS", myFld.get_item_text("STATUS"),  htOpidk);
            ht_SaveEx = Functions.ht_SaveEx("SATISFY_RESULT", myFld.get_item_text("SATISFY_RESULT"),  htOpidk);
            ht_SaveEx = Functions.ht_SaveEx("MOBILE", myFld.get_item_text("MOBILE"),  htOpidk);
            ht_SaveEx = Functions.ht_SaveEx("INFO", myFld.get_item_text("INFO"),  htOpidk);
            ht_SaveEx = Functions.ht_SaveEx("CLOSE_RESULT", myFld.get_item_text("CLOSE_RESULT"),  htOpidk);
  
            int cord = pOpidk.my_odbc_update(pTableName, htOpidk, "CASEID='" +caseIda+ "'");
            pOpidk.my_odbc_disconnect();
            
            if(cord > 0){
            	strResult = "OK";
            	strData = "提示:保存操作成功！";
            }else{
            	strResult = "OK";
            	strData = "提示:保存操作失败！";
            }
        }
    	m_Submit_res = fun_main.getResult(name, strResult, strData);
    }
}
