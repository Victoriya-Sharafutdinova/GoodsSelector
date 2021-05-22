package com.example.GoodsSelector.services;

import com.example.GoodsSelector.models.UserModel;
import com.example.GoodsSelector.models.UserTokenModel;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface IUserService {
    public UserTokenModel auth(UserModel userModel);

    public UserTokenModel register(UserModel userModel) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
