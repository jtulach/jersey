package cz.xelfi.test.shared;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class ContactsTest {
    
    public ContactsTest() {
    }

    @Test public void noAlphabetInPhoneNumber() {
        Phone p = new Phone("+4AFDc", Contacts.PhoneType.HOME);
        String err = p.getValidatePhone();
        assertNotNull(err, "Need error message, number is not valid");
    }

    @Test public void hasToStartWithPlus() {
        Phone p = new Phone("464254", Contacts.PhoneType.WORK);
        String err = p.getValidatePhone();
        assertNotNull(err, "Need error message, number is not international");
    }

    @Test public void mayContainSpaces() {
        Phone p = new Phone("+1 464 254 542 555", Contacts.PhoneType.MOBILE);
        String err = p.getValidatePhone();
        assertNull(err, "Spaces are OK");
    }
    
}
