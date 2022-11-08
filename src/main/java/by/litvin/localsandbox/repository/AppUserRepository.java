package by.litvin.localsandbox.repository;

import by.litvin.localsandbox.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    List<AppUser> findAllByLastName(String lastName);
}
