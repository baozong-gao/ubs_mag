<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>代理账户用户</title>
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

</style>

<body>
<div class="bjui-pageHeader">
    <form id="pagerForm" name="accountUserListForm" data-toggle="ajaxsearch" action="${pageContext.request.contextPath}/accountUser/query_accountUser_list" method="get" modelAttribute="accountUserListForm">
        <input type="hidden" id="pageSize" name="pageSize" value="${accountUserListForm.pageSize}">
        <input type="hidden" id="pageCurrent" name="pageCurrent" value="${accountUserListForm.pageCurrent}">
        <div class="bjui-searchBar">
            <div class="row-inut">

                <label class="labelblock">机构号:</label>
                <select name="instId" id="instId" data-toggle="selectpicker"  data-live-search="true">
                    <option style="width: 60px; display: inline-block" value="">-请选择-</option>
                    <c:forEach var="record" items="${instList}"
                               varStatus="status">
                        <option value="${record.instId}"
                                <c:if test="${record.instId == accountUserListForm.instId}">selected</c:if> >${record.instName}</option>
                    </c:forEach>
                </select>
                <label class="labelblock">代理号:</label>
                <select name="agentId" id="agentId" data-toggle="selectpicker" data-live-search="true">
                    <option style="width: 60px; display: inline-block" value="">-请选择-</option>
                    <c:forEach var="record" items="${agentList}"
                               varStatus="status">
                        <option value="${record.agentId}"
                                <c:if test="${record.agentId == accountUserListForm.agentId}">selected</c:if> >${record.agentName}</option>
                    </c:forEach>
                </select>

                <label class="labelblock">用户号:</label>
                <input type="text" name="userId" id="userId" placeholder="输入用户号" size="19" value="${accountUserListForm.userId}">&nbsp;

                <label class="labelblock">状态:</label>
                <select name="status" id="status" data-toggle="selectpicker">
                    <option value=""  <c:if test="${'' == accountUserListForm.status}"> selected</c:if>>请选择</option>
                    <option value="I" <c:if test="${'I' == accountUserListForm.status}"> selected</c:if>>初始</option>
                    <option value="P" <c:if test="${'P' == accountUserListForm.status}"> selected</c:if>>处理中</option>
                    <option value="S" <c:if test="${'S' == accountUserListForm.status}"> selected</c:if>>成功</option>
                    <option value="F" <c:if test="${'F' == accountUserListForm.status}"> selected</c:if>>失败</option>
                </select>&nbsp;
            </div>

            <div class="row-input">
                <label class="labelblock">用户名称:</label>
                <input type="text" name="userName" id="userName" placeholder="请用户名称" size="19" value="${accountUserListForm.userName}">
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
            <%--<th align="center"><input type="checkbox" id="selectAll"></th>--%>
            <th align="center">用户号</th>
            <th align="center">代理号</th>
            <th align="center">用户名</th>
            <th align="center">状态</th>
            <%--<th align="center">操作</th>--%>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="record" items="${accountUserListForm.pagination.list}" varStatus="status">
            <%--<td align="center">--%>
                <%--<input type="checkbox" name ='selone' value=" ${record.userId}">--%>
            <%--</td>--%>
            <td align="center"><c:out value="${record.userId}"/></td>
            <td align="center"><c:out value="${record.agentId}"/></td>
            <%--<td align="center"><c:out value="${record.userName}"/></td>--%>
            <td align="center"></td>
            <td align="center">
                <c:if test="${record.status=='N'}">新增</c:if>
                <c:if test="${record.status=='E'}">激活</c:if>
                <c:if test="${record.status=='D'}">禁用</c:if>
                <c:if test="${record.status=='C'}">注销</c:if>
            </td>
            <%--<td align="center">--%>
                <%--<a href="${pageContext.request.contextPath}/accountUser/detailAccountUserPage?userId=<c:out value="${record.userId}"/>" class="btn btn-primary" data-toggle="navtab" data-id="tab-detail-accountUser" data-title="用户详细信息">详细</a>--%>
            <%--</td>--%>
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
        <span> 条，共 <c:out value="${accountUserListForm.pagination.itemCount}"/> 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="<c:out value="${accountUserListForm.pagination.itemCount}"/>" data-page-size="<c:out value="${accountUserListForm.pageSize}"/>" data-page-current="<c:out value="${accountUserListForm.pageCurrent}"/>">
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
