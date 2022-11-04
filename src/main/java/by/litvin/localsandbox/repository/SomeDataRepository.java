package by.litvin.localsandbox.repository;

import by.litvin.localsandbox.data.SomeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SomeDataRepository extends JpaRepository<SomeData, Long> {

}
