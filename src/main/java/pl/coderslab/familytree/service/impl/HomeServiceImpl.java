package pl.coderslab.familytree.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.familytree.model.FamilyMember;
import pl.coderslab.familytree.model.Home;
import pl.coderslab.familytree.repository.HomeRepository;
import pl.coderslab.familytree.service.HomeService;

import java.util.List;
import java.util.Optional;

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
        Optional<Home> home = homeRepository.findById(id);
        if (home.isPresent()) {
            Home finalHome = home.get();
            return finalHome;
        } else {
            return null;
        }
        //TODO: dopisac swoją metodę find by id
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
        Home homeToDelete = homeRepository.findById(id).get();
        List<FamilyMember> familyMembers = homeToDelete.getActualFamilyMembers();
        for (FamilyMember fm: familyMembers
             ) {
            fm.setActualHome(null);
        }
        homeRepository.deleteById(id);
    }
}
