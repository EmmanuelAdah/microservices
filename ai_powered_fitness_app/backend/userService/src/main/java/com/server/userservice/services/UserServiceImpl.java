package com.server.userservice.services;

import com.server.userservice.data.models.User;
import com.server.userservice.data.repositories.UserRepository;
import com.server.userservice.dtos.requests.UserRequest;
import com.server.userservice.dtos.response.UserResponse;
import com.server.userservice.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import static com.server.userservice.utils.Validator.isValidRequest;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserResponse saveUser(UserRequest request){
        isValidRequest(request);
        User mappedUser = modelMapper.map(request, User.class);

        User savedUser = userRepository.save(mappedUser);
        return modelMapper.map(savedUser, UserResponse.class);
    }

    @Override
    public UserResponse findById(String userId){
        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return modelMapper.map(savedUser, UserResponse.class);
    }

    @Override
    public Boolean existById(String userId) {
        return userRepository.existsById(userId);
    }
}
