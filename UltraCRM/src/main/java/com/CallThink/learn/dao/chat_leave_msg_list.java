///########################################################################################
///
///#########################################################################################
/// 文件创建时间：2015-07-20
///   文件创建人：zhaol 
/// 文件功能描述：微信、webcall交谈记录表。
///     调用格式：
///     
///     维护记录：
///         
///#########################################################################################
package com.CallThink.learn.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.ui.Model;
import com.CallThink.base.pmClass.e_Level_base;
import com.CallThink.base.pmClass.fun_main;
import com.CallThink.base.pmClass.pmSys;
import com.CallThink.base.pmCode.UltraCRM_Page;
import com.ToneThink.DateTime.DateTime;
import com.ToneThink.ctsTools.dbHelper.my_odbc;
import com.ToneThink.ctsTools.myUtility.pmMap;
import com.ToneThink.ctsTools.myUtility.Functions;
import com.ToneThink.ctsTools.myUtility.myString;
import com.ToneThink.ctsTools.WebUI.my_ToolStrip;
import com.ToneThink.ctsTools.WebUI.my_dataGrid;
import com.ToneThink.ctsTools.WebUI.my_SearchField;

public class chat_leave_msg_list extends UltraCRM_Page {
	private String m_TableName = "WMC_CHAT_LEAVE_MSG";
	private String pOp = ""; // 保留
	private int pType = 0; // 标记，如果pType=0代表webcall；如果pType=1代表微信
	my_dataGrid mydg = new my_dataGrid(10);
	my_SearchField mySearch = new my_SearchField(3);
	my_ToolStrip myToolBar = new my_ToolStrip();

	String m_Filter_search = "";

	public void Page_Load(Object sender, Model model) {
		int nHistory = 0;
		if (IsPostBack == false) // 正被首次加载和访问
		{
			HashMap htQuery; // 创建一个哈希表，用来存放参数
			pmMap res = fun_main.QuerySplit(Request);
			htQuery = res.htRet;
			int rc = res.nRet;
			if (rc > 0) // 如果哈希表不是空的，取值。
			{
				pOp = Functions.ht_Get_strValue("cmd", htQuery);
				pType = Functions.atoi(Functions.ht_Get_strValue("ntype", htQuery));
				nHistory = Functions.atoi(Functions.ht_Get_strValue("history", htQuery));
				if (nHistory > 0) // 编辑页面返回
				{
					String strFilter_temp = Load_ss("m_Filter_search" + pType);
					if (strFilter_temp.length() > 0)
						m_Filter_search = strFilter_temp;
					mydg.nRestore_history = 1;
				}
			}
			Save_vs("pOp", pOp);
			Save_vs("pType", pType);
			Save_ss("m_Filter_search" + pType, m_Filter_search);
		} else {
			pOp = Load_vs("pOp");
			pType = Functions.atoi(Load_vs("pType"));
			m_Filter_search = Load_ss("m_Filter_search" + pType);
		}
		InitToolbar();
		Fillin_SearchField();
		Fillin_grid();

		myToolBar.render(model);
		mySearch.render(model);
		mydg.render(model);
	}

	private void Fillin_SearchField() {
	      mySearch.SetWidth(pmAgent.screen_width);
          mySearch.SetMaxLabelLenth(120);
		mySearch.funName_OnClientClick("mySearch_FieldLinkClicked");
		mySearch.fill_fld("开始日期", "SDATE", 21, 5, true, true, DateTime.Now().AddMonths(-3).ToString("yyyy-MM-dd"), "yyyy-MM-dd");
		mySearch.fill_fld("结束日期", "EDATE", 21, 5, true, false, DateTime.Now().ToString("yyyy-MM-dd"), "yyyy-MM-dd");
		mySearch.fill_fld("处理状态", "FLAG", 20, 1, true, true);
		mySearch.set_list("FLAG", "未查看,已通知,已查看,已处理");
		mySearch.fill_fld("查询内容", "INFO", 50, 0);
		mySearch.set_MaxLenth("INFO", 50);
		mySearch.set_tooltip("INFO", "支持留言内容,客户姓名,联系电话的模糊查询");

		ArrayList alButton_ex = new ArrayList();
		mySearch.fill_fld_button("查询", "QuickSearch", null, false, alButton_ex, "right");

		mySearch.fill_Panel("plSearch");
		mySearch.FieldLinkClicked = this;
	}

	public void mySearch_FieldLinkClicked(Object sender, String name, String parms, int nparms, int ntype) {
		if (name.equals("QuickSearch")) {
			String strFilter_search = GetFilter_search();
			if (strFilter_search.length() < 1) {
				Functions.MsgBox("请输入查询条件！");
				return;
			}
			m_Filter_search = strFilter_search;
			Save_ss("m_Filter_search" + pType, m_Filter_search);

			mydg.refresh("AUTOID", m_strFilter() + m_strOrder_by());
			mydg.Goto_NewPageIndex(1);
		}
	}

	private String GetFilter_search() {
		String strSdate = mySearch.get_item_value("SDATE") + " 00:00:00";
		String strEdate = mySearch.get_item_value("EDATE") + " 23:59:59";
		String strFlag = mySearch.get_item_value("FLAG");
		String strInfo = mySearch.get_item_value("INFO");
		String strFilter_search = "";
		if (strSdate.length() > 0) {
			strFilter_search = " SDATE>= '" + strSdate + "'";
		}
		if (strEdate.length() > 0) {
			if (strFilter_search.length() > 0)
				strFilter_search += " AND ";
			strFilter_search += " SDATE<= '" + strEdate + "'";
		}
		if (strFlag.length() > 0) {
			if (strFilter_search.length() > 0)
				strFilter_search += " AND ";
			strFilter_search += " FLAG= '" + strFlag + "'";
		}
		if (strInfo.length() > 0) {
			if (strFilter_search.length() > 0)
				strFilter_search += " AND ";
			strFilter_search += " (MESSAGE like '%" + strInfo + "%' OR NAME like '%" + strInfo + "%' OR TELNUM like '%" + strInfo + "%')";
		}
		return strFilter_search;
	}

	private void Fillin_grid() {
		int i = 0;
		mydg.SetConnStr(pmSys.conn_crm);

		mydg.SetTable(m_TableName);
		mydg.SetPageSize(pmAgent.page_maxline - 2);
		// mydg.AllowSorting = 1;
		mydg.fill_fld(i++, "SELECT", "SELECT", 4, 9);
		mydg.fill_fld(i++, "留言时间", "SDATE", 22, 0, "yyyy-MM-dd HH:mm:ss"); // 留言的时间
		mydg.fill_fld(i++, "留言编号", "AUTOID", 0);
		if (pType == 0) {
			mydg.fill_fld(i++, "客户姓名", "NAME", 22, 8, "CMDNAME=Link;NULLAS=[null];"); // 客户名称
			mydg.fill_fld(i++, "联系电话", "TELNUM", 20);
		} else
			mydg.fill_fld(i++, "访客ID", "GUEST_ID", 22, 8, "CMDNAME=Link;NULLAS=[null];");
		mydg.fill_fld(i++, "留言内容", "MESSAGE", -1);
		mydg.fill_fld(i++, "留言目标", "GHID", 10); // 留言的工号或组号（组号前加G）
		mydg.fill_fld(i++, "处理状态", "FLAG", 10, 1);// 状态
		mydg.set_cols_cbo_list("FLAG", "未查看,已通知,已查看,已处理");
		mydg.fill_header("dgvList", "AUTOID", m_strFilter() + m_strOrder_by());
		mydg.CellLinkClicked = this;// new
									// CellLinkClickedEventHandler(mydg_CellLinkClicked);
	}

	public void mydg_CellLinkClicked(Object sender, String text, int rows, int cols) {
		if (text.equals("Link")) {
			String autoid = mydg.get_cell(rows, "AUTOID");
			int nFlag = Functions.atoi(mydg.get_cell(rows, "FLAG"));
			if (nFlag < 2) {
				my_odbc pTable = new my_odbc(pmSys.conn_crm);
				int rc = pTable.my_odbc_update(m_TableName, "FLAG=2", "AUTOID='" + autoid + "'");
				pTable.my_odbc_disconnect();
			}
			Functions.Redirect(myString.Format("chat_leave_msg_edit.aspx?cmd=Edit&key={0}&ptype={1}", autoid, pType));
		}
	}

	private void InitToolbar() {
		myToolBar.fill_fld("显示全部", "Refresh", 0, 10);
		myToolBar.fill_fld("Separator", "Separator", 0, 3);
		myToolBar.fill_fld_confirm("删除", "Delete", "确实要删除选中的记录吗？");
		myToolBar.fill_toolStrip("plCommand");
		myToolBar.btnItemClick = this;
	}

	public void myToolBar_btnItemClick(Object sender, String name, String parms, int nparms) {
		if (name.equals("Refresh")) {
			m_Filter_search = "";
			Save_ss("m_Filter_search" + pType, m_Filter_search);
			mydg.refresh("AUTOID", m_strFilter() + m_strOrder_by());
			mydg.Goto_NewPageIndex(1);
		} else if (name.equals("Delete")) {
			List<String> alSelect = mydg.GetSelectedKey("AUTOID");
			if (alSelect.size() <= 0) {
				Functions.MsgBox("请先选中要删除的记录！");
				return;
			}

			int nCnt = 0;
			for (int index = 0; index < alSelect.size(); index++) {
				String strId = alSelect.get(index);
				my_odbc pTable = new my_odbc(pmSys.conn_crm);
				int rc = pTable.my_odbc_delete(m_TableName, "AUTOID='" + strId + "'");
				if (rc > 0)
					nCnt++;
				pTable.my_odbc_disconnect();
			}
			if (nCnt > 0) {
				mydg.refresh("AUTOID", m_strFilter() + m_strOrder_by());
				Functions.MsgBox("记录删除成功！");
			}
		}
	}

	private String m_strFilter() {
		String strFilter = m_strFilter_base();
		if (m_strFilter_priv().length() > 0) {
			if (strFilter.length() > 0)
				strFilter += " AND ";
			strFilter += m_strFilter_priv();
		}
		return strFilter;
	}

	// 基本的显示条件
	private String m_strFilter_base() {
		String strFilter = "TYPE='" + pType + "'";
		if (m_Filter_search.length() > 0)
			strFilter += " AND " + m_Filter_search;
		return strFilter;
	}

	// 只查看本组或工号是自己的，多媒体管理权限的座席看所有的
	private String m_strFilter_priv() {
		String strFilter = "";
		if (pmAgent.c_Levels.check_authority(e_Level_base.mci_adv) == true)
			strFilter = "1=1";
		else {
			strFilter = "GHID='" + pmAgent.uid + "'"; // 本人
			String[] strACDGp = pmAgent.c_groups.getACDGroups().split("[|]");
			for (int i = 0; i < strACDGp.length; i++) {
				strFilter += " OR GHID='G" + strACDGp[i] + "'";
			}
			strFilter = "(" + strFilter + ")";
		}
		return strFilter;
	}

	// 排序规则
	private String m_strOrder_by() {
		String strOrderby = " ORDER BY SDATE DESC";
		return strOrderby;
	}

}
