package org.onion.web.controller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.onion.web.core.authorize.AopAuthorizeValidator;
import org.onion.web.core.exception.AuthorizeException;
import org.onion.web.core.exception.AuthorizeForbiddenException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class AopAuthorizeValidatorAutoConfiguration {

    @Bean
    public ControllerAuthorizeValidator controllerAuthorizeValidator() {
        return new ControllerAuthorizeValidator();
    }

    @Aspect
    @Order(1)
    static class ControllerAuthorizeValidator extends AopAuthorizeValidator {
        @Around(value = "execution(* org.onion.web..controller..*Controller..*(..))||@annotation(org.onion.web.core.authorize.annotation.Authorize)")
        public Object around(ProceedingJoinPoint pjp) throws Throwable {
            boolean access = super.validate(pjp);
            if (!access) throw new AuthorizeForbiddenException("无权限", 403);
            return pjp.proceed();
        }
    }
}
