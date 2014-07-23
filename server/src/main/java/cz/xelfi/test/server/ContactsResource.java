package cz.xelfi.test.server;

import cz.xelfi.test.shared.Address;
import cz.xelfi.test.shared.Contact;
import cz.xelfi.test.shared.Phone;
import cz.xelfi.test.shared.PhoneType;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/contacts/") @Singleton
public final class ContactsResource {
    private final List<Contact> contacts = new CopyOnWriteArrayList<>();
    
    public ContactsResource() {
        contacts.add(new Contact(
            "Jaroslav", "Tulach", 
            new Address("V Parku 2308", "Praha 4"), 
            new Phone("+420 2 2143 8941", PhoneType.WORK)
        ));
    }
    
    @GET @Produces(MediaType.APPLICATION_JSON)
    public List<Contact> allContacts() {
        return contacts;
    }
    
    @POST @Consumes(MediaType.APPLICATION_JSON)
    public List<Contact> addContact(Contact c) {
        validatePhones(c);
        contacts.add(c);
        return contacts;
    }
    
    @DELETE public List<Contact> removeContact(Contact c) {
        contacts.remove(c);
        return contacts;
    }
    
    @PUT @Consumes(MediaType.APPLICATION_JSON)
    public void updateContact(Contact c) {
        
    }
    
    private static void validatePhones(Contact c) {
        for (Phone phone : c.getPhones()) {
            String err = phone.getValidatePhone();
            if (err != null) {
                throw new javax.ws.rs.NotAcceptableException(err);
            }
        }
    }
}
