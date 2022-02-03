package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.cfg.Configuration;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Admin;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.impl.DBServiceAdminImpl;
import ru.otus.crm.service.impl.DbServiceClientImpl;
import ru.otus.web.server.ClientsWebServer;
import ru.otus.web.server.ClientsWebServerWithFilterBasedSecurity;
import ru.otus.web.service.AdminAuthService;
import ru.otus.web.service.AdminAuthServiceImpl;
import ru.otus.web.service.TemplateProcessor;
import ru.otus.web.service.TemplateProcessorImpl;

public class WebServerWithFilterBasedSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration,
                Client.class, Address.class, Phone.class, Admin.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);

        var dbServiceAdmin = new DBServiceAdminImpl(new DataTemplateHibernate<>(Admin.class), transactionManager);
        var dbServiceClient = new DbServiceClientImpl(new DataTemplateHibernate<>(Client.class), transactionManager);


        Gson gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        AdminAuthService authService = new AdminAuthServiceImpl(dbServiceAdmin);

        ClientsWebServer clientsWebServer = new ClientsWebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, dbServiceClient, gson, templateProcessor);

        clientsWebServer.start();
        clientsWebServer.join();
    }
}
