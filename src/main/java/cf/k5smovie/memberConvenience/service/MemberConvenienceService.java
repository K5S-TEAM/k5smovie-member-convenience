package cf.k5smovie.memberConvenience.service;

import cf.k5smovie.memberConvenience.dto.AuthenticationRequestDto;
import cf.k5smovie.memberConvenience.dto.AuthenticationResponseDto;
import cf.k5smovie.memberConvenience.dto.SignUpRequestDto;
import cf.k5smovie.memberConvenience.entity.MemberConvenience;
import cf.k5smovie.memberConvenience.error.InvalidAuthenticationException;
import cf.k5smovie.memberConvenience.error.NoSuchMemberException;
import cf.k5smovie.memberConvenience.repository.MemberConvenienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MemberConvenienceService {

    private final MemberConvenienceRepository memberConvenienceRepository;
    private final S3Service s3Service;
    private final WebClient webClient;

    @Value("${msa.auth}")
    String authServerUrl;

    @Transactional
    public void saveNickname(SignUpRequestDto dto) {
        MemberConvenience memberConvenience = new MemberConvenience(dto.getId(), dto.getNickname());
        memberConvenienceRepository.save(memberConvenience);
    }

    @Transactional
    public MemberConvenience getMyInformation(String accessToken) {
        Long id = requestAuthentication(accessToken);

        return memberConvenienceRepository.findById(id)
                .orElseThrow(() -> new NoSuchMemberException("사용자 정보가 존재하지 않습니다."));
    }

    @Transactional
    public void updateMyInformation(String accessToken, String nickname, MultipartFile image) {
        MemberConvenience myInformation = getMyInformation(accessToken);

        //이름 변경
        if (!nickname.equals(myInformation.getNickname())) {
            myInformation.changeNickname(nickname);
        }

        //이미지 변경
        if (image != null && !image.isEmpty()) {
            try {
                s3Service.delete(myInformation.getProfileImageUrl());
                String newImageUrl = s3Service.upload(image, S3Service.s3Path+"/" + myInformation.getId());
                myInformation.changeProfileImageUrl(newImageUrl);
            } catch (IOException e) {
                System.out.println("이미지 저장에 실패했습니다.");
                throw new RuntimeException("이미지 저장에 실패했습니다.");
            }
        }
    }

    @Transactional
    public void deleteMemberConvenience(Long id) {
        MemberConvenience memberConvenience = memberConvenienceRepository.findById(id)
                .orElseThrow(() -> new NoSuchMemberException("사용자 정보가 존재하지 않습니다."));

        s3Service.delete(memberConvenience.getProfileImageUrl());
        memberConvenienceRepository.deleteById(id);
    }

    @Transactional
    public Long requestAuthentication(String accessToken) {
        AuthenticationRequestDto dto = new AuthenticationRequestDto(accessToken);

        AuthenticationResponseDto result = webClient.post()
                .uri("/auth/access-token-valid")
                .body(Mono.just(dto), AuthenticationRequestDto.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, error -> Mono.error(new InvalidAuthenticationException("인증 정보가 존재하지 않습니다.")))
                .bodyToMono(AuthenticationResponseDto.class)
                .block();

        if (result.getId() == null) {
            throw new InvalidAuthenticationException("인증 정보가 존재하지 않습니다.");
        }

        return result.getId();
    }
}
