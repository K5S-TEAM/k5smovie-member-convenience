package cf.k5smovie.memberConvenience.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberConvenience {

    @Id
    private Long id;

    @Column(length = 128)
    private String nickname;

    @Column(length = 512)
    private String profileImageUrl;

    public MemberConvenience(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public MemberConvenience(Long id, String nickname, String profileImageUrl) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
