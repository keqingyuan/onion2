package org.onion.web.controller.profile;

import org.onion.web.bean.po.profile.UserProfile;
import org.onion.web.bean.po.user.User;
import org.onion.web.core.authorize.annotation.Authorize;
import org.onion.web.core.exception.NotFoundException;
import org.onion.web.core.logger.annotation.AccessLogger;
import org.onion.web.core.message.ResponseMessage;
import org.onion.web.core.utils.WebUtil;
import org.onion.web.service.profile.UserProfileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user-profile")
@AccessLogger("用户配置")
@Authorize
public class UserProfileController {

    @Resource
    private UserProfileService userProfileService;

    @RequestMapping(value = "/{type}", method = RequestMethod.GET)
    public ResponseMessage getProfile(@PathVariable String type) {
        User user = WebUtil.getLoginUser();
        UserProfile userProfile = userProfileService.selectByUserIdAndType(user.getId(), type);
        if (null == userProfile) throw new NotFoundException("配置不存在");
        return ResponseMessage.ok(userProfile);
    }

    @RequestMapping(value = "/{type}", method = RequestMethod.PATCH)
    public ResponseMessage createOrUpdateProfile(@PathVariable String type,
                                                 @RequestBody(required = true) String content) {
        User user = WebUtil.getLoginUser();
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(user.getId());
        userProfile.setType(type);
        userProfile.setContent(content);
        return ResponseMessage.ok(userProfileService.saveOrUpdate(userProfile));
    }


}
