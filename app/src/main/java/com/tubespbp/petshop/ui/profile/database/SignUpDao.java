package com.tubespbp.petshop.ui.profile.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tubespbp.petshop.ui.profile.model.User;

import java.util.List;

@Dao
public interface SignUpDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id = :id")
    List<User> getUser(int id);

    @Query("UPDATE user SET full_name = :name, phone_number = :phone, city = :city, country = :country WHERE id = :id")
    void updateUser(String name, String phone, String city, String country, int id);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

}

