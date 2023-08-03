package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.entities.Periode;
import ci.nkagou.parcauto.exceptions.ResourceNotFoundException;
import ci.nkagou.parcauto.repositories.PeriodeRepository;
import ci.nkagou.parcauto.services.PeriodeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class PeriodeServiceImpl implements PeriodeService {

    private PeriodeRepository periodeRepository;
    @Override
    public List<Periode> all() {
        return periodeRepository.findAll();
    }

    @Override
    public Periode findById(Long id) {

        return periodeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Periode introuvable avec l'identifiant :  " + id)
        );
    }

    @Override
    public Periode findByExercice(int exercice) {
        return periodeRepository.findByExercice(exercice);
    }

    @Override
    public Periode create(Periode periode) {
        return periodeRepository.save(periode);
    }

    @Override
    public Periode update(Periode periode) {
        return periodeRepository.save(periode);
    }

    @Override
    public void delete(Periode periode) {
        periodeRepository.delete(periode);
    }
}
