package com.example.GoodsSelector.services;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class KeyManager {
    private Key key;
    private static KeyManager instance = null;

    public KeyManager() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public static KeyManager getInstance() {
        if (instance == null) {
            instance = new KeyManager();
        }
        return instance;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }
}
