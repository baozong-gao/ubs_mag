<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改代理费率</title>
</head>


<style>

    .labellength88 {
        display: inline-block;
        width: 8rem;
    }

</style>

<body>
<div class="bjui-pageContent">
    <form:form class="nice-validator n-red" data-alertmsg="false"
               data-toggle="validate" action="agent/update_default_agent_fee"
               novalidate="novalidate" modelAttribute="defaultAgentFeeForm" id="defaultAgentFeeForm" name="defaultAgentFeeForm">

    <input type="hidden" id="instId" name="instId" value="${defaultAgentFeeForm.instId}">
    <input type="hidden" id="agentId" name="agentId" value="${defaultAgentFeeForm.agentId}">
    <input type="hidden" id="userType" name="userType" value="${defaultAgentFeeForm.userType}">
    <input type="hidden" id="userCode" name="userCode" value="${defaultAgentFeeForm.userCode}">

        <!-- 代理费率 -->
    <div style="margin:15px auto 0; width:96%;">
        <div class="row" style="padding: 0 8px;">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading"><h3 class="panel-title">代理费率-${defaultAgentFeeForm.agentId}</h3></div>
                    <div class="panel-body">
                        <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                            <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                <div class="form-group col-md-6">
                                    <label class="control-label">成本固定单笔费用：</label>
                                    <input type="text" disabled="true"
                                           name="defaultFeeFixed" size="15" value="${defaultAgentFeeForm.defaultFeeFixed}"
                                           placeholder="单位笔/元"/>&nbsp;
                                </div>

                                <div class="form-group col-md-6">
                                    <label class="control-label">成本交易比例费率：</label>
                                    <input type="text" disabled="true"
                                           name="defaultFeeRate" size="15" value="${defaultAgentFeeForm.defaultFeeRate}"
                                           placeholder="如:千5, 请输入0.5"/>%&nbsp;
                                </div>
                            </div>
                            <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                <div class="form-group col-md-6">
                                    <label class="control-label">实收固定单笔费用：</label>
                                    <input type="text"
                                           name="EffectiveFeeFixed" size="15" value="${defaultAgentFeeForm.effectiveFeeFixed}"
                                           placeholder="单位笔/元"/>&nbsp;
                                </div>

                                <div class="form-group col-md-6">
                                    <label class="control-label">实收交易比例费率：</label>
                                    <input type="text"
                                           name="EffectiveFeeRate" size="15" value="${defaultAgentFeeForm.effectiveFeeRate}"
                                           placeholder="如:千5, 请输入0.5"/>%&nbsp;
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-12" style="margin: 20px 0 20px; ">
                <button type="submit" class="btn-default" data-icon="save" style="float: right">更新</button>&nbsp;
            </div>
        </div>
        </form:form>
    </div>
    <div class="bjui-pageFooter">
    </div>
</body>
</html>
