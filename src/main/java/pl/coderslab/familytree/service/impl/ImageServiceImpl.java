package pl.coderslab.familytree.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.familytree.model.Image;
import pl.coderslab.familytree.repository.ImageRepository;
import pl.coderslab.familytree.service.ImageService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public void saveImage(Image image) {
        imageRepository.save(image);
    }

    @Override
    public List<Image> loadAllImages() {
        List<Image> images = imageRepository.findAll();
        return images;
    }

    @Override
    public Image loadImageById(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isPresent()){
            Image im = image.get();
            return im;
        } else {
            return null;
        }
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.deleteById(id);
    }
}
