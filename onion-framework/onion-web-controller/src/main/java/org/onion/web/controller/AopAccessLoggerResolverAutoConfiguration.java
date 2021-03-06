package org.onion.web.controller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.onion.commons.StringUtils;
import org.onion.web.bean.po.logger.LoggerInfo;
import org.onion.web.bean.po.user.User;
import org.onion.web.core.exception.BusinessException;
import org.onion.web.core.logger.AccessLoggerPersisting;
import org.onion.web.core.message.FastJsonHttpMessageConverter;
import org.onion.web.core.message.ResponseMessage;
import org.onion.web.core.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@ConditionalOnProperty(name = "onion.access-logger", havingValue = "true")
public class AopAccessLoggerResolverAutoConfiguration {
    @Bean
    public AopAccessLoggerResolverConfiguration aopAccessLoggerResolverConfiguration() {
        return new AopAccessLoggerResolverConfiguration();
    }

    @Aspect
    @Order(Ordered.HIGHEST_PRECEDENCE)
    static class AopAccessLoggerResolverConfiguration extends org.onion.web.core.logger.AopAccessLoggerResolver {
        @Autowired(required = false)
        private FastJsonHttpMessageConverter fastJsonHttpMessageConverter;

        @Autowired(required = false)
        private List<AccessLoggerPersisting> accessLoggerPersisting;

        @PostConstruct
        private void init() {
            if (fastJsonHttpMessageConverter == null)
                fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        }

        @Around(value = "execution(* org.onion.web..controller..*Controller..*(..))||@annotation(org.onion.web.core.logger.annotation.AccessLogger)")
        public Object around(ProceedingJoinPoint pjp) throws Throwable {
            LoggerInfo loggerInfo = resolver(pjp);
            long requestTime = System.currentTimeMillis();
            Object result = null;
            try {
                result = pjp.proceed();
            } catch (Throwable e) {
                if (!(e instanceof BusinessException)) {
                    result = ResponseMessage.error(e.getMessage());
                    loggerInfo.setExceptionInfo(StringUtils.throwable2String(e));
                } else {
                    result = ResponseMessage.error(e.getMessage(), ((BusinessException) e).getStatus());
                }
                throw e;
            } finally {
                long responseTime = System.currentTimeMillis();
                User user = WebUtil.getLoginUser();
                loggerInfo.setRequestTime(requestTime);
                loggerInfo.setResponseTime(responseTime);
                loggerInfo.setResponseContent(fastJsonHttpMessageConverter.converter(result));
                if (user != null)
                    loggerInfo.setUserId(user.getId());
                if (result instanceof ResponseMessage)
                    loggerInfo.setResponseCode(String.valueOf(((ResponseMessage) result).getCode()));
                if (accessLoggerPersisting != null) {
                    accessLoggerPersisting.forEach(loggerPersisting -> loggerPersisting.save(loggerInfo));
                }
            }
            return result;
        }
    }

}
