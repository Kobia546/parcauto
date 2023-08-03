package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUserName(String userName);
    Boolean existsByUserName(String userName);
}
