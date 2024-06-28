package ci.nkagou.parcauto.controllers;

import ci.nkagou.parcauto.entities.Destination;
import ci.nkagou.parcauto.entities.Motif;
import ci.nkagou.parcauto.services.DestinationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class DestinationController {

    private DestinationService destinationService;

    public DestinationController(DestinationService destinationService){
        this.destinationService = destinationService;
    }

    @RequestMapping("/Destination/destination")
    public String indexMotif(Model model, Principal principal, HttpServletRequest request) {

        List<Destination> destination = destinationService.all();

        model.addAttribute("listDestination",destination);
        return "Destination/index";

    }

}
