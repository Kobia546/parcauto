package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.entities.Direction;
import ci.nkagou.parcauto.entities.Site;
import ci.nkagou.parcauto.exceptions.ResourceNotFoundException;
import ci.nkagou.parcauto.repositories.DirectionRepository;
import ci.nkagou.parcauto.services.DirectionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class DirectionServiceImpl implements DirectionService {

    private DirectionRepository directionRepository;


    @Override
    public List<Direction> all() {
        return directionRepository.findAll();
    }

    @Override
    public Direction findById(Long id) {
        return directionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Direction introuvable avec l'identifiant :  " + id)
        );
    }

    @Override
    public Direction findByLibelle(String libelle) {
        return directionRepository.findByLibelle(libelle);
    }

    @Override
    public Direction create(Direction direction) {
        return directionRepository.save(direction);
    }

    @Override
    public Direction update(Direction direction) {
        return directionRepository.save(direction);
    }

    @Override
    public void delete(Direction direction) {
        directionRepository.delete(direction);
    }
}
