package kg.zavod.Tare.dto.sequrity;

import kg.zavod.Tare.dto.role.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    private Integer id;
    private String username;
    private List<RoleDto> roles;
    private String accessToken;
    private String refreshToken;
}
