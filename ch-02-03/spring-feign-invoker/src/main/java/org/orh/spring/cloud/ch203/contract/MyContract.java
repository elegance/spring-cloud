package org.orh.spring.cloud.ch203.contract;

import feign.Contract;
import feign.MethodMetadata;
import org.springframework.cloud.netflix.feign.support.SpringMvcContract;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MyContract extends SpringMvcContract {

    @Override
    protected void processAnnotationOnMethod(MethodMetadata methodMetadata, Annotation annotation, Method method) {
        super.processAnnotationOnMethod(methodMetadata, annotation, method);

        if (annotation.annotationType().isAssignableFrom(MyUrl.class)) {
            MyUrl myUrl = method.getDeclaredAnnotation(MyUrl.class);
            String httpMethod = myUrl.method();
            String uri = myUrl.uri();

            methodMetadata.template()
                    .method(httpMethod)
                    .append(uri);
        }
    }
}
