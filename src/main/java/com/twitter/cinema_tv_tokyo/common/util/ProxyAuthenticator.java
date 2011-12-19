package com.twitter.cinema_tv_tokyo.common.util;

import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 
 * @author ITO Yoshiichi
 */
public class ProxyAuthenticator extends Authenticator {

    private String proxyHost;

    private String proxyPort;

    private String proxyUser;

    private String proxyPassword;

    ProxyAuthenticator() {
        this.proxyHost = getProperty("http.proxyHost");
        this.proxyPort = getProperty("http.proxyPort");
        this.proxyUser = getProperty("http.proxyUser");
        this.proxyPassword = getProperty("http.proxyPassword");
    }

    String getProperty(String key) {
        return StringUtils.trimToNull(System.getProperty(key));
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public String getProxyUser() {
        return proxyUser;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    @Override
    protected URL getRequestingURL() {
        if (proxyHost == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(proxyHost);
        if (proxyPort == null) {
            sb.append(':');
            sb.append(proxyPort);
        }
        try {
            return new URL(sb.toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected RequestorType getRequestorType() {
        return RequestorType.PROXY;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        if (proxyUser == null || proxyPassword == null) {
            return null;
        }
        return new PasswordAuthentication(
                proxyUser, proxyPassword.toCharArray());
    }

    public static final ProxyAuthenticator INSTANCE = new ProxyAuthenticator();

}
