package org.onion.web.service.profile;

import org.onion.web.bean.common.QueryParam;
import org.onion.web.bean.po.profile.UserProfile;
import org.onion.web.service.GenericService;

/**
 * Created by zhouhao on 16-7-4.
 */
public interface UserProfileService extends GenericService<UserProfile, String> {

    default UserProfile selectByUserIdAndType(String userId, String type) {
        return selectSingle(QueryParam.build().where("userId", userId).and("type", type));
    }
}
