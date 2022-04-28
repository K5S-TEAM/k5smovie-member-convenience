package cf.k5smovie.memberConvenience.controller;


import cf.k5smovie.memberConvenience.dto.AuthenticationResponseDto;
import cf.k5smovie.memberConvenience.dto.MyInformationResponseDto;
import cf.k5smovie.memberConvenience.entity.MemberConvenience;
import cf.k5smovie.memberConvenience.error.ApiNotRespondException;
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

        //토큰 인증 확인
        AuthenticationResponseDto authenticationResponseDto = memberConvenienceService.requestAuthentication(accessToken);

        //정보 get
        MemberConvenience memberConvenience = memberConvenienceService.getMyInformation(authenticationResponseDto.getId());

        model.addAttribute("memberName", authenticationResponseDto.getName());
        model.addAttribute("myInformationResponseDto", new MyInformationResponseDto(memberConvenience));
        return "memberConvenience/my-page";
    }

    @PutMapping("/member/my-page")
    public ResponseEntity myPage(@CookieValue(value = "accessToken", required = false) String accessToken
            , @RequestParam("nickname") String nickname, @RequestParam("image") MultipartFile image) {
        if (accessToken == null) {
            throw new InvalidAuthenticationException("인증 정보가 존재하지 않습니다.");
        }

        //토큰 인증 확인
        AuthenticationResponseDto authenticationResponseDto = memberConvenienceService.requestAuthentication(accessToken);

        //정보 update
        memberConvenienceService.updateMyInformation(authenticationResponseDto.getId(), nickname, image);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/member/request-logout")
    public ResponseEntity requestLogout(@CookieValue(value = "accessToken", required = false) String accessToken) {
        if (accessToken == null) {
            throw new InvalidAuthenticationException("인증 정보가 존재하지 않습니다.");
        }

        memberConvenienceService.requestLogout(accessToken);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ExceptionHandler
    public String invalidAuthenticationExceptionHandler(InvalidAuthenticationException e) {
        return "redirect:https://k5smovie.ga/auth/login";
    }

    @ExceptionHandler
    public ResponseEntity noSuchMemberExceptionHandler(NoSuchMemberException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler
    public ResponseEntity s3ConnectExceptionHandler(S3ConnectException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }

    @ExceptionHandler
    public ResponseEntity apiNotRespondExceptionHandler(ApiNotRespondException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
