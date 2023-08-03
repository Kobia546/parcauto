package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.entities.AppRole;
import ci.nkagou.parcauto.entities.AppUser;
import ci.nkagou.parcauto.entities.UserRole;
import ci.nkagou.parcauto.repositories.RoleRepository;
import ci.nkagou.parcauto.services.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Override
    public AppRole create(AppRole role) {
        log.info("Nouveau role enregisté");
        return roleRepository.save(role);
    }

    @Override
    public AppRole findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public List<AppRole> all() {
        return roleRepository.findAll();
    }

    @Override
    public List<AppRole> allSortByRoleProperty(String roleProperty) {
        return roleRepository.findAll(Sort.by(Sort.Direction.DESC,roleProperty));
    }

    @Override
    public List<AppRole> findByRoleNameIsNot(String roleName) {
        return roleRepository.findByRoleNameIsNot(roleName);
    }

    @Override
    public AppRole getById(Long id) {
        return roleRepository.getById(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        roleRepository.deleteById(id);
        return true;
    }

    @Override
    public List<AppRole> getRolesByUser(AppUser user) {
        List<UserRole> userroles = user.getUserRoles();
        List<AppRole> roles = new ArrayList<AppRole>();
        for (UserRole userrole :userroles){
            AppRole r = userrole.getAppRole();
            roles.add(r);
        }
        return roles;
    }

    @Override
    @Transactional(readOnly=true)
    public List<String> getRoleNames(List<AppRole> roles) {

        List<String> roleNames = new ArrayList<>();

        for (AppRole a : roles){
            // Get User Role Name
            String s = a.getRoleName();

            //Add in list array
            roleNames.add(s);
        }
        return roleNames;
    }
}
