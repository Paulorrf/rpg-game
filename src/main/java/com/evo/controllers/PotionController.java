package com.evo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evo.dtos.MagicDto;
import com.evo.dtos.PotionDto;
import com.evo.services.PotionService;

@RestController
@RequestMapping("/potion")
public class PotionController {

    @Autowired
    PotionService potionService;

    @PostMapping
    public PotionDto save(@RequestBody PotionDto potionDto) {
        return potionService.save(potionDto);
    }

    @GetMapping("/{id}")
    public PotionDto findById(@PathVariable Long id) {
        return potionService.findById(id);
    }
}
