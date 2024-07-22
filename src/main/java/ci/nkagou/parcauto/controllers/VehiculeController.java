package ci.nkagou.parcauto.controllers;

import ci.nkagou.parcauto.dtos.chauffeurhistorique.ChauffeurHistoriqueDtoOut;
import ci.nkagou.parcauto.dtos.dmd.EtatChauffeurDto;
import ci.nkagou.parcauto.dtos.dmd.EtatVehiculeDto;
import ci.nkagou.parcauto.dtos.vehicule.VehiculeDto;
import ci.nkagou.parcauto.dtos.vehicule.VehiculeDtoOut;
import ci.nkagou.parcauto.dtos.vehicule.VehiculeDtoZ;
import ci.nkagou.parcauto.dtos.vehiculeIndisponible.VehiculeHistoriqueDtoOut;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.*;
import ci.nkagou.parcauto.repositories.VehiculeAttRepository;
import ci.nkagou.parcauto.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
public class VehiculeController {

    private final VehiculeService vehiculeService;

    private final TypevehiculeService typevehiculeService;

    private final MarqueService marqueService;

    private VehiculeHistoriqueService vehiculeHistoriqueService;

    private AttributionService attributionService;

    private VehiculeAttRepository vehiculeAttRepository;

    private EmployeService employeService;

    private ChauffeurHistoriqueService chauffeurHistoriqueService;

    public VehiculeController(VehiculeService vehiculeService, TypevehiculeService typevehiculeService, MarqueService marqueService, VehiculeHistoriqueService vehiculeHistoriqueService,AttributionService attributionService,VehiculeAttRepository vehiculeAttRepository,EmployeService employeService,ChauffeurHistoriqueService chauffeurHistoriqueService) {
        this.vehiculeService = vehiculeService;
        this.typevehiculeService = typevehiculeService;
        this.marqueService = marqueService;
        this.vehiculeHistoriqueService = vehiculeHistoriqueService;
        this.attributionService = attributionService;
        this.vehiculeAttRepository = vehiculeAttRepository;
        this.employeService = employeService;
        this.chauffeurHistoriqueService = chauffeurHistoriqueService;
    }



    @RequestMapping(value =  "/vehicule/vehicules", method = RequestMethod.GET)
    public String index(Model model, Principal principal, HttpServletRequest request) {

        List<Vehicule> vehicules = vehiculeService.all();
        List<VehiculeDtoOut> dtos = vehiculeService.listVehiculesToDto(vehicules);

        model.addAttribute("listVehicules", dtos);
        model.addAttribute("title", "Vehicules - Liste");

        return "vehicule/index";
    }

    @RequestMapping(value = "/vehicule/vehicules/new", method = RequestMethod.GET)
    public String newVehicule(Model model){

        //AppUser user = userService.findByUserName(principal.getName());
        List<Typevehicule> typevehicules = typevehiculeService.all();
        List<Marque> marques = marqueService.all();
        List<Couleur> couleurs = Arrays.asList(Couleur.values());

        model.addAttribute("vehiculedto",new VehiculeDto());
        model.addAttribute("listTypevehicules",typevehicules);
        model.addAttribute("listMarques",marques);
        model.addAttribute("listCouleurs",couleurs);
        model.addAttribute("title", "Vehicule - Nouveau");
        log.info("Vehicule : formulaire creation affiché");

        return "vehicule/new";
    }

    @RequestMapping(value = "/vehicule/vehicules/save", method = RequestMethod.POST)
    public String saveVehicule(@Valid VehiculeDtoZ dto,  Model model, RedirectAttributes redirectAttributes) {
        /*if (errors.hasErrors()) {
       // if ((bindingResult.hasErrors())) {
            System.out.println("error YES");
            model.addAttribute("vehiculedto", new VehiculeDto());
            List<Typevehicule> typevehicules = typevehiculeService.all();
            List<Marque> marques = marqueService.all();
            List<Couleur> couleurs = Arrays.asList(Couleur.values());
            model.addAttribute("listTypevehicules",typevehicules);
            model.addAttribute("listMarques",marques);
            model.addAttribute("listCouleurs",couleurs);
            model.addAttribute("title", "Vehicule - Nouveau");
            model.addAttribute("errors", errors);
           // return  "vehicule/new";
            return "vehicule/new";
        }*/

        Vehicule vehicule = vehiculeService.dtoToVehicule(dto);
        Vehicule v = vehiculeService.create(vehicule);
        log.info("Vehicule : immatriculation : [ "+vehicule.getImmatriculation()+" ] cree avec succes");
        //Get user connected
        // AppUser user = userService.findByUserName(principal.getName());

        redirectAttributes.addFlashAttribute("messagesucces", "Opération de création éffectuée avec succès");
        return "redirect:/vehicule/vehicules/";

    }

    @RequestMapping(value = "/vehicule/vehicules/edit/{id}", method = RequestMethod.GET)
    public String editVehicule(@PathVariable Long id, Model model){

        Vehicule vehicule = vehiculeService.findById(id);

        VehiculeDtoZ dto = new VehiculeDtoZ();

        dto.setIdVehicule(vehicule.getIdVehicule());
        dto.setImmatriculation(vehicule.getImmatriculation());
        dto.setCouleur(vehicule.getCouleur());
        dto.setCarteGrise(vehicule.getCarteGrise());
        dto.setNumeroChassis(vehicule.getNumeroChassis());
        dto.setStatutVehicule(vehicule.getStatutVehicule());
        dto.setMarque(vehicule.getMarque());
        dto.setTypeVehicule(vehicule.getTypevehicule());
        dto.setRaison(vehicule.getRaison());
        dto.setDateAchat(vehicule.getDateAchat());


        List<Typevehicule> typevehicules = typevehiculeService.all();

        List<Marque> marques = marqueService.all();
        List<Couleur> couleurs = Arrays.asList(Couleur.values());
        List<StatutVehicule> statutVehicules = Arrays.asList(StatutVehicule.values());
        model.addAttribute("listTypevehicules",typevehicules);
        model.addAttribute("listMarques",marques);
        model.addAttribute("listCouleurs",couleurs);
        model.addAttribute("vehiculedto",dto);
        model.addAttribute("StatutVehicule",statutVehicules);
        model.addAttribute("title", "Vehicule - Edition");
        return "vehicule/edit";
    }

    @RequestMapping(value = "/vehicule/vehicules/update", method = RequestMethod.POST)
    public String updateProjet(@Valid VehiculeDtoZ dto,  Model model, RedirectAttributes redirectAttributes){

        /*if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            //model.addAttribute("monprojet", new Projet());
            return "vehicule/new";
        }*/
        Vehicule vehicule = vehiculeService.dtoToVehiculeUpdate(dto);
        Vehicule v = vehiculeService.update(vehicule);


        return "redirect:/vehicule/vehicules";

    }

    @RequestMapping(value = "/vehicule/vehicules/delete/{id}", method = RequestMethod.GET)
    public String deleteVehicule(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Vehicule vehicule = vehiculeService.findById(id);
        if (vehicule != null) {
            vehiculeService.deleteReferences(id); // Supprime les références avant de supprimer le véhicule
            vehiculeService.delete(vehicule);
            redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation effectuée avec succès");
        } else {
            redirectAttributes.addFlashAttribute("messageerror", "Véhicule introuvable");
        }
        return "redirect:/vehicule/vehicules/";
    }
    @RequestMapping(value = "/vehicule/vehicules/disponible/{id}", method = RequestMethod.GET)
    public String vehiculeDisponible(@PathVariable Long id, @Valid Vehicule vehicule, Model model, RedirectAttributes redirectAttributes, Principal principal){


        Vehicule vehicule3 = vehiculeService.findById(id);
        Vehicule vehicule1 = vehiculeService.disponible(id, vehicule3);

        vehiculeService.update(vehicule1);


        redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation éffectuée avec succès");

        return "redirect:/vehicule/vehicules";

    }

    /*@RequestMapping(value = "/vehicule/vehicules/indisponible/{id}", method = RequestMethod.GET)
    public String vehiculeIndisponible(@PathVariable Long id, @Valid Vehicule vehicule, Model model, RedirectAttributes redirectAttributes, Principal principal){


        Vehicule vehicule3 = vehiculeService.findById(id);
        Vehicule vehicule1 = vehiculeService.indisponible(id, vehicule3);

        vehiculeService.update(vehicule1);


        redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation éffectuée avec succès");

        return "redirect:/vehicule/vehicules";

    }*/

    @RequestMapping(value = "/vehicule/vehicules/indisponible/{id}", method = RequestMethod.GET)
    public String vehiculeIndisponible(@PathVariable Long id,  Model model, RedirectAttributes redirectAttributes){


        Vehicule vehicule = vehiculeService.findById(id);

        VehiculeDtoZ dto = new VehiculeDtoZ();

        dto.setIdVehicule(vehicule.getIdVehicule());
        dto.setImmatriculation(vehicule.getImmatriculation());
        dto.setCouleur(vehicule.getCouleur());
        dto.setCarteGrise(vehicule.getCarteGrise());
        dto.setNumeroChassis(vehicule.getNumeroChassis());
        dto.setStatutVehicule(vehicule.getStatutVehicule());
        dto.setMarque(vehicule.getMarque());
        dto.setTypeVehicule(vehicule.getTypevehicule());
        dto.setRaison(vehicule.getRaison());
        dto.setDateAchat(vehicule.getDateAchat());

        List<Typevehicule> typevehicules = typevehiculeService.all();

        List<Marque> marques = marqueService.all();
        List<Couleur> couleurs = Arrays.asList(Couleur.values());
        List<StatutVehicule> statutVehicules = Arrays.asList(StatutVehicule.values());
        model.addAttribute("listTypevehicules",typevehicules);
        model.addAttribute("listMarques",marques);
        model.addAttribute("listCouleurs",couleurs);
        model.addAttribute("vehiculedto",dto);
        model.addAttribute("StatutVehicule",statutVehicules);
        model.addAttribute("title", "Vehicule - Edition");

        redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation éffectuée avec succès");

        return "/vehicule/raison";

    }

    @RequestMapping(value = "/vehicule/vehicules/reparation/{id}", method = RequestMethod.GET)
    public String vehiculeReparation(@PathVariable Long id, @Valid Vehicule vehicule, Model model, RedirectAttributes redirectAttributes, Principal principal){


        Vehicule vehicule3 = vehiculeService.findById(id);
        Vehicule vehicule1 = vehiculeService.reparation(id, vehicule3);

        vehiculeService.update(vehicule1);


        redirectAttributes.addFlashAttribute("messagesucces", "Opération de validation éffectuée avec succès");

        return "redirect:/vehicule/vehicules";

    }

    @RequestMapping(value = "/vehicule/vehicules/raison/{id}", method = RequestMethod.GET)
    public String raison(@PathVariable Long id, Model model){

        Vehicule vehicule = vehiculeService.findById(id);
        //VehiculeIndisponible vehicule2 = vehiculeIndisponibleService.findById(id);
        VehiculeDto dto = new VehiculeDto();

        dto.setId(vehicule.getIdVehicule());
        dto.setImmatriculation(vehicule.getImmatriculation());
        dto.setMarque(vehicule.getMarque().getIdMarque());
        dto.setStatutVehicule(vehicule.getStatutVehicule().name());
        dto.setTypeVehicule(vehicule.getTypevehicule().getIdTypevehicule());
        dto.setCouleur(String.valueOf(vehicule.getCouleur()));
        dto.setCarteGrise(vehicule.getCarteGrise());
        dto.setDateAchat(vehicule.getDateAchat());
        dto.setRaison(vehicule.getRaison());
        //dto.setDateIndisponibilité(vehicule2.getDateIndisponibilité());
        //dto.setStatutVehiculeIn(vehicule2.getStatutVehiculeIn());


        List<Typevehicule> typevehicules = typevehiculeService.all();
        List<Marque> marques = marqueService.all();
        List<Couleur> couleurs = Arrays.asList(Couleur.values());
        List<StatutVehicule> statutVehicules = Arrays.asList(StatutVehicule.values());

        model.addAttribute("listTypevehicules",typevehicules);
        model.addAttribute("listMarques",marques);
        model.addAttribute("vehicule",dto);
        model.addAttribute("listCouleurs",couleurs);
        model.addAttribute("listStatutVehicule",statutVehicules);

        model.addAttribute("title", "Vehicule - Nouveau");

        return "vehicule/raison";

    }

    @RequestMapping(value = "/vehicule/vehicules/raison", method = RequestMethod.POST)
    public String update(@Valid VehiculeDtoZ dto, RedirectAttributes redirectAttributes){


        //Vehicule vehicule = vehiculeService.dtoToVehicule(dto);
        vehiculeService.raison(dto);

        redirectAttributes.addFlashAttribute("messagesucces", "Opération de création éffectuée avec succès");

        return "redirect:/vehicule/vehicules";

    }

    @RequestMapping(value = "/vehiculeHistorique/vehiculeHistoriques")
    public String listVehiculeHistorique(Model model, Principal principal, HttpServletRequest request){

        List<VehiculeHistoriqueDtoOut> dtos = new ArrayList<>();

        List<VehiculeHistorique> vehiculeHistoriques = vehiculeHistoriqueService.all();
        dtos = vehiculeHistoriqueService.listVehiculesToDto(vehiculeHistoriques);


        model.addAttribute("listVehiculeHistorique", dtos);

        return "vehicule/vehiculehistorique";

    }

    @RequestMapping("/EtatVehicule/etatVehicule")
    public String etatVehicule(Model model, Principal principal, HttpServletRequest request) {
        EtatVehiculeDto dto = new EtatVehiculeDto();

        List<Vehicule> vehicules = vehiculeService.all();
        List<Employe> employe = employeService.all();

        //List<EmployeDmd> employeDmds = dmdService.all();
        List<SelectionEmploye> selections = Arrays.asList(SelectionEmploye.values());

        model.addAttribute("listVehicule", vehicules);
        model.addAttribute("listEmploye", employe);
        model.addAttribute("listSelectionChoix", selections);
        model.addAttribute("dto",dto);

        return "dmd/etatVehicule";
    }

    @RequestMapping("/FiltreEtatVehicule/filtreEtatVehicule")
    public String etatEmployeRapport(@Valid @ModelAttribute("dto") EtatVehiculeDto dto, BindingResult bindingResult, Model model, Principal principal, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            System.out.println("error YES");

            //*EtatVehiculeDto dto = new EtatVehiculeDto();*//*

            List<Vehicule> vehicules = vehiculeService.all();
            List<Employe> employe = employeService.all();

            //List<EmployeDmd> employeDmds = dmdService.all();
            List<SelectionEmploye> selections = Arrays.asList(SelectionEmploye.values());

            model.addAttribute("listVehicule", vehicules);
            model.addAttribute("listEmploye", employe);
            model.addAttribute("listSelectionChoix", selections);
            //*model.addAttribute("dto",dto);*//*

            return "dmd/etatVehicule";

        }
        List<VehiculeHistoriqueDtoOut> dtos = new ArrayList<>();
        List<VehiculeHistorique> vehiculeHistoriques = vehiculeHistoriqueService.listVehiculeHistoriqueByDateBetweenAndVehiculeAttVehicule(dto);
        dtos = vehiculeHistoriqueService.listVehiculesToDto(vehiculeHistoriques);

        /*if (dto.getSelectionEmploye() == SelectionEmploye.VEHICULE) {

        } else if (dto.getSelectionEmploye() == SelectionEmploye.CONDUCTEUR) {
            // List<VehiculeHistorique> vehiculeHistoriques = vehiculeHistoriqueService.listVehiculeHistoriqueByDateBetweenAndVehiculeAttEmployeDmdEmploye(dto);
             //dtos = vehiculeHistoriqueService.listVehiculesToDto(vehiculeHistoriques);
        }*/

        /*if (dto.getSelectionEmploye() == SelectionEmploye.VEHICULE) {
            model.addAttribute("statusVehiculeVisible", true);
        } else if (dto.getSelectionEmploye() == SelectionEmploye.CONDUCTEUR) {
            model.addAttribute("statusConducteurVisible", true);
        }*/

        //List<EmployeDmd> employeDmds = dmdService.all();
        model.addAttribute("listEtatVehicule",dtos);
        //model.addAttribute("listDmdRapport",employeDmds);

        return "dmd/indexEtatVehicule";

    }

    @RequestMapping("/EtatChauffeur/etatChauffeur")
    public String etatChauffeur(Model model, Principal principal, HttpServletRequest request) {
        EtatChauffeurDto dto = new EtatChauffeurDto();

        List<Vehicule> vehicules = vehiculeService.all();
        List<Employe> employe = employeService.findEmployesByEstChauffeur(true);

        //List<EmployeDmd> employeDmds = dmdService.all();
        List<SelectionChauffeur> selections = Arrays.asList(SelectionChauffeur.values());

        model.addAttribute("listVehicule", vehicules);
        model.addAttribute("listEmploye", employe);
        model.addAttribute("listSelectionChoix", selections);
        model.addAttribute("dto",dto);

        return "dmd/etatChauffeur";
    }

    @RequestMapping("/FiltreEtatChauffeur/filtreEtatChauffeur")
    public String etatChauffeur(@Valid @ModelAttribute("dto") EtatChauffeurDto dto, BindingResult bindingResult, Model model, Principal principal, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            System.out.println("error YES");

            /*EtatChauffeurDto dto = new EtatChauffeurDto();*/

            List<Vehicule> vehicules = vehiculeService.all();
            List<Employe> employe = employeService.findEmployesByEstChauffeur(true);

            //List<EmployeDmd> employeDmds = dmdService.all();
            List<SelectionChauffeur> selections = Arrays.asList(SelectionChauffeur.values());

            model.addAttribute("listVehicule", vehicules);
            model.addAttribute("listEmploye", employe);
            model.addAttribute("listSelectionChoix", selections);
            /*model.addAttribute("dto",dto);*/

            return "dmd/etatChauffeur";

        }
        List<ChauffeurHistoriqueDtoOut> dtos = new ArrayList<>();
        if (dto.getSelectionChauffeur() == SelectionChauffeur.VEHICULE) {
            List<ChauffeurHistorique> chauffeurHistoriques = chauffeurHistoriqueService.listChauffeurHistoriqueByDateBetweenAndVehiculeChauffeurAttVehicule(dto);
            dtos = chauffeurHistoriqueService.listChauffeurHistoriquesToDto(chauffeurHistoriques);
        } else if (dto.getSelectionChauffeur() == SelectionChauffeur.CHAUFFEUR) {
            List<ChauffeurHistorique> chauffeurHistoriques = chauffeurHistoriqueService.listChauffeurHistoriqueByDateBetweenAndVehiculeChauffeurAttEmploye(dto);
            dtos = chauffeurHistoriqueService.listChauffeurHistoriquesToDto(chauffeurHistoriques);
        }

        if (dto.getSelectionChauffeur() == SelectionChauffeur.VEHICULE) {
            model.addAttribute("statusVehiculeVisible", true);
        } else if (dto.getSelectionChauffeur() == SelectionChauffeur.CHAUFFEUR) {
            model.addAttribute("statusChauffeurVisible", true);
        }
        //List<EmployeDmd> employeDmds = dmdService.all();
        model.addAttribute("listEtatChauffeur",dtos);
        //model.addAttribute("listDmdRapport",employeDmds);

        return "dmd/indexEtatChauffeur";
    }


}
