package edu.uoregon.teamwon;

//first and last names, address, city, state, zip, phone number and e-mail address.
public class Person{
    private String firstName, lastName, streetAddress1, streetAddress2, city, state, zip, phoneNumber, email;

    public Person(){
    }

    public Person(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String firstName, String lastName, String streetAddress1, String streetAddress2, String city, String state, String zip, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = streetAddress2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreetAddress1() {
        return streetAddress1;
    }

    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }


    /**
     * @author Yehui
     * Check if names, addresses, city, phone, and email are valid.
     * @return a string that shows which one was invalid.
     */
    public String validationStatus() {

        StringBuilder sb = new StringBuilder();

        if (!inputValidation.validName(firstName)) sb.append("[First Name]  ");
        if (!inputValidation.validName(lastName)) sb.append("[Last Name]  ");
        if (!inputValidation.validAddress(streetAddress1)) sb.append("[Address 1]  ");
        if (!inputValidation.validAddress2(streetAddress2)) sb.append("[Address 2]  ");
        if (!inputValidation.validCity(city)) sb.append("[City]  ");
        if (!inputValidation.validZip((zip))) sb.append("[Zip]  ");
        if (!inputValidation.validState(state)) sb.append("[state]  ");
        if (!inputValidation.validPhone(phoneNumber)) sb.append("[Phone Number]  ");
        if (!inputValidation.validEmail(email)) sb.append("[Email]");

        return sb.toString();
    }


    private String[] getAttributes() {
        return new String[]{getFirstName(), getLastName(), getZip(), getStreetAddress1(), getStreetAddress2(),
                getCity(), getState(), getEmail(), getPhoneNumber()};
    }

    /**
     * Calculates person's search priority for the given search string.
     * @param searchText The search text
     * @return 0 for no match, larger numbers for better match.
     */
    public int matchesString(String searchText) {
        if(searchText == null || searchText.equals("")) {
            return 1;
        }
        String[] attributes = getAttributes();
        for(int i = 0; i < attributes.length; i++) {
            if(attributes[i] == null) {
                continue;
            }
            if(attributes[i].toLowerCase().startsWith(searchText.toLowerCase())) {
                return (attributes.length - i)*2;
            }
            if(attributes[i].toLowerCase().contains(searchText.toLowerCase())) {
                return (attributes.length - i)*2 - 1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "[ firstName=" + firstName
                + ", lastName=" + lastName + "]";
    }


    public void copyValues(Person target){
        this.firstName = target.getFirstName();
        this.lastName = target.getLastName();
        this.streetAddress1 = target.getStreetAddress1();
        this.streetAddress2 = target.getStreetAddress2();
        this.city = target.getCity();
        this.state = target.getState();
        this.zip = target.getZip();
        this.phoneNumber = target.getPhoneNumber();
        this.email = target.getEmail();
    }

}
