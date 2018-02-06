package edu.uoregon.teamwon;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Yehui
 * @date Jan 28, 2018
 */
public class inputValidation {

    private static final char LETTER_A = 'A';
    private static final char LETTER_Z = 'Z';
    private static final char LETTER_a = 'a';
    private static final char LETTER_z = 'z';
    private static final char NUMBER_0 = '0';
    private static final char NUMBER_9 = '9';
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static boolean isEnglishLetter(char c){
        return (c >= LETTER_A && c <= LETTER_Z) || (c >= LETTER_a && c <= LETTER_z);
    }

    private static boolean isNumber(char c){
        return c>= NUMBER_0 && c<= NUMBER_9;
    }

    /**
     * Only English Alphabet and Space is valid
     * @param
     * @return
     */
    public static boolean validName(String name){
        if(name == null || name.length() == 0) return false;
        char[] chars = name.toCharArray();

        for(char c : chars){
            if(!isEnglishLetter(c) && c != ' ') return false;
        }
        return true;
    }


    /**
     * The address only accepts english letters, number, space, Hash, and dot.
     * @param address
     * @return
     */
    public static boolean validAddress(String address){
        if(address == null || address.length() == 0) return true;
        char[] chars = address.toCharArray();
        for(char c : chars){
            if(!isEnglishLetter(c) && !isNumber(c) && c!= ' ' && c!='#' && c != '.') return false;
        }
        return true;
    }

    public static boolean validAddress2(String address){
        if(address == null || address.length()==0) return true;
        char[] chars = address.toCharArray();
        for(char c : chars){
            if(!isEnglishLetter(c) && !isNumber(c) && c!= ' ' && c!='#' && c != '.') return false;
        }
        return true;
    }

    public static boolean validCity(String city){
        if(city == null || city.length() == 0) return true;
        char[] chars = city.toCharArray();
        for(char c : chars){
            if(!isEnglishLetter(c) && c != ' ') return false;
        }
        return true;
    }

    public static boolean validZip(String zip){
        if(zip == null || zip.length() == 0) return true;
        char[] chars = zip.toCharArray();
        // If zip is like 12345
        if(zip.length() == 5){
            for(char c : chars){
                if(!isNumber(c)) return false;
            }
            return true;
        }
        // If zip is like 12345-6789
        if(zip.length() == 10){
            if(chars[5] != '-') return false;
            for(int i = 0; i<5; i++){
                if(!isNumber(chars[i])) return false;
            }
            for(int i = 6; i<10; i++){
                if(!isNumber(chars[i])) return false;
            }
            return true;
        }
        return false;
    }

    public static boolean validState(String state) {
        if(state == null || state.length() == 0) return true;
        AmericanStates validationState = new AmericanStates();
        if (validationState.full_to_abbre.containsKey(state)) {
            return true;
        }
        return false;
    }

    /**
     * Phone number only accepts numbers and plus.
     * @param phone
     * @return
     */
    public static boolean validPhone(String phone){
        if(phone == null || phone.length() == 0) return true;
        char[] chars = phone.toCharArray();
        for(char c : chars){
            if(!isNumber(c) && c != '+') return false;
        }
        return true;
    }


    public static boolean validEmail(String email){
        if(email == null || email.length() == 0) return true;
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(email).matches();
    }




    public static void main(String[] args) {
        String n1 = "Henry";
        System.out.println(n1);
        System.out.println(validName(n1));

        String n3 = "Henry Brain";
        System.out.println(n3);
        System.out.println(validName(n3));

        String n2 = "Henry 张";
        System.out.println(n2);
        System.out.println(validName(n2));

    }
}
