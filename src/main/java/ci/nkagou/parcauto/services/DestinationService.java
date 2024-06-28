package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.entities.Destination;

import java.util.List;

public interface DestinationService {

    List<Destination> all();
    Destination findByNomDestination(String nomDestination);
}
