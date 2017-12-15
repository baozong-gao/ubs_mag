<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: APPLE
  Date: 15/12/18
  Time: 上午11:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色资源设置</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/roleManagement/update_rolefunc" id="editRoleFuncForm" name="editRoleFuncForm" class="pageForm" data-toggle="validate">

    <input type="hidden" id="roleId" name="roleId" value="${roleFuncForm.roleId}" >
    <input type="hidden" id="roleName" name="roleName" value="${roleFuncForm.roleName}" >
    <input type="hidden" name="oldRoleChecked" value="${roleFuncForm.oldRoleChecked}">
    <input type="hidden" id="checkIds" name="roleChecked" value="${roleFuncForm.roleChecked}">

    <div class="bjui-pageContent">
        <div class="bs-callout bs-callout-danger">
            <label class="btn-green btn">角 色：<c:out value="${roleFuncForm.roleName}"/></label>&nbsp;
            <label class="btn-orange btn">角色状态：<c:out value="${roleFuncForm.roleDisableTag}"/></label>&nbsp;
            <c:if test="${roleFuncForm.roleDisableTag=='启用'}">
                <a href="${pageContext.request.contextPath}/roleManagement/roledisable?id=${roleFuncForm.roleId}" class="btn btn-red" data-toggle="doajax" data-confirm-msg="确定禁用吗？" >禁用</a>&nbsp;
            </c:if>
            <c:if test="${roleFuncForm.roleDisableTag=='禁用'}">
                <a href="${pageContext.request.contextPath}/roleManagement/roleenable?id=${roleFuncForm.roleId}" class="btn btn-red" data-toggle="doajax" data-confirm-msg="确定启用吗？" >启用</a>&nbsp;
            </c:if>
        </div>

        <c:if test="${roleFuncForm.allFuncBOList!=null}">
            <div style=" height:500px; overflow:auto;">
                <ul id="ztree" class="ztree" data-toggle="ztree"
                    data-options="{
            expandAll: true,
            checkEnable: true,
            onClick: 'ZtreeClick'
        }" data-on-check="S_NodeCheck"
                >
                    <c:forEach var="item" items="${roleFuncForm.allFuncBOList}" varStatus="status">
                        <c:if test="${item.checked=='未授权'}">
                            <li data-id="${item.funcId}" data-checked="false" data-pid="${item.funcFatherId}">
                                    ${item.funcName}
                            </li>
                        </c:if>
                        <c:if test="${item.checked!='未授权'}">
                            <li data-id="${item.funcId}" data-checked="true" data-pid="${item.funcFatherId}">
                                    ${item.funcName}
                            </li>
                        </c:if>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

    </div>

    <div class="bjui-pageFooter">
        <ul>
            <li>
                <button type="button" class="btn-close" data-icon="close">关闭</button>
            </li>
            <li>
                <button id="save" type="submit" class="btn-default" data-icon="save" disabled>保存</button>
            </li>
        </ul>
    </div>
</form>
<script type="text/javascript">

    //选择事件
    function S_NodeCheck(e, treeId, treeNode) {
        $("#save").removeAttr("disabled");
        var zTree = $.fn.zTree.getZTreeObj(treeId),
            nodes = zTree.getCheckedNodes(true)
        var ids = '', names = ''
        for (var i = 0; i < nodes.length; i++) {
            ids += ',' + nodes[i].id
            names += ',' + nodes[i].name
        }
        if (ids.length > 0) {
            ids = ids.substr(1), names = names.substr(1)
        }

        $('#checkIds').val(ids);
    }
</script>
</body>
</html>
