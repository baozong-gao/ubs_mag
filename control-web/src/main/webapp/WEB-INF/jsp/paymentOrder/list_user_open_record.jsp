<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户开通记录</title>
</head>

<style>

    .labelblock{
        display: inline-block;
        width: 5rem;
    }

    .selectlength{
        display: inline-block;
        width: 60px;
    }

    .labelOrderStatus {
        background-color: #A8BDCF!important;
        color: #FFFFFF;
        display: inline-block;
        font-family: Open Sans;
        font-size: 11px;
        font-weight: normal;
        line-height: 14px;
        padding: 2px 4px;
        vertical-align: baseline;
        white-space: nowrap;
        border-radius: .25em;
    }

    .order-success {
        background-color: #45B6B0!important;
    }
    .order-warning {
        background-color: #FBB44C!important;
    }
    .order-important {
        background-color: #FF6B6B!important;
    }

</style>
<body>
<div class="bjui-pageHeader">
    <form id="pagerForm" name="paymentOrderListForm" data-toggle="ajaxsearch" action="${pageContext.request.contextPath}/paymentOrder/query_user_open_list" method="get" modelAttribute="paymentOrderListForm">
        <input type="hidden" id="pageSize" name="pageSize" value="${paymentOrderListForm.pageSize}">
        <input type="hidden" id="pageCurrent" name="pageCurrent" value="${paymentOrderListForm.pageCurrent}">
        <div class="bjui-searchBar">
            <div class="row-inut">

                <label class="labelblock">订单号:</label>
                <input type="text" name="orderId" id="orderId" placeholder="输入订单号" size="19" value="${paymentOrderListForm.orderId}">&nbsp;

                <label class="labelblock">订单类型:</label>
                <select name="orderType" id="orderType" data-toggle="selectpicker">
                    <option value=""  <c:if test="${'' == paymentOrderListForm.orderType}"> selected</c:if>>请选择</option>
                    <option value="KT" <c:if test="${'KT' == paymentOrderListForm.orderType}"> selected</c:if>>付费开通
                    </option>
                </select>

                <label>状态:</label>
                <select name="orderStatus" id="orderStatus" data-toggle="selectpicker">
                    <option value=""  <c:if test="${'' == paymentOrderListForm.orderStatus}"> selected</c:if>>请选择</option>
                    <option value="S" <c:if test="${'S' == paymentOrderListForm.orderStatus}"> selected</c:if>>支付成功</option>
                    <option value="F" <c:if test="${'F' == paymentOrderListForm.orderStatus}"> selected</c:if>>支付失败</option>
                    <option value="P" <c:if test="${'P' == paymentOrderListForm.orderStatus}"> selected</c:if>>支付中</option>
                    <option value="I" <c:if test="${'I' == paymentOrderListForm.orderStatus}"> selected</c:if>>未支付</option>
                </select>&nbsp;

                <label>订单日期从</label>
                <input id="startOrderDate" name="startOrderDate" type="text" data-toggle="datepicker"
                            value="${paymentOrderListForm.startOrderDate}"
                            class="form-control" size="15" data-pattern="yyyyMMdd"/>&nbsp;
                <label>至</label>&nbsp;
                <input id="endOrderDate"  name="endOrderDate" type="text" data-toggle="datepicker"
                            value="${paymentOrderListForm.endOrderDate}"
                            class="form-control" size="15" data-pattern="yyyyMMdd"/>

            </div>

            <div class="row-input">
                <button type="submit" class="btn-default" data-icon="search" style="width: 91px">查询</button>
            </div>

        </div>
    </form>
</div>

<div class="bjui-pageContent tableContent">
    <table  class="table table-bordered table-hover table-striped table-top" data-toggle="tablefixed" data-width="100%" data-nowrap="true">
        <thead>
        <tr>
            <th align="center">订单号</th>
            <th align="center">金额</th>
            <th align="center">费用</th>
            <th align="center">状态</th>
            <th align="center">状态描述</th>
            <th align="center">订单时间</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="record" items="${paymentOrderListForm.pagination.list}" varStatus="status">
            <td align="center"><c:out value="${record.orderId}"/></td>
            <td align="center"><c:out value="${record.tradeAmount}"/></td>
            <td align="center"><c:out value="${record.feeAmount}"/></td>
            <td align="center">
                <c:if test="${record.bipiStatus=='F'}"><span class="labelOrderStatus order-important">支付失败</span></c:if>
                <c:if test="${record.bipiStatus=='S'}"><span class="labelOrderStatus order-success">支付成功</span></c:if>
            </td>
            <td align="center"><c:out value="${record.bipiRespMessage}"/></td>
            <td align="center"><c:out value="${record.orderDate}${record.orderTime}"/></td>
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
        <span> 条，共 <c:out value="${paymentOrderListForm.pagination.itemCount}"/> 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="<c:out value="${paymentOrderListForm.pagination.itemCount}"/>" data-page-size="<c:out value="${paymentOrderListForm.pageSize}"/>" data-page-current="<c:out value="${paymentOrderListForm.pageCurrent}"/>">
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/bootstrap/js/jquery.checkbox.js"></script>

<script type="text/javascript">

    $(document).ready(function () {
        var errorCode = "${errorCode}";
        var errorMessage = "${errorMessage}";
        if ('' != errorCode &&  typeof errorCode != 'undefined' && 'null' != errorCode && 'F' == errorCode) {
            $.toast(errorMessage, "text");
        }
    });

</script>


</body>

</html>
