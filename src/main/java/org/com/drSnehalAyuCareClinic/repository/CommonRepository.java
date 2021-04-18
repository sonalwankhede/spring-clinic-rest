package org.com.drSnehalAyuCareClinic.repository;

import org.com.drSnehalAyuCareClinic.model.Drug;
import org.springframework.data.repository.CrudRepository;

public interface CommonRepository  extends CrudRepository<Drug, Long> {

}