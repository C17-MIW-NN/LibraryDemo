package nl.miwnn.ch17.vincent.librarydemo.service;

import nl.miwnn.ch17.vincent.librarydemo.model.Image;
import nl.miwnn.ch17.vincent.librarydemo.repositories.ImageRepository;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void saveImage(MultipartFile file) throws IOException {
        if (imageRepository.existsByFileName(file.getOriginalFilename())) {
            throw new IllegalIdentifierException(file.getOriginalFilename() + " already exists");
        }

        MediaType contentType = MediaType.IMAGE_JPEG;
        if (file.getContentType() != null) {
            // TODO: Filter to only allow image types
            contentType = MediaType.parseMediaType(file.getContentType());
        }

        Image image = new Image();
        image.setFileName(file.getOriginalFilename());
        image.setContentType(contentType);
        image.setFileName(image.getFileName());
        image.setData(file.getBytes());

        System.out.println(image);

        imageRepository.save(image);
    }

    public Image getImage(String fileName) {
        return imageRepository.findByFileName(fileName)
                .orElseThrow(() -> new NoSuchElementException(fileName));
    }
}
