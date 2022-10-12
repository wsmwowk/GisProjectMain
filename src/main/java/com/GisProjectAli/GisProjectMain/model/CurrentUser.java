package com.GisProjectAli.GisProjectMain.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUser implements Serializable {
    private Long userId;
    private String userName;
    private String role;
}
