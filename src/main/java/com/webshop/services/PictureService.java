package com.webshop.services;

import com.google.common.collect.Lists;
import com.webshop.model.PictureFileType;
import com.webshop.model.entity.PictureEntity;
import com.webshop.model.instance.PictureRef;
import com.webshop.model.mapping.PictureMapping;
import com.webshop.repositories.PictureRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Service
public class PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private PictureMapping pictureMapping;

    public List<PictureRef> getAllPicture() {
        return pictureMapping.mapToRef(
                Lists.newArrayList(pictureRepository.findAll())
        );
    }

    public PictureRef getPicture(Long id) {
        return pictureMapping.loadFromEntity(
                pictureRepository.findById(id).get()
        );
    }

    public InputStream getPictureContent(Long id) {
        PictureEntity pictureEntity = pictureRepository.findById(id).get();
        try {
            return pictureEntity.getImageData().getBinaryStream();
        } catch (SQLException e) {
            throw new RuntimeException("error on sql stream",e);
        }
    }

    public PictureRef addPicture(MultipartFile file) throws IOException {
        try {
            PictureEntity pictureEntity = PictureEntity.builder()
                    .imageData(new SerialBlob(file.getBytes()))
                    .pictureName(FilenameUtils.getBaseName(file.getOriginalFilename()))
                    .pictureType(PictureFileType.getFromMimeType(file.getContentType()))
                    .build();
            return pictureMapping.loadFromEntity(
                    pictureRepository.save(pictureEntity)
            );
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO make a real exception and error handler to 500
            throw new IllegalStateException("TODO");
        }
    }

    public void deletePicture(Long id) {
        pictureRepository.deleteById(id);
    }

}
