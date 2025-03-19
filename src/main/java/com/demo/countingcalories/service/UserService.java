package com.demo.countingcalories.service;

import com.demo.countingcalories.dto.UserAddUpdateDTO;
import com.demo.countingcalories.exception.UserNotFoundException;
import com.demo.countingcalories.model.dao.UserRepositoryDAO;
import com.demo.countingcalories.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepositoryDAO userRepository;
    private final CalorieCalculatorService calorieCalculatorService;

    public UserService(UserRepositoryDAO userRepository, CalorieCalculatorService calorieCalculatorService) {
        this.userRepository = userRepository;
        this.calorieCalculatorService = calorieCalculatorService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User createUser(UserAddUpdateDTO userDTO) {
        return createAndFillUser(userDTO);
    }

    public User editUser(Long id, UserAddUpdateDTO updatedUserDTO) {
        return findAndFillUser(id, updatedUserDTO);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    private User fillUserFromDTO(User user, UserAddUpdateDTO dto) {

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setWeight(dto.getWeight());
        user.setHeight(dto.getHeight());
        user.setAge(dto.getAge());
        user.setPurpose(dto.getPurpose());
        user.setGender(dto.getGender());

        int dailyCalories = calorieCalculatorService.calculateDailyCalories(
                user.getWeight(), user.getHeight(), user.getAge(), user.getPurpose(), user.getGender());

        user.setDailyCalories(dailyCalories);

        return user;
    }

    public User createAndFillUser(UserAddUpdateDTO dto) {
        User user = new User();
        fillUserFromDTO(user, dto);
        return userRepository.save(user);
    }

    public User findAndFillUser(Long id, UserAddUpdateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        fillUserFromDTO(user, dto);
        return userRepository.save(user);
    }


}
