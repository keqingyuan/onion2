/*
 * Copyright 2015-2016 http://onion.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onion.web.core.datasource;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public enum DatabaseType {
    unknown(null, null, null),
    mysql("com.mysql.jdbc.Driver", "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource", "select 1"),
    h2("org.h2.Driver", "org.h2.jdbcx.JdbcDataSource", "select 1"),
    oracle("oracle.jdbc.driver.OracleDriver", "oracle.jdbc.xa.client.OracleXADataSource", "select 1 from dual");

    DatabaseType(String driverClassName, String xaDataSourceClassName, String testQuery) {
        this.driverClassName = driverClassName;
        this.testQuery = testQuery;
        this.xaDataSourceClassName = xaDataSourceClassName;
    }

    private final String testQuery;

    private final String driverClassName;

    private final String xaDataSourceClassName;

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getXaDataSourceClassName() {
        return xaDataSourceClassName;
    }

    public String getTestQuery() {
        return testQuery;
    }

    public static DatabaseType fromJdbcUrl(String url) {
        if (StringUtils.hasLength(url)) {
            Assert.isTrue(url.startsWith("jdbc"), "URL must start with 'jdbc'");
            String urlWithoutPrefix = url.substring("jdbc".length()).toLowerCase();
            for (DatabaseType driver : values()) {
                String prefix = ":" + driver.name().toLowerCase() + ":";
                if (driver != unknown && urlWithoutPrefix.startsWith(prefix)) {
                    return driver;
                }
            }
        }
        return unknown;
    }
}