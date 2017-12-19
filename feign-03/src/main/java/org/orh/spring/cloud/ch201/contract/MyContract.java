package org.orh.spring.cloud.ch201.contract;

import feign.Contract;
import feign.MethodMetadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MyContract extends Contract.BaseContract {

    @Override
    protected void processAnnotationOnMethod(MethodMetadata methodMetadata, Annotation annotation, Method method) {
        if (annotation.annotationType().isAssignableFrom(MyUrl.class)) {
            MyUrl myUrl = method.getDeclaredAnnotation(MyUrl.class);
            String httpMethod = myUrl.method();
            String uri = myUrl.uri();

            methodMetadata.template().method(httpMethod)
                    .append(uri);
        }
    }

    @Override
    protected void processAnnotationOnClass(MethodMetadata methodMetadata, Class<?> aClass) {

    }

    @Override
    protected boolean processAnnotationsOnParameter(MethodMetadata methodMetadata, Annotation[] annotations, int i) {
        return false;
    }
}
