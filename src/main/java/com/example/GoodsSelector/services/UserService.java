package com.example.GoodsSelector.services;

import com.example.GoodsSelector.entities.User;
import com.example.GoodsSelector.models.UserModel;
import com.example.GoodsSelector.models.UserTokenModel;
import com.example.GoodsSelector.repositories.IUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class UserService implements IUserService {
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Autowired
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserTokenModel auth(UserModel userModel) {
        var user = userRepository.findByLogin(userModel.getLogin());

        if (BCrypt.checkpw(userModel.getPassword(), user.getPassword())) {
            String jws = Jwts.builder().setSubject(user.getId().toString()).signWith(key).compact();
            return new UserTokenModel(jws);
        }
        else {
            return null;
        }

    }

    @Override
    public UserTokenModel register(UserModel userModel) {
        String pw_hash = BCrypt.hashpw(userModel.getPassword(), BCrypt.gensalt());

        userModel.setPassword(pw_hash);
        User user = userRepository.save(new User(userModel));

        String jws = Jwts.builder().setSubject(user.getId().toString()).signWith(key).compact();
        return new UserTokenModel(jws);
        // String subject = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject();
    }
}
