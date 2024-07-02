package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.dashboard.DashbordUserResponseDto;
import ci.nkagou.parcauto.dtos.dmd.EmployeRapportDto;
import ci.nkagou.parcauto.entities.Dmd;

import java.security.Principal;
import java.util.List;

public interface DashboardService {
    List<DashbordUserResponseDto> dashbordUserResponseDto(Principal principal, EmployeRapportDto EmployeRapportDto);

    List<DashbordUserResponseDto> dashbordUserResponseDto(Principal principal, EmployeRapportDto rapportDto, List<Dmd> dmds);
}
