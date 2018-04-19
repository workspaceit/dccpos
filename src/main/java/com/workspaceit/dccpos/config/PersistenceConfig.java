package com.workspaceit.dccpos.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
public class PersistenceConfig {

    @Autowired
    private Environment env;
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(restDataSource());
        sessionFactory.setPackagesToScan(
                new String[] { "com.workspaceit.dccpos.entity"});
        sessionFactory.setHibernateProperties(hibernateProperties());
//        sessionFactory.setAnnotatedPackages(new String[] {"com.workspaceit.pos.entity"});
        return sessionFactory;
    }

    @Bean
    public DataSource restDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getJdbcDriverClassName());
        dataSource.setUrl(env.getUrl());
        dataSource.setUsername(env.getJdbcUser());
        dataSource.setPassword(env.getJdbcPassword());
        return dataSource;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    @Bean                       
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties hibernateProperties() {
        return new Properties() {
            {

                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                setProperty("hibernate.globally_quoted_identifiers","true");
                setProperty("hibernate.jdbc.batch_size","10");
               /* setProperty("org.hibernate.envers.audit_table_prefix", "history_");
                setProperty("org.hibernate.envers.audit_table_suffix", "");*/
    /*            setProperty("hibernate.cache.use_second_level_cache","true");
                setProperty("hibernate.cache.region.factory_class","org.hibernate.cache.ehcache.EhCacheRegionFactory");*/
/*                setProperty("hibernate.show_sql","true");
                setProperty("hibernate.format_sql","true");
                setProperty("hibernate.use_sql_comments","true");*/

                /**
                 * Be extra careful with this configuration
                 * Could cause data damage
                 * */
               // setProperty("hibernate.hbm2ddl.auto", "update");
            }
        };
    }
}