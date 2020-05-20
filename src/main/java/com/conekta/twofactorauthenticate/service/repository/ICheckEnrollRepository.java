package com.conekta.twofactorauthenticate.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conekta.twofactorauthenticate.service.model.CheckEnrollResponseModel;

@Repository
public interface ICheckEnrollRepository extends JpaRepository<CheckEnrollResponseModel, String> {

}
