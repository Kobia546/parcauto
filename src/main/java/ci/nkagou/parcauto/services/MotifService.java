package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.entities.Motif;

import java.util.List;

public interface MotifService {

    List<Motif> all();
    Motif findByNomMotif(String nomMotif);
    Motif create(Motif motif);
    Motif findById(Long id);

}
