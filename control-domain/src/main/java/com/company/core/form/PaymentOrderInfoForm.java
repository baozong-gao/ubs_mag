package com.company.core.form;

import lombok.Data;

@Data
public class PaymentOrderInfoForm  extends BaseForm {
	
	private String userType;
	private String userCode;
	private String instId;
	private String instName;
	private String agentId;
	private String agentName;
	private String userId;
	private String userName;
	
	private String mid;// 商户号 - 支付业务的商户号
	private String tid; //终端号;
	private String orderId; //订单号;
	private String orderDate;//订单日期;
	private String orderTime; //订单时间;
	private String platformSeq; //平台订单序列号;
	private String orderType; //订单类型;
	private String tradeAmount; //订单金额;
	private String feeAmount;//手续费;
	private String orderStatus;//订单状态;
	private String orderStatusDesc;//订单状态描述;
	
	private String startOrderDate;//订单时间区间-开始;
	private String endOrderDate;//订单时间区间-结束;
	
	
	
}
