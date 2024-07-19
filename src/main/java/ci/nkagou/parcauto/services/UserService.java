package ci.nkagou.parcauto.services;


import ci.nkagou.parcauto.entities.AppRole;
import ci.nkagou.parcauto.entities.AppUser;
import ci.nkagou.parcauto.entities.Employe;

import java.util.List;

public interface UserService  {

    AppUser create (AppUser user);
    List<AppUser> all();
    List<AppUser> allSortByRoleProperty(String roleProperty);
    List<AppUser> getUserListWithRoleInString(List<AppUser> Users);
    AppUser findByUserName(String userName);
    Boolean existsByUserName(String userName);

    AppUser findByEmploye(Employe employe);

    List<AppUser> listUserByRole(AppRole role);

    AppRole getRoleByName(String roleName);
    void updateEmailEmploye(String emailEmploye, Employe employe);


}
