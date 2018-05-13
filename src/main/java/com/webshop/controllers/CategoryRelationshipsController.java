package com.webshop.controllers;

import com.webshop.controllers.params.RelationParams;
import com.webshop.model.CategoryRelationship;
import com.webshop.model.tree.CategoryTreeNode;
import com.webshop.services.CategoryRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/categories")
public class CategoryRelationshipsController {

    @Autowired
    private CategoryRelationshipService categoryRelationshipService;


    @RequestMapping(value = "/{id}/relationships/categories",method = RequestMethod.POST)
    public @ResponseBody CategoryTreeNode createRelation(@PathVariable Long id, @RequestBody RelationParams relation){
        System.out.println(categoryRelationshipService.createRelation(id, relation));
        return categoryRelationshipService.createRelation(id, relation);
    }

//    @RequestMapping(value = "/tree", method = RequestMethod.GET)
//    public CategoryTreeNode getTree(){
//        return categoryRelationshipService.getTree();
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {EmptyResultDataAccessException.class, EntityNotFoundException.class, NoSuchElementException.class})
    public void handleNotFound() {
    }

}


