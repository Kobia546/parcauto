package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.entities.Typevehicule;
import ci.nkagou.parcauto.exceptions.ResourceNotFoundException;
import ci.nkagou.parcauto.repositories.TypevehiculeRepository;
import ci.nkagou.parcauto.services.TypevehiculeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class TypevehiculeServiceImpl implements TypevehiculeService {

    private TypevehiculeRepository typevehiculeRepository;
    @Override
    public List<Typevehicule> all() {
        return typevehiculeRepository.findAll();
    }

    @Override
    public Typevehicule findById(Long id) {
        return typevehiculeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Type de vehicule introuvable avec l'identifiant :  " + id)
        );
    }

    @Override
    public Typevehicule findByLibelle(String libelle) {
        return typevehiculeRepository.findByLibelle(libelle);
    }

    @Override
    public Typevehicule create(Typevehicule typevehicule) {
        return typevehiculeRepository.save(typevehicule);
    }

    @Override
    public Typevehicule update(Typevehicule typevehicule) {
        return typevehiculeRepository.save(typevehicule);
    }

    @Override
    public void delete(Typevehicule typevehicule) {
        typevehiculeRepository.delete(typevehicule);
    }
}
