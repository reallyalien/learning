package com.ot.es.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.io.Serializable;

/**
 * EsIcd
 *
 * @author lfy
 * @date 19-5-7
 **/
@Setter
@Getter
@Document(indexName = "local_docinfo", type = "doc")
@Setting(settingPath = "/json/common-setting-new.json")
@Mapping(mappingPath = "/json/doc_info-mapping.json")
public class EsDocInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public String hosId;
    @Id
    public String docCode;
    public String docName;
    public String submitTime;
    public String usedTime;
    public String source;
    public String ipInfo;
    public String deptCode;
    public String deptName;
    public String title;
    public String intro;
    public String special;
    public String labels;
    public String declaration;
    public String comment;
    public String score;


}
