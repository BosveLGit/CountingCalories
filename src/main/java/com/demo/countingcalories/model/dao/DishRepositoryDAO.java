package com.demo.countingcalories.model.dao;

import com.demo.countingcalories.model.entity.Dish;
import com.demo.countingcalories.model.entity.Eating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepositoryDAO extends JpaRepository<Dish, Long> {

}
