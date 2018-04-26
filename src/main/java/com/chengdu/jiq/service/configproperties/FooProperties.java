package com.chengdu.jiq.service.configproperties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jiyiqin on 2017/11/25.
 * 下面这个POJO定义了如下几个属性：
 foo.enabled, false by default
 foo.remote-address, with a type that can be coerced from String
 foo.security.username, with a nested "security" whose name is determined by the name of the property. In particular the return type is not used at all there and could have been SecurityProperties
 foo.security.password
 foo.security.roles, with a collection of String
 */
@ConfigurationProperties("foo")
public class FooProperties {
    private boolean enabled;
    private InetAddress remoteAddress;
    private final Security security = new Security();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public InetAddress getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(InetAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public Security getSecurity() {
        return security;
    }

    public static class Security {
        private String username;
        private String password;
        private List<String> roles = new ArrayList<>(Collections.singleton("USER"));

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }
}