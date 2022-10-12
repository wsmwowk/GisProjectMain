package com.GisProjectAli.GisProjectMain.repo;

import com.GisProjectAli.GisProjectMain.model.UserGisData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGisRepo extends JpaRepository<UserGisData, Long> {
    UserGisData findUserGisDataByUserId(Long userId);
}
