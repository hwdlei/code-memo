package zx.soft.spring.demo.lifecycle;

public class BeanLifecycle {
	/**
	 * Bean的生命周期
	 *   定义
	 *   初始化
	 *      1.  实现org.springframework.beans.factory.InitializingBean接口，
	 *          覆盖afterPropertiesSet方法
	 *      2.  配置xml中init-method
	 *   使用
	 *   销毁
	 *      1.  实现org.springframework.beans.factory.DisposableBean接口，
	 *          覆盖destroy方法
	 *      2.  配置xml中destory-method
	 *
	 *  以上是针对具体的bean的
	 *  配置全局的初始化与销毁方法：
	 *  	在spring配置文件中beans添加default-init-method  和  default-destory-method
	 *
	 *  优先级： 实现接口  > 配置xml（Bean种方法必须） > 全局 （Bean中方法可选）
	 */
	public void start() {
		System.out.println("BeanLifeCycle : init");
	}

	public void finish() {
		System.out.println("BeanLifeCycle : stop");

	}
}
