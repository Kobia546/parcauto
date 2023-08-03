package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.entities.Site;

import java.util.List;
import java.util.Optional;

public interface SiteService {

    List<Site> all();
    Site findById(Long id);

    Site findByLibelle(String libelle);
    Site create (Site site);
    Site update (Site site);

    void delete(Site site);
}
