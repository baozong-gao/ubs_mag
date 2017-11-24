<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册码管理</title>
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
    <form id="pagerForm" name="recomCodeListForm" data-toggle="ajaxsearch" action="${pageContext.request.contextPath}/recomCode/query_recomCode_list" method="post" modelAttribute="recomCodeListForm">
        <input type="hidden" id="pageSize" name="pageSize" value="${recomCodeListForm.pageSize}">
        <input type="hidden" id="pageCurrent" name="pageCurrent" value="${recomCodeListForm.pageCurrent}">
        <div class="bjui-searchBar">
            <div class="row-inut">

                <label class="labelblock">机构号:</label>
                <select name="instId" id="instId" data-toggle="selectpicker"  data-live-search="true">
                    <option style="width: 60px; display: inline-block" value="">-请选择-</option>
                    <c:forEach var="record" items="${instList}"
                               varStatus="status">
                        <option value="${record.instId}"
                                <c:if test="${record.instId == recomCodeListForm.instId}">selected</c:if> >${record.instName}</option>
                    </c:forEach>
                </select>
                <label class="labelblock">代理号:</label>
                <select name="agentId" id="agentId" data-toggle="selectpicker" data-live-search="true">
                    <option style="width: 60px; display: inline-block" value="">-请选择-</option>
                    <c:forEach var="record" items="${agentList}"
                               varStatus="status">
                        <option value="${record.agentId}"
                                <c:if test="${record.agentId == recomCodeListForm.agentId}">selected</c:if> >${record.agentName}</option>
                    </c:forEach>
                </select>

                <label class="labelblock">用户号:</label>
                <input type="text" name="userId" id="userId" placeholder="输入用户号" size="15" value="${recomCodeListForm.userId}">&nbsp;

                <label class="labelblock">状态:</label>
                <select name="status" id="status" data-toggle="selectpicker">
                    <option value=""  <c:if test="${'' == recomCodeListForm.status}"> selected</c:if>>请选择</option>
                    <option value="N" <c:if test="${'N' == recomCodeListForm.status}"> selected</c:if>>新申请</option>
                    <option value="E" <c:if test="${'E' == recomCodeListForm.status}"> selected</c:if>>已激活</option>
                    <option value="U" <c:if test="${'U' == recomCodeListForm.status}"> selected</c:if>>已使用</option>
                    <option value="M" <c:if test="${'M' == recomCodeListForm.status}"> selected</c:if>>已过期</option>
                </select>&nbsp;
            </div>

            <div class="row-input">
                <label class="labelblock">注册批次:</label>
                <input type="text" name="batchId" id="batchId" placeholder="生成的批次号" size="19" value="${recomCodeListForm.batchId}">
                <label class="labelblock">注册码:</label>
                <input type="text" name="recomCode" id="recomCode" placeholder="输入6位注册码" size="19" value="${recomCodeListForm.recomCode}">&nbsp;
            </div>

            <div class="row-input">
                <button type="submit" class="btn-default" data-icon="search" style="width: 91px">查询</button>
                <button type="button" class="btn-default" data-icon="home" style="width: 91px" data-toggle="navtab" data-id="navtab-recomCode-generate" data-title="生成注册码" href="${pageContext.request.contextPath}/recomCode/addPage">生成</button>
            </div>

            <div class="row-input">
                    <a data-icon="save" class="btn btn-default" id="dispatch_selected" name='dispatch_selected' data-toggle="dialog" data-width="300" data-height="200" data-id="dialog-normal" data-title="下拨注册码">下拨选中</a>

                    <a href="${pageContext.request.contextPath}/recomCode/dispatchBatchPage" data-icon="home" class="btn btn-default" data-id="navtab-recomCode-dispatch-batch" data-toggle="dialog" data-width="600" data-height="400" data-id="dialog-normal" data-title="批量下拨注册码">批量下拨</a>
            </div>
        </div>
    </form>
</div>

<div class="bjui-pageContent tableContent">
    <table  class="table table-bordered table-hover table-striped table-top" data-toggle="tablefixed" data-width="100%" data-nowrap="true">
        <thead>
        <tr>
            <th align="center"><input type="checkbox" id="selectAll"></th>
            <th align="center">批次号</th>
            <th align="center">序列号</th>
            <th align="center">注册码</th>
            <th align="center">代理号</th>
            <th align="center">绑定用户</th>
            <th align="center">状态</th>
            <th align="center">过期日期</th>
            <th align="center">创建人</th>
            <th align="center">创建日期</th>
            <th align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="record" items="${recomCodeListForm.pagination.list}" varStatus="status">
            <td align="center">
                <input type="checkbox" name ='selone' value=" ${record.userCode}&${record.recomCode}">
            </td>
            <td align="center"><c:out value="${record.batchId}"/></td>
            <td align="center"><c:out value="${record.recomCodeSeq}"/></td>
            <td align="center"><c:out value="${record.recomCode}"/></td>
            <td align="center"><c:out value="${record.userCode}"/></td>
            <td align="center"><c:out value="${record.userId}"/></td>
            <td align="center">
                <c:if test="${record.status=='N'}">新增</c:if>
                <c:if test="${record.status=='E'}">激活</c:if>
                <c:if test="${record.status=='D'}">禁用</c:if>
                <c:if test="${record.status=='M'}">过期</c:if>
                <c:if test="${record.status=='U'}">已用</c:if>
            </td>
            <td align="center"><c:out value="${record.expireDate}"/></td>
            <td align="center"><c:out value="${record.createUser}"/></td>
            <td align="center"><c:out value="${record.createTime}"/></td>
            <td align="center">
                    <%--<a href="${pageContext.request.contextPath}/recomCode/activate?recomCode=<c:out value="${record.recomCode}"/>" class="btn btn-green" data-toggle="doajax" <c:if test="${record.status=='E'} || ${record.status=='M'} || ${record.status=='U'}"> disabled=true </c:if>>激活</a>--%>
                <%--<a href="${pageContext.request.contextPath}/recomCode/activate?recomCode=<c:out value="${record.recomCode}"/>" class="btn btn-green" data-toggle="doajax" <c:if test="${record.status=='E' || record.status=='M' || record.status=='U'}"> disabled=true </c:if>>激活</a>--%>
                <a href="${pageContext.request.contextPath}/recomCode/disabled?recomCode=<c:out value="${record.recomCode}"/>" class="btn btn-blue" data-toggle="doajax" data-confirm-msg="确定？" <c:if test="${record.status=='D' || record.status=='U' }"> disabled=true </c:if>>禁用</a>
                <%--<a href="${pageContext.request.contextPath}/recomCode/modifyPage?recomCode=<c:out value="${record.recomCode}"/>" class="btn btn-refresh" data-toggle="dialog" data-width="600" data-height="400" data-id="dialog-normal" data-title="注册码修改" <c:if test="${record.status=='U'}"> disabled=true </c:if>>修改</a>--%>
                <a href="${pageContext.request.contextPath}/recomCode/dispatchPage?recomCode=<c:out value="${record.recomCode}"/>" class="btn btn-primary" data-toggle="dialog" data-width="600" data-height="400" data-id="dialog-normal" data-title="注册码下发" <c:if test="${record.status=='D' || record.status=='M' || record.status=='U' }"> disabled=true </c:if>>下发</a>
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
        <span> 条，共 <c:out value="${recomCodeListForm.pagination.itemCount}"/> 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="<c:out value="${recomCodeListForm.pagination.itemCount}"/>" data-page-size="<c:out value="${recomCodeListForm.pageSize}"/>" data-page-current="<c:out value="${recomCodeListForm.pageCurrent}"/>">
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
                    alert("注册码不属于同一代理无法批量下发选中");
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
            array.join(" ") + "&agentId=" +  recAgent));
    });


</script>


</body>

</html>
