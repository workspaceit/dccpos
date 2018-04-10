package com.workspaceit.pos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by anik on 12/18/17.
 */
@Component
public class Environment {

    @Value("${jdbc.user}")
    private String jdbcUser;
    @Value("${jdbc.password}")
    private String jdbcPassword;
    @Value("${jdbc.driverClassName}")
    private String jdbcDriverClassName;
    @Value("${jdbc.url}")
    private String url;
    @Value("${hibernate.dialect}")
    private String hibernateDialect;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddl;
    //File Path
    @Value("${file.tmp.path}")
    private String tmpFilePath;

    @Value("${file.common.path}")
    private String commonFilePath;

    @Value("${oauth.ClientId}")
    private String oauthClientId;

    @Value("${oauth.secret}")
    private String oauthSecret;

    /**
     * Email configuration
     * */
    @Value("${mail.driver}")
    private String mailDriver;

    @Value("${mail.host}")
    private String mailHost;

    @Value("${mail.port}")
    private String mailPort;

    @Value("${mail.username}")
    private String mailUsername;

    @Value("${mail.password}")
    private String mailPassword;

    @Value("${mail.sender.email}")
    private String mailSenderEmail;

    @Value("${mail.sender.password}")
    private String mailSenderPassword;

    @Value("${mail.server.link}")
    private String mailServerLink;



    public String getHbm2ddl() {
        return hbm2ddl;
    }

    public String getJdbcUser() {
        return jdbcUser;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public String getJdbcDriverClassName() {
        return jdbcDriverClassName;
    }

    public String getUrl() {
        return url;
    }

    public String getHibernateDialect() {
        return hibernateDialect;
    }

	public String getTmpFilePath() {
		return tmpFilePath;
	}


    public String getCommonFilePath() {
        return commonFilePath;
    }

    public void setCommonFilePath(String commonFilePath) {
        this.commonFilePath = commonFilePath;
    }

    public String getMailDriver() {
        return mailDriver;
    }

    public String getMailHost() {
        return mailHost;
    }

    public String getMailPort() {
        return mailPort;
    }

    public String getMailUsername() {
        return mailUsername;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public String getMailSenderEmail() {
        return mailSenderEmail;
    }

    public String getMailSenderPassword() {
        return mailSenderPassword;
    }

    public String getMailServerLink() {
        return mailServerLink;
    }

    public String getOauthClientId() {
        return oauthClientId;
    }

    public String getOauthSecret() {
        return oauthSecret;
    }
}