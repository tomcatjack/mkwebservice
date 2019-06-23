package com.greatonce.mkwebservice.config;

import com.greatonce.mkwebservice.service.DefaultService;
import com.greatonce.mkwebservice.service.impl.DefaultServiceImpl;
import javax.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @sum ：
 * @description：
 * @date ：Created in 2019/6/20
 */
@Configuration
public class CxfConfig {

  @Bean
  public ServletRegistrationBean dispatcherServlet() {
    return new ServletRegistrationBean(new CXFServlet(),"/mk/*");
  }
  @Bean(name = Bus.DEFAULT_BUS_ID)
  public SpringBus springBus() {
    return new SpringBus();
  }
  @Bean
  public DefaultService demoService() {
    return new DefaultServiceImpl();
  }

  @Bean
  public Endpoint endpoint() {
    EndpointImpl endpoint = new EndpointImpl(springBus(), demoService());
    endpoint.publish("/api");
    return endpoint;
  }

}
