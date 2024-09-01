package com.foodtogo.mono.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class RequestParamCleaningFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        CleanedHttpServletRequestWrapper cleanedRequest = new CleanedHttpServletRequestWrapper(httpRequest);
        chain.doFilter(cleanedRequest, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터 초기화 로직 (필요한 경우)
    }

    @Override
    public void destroy() {
        // 필터 종료 로직 (필요한 경우)
    }

    private static class CleanedHttpServletRequestWrapper extends HttpServletRequestWrapper {

        public CleanedHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String name) {
            String value = super.getParameter(name);
            return value != null ? value.replaceAll("\\p{Cntrl}", "") : null;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            Map<String, String[]> originalMap = super.getParameterMap();
            Map<String, String[]> cleanedMap = new HashMap<>();
            originalMap.forEach((key, values) -> {
                String[] cleanedValues = values != null ? cleanValues(values) : null;
                cleanedMap.put(key, cleanedValues);
            });
            return cleanedMap;
        }

        @Override
        public String[] getParameterValues(String name) {
            String[] values = super.getParameterValues(name);
            return values != null ? cleanValues(values) : null;
        }

        private String[] cleanValues(String[] values) {
            for (int i = 0; i < values.length; i++) {
                values[i] = values[i].replaceAll("\\p{Cntrl}", "");
            }
            return values;
        }
    }
}
