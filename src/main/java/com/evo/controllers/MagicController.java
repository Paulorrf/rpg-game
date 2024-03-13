package com.evo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evo.dtos.MagicDto;
import com.evo.services.MagicService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/magic")
public class MagicController {

    @Autowired
    private MagicService magicService;

    public MagicController(MagicService magicService) {
        this.magicService = magicService;
    }

    @PostMapping
    public MagicDto save(@RequestBody MagicDto magicDto) {
        return magicService.save(magicDto);
    }

    @GetMapping
    public List<MagicDto> findAll() {
        return magicService.findAll();
    }

    @GetMapping("/{id}")
    public MagicDto findById(@PathVariable Long id) {
        return magicService.findById(id);
    }

    @PutMapping("path/{id}")
    public MagicDto update(@PathVariable Long id, @RequestBody MagicDto magicDto) {
        return magicService.update(id, magicDto);
    }

}
