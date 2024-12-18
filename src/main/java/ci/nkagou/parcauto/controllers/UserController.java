package ci.nkagou.parcauto.controllers;

import ci.nkagou.parcauto.dtos.chauffeur.EmployeDtoOut;
import ci.nkagou.parcauto.entities.AppRole;
import ci.nkagou.parcauto.entities.AppUser;
import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.UserRole;
import ci.nkagou.parcauto.services.EmployeService;
import ci.nkagou.parcauto.services.RoleService;
import ci.nkagou.parcauto.services.UserRoleService;
import ci.nkagou.parcauto.services.UserService;
import ci.nkagou.parcauto.utils.EncrytedPasswordUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
/*@Transactional*/
public class UserController {

    private UserService userService;
    private RoleService roleService;
    private UserRoleService userRoleService;
    private EmployeService employeService;

    @RequestMapping(value = "/acces/users")
    public String indexUser(Model model, HttpServletRequest request){

        List<AppUser> users = new ArrayList<AppUser>();

        if (request.isUserInRole("ROLE_ADMIN")) {
            users = userService.allSortByRoleProperty("userId");
            users = userService.getUserListWithRoleInString(users);

        }else {

            String r = "ROLE_ADMIN";
            AppRole role = roleService.findByRoleName(r);
            List<UserRole> listUsers = userRoleService.findByAppRoleIsNot(role);
            List<AppUser> allusers = new ArrayList<AppUser>();

            for (UserRole userrole:listUsers) {

                AppUser myuser = userrole.getAppUser();
                allusers.add(myuser);
            }

            users = allusers;
            users = userService.getUserListWithRoleInString(users);
        }

        model.addAttribute("listusers",users);
        model.addAttribute("title", "Utilisateur - Liste");
        return "user/index";
    }
    @RequestMapping(value = "/createOrUpdate", method = RequestMethod.POST)
    public Employe createOrUpdateEmploye(@RequestBody EmployeDtoOut employeDto) {
        return employeService.createOrUpdateEmploye(employeDto);
    }
//@PostMapping("/createOrUpdate")
//public RedirectView createOrUpdateEmploye(@ModelAttribute EmployeDtoOut employeDto) {
//    employeService.createOrUpdateEmploye(employeDto);
//    return new RedirectView("/acces/users");
//}
     @RequestMapping(value = "/acces/users/newuser",method = RequestMethod.GET)
        public String newcreateuser(){
            return "dmd/newUser" ;
    }

    @RequestMapping(value = "/acces/users/new", method = RequestMethod.GET)
    public String newUser(Model model, HttpServletRequest request){

        List<AppRole> roles = new ArrayList<AppRole>();

        String r = "ROLE_ADMIN";

        List<Employe> employes = employeService.findEmployesByEstUtilisateur(false);

        if (request.isUserInRole(r)) {
            roles = roleService.all();
        }
        else {

            roles = roleService.findByRoleNameIsNot(r);
        }

        model.addAttribute("monuser",new AppUser());
        model.addAttribute("title", "Utilisateur - Nouveau");
        model.addAttribute("roles", roles);
        model.addAttribute("listEmployes", employes);
        return "user/new";

    }

    @RequestMapping(value = "/acces/users/save", method = RequestMethod.POST)
    public String saveUser(@Valid AppUser user, Errors errors, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){

        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("monuser", new AppUser());
            //model.addAttribute("errors", errors);
            return "user/new";
        }
        //Encrypt Password
        String password = EncrytedPasswordUtils.encrytePassword(user.getEncrytedPassword());
        user.setEncrytedPassword(password);;
        user.setEnabled(true);

        // Save User
        AppUser u = userService.create(user);

        userService.updateEmailEmploye(u.getEmail(),u.getEmploye());

        //Update <employe table field est utilisateur


        //Get role form Select multiple value
        String[] selected = request.getParameterValues("role");

        //Add role
        for (String s : selected){

            Long id = Long.parseLong(s);
            AppRole role = roleService.getById(id);
            UserRole userRole = new UserRole();
            userRole.setAppUser(u);
            userRole.setAppRole(role);

            userRoleService.create(userRole);


        }
        redirectAttributes.addFlashAttribute("messagesucces","Opération éffectée avec succès");
        return "redirect:/acces/users";
    }


}
