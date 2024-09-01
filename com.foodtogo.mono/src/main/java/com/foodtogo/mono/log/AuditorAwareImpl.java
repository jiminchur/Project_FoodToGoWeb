package com.foodtogo.mono.log;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public Optional<String> getCurrentAuditor() {
        String byWho = httpServletRequest.getHeader("X-User-Id");
        if(!StringUtils.hasText(byWho)) {
            byWho = "system";
        }
        return Optional.of(byWho);
    }
}
