package com.example.week6.controller;

import com.example.week6.pojo.Wizard;
import com.example.week6.pojo.Wizards;
import com.example.week6.repository.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WizardController {
    @Autowired
    private WizardService service;
    private Wizards wizards;

    public WizardController(WizardService service) {
        this.service = service;
    }

    @RequestMapping(value ="/wizards", method = RequestMethod.GET)
    public ResponseEntity<?> getAllWizards() {
        return ResponseEntity.ok(service.getAll());
    }

    @RequestMapping(value ="/addWizard", method = RequestMethod.POST)
    public ResponseEntity<?> addWizard(@RequestBody Wizard wizard) {
        return ResponseEntity.ok(service.insertWizard(wizard));
    }

    @RequestMapping(value ="/updateWizard", method = RequestMethod.POST)
    public boolean updateWizard(@RequestBody Wizard wizard) {
        return service.updateWizard(wizard);
    }

    @RequestMapping(value ="/deleteWizard", method = RequestMethod.POST)
    public boolean deleteWizard(@RequestBody Wizard wizard) {
        return service.deleteWizard(wizard);
    }
}
