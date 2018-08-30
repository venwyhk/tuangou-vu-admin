package org.sulei.vu;

import javax.servlet.Servlet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;

import com.ikasoa.core.thrift.server.ThriftServlet;
import com.ikasoa.rpc.RpcException;
import com.ikasoa.springboot.autoconfigure.ServerAutoConfiguration;

@Configuration
public class AutoConfig extends ServerAutoConfiguration {
	
	@Bean("/vu/admin.do")
	public Servlet getServlet() throws RpcException {
		return new ThriftServlet(getServer(getIkasoaFactoryFactory().getIkasoaServletFactory(), getImplWrapperList()));
	}

	@Bean("servletHandlerAdapter")
	public HandlerAdapter getHandlerAdapter() {
		return new SimpleServletHandlerAdapter();
	}

}
