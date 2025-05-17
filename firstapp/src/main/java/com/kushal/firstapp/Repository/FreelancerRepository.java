package com.kushal.firstapp.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.kushal.firstapp.Model.User;
import com.kushal.firstapp.dto.TopFreelancerDto;

import java.util.List;

public interface FreelancerRepository extends CrudRepository<User, Long> {

    @Query(value = "CALL GetTopFreelancers();", nativeQuery = true)
    List<TopFreelancerDto> getTopFreelancers();

}