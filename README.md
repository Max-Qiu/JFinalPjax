导入开发工具，运行`DemoConfig.java`内的`main`方法，然后浏览器访问`127.0.0.1`

# 编写一个pjax拦截器

> 本拦截器代码参照`JFinal俱乐部`的`jfinal-club`项目

```java
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
```

# 建一个测试controller

```java
public class IndexController extends Controller {
    public void index() {
        render("index.html");
    }

    public void test1() {
        render("test1.html");
    }
}
```

# 添加拦截器

如果要所有的页面都需要进行拦截，则添加到`configInterceptor`中

```java
public void configRoute(Routes me) {
	// 设置默认资源路径
	me.setBaseViewPath("/WEB-INF");
	me.add("/", IndexController.class);
}
public void configInterceptor(Interceptors me) {
	// 全局pjax拦截器
	me.add(new PjaxInterceptor());
}
```

如果仅对部分页面（即单个`route`）进行拦截，则添加到`configRoute`中

```java
public void configRoute(Routes me) {
	// 当前路由添加pjax拦截器
	me.addInterceptor(new PjaxInterceptor());
	// 设置默认资源路径
	me.setBaseViewPath("/WEB-INF");
	me.add("/", IndexController.class);
}
```

# 建一个公用页面

```html
#-- 如果是 pjax 请求则只调用 main 函数 否则调用 doLayout 函数 --#
#define layout()
	#if(isPjax)
		#@main()
	#else
		#@doLayout()
	#end
#end
#-- 主页面 --#
#define doLayout()
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<title>JFinalPjaxDemo</title>
		<link rel="stylesheet" type="text/css" href="http://cdn.staticfile.org/nprogress/0.2.0/nprogress.min.css" />
		<script src="http://cdn.staticfile.org/jquery/1.12.4/jquery.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="http://cdn.staticfile.org/jquery.pjax/2.0.1/jquery.pjax.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="http://cdn.staticfile.org/nprogress/0.2.0/nprogress.min.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			/**
			 * 开启pjax
			 */
			$(document).pjax('[data-pjax] a, a[data-pjax]', '#pjax-container', {
				/*pjax等待时间，单位ms，0为一直等待（可选项）*/
				timeout: 0,
				/*禁用缓存（可选项）*/
				cache: false
			});
			/**
			 * 关闭NProgress右上角的小圆圈（可选项）
			 */
			NProgress.configure({
				showSpinner: false
			});
			/**
			 * pjax开启与结束时使用nprogress特效
			 */
			$(document).on('pjax:start', function() {
				NProgress.start();
			}).on('pjax:end', function() {
				NProgress.done();
			});
			/**
			 * 使用pjax加载页面
			 * 其他地方需要无刷新变更页面时，调用此方法，传入URL即可
			 * @param {Object} url 需要跳转的URL
			 */
			function jumpByPjax(url) {
				$.pjax({
					url: url,
					container: '#pjax-container'
				})
			}
		</script>
	</head>

	<body>
		<!-- 菜单demo -->
		<ul>
			<li>
				<a data-pjax href="index">index</a>
			</li>
			<li>
				<a data-pjax href="test1">test1</a>
			</li>
		</ul>
		<!-- 主要内容 -->
		<div id="pjax-container">
			#@main()
		</div>
	</body>

</html>
#end
```

# 建立测试页面

index.html

```html
#@layout()
#define main()
<div>
	这个是index页面
</div>
#end
```

test1.html

```html
#@layout()
#define main()
<div>
	这个是test1页面
</div>
#end
```

# 跑起来测试

![](https://cdn.maxqiu.com/upload/d58b900b332f4d94b1e8e3f0300f09e4.jpg)

![](https://cdn.maxqiu.com/upload/65e5bacea5d0480d97ea64b8f8a2270f.jpg)

***完毕，收工***

# 附：通过重构render实现方法

参照：[http://www.jfinal.com/share/228](http://www.jfinal.com/share/228)