<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>添加工单</title>
<%@ include file="/ut_controls/PageHeaderMeta.jspf"%>

<%--   <link href="${rootURL }/ut_carnet/map_css/gaode_style.css" rel="stylesheet" /> 
 <script type="text/javascript" src="${rootURL }/ctsTools_res/Field/field_javascript.js"></script> --%>
 
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
		    if ($ ("#REAL_NAME").val () == "")
		    {
			    alert ("座席员姓名不能为空!");
			    return bReturn;
		    }
		    if ($ ("#PASS").val () == "")
		    {
			    alert ("座席员密码不能为空!");
			    return bReturn;
		    }
		    
		    var strPattern = /^\d{1,20}$/;
		    var strTemp = $ ("#MOBILENO").val ();
		    if ((strTemp != "") && (strTemp.search (strPattern) == -1))
		    {
			    alert ("您输入的手机号码有误，请重新输入！");
			    return bReturn;
		    }
		    strTemp = $ ("#TEL").val ();
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
	    
	  /*   function myFld_FieldLinkClicked (strName, strParm, nType)
	    {
		    var bReturn = true;
		    if (strName == "ISPLAY_MSG_MUSIC")
		    {//add by gaoww 20160607 
			    if (nType == 11)
			    { //播放试听音乐
				    var select = $ ("#ISPLAY_MSG_MUSIC").val ();
				    var music_src = "";
				    if (select == "1")
					    music_src = "../web_desk/desktop/wav/msg01.mp3";
				    else if (select == "2")
					    music_src = "../web_desk/desktop/wav/msg02.mp3";
				    else if (select == "3")
					    music_src = "../web_desk/desktop/wav/msg03.mp3";
				    else if (select == "4")
					    music_src = "../web_desk/desktop/wav/msg04.mp3";
				    var audio = document.getElementById ('audio_desktop');
				    audio.src = music_src;
				    audio.play ();
				    return false;
			    }
		    }
		    if ((nType == 6) || (nType == 16))
		    { //add by gaoww 20151210 由ctstools控件实现image
			    if (strParm == "view") //查看原图
			    {
				    var strUrl = $ ('#' + strName).attr ("src");
				    var strTitle = "查看原图"
				    if (strUrl != "")
					    open (strUrl, strTitle);
			    }
			    else if (strParm == "upload") //16  Image 上传图片
			    {
				    //ONLY FOR IPAD   
				    if (bIsIpad == true)
					    bReturn = false; //如果是ipad则不回调
				    else
					    bReturn = true;
			    }
		    }
		    return bReturn;
	    } */
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
