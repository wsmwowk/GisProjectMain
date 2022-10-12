package com.GisProjectAli.GisProjectMain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//مودل بسيط ع مود  نجيب جيسون من الجافا سكربت ونتعامل ويا بالجافا
// ملاحظة هذا كلاس عادي مو جدول للداتابيس
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersArea {
    private Long userId;

    private String userArea;

    @Override
    public String toString() {
        return "UsersBounds{" +
                "userId='" + userId + '\'' +
                ", user_boundaries='" + userArea + '\'' +
                '}';
    }
}
