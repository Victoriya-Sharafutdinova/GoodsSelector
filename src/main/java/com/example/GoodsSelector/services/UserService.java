package com.example.GoodsSelector.services;

import com.example.GoodsSelector.entities.User;
import com.example.GoodsSelector.models.HistoryModel;
import com.example.GoodsSelector.models.UserModel;
import com.example.GoodsSelector.models.UserTokenModel;
import com.example.GoodsSelector.repositories.ICategoryRepository;
import com.example.GoodsSelector.repositories.IProductTypeRepository;
import com.example.GoodsSelector.repositories.IUserRepository;
import io.jsonwebtoken.Jwts;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {
    private KeyManager keyManager;

    @Autowired
    private final IUserRepository userRepository;

    @Autowired
    private final IProductTypeRepository productTypeRepository;

    @Autowired
    private final ICategoryRepository categoryRepository;

    public UserService(IUserRepository userRepository, IProductTypeRepository productTypeRepository, ICategoryRepository categoryRepository){
        this.userRepository = userRepository;
        this.productTypeRepository = productTypeRepository;
        this.categoryRepository = categoryRepository;

        keyManager = KeyManager.getInstance();
    }

    @Override
    public UserTokenModel auth(UserModel userModel) {
        var user = userRepository.findByLogin(userModel.getLogin());
        if (user == null) {
            return null;
        }
        if (BCrypt.checkpw(userModel.getPassword(), user.getPassword())) {
            String jws = Jwts.builder().setSubject(user.getId().toString()).signWith(keyManager.getKey()).compact();
            return new UserTokenModel(jws);
        }
        else {
            return null;
        }
    }

    @Override
    public UserTokenModel register(UserModel userModel) {
        var user = userRepository.findByLogin(userModel.getLogin());
        if (user != null) {
            return null;
        }

        String pw_hash = BCrypt.hashpw(userModel.getPassword(), BCrypt.gensalt());

        userModel.setPassword(pw_hash);
        user = userRepository.save(new User(userModel));

        String jws = Jwts.builder().setSubject(user.getId().toString()).signWith(keyManager.getKey()).compact();
        return new UserTokenModel(jws);
    }

    @Override
    public List<HistoryModel> getHistories(UserTokenModel userTokenModel) {
        long id = 0;
        String subject = "";
        try {
            subject = Jwts.parserBuilder().setSigningKey(keyManager.getKey()).build().parseClaimsJws(userTokenModel.getToken()).getBody().getSubject();
        }
        catch (Exception e) {
            return null;
        }
        try {
            id = Long.parseLong(subject);
        }
        catch (NumberFormatException e) {
            return null;
        }

        var user = userRepository.getOne(id);
        var historyModels = new ArrayList<HistoryModel>();
        for (var history : user.getHistories()) {
            var productType = productTypeRepository.getOne(history.getHistoryProducts().get(0).getProduct().getProductTypeId());
            String productTypeName = productType.getName();
            String categoryName = categoryRepository.getOne(productType.getCategoryId()).getName();
            historyModels.add(new HistoryModel(history, categoryName, productTypeName));
        }

        return historyModels;
    }
}
