package com.maxqiu.demo;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * 设置 pjax 标志
 *
 * 本拦截器代码参照JFinal俱乐部的jfinal-club项目
 *
 * @author Max_Qiu
 */
public class PjaxInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        try {
            inv.invoke();
        } finally {
            Controller c = inv.getController();
            boolean isPjax = "true".equalsIgnoreCase(c.getHeader("X-PJAX"));
            c.setAttr("isPjax", isPjax);
        }
    }
}
