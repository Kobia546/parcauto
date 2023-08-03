package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.entities.AppRole;
import ci.nkagou.parcauto.entities.AppUser;
import ci.nkagou.parcauto.entities.UserRole;

import java.util.List;

public interface UserRoleService {

    List<UserRole> all ();
    UserRole create (UserRole userRole);
    List<UserRole> findByAppRoleIsNot(AppRole role);
    List<UserRole> findByAppUser(AppUser user);

}
