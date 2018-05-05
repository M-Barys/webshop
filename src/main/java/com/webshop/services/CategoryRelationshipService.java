package com.webshop.services;

import com.webshop.model.CategoryRelationship;
import com.webshop.repositories.CategoryRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryRelationshipService {

    @Autowired
    private CategoryRelationshipRepository categoryRelationshipRepository;

    public CategoryRelationship crateCategoryRelationship(CategoryRelationship categoryRelationship){
              return categoryRelationshipRepository.save(categoryRelationship);
    }
}