package com.vha.techdev.api.impl;

import org.apache.catalina.Context;
import org.apache.catalina.deploy.ApplicationParameter;
import org.apache.catalina.startup.Tomcat;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.context.ContextLoaderListener;

public class TomcatHandler {
    public final Tomcat tomcat;
    public int port;

    public static final String REST_SERVICES_PATH = "foo";
    public static final String SPRING_CONFIG_LOCATION = "classpath*:META-INF/spring-context.xml";

    public TomcatHandler() throws Exception {
        tomcat = new Tomcat();
        tomcat.setBaseDir(System.getProperty("java.io.tmpdir"));
        tomcat.setPort(0);

        Context context = tomcat.addContext("", System.getProperty("java.io.tmpdir"));

        ApplicationParameter applicationParameter = new ApplicationParameter();
        applicationParameter.setName("contextConfigLocation");
        applicationParameter.setValue(getSpringConfigLocation());
        context.addApplicationParameter(applicationParameter);

        context.addApplicationListener(ContextLoaderListener.class.getName());

        Tomcat.addServlet(context, "cxf", new CXFServlet());
        context.addServletMapping("/" + getRestServicesPath() + "/*", "cxf");

        tomcat.start();

        port = tomcat.getConnector().getLocalPort();

        System.out.println("Tomcat started on port:" + port);
    }

    public String getRestServicesPath() {
        return REST_SERVICES_PATH;
    }

    public String getSpringConfigLocation() {
        return SPRING_CONFIG_LOCATION;
    }

    public void shutdown() throws Exception {
        tomcat.stop();
    }
}
