package cf.k5smovie.memberConvenience.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public static String s3Path = "templates/memberConvenience";

    public String upload(MultipartFile file, String dirPath) throws IOException {
        String fileName = createFileName(file.getOriginalFilename());
        String uploadPath = dirPath + "/" + fileName;

        s3Client.putObject(
                new PutObjectRequest(bucket, uploadPath, file.getInputStream(), null)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        return s3Client.getUrl(bucket, uploadPath).toString();
    }

    public void delete(String imagePath) {
        if (imagePath != null) {
            String keyPath = imagePath.substring(imagePath.indexOf(s3Path));
            s3Client.deleteObject(new DeleteObjectRequest(bucket, keyPath));
        }
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getExtension(fileName));
    }

    private String getExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new RuntimeException("파일 형식이 올바르지 않습니다.");
        }
    }
}
