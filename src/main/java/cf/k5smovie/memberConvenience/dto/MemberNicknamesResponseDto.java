package cf.k5smovie.memberConvenience.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MemberNicknamesResponseDto {
    List<MemberIdNicknameDto> memberNicknames;

    public MemberNicknamesResponseDto(List<MemberIdNicknameDto> memberNicknames) {
        this.memberNicknames = memberNicknames;
    }
}
