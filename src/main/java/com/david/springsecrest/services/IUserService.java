package com.david.springsecrest.services;


import com.david.springsecrest.enums.ERole;
import com.david.springsecrest.enums.EUserStatus;
import com.david.springsecrest.models.User;
import com.david.springsecrest.payload.request.UpdateUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.util.Optional;
import java.util.UUID;


public interface IUserService {

    Page<User> getAll(Pageable pageable);

    User getById(UUID id);

    User create(User user);
    User update(UUID id, UpdateUserDTO dto);

    Page<User> getAllByRole(Pageable pageable, ERole role);

    Page<User> searchUser(Pageable pageable, String searchKey);

    User getLoggedInUser();

    User getByEmail(String email);

//    Optional<User> findByActivationCode(String verificationCode);
}
