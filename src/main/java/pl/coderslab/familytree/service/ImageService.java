package pl.coderslab.familytree.service;

import org.springframework.stereotype.Service;
import pl.coderslab.familytree.model.Image;

import java.util.List;

@Service
public interface ImageService {

    void saveImage(Image image);

    List<Image> loadAllImages();

    Image loadImageById(Long id);

    void deleteImageById(Long id);


}
