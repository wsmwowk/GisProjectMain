package com.GisProjectAli.GisProjectMain.controller;

import com.GisProjectAli.GisProjectMain.model.CurrentUser;
import com.GisProjectAli.GisProjectMain.model.UserGisData;
import com.GisProjectAli.GisProjectMain.model.UserLogin;
import com.GisProjectAli.GisProjectMain.services.UserGisDataServiceImpl;
import com.GisProjectAli.GisProjectMain.services.UserLoginServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * هاذا الكلاس  ما بي شي شغله بس يحدث البيانات مال يوزر الجغرافية يعني اذا ضاف لو حرك ماركر او مربع يحفظ هذا
 * التغيير كذلك بي دالة تجيب مساحة اليوزر المخصصه اله
 */
@Controller
@AllArgsConstructor
public class GisDataController {

    private final UserGisDataServiceImpl userGisDataService;
    private final UserLoginServiceImpl loginService;

    @Resource(name = "sessionValidation")
    CurrentUser usr;

    // الدالة الي تحفظ البيانات ورة التحديث
    @PostMapping(value = "/updateUserFeatures")
    public @ResponseBody
    void updateUserFeatures(@RequestBody String layer) {
        try {
            UserGisData gisData = new UserGisData();
            String currentUserName = usr.getUserName();
            UserLogin currentUser = loginService.findUserLoginByUserName(currentUserName);
            gisData.setUserId(currentUser.getUserId());

            if (layer != null && !layer.equals("empty") && !layer.equals("undefined")) {
                gisData.setGisData(layer);
            } else {
                gisData.setGisData("empty");
            }
            if (userGisDataService.findUserGisDataByUserId(currentUser.getUserId()) != null) {
                userGisDataService.removeUserGisData(userGisDataService.findUserGisDataByUserId(currentUser.getUserId()));
            }
            userGisDataService.saveNewUserGisData(gisData);

        } catch (Exception e) {
            e.getStackTrace();
        }
    }


    // جلب المساحة المخصصة للمستخدم الحالي الي مسجل دخول
    @GetMapping("/getCurrentUserBoundsFromDb")
    @ResponseBody
    public String getCurrentUserBoundsFromDb() {
        return loginService.findUserLoginByUserName(
                usr.getUserName()
        ).getUserBoundaries();
    }


}
