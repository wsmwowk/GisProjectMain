package com.GisProjectAli.GisProjectMain.controller;

import com.GisProjectAli.GisProjectMain.model.CurrentUser;
import com.GisProjectAli.GisProjectMain.model.UserGisData;
import com.GisProjectAli.GisProjectMain.model.UserLogin;
import com.GisProjectAli.GisProjectMain.model.UsersArea;
import com.GisProjectAli.GisProjectMain.services.UserGisDataServiceImpl;
import com.GisProjectAli.GisProjectMain.services.UserLoginServiceImpl;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * هنا كلاس السوبرفايسر
 * السوبرفايسر او الادمن اله صفحة خاصة شغلهة بس تحدد المساحات مال يوزرية حته يكدرون يشتغلون لان اذا
 * ما عدهم مساحة ما تفتح صفحتهم
 */
@Controller
@AllArgsConstructor
public class SuperVisorController {

    @Resource(name = "sessionValidation")
    CurrentUser usr;

    private final UserLoginServiceImpl loginService;
    private final UserGisDataServiceImpl userGisDataService;

    @GetMapping("/supervisorindex")
    public String superVisorIndex(Model model) {
        if (usr.getUserName() == null) {
            model.addAttribute("checkUser", new UserLogin());
            return "login";
        } else {
            if (Objects.equals(usr.getRole(), "SUPERVISOR")) {
                return "superVisorIndex";
            } else
                return "errors/error-403";
        }
    }

    // هاي الدالة تجيب اليوزرية من الداتابيس حته نحددلهم مساحات
    @RequestMapping("/getUsersFromDb")
    @ResponseBody
    public List<UserLogin> getUsersFromDb() {
        return loginService.getAllUsers().stream().filter(e -> e.getRole().equals("USER")).collect(Collectors.toList());
    }

    // هاي دالة حفظ المساحة مال يوزر المحددة بالداتابيس
    @PostMapping(value = "/updateUserBounds")
    public @ResponseBody
    void updateUserBound(@RequestBody String layer) {
        try {
            if (layer != null){
                UsersArea data = new Gson().fromJson(layer, UsersArea.class);
                UserLogin userToUpdate = loginService.findUserLoginByUserId(data.getUserId());
                userToUpdate.setUserBoundaries(data.getUserArea());
                loginService.saveNewLoginUser(userToUpdate);

                // اذا اليوزر عنده مساحة سابقة وبيهة بيانات احذفهن وسوي المساحة الجديدة
                if ( userGisDataService.findUserGisDataByUserId(userToUpdate.getUserId()) != null){
                    UserGisData clearGisDataModel =  userGisDataService.findUserGisDataByUserId(userToUpdate.getUserId());
                    clearGisDataModel.setGisData("empty");
                    userGisDataService.saveNewUserGisData(clearGisDataModel);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
