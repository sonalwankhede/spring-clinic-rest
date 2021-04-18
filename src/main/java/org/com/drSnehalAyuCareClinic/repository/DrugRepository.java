/*
 * Copyright 2002-2017 the original author or authors.
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
package org.com.drSnehalAyuCareClinic.repository;

import java.util.Collection;

import org.com.drSnehalAyuCareClinic.model.BaseEntity;
import org.com.drSnehalAyuCareClinic.model.Drug;
import org.springframework.dao.DataAccessException;

/**
 * Repository class for <code>Drug</code> domain objects All method names are compliant with Spring Data naming
 * conventions so this interface can easily be extended for Spring Data See here: http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 *
 * @author Sonal Wankhede
 */
public interface DrugRepository {

	/**
	 * Retrieve <code>Drug</code>s from the data store by last name, returning all Drugs whose last name <i>starts</i>
	 * with the given name.
	 *
	 * @param lastName Value to search for
	 * @return a <code>Collection</code> of matching <code>Drug</code>s (or an empty <code>Collection</code> if none
	 * found)
	 */
	Collection<Drug> findByContent(String drugName) throws DataAccessException;

	/**
	 * Retrieve an <code>Drug</code> from the data store by id.
	 *
	 * @param id the id to search for
	 * @return the <code>Drug</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException if not found
	 */
	Drug findById(int id) throws DataAccessException;


	/**
	 * Save an <code>Drug</code> to the data store, either inserting or updating it.
	 *
	 * @param Drug the <code>Drug</code> to save
	 * @see BaseEntity#isNew
	 */
	void save(Drug drug) throws DataAccessException;

	/**
	 * Retrieve <code>Drug</code>s from the data store, returning all Drugs 
	 *
	 * @return a <code>Collection</code> of <code>Drug</code>s (or an empty <code>Collection</code> if none
	 * found)
	 */
	Collection<Drug> findAll() throws DataAccessException;

	/**
	 * Delete an <code>Drug</code> to the data store by <code>Drug</code>.
	 *
	 * @param Drug the <code>Drug</code> to delete
	 * 
	 */
	void delete(Drug drug) throws DataAccessException;


}
