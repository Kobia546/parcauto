package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.entities.Site;
import ci.nkagou.parcauto.exceptions.ResourceNotFoundException;
import ci.nkagou.parcauto.repositories.SiteRepository;
import ci.nkagou.parcauto.services.SiteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class SiteServiceImpl implements SiteService {

    private SiteRepository siteRepository;


    @Override
    public List<Site> all() {
        return siteRepository.findAll();
    }

    @Override
    public Site findById(Long id) {

        return siteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Site introuvable avec l'identifiant :  " + id)
        );
    }



    @Override
    public Site findByLibelle(String libelle) {
        return siteRepository.findByLibelle(libelle);
    }

    @Override
    public Site create(Site site) {
        return siteRepository.save(site);
    }

    @Override
    public Site update(Site site) {
        return siteRepository.save(site);
    }

    @Override
    public void delete(Site site) {
        siteRepository.delete(site);
    }
}
