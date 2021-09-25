package com.maxqiu.demo;

import com.jfinal.core.Controller;

/**
 * 首页
 *
 * @author Max_Qiu
 */
public class IndexController extends Controller {
    public void index() {
        render("index.html");
    }

    public void test1() {
        render("test.html");
    }
}
