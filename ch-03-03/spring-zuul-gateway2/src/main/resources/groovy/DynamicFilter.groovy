package groovy

import com.netflix.zuul.ZuulFilter
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants

class DynamicFilter extends ZuulFilter {
    @Override
    String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }

    @Override
    int filterOrder() {
        return 3
    }

    @Override
    boolean shouldFilter() {
        return true
    }

    @Override
    Object run() {
        println("================= 这是一个动态加载的过滤器： DynamicFilter")
        return null
    }
}
