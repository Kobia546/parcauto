package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.entities.Destination;
import ci.nkagou.parcauto.repositories.DestinationRepository;
import ci.nkagou.parcauto.services.DestinationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class DestinationServiceImpl implements DestinationService {

    private DestinationRepository destinationRepository;


    @Override
    public List<Destination> all() {
        return destinationRepository.findAll();
    }

    @Override
    public Destination findByNomDestination(String nomDestination) {
        return destinationRepository.findByNomDestination(nomDestination);
    }
}
