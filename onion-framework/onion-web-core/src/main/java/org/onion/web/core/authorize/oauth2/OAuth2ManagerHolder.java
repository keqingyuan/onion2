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

package org.onion.web.core.authorize.oauth2;

import org.onion.web.core.authorize.annotation.Authorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author zhouhao
 * @TODO
 */
@Component
public class OAuth2ManagerHolder {

    @Autowired(required = false)
    private OAuth2Manager oAuth2Manager;

    public static OAuth2Manager target;

    public static final OAuth2Manager getManager() {
        return target;
    }

    @PostConstruct
    public void init() {
        if (target == null && oAuth2Manager != null)
            target = oAuth2Manager;
    }
}
