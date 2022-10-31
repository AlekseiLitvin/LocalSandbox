package by.litvin.localsandbox.data.repository;

import by.litvin.localsandbox.data.SomeData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SomeDataRepository extends CrudRepository<SomeData, Long> {

}
