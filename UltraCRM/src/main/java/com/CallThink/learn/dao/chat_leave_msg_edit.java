
package com.CallThink.learn.dao;
import java.util.HashMap;
import org.springframework.ui.Model;
import com.CallThink.base.pmClass.fun_main;
import com.CallThink.base.pmClass.pmSys;
import com.CallThink.base.pmCode.UltraCRM_Page;

import com.ToneThink.DataTable.DataTable;
import com.ToneThink.DateTime.DateTime;
import com.ToneThink.ctsTools.dbHelper.my_odbc; //1
import com.ToneThink.ctsTools.myUtility.pmMap;
import com.ToneThink.ctsTools.myUtility.Functions;
import com.ToneThink.ctsTools.WebUI.Fld_attr;
import com.ToneThink.ctsTools.WebUI.my_Field; //2
import com.ToneThink.ctsTools.WebUI.my_ToolStrip; 

    public class chat_leave_msg_edit extends UltraCRM_Page
    {
        private String pKey = "2";
        private int pType = 0;
        private String m_TableName = "WMC_CHAT_LEAVE_MSG";
        my_Field myFld = new my_Field(2);
        my_ToolStrip myToolBar = new my_ToolStrip();
        DataTable myDt = new DataTable();
        public void Page_Load(Object sender, Model model)
        {
	   //合并 
	    if (false)
            {
                HashMap htQuery;
                pmMap res = fun_main.QuerySplit(Request); htQuery = res.htRet;
                int rc = res.nRet;
                if (rc > 0)
                {
                    pKey = Functions.ht_Get_strValue("key", htQuery);
                    pType = Functions.atoi(Functions.ht_Get_strValue("ptype", htQuery));
                }
                Save_vs("pKey", pKey);
                Save_vs("ptype", pType);
          	  
            }
            else
            {
                pKey = Load_vs("pKey");
                pType = Functions.atoi(Load_vs("ptype"));

            }
            myFld = new my_Field(1);

            InitToolbar();
            Fillin_field();
            Fillin_text();
            if (myFld.get_item_value("FLAG") == "3")
            {
                myToolBar.set_readonly("Save",true );
                myToolBar.set_readonly("Deal", true);
            }
        myToolBar.render(model);
myFld.render(model);
}

        private void InitToolbar()
        {
            myToolBar.fill_fld("返回", "Return");
            myToolBar.fill_fld("Separator", "Separator1", 0, 3);
            myToolBar.fill_fld("保存", "Save");
            myToolBar.fill_fld("处理完成", "Deal");
            myToolBar.fill_toolStrip("plCommand");
            myToolBar.btnItemClick = this;// new btnClickEventHandler(myToolBar_btnItemClick);
        }

        private void Fillin_field()
        {
            myFld.SetWidth(400);
            myFld.SetMaxLabelLenth(120);
            myFld.SetLabelAlign("Right");
            myFld.fill_fld("留言编号", "AUTOID", 0, 0, false);
            if (pType == 0)
            {
                myFld.fill_fld("客户姓名", "NAME", 40, 0, false);
                myFld.fill_fld("客户电话", "TELNUM", 40, 0, false);
                myFld.fill_fld("EMAIL地址", "EMAIL", 40, 0, false);
            }
            else
                myFld.fill_fld("访客ID", "GUEST_ID", 40, 0, false);
            myFld.fill_fld("留言目标", "GHID", 40, 0, false);           
            myFld.fill_fld("留言的时间", "SDATE", 40, 0, false);
            myFld.fill_fld("状态", "FLAG", 40, 1, false);
            myFld.set_list("FLAG", "未查看,已通知,已查看,已处理");
            myFld.fill_fld("留言的内容", "MESSAGE",200, 0, false);
            myFld.fill_fld("处理人", "GHID_DEAL", 40, 0, false);
            myFld.fill_fld("更新时间", "SDATE_UPDATE", 40, 0, false);
            myFld.fill_fld("处理结果", "INFO",100);
            myFld.fill_Panel("gbEdit");
        }

        public void myToolBar_btnItemClick(Object sender, String name, String parms, int nparms)
        {
            String strInfo = myFld.get_item_value("INFO");
            if (name.equals("Save") || name.equals("Deal"))
            {
                HashMap htTemp = new HashMap();
                Functions.ht_SaveEx("INFO", strInfo,htTemp);
                Functions.ht_SaveEx("GHID_DEAL", pmAgent.uid,htTemp);
                Functions.ht_SaveEx("SDATE_UPDATE", DateTime.Now().ToString("yyyy-MM-dd HH:mm:ss"),htTemp);
                if (name.equals("Deal"))
                {
                    myFld.set_item_attr("FLAG",Fld_attr.Fld_index,"3");
                    Functions.ht_SaveEx("FLAG", "3",htTemp);
                }
                my_odbc pTable = new my_odbc(pmSys.conn_crm);
                int rc = pTable.my_odbc_update(m_TableName, htTemp, "AUTOID='" + pKey + "'");
                pTable.my_odbc_disconnect();
                if (rc > 0)
                {
                    myFld.set_item_value("GHID_DEAL", pmAgent.uid);
                    myFld.set_item_value("SDATE_UPDATE", DateTime.Now().ToString("yyyy-MM-dd HH:mm:ss"));
                    if (name.equals("Deal"))
                    {
                        myToolBar.set_readonly("Save", true);
                        myToolBar.set_readonly("Deal", true);
                    }
                    Functions.MsgBox("操作成功！");

                }
                else
                    Functions.MsgBox("操作失败！");
            }
            else if ((name.equals("Return"))) 
            {
                String strReturn_url = LastPageUrl_additem("history", "1");
                Functions.Redirect(strReturn_url);
            }

        }

        private void Fillin_text()
        {           
            myFld.Load(m_TableName, "AUTOID='" + pKey + "'", pmSys.conn_crm); //？？？
        } 
    }

