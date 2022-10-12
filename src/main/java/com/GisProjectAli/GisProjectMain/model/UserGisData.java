package com.GisProjectAli.GisProjectMain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_gis_data")
public class UserGisData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gisDataId")
    private Long gisDataId;

    @Column(columnDefinition="TEXT")
    private String gisData;

    private Long userId;

    @Override
    public String toString() {
        return "UserGisData{" +
                "gisDataId=" + gisDataId +
                '}';
    }
}
