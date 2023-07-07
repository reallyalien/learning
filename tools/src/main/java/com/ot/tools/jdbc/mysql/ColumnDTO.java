/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ot.tools.jdbc.mysql;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Builder
public class ColumnDTO {
    /**
     * 列名称
     */
    private String name;
    /**
     * 列类型
     */
    private String type;
    /**
     * 如果数据库里不存在指定的字段，则会把 value 的值作为常量列返回，如果指定的字段存在，当指定字段的值为 null 时，会以此 value 值作为默认值返回
     */
    private String value;
    /**
     * 如果数据库里不存在指定的字段，则会把 value 的值作为常量列返回，如果指定的字段存在，当指定字段的值为 null 时，会以此 value 值作为默认值返回
     */
    private String format;
    /**
     * 字段备注，注释
     */
    private String comment;

}
