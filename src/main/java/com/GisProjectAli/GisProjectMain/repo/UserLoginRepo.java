package com.GisProjectAli.GisProjectMain.repo;

import com.GisProjectAli.GisProjectMain.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepo extends JpaRepository<UserLogin, Long> {
    UserLogin findUserLoginByUserId(Long userId);
}
