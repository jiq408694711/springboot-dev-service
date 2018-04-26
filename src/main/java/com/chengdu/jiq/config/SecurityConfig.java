package com.chengdu.jiq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/main/**").hasAnyRole("ADMIN", "USER") //ADMIN和USER角色用户可访问main模块
                .antMatchers("/user/**").hasRole("USER")    //USER角色可访问user模块
                .antMatchers("/fileupload/**").hasRole("USER")    //USER角色可访问fileupload模块
                .and()
                .formLogin()
                .loginPage("/login")    //没有权限时默认导向的登录页面
                .defaultSuccessUrl("/main/home")    //登录成功后默认导向的页面
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")   //退出系统
                .permitAll()
        .and().csrf().disable();//"Invalid CSRF Token 'null' was found on the request parameter '_csrf' or header 'X-CSRF-TOKEN'.",
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("jiyiqin").password("345678").roles("ADMIN");
        auth.inMemoryAuthentication()
                .withUser("jiyiqin2").password("345678").roles("USER");
//        auth.jdbcAuthentication().
    }
}
