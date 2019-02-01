package pl.coderslab.familytree.service;

import pl.coderslab.familytree.model.Home;

import java.util.List;

public interface HomeService {

    void saveHome(Home home);

    List<Home> loadAllHomes();

    Home loadHomeById(Long id);

    List<Home> loadHomesByTown (String town);

    List<Home> loadHomesByName(String name);

    void deleteHomeById(Long id);
}
