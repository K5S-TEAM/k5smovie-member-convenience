package cf.k5smovie.memberConvenience.dto;

import lombok.Getter;

@Getter
public class MemberNicknameResponseDto {
    String memberNickname;

    public MemberNicknameResponseDto(String memberNickname) {
        this.memberNickname = memberNickname;
    }
}
