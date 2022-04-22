package cf.k5smovie.memberConvenience.dto;

import cf.k5smovie.memberConvenience.entity.MemberConvenience;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberIdNicknameDto {
    Long id;
    String nickname;

    public MemberIdNicknameDto(MemberConvenience memberConvenience) {
        this.id = memberConvenience.getId();
        this.nickname = memberConvenience.getNickname();
    }
}
