package com.GisProjectAli.GisProjectMain.controller;

import com.GisProjectAli.GisProjectMain.model.CurrentUser;
import com.GisProjectAli.GisProjectMain.model.UserLogin;
import com.GisProjectAli.GisProjectMain.services.UserLoginServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * كلاس اليوزر
 * هنا دوال تسجيل مستخدم جديد او تسجيل الدخول او الخروج
 */
@Controller
@AllArgsConstructor
public class UserController {

    private final UserLoginServiceImpl loginService;

    @Resource(name = "sessionValidation")
    CurrentUser usr;

    // الدوال التلاثة الجوة بس ع مود الصفحة الرئيسية
    @RequestMapping("/*")
    public String welcomePage(Model model) {
        if (usr.getUserName() != null && checkSessionValidation()) {
            if (usr.getRole().equals("SUPERVISOR")) {
                return "superVisorIndex";
            }
            return "index";
        } else {
            model.addAttribute("checkUser", new UserLogin());
            return "login";
        }
    }

    //

    // فحص اليوزر والباسورد
    @RequestMapping(value = "/login")
    public String doLogin(@ModelAttribute("checkUser") UserLogin checkUser, Model model) {
        if (usr.getUserName() == null) {
            UserLogin userValidation = loginService.findUserLoginByUserName(checkUser.getUserName());
            // اذا اليوزر المكتوب ما موجود لا تسوي شي
            if (userValidation == null) return "login";
                // اذا اليوزر موجود افحص الباسورد مالته
            else if (userValidation.getUserName().equals(checkUser.getUserName()) && userValidation.getUserPass().equals(checkUser.getUserPass())) {
                if (userValidation.getRole().equals("SUPERVISOR")) {
//                model.addAttribute("allUsers", loginService.getAllUsers());
                    usr.setUserId(userValidation.getUserId());
                    usr.setUserName(userValidation.getUserName());
                    usr.setRole(userValidation.getRole());
                    return "superVisorIndex";
                } else {
                    if (userValidation.getUserBoundaries().equals("empty")) {
                        model.addAttribute("checkUser", new UserLogin());
                        return "login";
                    } else {
                        usr.setUserId(userValidation.getUserId());
                        usr.setUserName(userValidation.getUserName());
                        usr.setRole(userValidation.getRole());
                        return "index";
                    }
                }
                // اذا اليوزر صلاحيته سوبرفايسر وديني لصفحة السوبرفايس
                // اذا يوزر عادي شوف المساحة مالته اذا عنده دخله اذا ما عنده لا تدخله
            } else {
                model.addAttribute("checkUser", new UserLogin());
                return "login";
            }
        } else if (usr.getRole().equals("SUPERVISOR"))
            return "superVisorIndex";
        else
            return "index";
    }

    // اذا دخلنه ع رابط تسجيل الدخول واحنه اصلا مسجلين  دخول شوف اليوزر شنو صلاحيته ووجه للصفحة مالته
    // Need to fix bug, we don`t have to use multiple functions for one function implementation
    // this is the best way to call multiple requests
//    @RequestMapping(value = "/page/**", method = RequestMethod.GET)
//    public String checkLoginSession2(Model model) {
//        return getString(model);
//    }

    //  اذا دسنه ع تسجيل خروج انهي السشن وسجل خروج
    @RequestMapping(value = "/do-logout", method = RequestMethod.GET)
    public String doLogout(Model model) {
        usr.setUserName(null);
        usr.setUserId(null);
        usr.setRole(null);
        model.addAttribute("checkUser", new UserLogin());
        return "login";
    }

    // دالة تودي لصفحة التسجيل
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String getSignupPage(Model model) {
        model.addAttribute("newUserModel", new UserLogin());
        return "signup";
    }

    // دالة تجيب الايدي مال يوزر الحالي
    @GetMapping("/getCurrentUserId")
    @ResponseBody
    public String getCurrentUserId() {
        return usr.getUserId().toString();
    }

    // تسجيل يوزر جديد ياخذ البيانات من صفحة الjsp ويضيفهن ك مودل
    // يخلي الصلاحية USER والمساحة الخاصة فارغة بالوضع الافتراضي حته يحددهة السوبر فايسر
    @RequestMapping(value = "/create-user", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("newUserModel") UserLogin newUser, Model model) {
        if (newUser != null) {
            if (loginService.findUserLoginByUserName(newUser.getUserName()) == null && !newUser.getUserColor().equals("")) {
                UserLogin loginUser = new UserLogin();
                loginUser.setUserName(newUser.getUserName());
                loginUser.setUserPass(newUser.getUserPass());
                loginUser.setUserColor(newUser.getUserColor());
                loginUser.setRole("USER");
                loginUser.setUserFullName(newUser.getUserFullName());
                loginUser.setUserBoundaries("empty");
                loginService.saveNewLoginUser(loginUser);

                System.out.println("Successfully added a new user");
                model.addAttribute("checkUser", new UserLogin());
                return "login";
            } else {
                System.out.println("User already exist");
                return "signup";
            }
        } else {
            System.out.println("Error");
            return "signup";
        }
    }

    // اذا اليوزر الموجود بالسيشن موجود بالداتابيس اوكي الوضع
    public boolean checkSessionValidation() {
        return loginService.findUserLoginByUserName(usr.getUserName()) != null;
    }

}
