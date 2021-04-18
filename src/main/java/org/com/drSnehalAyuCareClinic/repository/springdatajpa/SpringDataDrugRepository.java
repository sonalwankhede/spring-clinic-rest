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

import org.com.drSnehalAyuCareClinic.repository.DrugRepository;
import org.springframework.context.annotation.Profile;
import org.com.drSnehalAyuCareClinic.model.Drug;
import org.springframework.data.repository.Repository;

/**
 * Spring Data JPA specialization of the {@link PatientRepository} interface
 *
 * @author Sonal Wankhede

 */
@Profile("spring-data-jpa")
public interface SpringDataDrugRepository extends DrugRepository, Repository<Drug, Integer> {

}
