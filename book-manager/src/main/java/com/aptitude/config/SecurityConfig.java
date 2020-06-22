/*
package com.aptitude.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration      //  定义为配置类
@EnableWebSecurity  //  启用
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    //  创建放到spring容器中
    @Bean
    public PasswordEncoder getPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                                            //  拦截更目录及其下的所有子目录
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/**")
                //  对资源做完整的请求拦截
                .fullyAuthenticated()
                .and()
                .formLogin()   //表单认证，默认方式
                .loginPage("/login")
                .and()
                .csrf().disable();
    }

}
*/
