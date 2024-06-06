package com.forniturear.forniturear.controllers;

import com.forniturear.forniturear.models.Furniture;
import com.forniturear.forniturear.repositories.IFurnitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/furniture")
public class FurnitureController {
    @Autowired
    private IFurnitureRepository repository;

    @GetMapping
    public List<Furniture> getAllFurniture() {
        return repository.findAll();
    }

    @PostMapping
    public Furniture addFurniture(@RequestBody Furniture furniture) {
        return repository.save(furniture);
    }

    @PutMapping("/{id}")
    public Furniture updateFurniture(@PathVariable Long id, @RequestBody Furniture furnitureDetails) {
        Furniture furniture = repository.findById(id).orElseThrow(RuntimeException::new);
        furniture.setName(furnitureDetails.getName());
        furniture.setDescription(furnitureDetails.getDescription());
        furniture.setPrice(furnitureDetails.getPrice());
        furniture.setPicture(furnitureDetails.getPicture());
        return repository.save(furniture);
    }

    @DeleteMapping("/{id}")
    public void deleteFurniture(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

