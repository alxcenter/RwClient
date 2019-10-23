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



//    @Bean //этот dataSource подходит для работы локально, и соединения идет в один поток. Короче не производительно.
//    public DataSource getDataSource1(){
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUsername("root");
//        dataSource.setPassword("1");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/test1?serverTimezone=UTC");
//        return dataSource;
//    }
}
