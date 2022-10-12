package com.GisProjectAli.GisProjectMain.services;

import com.GisProjectAli.GisProjectMain.model.UserGisData;
import com.GisProjectAli.GisProjectMain.repo.UserGisRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGisDataServiceImpl implements UserGisDataService{

    private final UserGisRepo userGisRepo;

    @Override
    public void saveNewUserGisData(UserGisData data) {
        userGisRepo.save(data);
    }

    @Override
    public void removeUserGisData(UserGisData usr) {
        userGisRepo.delete(usr);
    }

    @Override
    public UserGisData findUserGisDataByUserId(Long userId) {
        return userGisRepo.findUserGisDataByUserId(userId);
    }

}
