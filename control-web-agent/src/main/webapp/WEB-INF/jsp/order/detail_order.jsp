<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html>
<head>
    <script
            src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
    <title>订单详细</title>
</head>

<style>

    .labelblock{
        display: inline-block;
        width: 5rem;
        text-align: left;
    }

</style>

<body>
<div class="bjui-pageHeader"></div>
<div class="bjui-pageContent tableContent">

    <form id="pagerForm" name="orderDetailForm">

        <div style="margin:15px auto 0; width:96%;">
            <div class="row" style="padding: 0 8px;">

                <!-- 订单信息属性 -->
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading"><h3 class="panel-title">订单信息--</h3></div>
                        <div class="panel-body">
                            <div class="row-input" style="margin: 20px 0 20px; width: 100%">

                                <div class="form-group col-md-6">
                                    <label class="control-label labelblock">机构：</label>
                                    <input type="text"
                                           name="instId" size="10" readonly="readonly"
                                           value="${orderDetailForm.instId}"/>
                                    <input type="text"
                                           name="instName" size="20" readonly="readonly"
                                           value="${orderDetailForm.instName}"/>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="control-label labelblock">代理：</label>
                                    <input type="text"
                                           name="agentId" size="10" readonly="readonly"
                                           value="${orderDetailForm.agentId}"/>
                                    <input type="text"
                                           name="agentName" size="20" readonly="readonly"
                                           value="${orderDetailForm.agentName}"/>
                                </div>
                            </div>
                            <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                <div class="form-group col-md-6">
                                    <label class="control-label labelblock">用户：</label>
                                    <input type="text"
                                           name="userId" size="10" readonly="readonly"
                                           value="${orderDetailForm.userId}"/>
                                    <input type="text"
                                           name="userName" size="20" readonly="readonly"
                                           value="${orderDetailForm.userName}"/>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="control-label labelblock">订单状态：</label>
                                    <input type="text"
                                           name="orderStatus" size="5" readonly="readonly"
                                           value="${orderDetailForm.orderStatus}"/>
                                    <label class="control-label labelblock">处理状态：</label>
                                    <input type="text"
                                           name="processStatus" size="5" readonly="readonly"
                                           value="${orderDetailForm.processStatus}"/>
                                </div>
                            </div>
                            <div class="row-input" style="margin: 20px 0 20px; width: 100%">

                                <div class="form-group col-md-6">
                                    <label class="control-label labelblock">订单号：</label>
                                    <input type="text"
                                           name="orderId" size="20" readonly="readonly"
                                           value="${orderDetailForm.orderId}"/>
                                </div>

                                <div class="form-group col-md-6">
                                    <label class="control-label labelblock">支付单号：</label>
                                    <input type="text"
                                           name="payOrder" size="20" readonly="readonly"
                                           value="${orderDetailForm.payOrder}">
                                </div>
                            </div>
                            <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                <div class="form-group col-md-6">
                                    <label class="control-label labelblock">下单日期：</label>
                                    <input type="text"
                                           name="exeBegTime" size="20" readonly="exeBegTime"
                                           value="${orderDetailForm.exeBegTime}"/>
                                </div>

                                <div class="form-group col-md-6">
                                    <label class="control-label labelblock">结束日期：</label>
                                    <input type="text"
                                           name="exeEndTime" size="20" readonly="readonly"
                                           value="${orderDetailForm.exeEndTime}">
                                </div>
                            </div>
                            <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                <div class="form-group col-md-4">
                                    <label class="control-label labelblock">总金额：</label>
                                    <input type="text"
                                           name="totAmount" size="20" readonly="exeBegTime"
                                           value="${orderDetailForm.totAmount}"/>
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="control-label labelblock">总扣分：</label>
                                    <input type="text"
                                           name="totDegree" size="20" readonly="readonly"
                                           value="${orderDetailForm.totDegree}">
                                </div>
                                <div class="form-group col-md-4">
                                    <label class="control-label labelblock">总条数：</label>
                                    <input type="text"
                                           name="totCount" size="20" readonly="readonly"
                                           value="${orderDetailForm.totCount}">
                                </div>
                            </div>
                            <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                <div class="form-group col-md-4">
                                    <label class="control-label labelblock">总费用：</label>
                                    <input type="text"
                                           name="serviceFee" size="20" readonly="readonly"
                                           value="${orderDetailForm.serviceFee}"/>
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="control-label labelblock">渠道费用：</label>
                                    <input type="text"
                                           name="chnlFee" size="20" readonly="readonly"
                                           value="${orderDetailForm.chnlFee}">
                                </div>
                                <div class="form-group col-md-4">
                                    <label class="control-label labelblock">刷单费率：</label>
                                    <input type="text"
                                           name="feeRate" size="20" readonly="readonly"
                                           value="${orderDetailForm.feeRate}">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 违章信息 -分割线 -->
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading"><h3 class="panel-title">具体违章信息列表</h3></div>
                    </div>
                </div>

                <!-- 违章信息 -->
                <c:forEach var="record" items="${wzList}"
                           varStatus="status">
                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-heading"><h3 class="panel-title">违章信息---${record.time}---${record.wflx}---${record.locationName}</h3></div>
                            <div class="panel-body">
                                <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                    <div class="form-group col-md-4">
                                        <label class="control-label labelblock">违章ID：</label>
                                        <input type="text"
                                               name="wzId" size="20" readonly="readonly"
                                               value="${record.wzId}"/>
                                    </div>

                                    <div class="form-group col-md-4">
                                        <label class="control-label labelblock">驾驶员：</label>
                                        <input type="text"
                                               name="carDriverNumber" size="20" readonly="readonly"
                                               value="${record.carDriveNumber}">
                                    </div>
                                    <div class="form-group col-md-4">
                                        <label class="control-label labelblock">车牌号：</label>
                                        <input type="text"
                                               name="carNumber" size="20" readonly="readonly"
                                               value="${record.carNumber}">
                                    </div>
                                </div>
                                <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                    <div class="form-group col-md-4">
                                        <label class="control-label labelblock">扣分：</label>
                                        <input type="text"
                                               name="degree" size="20" readonly="readonly"
                                               value="${record.degree}"/>
                                    </div>

                                    <div class="form-group col-md-4">
                                        <label class="control-label labelblock">罚款：</label>
                                        <input type="text"
                                               name="refine" size="20" readonly="readonly"
                                               value="${record.refine}">
                                    </div>
                                    <div class="form-group col-md-4">
                                        <label class="control-label labelblock">状态：</label>
                                        <input type="text"
                                               name="canProcessMsg" size="20" readonly="readonly"
                                               value="${record.canProcessMsg}">
                                    </div>
                                </div>
                                <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                    <div class="form-group col-md-12">
                                        <label class="control-label labelblock">具体地点：</label>
                                        <input type="text"
                                               name="location" size="20" readonly="readonly"
                                               value="${record.location}"/>
                                    </div>
                                </div>
                                <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                    <div class="form-group col-md-4">
                                        <label class="control-label labelblock">服务费：</label>
                                        <input type="text"
                                               name="serviceFee" size="20" readonly="readonly"
                                               value="${record.serviceFee}"/>
                                    </div>

                                    <div class="form-group col-md-4">
                                        <label class="control-label labelblock">支付费用：</label>
                                        <input type="text"
                                               name="payFee" size="20" readonly="readonly"
                                               value="${record.payFee}">
                                    </div>
                                    <div class="form-group col-md-4">
                                    </div>
                                </div>
                                <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                    <div class="form-group col-md-12">
                                        <label class="control-label labelblock">违章简介：</label>
                                        <textarea id="wzReason" name="wzReason" style="width:100%;height: 60px;" >${record.reason}</textarea>
                                    </div>
                                    <div class="form-group col-md-12">
                                        <label class="control-label labelblock">办理说明：</label>
                                        <textarea id="wzBLSM" name="wzBLSM" style="width:100%;height: 60px;" >${record.blsm}</textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </form>
</div>
</body>

</html>
