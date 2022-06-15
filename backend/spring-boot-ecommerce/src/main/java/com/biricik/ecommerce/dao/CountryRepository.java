package com.biricik.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.biricik.ecommerce.entity.Country;


@RepositoryRestResource(collectionResourceRel = "countries" ,path = "countries")
public interface CountryRepository extends JpaRepository<Country, Integer> {

}
