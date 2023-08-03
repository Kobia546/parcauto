package ci.nkagou.parcauto.controllers;

import ci.nkagou.parcauto.dtos.chauffeur.EmployeDtoOut;
import ci.nkagou.parcauto.dtos.chauffeurhistorique.ChauffeurHistoriqueDtoOut;
import ci.nkagou.parcauto.dtos.dmd.*;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.*;
import ci.nkagou.parcauto.repositories.AttributionRepository;
import ci.nkagou.parcauto.repositories.CarburantAttRepository;
import ci.nkagou.parcauto.repositories.EmployeDmdRepository;
import ci.nkagou.parcauto.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ci.nkagou.parcauto.enums.Statut.REFUS;
import static ci.nkagou.parcauto.enums.Statut.VALIDATION;
import static ci.nkagou.parcauto.enums.StatutAttrib.TERMINEE;
import static ci.nkagou.parcauto.enums.StatutVehicule.DISPONIBLE;
import static ci.nkagou.parcauto.enums.StatutVehicule.EN_COURSE;


@Controller
public class DmdController {

    private final DmdService dmdService;
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
    private final ServletContext context;


    private  UserService userService;

    //Save the uploaded file to this folder
   // private static String UPLOADED_FOLDER = "C:\\Users\\PC\\Desktop\\parcauto-master\\src\\main\\resources\\static\\temp\\";

   // private static String UPLOADED_FOLDER = "C://temp//";

    public DmdController(DmdService dmdService, EmployeService employeService, UserService userService, DirectionService directionService, VehiculeService vehiculeService, AttributionService attributionService, EmployeDmdService employeDmdService, ChauffeurHistoriqueService chauffeurHistoriqueService, CarburantAttRepository carburantAttRepository, AttributionRepository attributionRepository, ServletContext context) {
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
        this.context = context;
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

    @RequestMapping(value = "/dmd/dmds/user", method = RequestMethod.GET)
    public String newDmdUser(Model model) {

        List<Statut> statuts = Arrays.asList(Statut.values());
        List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());
        model.addAttribute("dmdUserDto", new DmdUserDto());
        model.addAttribute("title", "Dmd - Nouveau");
        model.addAttribute("listMoyenDemandes", moyenDemandes);
        model.addAttribute("listStatuts", statuts);
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
    public String newParcSuivant(@Valid DmdParcDto dmdParcDto, Model model, Principal principal, HttpServletRequest request, Errors errors) {

        if (errors.hasErrors()) {
            System.out.println("error YES");
        }
        DmdParcDto dto = dmdParcDto;
        List<DmdParcDtoOut> dtos = new ArrayList<>();
        List<Employe> employes = employeService.all();
        model.addAttribute("listDmd", dtos);
        model.addAttribute("title", "Dmd - Nouveau");
        model.addAttribute("listEmploye", employes);
        model.addAttribute("dto", new EmployeDmdDto());
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
    public String saveDmdUser(@Valid DmdUserDto dmdUserDto, Errors errors,  Model model, RedirectAttributes redirectAttributes, Principal principal) {

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
        Employe employe = employeService.getEmployeByUserName(principal.getName());

        //Set employe to DmDtoUser
        dmdUserDto.setEmploye(employe);

        //Create Dmd and Employe Dmd on DataBase
        dmdService.createDmdUser(dmdUserDto);

        //Get information of flash message
        redirectAttributes.addFlashAttribute("messagesucces", "Opération de création éffectuée avec succès");

        return "redirect:/dmd/dmds";
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
        dto .setDatePrevue(employeDmd.getDmd().getDatePrevue());
        dto.setHeurePrevue(employeDmd.getDmd().getHeurePrevue());
        dto.setMoyenDemande(employeDmd.getDmd().getMoyenDemande().name());
        dto.setMotif(employeDmd.getMotifDmd());
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
        return "dmd/edit";
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
        dto.setMotif(employeDmd.getMotifDmd());
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
        dto.setTypeAttribution(vehiculeChauffeurAtt.getTypeAttribution());
        dto.setDateAttribution(vehiculeChauffeurAtt.getDateAttribution());
        dto.setEmploye(vehiculeChauffeurAtt.getEmploye());
        dto.setEmployeDmd(vehiculeChauffeurAtt.getEmployeDmd());
        dto.setVehicule(vehiculeChauffeurAtt.getVehicule());
        dto.setDateDeDepart(vehiculeChauffeurAtt.getDateDeDepart());
        dto.setDateArrivee(vehiculeChauffeurAtt.getDateArrivee());
        dto.setStatutChauffeurA(vehiculeChauffeurAtt.getStatutChauffeurA());
        dto.setMotif(vehiculeChauffeurAtt.getMotif());
        dto.setObservation(vehiculeChauffeurAtt.getObservation());

        //Get Moyen demande list
        List<Employe> chauffeur = employeService.findEmployesByEstChauffeur(true);
        List<Vehicule> vehicule = vehiculeService.all();
        List<EmployeDmd> employeDmds = employeDmdService.all();
        List<Motif> motif = Arrays.asList(Motif.values());
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
        dto.setEmployeDmd(vehiculeChauffeurAtt.getEmployeDmd());
        dto.setVehicule(vehiculeChauffeurAtt.getVehicule());
        dto.setStatutAttrib(vehiculeChauffeurAtt.getStatutAttrib());


        //Get Moyen demande list
        List<Employe> chauffeur = employeService.findEmployesByEstChauffeur(true);
        List<Vehicule> vehicule = vehiculeService.all();
        List<EmployeDmd> employeDmds = employeDmdService.all();
        List<StatutAttrib> statutAttrib = Arrays.asList(StatutAttrib.values());
        model.addAttribute("attributionDto",dto);
        model.addAttribute("listChauffeur",chauffeur);
        model.addAttribute("listVehicule",vehicule);
        model.addAttribute("listEmployee",employeDmds);
        model.addAttribute("listStatutAttrib",statutAttrib);
        model.addAttribute("title", "Dmd - Edition");

        return "Attribution/edit";
    }

    @RequestMapping(value = "/Attribution/attribution/user/terminée", method = RequestMethod.POST)
    public String saveAttributionUser(@Valid AttributionDtoZ attributionDtoZ,Model model, RedirectAttributes redirectAttributes) {

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
        dto.setEmployeDmd(vehiculeAtt.getEmployeDmd());
        dto.setVehicule(vehiculeAtt.getVehicule());
        dto.setDateDeDepart(vehiculeAtt.getDateDeDepart());
        dto.setDateArrivee(vehiculeAtt.getDateArrivee());
        //dto.setStatutChauffeurA(vehiculeAtt.getStatutChauffeurA());
        dto.setMotif(vehiculeAtt.getMotif());
        dto.setObservation(vehiculeAtt.getObservation());

        //Get Moyen demande list
        List<Employe> chauffeur = employeService.findEmployesByEstChauffeur(true);
        List<Vehicule> vehicule = vehiculeService.all();
        List<EmployeDmd> employeDmds = employeDmdService.all();
        List<Motif> motif = Arrays.asList(Motif.values());
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
    public String CommencerCourseVE(@Valid AttributionDtoZ attributionDtoZ, Model model, RedirectAttributes redirectAttributes){

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
        //dto.setEmploye(vehiculeAtt.getEmploye());
        dto.setEmployeDmd(vehiculeAtt.getEmployeDmd());
        dto.setVehicule(vehiculeAtt.getVehicule());
        dto.setDateDeDepart(vehiculeAtt.getDateDeDepart());
        dto.setDateArrivee(vehiculeAtt.getDateArrivee());
        //dto.setStatutChauffeurA(vehiculeAtt.getStatutChauffeurA());
        dto.setMotif(vehiculeAtt.getMotif());
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
    public String saveAttributionUserVE(@Valid AttributionDtoZ attributionDtoZ,Model model, RedirectAttributes redirectAttributes) {

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
        dto.setEmployeDmd(carburantAtt.getEmployeDmd());
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
    public String saveAttributionUserCC(@Valid AttributionDtoZ attributionDtoZ, @RequestParam("recuCarburant") MultipartFile file,
                                         RedirectAttributes redirectAttributes) {


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
            //dtoss = attributionService.listAttributionToDtoVehicule(vehiculeAtts);

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

        } else {

            List<Attribution> attributions = attributionService.findAttributionsByEmployeDmdEmploye(employe)
                    .stream()
                    .filter(Attribution -> Attribution.getStatutAttrib() == StatutAttrib.EN_ATTENTE || Attribution.getStatutAttrib() == StatutAttrib.EN_COURSE)
                    .sorted(Comparator.comparing(Attribution::getStatutAttrib)
                            .reversed())
                    .collect(Collectors.toList());
            dtoss = attributionService.listAttributionToDto(attributions);
        }

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
            //dtoss = attributionService.listAttributionToDtoVehicule(vehiculeAtts);

            List<VehiculeChauffeurAtt> vehiculeChauffeurAtts = attributionService.allAttributionVehiculeChauffeur()
                    .stream()
                    .filter(VehiculeChauffeurAtt -> VehiculeChauffeurAtt.getStatutAttrib() == TERMINEE || VehiculeChauffeurAtt.getStatutAttrib() == StatutAttrib.ANNULER)
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
                    .filter(CarburantAtt -> CarburantAtt.getStatutAttrib() == TERMINEE || CarburantAtt.getStatutAttrib() == StatutAttrib.ANNULER)
                    .sorted(Comparator.comparing(Attribution::getStatutAttrib)
                            .reversed())
                    .collect(Collectors.toList());
            dtoss = attributionService.listAttributionToDtoCarburant(carburantAtts);

        } else {

            List<Attribution> attributions = attributionService.findAttributionsByEmployeDmdEmploye(employe)
                    .stream()
                    .filter(Attribution -> Attribution.getStatutAttrib() == TERMINEE || Attribution.getStatutAttrib() == StatutAttrib.ANNULER)
                    .sorted(Comparator.comparing(Attribution::getStatutAttrib)
                            .reversed())
                    .collect(Collectors.toList());
            dtoss = attributionService.listAttributionToDto(attributions);
        }

        model.addAttribute("listAttribution", dtoss);

        return "Attribution/HistoriqueAttribution";

    }



    @RequestMapping(value = "/dmd/dmdVehicule")
    public String DmdVehicule(Model model, Principal principal, HttpServletRequest request){

        List<DmdUserDtoOut> dtos = new ArrayList<>();
        List<EmployeDmd> employeDmds = dmdService.findEmployeDmdsByDmdMoyenDemandeStatut(MoyenDemande.VEHICULE, VALIDATION);
        dtos = dmdService.listDmdsToDto(employeDmds);

        model.addAttribute("listDmdVehicule", dtos);

        return "dmd/indexVehicule";

    }

    @RequestMapping(value = "/dmd/dmdVehiculeChauffeur")
    public String DmdVehiculeChauffeur(Model model, Principal principal, HttpServletRequest request){

        List<DmdUserDtoOut> dtos = new ArrayList<>();
        List<EmployeDmd> employeDmds = dmdService.findEmployeDmdsByDmdMoyenDemandeStatut(MoyenDemande.VEHICULE_CHAUFFEUR, VALIDATION);
        dtos = dmdService.listDmdsToDto(employeDmds);

        model.addAttribute("listDmdVehiculeChauf", dtos);

        return "dmd/indexVehiculeChauf";

    }

    @RequestMapping(value = "/dmd/dmdCarburant")
    public String DmdCarburant(Model model, Principal principal, HttpServletRequest request){

        List<DmdUserDtoOut> dtos = new ArrayList<>();
        List<EmployeDmd> employeDmds = dmdService.findEmployeDmdsByDmdMoyenDemandeStatut(MoyenDemande.CARBURANT, VALIDATION);
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

    }

    @RequestMapping(value = "/dmd/dmds/user/annuler/{id}", method = RequestMethod.GET)
    public String annulerDmdUser(@PathVariable Long id,@Valid DmdUserDto dmdUserDto,  Model model, RedirectAttributes redirectAttributes, Principal principal){

        //EmployeDmd employeDmd = dmdService.findById(id);

        /* employeDmd.setStatut(VALIDATION);*/
        //Get Employe by user connected
        EmployeDmd employeDmd2 = dmdService.findById(id);
        EmployeDmd employeDmd1 = dmdService.annulerDmd(id, employeDmd2);
        //employeDmd.setResponsable(employe.getIdEmploye());
        employeDmdRepository.save(employeDmd1);

        redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation éffectuée avec succès");

        return "redirect:/dmd/dmds";

    }

    @RequestMapping(value = "/dmd/dmds/user/refuser/{id}", method = RequestMethod.GET)
    public String refuserDmdUser(@PathVariable Long id,@Valid DmdUserDto dmdUserDto,  Model model, RedirectAttributes redirectAttributes, Principal principal){

        EmployeDmd employeDmd = dmdService.findById(id);


        DmdUserDto dto = new DmdUserDto();

        dto.setId(employeDmd.getIdEmployeDmd());
        dto .setDatePrevue(employeDmd.getDmd().getDatePrevue());
        dto.setHeurePrevue(employeDmd.getDmd().getHeurePrevue());
        dto.setMoyenDemande(employeDmd.getDmd().getMoyenDemande().name());
        dto.setMotif(employeDmd.getMotifDmd());
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

    @RequestMapping(value = "/dmd/dmds/user/update", method = RequestMethod.POST)
    public String updateDmdUser(@Valid DmdUserDto dmdUserDto, Errors errors,  Model model, RedirectAttributes redirectAttributes, Principal principal) {

        if (errors.hasErrors()) {
            System.out.println("error YES");
            List<Statut> statuts = Arrays.asList(Statut.values());
            List<MoyenDemande> moyenDemandes = Arrays.asList(MoyenDemande.values());
            model.addAttribute("dmdParcDto", new DmdParcDto());
            model.addAttribute("title", "Dmd - Nouveau");
            model.addAttribute("listMoyenDemandes", moyenDemandes);
            model.addAttribute("listStatuts", statuts);
            return "Dmd/dmd";
        }

        String s = "";

        //Get Employe by user connected
        Employe employe = employeService.getEmployeByUserName(principal.getName());

        //Set employe to DmDtoUser
        dmdUserDto.setEmploye(employe);
        //update Dmd and Employe Dmd on DataBase
        dmdService.updateDmdUser(dmdUserDto);


        redirectAttributes.addFlashAttribute("messagesucces", "Opération de création éffectuée avec succès");
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

    @RequestMapping(value = "/Attribution/attribution/user/commencerVehiculeChauffeur", method = RequestMethod.POST)
    public String CommencerCourse(@Valid AttributionDtoZ attributionDtoZ, Model model, RedirectAttributes redirectAttributes){

        attributionService.updateCommencerLaCourseVehiculeChauffeur(attributionDtoZ);

        redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation éffectuée avec succès");

        return "redirect:/Attribution/attribution";

    }

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
        dto.setEmployeDmd(vehiculeAtt.getEmployeDmd());
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
        dto.setEmployeDmd(vehiculeChauffeurAtt.getEmployeDmd());
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
        dto.setEmployeDmd(carburantAtt.getEmployeDmd());
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

  /*  @RequestMapping("/Entretien/entretien")
    public String indexhebdomadaire(Model model, Principal principal, HttpServletRequest request) {


        return"entretien/indexhebdomadaire";

    }

    @RequestMapping(value = "/Entretien/entretien/new", method = RequestMethod.GET)
    public String nouvelleEntretien(Model model) {

        EntretienherDto entretienherDto = new EntretienherDto();


        model.addAttribute("entretienherDto",entretienherDto);


        return "entretien/edithebdomadaire";

    }*/







}
