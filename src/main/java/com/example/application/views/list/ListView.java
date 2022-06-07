package com.example.application.views.list;


import com.example.application.data.entity.Contact;
import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Contacts | Vaadin CRM")
@Route(value = "")
public class ListView extends VerticalLayout {

    private Grid<Contact> grid = new Grid<Contact>(Contact.class);
    private TextField filter = new TextField();
    private ContactForm form;

    private CrmService service;

    public ListView(CrmService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();

        add(getToolbar(), getContent());

        updateList();

        closeForm();
    }

    private void closeForm() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void openForm(Contact contact) {
        form.setContact(contact);
        form.setVisible(true);
        addClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.allContacts(filter.getValue()));
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
        form = new ContactForm(service.allCompanies(), service.allStatus());
        form.setWidth("25em");

        form.addListener(ContactForm.SaveEvent.class, this::saveContact);
        form.addListener(ContactForm.DeleteEvent.class, this::deleteContact);
        form.addListener(ContactForm.CloseEvent.class, e-> closeForm());

        return form;
    }

    private void deleteContact(ContactForm.DeleteEvent event) {
        service.deleteContact(event.getContact());
        updateList();
        closeForm();
    }

    private void saveContact(ContactForm.SaveEvent event) {
        service.saveContact(event.getContact());
        updateList();
        closeForm();
    }

    private Component getToolbar() {
        filter.setPlaceholder("Filter by name...");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(v -> updateList());

        Button addContactButton = new Button("Add Contact");
        addContactButton.addClickListener(c -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filter, addContactButton);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        openForm(new Contact());
    }

    private Component getGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email");

        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> {
            if(e.getValue() == null)
                closeForm();
            else
                openForm(e.getValue());
        });

        return grid;
    }

}
