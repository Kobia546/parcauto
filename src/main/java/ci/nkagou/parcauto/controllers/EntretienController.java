package ci.nkagou.parcauto.controllers;

import ci.nkagou.parcauto.dtos.dmd.DmdParcDto;
import ci.nkagou.parcauto.dtos.entretien.*;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EntretienController {


    private ArticleService articleService;
    private final ServletContext context;


    private  UserService userService;
    private VehiculeService vehiculeService;
    private EntretienService entretienService;

    //Save the uploaded file to this folder
    // private static String UPLOADED_FOLDER = "C:\\Users\\PC\\Desktop\\parcauto-master\\src\\main\\resources\\static\\temp\\";

    // private static String UPLOADED_FOLDER = "C://temp//";

    public EntretienController(ArticleService articleService, ServletContext context,VehiculeService vehiculeService,EntretienService entretienService) {

        this.articleService = articleService;
        this.vehiculeService = vehiculeService;
        this.entretienService = entretienService;
        this.context = context;
    }



    @RequestMapping("/Entretien/entretien")
    public String indexhebdomadaire(Model model, Principal principal, HttpServletRequest request) {

        List<DetailHerbdomadaireDtoOut> dto = new ArrayList<>();
        List<DetailHerbdomadaire> DetailHerbdomadaires = entretienService.all();
        dto = entretienService.listEntretienDto(DetailHerbdomadaires);


        model.addAttribute("listEntretienH",dto);

        return"entretien/indexhebdomadaire";
    }

    @RequestMapping(value = "/Entretien/entretien/new", method = RequestMethod.GET)
    public String nouvelleEntretien(Model model) {
        EntretienHerbdomadaireDto dto = new EntretienHerbdomadaireDto();
        model.addAttribute("title", "Entretien - Nouveau");
        model.addAttribute("entretienHerbDto",dto);

        return "entretien/newherbdomadaire";
    }

    @RequestMapping(value = "/Entretien/entretien/Suivant", method = RequestMethod.POST)
    public String nouvelleEntretien2(@Valid EntretienHerbdomadaireDto dto, Model model, Errors errors) {
        if (errors.hasErrors()) {
            System.out.println("error YES");
        }
        EntretienHerbdomadaireDto dto1 = dto;
        List<Vehicule> vehicules = vehiculeService.all();
        model.addAttribute("title", "Entretien - Nouveau");
        model.addAttribute("listVehicule",vehicules);
        model.addAttribute("entretienHerbDto",dto);

        return "entretien/newherbdomadaire2";
    }

    @RequestMapping("/Vidange/vidange")
    public String indexvidange(Model model, Principal principal, HttpServletRequest request) {

        List<DetailVidangeDtoOut> dto = new ArrayList<>();
        List<DetailVidange> detailVidanges = entretienService.allEntretienVidange();
        dto = entretienService.listVidangeDto(detailVidanges);

        model.addAttribute("listVidangeDto",dto);

        return"entretien/indexvidange";

    }

    @RequestMapping(value = "/Vidange/vidange/new", method = RequestMethod.GET)
    public String nouvelleVidange( Model model) {


        List<Vehicule> vehicules = vehiculeService.all();

        EntretienVidangeDto dto = new EntretienVidangeDto();


        model.addAttribute("listVehicule",vehicules);
        model.addAttribute("entretienVDto",dto);

        return "entretien/newvidange";

    }

    @RequestMapping(value = "/Vidange/vidange/suivant", method = RequestMethod.POST)
    public String nouvelleVidange2(@Valid EntretienVidangeDto dto,Model model,Errors errors) {
        if (errors.hasErrors()) {
            System.out.println("error YES");
        }

        List<Article> articles = articleService.all();
        model.addAttribute("listArticle",articles);
        model.addAttribute("entretienVDto",dto);

        return "entretien/newvidange2";

    }
}
