/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.com.drSnehalAyuCareClinic.repository.springdatajpa;

import java.util.Collection;

import org.com.drSnehalAyuCareClinic.model.Patient;
import org.com.drSnehalAyuCareClinic.repository.PatientRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA specialization of the {@link PatientRepository} interface
 *
 * @author Sonal Wankhede

 */

@Profile("spring-data-jpa")
public interface SpringDataPatientRepository extends PatientRepository, Repository<Patient, Integer> {

    @Override
    @Query("SELECT DISTINCT patient FROM Patient patient WHERE patient.lastName LIKE :lastName%")
    Collection<Patient> findByLastName(@Param("lastName") String lastName);

    @Override
    @Query("SELECT patient FROM Patient patient WHERE patient.id =:id")
    Patient findById(@Param("id") int id);
}
