package cf.k5smovie.memberConvenience.repository;

import cf.k5smovie.memberConvenience.entity.MemberConvenience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberConvenienceRepository extends JpaRepository<MemberConvenience, Long> {

    List<MemberConvenience> findByIdIn(List<Long> ids);
}
