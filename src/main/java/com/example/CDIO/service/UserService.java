package com.example.CDIO.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.CDIO.domain.User;
import com.example.CDIO.domain.dto.Meta;
import com.example.CDIO.domain.dto.ResultPaginationDTO;
import com.example.CDIO.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public User fetchUserById(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    // Phân trang dữ liệu (dùng pageable)
    public ResultPaginationDTO fetchAllUser(Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();

        mt.setPage(pageable.getPageNumber());
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageUser.getContent());
        return rs;
    }

    public User handleUpdateUser(User user) {
        User currentUser = this.fetchUserById(user.getId());
        if (currentUser != null) {
            currentUser.setName(user.getName());
            currentUser.setEmail(user.getEmail());
            currentUser.setPassword(user.getPassword());

            currentUser = this.userRepository.save(currentUser);
        }
        return currentUser;
    }

    public void handleDeleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }

    public void updateUserToken(String token, String email) {
        User currentUser = this.handleGetUserByUsername(email);
        if (currentUser != null) {
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);
        }
    }

    public boolean isEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }
    
}
