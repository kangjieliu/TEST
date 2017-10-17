package com.CallThink.learn.login;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import com.CallThink.base.pmClass.*;
import com.CallThink.base.pmCode.UltraCRM_Page;
import com.CallThink.login.login_permit;
import com.ToneThink.DataTable.DataTable;
import com.ToneThink.DateTime.DateTime;
import com.ToneThink.FileUpload.FileUpload;
import com.ToneThink.ctsTools.Regex.Regex;
import com.ToneThink.ctsTools.Regex.Regex.RegexOptions;
import com.ToneThink.ctsTools.WebUI.my_Field;
import com.ToneThink.ctsTools.WebUI.my_ToolStrip;
import com.ToneThink.ctsTools.dbHelper.my_odbc;
import com.ToneThink.ctsTools.myUtility.Functions;
import com.ToneThink.ctsTools.myUtility.myString;
import com.ToneThink.ctsTools.myUtility.pmMap;
import com.alibaba.druid.sql.ast.expr.SQLSequenceExpr.Function;


public class agent  extends  UltraCRM_Page  {

    private String pTableName = "CTS_OPIDK";
    my_Field myFld = new my_Field(0);
    my_ToolStrip myToolBar = new my_ToolStrip();
 

   public void Page_Load(Object sender, Model model)
    {
        //pmAgent = fun_main.GetParm();
        if (IsPostBack == false)//正被首次加载和访问
        {
            pmMap res = fun_main.QuerySplit(Request);
            int rc=res.nRet;
            if(rc>0)
            {
                 HashMap htQuery = res.htRet;      
            }
            //Save_vs("m_OPConfig", m_OPConfig);
        }
        else
        {
            //m_OPConfig = Functions.atoi(Load_vs("m_OPConfig"));
        }
      	InitToolbar();
    	Fillin_Field();
    	FillText();
    	
    	//model.addAttribute("TestMsg", "测试数据<br/><a>侧嗯嗯</a>");
     	myToolBar.render(model);
      	myFld.render(model);
     
    }

    public String doSubmit(){
    	//pmAgent = fun_main.GetParm();
        pmMap res = fun_main.QuerySplit(Request);
        String strCmd = Functions.ht_Get_strValue("act", res.htRet) ;//request.getParameter("act");
      	Fillin_Field();
      	myToolBar_btnItemClick(null,strCmd,"",0); 
      	String jnRet = m_Submit_res;//myToolBar_btnItemClick(strCmd);
        return jnRet;
    }

    public String doUpload(HttpServletRequest request,String strName,int nType){
      	myFld_FieldLinkClicked(request, strName, null, -1,  nType);
    	String jnRet = m_Submit_res;//myToolBar_btnItemClick(strCmd);
        return jnRet;
    }
    
    private void Fillin_Field()
    {
        //myFld.setCaption("修改座席员资料...");
    	
        myFld.SetMaxLabelLenth(150);
        myFld.funName_OnClientClick("myFld_FieldLinkClicked");
        //myFld.fill_fld("#座席员基本信息：", "BASE_INFO", 0);
        myFld.fill_fld("座席员工号", "GHID", 30, 0);
        myFld.set_readonly("GHID");
        myFld.fill_fld("座席员头像", "PHOTO", 10,16); //add by gaoww 20160613 新增上传座席头像功能
        myFld.fill_fld("座席员姓名", "REAL_NAME", 30, "*");
        myFld.fill_fld("座席类型", "UTYPE", 30, 1, false, true, "1");
        myFld.set_list("UTYPE", "0-停止服务,1-座席员（可以登录CTI）,2-二线工程师,3-常在线座席,9-其他");

        myFld.fill_fld("座席员角色", "ROLES", 50, 0, false, true);
        //myFld.set_readonly("ROLES");
        //myFld.fill_fld("所属ACD组:","GROUPS",10,0);
        //myFld.set_readonly("GROUPS");
        myFld.fill_fld("所属业务组", "LEVELS", 50, 0, false, true);
        //myFld.set_readonly("LEVELS");
        myFld.fill_fld("登录名", "LOGIN_NAME", 0); //modify by gaoww 20130308 登陆名目前未使用，先暂时封上不显示            
        myFld.fill_fld("性别", "SEX", 10, 2, true, true);
        myFld.set_list("SEX", new String[] { "男,男", "女,女" });
        myFld.fill_fld("密码", "PASS", 30, 0, true, true, "", "password","密码只能使用字母或数字");
        myFld.set_MaxLenth("PASS", 10);//add by gaoww 20150911 为了和保存时密码检查规则一致，限制输入长度为10
        //myFld.fill_fld("密码:", "PASS", 8, 0);
        myFld.fill_fld("手机号码", "MOBILENO", 30, 0);
        myFld.fill_fld("联系电话", "TEL", 30, 0);
        myFld.fill_fld("Email地址", "EMAIL", 50); //modify by gaoww 20130308 email账号等信息现在未使用，所以暂时封上不显示
        myFld.fill_fld("Email帐号", "EMAIL_UID", 0);//modify by gaoww 20130308 email账号等信息现在未使用，所以暂时封上不显示
        myFld.fill_fld("Email密码", "EMAIL_PWD", 0, 0, true, false, "", "password");//modify by gaoww 20130308 email账号等信息现在未使用，所以暂时封上不显示
        myFld.fill_fld("联系地址", "ADDR", 80, 0);
        myFld.fill_fld("备注", "MEMO", 80, 0, true);

        //add by gaoww 20121226 增加个性化设置           
        myFld.fill_fld("#座席员个性化设置", "-", 0, 0, true, true);
        //myFld.fill_fld("语言选择", "LANGUAGES", 10, 2);
        //myFld.set_list("LANGUAGES", "中文(1),英文");
        myFld.fill_fld("来电弹出类型", "FIRST_POPUP", 30, 1, true, true);
        myFld.set_list("FIRST_POPUP", "客户资料,工单资料");
        //delete by gaoww 20140919 来电弹屏改为只弹联系人， 所以去掉客户类型选择
        myFld.fill_fld("工单类型", "DEFAULT_CASETYPE", 30, 1, true, true);
        myFld.set_list("DEFAULT_CASETYPE", "SELECT CASETYPE AS DEFAULT_CASETYPE,CASE_NAME FROM CRM_CASE_TABLE", "DEFAULT_CASETYPE,CASE_NAME", pmSys.conn_crm);
        //myFld_config.fill_fld("主题颜色", "THEME",5, 2);
        //myFld_config.set_list("THEME", "默认主题(1),清新淡雅,柔和友善,超酷动感,用户定义");
        myFld.fill_fld("播放提示音", "ISPLAY_MSG_MUSIC", 30, 11,true, true, "0","","(来电或有新消息时可选择播放提示音，点击…可试听)");
        myFld.set_list("ISPLAY_MSG_MUSIC", "否,提示音1,提示音2,提示音3,提示音4");
        myFld.fill_fld("是否显示队列消息", "ISSHOW_TRUNK", 15, 2);
        myFld.set_list("ISSHOW_TRUNK", "不显示(1),显示");
        myFld.fill_fld("座席订阅CTI消息", "REG_EVENT", 15, 2);
        myFld.set_list("REG_EVENT", "0=本人;1=所属ACD组;2=所有人;");
        myFld.fill_fld("首页定制", "HOMEPAGE_WIDGET", 30, "编号之间使用‘,’分隔，如：1,2,3,4");
        myFld.fill_fld("列表每页显示最大行数", "PAGE_MAXLINE", 15, 1, "0-自适应");
        myFld.set_list("PAGE_MAXLINE", "0,15,20,30,50,100");
        myFld.fill_fld("工作地点", "WORK_ADDR", 80, 0);

        String strInfo = "编号含义如下：";

        my_odbc pTable = new my_odbc(pmSys.conn_crm);
        int rc = pTable.my_odbc_find("CRM_HOMEPAGE_SUBMENU", "STATUS=1");
        String strID, strTitle;
        while (rc == 1)
        {
        	strID = pTable.my_odbc_result("ID");        //页面地址，/ut_homepage/frmMain/myCase.asc
        	strTitle = pTable.my_odbc_result("TITLE");  //页面地址，/ut_homepage/frmMain/myCase.asc
            rc = pTable.my_odbc_nextrows(1);
            strInfo += "\n" + strID + "-" + strTitle;
        }
        pTable.my_odbc_disconnect();

        myFld.set_tooltip("HOMEPAGE_WIDGET", strInfo);
        //myFld.FieldLinkClicked += new FieldLinkClickedEventHandler(myFld_FieldLinkClicked);
        
        myFld.fill_Panel("gbEdit");
        myFld.set_MaxLenth(pTableName, pmSys.conn_callthink);
        
    }
    
    @Override
    public void myFld_FieldLinkClicked(Object sender, String name, String parms, int nparms, int ntype)
    {
        if ((ntype == 16)) //上传图片   //add by gaoww 20151207 由ctstools控件实现image
        {
        	FileUpload fuUpload = new FileUpload((HttpServletRequest)sender);
            if (fuUpload.HasFile() == false) return;

            String strUrl = fun_main.Upload_imgFile(fuUpload, "headimg\\"+pmAgent.uid , true);
            if(strUrl.length()>0)
            	m_Submit_res = fun_main.getResult(name, "OK", strUrl);
            //myFld.set_item_value(name, strUrl);
        }
    }

    
    public void FillText()
    {
    	if(IsPostBack) return;
        my_odbc pOpidk = new my_odbc(pmSys.conn_callthink);
        String strSql = myString.Format("SELECT * FROM {0} WHERE GHID='{1}'", pTableName, pmAgent.uid);
        pmMap res = pOpidk.my_odbc_find(strSql, true);
        int rc = res.nRet;
        HashMap htRet = res.htRet;
       if (rc == 1)
        {
            myFld.Load(htRet);//pTableName, "GHID = '" + pmAgent.uid + "'", pmSys.conn_callthink);
            //string fld_value = Functions.ht_Get_strValue("UTYPE", htRet);
            //if (fld_value == "1")
            //    myFld.set_item_text("UTYPE", "1-可以登录CTI");
            //else if (fld_value == "2")
            //    myFld.set_item_text("UTYPE", "2-二线工程师");
            //else if (fld_value == "9")
            //    myFld.set_item_text("UTYPE", "9-其它");
            //else      // if(fld_value =="0")
            //    myFld.set_item_text("UTYPE", "0-停止服务");
            String fld_value;
            fld_value = Functions.strItem_fromBase64(Functions.ht_Get_strValue("PASS", htRet));
            myFld.set_item_text("PASS", fld_value);

            fld_value = Functions.ht_Get_strValue("ROLES", htRet);
            //从角色库里读取角色名称
            strSql = myString.Format("SELECT RNAME FROM CTS_OPIDK_ROLES WHERE ROLES={0}", fld_value);
            rc = pOpidk.my_odbc_find(strSql);
            if (rc == 1)
            {
            	fld_value = pOpidk.my_odbc_result("RNAME");
            }
            myFld.set_item_text("ROLES", fld_value);

            ////从ACD组库里读取组名称
            //string strTemp = "";
            //strSql = String.Format("SELECT GNAME FROM CTS_ACDGP_MEMBER WHERE GHID='{0}'", pmAgent.uid);
            //rc = pOpidk.my_odbc_find(strSql);
            //while (rc == 1)
            //{
            //    pOpidk.my_odbc_result("GNAME", out fld_value);
            //    strTemp += fld_value + ";";
            //    rc = pOpidk.my_odbc_nextrows(1);
            //}
            //txtGroups_ACD.Text = strTemp;

            //从业务组库里读取组名称
            String strTemp = fun_main.ATGetGroupName(pmAgent.uid, "", 1);
            myFld.set_item_text("LEVELS", strTemp);
            //fld_value = Functions.ht_Get_strValue("SEX", htRet);
            //if (fld_value == "男")
            //    myFld.set_item_value("SEX", "1");
            //else
            //    myFld.set_item_value("SEX", "2");
        }
        if (Functions.isExist_TableCol("CTS_OPIDK_INFO", "PHOTO", pmSys.conn_callthink)==true )
        {
            strSql = myString.Format("SELECT PHOTO FROM CTS_OPIDK_INFO WHERE GHID='{0}'", pmAgent.uid);
            res = pOpidk.my_odbc_find(strSql, true);
            if (res.nRet == 1)
            {
                myFld.set_item_text("PHOTO", Functions.ht_Get_strValue("PHOTO", res.htRet));
            }
        } 
 
        pOpidk.my_odbc_disconnect();

        //add by gaoww 20121226
        DataTable dtRet = fun_main.Get_Profile(pmAgent.uid);
        //myFld.set_item_value("DEFAULT_UTYPE", Functions.dtCols_strValue(dtRet, "DEFAULT_UTYPE")); //delete by gaoww 20140919
        myFld.set_item_value("DEFAULT_CASETYPE", Functions.dtCols_strValue(dtRet, "DEFAULT_CASETYPE"));
        myFld.set_item_value("FIRST_POPUP", Functions.dtCols_strValue(dtRet, "FIRST_POPUP"));
        myFld.set_item_value("ISSHOW_TRUNK", Functions.dtCols_strValue(dtRet, "ISSHOW_TRUNK"));
        myFld.set_item_value("REG_EVENT", Functions.dtCols_strValue(dtRet, "REG_EVENT"));
        myFld.set_item_value("HOMEPAGE_WIDGET", Functions.dtCols_strValue(dtRet, "HOMEPAGE_WIDGET"));
        myFld.set_item_text("PAGE_MAXLINE", Functions.dtCols_strValue(dtRet, "PAGE_MAXLINE"));
        myFld.set_item_text("ISPLAY_MSG_MUSIC", Functions.dtCols_strValue(dtRet, "ISPLAY_MSG_MUSIC")); //add by gaoww 20160607
        //if (strName == "THEME")
        //{
        //    if (strValue == "Green") myFld_config.set_item_value(strName, "2");
        //    else if (strValue == "Pink") myFld_config.set_item_value(strName, "3");
        //    else if (strValue == "Gray") myFld_config.set_item_value(strName, "4");
        //    else if (strValue == "Custom") myFld_config.set_item_value(strName, "5");
        //myFld.set_item_text("LANGUAGES", Functions.dtCols_strValue(dtRet, "LANGUAGES")); //add by peng
        myFld.set_item_text("WORK_ADDR", Functions.dtCols_strValue(dtRet, "WORK_ADDR")); //add by peng
        /**/
    }

    
    private void InitToolbar()
    {
    	myToolBar.fill_fld(fun_main.Term("LBL_SAVE"), "Save", 0, 20);
    	myToolBar.fill_fld("关闭", "Close", 0, 10);
    	myToolBar.fill_toolStrip("plCommand");
        myToolBar.btnItemClick =this;//+= new btnClickEventHandler(myToolBar_btnItemClick);
    }


	//public String myToolBar_btnItemClick(String name)
 	public void myToolBar_btnItemClick(Object sender, String name, String parms, int nparms) 
     {
       String strResult = "FAIL";
 	   String strData = "";
 
    	//pmRet<String,String> myRet = new  pmRet<String,String>(strResult,strData);
    	//myFld.setRequest(Request);
        if (name.equals("Save"))
        {
            //myFld.Save();
        	//peng 2016-11-15 以下为敏感信息，其它判断应放在前端
            if (myFld.get_item_text("REAL_NAME").length()<1)
            {
                //Functions.MsgBox("座席员姓名不能为空!");
            	strData = "座席员姓名不能为空!";
            	m_Submit_res = fun_main.getResult(name, strResult, strData);
             	return; 
            }
            String strPwd = myFld.get_item_text("PASS");
            if (strPwd.length()< 1)
            {
                //Functions.MsgBox("座席员密码不能为空!");
            	strData = "座席员密码不能为空!";
            	m_Submit_res = fun_main.getResult(name, strResult, strData);
             	return; 
            }
            else if (strPwd.length() < 4)
            {
                //Functions.MsgBox("您设定的密码过短，建议长度大于4位，字母加数字混用！");
            	strData = "您设定的密码过短，建议长度大于4位，字母加数字混用！";
            	m_Submit_res = fun_main.getResult(name, strResult, strData);
             	return; 
            }
            else if (Regex.IsMatch(strPwd, "^([a-zA-Z]|\\d){1,10}$") == false) //add by gaoww 20150821 增加判断，不能使用字母数字以外的字符，否则设定后无法登陆
            {
                //Functions.MsgBox("您设定的密码包含非法字符，建议字母加数字混用！");
            	strData = "您设定的密码包含非法字符，建议字母加数字混用！";
            	m_Submit_res = fun_main.getResult(name, strResult, strData);
             	return; 
            }
            else if (Regex.IsMatch(strPwd, "5266|1234|456|4321|abc|qwe|987|888|999", RegexOptions.IgnoreCase) == true)
            {
                //Functions.MsgBox("您设定的密码过于简单，建议及时修改！");
            	strData = "您设定的密码过于简单，建议及时修改！";
            	m_Submit_res = fun_main.getResult(name, strResult, strData);
             	return; 
             }
            
            my_odbc pOpidk = new my_odbc(pmSys.conn_callthink);
            HashMap htOpidk = new HashMap();
           
            int ht_SaveEx = Functions.ht_SaveEx("LOGIN_NAME", myFld.get_item_text("LOGIN_NAME"),  htOpidk);
            Functions.ht_SaveEx("REAL_NAME", myFld.get_item_text("REAL_NAME"),  htOpidk);
            Functions.ht_SaveEx("SEX", myFld.get_item_value("SEX"),  htOpidk);
            Functions.ht_SaveEx("EMAIL", myFld.get_item_text("EMAIL"),  htOpidk);
            Functions.ht_SaveEx("EMAIL_UID", myFld.get_item_text("EMAIL_UID"),  htOpidk);
            Functions.ht_SaveEx("EMAIL_PWD", myFld.get_item_text("EMAIL_PWD"),  htOpidk);
            ht_SaveEx = Functions.ht_SaveEx("MOBILENO", myFld.get_item_text("MOBILENO"),  htOpidk);
            Functions.ht_SaveEx("TEL", myFld.get_item_text("TEL"),  htOpidk);
            Functions.ht_SaveEx("ADDR", myFld.get_item_text("ADDR"),  htOpidk);
            ht_SaveEx = Functions.ht_SaveEx("MEMO", myFld.get_item_text("MEMO"),  htOpidk);
            Functions.ht_SaveEx("LAST_PWD_DATE", DateTime.NowString("yyyy-MM-dd HH:mm:ss"),  htOpidk);
            ht_SaveEx = Functions.ht_SaveEx("PASS", Functions.strItem_toBase64(myFld.get_item_text("PASS")),  htOpidk);
  
            pOpidk.my_odbc_update(pTableName, htOpidk, "GHID='" + pmAgent.uid + "'");

             //add by gaoww 20160613 新增座席头像功能
            htOpidk = new HashMap();
            Functions.ht_SaveEx("PHOTO", myFld.get_item_text("PHOTO"), htOpidk);
            pOpidk.my_odbc_update("CTS_OPIDK_INFO", htOpidk, "GHID='" + pmAgent.uid + "'");
            pOpidk.my_odbc_disconnect();

            //peng 2014-05-13 参数立即生效
            int First_Popup = Functions.atoi(myFld.get_item_value("FIRST_POPUP"));         //优先弹出对话框方式，0，优先弹出客户资料： 1，优先弹出工单资料
            int utype_default = Functions.atoi(myFld.get_item_value("DEFAULT_UTYPE"));      //来单弹出默认客户类型               
            int casetype_default = Functions.atoi(myFld.get_item_value("DEFAULT_CASETYPE"));   //来电弹出默认工单类型

            if (((pmAgent.First_Popup != First_Popup) || (pmAgent.utype_default != utype_default) || (pmAgent.casetype_default != casetype_default)) == true)
            {
                pmAgent.First_Popup = First_Popup;
                pmAgent.utype_default = utype_default;
                pmAgent.casetype_default = casetype_default;
                pmAgent.set_Parm();
            }
            //ADD BY GAOWW 20121226
            HashMap htTemp = new HashMap();
            htTemp.put("FIRST_POPUP", First_Popup);
            htTemp.put("DEFAULT_UTYPE", utype_default);
            htTemp.put("DEFAULT_CASETYPE", casetype_default);
            htTemp.put("ISSHOW_TRUNK", myFld.get_item_value("ISSHOW_TRUNK"));
            htTemp.put("REG_EVENT", myFld.get_item_value("REG_EVENT"));
            htTemp.put("HOMEPAGE_WIDGET", myFld.get_item_value("HOMEPAGE_WIDGET"));
            htTemp.put("ISPLAY_MSG_MUSIC", myFld.get_item_value("ISPLAY_MSG_MUSIC"));  //add by gaoww 20160607
            htTemp.put("WORK_ADDR", myFld.get_item_value("WORK_ADDR"));  //add by gaoww 20160607
            int nPage_maxline = Functions.atoi(myFld.get_item_text("PAGE_MAXLINE")); //2015-07-21
            //string strVal = myFld.get_item_value("PAGE_MAXLINE");
            //if (strVal == "自适应") strVal = "0";
            //else if (strVal.CompareTo("100") > 0) strVal = "100";  
            htTemp.put("PAGE_MAXLINE", nPage_maxline);

            fun_main.Save_Profile(pmAgent.uid, htTemp);
            //Functions.MsgBox("保存操作成功！");
            //Functions.MsgBox("提示", "保存操作成功！");
            strResult = "OK";
            strData = "提示:保存操作成功！";
            if (pmAgent.page_maxline != nPage_maxline)
            {
                if (nPage_maxline < 5)
                {
                    nPage_maxline = Functions.atoi(fun_main.getConfig_key("cfgMaxLine_perPage"));
                    if (nPage_maxline == 0)
                    {
                       nPage_maxline = new login_permit().GetPage_maxline(0);
                    }
                }
                pmAgent.page_maxline = nPage_maxline;
                pmAgent.set_Parm();
            }
        }
    	m_Submit_res = fun_main.getResult(name, strResult, strData);
    }
}
 