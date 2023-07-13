package com.bookstore.apigateway.filter;

import com.bookstore.securitymodule.configurations.AppSecurityConfig;
import com.bookstore.securitymodule.util.JwtUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BasicAuthRequestFilter extends ZuulFilter {

    private final JwtUtil jwtUtil;
    private final AppSecurityConfig appSecurityConfig;

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 0; // Set the filter order as needed
    }

    @Override
    public boolean shouldFilter() {
        return true; // Enable the filter for all requests
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        if(appSecurityConfig.getAddBasicAuthHeader()){
            ctx.addZuulRequestHeader("Authorization", appSecurityConfig.getBasicAuthToken());
        }
        if(appSecurityConfig.getIsJwtAuthEnabled()){
            ctx.addZuulRequestHeader("username", jwtUtil.extractUsernameFromRequest(ctx.getRequest()));
        }
        return null;
    }

}
