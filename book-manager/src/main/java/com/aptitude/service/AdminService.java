package com.aptitude.service;

import com.aptitude.domain.Admin;

public interface AdminService {

    Admin findAdminByName (String username, String password);

}
