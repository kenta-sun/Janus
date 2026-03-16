package com.eredar.janus.starter;

import com.eredar.janus.core.aspect.JanusAspect;
import com.eredar.janus.core.config.JanusConfigProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Janus Spring Boot Starter 自动装配入口。
 */
@Configuration
@ConditionalOnClass(JanusAspect.class)
@ComponentScan(basePackages = "com.eredar.janus") // 扫描 Janus 核心包，自动注册组件
@EnableConfigurationProperties(JanusConfigProperties.class) // 启用 Janus 配置属性绑定
public class JanusAutoConfiguration {

}
