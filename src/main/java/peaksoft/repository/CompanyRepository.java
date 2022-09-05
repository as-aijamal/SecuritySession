package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.model.Company;

public interface CompanyRepository extends JpaRepository<Company,Long> {

}
