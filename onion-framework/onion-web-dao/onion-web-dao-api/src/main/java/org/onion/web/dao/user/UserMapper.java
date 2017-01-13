package org.onion.web.dao.user;

import org.onion.web.dao.GenericMapper;
import org.onion.web.bean.po.user.User;

/**
 * 后台管理用户数据映射接口
 * Created by generator
 */
public interface UserMapper extends GenericMapper<User, String> {
    User selectByUserName(String userName);

    void updatePassword(User user);

    String selectNameByPk(String id);
}
