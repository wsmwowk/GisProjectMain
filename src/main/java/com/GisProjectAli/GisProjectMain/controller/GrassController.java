package com.GisProjectAli.GisProjectMain.controller;

import com.GisProjectAli.GisProjectMain.model.Slope;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * هذا الكلاس مال سلوب يسوي ملف الcmd مال سلوب
 * يكتب الاوامر مال سلوب بالملف وينفذهن عند الطلب
 * ملاحظة: الاوامر هاي مال نظام الماك لازم اذا وندوز تتغير شكم شغلة
 */
@Getter
@Setter
@NoArgsConstructor
@Controller
public class GrassController {

    private String grassAnalysis;
    private String grassAddress;
    private String grassProject;
    private String fileName;

    // هاي الدالة الي تسوي ملف الاوامر مال كراس للماك
    // طبعا هنا ماخذ البيانات من الجافا سكربت بصيغة جيسون حته نعرف اسم اللاير والحدود مال سلوب
    @PostMapping("/setSlopeInfo")
    public @ResponseBody
    void setSlopeInfo(@RequestBody String slope) {
        try {
            // Gson هاي مكتبة خاصة تجيب جيسون من الجافا سكربت وتحوله مثل كلاس جافا الي عدنه
            Slope slopeData = new Gson().fromJson(slope, Slope.class);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss", Locale.getDefault());
            String slopeName = "slope-" + sdf.format(new Date());
            setFileName(slopeData.getSlopeTitle() + "-" + slopeName);
            String[] regionData = slopeData.getSlopeRegion();
            String region = "g.region" + " n=" + regionData[0] + " e=" + regionData[1] + " s=" + regionData[2] + " w=" + regionData[3];
            String slopeCommand = " r.slope.aspect" + " elevation=iqNewDem" + " slope=" + slopeName + " format=" + slopeData.getSlopeType();
            //  الاوامر الجوة ما مطلوبة بس عند الحاجة نلغي الكومنت ونضيف اسم الامر جوة وية الcommands المصفوفة الي جوة
            //        String colorCommand = " r.colors map="+ slopeName +" rules="+grassAnalysis + "rule.txt ";
//        String slopeReclass = " r.reclass --overwrite input=" + slopeName + " output=reClassedSlope " + " rules=" + grassAnalysis + "rule.txt";
//        String slopeToVec = " r.to.vect -s --overwrite --verbose input="+ slopeName + " output=newVector"+ " type=area";
//        String vecToGeoJson = " v.out.ogr -c --overwrite --verbose input=newVector" + " type=area " + " output=" + grassAnalysis + slopeName + "Gj.geoJson " + "format=GeoJSON";
            String exportGeoTiff = " r.out.gdal -c " + "input=" + slopeName + " output=" + grassAnalysis + slopeName + ".tif";
            String[] commands = {"#!/bin/bash", region, slopeCommand, exportGeoTiff};
            writeRunFile(grassAnalysis + "run.sh", commands);
            runSlopeCommand(grassAddress + "Grass.sh --text " + grassProject + "PERMANENT --exec sh " + grassAnalysis + "run.sh");
            GeoServerConnection geoServerConnection = new GeoServerConnection();
            geoServerConnection.addTiffStore(new File(grassAnalysis + File.separator + slopeName + ".tif"), fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // هنا نعبر اسم اللاير للجافا سكربت حته يعرض اللاير ع الخريطة
    @GetMapping("/CurrentSlopeFileName")
    @ResponseBody
    public String CurrentSlopeFileName() {
        return getFileName();
    }

    // تنفيذ كود الملف الي سوينا مال اوامر الكراس
    public static void runSlopeCommand(String request) {
        Runtime rt = Runtime.getRuntime();
        Process pr;
        try {
            pr = rt.exec(request);
            while (isAlive(pr)) {
            }
            System.out.println("Process completed successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ينتظر العملية مال تحليل السلوب ع ما ينتهي
    public static boolean isAlive(Process p) {
        try {
            assert p != null;
            p.waitFor();
            p.exitValue();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // نسوي الملف مال اوامر بالمكان الي نريد نحفظه بي
    public void writeRunFile(String path, String[] commands) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for (String command : commands) {
                bw.write(command + "\n");
            }
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * الدوال  الي جوة شغلهة تجيب البيانات من ملف ال application.properties مال برنامج
     */

    @Autowired
    public void setGrassAnalysis(@Value("${grassAnalysis}") String grassAnalysis) {
        this.grassAnalysis = grassAnalysis;
    }

    @Autowired
    public void setGrassAddress(@Value("${grassAddress}") String grassAddress) {
        this.grassAddress = grassAddress;
    }

    @Autowired
    public void setGrassProject(@Value("${grassProject}") String grassProject) {
        this.grassProject = grassProject;
    }
}
