package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.entities.Visitetechnique;
import ci.nkagou.parcauto.repositories.VisitetechniqueRepository;
import ci.nkagou.parcauto.services.VisitetechniqueService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class VisitetechniqueServiceImpl implements VisitetechniqueService {

    private VisitetechniqueRepository visitetechniqueRepository;

    @Override
    public List<Visitetechnique> all() {
        return visitetechniqueRepository.findAll();
    }

    @Override
    public Visitetechnique getById(Long id) {
        return visitetechniqueRepository.getById(id);
    }

    @Override
    public Visitetechnique findByVignette(String numVignette) {
        return visitetechniqueRepository.findByNumVignette(numVignette);
    }

    @Override
    public Visitetechnique create(Visitetechnique visitetechnique) {
        return visitetechniqueRepository.save(visitetechnique);
    }

    @Override
    public Visitetechnique update(Visitetechnique visitetechnique) {
        return visitetechniqueRepository.save(visitetechnique);
    }

    @Override
    public void delete(Visitetechnique visitetechnique) {
        visitetechniqueRepository.delete(visitetechnique);
    }

    @Override
    public List<Visitetechnique> findVisitetechniquesByDateVisitTechn(LocalDate date) {
        return visitetechniqueRepository.findVisitetechniquesByDateVisitTechn(date);
    }

    @Override
    public List<Visitetechnique> findVisitetechniquesByDateProchaineVisitTechn(LocalDate date) {
        return visitetechniqueRepository.findVisitetechniquesByDateProchaineVisitTechn(date);
    }

    @Override
    public List<Visitetechnique> findVisitetechniquesByDateProchaineVisitTechnBefore(LocalDate date) {
        return visitetechniqueRepository.findVisitetechniquesByDateProchaineVisitTechnBefore(date);
    }
}
