package com.GisProjectAli.GisProjectMain.services;

import com.GisProjectAli.GisProjectMain.model.UserLogin;
import com.GisProjectAli.GisProjectMain.repo.UserLoginRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserLoginRepo userLoginRepo;

    @Override
    public UserLogin findUserLoginByUserName(String username) {
        for (UserLogin user : userLoginRepo.findAll()) {
            if (user.getUserName().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void saveNewLoginUser(UserLogin user) {
        userLoginRepo.save(user);
    }

    @Override
    public UserLogin findUserLoginByUserId(Long userId) {
        return
                userLoginRepo.findUserLoginByUserId(userId);
    }

    @Override
    public List<UserLogin> getAllUsers() {
        return userLoginRepo.findAll();
    }
}
