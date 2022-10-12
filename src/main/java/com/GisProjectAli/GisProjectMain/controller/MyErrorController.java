package com.GisProjectAli.GisProjectMain.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * هذا الكلاس مال اخطاء المحتمل تطلع للمستخدم كل خطأ مسويله صفحة خاصة
 * الاخطاء مال ويب معروفة, كل خطأ اله كود لذلك مثلا اذا الكود مال خطا 404 وجه المستخدم لصفحة الخطأ 404 وهكذا
 */
@Controller
public class MyErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        String errorPage = "error";

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                errorPage = "errors/error-404";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                errorPage = "errors/error-403";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                errorPage = "errors/error-500";
            }
        }

        return errorPage;
    }
}
