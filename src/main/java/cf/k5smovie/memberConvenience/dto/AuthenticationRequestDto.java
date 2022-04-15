package cf.k5smovie.memberConvenience.dto;

import lombok.Getter;

@Getter
public class AuthenticationRequestDto {
    String accessToken;

    public AuthenticationRequestDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
