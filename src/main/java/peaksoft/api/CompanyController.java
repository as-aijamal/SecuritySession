package peaksoft.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Company;
import peaksoft.security.AuthInfo;
import peaksoft.service.CompanyService;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }



    @GetMapping("/new")
    public String newCompany(Model model) {
        model.addAttribute("newCompany", new Company());
        return "newCompany";
    }

    @PostMapping("/saveCompany")
    public String saveCompany(@ModelAttribute("newCompany") Company company) {
        companyService.saveCompany(company);
        return "redirect:/companies/allCompanies";
    }

    @GetMapping("/allCompanies")
    public String getAllCompanies(Model model, Authentication authentication) {
        model.addAttribute("allCompanies", companyService.getAllCompany());
        AuthInfo user = (AuthInfo) authentication.getPrincipal();
        System.out.println(user.getUser());
        return "allCompanies";
    }

    @GetMapping("/editCompany/{id}")
    public String editCompany(@PathVariable("id") Long id, Model model) {
        model.addAttribute("company", companyService.getById(id));
        return "editCompany";
    }

    @GetMapping("/updateCompany/{id}")
    public String updateCompany(@ModelAttribute("company") Company company, @PathVariable("id") Long id) {
        companyService.update(company,id);
        return "redirect:/companies/allCompanies";
    }

    @GetMapping ("/deleteCompany/{id}")
    public String deleteCompany(@PathVariable("id") Long id,Authentication authentication) {
       AuthInfo authInfo = (AuthInfo) authentication.getPrincipal();
        System.out.println(authInfo.getUser());
        companyService.deleteById(id);
        return "redirect:/companies/allCompanies";
    }
}