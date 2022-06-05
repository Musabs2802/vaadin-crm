package com.example.application.views.list;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Status;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class ContactForm extends FormLayout {

    public ContactForm(List<Company> companyList, List<Status> statusList) {
        TextField firstName = new TextField("First name");
        TextField lastName = new TextField("Last name");
        TextField email = new TextField("Email");

        ComboBox<Status> status = new ComboBox<>("Status");
        ComboBox<Company> company = new ComboBox<>("Status");

        Button save = new Button("Save");
        Button delete = new Button("Delete");
        Button cancel = new Button("Cancel");

        addClassName("contact-form");

        company.setItems(companyList);
        company.setItemLabelGenerator((ItemLabelGenerator<Company>) Company::getName);

        status.setItems(statusList);
        status.setItemLabelGenerator((ItemLabelGenerator<Status>) Status::getName);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        add(firstName, lastName, email, status, company, new HorizontalLayout(save, delete, cancel));
    }
}
