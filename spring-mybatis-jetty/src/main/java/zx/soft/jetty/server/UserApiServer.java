package zx.soft.jetty.server;

import java.io.IOException;
import java.util.Properties;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 用户数据接口服务
 * 
 * 接口说明：
 *     1、http://localhost:8111/users/{uid}   POST: User
 *     2、http://localhost:8111/users/{uid}/{mid}   DELETE
 *     3、http://localhost:8111/users/{uid}/{mid}/gender  GET
 * 
 * @author wanggang
 *
 */
public class UserApiServer {

	private static final Logger logger = LoggerFactory.getLogger(UserApiServer.class);

	// 默认端口
	private static final int DEFAULT_PORT = 8080;
	// Context路径
	private static final String CONTEXT_PATH = "/";
	// Mapping路径
	private static final String MAPPING_URL = "/*";

	/**
	 * 主函数
	 */
	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		props.load(UserApiServer.class.getClassLoader().getResourceAsStream("web_server.properties"));
		new UserApiServer().startJetty(Integer.valueOf(props.getProperty("api.port", String.valueOf(DEFAULT_PORT))));
	}

	private static WebApplicationContext getContext() {
		XmlWebApplicationContext context = new XmlWebApplicationContext();
		return context;
	}

	private static ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException {
		ServletContextHandler contextHandler = new ServletContextHandler();
		contextHandler.setErrorHandler(null);
		contextHandler.setContextPath(CONTEXT_PATH);
		contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), MAPPING_URL);
		contextHandler.addEventListener(new ContextLoaderListener(context));
		contextHandler.setResourceBase(new ClassPathResource("webapp").getURI().toString());
		return contextHandler;
	}

	private void startJetty(int port) throws Exception {
		logger.debug("Starting server at port {}", port);
		Server server = new Server(port);
		server.setHandler(getServletContextHandler(getContext()));
		server.start();
		logger.info("Server started at port {}", port);
		server.join();
	}

}
