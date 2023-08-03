package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<AppRole, Long> {

    AppRole findByRoleId (Long id);

    AppRole findByRoleName(String roleName);

    List<AppRole> findByRoleNameIsNot(String rolename);
}
