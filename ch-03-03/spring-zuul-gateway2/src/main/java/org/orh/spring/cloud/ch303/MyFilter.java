package org.orh.spring.cloud.ch303;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

public class MyFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        System.out.println("get:" + ctx.get("ctx-key"));
        ctx.set("ctx-key", "test-value");
        System.out.println("shouldFilter url:" + request.getRequestURI());
        // 根据判断逻辑是否执行
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        System.out.println(ctx.get("ctx-key"));
        System.out.println("执行自定义过滤器");
        return null;
    }
}
