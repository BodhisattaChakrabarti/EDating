package com.trainings.dating.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainings.dating.entities.Interest;

public interface InterestRepository extends JpaRepository<Interest, Integer> {

}
