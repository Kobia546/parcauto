package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.AppRole;
import ci.nkagou.parcauto.entities.AppUser;
import ci.nkagou.parcauto.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findByAppUser(AppUser user);
    List<UserRole> findByAppRoleIsNot(AppRole role);
}
