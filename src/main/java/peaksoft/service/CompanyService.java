package peaksoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.model.Company;
import peaksoft.model.Role;
import peaksoft.model.User;
import peaksoft.repository.CompanyRepository;
import peaksoft.repository.RoleRepository;
import peaksoft.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    public List<Company> getAllCompany() {
        List<Company> companies = companyRepository.findAll();
        return companies;
    }

    public Company getById(Long id) {
        return companyRepository.findById(id).
                orElseThrow(() -> new NullPointerException("Company with " + id + " was not found"));
    }

    public void deleteById(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
        } else
            throw new NullPointerException(String.format("Company with id %s doesn't exists", id));

    }

    public Company update(Company company, Long id) {
        Company company1 = companyRepository.findById(id).
                orElseThrow(() ->
                        new NullPointerException("Company with " + id + " was not found"));

        company1.setCompanyName(company.getCompanyName());
        company1.setLocatedCountry(company.getLocatedCountry());
        companyRepository.save(company1);
        return company1;
    }

    public void saveStudent(User user){
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findById(3L).orElseThrow());
        user.setRoles(roles);
        userRepository.save(user);
    }
}
