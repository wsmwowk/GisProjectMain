package com.GisProjectAli.GisProjectMain.services;

import com.GisProjectAli.GisProjectMain.model.UserLogin;

import java.util.List;

public interface UserLoginService {
     // جلب اليوزر باستخدام اليوزرنيم
    UserLogin findUserLoginByUserName(String username);
     //تحديث اليوزر الحالي
    void saveNewLoginUser(UserLogin user);
     // جلب اليوزر باستخدام الأيدي
    UserLogin findUserLoginByUserId(Long username);
     // جلب كافة اليوزرات
    List<UserLogin> getAllUsers();
}
