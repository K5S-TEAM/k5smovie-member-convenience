package cf.k5smovie.memberConvenience.controller;


import cf.k5smovie.memberConvenience.dto.MyInformationResponseDto;
import cf.k5smovie.memberConvenience.entity.MemberConvenience;
import cf.k5smovie.memberConvenience.error.InvalidAuthenticationException;
import cf.k5smovie.memberConvenience.error.NoSuchMemberException;
import cf.k5smovie.memberConvenience.error.S3ConnectException;
import cf.k5smovie.memberConvenience.service.MemberConvenienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class MemberConvenienceController {

    private final MemberConvenienceService memberConvenienceService;

    @Value("${msa.auth}")
    String authServerUrl;

    @GetMapping("/member/my-page")
    public String myPage(@CookieValue(value = "accessToken", required = false) String accessToken, Model model) {
        if (accessToken == null) {
            throw new InvalidAuthenticationException("인증 정보가 존재하지 않습니다.");
        }

        MemberConvenience memberConvenience = memberConvenienceService.getMyInformation(accessToken);

        model.addAttribute("myInformationResponseDto", new MyInformationResponseDto(memberConvenience));
        return "memberConvenience/my-page";
    }

    @PutMapping("/member/my-page")
    public ResponseEntity myPage(@CookieValue(value = "accessToken", required = false) String accessToken
            , @RequestParam("nickname") String nickname, @RequestParam("image") MultipartFile image) {
        if (accessToken == null) {
            throw new InvalidAuthenticationException("인증 정보가 존재하지 않습니다.");
        }

        memberConvenienceService.updateMyInformation(accessToken, nickname, image);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ExceptionHandler
    public String invalidAuthenticationExceptionHandler(InvalidAuthenticationException e) {
        return "redirect:" + authServerUrl + "/auth/login";
    }

    @ExceptionHandler
    public ResponseEntity noSuchMemberExceptionHandler(NoSuchMemberException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler
    public ResponseEntity s3ConnectExceptionHandler(S3ConnectException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }
}
