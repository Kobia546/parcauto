package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.etat.DmdPapierDetailResponse;
import ci.nkagou.parcauto.dtos.etat.DmdPapierResponse;
import ci.nkagou.parcauto.entities.*;

import java.util.List;

public interface EtatService {
    List <EmployeDmd> EMPLOYE_DMD_LIST(List <DetailVehiculeChauffeurA> detailVehiculeChauffeurAS);

    List<EmployeDmd> employeDmdList(List<DetailVehiculeA> detailVehiculeAS);

    List <EmployeDmd> employeDmds(List<DetailCarburantA> detailcarburantAtts);

    List <DmdPapierDetailResponse> DMD_PAPIER_DETAIL_RESPONSE(List<EmployeDmd> employeDmdList);
    DmdPapierResponse PAPIER_RESPONSE(Long id );

}
