package com.server.fitnessapp.services;

import com.server.fitnessapp.data.models.User;
import com.server.fitnessapp.data.repositories.UserRepository;
import com.server.fitnessapp.dtos.reponse.UserResponse;
import com.server.fitnessapp.dtos.requests.UserRequest;
import com.server.fitnessapp.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static com.server.fitnessapp.utils.Mapper.map;
import static com.server.fitnessapp.utils.Validator.validate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponse register(UserRequest request) {
        validate(request);
        User user = userRepository.save(map(request));
        return map(user);
    }

    @Override
    public UserResponse findById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return map(user);
    }
}
