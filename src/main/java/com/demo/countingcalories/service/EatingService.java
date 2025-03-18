package com.demo.countingcalories.service;

import com.demo.countingcalories.dto.DailyReportDTO;
import com.demo.countingcalories.exception.EatingNotFoundException;
import com.demo.countingcalories.exception.UserNotFoundException;
import com.demo.countingcalories.model.dao.EatingRepositoryDAO;
import com.demo.countingcalories.model.dao.UserRepositoryDAO;
import com.demo.countingcalories.model.entity.Dish;
import com.demo.countingcalories.model.entity.Eating;
import com.demo.countingcalories.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class EatingService {

    private final EatingRepositoryDAO eatingRepository;
    private final UserRepositoryDAO userRepository;

    public EatingService(EatingRepositoryDAO eatingRepository, UserRepositoryDAO userRepository) {
        this.eatingRepository = eatingRepository;
        this.userRepository = userRepository;
    }

    public Eating getEatingById(Long id) {
        return eatingRepository.findById(id)
                .orElseThrow(() -> new EatingNotFoundException(id));
    }

    public DailyReportDTO getDailyReport(Long userId, LocalDate date) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<Eating> eatingList = eatingRepository.findByUserIdAndDate(userId, date);

        int totalCalories = 0;

        for (Eating eating : eatingList) {
            for (Dish dish : eating.getDishes()) {
                totalCalories += dish.getCalories();
            }
        }

        return new DailyReportDTO(eatingList, totalCalories, user.getDailyCalories());
    }


    public boolean checkDailyCaloriesByUserIdByDate(Long userId, LocalDate date) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<Eating> eatingList = eatingRepository.findByUserIdAndDate(userId, date);

        int totalCalories = 0;

        for (Eating eating : eatingList) {
            for (Dish dish : eating.getDishes()) {
                totalCalories += dish.getCalories();
            }
        }

        return totalCalories <= user.getDailyCalories();

    }

    public Page<Eating> getEatingHistory(Long userId, LocalDate startDate, LocalDate endDate, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (startDate != null && endDate != null) {
            return eatingRepository.findByUserIdAndDateBetween(userId, startDate, endDate, pageRequest);
        }
        return eatingRepository.findByUserId(userId, pageRequest);
    }

    public Eating createEating(Eating eating) {
        return eatingRepository.save(eating);
    }

    public Eating editEating(Long id, Eating updatedDish) {

        Eating eating = eatingRepository.findById(id)
                .orElseThrow(() -> new EatingNotFoundException(id));

        eating.setDate(updatedDish.getDate());
        eating.setUser(updatedDish.getUser());
        eating.setDishes(updatedDish.getDishes());

        return eatingRepository.save(eating);
    }

    public void deleteEating(Long id) {

        if (!eatingRepository.existsById(id)) {
            throw new EatingNotFoundException(id);
        }
        eatingRepository.deleteById(id);
    }


}
