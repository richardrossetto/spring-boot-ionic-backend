package com.richard.cursomc.filters;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Richard Rossetto on 9/22/18.
 */
@Component
public class HeaderExposureFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {  }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.addHeader("access-control-expose-headers", "location");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() { }
}
