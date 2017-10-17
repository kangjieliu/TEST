package com.CallThink.learn.service;

import java.util.List;

import org.springframework.ui.Model;
import com.CallThink.base.pmClass.pmSys;
import com.CallThink.base.pmCode.UltraCRM_Page;
import com.ToneThink.ctsTools.WebUI.my_SearchField;
import com.ToneThink.ctsTools.WebUI.my_ToolStrip;
import com.ToneThink.ctsTools.WebUI.my_dataGrid;
import com.ToneThink.ctsTools.dbHelper.my_odbc;
import com.ToneThink.ctsTools.myUtility.Functions;

public class OrderList extends UltraCRM_Page {

	private String pTableName = "CRM_CASE2";
	my_dataGrid mydg = new my_dataGrid(10);
	my_SearchField mySearch = new my_SearchField(3);
	my_ToolStrip myToolBar = new my_ToolStrip();
	String m_Filter_search = "";

	public void Page_Load(Object sender, Model model) {

		InitToolbar();
		Fillin_grid();

		myToolBar.render(model);
		mySearch.render(model);
		mydg.render(model);
	}

	/**
	 * 查询数据库 设置页面表格数据
	 */
	private void Fillin_grid() {
		int i = 0;
		mydg.SetConnStr(pmSys.conn_crm);
		mydg.SetTable(pTableName);
		mydg.SetPageSize(pmAgent.page_maxline - 2);
		mydg.fill_fld(i++, "SELECT", "SELECT", 4, 9);
		mydg.fill_fld(i++, "来电日期时间", "SDATE", 22, 0, "yyyy-MM-dd HH:mm:ss"); // 来电时间
		mydg.fill_fld(i++, "订单编号", "CASEID", -1, 8);
		mydg.fill_fld(i++, "工单状态", "STATUS", 10, 1);// 状态
		mydg.set_cols_cbo_list("STATUS", "未处理,处理中,已完成");
		mydg.fill_fld(i++, "联系人", "PERSON", 22, 1); // 联系人
		mydg.fill_fld(i++, "客户满意度", "SATISFY_RESULT", 20, 1);// 客户满意度
		mydg.set_cols_cbo_list("SATISFY_RESULT", "满意,基本满意,不满意");
		mydg.fill_fld(i++, "用户咨询问题", "INFO", -1);
		mydg.fill_fld(i++, "处理结果", "CLOSE_RESULT", -1);
		mydg.fill_fld(i++, "联系电话", "MOBILE", 20);
		mydg.fill_fld(i++, "用户ID", "USERID", 22, 1);
		mydg.fill_fld(i++, "邮箱", "EMAIL", 22, 1);
		mydg.fill_header("orderList", "CASEID", m_strOrder_by()); //  放入数据
		mydg.CellLinkClicked = this;
	}

	// 排序规则
	private String m_strOrder_by() {
		String strOrderby = " ORDER BY SDATE DESC";
		return strOrderby;
	}
	
	private void InitToolbar() {
		myToolBar.fill_fld("增加", "Add", 0, 10);
		myToolBar.fill_fld("修改", "Edit", 0, 10);
		myToolBar.fill_fld_confirm("删除", "Delete", "确实要删除选中的记录吗？");
		myToolBar.fill_toolStrip("plCommand");
		myToolBar.btnItemClick = this;
	}
	
	/**
	 * 按钮控制
	 */
	public void myToolBar_btnItemClick(Object sender, String name, String parms, int nparms) {
		if (name.equals("Delete")) {
			List<String> alSelect = mydg.GetSelectedKey("CASEID");//获取字段CASEID的值
			if (alSelect.size() <= 0) {
				Functions.MsgBox("请先选中要删除的记录！");
				return;
			}
			int nCnt = 0;
			for (int index = 0; index < alSelect.size(); index++) {
				String strId = alSelect.get(index);
				my_odbc pTable = new my_odbc(pmSys.conn_crm);
				int rc = pTable.my_odbc_delete(pTableName, "CASEID='" + strId + "'");
				if (rc > 0)
					nCnt++;
				pTable.my_odbc_disconnect();
			}
			if (nCnt > 0) {
				mydg.refresh("CASEID",  m_strOrder_by());
				Functions.MsgBox("记录删除成功！");
			}
		}else if(name.equals("Edit")){
			List<String> alSelect = mydg.GetSelectedKey("CASEID");
			if (alSelect.size() <= 0) {
				Functions.MsgBox("请选中要修改的工单！");
			}else if(alSelect.size() > 1){
				Functions.MsgBox("请选择 1 条工单 进行修改！");
			}
			mydg.refresh("CASEID",  m_strOrder_by());
			Functions.MsgBox("记录修改成功！");
		} 
	}
}
