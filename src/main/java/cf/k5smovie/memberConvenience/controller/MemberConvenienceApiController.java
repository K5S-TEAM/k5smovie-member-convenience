package cf.k5smovie.memberConvenience.controller;

import cf.k5smovie.memberConvenience.dto.*;
import cf.k5smovie.memberConvenience.error.NoSuchMemberException;
import cf.k5smovie.memberConvenience.service.MemberConvenienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberConvenienceApiController {

    private final MemberConvenienceService memberConvenienceService;

    @PostMapping("/member")
    public ResponseEntity saveMemberConvenience(@RequestBody SignUpRequestDto dto) {
        memberConvenienceService.saveMemberConvenience(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/member")
    public ResponseEntity deleteMemberConvenience(@RequestBody MyInformationRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/member/{memberId}/nickname")
    public ResponseEntity getMemberNickname(@PathVariable("memberId") Long memberId) {
        String memberNickname = memberConvenienceService.getMemberNickname(memberId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MemberNicknameResponseDto(memberNickname));
    }

    @PostMapping("/member/nicknames")
    public ResponseEntity getMemberNicknames(@RequestBody MemberNicknamesRequestDto dto) {
        List<MemberIdNicknameDto> memberNicknames = memberConvenienceService.getMemberNicknames(dto.getIds());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MemberNicknamesResponseDto(memberNicknames));
    }

    @ExceptionHandler
    public ResponseEntity noSuchMemberExceptionHandler(NoSuchMemberException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
