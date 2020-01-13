package net.frey.sfgpetclinic.controller;

import net.frey.sfgpetclinic.model.Pet;
import net.frey.sfgpetclinic.model.Visit;
import net.frey.sfgpetclinic.service.PetService;
import net.frey.sfgpetclinic.service.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;

@Controller
public class VisitController {
    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void setDataBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");

        dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDate.parse(text));
            }
        });
    }

    /**
     * Called before each and every @RequestMapping annotated method.
     * 2 goals:
     * - Make sure we always have fresh data
     * - Since we do not use the session scope, make sure that Pet object always has an id
     * (Even though id is not part of the form fields)
     *
     * @param id
     * @return Pet
     */
    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable Long id, Model model) {
        Pet pet = petService.findById(id);
        model.addAttribute("pet", pet);
        Visit visit = new Visit();
        pet.addVisit(visit);

        return visit;
    }

    // Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
    @GetMapping("/owners/*/pets/{id}/visits/new")
    public String initNewVisitForm(@PathVariable Long id, Model model) {
        return "visits/createOrUpdateForm";
    }

    // Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
    @PostMapping("/owners/{ownerId}/pets/{id}/visits/new")
    public String processNewVisitForm(@PathVariable Long ownerId, @PathVariable Long id, @Valid Visit visit, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "visits/createOrUpdateForm";
        } else {
            visit.setPet((Pet) model.asMap().get("pet"));
            visitService.save(visit);

            return "redirect:/owners/" + ownerId;
        }
    }
}
