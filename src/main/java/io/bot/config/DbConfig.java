package io.bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.sql.DataSource;

@Configuration
@Import(HibernateConfig.class)
@ComponentScan("io.bot.repositories")
public class DbConfig {
    @Bean
    public JndiObjectFactoryBean getDataSource() {
        JndiObjectFactoryBean dataSource = new JndiObjectFactoryBean();
        dataSource.setProxyInterface(DataSource.class);
        dataSource.setJndiName("jdbc/testDB");
        dataSource.setResourceRef(true);
        return dataSource;
    }
}
