package com.GisProjectAli.GisProjectMain.controller;

import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.encoder.GSResourceEncoder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

/**
 *  هذا الكلاس للارتباط بالجيوسيرفر وحفظ صورة السلوب بي
 */
@Getter
@Setter
@AllArgsConstructor
public class GeoServerConnection {

    private String geoServerUrl;
    private String geoServerUser;
    private String geoServerPassword;

    /**
     * هنا عرفت الرابط واليوزر والباسسورد
     * هنا ذني نفسهن الافتراضيات مال جيوسيرفر يعني اذا ما متغيرات يبقن نفسهن ويتغير بس الرابط والبورت
     */
    public GeoServerConnection() {
        geoServerUrl = "http://localhost:8070/geoserver/";
        geoServerUser = "admin";
        geoServerPassword = "geoserver";
    }

    /**
     * هاي الدالة الي تضيف للجيو سيرفر تاخذ ملف واسم اللاير الي راح ينخزن بالجيوسيرفر
     * @param file هذا الملف نعبره الهة من الدالة مال سلوب
     * @param layerName وهذا الأسم الي نريده ولازم يكون مختلف كل مرة حته ما تشبج ع البرنامج ويجيبلك غير سلوب
     */
    public void addTiffStore(File file, String layerName) {
        try {
            GeoServerRESTPublisher geoServerRESTPublisher = new GeoServerRESTPublisher(getGeoServerUrl(), getGeoServerUser(), getGeoServerPassword());
            geoServerRESTPublisher.publishGeoTIFF("cite", getGeoServerUser(),
                    layerName, file,"4326",GSResourceEncoder.ProjectionPolicy.NONE
                    , "dem",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}