package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.entities.Motif;
import ci.nkagou.parcauto.repositories.MotifRepository;
import ci.nkagou.parcauto.services.MotifService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class MotifServiceImpl implements MotifService {

    private MotifRepository motifRepository;


    @Override
    public List<Motif> all() {
        return motifRepository.findAll();
    }

    @Override
    public Motif findByNomMotif(String nomMotif) {
        return motifRepository.findByNomMotif(nomMotif);
    }

    @Override
    public Motif create(Motif motif) {



        return motifRepository.save(motif);
    }

    @Override
    public Motif findById(Long id) {
        return motifRepository.getById(id);
    }
}
