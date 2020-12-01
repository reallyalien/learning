package com.ot.spring.model.yes;

import com.ot.spring.model.no.ApplyRequest;
import lombok.Data;

@Data
public abstract class Manager {

    //管理者名称
    private String name;

    //上级领导
    public Manager superManager;

    public abstract void requestHandler(ApplyRequest request);
}
