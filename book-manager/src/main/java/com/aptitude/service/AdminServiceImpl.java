package com.aptitude.service;


import com.aptitude.domain.Admin;
import com.aptitude.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminMapper adminMapper;

    @Transactional(readOnly = true)
    @Override
    public Admin findAdminByName(String username, String passowrd) {
        return adminMapper.findByName(username, passowrd);
    }
}
