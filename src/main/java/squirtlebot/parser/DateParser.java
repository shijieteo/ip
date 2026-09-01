package squirtlebot.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Optional;

/**
 * Parses user input strings to identify date or dateTime values
 * DateParser contains a set of date and dateTime formats that it accepts
 */
public class DateParser {
    private List<String> dateFormatList;
    private List<String> dateTimeFormatList;

    /**
     * Constructs a DateParser object
     * Initializes the formats list to include all the date and dateTime formats it can parse
     */
    public DateParser() {
        dateFormatList = List.<String>of("dd-MM-yyyy", "dd/MM/yyyy", "yyyy-MM-dd", "yyyy/MM/dd");
        dateTimeFormatList = List.<String>of("dd-MM-yyyy HH:mm:ss", "dd/MM/yyyy HH:mm:ss",
                "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss");
    }

    /**
     * Converts a user input string into a {@link LocalDate} object
     *
     * @param userInput user input containing a date
     * @return an {@link Optional} containing the date object represented by user input
     *                  or an empty {@link Optional} if the user input is of an unsupported format
     */
    public Optional<Temporal> parseDate(String userInput) {
        for (String format : dateFormatList) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            try {
                LocalDate date = LocalDate.parse(userInput, formatter);
                return Optional.of(date);
            } catch (DateTimeParseException e) {
                continue;
            }
        }
        return Optional.<Temporal>empty();
    }

    /**
     * Converts a user input string into a {@link LocalDateTime} object
     *
     * @param userInput user input containing a date
     * @return an {@link Optional} containing the dateTime object represented by user input
     *                  or an empty {@link Optional} if the user input is of an unsupported format
     */
    public Optional<Temporal> parseDateTime(String userInput) {
        for (String format : dateTimeFormatList) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            try {
                LocalDateTime date = LocalDateTime.parse(userInput, formatter);
                return Optional.<Temporal>of(date);
            } catch (DateTimeParseException e) {
                continue;
            }
        }
        return Optional.<Temporal>empty();
    }
}
