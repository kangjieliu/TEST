<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>工单管理</title>
    <%@ include file="/ut_controls/PageHeaderMeta.jspf" %>
    <script type="text/javascript" language="javascript"> 

     function btnToolBarClick(strName) {
            var bReturn = true;
            if(strName == "Add"){
            	window.location.href="http://127.0.0.1:8080/UltraCRM-learning/order/order_add.aspx";
            	return false;
            }
            if(strName == "Edit"){
            	window.location.href="http://127.0.0.1:8080/UltraCRM-learning/order/order_edit.aspx";
            	return false;
            }
            
            return bReturn;
        } 
        
        
    </script>
</head>
<body>
    <form id="form1"  method="post">
    <div id="divBody" align="center">
    <div>
          <div style="text-align: left;">${plCommand }</div>
    </div>
  
    <div id="divContent" >
		<div >${orderList}</div>
     </div>
    </div>
    </form>
</body>
</html>



