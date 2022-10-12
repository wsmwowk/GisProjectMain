package com.GisProjectAli.GisProjectMain.services;

import com.GisProjectAli.GisProjectMain.model.UserGisData;

public interface UserGisDataService {

    // حفظ الفيجرات مال مستخدم
    void saveNewUserGisData(UserGisData data);
    // مسح الفيجرات مال مستخدم
    void removeUserGisData(UserGisData usr);
    // جلب الفيجرات مال مستخدم
    UserGisData findUserGisDataByUserId(Long userId);

}
