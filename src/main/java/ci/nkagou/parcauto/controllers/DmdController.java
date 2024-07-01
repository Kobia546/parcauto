package ci.nkagou.parcauto.controllers;

import ci.nkagou.parcauto.dtos.chauffeur.EmployeDtoOut;
import ci.nkagou.parcauto.dtos.chauffeurhistorique.ChauffeurHistoriqueDtoOut;
import ci.nkagou.parcauto.dtos.dmd.*;
import ci.nkagou.parcauto.dtos.etat.DmdPapierDetailResponse;
import ci.nkagou.parcauto.dtos.etat.DmdPapierResponse;
import ci.nkagou.parcauto.dtos.rapport.RapportChauffeurDto;
import ci.nkagou.parcauto.dtos.rapport.RapportEmployeDto;
import ci.nkagou.parcauto.dtos.rapport.RapportVehiculeDto;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.*;
import ci.nkagou.parcauto.enums.Motifs;
import ci.nkagou.parcauto.repositories.*;
import ci.nkagou.parcauto.services.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static ci.nkagou.parcauto.enums.RapportStatut.TOUS;
import static ci.nkagou.parcauto.enums.Statut.*;
import static ci.nkagou.parcauto.enums.StatutAttrib.TERMINEE;
import static ci.nkagou.parcauto.enums.StatutVehicule.DISPONIBLE;


@Controller
public class DmdController {

    private final DmdService dmdService;
    private  DmdRepository dmdRepository;

    private final EmployeService employeService;
    public static Principal principal;
    private final DirectionService directionService;
    private EmployeDmdService employeDmdService;
    private EmployeDmdRepository employeDmdRepository;
    private final VehiculeService vehiculeService;
    private AttributionService attributionService;
    private ChauffeurHistoriqueService chauffeurHistoriqueService;
    private CarburantAttRepository carburantAttRepository;
    private AttributionRepository attributionRepository;
    private NotificationService notificationService;
    private UserRoleService userRoleService;
    private ArticleService articleService;
    private RoleService roleService;
    private final ServletContext context;
    private MotifService motifService;
    private DestinationService destinationService;
    private DetailVehiculeChauffeurAService detailVehiculeChauffeurAService;
    private DetailVehiculeAService detailVehiculeAService;
    private DetailCarburantAService detailCarburantAService;
    private final EtatService etatService;




    private  UserService userService;

    //Save the uploaded file to this folder
   // private static String UPLOADED_FOLDER = "C:\\Users\\PC\\Desktop\\parcauto-master\\src\\main\\resources\\static\\temp\\";

   // private static String UPLOADED_FOLDER = "C://temp//";

    public DmdController(DmdService dmdService, EmployeService employeService, UserService userService, DirectionService directionService, VehiculeService vehiculeService, AttributionService attributionService, EmployeDmdService employeDmdService, ChauffeurHistoriqueService chauffeurHistoriqueService, CarburantAttRepository carburantAttRepository, AttributionRepository attributionRepository, ArticleService articleService, ServletContext context, NotificationService notificationService, UserRoleService userRoleService, RoleService roleService, DmdRepository dmdRepository, MotifService motifService, DestinationService destinationService, DetailVehiculeChauffeurAService detailVehiculeChauffeurAService, DetailVehiculeAService detailVehiculeAService, DetailCarburantAService detailCarburantAService, EtatService etatService) {
        this.dmdService = dmdService;
        this.employeService = employeService;
        //this.userService = userService;
        this.directionService = directionService;
        //this.vehiculeService = vehiculeService;
        this.vehiculeService = vehiculeService;
        this.attributionService = attributionService;
        this.employeDmdService = employeDmdService;
        this.chauffeurHistoriqueService = chauffeurHistoriqueService;
        this.attributionRepository = attributionRepository;
        this.carburantAttRepository = carburantAttRepository;
        this.articleService = articleService;
        this.notificationService = notificationService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.dmdRepository = dmdRepository;
        this.context = context;
        this.motifService = motifService;
        this.destinationService = destinationService;
        this.detailVehiculeChauffeurAService = detailVehiculeChauffeurAService;
        this.detailVehiculeAService = detailVehiculeAService;
        this.detailCarburantAService = detailCarburantAService;

        this.etatService = etatService;
    }

    @RequestMapping("/dmd/dmds")
    public String index(Model model, Principal principal, HttpServletRequest request) {

        //List<Employe> employeResponsable = employeService.listEmployesEstSuperieurHierachique(true);

        //Get User connected
        //AppUser user = userService.findByUserName(principal.getName());

        //Get Employe by user connected
        Employe employe = employeService.getEmployeByUserName(principal.getName());

        //Set roleParcauto
        String roleParcauto = "ROLE_PARCAUTO";
        String roleResponsableDSI = "ROLE_RESPONSABLE";
        String roleAdmin = "ROLE_ADMIN";


        List<DmdUserDtoOut> dtos = new ArrayList<>();
        List<DmdUserDtoOut> dmds = new ArrayList<>();
        //Check in  user connected have role "ROLE_PARCAUTO"

        if (request.isUserInRole(roleParcauto)) {

            //Get all EmployeDmd
            List<EmployeDmd> dmdss = dmdService.listEmployeDmdsByStatut(VALIDATION);

            dtos = dmdService.listDmdsToDto(dmdss);

            //Convert EmployeDmd to DmdParcDtoOut
            //List<DmdParcDtoOut> dmds = dmdService.listDmdsParcToDto(epDmds);
            //model.addAttribute("listDmdParcauto",dmds);
            //return "indexParc";

        } else if (request.isUserInRole(roleResponsableDSI)) {
            //List<Employe> Dmdss = dmdService.getEmployeByDirection();
            Direction direction = directionService.findById(1L);
            List<EmployeDmd> dmd = dmdService.listEmployeDmdByStatutDirection(Statut.DEMANDE, direction);
            //Direction directions = directionService.findById(1L);
            dtos = dmdService.listDmdsToDto(dmd);
        } else if (request.isUserInRole(roleAdmin)) {

            List<EmployeDmd> dmds2 = dmdService.all();
            dtos = dmdService.listDmdsToDto(dmds2);

        } else {

            //Get EmployeDmd by Employe
            List<EmployeDmd> epDmds = dmdService.findEmployeDmdsByEmploye(employe);
            dtos = dmdService.listDmdsToDto(epDmds);

            //Convert EmployeDmd to DmdUserDtoOut
            //List<DmdUserDtoOut> dmds = dmdService.listDmdsToDto(epDmds);
            //model.addAttribute("listDmdUser",dmds);
            //return "indexUser";

        }

        List<Statut> statuts = Arrays.asList(Statut.values());


        model.addAttribute("listDmds", dtos);
        model.addAttribute("listStatuts", statuts);

        return "dmd/index";

    }

    @RequestMapping("/dmd/dmdResponsable")
    public String indexResponsable(Model model, Principal principal, HttpServletRequest request) {

        Employe employe = employeService.getEmployeByUserName(principal.getName());

        String roleResponsableDSI = "ROLE_RESPONSABLE";

        List<DmdUserDtoOut> dtos = new ArrayList<>();

        if (request.isUserInRole(roleResponsableDSI)) {

            Direction direction = directionService.findById(1L);
            List<EmployeDmd> dmd = dmdService.listEmployeDmdByStatutStatutDirection(Statut.VALIDATION, REFUS, direction);
            dtos = dmdService.listDmdsToDto(dmd);
        }

        model.addAttribute("listDmdResponsable", dtos);

        return "dmd/indexResponsable";

    }

    @RequestMapping("/dmd/dmdEmploye")
    public String indexEmploye(Model model, Principal principal, HttpServletRequest request) {


        Employe employe = employeService.getEmployeByUserName(principal.getName());

        String roleUser = "ROLE_USER";

        List<DmdUserDtoOut> dtos = new ArrayList<>();

        if (request.isUserInRole(roleUser)) {

            //Direction direction = directionService.findById(1L);
            List<EmployeDmd> dmd = dmdService.findEmployeDmdsByEmploye(employe);
            dtos = dmdService.listDmdsToDto(dmd);

        }

        model.addAttribute("listDmdEmploye", dtos);

        return "dmd/indexEmploye";

    }

    @RequestMapping("/dmd/dmdDemande")
    public String indexDemande(Model model, Principal principal, HttpServletRequest request) {


        Employe employe = employeService.getEmployeByUserName(principal.getName());

        String roleUser = "ROLE_USER";

        List<DmdUserDtoOut> dtos = new ArrayList<>();

        if (request.isUserInRole(roleUser)) {

            //Direction direction = directionService.findById(1L);
            List<EmployeDmd> dmd = dmdService.findEmployeDmdsByEmploye(employe)
                    .stream()
                    .filter(EmployeDmd -> EmployeDmd.getStatut() == Statut.DEMANDE)
                    .sorted(Comparator.comparing(EmployeDmd::getStatut)
                            .reversed())
                    .collect(Collectors.toList());
            dtos = dmdService.listDmdsToDto(dmd);
        }

        model.addAttribute("listDmdDemande", dtos);

        return "dmd/indexDemande";

    }

    @RequestMapping(value = "/dmd/dmds/user", method = RequestMethod.GET)
    public String newDmdUser(Model model, @ModelAttribute("dmdUserDto") DmdUserDto dmdUserDto, BindingResult result) {

        // Charger les données nécessaires pour le formulaire
        List<Statut> statuts = Arrays.asList(Statut.values());
        List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());
        List<Motif> motif = motifService.all();
        List<Destination> destination = destinationService.all();

        // Ajouter les données au modèle
        model.addAttribute("title", "Dmd - Nouveau");
        model.addAttribute("listMoyenDemandes", moyenDemandes);
        model.addAttribute("listStatuts", statuts);
        model.addAttribute("listMotif", motif);
        model.addAttribute("listDestination", destination);

        // Vérifier s'il y a des erreurs de validation
        if (result.hasErrors()) {
            model.addAttribute("hasErrors", true);
        }

        return "dmd/new";
    }


    @RequestMapping(value = "/dmd/dmds/parc", method = RequestMethod.GET)
    public String newDmdParc(Model model) {
        DmdParcDto dmdParcDto = new DmdParcDto();
        List<Employe> employeResponsable = employeService.listEmployesEstSuperieurHierachique(true);
        List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());
        model.addAttribute("dmdParcDto", dmdParcDto);
        model.addAttribute("title", "Dmd - Nouveau");
        model.addAttribute("listMoyenDemandes", moyenDemandes);
        model.addAttribute("listresponsable", employeResponsable);

        return "dmd/newParc";
    }

    @RequestMapping(value = "/dmd/dmds/suivant", method = RequestMethod.POST)
    public String newParcSuivant(@Valid @ModelAttribute("dmdParcDto") DmdParcDto dmdParcDto, BindingResult bindingResult,Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println("error YES");

            List<Employe> employeResponsable = employeService.listEmployesEstSuperieurHierachique(true);
            List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());
            model.addAttribute("title", "Dmd - Nouveau");
            model.addAttribute("listMoyenDemandes", moyenDemandes);
            model.addAttribute("listresponsable", employeResponsable);

            return "dmd/newParc";
        }
        //DmdParcDto dto = dmdParcDto;
        List<DmdParcDtoOut> dtos = new ArrayList<>();
        List<Employe> employes = employeService.all();
        List<Motif> motif = motifService.all();
        List<Destination> destination = destinationService.all();
        model.addAttribute("listDmd", dtos);
        model.addAttribute("title", "Dmd - Nouveau");
        model.addAttribute("listEmploye", employes);
        model.addAttribute("dto", new EmployeDmdDto());
        model.addAttribute("listMotif", motif);
        model.addAttribute("listDestination", destination);

        return "dmd/newParcSuivant";
    }

    @RequestMapping(value = "/dmd/dmds/parc/ajouter", method = RequestMethod.POST)
    public String saveDmdUser(@Valid DmdParcDto dmdParcDto, Errors errors,  Model model, RedirectAttributes redirectAttributes, Principal principal) {

        if (errors.hasErrors()) {
            System.out.println("error YES");

            List<DmdParcDtoOut> dtos = new ArrayList<>();
            List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());
            // model.addAttribute("dmdParcDto", new DmdParcDtoOut());
            model.addAttribute("title", "Dmd - Nouveau");
            model.addAttribute("listMoyenDemandes", moyenDemandes);

            return "dmd/dmd/user";
        }

        //Get Employe by user connected
        Employe employe = employeService.getEmployeByUserName(principal.getName());

        //Set employe to DmDtoUser
        // dmdParcDto.setEmploye(employe);

        //Create Dmd and Employe Dmd on DataBase
        dmdService.createDmdParc(new DmdParcDtoW());

        List<EmployeDmdDto> employeDmdDtos = new ArrayList<>();
        //model.addAttribute("listDmd", employeDmdDtos);
        model.addAttribute("title", "Dmd - Nouveau");
        //Get information of flash message
        redirectAttributes.addFlashAttribute("messagesucces", "Opération de création éffectuée avec succès");

        return "redirect:/dmd/newParcSuivant";
    }

    @RequestMapping(value = "/dmd/dmds/user/save", method = RequestMethod.POST)
    public String saveDmdUser(@Valid @ModelAttribute("dmdUserDto") DmdUserDto dmdUserDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, Principal principal) {

        if (bindingResult.hasErrors()) {
            System.out.println("error YES");

            List<Statut> statuts = Arrays.asList(Statut.values());
            List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());
            //model.addAttribute("dmdUserDto", new DmdUserDto());
            model.addAttribute("title", "Dmd - Nouveau");
            model.addAttribute("listMoyenDemandes", moyenDemandes);
            model.addAttribute("listStatuts", statuts);

            return "dmd/new";
        }

        Employe employe = employeService.getEmployeByUserName(principal.getName());
        EmployeDmd dmd = new EmployeDmd();

        dmdUserDto.setId(dmd.getIdEmployeDmd());
        dmdUserDto.setEmploye(employe);

        EmployeDmd dmd1 = dmdService.createDmdUser(dmdUserDto);

        Long id = dmd1.getIdEmployeDmd();

        Direction direction = directionService.findById(1L);
        Employe employe1 = employeService.findByDirectionEstSuperieurHirarchique(direction,true);
        String from = employe.getEmail();
        //String to = employe1.getEmail();
        List<String> to = Arrays.asList(employe1.getEmail());

        //EmployeDmd dmd = dmdService.findById(id);


        String baseUrl = "http://localhost:8089/dmd/dmds/" + id;
        String sujet = "Demande de la validation d'une Demande de deplacement";
        //String Url = "/dmd/dmds"; // The relative URL you want to link to

        EmployeDmd employeDmd = dmdService.findById(id);
        String nom = employeDmd.getEmploye().toNomComplet();
        String moyen = employeDmd.getDmd().getMoyenDemande().toString();
        /*String[] moyen2 = moyen.split("_");
        StringBuilder MoyenD = new StringBuilder();

        for (String part : moyen2) {
            MoyenD.append(part).append(" ");
        }*/
        String motif = employeDmd.getMotif().getNomMotif();
        String destination = employeDmd.getDestination().getNomDestination();
        LocalDate date = employeDmd.getDmd().getDatePrevue();
        String heure = employeDmd.getDmd().getHeurePrevue().toString();

        String linkText = "Cliquez-ici pour la validation";

        String n = "Nom :" + nom + "<br>";
        String m1 = "Moyen :" + moyen.replace("_"," + ") + "<br>";
        String m2 = "Motif :" + motif + "<br>";
        String d = "Destination :" + destination + "<br>";
        DateTimeFormatter date1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date2 = date1.format(date);
        String d2 = "Date du rendez-vous: " + date2 + "<br>";
        String h = "Heure :" + heure + "<br><br>";

        String message = "Une Dmd a été créée  <br><br>" + n + m1 + m2 + d + d2 + h + "<a href='" + baseUrl  + "'>" + linkText + "</a>";


        try {
            notificationService.sendHtmlEmail(sujet, message,from,to);
            redirectAttributes.addFlashAttribute("messagesucces", "Opération de création effectuée avec succès");
        } catch (Exception e) {
            e.printStackTrace(); // Print the full exception stack trace for debugging
            redirectAttributes.addFlashAttribute("messageerror", "Erreur lors de l'envoi de l'email.");
        }


        redirectAttributes.addFlashAttribute("messagesucces", "Opération de création effectuée avec succès");

        return "redirect:/dmd/dmds";
    }

    @RequestMapping(value = "/dmd/dmds/users/valider/{id}", method = RequestMethod.POST)
    public String validerDmdUsers(@PathVariable Long id, RedirectAttributes redirectAttributes, Principal principal){

        //EmployeDmd employeDmd = dmdService.findById(id);

        /* employeDmd.setStatut(VALIDATION);*/
        //Get Employe by user connected
        Employe employe = employeService.getEmployeByUserName(principal.getName());
        EmployeDmd employeDmd = dmdService.validerDmd(id, employe);
        int Id = employeDmd.getIdEmployeDmd().intValue();
        String nom = employeDmd.getEmploye().toNomComplet();
        String moyen = employeDmd.getDmd().getMoyenDemande().toString();
        /*String[] moyen2 = moyen.split("_");
        StringBuilder MoyenD = new StringBuilder();


        for (String part : moyen2) {
            MoyenD.append(part).append(" + ");
        }*/
        String motif = employeDmd.getMotif().getNomMotif();
        String destination = employeDmd.getDestination().getNomDestination();
        LocalDate date = employeDmd.getDmd().getDatePrevue();
        String heure = employeDmd.getDmd().getHeurePrevue().toString();

        String i = ":" + Id ;
        String n = "Nom :" + nom + "<br>";
        String m1 = "Moyen :" + moyen.replace("_"," + ") + "<br>";
        String m2 = "Motif :" + motif + "<br>";
        String d = "Destination :" + destination + "<br>";
        DateTimeFormatter date1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date2 = date1.format(date);
        String d1 = "Date du rendez-vous :" + date2 + "<br>";
        String h = "Heure :" + heure + "<br><br>";

        //employeDmd.setResponsable(employe.getIdEmploye());
        employeDmdService.update(employeDmd);

        //Direction direction = directionService.findById(1L);
        //Employe employe1 = employeService.findByDirectionEstSuperieurHirarchique(direction,false);
        String from = employe.getEmail();
        String email = employeDmd.getEmploye().getEmail();
        AppRole appRole = roleService.getById(5L);
        List<UserRole> userRole =userRoleService.findByAppRoleIsNot(appRole);
        List<String> to = new ArrayList<>();

        to.add(email);

        for (UserRole role : userRole) {
            to.add(role.getAppUser().getEmploye().getEmail());
        }

        String baseUrl = "http://localhost:8089/dmd/dmds";
        String sujet = "Reception de la validation d'une Demande de deplacement";

        String linkText = "Cliquez-ici";
        String message = "La DMD " + i + " a été validée  <br><br>" + n + m1 + m2 + d + d1 + h + "Voir resultat - <a href='" + baseUrl + "'>" + linkText + "</a>";
        //String message1 = "Votre demande a ete valider";

        try {
            notificationService.sendHtmlEmail(sujet, message, from, to);
            redirectAttributes.addFlashAttribute("messagesucces", "Opération de création effectuée avec succès");

        } catch (Exception e) {
            e.printStackTrace(); // Print the full exception stack trace for debugging
            redirectAttributes.addFlashAttribute("messageerror", "Erreur lors de l'envoi de l'email.");
        }

        redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation éffectuée avec succès");

        return "redirect:/dmd/dmds";
        /*return "dmd/indexResponsable";*/
    }



    @RequestMapping(value = "/dmd/dmds/parc/save", method = RequestMethod.POST)
    public String saveDmdParc(@Valid DmdParcDto dmdParcDto, Errors errors,  Model model, RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            System.out.println("error YES");

            List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());
            model.addAttribute("dmdParcDto", new DmdParcDto());
            model.addAttribute("title", "Dmd - Nouveau");
            model.addAttribute("listMoyenDemandes", moyenDemandes);

            return "Dmd/dmd";
        }


        redirectAttributes.addFlashAttribute("messagesucces", "Opération de création éffectuée avec succès");
        return "redirect:/dmd/dmds";
    }

    @RequestMapping(value = "/dmd/dmds/user/edit/{id}", method = RequestMethod.GET)
    public String editDmdUser(@PathVariable Long id, Model model){

        //Get EmployeDmd By id
        EmployeDmd employeDmd = dmdService.findById(id);

        //Set DmdUserDto
        DmdUserDto dto = new DmdUserDto();

        dto.setId(employeDmd.getIdEmployeDmd());

        dto.setDatePrevue(employeDmd.getDmd().getDatePrevue());
        dto.setHeurePrevue(employeDmd.getDmd().getHeurePrevue());
        dto.setMoyenDemande(employeDmd.getDmd().getMoyenDemande().name());
        dto.setMotif(employeDmd.getMotif());
        dto.setDestination(employeDmd.getDestination());
        dto.setStatut(employeDmd.getStatut());
        dto.setResponsable(employeDmd.getResponsable());

        //Get Moyen demande list
        List<Statut> statuts = Arrays.asList(Statut.values());
        List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());
        List<Motif> motifs = motifService.all();
        List<Destination> destinations = destinationService.all();
        model.addAttribute("dmdUserDto", dto);
        model.addAttribute("listMoyenDemandes", moyenDemandes);
        model.addAttribute("title", "Dmd - Edition");
        model.addAttribute("listStatuts", statuts);
        model.addAttribute("listMotif",motifs);
        model.addAttribute("listDestination",destinations);

        return "dmd/edit";
    }

    @RequestMapping(value = "/dmd/dmds/user/update", method = RequestMethod.POST)
    public String updateDmdUser(@Valid @ModelAttribute("dmdUserDto") DmdUserDto dto, Errors errors,  Model model, RedirectAttributes redirectAttributes, Principal principal) {

        if (errors.hasErrors()) {

            //Get Moyen demande list
            List<Statut> statuts = Arrays.asList(Statut.values());
            List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());
            List<Motif> motifs = motifService.all();
            List<Destination> destinations = destinationService.all();
            //model.addAttribute("dmdUserDto", dto);
            model.addAttribute("listMoyenDemandes", moyenDemandes);
            model.addAttribute("title", "Dmd - Edition");
            model.addAttribute("listStatuts", statuts);
            model.addAttribute("listMotif",motifs);
            model.addAttribute("listDestination",destinations);

            return "dmd/edit";
        }

        String s = "";

        //Get Employe by user connected
        Employe employe = employeService.getEmployeByUserName(principal.getName());

        //Set employe to DmDtoUser
        dto.setEmploye(employe);
        //update Dmd and Employe Dmd on DataBase
        dmdService.updateDmdUser(dto);


        redirectAttributes.addFlashAttribute("messagesucces", "Opération de création éffectuée avec succès");
        return "redirect:/dmd/dmds";
    }

    @RequestMapping(value = "/dmd/dmds/user/print/{id}", method = RequestMethod.GET)
    public String printDmdUser(@PathVariable Long id, Model model){

        //Get EmployeDmd By id
        EmployeDmd employeDmd = dmdService.findById(id);

        //Set DmdUserDto
        DmdUserDtoS dto = new DmdUserDtoS();

        dto.setId(employeDmd.getIdEmployeDmd());
        dto.setEmploye(employeDmd.getEmploye());
        dto .setDatePrevue(employeDmd.getDmd().getDatePrevue());
        dto.setHeurePrevue(employeDmd.getDmd().getHeurePrevue());
        dto.setMoyenDemande(employeDmd.getDmd().getMoyenDemande().name());
        dto.setMotif(employeDmd.getMotif().getNomMotif());
        dto.setDestination(employeDmd.getDestination().getNomDestination());
        dto.setStatut(employeDmd.getStatut());
        dto.setDateOperation(employeDmd.getDmd().getDateOperation());
        dto.setResponsable(employeDmd.getResponsable());

        //Get Moyen demande list
        List<Statut> statuts = Arrays.asList(Statut.values());
        List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());
        model.addAttribute("dmdUserDto", dto);
        model.addAttribute("listMoyenDemandes", moyenDemandes);
        model.addAttribute("title", "Dmd - Edition");
        model.addAttribute("listStatuts", statuts);

        return "dmd/printDmd";
    }

    @RequestMapping(value = "/dmd/dmds/annulers/{id}", method = RequestMethod.GET)
    public String annulerDmdUser(@PathVariable Long id, Model model){

        //Get EmployeDmd By id
        EmployeDmd employeDmd = dmdService.findById(id);

        //Set DmdUserDto
        DmdUserDto dto = new DmdUserDto();

        dto.setId(employeDmd.getIdEmployeDmd());
        dto .setDatePrevue(employeDmd.getDmd().getDatePrevue());
        dto.setHeurePrevue(employeDmd.getDmd().getHeurePrevue());
        dto.setMoyenDemande(employeDmd.getDmd().getMoyenDemande().name());
        dto.setMotif(employeDmd.getMotif());
        dto.setDestination(employeDmd.getDestination());
        dto.setStatut(employeDmd.getStatut());
        dto.setResponsable(employeDmd.getResponsable());

        //Get Moyen demande list
        List<Statut> statuts = Arrays.asList(Statut.values());
        List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());
        model.addAttribute("dmdUserDto", dto);
        model.addAttribute("listMoyenDemandes", moyenDemandes);
        model.addAttribute("title", "Dmd - Edition");
        model.addAttribute("listStatuts", statuts);

        return "dmd/annulerdmd";
    }



    @RequestMapping(value = "/dmd/dmds/annuler", method = RequestMethod.POST)
    public String annuler(@Valid DmdUserDto dmdUserDto, RedirectAttributes redirectAttributes){

       dmdService.annulerDmdUser(dmdUserDto);

       redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation éffectuée avec succès");

       return "redirect:/dmd/dmds";

    }


    @RequestMapping(value = "/Attribution/attribution/user/editVC/{id}", method = RequestMethod.GET)
    public String AttributionUser(@PathVariable Long id, Model model){

        //Get EmployeDmd By id
        VehiculeChauffeurAtt vehiculeChauffeurAtt = attributionService.findById(id);

        //Set DmdUserDto
        AttributionDtoZ dto = new AttributionDtoZ();

        dto.setId(vehiculeChauffeurAtt.getIdAttribution());
        dto.setKilometrageDebut(vehiculeChauffeurAtt.getKilometrageDebut());
        dto.setKilometrageFin(vehiculeChauffeurAtt.getKilometrageFin());
        dto.setTypeAttribution(vehiculeChauffeurAtt.getTypeAttribution());
        dto.setDateAttribution(vehiculeChauffeurAtt.getDateAttribution());
        dto.setEmploye(vehiculeChauffeurAtt.getEmploye());
        //dto.setEmployeDmd(vehiculeChauffeurAtt.getEmployeDmd());
        //dto.setDetailVehiculeChauffeurA(vehiculeChauffeurAtt.getDetailVehiculeChauffeurA());
        List<DetailVehiculeChauffeurA> detailVehiculeChauffeurAList = vehiculeChauffeurAtt.getDetailVehiculeChauffeurA();
        dto.setDetailVehiculeChauffeurA(new ArrayList<>(detailVehiculeChauffeurAList));
        dto.setVehicule(vehiculeChauffeurAtt.getVehicule());
        dto.setDateDeDepart(vehiculeChauffeurAtt.getDateDeDepart());
        dto.setDateArrivee(vehiculeChauffeurAtt.getDateArrivee());
        dto.setStatutChauffeurA(vehiculeChauffeurAtt.getStatutChauffeurA());
        //dto.setMotif(vehiculeChauffeurAtt.getMotif());
        dto.setObservation(vehiculeChauffeurAtt.getObservation());

        //Get Moyen demande list
        List<Employe> chauffeur = employeService.findEmployesByEstChauffeur(true);
        List<Vehicule> vehicule = vehiculeService.all();
        List<EmployeDmd> employeDmds = employeDmdService.all();
        List<Motifs> motif = Arrays.asList(Motifs.values());
        List<MotifChauffeur> motifChauffeur = Arrays.asList(MotifChauffeur.values());
        List<TypeAttribution> typeAttribution = Arrays.asList(TypeAttribution.values());


        model.addAttribute("attributionDto",dto);
        model.addAttribute("listChauffeur",chauffeur);
        model.addAttribute("listVehicule",vehicule);
        model.addAttribute("listEmployee",employeDmds);
        model.addAttribute("listMotif",motif);
        model.addAttribute("listMotifChauffeur",motifChauffeur);
        model.addAttribute("listTypeAttribution",typeAttribution);

        model.addAttribute("title", "Dmd - Edition");

        return "Attribution/edit2";
    }

    @RequestMapping(value = "/Attribution/attribution/user/editVC2/{id}", method = RequestMethod.GET)
    public String AttributionUser2(@PathVariable Long id, Model model){

        //Get EmployeDmd By id
        VehiculeChauffeurAtt vehiculeChauffeurAtt = attributionService.findById(id);

        //Set DmdUserDto
        AttributionDtoZ dto = new AttributionDtoZ();

        dto.setId(vehiculeChauffeurAtt.getIdAttribution());
        dto.setDateAttribution(vehiculeChauffeurAtt.getDateAttribution());
        dto.setDateDeDepart(vehiculeChauffeurAtt.getDateDeDepart());
        dto.setEmploye(vehiculeChauffeurAtt.getEmploye());
        dto.setKilometrageFin(vehiculeChauffeurAtt.getKilometrageFin());
        //dto.setEmployeDmd(vehiculeChauffeurAtt.getEmployeDmd());
        //dto.setDetailVehiculeChauffeurA(vehiculeChauffeurAtt.getDetailVehiculeChauffeurA());
        List<DetailVehiculeChauffeurA> detailVehiculeChauffeurAList = vehiculeChauffeurAtt.getDetailVehiculeChauffeurA();
        dto.setDetailVehiculeChauffeurA(new ArrayList<>(detailVehiculeChauffeurAList));
        dto.setVehicule(vehiculeChauffeurAtt.getVehicule());

        dto.setStatutAttrib(vehiculeChauffeurAtt.getStatutAttrib());


        //Get Moyen demande list
        List<Employe> chauffeur = employeService.findEmployesByEstChauffeur(true);
        List<Vehicule> vehicule = vehiculeService.all();
        //List<EmployeDmd> employeDmds = employeDmdService.all();
        List<StatutAttrib> statutAttrib = Arrays.asList(StatutAttrib.values());
        model.addAttribute("attributionDto",dto);
        model.addAttribute("listChauffeur",chauffeur);
        model.addAttribute("listVehicule",vehicule);
        //model.addAttribute("listEmployee",employeDmds);
        model.addAttribute("listStatutAttrib",statutAttrib);
        model.addAttribute("title", "Dmd - Edition");

        return "Attribution/edit";
    }

    @RequestMapping(value = "/Attribution/attribution/user/commencerVehiculeChauffeur", method = RequestMethod.POST)
    public String CommencerCourse(@Valid @ModelAttribute("attributionDto") AttributionDtoZ attributionDtoZ, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
       /* if (bindingResult.hasErrors()) {
            System.out.println("error YES");

            //Get Moyen demande list
            List<Employe> chauffeur = employeService.findEmployesByEstChauffeur(true);
            List<Vehicule> vehicule = vehiculeService.all();
            //List<EmployeDmd> employeDmds = employeDmdService.all();
            List<Motifs> motif = Arrays.asList(Motifs.values());
            List<MotifChauffeur> motifChauffeur = Arrays.asList(MotifChauffeur.values());
            List<TypeAttribution> typeAttribution = Arrays.asList(TypeAttribution.values());


            //model.addAttribute("attributionDto",dto);
            //model.addAttribute("attributionDto", attributionDtoZ);
            model.addAttribute("listChauffeur",chauffeur);
            model.addAttribute("listVehicule",vehicule);
            //model.addAttribute("listEmployee",employeDmds);
            model.addAttribute("listMotif",motif);
            model.addAttribute("listMotifChauffeur",motifChauffeur);
            model.addAttribute("listTypeAttribution",typeAttribution);

            model.addAttribute("title", "Dmd - Edition");

            return "Attribution/edit2";

        }*/

        attributionService.updateCommencerLaCourseVehiculeChauffeur(attributionDtoZ);

        redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation éffectuée avec succès");

        return "redirect:/Attribution/attribution";

    }

    @RequestMapping(value = "/Attribution/attribution/user/terminée", method = RequestMethod.POST)
    public String saveAttributionUser(@Valid @ModelAttribute("attributionDto") AttributionDtoZ attributionDtoZ, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        /*if (bindingResult.hasErrors()) {
            System.out.println("error YES");

            List<Employe> chauffeur = employeService.findEmployesByEstChauffeur(true);
            List<Vehicule> vehicule = vehiculeService.all();
            List<EmployeDmd> employeDmds = employeDmdService.all();
            List<StatutAttrib> statutAttrib = Arrays.asList(StatutAttrib.values());
            //model.addAttribute("attributionDto",dto);
            model.addAttribute("listChauffeur",chauffeur);
            model.addAttribute("listVehicule",vehicule);
            model.addAttribute("listEmployee",employeDmds);
            model.addAttribute("listStatutAttrib",statutAttrib);
            model.addAttribute("title", "Dmd - Edition");

            return "Attribution/edit";

        }*/

        //Create Dmd and Employe Dmd on DataBase
        attributionService.updateTerminerLaCourseVehiculeChauffeur(attributionDtoZ);

        //Get information of flash message
        redirectAttributes.addFlashAttribute("messagesucces", "Opération de création éffectuée avec succès");

        return "redirect:/Attribution/attribution";
    }

    @RequestMapping(value = "/Attribution/attribution/user/editVE/{id}", method = RequestMethod.GET)
    public String AttributionUserVE(@PathVariable Long id, Model model){

        //Get EmployeDmd By id
        VehiculeAtt vehiculeAtt = attributionService.findVEById(id);

        //Set DmdUserDto
        AttributionDtoZ dto = new AttributionDtoZ();

        dto.setId(vehiculeAtt.getIdAttribution());
        dto.setTypeAttribution(vehiculeAtt.getTypeAttribution());
        dto.setDateAttribution(vehiculeAtt.getDateAttribution());
        //dto.setEmploye(vehiculeAtt.getEmploye());
        dto.setDetailVehiculeA(vehiculeAtt.getDetailVehiculeA());
        dto.setKilometrageFin(vehiculeAtt.getKilometrageFin());
        dto.setVehicule(vehiculeAtt.getVehicule());
        dto.setDateDeDepart(vehiculeAtt.getDateDeDepart());
        dto.setDateArrivee(vehiculeAtt.getDateArrivee());
        //dto.setStatutChauffeurA(vehiculeAtt.getStatutChauffeurA());
        //dto.setMotif(vehiculeAtt.getMotif());
        dto.setObservation(vehiculeAtt.getObservation());

        //Get Moyen demande list
        List<Employe> chauffeur = employeService.findEmployesByEstChauffeur(true);
        List<Vehicule> vehicule = vehiculeService.all();
        List<EmployeDmd> employeDmds = employeDmdService.all();
        List<Motifs> motif = Arrays.asList(Motifs.values());
        List<MotifChauffeur> motifChauffeur = Arrays.asList(MotifChauffeur.values());
        List<TypeAttribution> typeAttribution = Arrays.asList(TypeAttribution.values());


        model.addAttribute("attributionDto",dto);
        model.addAttribute("listChauffeur",chauffeur);
        model.addAttribute("listVehicule",vehicule);
        model.addAttribute("listEmployee",employeDmds);
        model.addAttribute("listMotif",motif);
        model.addAttribute("listMotifChauffeur",motifChauffeur);
        model.addAttribute("listTypeAttribution",typeAttribution);

        model.addAttribute("title", "Dmd - Edition");

        return "Attribution/edit3";

    }

    @RequestMapping(value = "/Attribution/attribution/user/commencerVehicule", method = RequestMethod.POST)
    public String CommencerCourseVE(@Valid @ModelAttribute("attributionDto") AttributionDtoZ attributionDtoZ, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){

        /*if (bindingResult.hasErrors()) {
            System.out.println("error YES");

            List<Employe> chauffeur = employeService.findEmployesByEstChauffeur(true);
            List<Vehicule> vehicule = vehiculeService.all();
            List<EmployeDmd> employeDmds = employeDmdService.all();
            List<Motifs> motif = Arrays.asList(Motifs.values());
            List<MotifChauffeur> motifChauffeur = Arrays.asList(MotifChauffeur.values());
            List<TypeAttribution> typeAttribution = Arrays.asList(TypeAttribution.values());


            //model.addAttribute("attributionDto",dto);
            model.addAttribute("listChauffeur",chauffeur);
            model.addAttribute("listVehicule",vehicule);
            model.addAttribute("listEmployee",employeDmds);
            model.addAttribute("listMotif",motif);
            model.addAttribute("listMotifChauffeur",motifChauffeur);
            model.addAttribute("listTypeAttribution",typeAttribution);

            model.addAttribute("title", "Dmd - Edition");

            return "Attribution/edit3";

        }*/

        attributionService.updateCommencerLaCourseVehicule(attributionDtoZ);

        redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation éffectuée avec succès");

        return "redirect:/Attribution/attribution";

    }

    @RequestMapping(value = "/Attribution/attribution/user/editVE2/{id}", method = RequestMethod.GET)
    public String AttributionUserVE2(@PathVariable Long id, Model model){

        VehiculeAtt vehiculeAtt = attributionService.findVEById(id);

        //Set DmdUserDto
        AttributionDtoZ dto = new AttributionDtoZ();

        dto.setId(vehiculeAtt.getIdAttribution());
        dto.setTypeAttribution(vehiculeAtt.getTypeAttribution());
        dto.setDateAttribution(vehiculeAtt.getDateAttribution());
        dto.setKilometrageFin(vehiculeAtt.getKilometrageFin());
        //dto.setEmploye(vehiculeAtt.getEmploye());
        //dto.setEmployeDmd(vehiculeAtt.getEmployeDmd());
        dto.setDetailVehiculeA(vehiculeAtt.getDetailVehiculeA());
        dto.setVehicule(vehiculeAtt.getVehicule());
        dto.setDateDeDepart(vehiculeAtt.getDateDeDepart());
        dto.setDateArrivee(vehiculeAtt.getDateArrivee());
        //dto.setStatutChauffeurA(vehiculeAtt.getStatutChauffeurA());
        //dto.setMotif(vehiculeAtt.getMotif());
        dto.setObservation(vehiculeAtt.getObservation());


        //Get Moyen demande list
        //List<Employe> chauffeur = employeService.findEmployesByEstChauffeur(true);
        List<Vehicule> vehicule = vehiculeService.all();
        List<EmployeDmd> employeDmds = employeDmdService.all();
        List<StatutAttrib> statutAttrib = Arrays.asList(StatutAttrib.values());
        model.addAttribute("attributionDto",dto);
        //model.addAttribute("listChauffeur",chauffeur);
        model.addAttribute("listVehicule",vehicule);
        model.addAttribute("listEmployee",employeDmds);
        model.addAttribute("listStatutAttrib",statutAttrib);
        model.addAttribute("title", "Dmd - Edition");

        return "Attribution/edit4";
    }

    @RequestMapping(value = "/Attribution/attribution/user/terminéeVehicule", method = RequestMethod.POST)
    public String saveAttributionUserVE(@Valid @ModelAttribute("attributionDto") AttributionDtoZ attributionDtoZ, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        /*if (bindingResult.hasErrors()) {
            System.out.println("error YES");

            //Get Moyen demande list
            //List<Employe> chauffeur = employeService.findEmployesByEstChauffeur(true);
            List<Vehicule> vehicule = vehiculeService.all();
            List<EmployeDmd> employeDmds = employeDmdService.all();
            List<StatutAttrib> statutAttrib = Arrays.asList(StatutAttrib.values());
            //model.addAttribute("attributionDto",dto);
            //model.addAttribute("listChauffeur",chauffeur);
            model.addAttribute("listVehicule",vehicule);
            model.addAttribute("listEmployee",employeDmds);
            model.addAttribute("listStatutAttrib",statutAttrib);
            model.addAttribute("title", "Dmd - Edition");

            return "Attribution/edit4";

        }*/

        //Create Dmd and Employe Dmd on DataBase
        attributionService.updateTerminerLaCourseVehicule(attributionDtoZ);

        //Get information of flash message
        redirectAttributes.addFlashAttribute("messagesucces", "Opération de création éffectuée avec succès");

        return "redirect:/Attribution/attribution";
    }

    @RequestMapping(value = "/Attribution/attribution/user/editCC/{id}", method = RequestMethod.GET)
    public String AttributionUserCC(@PathVariable Long id, Model model){

        CarburantAtt carburantAtt = attributionService.findCCById(id);

        //Set DmdUserDto
        AttributionDtoZ dto = new AttributionDtoZ();

        dto.setId(carburantAtt.getIdAttribution());
        dto.setTypeAttribution(carburantAtt.getTypeAttribution());
        dto.setDateAttribution(carburantAtt.getDateAttribution());
        dto.setMontant(carburantAtt.getMontant());
        //dto.setEmploye(vehiculeAtt.getEmploye());
        //dto.setEmployeDmd(carburantAtt.getEmployeDmd());
        dto.setDetailCarburantA(carburantAtt.getDetailCarburantA());
        //dto.setVehicule(carburantAtt.getVehicule());
        dto.setDateDeDepart(carburantAtt.getDateDeDepart());
        //dto.setDateArrivee(carburantAtt.getDateArrivee());
        //dto.setStatutChauffeurA(vehiculeAtt.getStatutChauffeurA());
        //dto.setMotif(carburantAtt.getMotif());
        //dto.setObservation(carburantAtt.getObservation());


        //Get Moyen demande list
        //List<Employe> chauffeur = employeService.findEmployesByEstChauffeur(true);
        //List<Vehicule> vehicule = vehiculeService.findVehiculesByStatutVehicule(EN_COURSE);
        List<EmployeDmd> employeDmds = employeDmdService.all();
        List<StatutAttrib> statutAttrib = Arrays.asList(StatutAttrib.values());
        List<TypeAttribution> typeAttribution = Arrays.asList(TypeAttribution.values());
        model.addAttribute("attributionDto",dto);
        //model.addAttribute("listChauffeur",chauffeur);
        //model.addAttribute("listVehicule",vehicule);
        model.addAttribute("listEmployee",employeDmds);
        model.addAttribute("listStatutAttrib",statutAttrib);
        model.addAttribute("listTypeAttribution",typeAttribution);
        model.addAttribute("title", "Dmd - Edition");

        return "Attribution/edit5";
    }

    @RequestMapping(value = "/Attribution/attribution/user/termineeCarburant", method = RequestMethod.POST)
    public String saveAttributionUserCC(@Valid @ModelAttribute("attributionDto") AttributionDtoZ attributionDtoZ, BindingResult bindingResult, @RequestParam("recuCarburant") MultipartFile file,
                                         RedirectAttributes redirectAttributes, Model model) {
        /*if (bindingResult.hasErrors()) {
            System.out.println("error YES");

            //Get Moyen demande list
            //List<Employe> chauffeur = employeService.findEmployesByEstChauffeur(true);
            //List<Vehicule> vehicule = vehiculeService.findVehiculesByStatutVehicule(EN_COURSE);
            List<EmployeDmd> employeDmds = employeDmdService.all();
            List<StatutAttrib> statutAttrib = Arrays.asList(StatutAttrib.values());
            List<TypeAttribution> typeAttribution = Arrays.asList(TypeAttribution.values());
            //model.addAttribute("attributionDto",dto);
            //model.addAttribute("listChauffeur",chauffeur);
            //model.addAttribute("listVehicule",vehicule);
            model.addAttribute("listEmployee",employeDmds);
            model.addAttribute("listStatutAttrib",statutAttrib);
            model.addAttribute("listTypeAttribution",typeAttribution);
            model.addAttribute("title", "Dmd - Edition");

            return "Attribution/edit5";

        }*/


        String UPLOADED_FOLDER1 = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\temp\\";
        //String UPLOADED_FOLDER = "C:\\Users\\PC\\Desktop\\parcauto-master\\src\\main\\resources\\static\\temp\\";
        String UPLOADED_FOLDER = UPLOADED_FOLDER1.replace("\\", "\\\\");
        //String up = "src/resources/static/temp";
        CarburantAtt carburantAtt = carburantAttRepository.getById(attributionDtoZ.getId());

        carburantAtt.setTypeAttribution(attributionDtoZ.getTypeAttribution());
        carburantAtt.setDateAttribution(attributionDtoZ.getDateAttribution());
        carburantAtt.setDateDeDepart(attributionDtoZ.getDateDeDepart());
        carburantAtt.setDateArrivee(attributionDtoZ.getDateArrivee());
        carburantAtt.setStatutAttrib(TERMINEE);

        String a = "";

        LocalDateTime dateTime = LocalDateTime.now();
        String dateString = dateTime.toString();
        String fileName = dateString + file.getOriginalFilename();
        String originalFilename = file.getOriginalFilename();
        String sanitizedFilename = dateString.replace(":", "") + originalFilename;
        try {

            // Get the file and save it somewhere

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + sanitizedFilename);
            //Path path = Paths.get(up + sanitizedFilename);
            //Path path = Paths.get(this.getClass().getResource("/static/temp").getPath() + sanitizedFilename);
            Files.write(path, bytes);
            String s = "";



        } catch (IOException e) {
            e.printStackTrace();
        }
        //carburantAtt.setRecuCarburant(sanitizedFilename);

        carburantAtt.setRecuCarburant(sanitizedFilename + "?t=" + System.currentTimeMillis());
       //attributionDtoZ.setRecuCarburant(fileName);


        carburantAtt.setImmatriculationVehicule(attributionDtoZ.getImmatriculationVehicule());
        carburantAtt.setLitre(attributionDtoZ.getLitre());
        carburantAtt.setMontant(attributionDtoZ.getMontant());


        attributionRepository.save(carburantAtt);

        //Create Dmd and Employe Dmd on DataBase
        //attributionService.updateTerminerLaCourseCarburant(attributionDtoZ);

        //Get information of flash message
        redirectAttributes.addFlashAttribute("messagesucces", "Opération de création éffectuée avec succès");

        return "redirect:/Attribution/attribution";

    }



    @RequestMapping(value = "/dmd/dmds/attribution", method = RequestMethod.GET)
    public String attributionDmdUser(Model model, Principal principal, HttpServletRequest request){


        //Get EmployeDmd By id
        //EmployeDmd employeDmd = dmdService.findById(id);

        //Set DmdUserDto
        //List<EmployeDmd> employeDmd = dmdService.getById(id);

        //List<DmdUserDtoOut> dto = dmdService.listDmdsToDto(employeDmd);


        DmdUserDto dto = new DmdUserDto();

        /*dto.setId(employeDmd.getIdEmployeDmd());
        dto .setDatePrevue(employeDmd.getDmd().getDatePrevue());
        dto.setHeurePrevue(employeDmd.getDmd().getHeurePrevue());
        dto.setMoyenDemande(employeDmd.getDmd().getMoyenDemande().name());
        dto.setMotif(employeDmd.getMotifDmd());
        dto.setDestination(employeDmd.getDestination());
        dto.setStatut(employeDmd.getStatut());
        dto.setEmploye(employeDmd.getEmploye());*/

        //Get Moyen demande
        List<Employe> employes = employeService.findEmployesEstChauffeurStatutChauffeur(true,StatutChauffeur.DISPONIBLE);

        //AFFICHER QUE LES VEHICULES DISPONIBLE
        List<Vehicule> vehicules = vehiculeService.findVehiculesByStatutVehicule(DISPONIBLE);

        List<Statut> statuts = Arrays.asList(Statut.values());
        List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());

        model.addAttribute("dmdUserDto", dto);
        model.addAttribute("listMoyenDemandes", moyenDemandes);
        model.addAttribute("title", "Dmd - Attribution");
        model.addAttribute("listChauffeur",employes);
        model.addAttribute("listVehicule", vehicules);
        //model.addAttribute("listStatuts", statuts);

        return "dmd/attributionVehicule";

    }

    @RequestMapping(value = "/dmd/dmds/attributions", method = RequestMethod.GET)
    public String attributionDmdUserVehiculeChauffeur(Model model, Principal principal, HttpServletRequest request){


        //Get EmployeDmd By id
        //EmployeDmd employeDmd = dmdService.findById(id);

        //Set DmdUserDto
        //List<EmployeDmd> employeDmd = dmdService.getById(id);

        //List<DmdUserDtoOut> dto = dmdService.listDmdsToDto(employeDmd);


        DmdUserDto dto = new DmdUserDto();

        /*dto.setId(employeDmd.getIdEmployeDmd());
        dto .setDatePrevue(employeDmd.getDmd().getDatePrevue());
        dto.setHeurePrevue(employeDmd.getDmd().getHeurePrevue());
        dto.setMoyenDemande(employeDmd.getDmd().getMoyenDemande().name());
        dto.setMotif(employeDmd.getMotifDmd());
        dto.setDestination(employeDmd.getDestination());
        dto.setStatut(employeDmd.getStatut());
        dto.setEmploye(employeDmd.getEmploye());*/

        //Get Moyen demande
        List<Employe> employes = employeService.findEmployesEstChauffeurStatutChauffeur(true,StatutChauffeur.DISPONIBLE);

        //AFFICHER QUE LES VEHICULES DISPONIBLE
        List<Vehicule> vehicules = vehiculeService.findVehiculesByStatutVehicule(DISPONIBLE);

        List<Statut> statuts = Arrays.asList(Statut.values());
        List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());

        model.addAttribute("dmdUserDto", dto);
        model.addAttribute("listMoyenDemandes", moyenDemandes);
        model.addAttribute("title", "Dmd - Attribution");
        model.addAttribute("listChauffeur",employes);
        model.addAttribute("listVehicule", vehicules);
        //model.addAttribute("listStatuts", statuts);

        return "dmd/attributionVehiculeChauf";

    }


    @RequestMapping(value = "/dmd/dmds/attributionss", method = RequestMethod.GET)
    public String attributionDmdUserCarburant(Model model, Principal principal, HttpServletRequest request){


        //Get EmployeDmd By id
        //EmployeDmd employeDmd = dmdService.findById(id);

        //Set DmdUserDto
        //List<EmployeDmd> employeDmd = dmdService.getById(id);

        //List<DmdUserDtoOut> dto = dmdService.listDmdsToDto(employeDmd);


        DmdUserDto dto = new DmdUserDto();

        /*dto.setId(employeDmd.getIdEmployeDmd());
        dto .setDatePrevue(employeDmd.getDmd().getDatePrevue());
        dto.setHeurePrevue(employeDmd.getDmd().getHeurePrevue());
        dto.setMoyenDemande(employeDmd.getDmd().getMoyenDemande().name());
        dto.setMotif(employeDmd.getMotifDmd());
        dto.setDestination(employeDmd.getDestination());
        dto.setStatut(employeDmd.getStatut());
        dto.setEmploye(employeDmd.getEmploye());*/

        //Get Moyen demande
        List<Employe> employes = employeService.findEmployesEstChauffeurStatutChauffeur(true,StatutChauffeur.DISPONIBLE);

        //AFFICHER QUE LES VEHICULES DISPONIBLE
        List<Vehicule> vehicules = vehiculeService.findVehiculesByStatutVehicule(DISPONIBLE);

        List<Statut> statuts = Arrays.asList(Statut.values());
        List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());

        model.addAttribute("dmdUserDto", dto);
        model.addAttribute("listMoyenDemandes", moyenDemandes);
        model.addAttribute("title", "Dmd - Attribution");
        model.addAttribute("listChauffeur",employes);
        model.addAttribute("listVehicule", vehicules);
        //model.addAttribute("listStatuts", statuts);

        return "dmd/attributionCarburant";

    }

    @RequestMapping(value = "/Attribution/attribution")
    public String attributionDmd(Model model,Principal principal, HttpServletRequest request){

       Employe employe = employeService.getEmployeByUserName(principal.getName());

        String roleParcauto = "ROLE_PARCAUTO";
        String roleAdmin = "ROLE_ADMIN";
        String roleMoyenGeneraux = "ROLE_MOYEN-GENERAUX";

        List<AttributionDtoOut> dtoss = new ArrayList<>();



        if (request.isUserInRole(roleParcauto)) {

            List<VehiculeAtt> vehiculeAtts = attributionService.allAttributionVehicule()
                    .stream()
                    .filter(VehiculeAtt -> VehiculeAtt.getStatutAttrib() == StatutAttrib.EN_ATTENTE || VehiculeAtt.getStatutAttrib() == StatutAttrib.EN_COURSE)
                    .sorted(Comparator.comparing(Attribution::getStatutAttrib)
                            .reversed())
                    .collect(Collectors.toList());
            dtoss = attributionService.listAttributionToDtoVehicule(vehiculeAtts);

            List<VehiculeChauffeurAtt> vehiculeChauffeurAtts = attributionService.allAttributionVehiculeChauffeur()
                    .stream()
                    .filter(VehiculeChauffeurAtt -> VehiculeChauffeurAtt.getStatutAttrib() == StatutAttrib.EN_ATTENTE || VehiculeChauffeurAtt.getStatutAttrib() == StatutAttrib.EN_COURSE)
                    .sorted(Comparator.comparing(Attribution::getStatutAttrib)
                            .reversed())
                    .collect(Collectors.toList());
            dtoss = attributionService.listAttributionToDtoVehiculeChauffeur(vehiculeAtts,vehiculeChauffeurAtts);

        } else if (request.isUserInRole(roleAdmin)) { // Afficher tout les attributions dans la partie administrateur

            List<Attribution> attribution = attributionService.all();
            dtoss = attributionService.listAttributionToDto(attribution);

        } else if (request.isUserInRole(roleMoyenGeneraux)) {
            List<CarburantAtt> carburantAtts = attributionService.allAttribution()
                    .stream()
                    .filter(CarburantAtt -> CarburantAtt.getStatutAttrib() == StatutAttrib.EN_ATTENTE || CarburantAtt.getStatutAttrib() == StatutAttrib.EN_COURSE)
                    .sorted(Comparator.comparing(Attribution::getStatutAttrib)
                            .reversed())
                    .collect(Collectors.toList());
            dtoss = attributionService.listAttributionToDtoCarburant(carburantAtts);

        } /*else {

            List<Attribution> attributions = attributionService.findAttributionsByEmployeDmdEmploye(employe)
                    .stream()
                    .filter(Attribution -> Attribution.getStatutAttrib() == StatutAttrib.EN_ATTENTE || Attribution.getStatutAttrib() == StatutAttrib.EN_COURSE)
                    .sorted(Comparator.comparing(Attribution::getStatutAttrib)
                            .reversed())
                    .collect(Collectors.toList());
            dtoss = attributionService.listAttributionToDto(attributions);
        }*/


        model.addAttribute("listAttribution", dtoss);

        return "Attribution/index";

    }

    @RequestMapping(value = "/Attribution/attributionTerminée")
    public String attributionDmdHistorique(Model model,Principal principal, HttpServletRequest request){

        Employe employe = employeService.getEmployeByUserName(principal.getName());

        String roleParcauto = "ROLE_PARCAUTO";
        String roleAdmin = "ROLE_ADMIN";
        String roleMoyenGeneraux = "ROLE_MOYEN-GENERAUX";

        List<AttributionDtoOut> dtoss = new ArrayList<>();


        if (request.isUserInRole(roleParcauto)) {

            List<VehiculeAtt> vehiculeAtts = attributionService.allAttributionVehicule()
                    .stream()
                    .filter(VehiculeAtt -> VehiculeAtt.getStatutAttrib() == TERMINEE || VehiculeAtt.getStatutAttrib() == StatutAttrib.ANNULER)
                    .sorted(Comparator.comparing(Attribution::getStatutAttrib)
                            .reversed())
                    .collect(Collectors.toList());
            dtoss = attributionService.listAttributionToDtoVehicule(vehiculeAtts);

            List<VehiculeChauffeurAtt> vehiculeChauffeurAtts = attributionService.allAttributionVehiculeChauffeur()
                    .stream()
                    .filter(VehiculeChauffeurAtt -> VehiculeChauffeurAtt.getStatutAttrib() == TERMINEE || VehiculeChauffeurAtt.getStatutAttrib() == StatutAttrib.ANNULER)
                    .sorted(Comparator.comparing(Attribution::getStatutAttrib)
                            .reversed())
                    .collect(Collectors.toList());
            dtoss = attributionService.listAttributionToDtoVehiculeChauffeur(vehiculeAtts,vehiculeChauffeurAtts);
            List<CarburantAtt> carburantAtts = attributionService.allAttribution()
                    .stream()
                    .filter(att -> att.getStatutAttrib() == TERMINEE || att.getStatutAttrib() == StatutAttrib.ANNULER)
                    .sorted(Comparator.comparing(Attribution::getStatutAttrib).reversed())
                    .collect(Collectors.toList());
            dtoss.addAll(attributionService.listAttributionToDtoCarburant(carburantAtts));

        } else if (request.isUserInRole(roleAdmin)) { // Afficher tout les attributions dans la partie administrateur

            List<Attribution> attribution = attributionService.all();
            dtoss = attributionService.listAttributionToDto(attribution);

        } else if (request.isUserInRole(roleMoyenGeneraux)) {
            List<CarburantAtt> carburantAtts = attributionService.allAttribution()
                    .stream()
                    .filter(CarburantAtt -> CarburantAtt.getStatutAttrib() == TERMINEE || CarburantAtt.getStatutAttrib() == StatutAttrib.ANNULER)
                    .sorted(Comparator.comparing(Attribution::getStatutAttrib)
                            .reversed())
                    .collect(Collectors.toList());
            dtoss = attributionService.listAttributionToDtoCarburant(carburantAtts);

        } /*else {

            List<Attribution> attributions = attributionService.findAttributionsByEmployeDmdEmploye(employe)
                    .stream()
                    .filter(Attribution -> Attribution.getStatutAttrib() == TERMINEE || Attribution.getStatutAttrib() == StatutAttrib.ANNULER)
                    .sorted(Comparator.comparing(Attribution::getStatutAttrib)
                            .reversed())
                    .collect(Collectors.toList());
            //dtoss = attributionService.listAttributionToDto(attributions);
        }*/

        model.addAttribute("listAttribution", dtoss);

        return "Attribution/HistoriqueAttribution";

    }



    @RequestMapping(value = "/dmd/dmdVehicule")
    public String DmdVehicule(Model model, Principal principal, HttpServletRequest request){

        List<DmdUserDtoOut> dtos = new ArrayList<>();
        List<EmployeDmd> employeDmds = dmdService.findEmployeDmdsByDmdMoyenDemandeStatut(MoyenDemande.VEHICULES, VALIDATION);
        dtos = dmdService.listDmdsToDto(employeDmds);

        model.addAttribute("listDmdVehicule", dtos);

        return "dmd/indexVehicule";

    }

    @RequestMapping(value = "/dmd/dmdVehiculeChauffeur")
    public String DmdVehiculeChauffeur(Model model, Principal principal, HttpServletRequest request){

        List<DmdUserDtoOut> dtos = new ArrayList<>();
        List<EmployeDmd> employeDmds = dmdService.listEmployeDmdsByStatut(VALIDATION);
        dtos = dmdService.listDmdsToDto(employeDmds);

        model.addAttribute("listDmdVehiculeChauf", dtos);

        return "dmd/indexVehiculeChauf";
    }

    @RequestMapping(value = "/dmd/dmdCarburant")
    public String DmdCarburant(Model model, Principal principal, HttpServletRequest request){

        List<DmdUserDtoOut> dtos = new ArrayList<>();
        List<EmployeDmd> employeDmds = dmdService.findEmployeDmdsByDmdMoyenDemandeStatut(MoyenDemande.ORIENTATION_TRANSPORT, VALIDATION);
        dtos = dmdService.listDmdsToDto(employeDmds);

        model.addAttribute("listDmdCarburant", dtos);

        return "dmd/indexCarburant";
    }




    @RequestMapping(value = "/dmd/dmds/user/valider/{id}", method = RequestMethod.GET)
    public String validerDmdUser(@PathVariable Long id, RedirectAttributes redirectAttributes, Principal principal){

        //EmployeDmd employeDmd = dmdService.findById(id);

        /* employeDmd.setStatut(VALIDATION);*/
        //Get Employe by user connected
        Employe employe = employeService.getEmployeByUserName(principal.getName());
        EmployeDmd employeDmd = dmdService.validerDmd(id, employe);
        //employeDmd.setResponsable(employe.getIdEmploye());
        employeDmdService.update(employeDmd);

        redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation éffectuée avec succès");

        return "redirect:/dmd/dmds";
        /*return "/dmd/index";*/
    }



    @RequestMapping(value = "/dmd/dmds/user/annuler/{id}", method = RequestMethod.GET)
    public String annulerDmdUser(@PathVariable Long id,@Valid DmdUserDto dmdUserDto,  Model model, RedirectAttributes redirectAttributes, Principal principal){

        //EmployeDmd employeDmd = dmdService.findById(id);

        /* employeDmd.setStatut(VALIDATION);*/
        //Get Employe by user connected
        EmployeDmd employeDmd2 = dmdService.findById(id);
        dmdService.annulerDmd(id, employeDmd2);
        //employeDmd.setResponsable(employe.getIdEmploye());
        //employeDmdRepository.save(employeDmd1);

        redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation éffectuée avec succès");

        return "redirect:/dmd/dmdVehiculeChauffeur";

    }

    @RequestMapping(value = "/dmd/dmds/user/refuser/{id}", method = RequestMethod.GET)
    public String refuserDmdUser(@PathVariable Long id,@Valid DmdUserDto dmdUserDto,  Model model, RedirectAttributes redirectAttributes, Principal principal){

        EmployeDmd employeDmd = dmdService.findById(id);


        DmdUserDto dto = new DmdUserDto();

        dto.setId(employeDmd.getIdEmployeDmd());
        dto .setDatePrevue(employeDmd.getDmd().getDatePrevue());
        dto.setHeurePrevue(employeDmd.getDmd().getHeurePrevue());
        dto.setMoyenDemande(employeDmd.getDmd().getMoyenDemande().name());
        dto.setMotif(employeDmd.getMotif());
        dto.setDestination(employeDmd.getDestination());


        dto.setStatut(REFUS);
        //Create EmployeDmd on Database.
        EmployeDmd dmd2 = dmdService.updateDmdUser(dto);



        //dto.setEmploye(employeDmd.getEmploye());
        List<Statut> statuts = Arrays.asList(Statut.values());
        model.addAttribute("dmdUserDtoOut", dmd2);
        model.addAttribute("listStatuts", statuts);
        //Get information of flash message
        redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation éffectuée avec succès");
        return "redirect:/dmd/dmds";

    }



    @RequestMapping(value = "/dmd/dmds/parc/edit/{id}", method = RequestMethod.GET)
    public String editDmdParc(@PathVariable Long id, Model model){

        model.addAttribute("title", "Dmd - Edition");
        return "dmd/edit";
    }

    @RequestMapping(value = "/dmd/dmds/parc/update", method = RequestMethod.POST)
    public String updateDmdParc(@Valid DmdParcDto dmdParcDto, Errors errors,  Model model, RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            System.out.println("error YES");
            List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());
            model.addAttribute("dmdParcDto", new DmdParcDto());
            model.addAttribute("title", "Dmd - Nouveau");
            model.addAttribute("listMoyenDemandes", moyenDemandes);
            return "Dmd/dmd";
        }


        redirectAttributes.addFlashAttribute("messagesucces", "Opération de création éffectuée avec succès");
        return "redirect:/dmd/dmds";
    }

    @RequestMapping(value = "/dmd/dmds/delete/{id}", method = RequestMethod.GET)
    public String deleteDmd(@PathVariable Long id){

        EmployeDmd employeDmd = dmdService.findById(id);
        dmdService.delete(employeDmd);
        //return "redirect:/acces/Dmd";
        return "redirect:/dmd/dmds";
    }

    @RequestMapping(value = "/dmd/dmds/user/saves", method = RequestMethod.POST)
    public String saveDmdUsers(@Valid DmdUserDto dmdUserDto, Errors errors,  Model model, RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            System.out.println("error YES");

           /* List<Employe> employes = employeService.findEmployesByEstChauffeur(true);
            List<Vehicule> vehicules = vehiculeService.all();*/

            List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());
            model.addAttribute("dmdUserDto", new DmdUserDto());
            model.addAttribute("title", "Dmd - Nouveau");
            model.addAttribute("listMoyenDemandes", moyenDemandes);
            /*model.addAttribute("listChauffeur",employes);
            model.addAttribute("listVehicule", vehicules);*/

            return "dmd/dmd/user";
        }

        //Get Employe by user connected
        //Employe employe = employeService.getEmployeByUserName(principal.getName());

        //Set employe to DmDtoUser
        //dmdUserDto.setEmploye(employe);

        //Create Dmd and Employe Dmd on DataBase
        dmdService.updateDmdUser(dmdUserDto);

        //Get information of flash message
        redirectAttributes.addFlashAttribute("messagesucces", "Opération de création éffectuée avec succès");

        return "redirect:/dmd/dmds";
    }

    @RequestMapping(value = "/dmd/dmds/user/savess", method = RequestMethod.POST)
    public String saveDmdUserss(@Valid DmdUserDto dmdUserDto, Errors errors,  Model model, RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            System.out.println("error YES");

           /* List<Employe> employes = employeService.findEmployesByEstChauffeur(true);
            List<Vehicule> vehicules = vehiculeService.all();*/

            List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());
            model.addAttribute("dmdUserDto", new DmdUserDto());
            model.addAttribute("title", "Dmd - Nouveau");
            model.addAttribute("listMoyenDemandes", moyenDemandes);
            /*model.addAttribute("listChauffeur",employes);
            model.addAttribute("listVehicule", vehicules);*/

            return "dmd/dmd/user";
        }

        //Get Employe by user connected
        //Employe employe = employeService.getEmployeByUserName(principal.getName());

        //Set employe to DmDtoUser
        //dmdUserDto.setEmploye(employe);

        //Create Dmd and Employe Dmd on DataBase
        dmdService.updateDmdUser(dmdUserDto);

        //Get information of flash message
        redirectAttributes.addFlashAttribute("messagesucces", "Opération de création éffectuée avec succès");

        return "redirect:/dmd/dmds";
    }

    @RequestMapping("/dmd/dmds/parc")
    public String newParc(Model model, Principal principal, HttpServletRequest request) {
     model.addAttribute("dmdparcdto", new DmdParcDto() );
     return "dmd/newParc";
    }

   /* @RequestMapping(value = "/dmd/dmds/suivant", method = RequestMethod.POST)
    public String newParcSuivant(@Valid DmdParcDto dmdParcDto, Model model, Principal principal, HttpServletRequest request) {
    model.addAttribute("dto", dmdParcDto);
    return "dmd/newParcSuivant";
    }*/

    @RequestMapping("/Chauffeur/chauffeur")
    public String Chauffeur(Model model, Principal principal, HttpServletRequest request) {

        List<Employe> chauffeur = employeService.findEmployesByEstChauffeur(true);

        List<EmployeDtoOut> dto = employeService.listEmployesToDto(chauffeur);

        model.addAttribute("listChauffeur", dto);

        return "Chauffeur/index";

    }

    @RequestMapping(value = "/Chauffeur/chauffeur/disponible/{id}", method = RequestMethod.GET)
    public String employeDisponible(@PathVariable Long id, @Valid Employe employe, Model model, RedirectAttributes redirectAttributes, Principal principal){


        Employe employe3 = employeService.findById(id);
        Employe employe1 = employeService.disponible(id, employe3);

        employeService.update(employe1);


        redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation éffectuée avec succès");

        return "redirect:/Chauffeur/chauffeur";

    }

    @RequestMapping(value = "/Chauffeur/chauffeur/indisponible/{id}", method = RequestMethod.GET)
    public String employeIndisponible(@PathVariable Long id, @Valid Employe employe, Model model, RedirectAttributes redirectAttributes, Principal principal){


        Employe employe3 = employeService.findById(id);
        Employe employe1 = employeService.indisponible(id, employe3);

        employeService.update(employe1);


        redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation éffectuée avec succès");

        return "redirect:/Chauffeur/chauffeur";

    }



//    @RequestMapping("/ChauffeurHistorique/print/{id}")
//    public void printChauffeurHistorique(@PathVariable Long id, HttpServletResponse response) throws JRException, IOException {
//        // Recherche de l'élément spécifique par son ID
//        ChauffeurHistoriqueDtoOut  dto = chauffeurHistoriqueService.findById(id);
//
//        if (dto == null) {
//            // Gérer le cas où aucun élément n'est trouvé pour cet ID
//            throw new IllegalArgumentException("No data found for id: " + id);
//        }
//
//        // Utilisation de getResourceAsStream pour charger le fichier JRXML
//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("templates/print1.jrxml");
//        if (inputStream == null) {
//            throw new FileNotFoundException("File not found: templates/print1.jrxml");
//        }
//        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
//
//        // Création des données pour le rapport
//        Map<String, Object> data = new HashMap<>();
//        data.put("statutHistorique", dto.getStatutHistorique());
//        data.put("dateParcours", dto.getDateParcours());
//        data.put("employe", dto.getEmploye());
//        data.put("vehicule", dto.getVehicule());
//        data.put("nomEmploye", dto.getNomEmploye().toString());
//        data.put("destination", dto.getDestination().toString());
//
//        // Set up parameters
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("data", data);
//
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "inline; filename=print1.pdf");
//
//        // Remplir le rapport avec les données et exporter en PDF
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
//        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

    @RequestMapping(value = "/ChauffeurHistorique/chauffeurHistorique")
    public String listChauffeurHistorique(Model model, Principal principal, HttpServletRequest request){

        List<ChauffeurHistoriqueDtoOut> dtos = new ArrayList<>();

        List<ChauffeurHistorique> chauffeurHistoriques = chauffeurHistoriqueService.all();
        dtos = chauffeurHistoriqueService.listChauffeurHistoriquesToDto(chauffeurHistoriques);


        model.addAttribute("listChauffeurHistoriques", dtos);

        return "Chauffeur/chauffeurhistorique";

    }









    @RequestMapping(value = "/Attribution/attribution/annulerVehicule/{id}", method = RequestMethod.GET)
    public String AnnulerAttribution(@PathVariable Long id, Model model){

        //Get EmployeDmd By id
        VehiculeAtt vehiculeAtt = attributionService.findVEById(id);

        //Set DmdUserDto
        AttributionDtoZ dto = new AttributionDtoZ();

        dto.setId(vehiculeAtt.getIdAttribution());
        dto.setDateAttribution(vehiculeAtt.getDateAttribution());
        //dto.setEmploye(vehiculeAtt.getEmploye());
        //dto.setEmployeDmd(vehiculeAtt.getEmployeDmd());
        dto.setVehicule(vehiculeAtt.getVehicule());
        dto.setStatutAttrib(vehiculeAtt.getStatutAttrib());
        //dto.setObservation(attribution.getObservation());
        /*dto.setStatutAttrib(attribution.getStatutAttrib());*/


        //Get Moyen demande list
        List<Employe> chauffeur = employeService.findEmployesByEstChauffeur(true);
        List<Vehicule> vehicule = vehiculeService.findVehiculesByStatutVehicule(DISPONIBLE);
        List<EmployeDmd> employeDmds = employeDmdService.all();
        List<StatutAttrib> statutAttrib = Arrays.asList(StatutAttrib.values());

        model.addAttribute("attributionDto",dto);
        model.addAttribute("listChauffeur",chauffeur);
        model.addAttribute("listVehicule",vehicule);
        model.addAttribute("listEmployee",employeDmds);
        model.addAttribute("listStatutAttrib",statutAttrib);
        model.addAttribute("title", "Dmd - Edition");

        return "Attribution/annulerV";
    }

    @RequestMapping(value = "/Attribution/attribution/annulerV", method = RequestMethod.POST)
    public String annulerAttribution(@Valid AttributionDtoZ attributionDtoZ, Model model, RedirectAttributes redirectAttributes){

        attributionService.annulerAttributionVehicule(attributionDtoZ);

        redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation éffectuée avec succès");

        return "redirect:/Attribution/attribution";

    }

    @RequestMapping(value = "/Attribution/attribution/annulerVehiculeChauffeur/{id}", method = RequestMethod.GET)
    public String AnnulerAttributionVC(@PathVariable Long id, Model model){

        //Get EmployeDmd By id
        VehiculeChauffeurAtt vehiculeChauffeurAtt = attributionService.findById(id);

        //Set DmdUserDto
        AttributionDtoZ dto = new AttributionDtoZ();

        dto.setId(vehiculeChauffeurAtt.getIdAttribution());
        dto.setDateAttribution(vehiculeChauffeurAtt.getDateAttribution());
        dto.setEmploye(vehiculeChauffeurAtt.getEmploye());
        //dto.setEmployeDmd(vehiculeChauffeurAtt.getEmployeDmd());
        dto.setVehicule(vehiculeChauffeurAtt.getVehicule());
        dto.setStatutAttrib(vehiculeChauffeurAtt.getStatutAttrib());
        //dto.setObservation(attribution.getObservation());
        /*dto.setStatutAttrib(attribution.getStatutAttrib());*/


        //Get Moyen demande list
        List<Employe> chauffeur = employeService.findEmployesByEstChauffeur(true);
        List<Vehicule> vehicule = vehiculeService.findVehiculesByStatutVehicule(DISPONIBLE);
        List<EmployeDmd> employeDmds = employeDmdService.all();
        List<StatutAttrib> statutAttrib = Arrays.asList(StatutAttrib.values());

        model.addAttribute("attributionDto",dto);
        model.addAttribute("listChauffeur",chauffeur);
        model.addAttribute("listVehicule",vehicule);
        model.addAttribute("listEmployee",employeDmds);
        model.addAttribute("listStatutAttrib",statutAttrib);
        model.addAttribute("title", "Dmd - Edition");

        return "Attribution/annulerVC";
    }

    @RequestMapping(value = "/Attribution/attribution/annulerVC", method = RequestMethod.POST)
    public String annulerAttributionVC(@Valid AttributionDtoZ attributionDtoZ, Model model, RedirectAttributes redirectAttributes){

        attributionService.annulerAttributionVehiculeChauffeur(attributionDtoZ);

        redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation éffectuée avec succès");

        return "redirect:/Attribution/attribution";

    }

    @RequestMapping(value = "/Attribution/attribution/annulerCarburant/{id}", method = RequestMethod.GET)
    public String AnnulerAttributionCC(@PathVariable Long id, Model model){

        //Get EmployeDmd By id
        CarburantAtt carburantAtt = attributionService.findCCById(id);

        //Set DmdUserDto
        AttributionDtoZ dto = new AttributionDtoZ();

        dto.setId(carburantAtt.getIdAttribution());
        dto.setDateAttribution(carburantAtt.getDateAttribution());
        //dto.setEmploye(carburantAtt.getEmploye());
        //dto.setEmployeDmd(carburantAtt.getEmployeDmd());
        //dto.setVehicule(carburantAtt.getVehicule());
        dto.setStatutAttrib(carburantAtt.getStatutAttrib());
        //dto.setObservation(attribution.getObservation());
        /*dto.setStatutAttrib(attribution.getStatutAttrib());*/


        //Get Moyen demande list
        List<Employe> chauffeur = employeService.findEmployesByEstChauffeur(true);
        List<Vehicule> vehicule = vehiculeService.findVehiculesByStatutVehicule(DISPONIBLE);
        List<EmployeDmd> employeDmds = employeDmdService.all();
        List<StatutAttrib> statutAttrib = Arrays.asList(StatutAttrib.values());

        model.addAttribute("attributionDto",dto);
        model.addAttribute("listChauffeur",chauffeur);
        model.addAttribute("listVehicule",vehicule);
        model.addAttribute("listEmployee",employeDmds);
        model.addAttribute("listStatutAttrib",statutAttrib);
        model.addAttribute("title", "Dmd - Edition");

        return "Attribution/annulerC";
    }

    @RequestMapping(value = "/Attribution/attribution/annulerCC", method = RequestMethod.POST)
    public String annulerAttributionCC(@Valid AttributionDtoZ attributionDtoZ, Model model, RedirectAttributes redirectAttributes){

        attributionService.annulerAttributionCarburant(attributionDtoZ);

        redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation éffectuée avec succès");

        return "redirect:/Attribution/attribution";

    }

    @RequestMapping(value = "/dmd/dmds/{id}", method = RequestMethod.GET)
    public String getDmd(@PathVariable Long id, Model model){

        EmployeDmd dmd = dmdService.findById(id);

        if (dmd.getStatut() == VALIDATION) {
            model.addAttribute("errorMessage", "la Dmd avec id: " + id + " n'est plus disponible pour cette operation");
            return "/main/error";
        } else if (dmd.getStatut() == REFUS) {
            model.addAttribute("errorMessage", "la Dmd avec id: " + id + " n'est plus disponible pour cette operation");
            return "/main/error";
        } else {

            DmdUserDto dto = dmdService.createDmdUserDto(dmd);

            /*DmdUserDto dto = new DmdUserDto();

            dto.setId(dmd.getIdEmployeDmd());
            dto.setDatePrevue(dmd.getDmd().getDatePrevue());
            dto.setHeurePrevue(dmd.getDmd().getHeurePrevue());
            dto.setMoyenDemande(dmd.getDmd().getMoyenDemande().name());
            dto.setMotif(dmd.getMotifDmd());
            dto.setDestination(dmd.getDestination());
            dto.setStatut(dmd.getStatut());
            dto.setResponsable(dmd.getResponsable());
            dto.setEmploye(dmd.getEmploye());*/

            //Get Moyen demande list
            List<Statut> statuts = Arrays.asList(Statut.values());
            List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());
            model.addAttribute("validation", dto);
            model.addAttribute("listMoyenDemandes", moyenDemandes);
            model.addAttribute("title", "Dmd - Edition");
            model.addAttribute("listStatuts", statuts);


        }
        return "/dmd/validation";
    }

    @RequestMapping("/Etat/etat")
    public String indexx(Model model, Principal principal, HttpServletRequest request) {

        return "dmd/rapport";
    }

    @RequestMapping("/EtatEmploye/etatEmploye")
    public String indexxx(Model model, Principal principal, HttpServletRequest request) {

        EmployeRapportDto dto = new EmployeRapportDto();
        //List<EmployeDmd> employeDmds = dmdService.all();
        List<RapportStatut> statuts = Arrays.asList(RapportStatut.values());
        model.addAttribute("listStatut", statuts);
        model.addAttribute("dto",dto);

        return "dmd/etatDMDEmploye";
    }

    @RequestMapping("/FiltreEtatEmploye/filtreEtatEmploye")
    public String etatEmployeRapport(@Valid @ModelAttribute("dto") EmployeRapportDto dto,BindingResult bindingResult, Model model, Principal principal, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            System.out.println("error YES");

            /*EmployeRapportDto dto = new EmployeRapportDto();*/
            //List<EmployeDmd> employeDmds = dmdService.all();
            List<RapportStatut> statuts = Arrays.asList(RapportStatut.values());
            model.addAttribute("listStatut", statuts);
            /*model.addAttribute("dto",dto);*/

            return "dmd/etatDMDEmploye";

        }
        List<DmdUserDtoOut> dtos = new ArrayList<>();
        List<EmployeDmd> employeDmds = employeDmdService.listEmployeDmdByDateBetweenAndRapportStatut(dto);
        //List<EmployeDmd> employeDmds = dmdService.all();
        dtos = dmdService.listDmdsToDto(employeDmds);

        if (dto.getRapportStatut() == TOUS) {
            model.addAttribute("statusNotAllVisible", false);
        } else if (dto.getRapportStatut() != TOUS) {
            model.addAttribute("statusAllVisible", true);
        }

        model.addAttribute("listDmdRapport",dtos);

        //model.addAttribute("dto", dto);
        //model.addAttribute("listDmdRapport",employeDmds);

        return "dmd/indexRapport";
    }

    @RequestMapping("/EtatEmployee/etatEmployee")
    public String etatEmployee(Model model, Principal principal, HttpServletRequest request) {
        EmployeEtatDto dto = new EmployeEtatDto();
        List<Employe> employe = employeService.all();
        //List<EmployeDmd> employeDmds = dmdService.all();
        List<Selection> selections = Arrays.asList(Selection.values());

        model.addAttribute("listEmploye", employe);
        model.addAttribute("listSelection", selections);
        model.addAttribute("dto",dto);

        return "dmd/etatDMDEmployee";
    }

    @RequestMapping("/FiltreEtatEmployee/filtreEtatEmployee")
    public String etatEmployeRapport(@Valid @ModelAttribute("dto") EmployeEtatDto dto, BindingResult bindingResult, Model model, Principal principal, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            System.out.println("error YES");

            /*EmployeEtatDto dto = new EmployeEtatDto();*/
            List<Employe> employe = employeService.all();
            //List<EmployeDmd> employeDmds = dmdService.all();
            List<Selection> selections = Arrays.asList(Selection.values());

            model.addAttribute("listEmploye", employe);
            model.addAttribute("listSelection", selections);
            /*model.addAttribute("dto",dto);*/

            return "dmd/etatDMDEmployee";

        }
        List<DmdUserDtoOut> dtos = new ArrayList<>();
        List<EmployeDmd> employeDmds = employeDmdService.listEmployeDmdByDateBetweenAndEmploye(dto);
        dtos = dmdService.listDmdsToDto(employeDmds);
        //List<EmployeDmd> employeDmds = dmdService.all();

        if (dto.getSelection() == Selection.TOUS) {
            model.addAttribute("statusNotAllVisible", false);
        } else if (dto.getSelection() == Selection.CHACUN) {
            model.addAttribute("statusAllVisible", true);
        }

        model.addAttribute("listDmdEtat",dtos);
        //model.addAttribute("listDmdRapport",employeDmds);

        return "dmd/indexRapportEmploye";
    }

    @RequestMapping("/EtatResponsable/etatResponsable")
    public String etatResponsable(Model model, Principal principal, HttpServletRequest request) {
        EtatResponsableDto dto = new EtatResponsableDto();

        List<Direction> directions = directionService.all();
        //List<EmployeDmd> employeDmds = dmdService.all();
        List<Selection> selections = Arrays.asList(Selection.values());

        model.addAttribute("listDirection", directions);
        model.addAttribute("listSelection", selections);
        model.addAttribute("dto",dto);

        return "dmd/etatDMDResponsable";
    }

    @RequestMapping("/FiltreEtatResponsable/filtreEtatResponsable")
    public String etatResponsable(@Valid @ModelAttribute("dto") EtatResponsableDto dto, BindingResult bindingResult, Model model, Principal principal, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            System.out.println("error YES");

            /*EtatResponsableDto dto = new EtatResponsableDto();*/

            List<Direction> directions = directionService.all();
            //List<EmployeDmd> employeDmds = dmdService.all();
            List<Selection> selections = Arrays.asList(Selection.values());

            model.addAttribute("listDirection", directions);
            model.addAttribute("listSelection", selections);
            /*model.addAttribute("dto",dto);*/

            return "dmd/etatDMDResponsable";

        }
        List<DmdUserDtoOut> dtos = new ArrayList<>();

        if (dto.getSelection() == Selection.TOUS){
            List<Dmd> dmds = dmdRepository.findAllByDatePrevueBetween(dto.getDebut(), dto.getFin());
            List<EmployeDmd> employeDmds = employeDmdService.getListEmployeDmdByDmdAndStatut(dmds,VALIDATION);
            dtos = dmdService.listDmdsToDto(employeDmds);
        } else {
            Direction d = directionService.findById(1L);
            Direction dp = directionService.findById(2L);
            Direction dt = directionService.findById(3L);
            Direction daf = directionService.findById(4L);

            if (d == dto.getDirection()) {
                List<Dmd> dmds = dmdRepository.findAllByDatePrevueBetween(dto.getDebut(), dto.getFin());
                List<EmployeDmd> employeDmds = employeDmdService.getListEmployeDmdByDmdAndStatutAndDirection(dmds,VALIDATION,d);
                dtos = dmdService.listDmdsToDto(employeDmds);
            } else if(dp == dto.getDirection()) {
                List<Dmd> dmds = dmdRepository.findAllByDatePrevueBetween(dto.getDebut(), dto.getFin());
                List<EmployeDmd> employeDmds = employeDmdService.getListEmployeDmdByDmdAndStatutAndDirection(dmds,VALIDATION,dp);
                dtos = dmdService.listDmdsToDto(employeDmds);
            } else if(dt == dto.getDirection()) {
                List<Dmd> dmds = dmdRepository.findAllByDatePrevueBetween(dto.getDebut(), dto.getFin());
                List<EmployeDmd> employeDmds = employeDmdService.getListEmployeDmdByDmdAndStatutAndDirection(dmds,VALIDATION,dt);
                dtos = dmdService.listDmdsToDto(employeDmds);
            } else if(daf == dto.getDirection()){
                List<Dmd> dmds = dmdRepository.findAllByDatePrevueBetween(dto.getDebut(), dto.getFin());
                List<EmployeDmd> employeDmds = employeDmdService.getListEmployeDmdByDmdAndStatutAndDirection(dmds,VALIDATION,daf);
                dtos = dmdService.listDmdsToDto(employeDmds);
            }
        }

        if (dto.getSelection() == Selection.TOUS) {
            model.addAttribute("statusNotAllVisible", false);
        } else if (dto.getSelection() == Selection.CHACUN) {
            model.addAttribute("statusAllVisible", true);
        }

        //List<EmployeDmd> employeDmds = dmdService.all();

        model.addAttribute("listDmdEtatResponsable",dtos);
        //model.addAttribute("listDmdRapport",employeDmds);

        return "dmd/indexEtatResponsable";
    }

    @RequestMapping("/EtatAttribution/etatAttribution")
    public String etatVehicule(Model model, Principal principal, HttpServletRequest request) {
        EtatAttributionDto dto = new EtatAttributionDto();

        List<Vehicule> vehicules = vehiculeService.all();
        List<Employe> employe = employeService.findEmployesByEstChauffeur(true);

        List<RapportVehiculeDto> selectionVehicule = vehiculeService.asListDto(vehicules);
        RapportVehiculeDto rapportVehiculeDto = new RapportVehiculeDto(0L, "TOUS");
        selectionVehicule.add(rapportVehiculeDto);

        List<RapportChauffeurDto> selectionChauffeur = employeService.listRapportChauffeur(employe);
        RapportChauffeurDto rapportChauffeurDto = new RapportChauffeurDto(0L, "TOUS");
        selectionChauffeur.add(rapportChauffeurDto);


        model.addAttribute("listVehicule", selectionVehicule);
        model.addAttribute("listChauffeur", selectionChauffeur);
        model.addAttribute("dto",dto);

        return "dmd/etatAttribution";
    }

    @RequestMapping("/FiltreEtatAttribution/filtreEtatAttribution")
    public String etatAttribution(@Valid @ModelAttribute("dto") EtatAttributionDto dto, BindingResult bindingResult, Model model, Principal principal, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            System.out.println("error YES");

            List<Vehicule> vehicules = vehiculeService.all();
            List<Employe> employe = employeService.findEmployesByEstChauffeur(true);

            List<RapportVehiculeDto> selectionVehicule = vehiculeService.asListDto(vehicules);
            RapportVehiculeDto rapportVehiculeDto = new RapportVehiculeDto(0L, "TOUS");
            selectionVehicule.add(rapportVehiculeDto);

            List<RapportChauffeurDto> selectionChauffeur = employeService.listRapportChauffeur(employe);
            RapportChauffeurDto rapportChauffeurDto = new RapportChauffeurDto(0L, "TOUS");
            selectionChauffeur.add(rapportChauffeurDto);


            model.addAttribute("listVehicule", selectionVehicule);
            model.addAttribute("listChauffeur", selectionChauffeur);
            model.addAttribute("dto",dto);

            return "dmd/etatAttribution";

        }

        List<AttributionDtoOut> dtoss = new ArrayList<>();
        List<Attribution> attribution = attributionService.listEtatAttribution(dto)
                .stream()
                .filter(Attribution -> Attribution.getTypeAttribution() == TypeAttribution.VEHICULE || Attribution.getTypeAttribution() == TypeAttribution.VEHICULE_CHAUFFEUR)
                .sorted(Comparator.comparing(Attribution::getTypeAttribution)
                        .reversed())
                .collect(Collectors.toList());
        dtoss = attributionService.listAttributionToDto(attribution);


        //String nom = employeService.findById(dto.getEmploye()).toNomComplet();
        //dto.setNom(nom);

        //List<Vehicule> v = vehiculeService.all();

        //String vehicule = vehiculeService.findById(dto.getVehicule()).getImmatriculation();
        //dto.setImmatriculation(vehicule);

        if (dto.getEmploye() != 0) {
            String employe = employeService.findById(dto.getEmploye())
                    .toNomComplet();

            dto.setNom(employe);
        } else {

            dto.setNom("TOUS");
        }

        if (dto.getVehicule() != 0) {
            String vehicule = vehiculeService.findById(dto.getVehicule())
                    .getImmatriculation();

            dto.setImmatriculation(vehicule);
        } else {

            dto.setImmatriculation("TOUS");
        }


        if(dto.getEmploye() == 0L && dto.getVehicule() == 0L){
            model.addAttribute("statusChauffeurVehiculeVisible1", false);
        }else if(dto.getEmploye() == 0L && dto.getVehicule() != 0L){
            model.addAttribute("statusVehiculeVisible", true);
        }else if(dto.getEmploye() != 0L && dto.getVehicule() == 0L){
            model.addAttribute("statusChauffeurVisible", true);
        }else if(dto.getEmploye() != 0L && dto.getVehicule() != 0L){
            model.addAttribute("statusChauffeurVehiculeVisible", true);
        }


        //List<EmployeDmd> employeDmds = dmdService.all();
        model.addAttribute("listEtatAttribution",dtoss);
        //model.addAttribute("listDmdRapport",employeDmds);

        return "dmd/indexEtatAttribution";

    }

    @RequestMapping("/EtatAttributionEmploye/etatAttributionEmploye")
    public String etatVehiculeEmploye(Model model, Principal principal, HttpServletRequest request) {

        EtatAttributionEmployeDto dto = new EtatAttributionEmployeDto();

        List<Employe> employe = employeService.all();
        List<TypeAttribution> typeAttribution = Arrays.asList(TypeAttribution.values());

        List<RapportEmployeDto> selectionEmploye = employeService.listRapportEmploye(employe);
        RapportEmployeDto rapportEmployeDto = new RapportEmployeDto(0L, "TOUS");
        selectionEmploye.add(rapportEmployeDto);

        model.addAttribute("listEmploye", selectionEmploye);
        model.addAttribute("listTypeAttribution",typeAttribution);

        model.addAttribute("dto",dto);

        return "dmd/etatAttributionEmploye";
    }

    @RequestMapping("/FiltreEtatAttributionEmploye/filtreEtatAttributionEmploye")
    public String etatAttributionEmploye(@Valid @ModelAttribute("dto") EtatAttributionEmployeDto dto, BindingResult bindingResult, Model model, Principal principal, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            System.out.println("error YES");


            List<Employe> employe = employeService.all();
            List<MoyenDemande> moyenDemande = Arrays.asList(MoyenDemande.values());

            List<RapportEmployeDto> selectionEmploye = employeService.listRapportEmploye(employe);
            RapportEmployeDto rapportEmployeDto = new RapportEmployeDto(0L, "TOUS");
            selectionEmploye.add(rapportEmployeDto);

            model.addAttribute("listEmploye", selectionEmploye);
            model.addAttribute("listMoyenDemande",moyenDemande);


            return "dmd/etatAttributionEmploye";

        }

        /*List<AttributionDtoOut> dtoss = new ArrayList<>();
        List<Attribution> attribution = attributionService.listEtatAttributionEmploye(dto)
                .stream()
                .filter(Attribution -> Attribution.getTypeAttribution() == TypeAttribution.VEHICULE || Attribution.getTypeAttribution() == TypeAttribution.VEHICULE_CHAUFFEUR)
                .sorted(Comparator.comparing(Attribution::getTypeAttribution)
                        .reversed())
                .collect(Collectors.toList());
        //dtoss = attributionService.listAttributionToDto(attribution);*/

        List<DetailADateDtoOut> dtoss = new ArrayList<>();

        if(dto.getTypeAttribution() == TypeAttribution.VEHICULE_CHAUFFEUR) {

            List<DetailVehiculeChauffeurA> detail = detailVehiculeChauffeurAService.listDetailVehiculeChauffeurAByDateBetween(dto);
            dtoss = detailVehiculeChauffeurAService.listDetailAttributionDto(detail);

        } else if(dto.getTypeAttribution() == TypeAttribution.VEHICULE){

            List<DetailVehiculeA> detail1 = detailVehiculeAService.listDetailVehiculeAByDateBetween(dto);
            dtoss = detailVehiculeAService.listDetailAttributionDto(detail1);

        } else {

            List<DetailCarburantA> detail2 = detailCarburantAService.listDetailCarburantAByDateBetween(dto);
            dtoss = detailCarburantAService.listDetailAttributionDto(detail2);

        }

        if (dto.getEmploye() != 0) {
            String employe = employeService.findById(dto.getEmploye())
                    .toNomComplet();

            dto.setNom(employe);
        } else {

            dto.setNom("TOUS");
        }

        //List<DetailVehiculeChauffeurA> detail = detailVehiculeChauffeurAService.listDetailVehiculeChauffeurAByDateBetween(dto);

        //List<EmployeDmd> employeDmds = dmdService.all();
        model.addAttribute("listEtatDetailEmploye",dtoss);
        //model.addAttribute("listDmdRapport",employeDmds);

        return "dmd/indexEtatDetailEmploye";

    }
    @RequestMapping("/Dmd/print2/{id}")
    public void printAttribution(@PathVariable Long id, HttpServletResponse response) throws JRException, IOException {
        // Fetch attribution data by ID
//        AttributionDtoOut dto = attributionService.getAttributionDto(id, VehiculeAtt.class, VehiculeChauffeurAtt.class, CarburantAtt.class);
        DmdPapierResponse papierResponse=etatService.PAPIER_RESPONSE(id);
        String ff = "";

        // Load JRXML template
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("templates/print2.jrxml");
        if (inputStream == null) {
            throw new FileNotFoundException("File not found: templates/print2.jrxml");
        }
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);


        List<Map<String, Object>> tableData = new ArrayList<>();
        for (DmdPapierDetailResponse detail : papierResponse.getDmdPapierResponseList()) {
            Map<String, Object> detailMap = new HashMap<>();
            detailMap.put("nomPrenom", detail.getNomPrenom());
            detailMap.put("destination", detail.getDestination());
            detailMap.put("motif", detail.getMotif());
            tableData.add(detailMap);
        }

        JRBeanCollectionDataSource tableDataSource = new JRBeanCollectionDataSource(tableData);

        // Report parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", "Attribution Details");
        parameters.put("subtitle", "Detailed Information");
        parameters.put("tabb", tableDataSource);
        parameters.put("dateDeDepart", papierResponse.getDateHeureDepart());
        parameters.put("typeattribution", papierResponse.getMoyenDemande());
        parameters.put("dateArrivee", papierResponse.getDateHeureArrivee());
        parameters.put("immatriculation", papierResponse.getImmatriculation());
        parameters.put("kilometrageDebut", papierResponse.getKmDepart());
        parameters.put("kilometrageFin", papierResponse.getKmArrivee());
        parameters.put("nomChauffeur", papierResponse.getNomChauffeur());

        // Configure HTTP response
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=attribution.pdf");

        // Fill and export report to PDF
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }


    @RequestMapping("/Dmd/print/{id}")
    public void printDmd(@PathVariable Long id, HttpServletResponse response) throws JRException, IOException {

        EmployeDmd employeDmd = dmdService.findById(id);
        Dmd dmd1 = employeDmd.getDmd();
        List<EmployeDmd> dmds = employeDmdService.getListEmployeDmdByDmd(dmd1);

        // Utilisation de getResourceAsStream pour charger le fichier JRXML
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("templates/print.jrxml");
        if (inputStream == null) {
            throw new FileNotFoundException("File not found: templates/print.jrxml");
        }
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        List<Map<String, Object>> tableData = new ArrayList<>();

        for (EmployeDmd employeDmd1 : dmds) {
            Map<String, Object> data = new HashMap<>();

            String nom =  employeDmd1.getEmploye().toNomComplet() ;
            String destination =  employeDmd1.getDestination().getNomDestination() ;
            String motif = employeDmd1.getMotif().getNomMotif();

            data.put("nom", nom);
            data.put("destination", destination);
            data.put("motif", motif);
            tableData.add(data);
        }

        JRBeanCollectionDataSource tableDataSource = new JRBeanCollectionDataSource(tableData);
        LocalDate date = employeDmd.getDmd().getDatePrevue();
        DateTimeFormatter date1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String d = date1.format(date);
        String heure = employeDmd.getDmd().getHeurePrevue().toString();
        String moyenDemande = employeDmd.getDmd().getMoyenDemande().toString();
        String moyen = moyenDemande.replace("_", "  ");

        // Set up parameters
        Map<String, Object> dmdPrint = new HashMap<>();
        dmdPrint.put("FirstName", d);
        dmdPrint.put("LastName", heure);
        dmdPrint.put("dbo", moyen);
        dmdPrint.put("tabb", tableDataSource);

        // Remaining JasperReport processing (filling report, exporting, etc.)

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=dmd.pdf");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, dmdPrint, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
    @RequestMapping(value = "Dmd/print1/{id}", method = RequestMethod.GET)
    public void printChauffeurHistorique(@PathVariable Long id, HttpServletResponse response) throws JRException, IOException {
        // Récupération des données spécifiques par ID
        ChauffeurHistorique dto = chauffeurHistoriqueService.findById(id);
        ChauffeurHistoriqueDtoOut dtos = chauffeurHistoriqueService.chauffeurHistoriqueToDto(dto);

        if (dto == null) {
            throw new IllegalArgumentException("No data found for id: " + id);
        }

        // Chargement du fichier JRXML
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("templates/print1.jrxml");
        if (inputStream == null) {
            throw new FileNotFoundException("File not found: templates/print1.jrxml");
        }
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        // Préparation des données pour le rapport
        List<Map<String, Object>> tableData = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("statutHistorique", dtos.getStatutHistorique());
        data.put("dateParcours", dtos.getDateParcours());
        data.put("employe", dtos.getEmploye());
        data.put("vehicule", dtos.getVehicule());
        data.put("nomEmploye", dtos.getNomEmploye());
        data.put("destination", dtos.getDestination());
        tableData.add(data);

        JRBeanCollectionDataSource tableDataSource = new JRBeanCollectionDataSource(tableData);

        // Paramètres pour le rapport
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", "Historique Chauffeur"); // Titre du rapport
        parameters.put("subtitle", "Détails de l'historique"); // Sous-titre du rapport
        parameters.put("tabb", tableDataSource); // Données tabulaires

        // Configuration de la réponse HTTP
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=print1.pdf");

        // Remplissage et exportation du rapport en PDF
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
}








