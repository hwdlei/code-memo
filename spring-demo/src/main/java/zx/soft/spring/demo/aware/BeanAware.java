package zx.soft.spring.demo.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring提供Aware结尾的接口，实现Aware接口的Bean，初始化后可以获得相应资源(自动由相应的方法传入)，
 * 并对其操作
 * 为Spring的简单扩展提供方便的入口
 * 如：
 * 		ApplicationContextAware
 * 		BeanNameAware
 */
public class BeanAware implements ApplicationContextAware, BeanNameAware {
	private String beanName;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		System.out.println(applicationContext.getBean(this.beanName));
	}

	@Override
	public void setBeanName(String arg0) {
		// TODO Auto-generated method stub
		this.beanName = arg0;
	}

}
