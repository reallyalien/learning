package com.ot.spring.model.no;

import lombok.Data;

/**
 * 申请请求
 */
@Data
public class ApplyRequest {

    // 申请类型
    private String requestType;
    // 申请内容
    private String requestContent;
    // 申请数量
    private int appNumber;
}
