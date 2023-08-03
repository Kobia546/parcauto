package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.entities.Moyen;
import ci.nkagou.parcauto.exceptions.ResourceNotFoundException;
import ci.nkagou.parcauto.repositories.MoyenRepository;
import ci.nkagou.parcauto.services.MoyenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class MoyenServiceImpl implements MoyenService {
    private MoyenRepository moyenRepository;
    @Override
    public List<Moyen> all() {
        return moyenRepository.findAll();
    }

    @Override
    public Moyen findById(Long id) {

        return moyenRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Moyen introuvable avec l'identifiant :  " + id)
        );
    }

    @Override
    public Moyen findByLibelle(String libelle) {
        return moyenRepository.findByLibelle(libelle);
    }

    @Override
    public Moyen create(Moyen moyen) {
        return moyenRepository.save(moyen);
    }

    @Override
    public Moyen update(Moyen moyen) {
        return moyenRepository.save(moyen);
    }

    @Override
    public void delete(Moyen moyen) {
        moyenRepository.delete(moyen);
    }
}
