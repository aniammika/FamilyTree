package pl.coderslab.familytree.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.familytree.model.Home;
import pl.coderslab.familytree.repository.HomeRepository;
import pl.coderslab.familytree.service.HomeService;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private HomeRepository homeRepository;

    @Override
    public void saveHome(Home home) {
        homeRepository.save(home);
    }

    @Override
    public List<Home> loadAllHomes() {
        List<Home> homes = homeRepository.findAll();
        return homes;
    }

    @Override
    public Home loadHomeById(Long id) {
        //Home home = homeRepository.findById(id);
        return null;
    }

    @Override
    public List<Home> loadHomesByTown(String town) {
        List<Home> homes = homeRepository.findAllByTown(town);
        return homes;
    }

    @Override
    public List<Home> loadHomesByName(String name) {
        List<Home> homes = homeRepository.findAllByName(name);
        return homes;
    }

    @Override
    public void deleteHomeById(Long id) {
        homeRepository.deleteById(id);
    }
}
