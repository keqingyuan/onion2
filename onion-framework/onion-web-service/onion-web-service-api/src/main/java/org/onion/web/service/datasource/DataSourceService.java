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

package org.onion.web.service.datasource;

import org.onion.web.bean.po.datasource.DataSource;
import org.onion.web.service.GenericService;

/**
 * 数据源服务类
 * Created by generator
 */
public interface DataSourceService extends GenericService<DataSource, String> {

    void enable(String id);

    void disable(String id);

}
