package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.entities.AppRole;
import ci.nkagou.parcauto.entities.AppUser;

import java.util.List;

public interface RoleService {

    AppRole create (AppRole role);
    AppRole findByRoleName (String roleName);
    List<AppRole> all();
    List<AppRole> allSortByRoleProperty(String roleProperty);
    List<AppRole> findByRoleNameIsNot(String roleName);
    AppRole getById(Long id);
    Boolean deleteById (Long id);

    List<AppRole> getRolesByUser(AppUser user);
    List<String> getRoleNames (List<AppRole> roles);
}
