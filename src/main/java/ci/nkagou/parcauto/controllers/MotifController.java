package ci.nkagou.parcauto.controllers;

import ci.nkagou.parcauto.dtos.dmd.DmdUserDtoOut;
import ci.nkagou.parcauto.entities.AppRole;
import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.EmployeDmd;
import ci.nkagou.parcauto.entities.Motif;
import ci.nkagou.parcauto.enums.Statut;
import ci.nkagou.parcauto.services.MotifService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
public class MotifController {
    private MotifService motifService;

    public MotifController(MotifService motifService){
        this.motifService = motifService;
    }

    @RequestMapping("/Motif/motif")
    public String indexMotif(Model model, Principal principal, HttpServletRequest request) {

        List<Motif> motif = motifService.all();

        model.addAttribute("listMotif",motif);
        return "Motif/index";

    }

    @RequestMapping(value = "/Motif/motif/new", method = RequestMethod.GET)
    public String newMotif(Model model){

        Motif motif = new Motif();

        model.addAttribute("motifs",motif);
        model.addAttribute("title", "Motif - Nouveau");
        return "Motif/new";

    }

    @RequestMapping(value = "/Motif/motif/save", method = RequestMethod.POST)
    public String saveMotif(@Valid Motif motif, Model model){

        motifService.create(motif);

        return "redirect:/Motif/motif";

    }

    @RequestMapping(value = "/Motif/motif/edit/{id}", method = RequestMethod.GET)
    public String editMotif(@PathVariable Long id, Model model){

        Motif motif = motifService.findById(id);
        //List<Motif> motif1 = motifService.all();
        model.addAttribute("motifs",motif);
        //model.addAttribute("listMotif",motif1);

        return "Motif/edit";
    }


}
