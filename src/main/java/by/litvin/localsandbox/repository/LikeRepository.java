package by.litvin.localsandbox.repository;

import by.litvin.localsandbox.data.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<PostLike, Long> {

}
