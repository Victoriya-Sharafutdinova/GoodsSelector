package com.example.GoodsSelector.services;

import com.example.GoodsSelector.models.HistoryModel;
import com.example.GoodsSelector.models.UserModel;
import com.example.GoodsSelector.models.UserTokenModel;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public interface IUserService {
    public UserTokenModel auth(UserModel userModel);

    public UserTokenModel register(UserModel userModel) throws NoSuchAlgorithmException, InvalidKeySpecException;

    public List<HistoryModel> getHistories(UserTokenModel userTokenModel);
}
