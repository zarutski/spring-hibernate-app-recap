package com.recap.self.springcourse.config.controllers;

import com.recap.self.springcourse.config.models.Item;
import com.recap.self.springcourse.config.models.Person;
import com.recap.self.springcourse.config.services.ItemService;
import com.recap.self.springcourse.config.services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final ItemService itemService;

    @Autowired
    public PeopleController(PeopleService peopleService, ItemService itemService) {
        this.peopleService = peopleService;
        this.itemService = itemService;
    }

    @GetMapping
    public String index(Model model) {
        // --- ------------------------------------
        // --- testing methods behaviour (no need to call)
        // --- ------------------------------------
        testingJPACustomMethodsWithDebug();
        // --- ------------------------------------

        List<Person> index = peopleService.findAll();
        model.addAttribute("people", index);
        return "people/index";
    }

    private void testingJPACustomMethodsWithDebug() {
        List<Item> items = itemService.findByItemName("Book");
        items = itemService.findByOwner(peopleService.find(1));
        List<Person> people = peopleService.findByName("Bob");
        people = peopleService.findByEmail("Jack_Vorobei@gmail.com");
        people = peopleService.findByNameStartingWith("M");
        people = peopleService.findByNameOrEmail("Bob", "Jack_Vorobei@gmail.com");

    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Person person = peopleService.find(id);
        model.addAttribute("person", person);
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Fast logging, validation errors occurred while person creation ...");
            return "people/new";
        }
        peopleService.save(person);
        return "redirect:/people"; // --- perform redirect as an option of successful creation
    }

    @GetMapping("/{id}/edit")
    public String update(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.find(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    private String update(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Fast logging, validation errors occurred while person update ...");
            return "people/edit";
        }
        peopleService.update(id, person);
        return "redirect:/people/{id}";
    }

    @DeleteMapping("/{id}")
    private String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }

}
