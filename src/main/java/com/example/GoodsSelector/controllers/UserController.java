package com.example.GoodsSelector.controllers;

import com.example.GoodsSelector.models.HistoryModel;
import com.example.GoodsSelector.models.UserModel;
import com.example.GoodsSelector.models.UserTokenModel;
import com.example.GoodsSelector.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/auth")
    public ResponseEntity<UserTokenModel> auth(@RequestBody UserModel userModel) {
        var userTokenModel = userService.auth(userModel);
        return new ResponseEntity<>(userTokenModel, HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserTokenModel> register(@RequestBody UserModel userModel) {
        var userTokenModel = userService.register(userModel);
        return new ResponseEntity<>(userTokenModel, HttpStatus.OK);
    }

    @PostMapping(value = "/history")
    public ResponseEntity<List<HistoryModel>> getHistories(@RequestBody UserTokenModel userTokenModel) {
        List<HistoryModel> historyModels = userService.getHistories(userTokenModel);
        return new ResponseEntity<>(historyModels, HttpStatus.OK);
    }
}
