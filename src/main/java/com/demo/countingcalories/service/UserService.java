package com.demo.countingcalories.service;

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

    public User createUser(User user) {

        int dailyCalories = calorieCalculatorService.calculateDailyCalories(
                user.getWeight(), user.getHeight(), user.getAge(), user.getPurpose(), user.getGender());

        user.setDailyCalories(dailyCalories);

        return userRepository.save(user);
    }

    public User editUser(Long id, User updatedUser) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setWeight(updatedUser.getWeight());
        user.setHeight(updatedUser.getHeight());
        user.setAge(updatedUser.getAge());
        user.setPurpose(updatedUser.getPurpose());
        user.setGender(updatedUser.getGender());

        int dailyCalories = calorieCalculatorService.calculateDailyCalories(
                user.getWeight(), user.getHeight(), user.getAge(), user.getPurpose(), user.getGender());

        user.setDailyCalories(dailyCalories);

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

}
