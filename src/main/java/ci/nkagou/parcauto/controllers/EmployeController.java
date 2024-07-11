package ci.nkagou.parcauto.controllers;

import ci.nkagou.parcauto.dtos.employe.EmployeRequest;
import ci.nkagou.parcauto.dtos.employe.EmployeResponse;
import ci.nkagou.parcauto.entities.AppRole;
import ci.nkagou.parcauto.services.EmployeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeController {

    final private EmployeService employeService;


    public EmployeController(EmployeService employeService) {
        this.employeService = employeService;
    }

    /*
    * Page d'affichage de la liste des employes
    * */
    @RequestMapping(value = "/employes/employes")
    public String indexEmploye(Model model, HttpServletRequest request){



        List<EmployeResponse> employes = employeService.DTOS();


        model.addAttribute("title", "Employes - Liste");
        model.addAttribute("listEmploye", employes);
        return "employe/index";
    }

    /*
     * Page d'affichage lr formulaire d'insertion d'un formulaire
     * */

    @RequestMapping(value = "/employes/employes/new", method = RequestMethod.GET)
    public String newEmploye(Model model){

        model.addAttribute("monemploye",new EmployeRequest());
        model.addAttribute("title", "Employé - Nouveau");
        return "employe/new";

    }

    @RequestMapping(value = "employes/employes/save", method = RequestMethod.POST)
    public String saveEmploye(@Valid @ModelAttribute("monemploye") EmployeRequest request, Errors errors, Model model, RedirectAttributes redirectAttributes){

        if (errors.hasErrors()){
            System.out.println("error YES");
           //  model.addAttribute("monemploye", new EmployeRequest());
            return "employe/new";
        }

        redirectAttributes.addFlashAttribute("messagesucces","Opération éffectée avec succès");
        return "redirect:/employes/employes";
    }

    @RequestMapping(value = "/employes/employes/edit/{id}", method = RequestMethod.GET)
    public String editEmploye(@PathVariable Long id, Model model){




        model.addAttribute("title", "Employe - Edition");
        return "employe/edit";
    }

    @RequestMapping(value = "/employes/employes/update", method = RequestMethod.PUT)
    public String updateEmploye(@Valid EmployeRequest request,Errors errors, Model model){

        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("monemploye", new EmployeRequest());
            return "employe/edit";
        }


        model.addAttribute("title", "Employe - Edition");
        return "employe/index";
    }

    @RequestMapping(value = "/employes/employes/delete/{id}", method = RequestMethod.GET)
    public String deleteRole(@PathVariable Long id){


        return "redirect:/employes/employes";
    }
}
