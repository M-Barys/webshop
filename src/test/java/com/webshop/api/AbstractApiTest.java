package com.webshop.api;

import com.webshop.model.StoreLanguage;
import com.webshop.model.instance.PictureRef;
import com.webshop.model.instance.Product;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import static com.webshop.api.ApiEndpointSpecification.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public abstract class AbstractApiTest {


    protected PictureRef createNewPicture(URL resource) throws IOException {
        File file = new File(resource.getFile());
        String mimetype = Files.probeContentType(file.toPath());

        return given()
                .multiPart("file", file, mimetype)
                .when()
                .post(pictureEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(PictureRef.class);
    }

    protected PictureRef loadPictureByID(Long pictureID) {
        return when()
                .get(pictureByIDEndpoint, pictureID)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(PictureRef.class);
    }


    protected Product createNewProduct(Product newProduct) {
        return given()
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(productEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);
    }

    protected Product loadProductByID(Long id) {
        return loadProductByID(id, StoreLanguage.PL);
    }

    protected Product loadProductByID(Long id, StoreLanguage language) {
        return given()
                .contentType(ContentType.JSON)
                .header(StoreLanguage.languageHeader, language.name())
                .accept(ContentType.JSON)
                .when()
                .get(productByIDEndpoint, id)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);
    }

}
