package com.example.animals_app.controller;

import com.example.animals_app.Entity.AnimalEntity;
import com.example.animals_app.Service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalService<AnimalEntity> animalService;

    @Autowired
    public AnimalController(AnimalService<AnimalEntity> animalService) {
        this.animalService = animalService;
    }

    @GetMapping(value ="/index")
    public String getIndexView(Model model){
        model.addAttribute("animals", animalService.findAll());
        return "index";
    }

    @GetMapping(value ="/addAnimal")
    public String getAddView(Model model){
        model.addAttribute("animal", new AnimalEntity());
        return "add";
    }

    @PostMapping(value = "/addAnimal")
    public String addAnimal(AnimalEntity animal, RedirectAttributes redirectAttributes){
        animalService.registration(animal);
        redirectAttributes.addFlashAttribute("successMessage", "animal");
        return "redirect:index";
    }

    @GetMapping(value = "/edit/{id}")
    public String getEditView(@PathVariable("id") Long id, Model model) {
        AnimalEntity animal=animalService.findAnimal(id);
        if (animal != null){
            model.addAttribute("animal", animal);
        }
        return "edit";
    }


    @PostMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") Long id, AnimalEntity animal, RedirectAttributes redirectAttributes) {
        animalService.updateAnimal(id, animal);
        redirectAttributes.addFlashAttribute("successMessage", "animal");
        return "redirect:/animals/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteAnimal(@PathVariable Long id, Model model) {
        animalService.deleteAnimal(id);
        return "redirect:/animals/index";
    }
}
