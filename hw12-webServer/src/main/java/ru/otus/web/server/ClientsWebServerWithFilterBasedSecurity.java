package ru.otus.web.server;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.crm.service.api.DBServiceClient;
import ru.otus.web.service.AdminAuthService;
import ru.otus.web.service.TemplateProcessor;
import ru.otus.web.servlet.AuthorizationFilter;
import ru.otus.web.servlet.LoginServlet;

import java.util.Arrays;

public class ClientsWebServerWithFilterBasedSecurity extends ClientsWebServerSimple {
    private final AdminAuthService adminAuthService;

    public ClientsWebServerWithFilterBasedSecurity(int port,
                                                   AdminAuthService adminAuthService,
                                                   DBServiceClient dbServiceClient,
                                                   Gson gson,
                                                   TemplateProcessor templateProcessor) {
        super(port, dbServiceClient, gson, templateProcessor);
        this.adminAuthService = adminAuthService;
    }

    @Override
    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, adminAuthService)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }
}
