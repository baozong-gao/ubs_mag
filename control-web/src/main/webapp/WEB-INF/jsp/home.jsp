<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: APPLE
  Date: 15/12/29
  Time: 下午3:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
    <link href="${pageContext.request.contextPath}/resources/BJUI/themes/css/AdminLTE.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/BJUI/themes/css/font-awesome-4.5.0/css/font-awesome.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/BJUI/themes/css/ionicons-master/css/ionicons.min.css"
          rel="stylesheet">
</head>
<body>
<section class="content">
    <div class="row">
        <div class="col-lg-6 col-xs-12">
            <c:forEach var="record" items="${balanceList}" varStatus="status">
                <div class="small-box bg-aqua">
                    <div class="inner">
                        <h3>${record.userMoney}<sup style="font-size: 20px">￥</sup></h3>
                        <p>${record.companyName} 余额（单位：元）</p>
                    </div>
                    <div class="icon">
                        <i class="ion ion-social-yen"></i>
                    </div>
                    <a href="javascript:;" data-toggle="navtab" data-options="{id:'211', title:'当日POS交易详情'}"
                       class="small-box-footer">详情 <i class="fa fa-arrow-circle-right"></i></a>
                </div>
            </c:forEach>

        </div>
    </div>
</section>
</body>
</html>
