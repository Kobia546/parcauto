package ci.nkagou.parcauto.controllers;

import ci.nkagou.parcauto.dtos.employe.EmployeRequest;
import ci.nkagou.parcauto.dtos.employe.EmployeResponse;
import ci.nkagou.parcauto.entities.Direction;
import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.Site;
import ci.nkagou.parcauto.enums.Genre;
import ci.nkagou.parcauto.services.DirectionService;
import ci.nkagou.parcauto.services.EmployeService;
import ci.nkagou.parcauto.services.SiteService;
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
import java.util.Arrays;
import java.util.List;

@Controller
public class EmployeController {

    final private EmployeService employeService;
    final private SiteService siteService;
    private final DirectionService directionService;

    public EmployeController(EmployeService employeService, SiteService siteService, DirectionService directionService) {
        this.employeService = employeService;
        this.siteService = siteService;
        this.directionService = directionService;
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
//        List<String> genres = Arrays.asList("Homme", "Femme");
//        List<Genre> genres = Arrays.asList(
//                new Genre(1L, "Homme"),
//                new Genre(2L, "Femme")
//                // Ajoutez d'autres genres si nécessaire
        List<Site> sites = siteService.all();
        List <Direction> directions=directionService.all();
//        List<Direction> directions = Arrays.asList(
//                new Direction(1L, "INFORMATIQUE"),
//                new Direction(2L, "PARC AUTO")
//
//                // Ajoutez d'autres directions si nécessaire
//        );
        model.addAttribute("sites", sites);
        model.addAttribute("directions",directions);
//        );
        model.addAttribute("monemploye",new EmployeRequest());
        model.addAttribute("genres", Genre.values());
//        model.addAttribute("genres", genres);

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
        employeService.create(request);
        redirectAttributes.addFlashAttribute("messagesucces","Opération éffectée avec succès");
        return "redirect:/employes/employes";
    }

    @RequestMapping(value = "/employes/employes/edit/{id}", method = RequestMethod.GET)
    public String editEmploye(@PathVariable Long id, Model model){

        Employe employe=employeService.getEmploye(id);
        List<Site> sites = siteService.all();
        List <Direction> directions=directionService.all();






        model.addAttribute("sites",sites);
        model.addAttribute("directions",directions);
        model.addAttribute("genres", Genre.values());
        model.addAttribute("monemploye", employe);
        return "employe/edit";
    }

    @RequestMapping(value = "/employes/employes/update/{id}", method = RequestMethod.POST)
    public String updateEmploye(@Valid @ModelAttribute("monemploye") EmployeRequest request,@PathVariable Long id, Errors errors, Model model){

//       employeService.update(request,idEmploye);
        if (errors.hasErrors()){
            System.out.println("error YES");
//            model.addAttribute("monemploye", new EmployeRequest());
            return "employe/edit";
        }
        employeService.update(request,id);


//        model.addAttribute("title", "Employe - Edition");
        return "redirect:/employes/employes";
    }

    @RequestMapping(value = "/employes/employes/delete/{id}", method = RequestMethod.GET)
    public String deleteUtilisateur(@PathVariable Long id){


        employeService.delete(id);

        return "redirect:/employes/employes";
    }
}
