package com.example.week6.repository;

import com.example.week6.pojo.Wizard;
import com.example.week6.pojo.Wizards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WizardService {
    @Autowired
    private WizardRepository repository;

    public WizardService(WizardRepository repository) {
        this.repository = repository;
    }
    public Wizards getAll(){
        return new Wizards((ArrayList<Wizard>) repository.findAll());
    }

    public Wizard createWizard(Wizard wizard){
        return repository.save(wizard);
    }

    public int countBook() {
        return (int)repository.count();
    }

    public boolean updateWizard(Wizard wizard){
        try {
            repository.save(wizard);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public boolean insertWizard(Wizard wizard){
        try {
            repository.insert(wizard);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public boolean deleteWizard(Wizard wizard){
        try{ repository.delete(wizard);
            return true;}
        catch (Exception e){return false;}
    }
}
