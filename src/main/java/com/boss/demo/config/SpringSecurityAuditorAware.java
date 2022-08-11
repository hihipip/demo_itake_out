package com.boss.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * 自動填充CreateUser 和 UpdateUser
 * 要增加這個配置類
 */
@Configuration
public class SpringSecurityAuditorAware implements AuditorAware<Long> {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Optional<Long> getCurrentAuditor() {
        try {
            return Optional.of(1L);
        } catch (Exception ex) {
            logger.error("get user Authentication failed: " + ex.getMessage(), ex);
            return Optional.of(1L);
        }
    }
}