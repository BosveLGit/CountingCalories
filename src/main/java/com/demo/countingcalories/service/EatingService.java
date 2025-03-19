package com.demo.countingcalories.service;

import com.demo.countingcalories.dto.DailyReportDTO;
import com.demo.countingcalories.dto.EatingAddUpdateDTO;
import com.demo.countingcalories.exception.EatingNotFoundException;
import com.demo.countingcalories.exception.UserNotFoundException;
import com.demo.countingcalories.model.dao.DishRepositoryDAO;
import com.demo.countingcalories.model.dao.EatingRepositoryDAO;
import com.demo.countingcalories.model.dao.UserRepositoryDAO;
import com.demo.countingcalories.model.entity.Dish;
import com.demo.countingcalories.model.entity.Eating;
import com.demo.countingcalories.model.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class EatingService {

    private final EatingRepositoryDAO eatingRepository;
    private final UserRepositoryDAO userRepository;
    private final DishRepositoryDAO dishRepository;

    public EatingService(EatingRepositoryDAO eatingRepository, UserRepositoryDAO userRepository, DishRepositoryDAO dishRepository) {
        this.eatingRepository = eatingRepository;
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
    }

    public Eating getEatingById(Long id) {
        return eatingRepository.findById(id)
                .orElseThrow(() -> new EatingNotFoundException(id));
    }

    public DailyReportDTO getDailyReport(Long userId, LocalDate date) {

        DailyCaloriesData data = getSumCaloriesByDate(userId, date);
        return new DailyReportDTO(data.eatingList(), data.totalCalories(), data.dailyCalories());

    }

    public boolean checkDailyCaloriesByUserIdByDate(Long userId, LocalDate date) {

        DailyCaloriesData data = getSumCaloriesByDate(userId, date);
        return data.totalCalories() <= data.dailyCalories();

    }

    public Page<Eating> getEatingHistory(Long userId, LocalDate startDate, LocalDate endDate, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        if (startDate != null && endDate != null) {
            LocalDateTime day_start = startDate.atStartOfDay();
            LocalDateTime day_end = endDate.atTime(23, 59, 59);
            return eatingRepository.findByUserIdAndDateBetween(userId, day_start, day_end, pageRequest);
        }
        return eatingRepository.findByUserId(userId, pageRequest);
    }

    public Eating createEating(EatingAddUpdateDTO eatingDTO) {
        return createAndFillEating(eatingDTO);
    }

    public Eating editEating(Long id, EatingAddUpdateDTO updatedEatingDTO) {
        return findAndFillDish(id, updatedEatingDTO);
    }

    public void deleteEating(Long id) {

        if (!eatingRepository.existsById(id)) {
            throw new EatingNotFoundException(id);
        }
        eatingRepository.deleteById(id);
    }

    private Eating fillEatingFromDTO(Eating eating, EatingAddUpdateDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(dto.getUserId()));

        List<Dish> dishes = dishRepository.findAllById(dto.getDishesId());

        if (dishes.size() != dto.getDishesId().size()) {
            throw new EntityNotFoundException("One or more dishes not found");
        }

        eating.setDate(dto.getDate());
        eating.setUser(user);

        Eating savedEating = eatingRepository.save(eating);

        savedEating.setDishes(dishes);
        
        return savedEating;

    }

    public Eating createAndFillEating(EatingAddUpdateDTO dto) {
        Eating eating = new Eating();
        fillEatingFromDTO(eating, dto);
        return eatingRepository.save(eating);
    }

    public Eating findAndFillDish(Long id, EatingAddUpdateDTO dto) {

        Eating eating = eatingRepository.findById(id)
                .orElseThrow(() -> new EatingNotFoundException(id));

        fillEatingFromDTO(eating, dto);
        return eatingRepository.save(eating);
    }

    public DailyCaloriesData getSumCaloriesByDate(Long userId, LocalDate date) {

        if (date == null) {
            throw new IllegalArgumentException("Необходимо заполнить дату");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        LocalDateTime day_start = date.atStartOfDay();
        LocalDateTime day_end = date.atTime(23, 59, 59);
        List<Eating> eatingList = eatingRepository.findByUserIdAndDateBetween(userId, day_start, day_end);

        int totalCalories = 0;

        for (Eating eating : eatingList) {
            List<Dish> dishes = eating.getDishes();
            if (dishes != null) {
                for (Dish dish : dishes) {
                    totalCalories = totalCalories + ((dish != null) ? dish.getCalories() : 0);
                }
            }
        }

        return new DailyCaloriesData(totalCalories, user.getDailyCalories(), eatingList);
    }

    public record DailyCaloriesData(int totalCalories, int dailyCalories, List<Eating> eatingList) {}

}
