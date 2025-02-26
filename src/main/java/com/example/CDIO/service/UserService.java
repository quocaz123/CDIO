package com.example.CDIO.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.CDIO.domain.User;
import com.example.CDIO.domain.dto.LoginDTO;
import com.example.CDIO.domain.dto.Meta;
import com.example.CDIO.domain.dto.RegisterDTO;
import com.example.CDIO.domain.dto.ResultPaginationDTO;
import com.example.CDIO.repository.UserRepository;

import jakarta.validation.Valid;

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

    public boolean isEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User getUserByRefreshTokenAndEmail(String token, String email){
        return this.userRepository.findByRefreshTokenAndEmail(token, email);
    }

    public User registerDTOtoUser(RegisterDTO registerDTO){
        User user = new User();
        user.setName(registerDTO.getName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    
    // public ResCreateUserDTO convertToResCreateUserDTO(User user){
    //     ResCreateUserDTO res = new ResCreateUserDTO();
    //     res.setId(user.getId());
    //     res.setEmail(user.getEmail());
    //     res.setName(user.getName());
    //     res.setAge(user.getAge());
    //     res.setCreateAt(user.getCreateAt());
    //     res.setGender(user.getGender());
    //     res.setAddress(user.getAddress());
    //     return res;
    // }

    // public ResUserDTO convertToResUserDTO(User user){
    //     ResUserDTO res = new ResUserDTO();
    //     res.setId(user.getId());
    //     res.setEmail(user.getEmail());
    //     res.setName(user.getName());
    //     res.setAge(user.getAge());
    //     res.setUpdateAt(user.getUpdateAt());
    //     res.setCreateAt(user.getCreateAt());
    //     res.setGender(user.getGender());
    //     res.setAddress(user.getAddress());
    //     return res;
    // }

    // public ResUpdateUserDTO convertToResUpdateUserDTO(User user){
    //     ResUpdateUserDTO res = new ResUpdateUserDTO();
    //     res.setId(user.getId());
    //     res.setName(user.getName());
    //     res.setAge(user.getAge());
    //     res.setUpdateAt(user.getUpdateAt());
    //     res.setGender(user.getGender());
    //     res.setAddress(user.getAddress());
    //     return res;
    // }

    public void updateUserToken(String token, String loginDTO){
        User currentUser = this.handleGetUserByUsername(loginDTO);
        if(currentUser != null){
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);
        }
    }
    
}
