import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Optional;

public class DateParser {
    private List<String> dateFormatList;
    private List<String> dateTimeFormatList;

    DateParser() {
        dateFormatList = List.<String>of("dd-MM-yyyy", "dd/MM/yyyy", "yyyy-MM-dd", "yyyy/MM/dd");
        dateTimeFormatList = List.<String>of("dd-MM-yyyy HH:mm:ss", "dd/MM/yyyy HH:mm:ss",
                "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss");
    }

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
