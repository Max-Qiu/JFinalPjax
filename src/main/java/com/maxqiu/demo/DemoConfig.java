package com.maxqiu.demo;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;

/**
 * 启动类
 *
 * @author Max_Qiu
 */
public class DemoConfig extends JFinalConfig {
    public static void main(String[] args) {
        UndertowServer.start(DemoConfig.class);
    }

    @Override
    public void configConstant(Constants me) {
        // 开发者模式
        me.setDevMode(true);
        // 设置 devMode 之下的 action report 是否在 invocation 之后
        me.setReportAfterInvocation(false);
    }

    @Override
    public void configRoute(Routes me) {
        // 设置默认资源路径
        me.setBaseViewPath("/WEB-INF");
        me.add("/", IndexController.class);
    }

    @Override
    public void configEngine(Engine me) {
        me.setDevMode(true);
        me.addSharedFunction("/WEB-INF/layout.html");
    }

    @Override
    public void configPlugin(Plugins me) {}

    @Override
    public void configInterceptor(Interceptors me) {
        // 全局pjax拦截器
        me.add(new PjaxInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {}
}
