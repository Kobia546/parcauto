package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.entities.Assurance;
import ci.nkagou.parcauto.exceptions.ResourceNotFoundException;
import ci.nkagou.parcauto.repositories.AssuranceRepository;
import ci.nkagou.parcauto.services.AssuranceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class AssuranceServiceImpl implements AssuranceService {

    private AssuranceRepository assuranceRepository;

    @Override
    public List<Assurance> all() {
        return assuranceRepository.findAll();
    }

    @Override
    public Assurance findById(Long id) {

        return assuranceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Assurance introuvable avec l'identifiant :  " + id)
        );
    }

    @Override
    public Assurance findByNumeroPolice(String numeroPolice) {
        return assuranceRepository.findByNumPolice(numeroPolice);
    }

    @Override
    public Assurance create(Assurance assurance) {
        return assuranceRepository.save(assurance);
    }

    @Override
    public Assurance update(Assurance assurance) {
        return assuranceRepository.save(assurance);
    }

    @Override
    public void delete(Assurance assurance) {
        assuranceRepository.delete(assurance);
    }
}
