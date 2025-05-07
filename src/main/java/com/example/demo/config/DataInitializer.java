package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.entity.Hero;
import com.example.demo.entity.Superpoder;
import com.example.demo.repository.HeroRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadData(HeroRepository heroRepository) {
        return args -> {
            // Si existen datos, los borramos
            if (heroRepository.count() > 0) {
                // Borrar todos los héroes existentes
                heroRepository.deleteAll();
                // Asegurarse de que los cambios se apliquen inmediatamente
                heroRepository.flush();
            }

            // Crear héroes y sus poderes
            
            Hero drNice = new Hero("Dr. Nice");
            // drNice.setId(12);
            drNice.addPower(new Superpoder("Healing"));
            drNice.addPower(new Superpoder("Super Intelligence"));

            Hero bombasto = new Hero("Bombasto");
            // bombasto.setId(13);
            bombasto.addPower(new Superpoder("Explosion Control"));
            bombasto.addPower(new Superpoder("Fire Resistance"));

            Hero celeritas = new Hero("Celeritas");
            // celeritas.setId(14);
            celeritas.addPower(new Superpoder("Super Speed"));
            celeritas.addPower(new Superpoder("Quick Reflexes"));

            Hero magneta = new Hero("Magneta");
            // magneta.setId(15);
            magneta.addPower(new Superpoder("Magnetism Control"));
            magneta.addPower(new Superpoder("Metal Manipulation"));

            Hero rubberMan = new Hero("RubberMan");
            // rubberMan.setId(16);
            rubberMan.addPower(new Superpoder("Elasticity"));
            rubberMan.addPower(new Superpoder("Shapeshifting"));

            Hero dynama = new Hero("Dynama");
            // dynama.setId(17);
            dynama.addPower(new Superpoder("Energy Projection"));
            dynama.addPower(new Superpoder("Force Fields"));

            Hero drIQ = new Hero("Dr. IQ");
            // drIQ.setId(18);
            drIQ.addPower(new Superpoder("Telepathy"));
            drIQ.addPower(new Superpoder("Mind Control"));

            Hero magma = new Hero("Magma");
            // magma.setId(19);
            magma.addPower(new Superpoder("Heat Generation"));
            magma.addPower(new Superpoder("Lava Control"));

            Hero tornado = new Hero("Tornado");
            // tornado.setId(20);
            tornado.addPower(new Superpoder("Wind Control"));
            tornado.addPower(new Superpoder("Flight"));

            // Guardar todos los héroes
            heroRepository.save(drNice);
            heroRepository.save(bombasto);
            heroRepository.save(celeritas);
            heroRepository.save(magneta);
            heroRepository.save(rubberMan);
            heroRepository.save(dynama);
            heroRepository.save(drIQ);
            heroRepository.save(magma);
            heroRepository.save(tornado);

        };
    }
}