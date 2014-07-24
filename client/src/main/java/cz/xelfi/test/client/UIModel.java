package cz.xelfi.test.client;

import cz.xelfi.test.shared.Contact;
import cz.xelfi.test.shared.Phone;
import cz.xelfi.test.shared.PhoneType;
import java.util.List;
import net.java.html.json.ComputedProperty;
import net.java.html.json.Function;
import net.java.html.json.Model;
import net.java.html.json.ModelOperation;
import net.java.html.json.OnReceive;
import net.java.html.json.Property;

/** Model annotation generates class Data with 
 * one message property, boolean property and read only words property
 */
@Model(className = "UI", properties = {
    @Property(name = "url", type = String.class),
    @Property(name = "message", type = String.class),
    @Property(name = "contacts", type = Contact.class, array = true),
    @Property(name = "selected", type = Contact.class),
    @Property(name = "edited", type = Contact.class)
})
final class UIModel {
    @OnReceive(url = "{url}", onError = "cannotConnect") 
    static void loadContacts(UI ui, List<Contact> arr) {
        ui.getContacts().clear();
        ui.getContacts().addAll(arr);
        ui.setMessage("Loaded " + arr.size() + " contacts.");
    }

    @OnReceive(method = "DELETE", url = "{url}/{id}", onError = "cannotConnect") 
    static void deleteContact(UI ui, List<Contact> remainingOnes, Contact original) {
        ui.getContacts().clear();
        ui.getContacts().addAll(remainingOnes);
        ui.setMessage("Deleted " + original.getLastName() + ". " + remainingOnes.size() + " contacts remains.");
    }
    
    @ModelOperation @Function static void connect(UI data) {
        data.loadContacts(data.getUrl());
    }
    
    static void cannotConnect(UI data, Exception ex) {
        data.setMessage("Cannot connect " + ex.getMessage());
    }
    
    @Function static void addNew(UI ui) {
        ui.setSelected(null);
        ui.setEdited(new Contact());
    }
    
    @Function static void edit(UI ui, Contact data) {
        ui.setSelected(data);
        ui.setEdited(data.clone());
    }

    @Function static void delete(UI ui, Contact data) {
        ui.deleteContact(ui.getUrl(), data.getId(), data);
    }
    
    @Function static void addPhoneToEdited(UI ui) {
        ui.getEdited().getPhones().add(new Phone("", PhoneType.HOME));
    }
}
