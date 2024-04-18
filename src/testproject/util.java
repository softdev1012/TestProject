package testproject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * The util class provides utility methods for user input and date manipulation.
 */

public class util {

    private static Scanner scanner = new Scanner(System.in);

    public static int getValidIntInput(String prompt) {
        int input;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // clear the invalid input
            }
            input = scanner.nextInt();
            if (input < 0) {
                System.out.println("Invalid input. Please enter a non-negative integer.");
            }
        } while (input < 0);
        return input;
    }

    public static Date getDateInput(String prompt) {
        Date date = null;
        boolean validInput = false;

        do {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                date = dateFormat.parse(input);

                validInput = true;
            } catch (ParseException e) {
                System.out.println("Please use the format yyyy-MM-dd HH:mm.");
            }
        } while (!validInput);

        return date;
    }

    public static Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDateTime(String dateTimeStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateTimeStr);
        } catch (ParseException e) {
            return null;
        }
    }

}
