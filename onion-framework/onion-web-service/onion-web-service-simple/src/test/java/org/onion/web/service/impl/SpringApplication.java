package org.onion.web.service.impl;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by zhouhao on 16-4-20.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"org.onion.web"})
@EnableTransactionManagement(proxyTargetClass = true)
//@MapperScan("org.onion.web.dao")
public class SpringApplication {

}
