
package com.example.demo.repository;

import com.example.demo.model.User;

public interface UserRepository {
    User findById(int id);
}
