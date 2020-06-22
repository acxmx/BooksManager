/*
package com.aptitude.service;

import com.aptitude.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserDetialServiceImpl implements UserDetailsService {

    //  注入创建到spring容器中的bean
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Admin admin = adminService.findAdminByName(username);

        if (admin == null) {
            return null;
        }

        String passowrd = passwordEncoder.encode(admin.getPassword());
        System.out.println("password:" + passowrd);

        return new User(username, passowrd, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }
}
*/
