<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>修改工单</title>
<%@ include file="/ut_controls/PageHeaderMeta.jspf"%>

<script type="text/javascript" language="javascript">
	//处理Toolbar 的按键事件
    function btnToolBarClick (strName)
    {
	    var bReturn = false;
	    if (strName == "Close")
	    {
		    window.parent.RemoveFrameTab (window.frameElement.id);
	    }
	    else if (strName == "Save")
	    {
		    if ($ ("#PERSON").val () == "")
		    {
			    alert ("PERSON不能为空!");
			    return bReturn;
		    }
		    
		    var strPattern = /^\d{1,20}$/;
		    var strTemp  = $ ("#MOBILE").val ();
		    if ((strTemp != "") && (strTemp.search (strPattern) == -1))
		    {
			    alert ("您输入的联系电话有误，请重新输入！");
			    return bReturn;
		    }
		    strPattern = /^[a-zA-Z]\w+@\w+\.\w+\.?\w*$/;
		    strTemp = $ ("#EMAIL").val ();
		    if ((strTemp != "") && (strTemp.search (strPattern) == -1))
		    {
			    alert ("您输入的Email地址有误，请重新输入！");
			    return bReturn;
		    }
		    bReturn = true;
	    }
	    return bReturn;
    }
</script>

</head>
<body>
	<form id="frmBody" method="post">
		<div align="center">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="5px" valign="top"></td>
					<td style="padding-right: 10px; vertical-align: top;">
						<div id="divContent">
							<div  style="width: 100%; height: auto;">${plCommand}</div>
							<div style="height: 100%; width: 98%;">${gbEdit}</div>
						</div>
					</td>
				</tr>
			</table>
	
		</div>
		<audio id="audio_desktop" src="" controls="controls" style="display: none;"></audio>
	</form>
</body>
</html>
