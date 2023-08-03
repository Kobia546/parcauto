package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.entities.Marque;
import ci.nkagou.parcauto.exceptions.ResourceNotFoundException;
import ci.nkagou.parcauto.repositories.MarqueRepository;
import ci.nkagou.parcauto.services.MarqueService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class MarqueServiceImpl implements MarqueService {

    private MarqueRepository  marqueRepository;
    @Override
    public List<Marque> all() {
        return marqueRepository.findAll();
    }

    @Override
    public Marque findById(Long id) {

        return marqueRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Marque introuvable avec l'identifiant :  " + id)
        );
    }

    @Override
    public Marque findByName(String nomMarque) {
        return marqueRepository.findByName(nomMarque);
    }

    @Override
    public Marque create(Marque marque) {
        return marqueRepository.save(marque);
    }

    @Override
    public Marque update(Marque marque) {
        return marqueRepository.save(marque);
    }

    @Override
    public void delete(Marque marque) {
        marqueRepository.delete(marque);

    }
}
