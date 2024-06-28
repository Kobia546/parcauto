package ci.nkagou.parcauto.controllers.webservice;

import ci.nkagou.parcauto.dtos.dmd.*;
import ci.nkagou.parcauto.dtos.entretien.DetailHerbdomadaireDto;
import ci.nkagou.parcauto.dtos.entretien.DetailVidangeDto;
import ci.nkagou.parcauto.dtos.entretien.EntretienHerbdomadaireDto;
import ci.nkagou.parcauto.dtos.entretien.EntretienVidangeDto;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.MoyenDemande;
import ci.nkagou.parcauto.repositories.*;
import ci.nkagou.parcauto.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DmdWebServiceController {

    private final MarqueService marqueService;
    private final DmdService dmdService;
    private EmployeService employeService;
    private EmployeDmdService employeDmdService;
    private EmployeRepository employeRepository;
    private EmployeDmdRepository employeDmdRepository;
    private EmployeDmdService employeDmdSevice;
    private AttributionService attributionService;
    private VehiculeHistoriqueService vehiculeIndisponibleService;
    private EntretienService entretienService;
    private VehiculeService vehiculeService;
    private EntretienVidangeRepository entretienVidangeRepository;
    private DetailVidangeRepository detailVidangeRepository;
    private RoleService roleService;
    private UserRoleService userRoleService;
    private NotificationService notificationService;
    private DetailCarburantAService detailCarburantAService;


    public DmdWebServiceController(MarqueService marqueService,DmdService dmdService,EmployeDmdService employeDmdService, EmployeRepository employeRepository,EmployeDmdRepository employeDmdRepository,AttributionService attributionService,EmployeService employeService,EntretienService entretienService,VehiculeService vehiculeService,EntretienVidangeRepository entretienVidangeRepository,DetailVidangeRepository detailVidangeRepository,RoleService roleService,UserRoleService userRoleService,NotificationService notificationService,DetailCarburantAService detailCarburantAService ) {
        this.marqueService = marqueService;
        this.dmdService = dmdService;
        this.employeService = employeService;
        this.employeDmdService = employeDmdService;
        this.employeRepository = employeRepository;
        this.attributionService = attributionService;
        this.entretienService = entretienService;
        this.vehiculeService = vehiculeService;
        this.entretienVidangeRepository = entretienVidangeRepository;
        this.detailVidangeRepository = detailVidangeRepository;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
        this.notificationService = notificationService;
        this.detailCarburantAService = detailCarburantAService;
    }


    @GetMapping("/test")
    List<Marque> test(){

        List<Marque> marques = marqueService.all();

        return marques;
    }


    @GetMapping("/test/{id}")
    Marque testMarque(@PathVariable Long id){

       Marque marque = marqueService.findById(id);

       return marque;
    }

    @PostMapping("/savedmd")
    String saveDMDParc(@RequestBody DmdParcDtoW dto){

        String a = "";

        dmdService.createDmdParc(dto);

        return "marque";
    }

    /*@PostMapping("/create")
    String saveAttribution(@RequestBody AttributionDto dto){

        String a = "";

        attributionService.create(dto);

        return "/Attribution/attribution";
    }*/

    @PostMapping("/create")
    @ResponseBody // This annotation is used to indicate that the method's return value should be serialized to JSON
    public ResponseEntity<String> saveAttribution(@RequestBody AttributionDto dto) {
        try {

            attributionService.create(dto);


            return ResponseEntity.ok("{\"success\": true}");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": false, \"message\": \"An error occurred.\"}");
        }
    }

   /* @PostMapping("/createVehiculeChauffeur")
    String saveAttributionVehiculeChauffeur(@RequestBody AttributionVehiculeChauffeurAttDto dtos){

        String a = "";

        attributionService.createVehiculeChauffeur(dtos);

        return "/Attribution/attribution";
    }*/

    @PostMapping("/createVehiculeChauffeur")
    @ResponseBody // This annotation is used to indicate that the method's return value should be serialized to JSON
    public ResponseEntity<String> saveAttributionVehiculeChauffeur(@RequestBody AttributionVehiculeChauffeurAttDto dtos) {
        try {


            attributionService.createVehiculeChauffeur(dtos);


            // Return a JSON success response
            return ResponseEntity.ok("{\"success\": true}");
        } catch (Exception e) {
            // Return a JSON error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": false, \"message\": \"An error occurred.\"}");
        }
    }

    /*@PostMapping("/createCarburant")
    String saveAttributionCarburant(@RequestBody AttributionCarburantAttDto dtos){

        String a = "";

        attributionService.createCarburant(dtos);

        return "/Attribution/attribution";
    }*/

    @PostMapping("/createCarburant")
    @ResponseBody // This annotation is used to indicate that the method's return value should be serialized to JSON
    public ResponseEntity<String> saveAttributionCarburant( @RequestBody AttributionCarburantAttDto dtos,RedirectAttributes redirectAttributes,Principal principal) {
        try {

              // Your logic to create the attribution here
              Attribution attribution = attributionService.createCarburant(dtos);
              //attributionService.createCarburant(dtos);

              Long id = attribution.getIdAttribution();

              //String a = attribution.getDetailCarburantA().toString();
              List<DetailCarburantA> a = attribution.getDetailCarburantA();
              StringBuilder d = new StringBuilder();

              if (a != null && !a.isEmpty()) {
                  for (DetailCarburantA detail : a) {
                      String name = detail.getEmployeDmd().getEmploye().toNomComplet();
                      d.append(name).append(", ");
                  }

                  if (d.length() > 0) {
                      //d = new StringBuilder(d.substring(0, d.length() - 2));
                      d.setLength(d.length() - 2);
                  }
              }


              String de = d.toString();
              LocalDateTime date = attribution.getDateAttribution();

              String i = ":" + id ;
              String n = "Nom :" + de + "<br>";
              DateTimeFormatter date1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
              String date2 = date1.format(date);
              String d1 = "Date de l'attribution :" + date2 + "<br><br>";
              //String d = "Nom :" + d + "<br>";

              String baseUrl = "http://localhost:8089/Attribution/attribution";
              String sujet = "Reception d'une emission d'attribution";

              String linkText = "Cliquez-ici";
              /*AppRole appRole = roleService.getById(5L);
              List<UserRole> userRole =userRoleService.findByAppRoleIsNot(appRole);
              List<String> from = new ArrayList<>();

              for (UserRole role : userRole) {
                  from.add(role.getAppUser().getEmploye().getEmail());
              }*/


              Employe employe1 = employeService.getEmployeByUserName(principal.getName());
              String from = employe1.getEmail();

              Employe employe = employeService.findById(22L);
              String email = employe.getEmail();
              List<String> to = new ArrayList<>();
              to.add(email);

              //String message = "Une attribution a été effectuée :" + id + d ;
              String message = "L'attribution" + i + " a été émise  <br><br>" + n + d1 + "Voir resultat - <a href='" + baseUrl + "'>" + linkText + "</a>";
              //String message1 = "Votre demande a ete valider";

              try {
                  notificationService.sendHtmlEmail(sujet, message, from, to);
                  redirectAttributes.addFlashAttribute("messagesucces", "Opération de création effectuée avec succès");

              } catch (Exception e) {
                  e.printStackTrace(); // Print the full exception stack trace for debugging
                  redirectAttributes.addFlashAttribute("messageerror", "Erreur lors de l'envoi de l'email.");
              }


            // Return a JSON success response
            return ResponseEntity.ok("{\"success\": true}");
        } catch (Exception e) {
            // Return a JSON error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": false, \"message\": \"An error occurred.\"}");
        }
    }

    /*@PostMapping("/update")
    @ResponseBody // This annotation is used to indicate that the method's return value should be serialized to JSON
    public ResponseEntity<String> updateTransport(@RequestBody DmdUserDto dto) {
        try {

            dmdService.updateDmdUserTransport(dto);

            // Return a JSON success response
            return ResponseEntity.ok("{\"success\": true}");
        } catch (Exception e) {
            // Return a JSON error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": false, \"message\": \"An error occurred.\"}");
        }
    }*/

    @PostMapping("/createEntretienHerbdomadaire")
    String saveEntretienHerbdomadaire(@RequestBody EntretienHerbdomadaireDto dto){

        String a = "";

        entretienService.createEntretienHerbdomadaire(dto);

        return "redirect:/Entretien/entretien";
    }

    @GetMapping("/retrieveVehiculeVidange/{idVehicule}")
    @ResponseBody
    public Map<String, Object> VidangeVehicule(@PathVariable Long idVehicule){
        Map<String, Object> data = new HashMap<>();

        Vehicule vehicule = vehiculeService.findById(idVehicule);

        if (vehicule != null) {
            List<EntretienVidange> entretienVidangeList = vehicule.getEntretienVidange();

            if (!entretienVidangeList.isEmpty()) {
                EntretienVidange latestEntretien = entretienVidangeList.get(entretienVidangeList.size() -1);

                data.put("dateEntretien", latestEntretien.getDateEntretien().toString());
                data.put("nouveauKilometrage", latestEntretien.getNouveauKilometrage());
                // Check if ancienKilometrage is not null before assigning

            }

        }
        return data;
    }


    @PostMapping("/createEntretienVidange")
    String saveEntretienVidange(@RequestBody EntretienVidangeDto dto, @RequestParam("recuEntretien") MultipartFile file,
                                RedirectAttributes redirectAttributes){

        Vehicule vehicule = vehiculeService.findById(dto.getVehicule().getIdVehicule());

        EntretienVidange entretienVidange = new EntretienVidange();

        entretienVidange.setDateEntretien(dto.getDateEntretien());
        entretienVidange.setVehicule(vehicule);
        entretienVidange.setAncienKilometrage(dto.getAncienKilometrage());
        entretienVidange.setNouveauKilometrage(dto.getNouveauKilometrage());
        entretienVidange.setMontantTotal(dto.getMontantTotal());

        /*String UPLOADED_FOLDER1 = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\temp\\";
        //String UPLOADED_FOLDER = "C:\\Users\\PC\\Desktop\\parcauto-master\\src\\main\\resources\\static\\temp\\";
        String UPLOADED_FOLDER = UPLOADED_FOLDER1.replace("\\", "\\\\");

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
        }*/

        entretienVidange.setRecuEntretien(dto.getRecuEntretienFilename());
        /*entretienVidange.setRecuEntretien(fileName);*/

        EntretienVidange entretienVidange1 = entretienVidangeRepository.save(entretienVidange);

        List<DetailVidangeDto> detailVidangeDtos = dto.getDetailVidangeDto();

        for(DetailVidangeDto dtos: detailVidangeDtos){

            DetailVidange detailVidange = new DetailVidange();

            detailVidange.setArticle(dtos.getArticle());
            detailVidange.setMontant(dtos.getMontant());
            detailVidange.setEntretien(entretienVidange1);

            detailVidangeRepository.save(detailVidange);

        }


        return "redirect:/Vidange/vidange";
    }








   /* @PostMapping("/vehiculeIndisponible")
    String saveIndisponible(@RequestBody VehiculeHistoriqueDto dto){

        String a = "";

        vehiculeIndisponibleService.create(dto);

        return "redirect:/Attribution/index";
    }*/




    @PostMapping("/attribution")
    public String showSelectedRows(@RequestParam("id[]") Long id, Model model, Principal principal) {
        Employe employe = employeService.getEmployeByUserName(principal.getName());
        List<DmdUserDtoOut> dtos = new ArrayList<>();
        List<EmployeDmd> epDmds = (List<EmployeDmd>) employeDmdSevice.findById(id);
        dtos = dmdService.listDmdsToDto(epDmds);
        model.addAttribute("rows", dtos);
        return "dmd/attribution";

    }

}
