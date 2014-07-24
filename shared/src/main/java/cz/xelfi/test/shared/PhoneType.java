package cz.xelfi.test.shared;

import java.util.List;
import net.java.html.json.ComputedProperty;
import net.java.html.json.Model;
import net.java.html.json.Property;

public enum PhoneType {
    HOME, WORK, MOBILE;
}

@Model(className = "Contact", properties = {
    @Property(name = "id", type = String.class),
    @Property(name = "firstName", type = String.class),
    @Property(name = "lastName", type = String.class),
    @Property(name = "address", type = Address.class),
    @Property(name = "phones", type = Phone.class, array=true)
})
final class Contacts {
    @ComputedProperty static String fullName(
        String firstName, String lastName
    ) {
        return firstName + " " + lastName;
    }
    
    @ComputedProperty static String callInfo(
        List<Phone> phones
    ) {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (Phone p : phones) {
            sb.append(sep).append(p.getNumber());
            sep = ", ";
        }
        return sb.toString();
    }
    
    @Model(className = "Address", properties = {
        @Property(name = "street", type = String.class),
        @Property(name = "town", type = String.class)
    })
    static class AddressImpl {
    }
    @Model(className = "Phone", properties = {
        @Property(name = "number", type = String.class),
        @Property(name = "type", type = PhoneType.class)
    })
    static class PhoneImpl {
        @ComputedProperty static String validatePhone(String number) {
            if (number == null) {
                return "Phone not specified!";
            }
            if (!number.startsWith("+")) {
                return "Get ready for internation calls!";
            }
            for (int i = 1; i < number.length(); i++) {
                if (Character.isDigit(number.charAt(i))) {
                    continue;
                }
                if (number.charAt(i) == ' ' && i > 1) {
                    continue;
                }
                return "Only numbers and spaces allowed after +";
            }
            return null;
        }
    }
}
