package com.example.application.views.list;


import com.example.application.data.entity.Contact;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;


@PageTitle("Contacts | Vaadin CRM")
@Route(value = "")
public class ListView extends VerticalLayout {


    public ListView() {
        addClassName("list-view");
        setSizeFull();

        add(getToolbar(), getContent());
    }

    private Component getContent() {
        Component grid = getGrid();
        Component form = getForm();

        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);

        return content;
    }

    private Component getForm() {
        ContactForm form = new ContactForm(Collections.emptyList(), Collections.emptyList());
        form.setWidth("25em");

        return form;
    }

    private Component getToolbar() {
        TextField filter = new TextField();

        filter.setPlaceholder("Filter by name...");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);

        Button addContactButton = new Button("Add Contact");

        HorizontalLayout toolbar = new HorizontalLayout(filter, addContactButton);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private Component getGrid() {
        Grid<Contact> grid = new Grid<Contact>(Contact.class);

        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email");

        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        return grid;
    }

}
