package com.mycompany.callcenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mycompany.callcenter.model.Director;
import com.mycompany.callcenter.model.Employee;
import com.mycompany.callcenter.model.Operator;
import com.mycompany.callcenter.model.Supervisor;

/**
 * The interface Employee repository.
 *
 */
@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {
}
