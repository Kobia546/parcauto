package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.entities.AppRole;
import ci.nkagou.parcauto.entities.AppUser;
import ci.nkagou.parcauto.entities.UserRole;
import ci.nkagou.parcauto.repositories.UserRoleRepository;
import ci.nkagou.parcauto.services.UserRoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> all() {
        return userRoleRepository.findAll();
    }

    @Override
    public UserRole create(UserRole userRole) {
        log.info("Utilisateur affecté aux roles");
        return userRoleRepository.save(userRole);
    }

    @Override
    public List<UserRole> findByAppRoleIsNot(AppRole role) {
        return userRoleRepository.findByAppRoleIsNot(role);
    }

    @Override
    public List<UserRole> findByAppUser(AppUser user) {
        return userRoleRepository.findByAppUser(user);
    }
}
