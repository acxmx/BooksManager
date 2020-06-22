package com.aptitude.mapper;

import com.aptitude.domain.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {

    Admin findByName (@Param("username") String username, @Param("password") String password);

}
