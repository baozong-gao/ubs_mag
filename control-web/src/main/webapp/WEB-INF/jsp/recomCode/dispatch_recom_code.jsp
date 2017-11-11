<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>推荐码管理</title>
</head>

<body>
<div class="bjui-pageHeader">
    <form id="pagerForm" name="recomCodeForm" data-toggle="ajaxsearch" action="${pageContext.request.contextPath}/recomCode/recomCode_search" method="post" modelAttribute="recomCodeForm">
        <input type="hidden" id="pageSize" name="pageSize" value="${recomCodeForm.pageSize}">
        <input type="hidden" id="pageCurrent" name="pageCurrent" value="${recomCodeForm.pageCurrent}">
        <div class="bjui-searchBar">
            <label>机构号:</label>
            <select name="instCode" id="instCode" data-toggle="selectpicker" data-live-search="true">
                <option value="">请选择</option>
                <c:forEach var="item" items="${tblBtsInstDOList}" varStatus="status">
                    <option value="${item.instCode}" <c:if test="${item.instCode == recomCodeForm.instCode}">selected</c:if>>${item.instName}</option>
                </c:forEach>
            </select>&nbsp;
            <label>商户号:</label>
            <input type="text" name="merId" id="merId" value="${recomCodeForm.merId}" size="15">&nbsp;
            <label>推荐码:</label>
            <input type="text" name="recomCode" id="recomCode" value="${recomCodeForm.recomCode}" size="15">&nbsp;
            <label>激活状态:</label>
            <select name="codeUsed" id="codeUsed" data-toggle="selectpicker">
                <option value="">请选择</option>
                <option <c:if test="${recomCodeForm.codeUsed=='Y'}"></c:if> value="Y">未注册</option>
                <option <c:if test="${recomCodeForm.codeUsed=='N'}">selected="selected"</c:if> value="N">已注册</option>
            </select>&nbsp;
            <button type="submit" class="btn-default" data-icon="search">查询</button>
            <button type="button" class="btn-default" data-icon="save" data-toggle="dialog" data-id="form" data-width="400" data-height="200" data-title="下载推荐码" method="post" href="${pageContext.request.contextPath}/recomCode/download_RecomCode_page?instCode=<c:out value="${recomCodeForm.instCode}"/>">下载</button>
            <button type="button" class="btn-default" data-icon="home" data-toggle="dialog" data-id="form" data-width="400" data-height="200" data-title="生成推荐码" method="post" href="${pageContext.request.contextPath}/recomCode/generate_recom_code_page?instCode=<c:out value="${recomCodeForm.instCode}"/>">生成</button>

        </div>
    </form>
</div>
<div class="bjui-pageContent tableContent">
    <table  class="table table-bordered table-hover table-striped table-top" data-toggle="tablefixed" data-width="100%" data-nowrap="true">
        <thead>
        <tr>
            <th align="center">机构号</th>
            <th align="center">商户号</th>
            <th align="center">推荐码</th>
            <th align="center">创建日期</th>
            <th align="center">过期日期</th>
            <th align="center">使用状态</th>
            <th align="center">二维码</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="record" items="${recomCodeForm.pagination.list}" varStatus="status">
            <td align="center"><c:out value="${recomCodeForm.instCode}"/></td>
            <td align="center"><c:out value="${record.merId}"/></td>
            <td align="center"><c:out value="${record.recomCode}"/></td>
            <td align="center"><c:out value="${record.createDate}"/></td>
            <td align="center"><c:out value="${record.expireDate}"/></td>
            <td align="center">
                <c:if test="${record.codeUsed=='Y'}">未注册</c:if>
                <c:if test="${record.codeUsed=='N'}">已注册</c:if>
            </td>
            <td align="center"><img width="100" id="qrImage" src="${record.qrcode}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="bjui-pageFooter" id="pageFooter">
    <div class="pages">
        <span>每页 </span>
        <div class="selectPagesize">
            <select data-toggle="selectpicker" data-toggle-change="changepagesize">
                <option value="10">10</option>
                <option value="30">30</option>
                <option value="60">60</option>
                <option value="100">100</option>
            </select>
        </div>
        <span> 条，共 <c:out value="${recomCodeForm.pagination.itemCount}"/> 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="<c:out value="${recomCodeForm.pagination.itemCount}"/>" data-page-size="<c:out value="${recomCodeForm.pageSize}"/>" data-page-current="<c:out value="${recomCodeForm.pageCurrent}"/>">
    </div>
</div>
</body>
</html>
