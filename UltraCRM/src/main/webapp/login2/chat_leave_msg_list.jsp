<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>无标题页</title>
    <%@ include file="/ut_controls/PageHeaderMeta.jspf" %>
    <script type="text/javascript" language="javascript"> 

        function btnToolBarClick(strName) {
            var bReturn = true;            
            return bReturn;
        }
        
        function mySearch_FieldLinkClicked(strName) {
            if (strName == "QuickSearch") {
                //add by gaoww 20160506 增加日期合法性检查
                var strTemp = $("#SDATE").val();
                strPattern = /^\d{4}-\d{2}-\d{2}$/;
                if ((strTemp.length > 0) && (strTemp.search(strPattern) == -1)) {
                    alert("您输入的开始日期有误，请重新输入！");
                    return false;
                }

                strTemp = $("#EDATE").val();
                if ((strTemp.length > 0) && (strTemp.search(strPattern) == -1)) {
                    alert("您输入的开始日期有误，请重新输入！");
                    return false;
                }

                var sdate = $("#SDATE").val();
                var edate = $("#EDATE").val();
                if (sdate > edate) {
                    alert("您输入结束日期小于开始日期，请重新输入！")
                    return false;
                }
                return true;
            }
        }
        
    </script>
</head>
<body>
    <form id="form1"  method="post">
    <div id="divBody" align="center">
    <div>
          <div style="text-align: left;">${plCommand }</div>
    </div>
  
    <div id="divQuery">         
             <div>${plSearch }</div>
    </div>
    <div id="divContent">
		<div>${dgvList}</div>
     </div>
    </div>
    </form>
</body>
</html>



