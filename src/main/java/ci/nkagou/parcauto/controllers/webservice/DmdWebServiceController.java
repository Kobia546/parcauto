package ci.nkagou.parcauto.controllers.webservice;

import ci.nkagou.parcauto.dtos.dmd.*;
import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.EmployeDmd;
import ci.nkagou.parcauto.entities.Marque;
import ci.nkagou.parcauto.repositories.EmployeDmdRepository;
import ci.nkagou.parcauto.repositories.EmployeRepository;
import ci.nkagou.parcauto.services.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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


    public DmdWebServiceController(MarqueService marqueService,DmdService dmdService,EmployeDmdService employeDmdService, EmployeRepository employeRepository,EmployeDmdRepository employeDmdRepository,AttributionService attributionService,EmployeService employeService ) {
        this.marqueService = marqueService;
        this.dmdService = dmdService;
        this.employeService = employeService;
        this.employeDmdService = employeDmdService;
        this.employeRepository = employeRepository;
        this.attributionService = attributionService;
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

    @PostMapping("/create")
    String saveAttribution(@RequestBody AttributionDto dto){

        String a = "";

        attributionService.create(dto);

        return "redirect:/Attribution/index";
    }

    @PostMapping("/createVehiculeChauffeur")
    String saveAttributionVehiculeChauffeur(@RequestBody AttributionVehiculeChauffeurAttDto dtos){

        String a = "";

        attributionService.createVehiculeChauffeur(dtos);

        return "redirect:/Attribution/index";
    }

    @PostMapping("/createCarburant")
    String saveAttributionCarburant(@RequestBody AttributionCarburantAttDto dtos){

        String a = "";

        attributionService.createCarburant(dtos);

        return "redirect:/Attribution/index";
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
