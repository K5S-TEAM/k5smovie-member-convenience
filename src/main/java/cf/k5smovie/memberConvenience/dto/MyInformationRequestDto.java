package cf.k5smovie.memberConvenience.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class MyInformationRequestDto {
    String nickname;
    MultipartFile image;
}
