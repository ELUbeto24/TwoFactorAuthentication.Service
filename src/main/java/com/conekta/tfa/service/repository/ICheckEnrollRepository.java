package com.conekta.tfa.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conekta.tfa.service.model.CheckEnrollResponseModel;

@Repository
public interface ICheckEnrollRepository extends JpaRepository<CheckEnrollResponseModel, String> {

}
