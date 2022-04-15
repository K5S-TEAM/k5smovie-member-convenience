package cf.k5smovie.memberConvenience.dto;

import cf.k5smovie.memberConvenience.entity.MemberConvenience;
import lombok.Getter;

@Getter
public class MyInformationResponseDto {
    String nickname;
    String profileImageUrl;

    public MyInformationResponseDto(MemberConvenience memberConvenience) {
        this.nickname = memberConvenience.getNickname();
        this.profileImageUrl = memberConvenience.getProfileImageUrl();
    }
}
