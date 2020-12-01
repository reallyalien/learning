package com.ot.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;


public class DruidSourceFactory extends UnpooledDataSourceFactory {

    public DruidSourceFactory() {
        this.dataSource = new DruidDataSource();
    }
}
