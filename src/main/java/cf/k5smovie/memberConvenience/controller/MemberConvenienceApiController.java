package cf.k5smovie.memberConvenience.controller;

import cf.k5smovie.memberConvenience.dto.MyInformationRequestDto;
import cf.k5smovie.memberConvenience.dto.SignUpRequestDto;
import cf.k5smovie.memberConvenience.service.MemberConvenienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberConvenienceApiController {

    private final MemberConvenienceService memberConvenienceService;

    @PostMapping("/member/nickname")
    public ResponseEntity saveNickname(@RequestBody SignUpRequestDto dto) {
        memberConvenienceService.saveNickname(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/member")
    public ResponseEntity deleteMemberConvenience(@RequestBody MyInformationRequestDto dto) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
