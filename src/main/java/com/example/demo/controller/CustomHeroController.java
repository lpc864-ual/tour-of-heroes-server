package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.HeroDTO;
import com.example.demo.entity.Hero;
import com.example.demo.entity.Superpoder;
import com.example.demo.repository.HeroRepository;

@RestController
@RequestMapping("/heroes")
// @Order usado para sobreescribir los endpoints generados automaticamente por
// Spring Boot en caso de solaparse con los endpoints
// definidos por el controlador al asignarle mayor prioridad a TODOS los
// endpoints del controlador. Si quiesieramos solo
// "sobreescribir" un endpoint deberiamos realizar un proceso diferente un pelín
// más complejo. Sin embargo, para
// nuestro caso tan simple nos funciona y es suficiente
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomHeroController {

    @Autowired
    private HeroRepository heroRepository;

    private HeroDTO convertToDTO(Hero hero) {
        List<String> powerNames = hero.getPowers().stream()
                .map(power -> power.getName())
                .collect(Collectors.toList());

        return new HeroDTO(
                hero.getId(),
                hero.getName(),
                powerNames);
    }

    @GetMapping
    public List<HeroDTO> getAllHeroes() {
        return heroRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public HeroDTO getHeroById(@PathVariable Long id) {
        return heroRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Hero not found with id: " + id));
    }

    @PutMapping("/{id}")
    public HeroDTO updateHero(@PathVariable Long id, @RequestBody HeroDTO heroDTO) {
        return heroRepository.findById(id)
                .map(hero -> {
                    // Actualizar el nombre solo si se proporciona
                    if (heroDTO.getName() != null && !heroDTO.getName().isEmpty()) {
                        hero.setName(heroDTO.getName());
                    }

                    // Actualizar poderes solo si se proporcionan
                    if (heroDTO.getPowers() != null) {
                        // Limpiar poderes existentes y agregar los nuevos
                        hero.getPowers().clear();

                        for (String powerName : heroDTO.getPowers()) {
                            Superpoder power = new Superpoder(powerName);
                            hero.addPower(power);
                        }
                    }

                    // Guardar el héroe actualizado
                    Hero updatedHero = heroRepository.save(hero);

                    return convertToDTO(updatedHero);
                })
                .orElseThrow(() -> new RuntimeException("Hero not found with id: " + id));
    }

    @PostMapping
    public HeroDTO addHero(@RequestBody HeroDTO heroDTO) {
        // Crear nueva entidad Hero
        Hero hero = new Hero();
        hero.setName(heroDTO.getName());

        // Agregar poderes si se proporcionan
        if (heroDTO.getPowers() != null) {
            for (String powerName : heroDTO.getPowers()) {
                Superpoder power = new Superpoder(powerName);
                hero.addPower(power);
            }
        }

        // Guardar el nuevo héroe
        Hero savedHero = heroRepository.save(hero);

        return convertToDTO(savedHero);
    }

    @DeleteMapping("/{id}")
    public void deleteHero(@PathVariable Long id) {
        heroRepository.findById(id)
                .ifPresent(hero -> {
                    heroRepository.delete(hero);
                });
    }
}