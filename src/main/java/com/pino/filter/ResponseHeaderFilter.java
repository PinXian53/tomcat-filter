package com.pino.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ResponseHeaderFilter implements Filter {

    private final Map<String, String> headerMap = new HashMap<>();

    @Override
    public void init(FilterConfig config) {
        // 讀取 web.xml 的資料，放入 headerMap
        Enumeration<String> paramNames = config.getInitParameterNames();

        if (paramNames == null) {
            return;
        }

        while (paramNames.hasMoreElements()) {
            String headerName = paramNames.nextElement();
            String headerValue = config.getInitParameter(headerName);

            if (isNotEmpty(headerName) && isNotEmpty(headerValue)) {
                this.headerMap.put(headerName, headerValue);
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 將 headerMap 的資料放入 Response Header
        headerMap.forEach(response::addHeader);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

    private static boolean isNotEmpty(String val) {
        return val != null && !val.isEmpty();
    }
}
