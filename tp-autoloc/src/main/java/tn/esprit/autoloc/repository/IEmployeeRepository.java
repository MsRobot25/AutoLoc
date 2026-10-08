// IEmployeeRepository.java
package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Employe;

public interface IEmployeeRepository extends JpaRepository<Employe, Long> {
}