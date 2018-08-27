package com.webshop.controllers;

import com.webshop.model.instance.PictureRef;
import com.webshop.services.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/pictures")

public class PictureController extends AbstractControllerExceptionHandler {

    @Autowired
    private PictureService pictureService;

    @RequestMapping(method = RequestMethod.GET)
    public List<PictureRef> getAllPicture() {
        return pictureService.getAllPicture();
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    PictureRef addPicture(@RequestParam("file") MultipartFile file) throws IOException {
        return pictureService.addPicture(file);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PictureRef getPicture(@PathVariable Long id) {
        return pictureService.getPicture(id);
    }


    @GetMapping(
            value = "/{id}/image.jpg",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    @ResponseBody
    public ByteArrayResource serveFile(@PathVariable("id") Long id) {
        //TODO Change to Resource. references:
        // https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/http/converter/ResourceHttpMessageConverter.html
        // https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/core/io/Resource.html

        return new ByteArrayResource(pictureService.getPictureContent(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePicture(@PathVariable Long id) {
        pictureService.deletePicture(id);
    }

}

