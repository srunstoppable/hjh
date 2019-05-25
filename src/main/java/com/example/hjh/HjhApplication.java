package com.example.hjh;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HjhApplication {

    public static void main(String[] args) {
        SpringApplication.run(HjhApplication.class, args);
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
                TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
                tomcat.addAdditionalTomcatConnectors(createStandardConnector()); // 添加http
                return tomcat;
            }

             // 配置http
           private Connector createStandardConnector() {
                 Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
                connector.setPort(7777);
               return connector;
           }
}
