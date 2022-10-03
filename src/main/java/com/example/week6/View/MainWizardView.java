package com.example.week6.View;

import com.example.week6.pojo.Wizard;
import com.example.week6.pojo.Wizards;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Route(value="/mainPage.it")
public class MainWizardView extends VerticalLayout{
    private TextField fullName, dollars;
    private RadioButtonGroup<String> rd1;
    private ComboBox<String> position, school, house;
    private Button left, create, update, delete, right;
    private Wizards wizards;
    private int index = -1;

    public MainWizardView(){
        fullName = new TextField();
        fullName.setPlaceholder("Fullname");
        rd1 = new RadioButtonGroup<>();
        rd1.setLabel("Gender :");
        rd1.setItems("Male", "Female");
        position = new ComboBox<>();
        position.setPlaceholder("Position");
        position.setItems("Student", "Teacher");
        dollars = new TextField("Dollars");
        dollars.setPrefixComponent(new Span("$"));
        school = new ComboBox<>();
        school.setPlaceholder("School");
        school.setItems("Hogwarts", "Beauxbatons", "Durmstrang");
        house = new ComboBox<>();
        house.setPlaceholder("House");
        house.setItems("Gryffindor", "Ravenclaw", "Hufflepuff", "Slyther");
        HorizontalLayout h1 = new HorizontalLayout();
        left = new Button("<<");
        create = new Button("Create");
        update = new Button("Update");
        delete = new Button("Delete");
        right = new Button(">>");
        h1.add(left, create, update, delete, right);
        add(fullName, rd1, position, dollars, school, house, h1);

        this.wizards = this.getWizard();

        left.addClickListener(event -> {

            if (this.index >= 1){
                this.index -= 1;
                this.callWizard();
            }else if (this.index <= 0){
                this.index = 0;
            }

        });

        right.addClickListener(event -> {
            this.index += 1;
            if (this.index >= this.wizards.getModel().size()-1){
                this.index = this.wizards.getModel().size()-1;
            }

            this.callWizard();
        });

        create.addClickListener(event -> {
            Wizard wizard = new Wizard();
            wizard.setSex(rd1.getValue().equals("Male")?"m":"f");
            wizard.setName(fullName.getValue());
            wizard.setSchool(school.getValue());
            wizard.setHouse(house.getValue());
            wizard.setMoney(Integer.parseInt(dollars.getValue()));
            wizard.setPosition(position.getValue());

            Boolean out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addWizard")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(wizard)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            if (out) {
                new Notification("Wizard has been Created", 1000).open();
                this.wizards = this.getWizard();
                this.index += 1;
                this.callWizard();
            }
            else {
                new Notification("Wizard not has been Created", 1000).open();
            }
        });

        update.addClickListener(event -> {
            Wizard wizard = this.wizards.getModel().get(this.index);
            wizard.setSex(rd1.getValue().equals("Male")?"m":"f");
            wizard.setName(fullName.getValue());
            wizard.setSchool(school.getValue());
            wizard.setHouse(house.getValue());
            wizard.setMoney(Integer.parseInt(dollars.getValue()));
            wizard.setPosition(position.getValue());

            Boolean out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/updateWizard")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(wizard)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            if (out) {
                new Notification("Wizard has been Updated", 1000).open();
                this.wizards = this.getWizard();
            }
            else {
                new Notification("Wizard not has been Updated", 1000).open();
            }
        });

        delete.addClickListener(event -> {
            Wizard wizard = this.wizards.getModel().get(this.index);
            Boolean out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/deleteWizard")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(wizard)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            if (out) {
                new Notification("Wizard has been Removed", 1000).open();
                this.index -= 1;
                this.wizards = this.getWizard();
                this.callWizard();
            }
            else {
                new Notification("Wizard not has been Removed", 1000).open();
            }
        });



    }
    public Wizards getWizard() {
        Wizards out = WebClient.create()
                .get()
                .uri("http://localhost:8080/wizards")
                .retrieve()
                .bodyToMono(Wizards.class)
                .block();
        return out;
    }

    public void callWizard(){
        Wizard wizards =  this.wizards.getModel().get(this.index);
        fullName.setValue(wizards.getName());
        if (wizards.getSex().equals("m")){
            rd1.setValue("Male");
        }else{
            rd1.setValue("Female");
        }

        position.setValue(wizards.getPosition());
        dollars.setValue(String.valueOf(wizards.getMoney()));
        school.setValue(wizards.getSchool());
        house.setValue(wizards.getHouse());
    }


}


