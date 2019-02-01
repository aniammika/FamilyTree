package pl.coderslab.familytree.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.familytree.model.Home;

import java.util.List;

public interface HomeRepository extends JpaRepository<Home, Long> {

    List<Home> findAllByTown(String town);

    List<Home> findAllByName(String name);
}
