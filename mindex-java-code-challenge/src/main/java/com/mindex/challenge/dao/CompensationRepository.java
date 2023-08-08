package com.mindex.challenge.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String> {

	@Query(value = "{employee: ?0, effectiveDate: {$lte : ?1}}",
			sort="{effectiveDate:-1}")
	List<Compensation> findCompensationsByEmployeeIdAndDate(Employee employee, Date date);
}
