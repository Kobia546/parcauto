package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.entities.AppUser;
import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.EmployeDmd;
import ci.nkagou.parcauto.exceptions.ResourceNotFoundException;
import ci.nkagou.parcauto.repositories.EmployeDmdRepository;
import ci.nkagou.parcauto.repositories.UserRepository;
import ci.nkagou.parcauto.services.EmployeDmdService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class EmployeDmdServiceImpl implements EmployeDmdService {

    private EmployeDmdRepository employeDmdRepository;
    private UserRepository userRepository;

    @Override
    public List<EmployeDmd> all() {
        return employeDmdRepository.findAll();
    }

    @Override
    public EmployeDmd findById(Long id) {

        return employeDmdRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employe_Dmd introuvable avec l'identifiant :  " + id)
        );
    }

    @Override
    public EmployeDmd create(EmployeDmd employeDmd) {
        return employeDmdRepository.save(employeDmd);
    }

    @Override
    public EmployeDmd update(EmployeDmd employeDmd) {
        return employeDmdRepository.save(employeDmd);
    }

    @Override
    public void delete(EmployeDmd employeDmd) {
    employeDmdRepository.delete(employeDmd);
    }


}
