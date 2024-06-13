package playmo;
public class Capitalize {

    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return "Error: You must provide a Name!";
        }
        char firstLetter = Character.toUpperCase(str.charAt(0));
        String restOfString = str.substring(1);
        return firstLetter + restOfString;
    }
}
