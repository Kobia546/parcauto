package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.dtos.dashboard.DashbordUserResponseDto;
import ci.nkagou.parcauto.dtos.dmd.EmployeRapportDto;
import ci.nkagou.parcauto.entities.Dmd;
import ci.nkagou.parcauto.entities.EmployeDmd;
import ci.nkagou.parcauto.enums.RapportStatut;
import ci.nkagou.parcauto.services.DashboardService;
import ci.nkagou.parcauto.services.EmployeDmdService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DashboardServiceImpl  {
//    private final EmployeDmdService employeDmdService;
//
//    public DashboardServiceImpl( EmployeDmdService employeDmdService) {
//        this.employeDmdService = employeDmdService;
//
//    }
//
//    @Override
//    public List<DashbordUserResponseDto> dashbordUserResponseDto(Principal principal, EmployeRapportDto rapportDto, List<Dmd> dmds){
//     List<DashbordUserResponseDto> userResponseDtoList=new ArrayList<>();
//     List<RapportStatut> statuts = Arrays.asList(RapportStatut.values());
//        List<EmployeDmd> employeDmds = employeDmdService.getListEmployeDmdByDmd(dmds);
//
//
////     for(EmployeRapportDto employeRapportDto:rapportDto){
////     DashbordUserResponseDto dashbordUserResponseDto=new DashbordUserResponseDto();
////     dashbordUserResponseDto.setNbreDmdTotal(employeDmds.size());
////     if(employeDmdService.s)
//
//
//     }
//
//
//        return  userResponseDtoList;
//    };



}
