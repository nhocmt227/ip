package commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Converter {
    public static LocalDate stringToDate(String deadline) throws DateTimeParseException {
        deadline = deadline.strip();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return LocalDate.parse(deadline, formatter);
    }

    public static String dateToFormattedString(LocalDate date) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);
        return date.format(outputFormatter);
    }

}
