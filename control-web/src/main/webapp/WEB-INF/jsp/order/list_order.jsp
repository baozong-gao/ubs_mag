<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>交易管理</title>
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
    <form id="pagerForm" name="orderListForm" data-toggle="ajaxsearch" action="${pageContext.request.contextPath}/order/query_order_list" method="get" modelAttribute="orderListForm">
        <input type="hidden" id="pageSize" name="pageSize" value="${orderListForm.pageSize}">
        <input type="hidden" id="pageCurrent" name="pageCurrent" value="${orderListForm.pageCurrent}">
        <div class="bjui-searchBar">
            <div class="row-inut">

                <label class="labelblock">机构号:</label>
                <select name="instId" id="instId" data-toggle="selectpicker"  data-live-search="true">
                    <option style="width: 60px; display: inline-block" value="">-请选择-</option>
                    <c:forEach var="record" items="${instList}"
                               varStatus="status">
                        <option value="${record.instId}"
                                <c:if test="${record.instId == orderListForm.instId}">selected</c:if> >${record.instName}</option>
                    </c:forEach>
                </select>
                <label class="labelblock">代理号:</label>
                <select name="agentId" id="agentId" data-toggle="selectpicker" data-live-search="true">
                    <option style="width: 60px; display: inline-block" value="">-请选择-</option>
                    <c:forEach var="record" items="${agentList}"
                               varStatus="status">
                        <option value="${record.agentId}"
                                <c:if test="${record.agentId == orderListForm.agentId}">selected</c:if> >${record.agentName}</option>
                    </c:forEach>
                </select>

                <label class="labelblock">用户号:</label>
                <input type="text" name="userId" id="userId" placeholder="输入用户号" size="19" value="${orderListForm.userId}">&nbsp;

                <label class="labelblock">状态:</label>
                <select name="status" id="status" data-toggle="selectpicker">
                    <option value=""  <c:if test="${'' == orderListForm.status}"> selected</c:if>>请选择</option>
                    <option value="0" <c:if test="${'0' == orderListForm.status}"> selected</c:if>>未支付</option>
                    <option value="1" <c:if test="${'1' == orderListForm.status}"> selected</c:if>>已支付</option>
                </select>&nbsp;
            </div>

            <div class="row-input">
                <label class="labelblock">ID:</label>
                <input type="text" name="id" id="id" placeholder="输入订单ID" size="19" value="${orderListForm.id}">
                <label class="labelblock">订单号:</label>
                <input type="text" name="orderId" id="orderId" placeholder="请输入订单号" size="19" value="${orderListForm.orderId}">
                <label class="labelblock">支付订单:</label>
                <input type="text" name="payOrder" id="payOrder" placeholder="请输入支付订单号" size="19" value="${orderListForm.payOrder}">
            </div>

            <div class="row-input">
                <button type="submit" class="btn-default" data-icon="search" style="width: 91px">查询</button>
            </div>

            <%--<div id="item_1" class="item">--%>
                <%--Item 1--%>
                <%--<div class="tooltip_description" style="display:none" title="Item 1 Description">--%>
                    <%--Lorem Ipsum--%>
                <%--</div>--%>
            <%--</div>--%>

        </div>
    </form>
</div>

<div class="bjui-pageContent tableContent">
    <table  class="table table-bordered table-hover table-striped table-top" data-toggle="tablefixed" data-width="100%" data-nowrap="true">
        <thead>
        <tr>
            <th align="center"><input type="checkbox" id="selectAll"></th>
            <th align="center">订单号</th>
            <th align="center">支付订单号</th>
            <th align="center">用户号</th>
            <th align="center">状态</th>
            <th align="center">总金额</th>
            <th align="center">总扣分</th>
            <th align="center">总条目</th>
            <th align="center">服务费</th>
            <th align="center">渠道费</th>
            <th align="center">订单开始日期</th>
            <th align="center">订单结束日期</th>
            <th align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="record" items="${orderListForm.pagination.list}" varStatus="status">
            <td align="center">
                <input type="checkbox" name ='selone' value=" ${record.orderId}">
            </td>
            <td align="center"><c:out value="${record.orderId}"/></td>
            <td align="center"><c:out value="${record.payOrder}"/></td>
            <td align="center"><c:out value="${record.userId}"/></td>
            <td align="center">
                <c:if test="${record.status=='0'}"><span class="labelOrderStatus order-important">未支付</span></c:if>
                <c:if test="${record.status=='1'}"><span class="labelOrderStatus order-success">已支付</span></c:if>
            </td>
            <td align="center"><c:out value="${record.totAmount}"/></td>
            <td align="center"><c:out value="${record.totDegree}"/></td>
            <td align="center"><c:out value="${record.totCount}"/></td>
            <td align="center"><c:out value="${record.serviceFee}"/></td>
            <td align="center"><c:out value="${record.chnlFee}"/></td>

            <td align="center"><c:out value="${record.exeBegTime}"/></td>
            <td align="center"><c:out value="${record.exeEndTime}"/></td>
            <td align="center">
                <a href="${pageContext.request.contextPath}/order/detailOrderPage?id=<c:out value="${record.id}"/>&orderId=<c:out value="${record.orderId}"/>&payOrder=<c:out value="${record.payOrder}"/>" class="btn btn-primary" data-toggle="navtab" data-id="tab-detail-order" data-title="订单详细信息">详细</a>
            </td>
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
        <span> 条，共 <c:out value="${orderListForm.pagination.itemCount}"/> 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="<c:out value="${orderListForm.pagination.itemCount}"/>" data-page-size="<c:out value="${orderListForm.pageSize}"/>" data-page-current="<c:out value="${orderListForm.pageCurrent}"/>">
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/bootstrap/js/jquery.checkbox.js"></script>

<script type="text/javascript">
    /* 全选、反选 */
    $(document.body).on("click", "#selectAll", function () {
        var _is_check = jQuery(this).get(0).checked;
        jQuery("input[name='selone']").each(function (_i, _o) {
            if (!$(_o).prop("disabled")) {
                jQuery(_o).get(0).checked = _is_check;
            }
        });
    });


    /**
     * 批量操作
     */
    $(document.body).on("click", "#dispatch_selected", function () {

        var array= new Array();
        var eflag = false;
        var c = 1;
        var oagent = "";
        var recAgent = "";
        var compareAgent = "";
        $('input[type="checkbox"][name="selone"]:checked').each(
            function() {
                var str = $(this).val();
                var sa = str.split("&");
                oagent = $("#agentId").val().trim();
                recAgent = sa[0].trim();
                if("" == compareAgent){
                    compareAgent = recAgent;
                }
                if(compareAgent != recAgent){
                    alert("注册码不属于同一机构无法批量下发选中");
                    eflag = true;
                    return;
                }
                sa[1].replace("[", "").replace("]", "");
                array.push(sa[1]);  //推荐码
            }
        );
        if(eflag){
            return;
        }
        if (array.length == "") {
            $(this).alertmsg("warn", "未选中任何记录!");
            return;
        }

        $("#dispatch_selected").attr("href", ("${pageContext.request.contextPath}/recomCode/dispatchSelectedPage" + "?recomCode=" +
            array.join(" ") + "&instId=" +  recAgent));
    });


</script>


</body>

</html>
