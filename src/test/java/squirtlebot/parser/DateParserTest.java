package squirtlebot.parser;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateParserTest {
    @Test
    public void parseDate_correctFormat_OptionalDateReturned() {
        DateParser parser = new DateParser();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse("01-01-1970", formatter);
        assertEquals(Optional.of(localDate), parser.parseDate("01-01-1970"));
    }

    @Test
    public void parseDate_wrongFormat_OptionalEmptyReturned() {
        DateParser parser = new DateParser();
        assertEquals(Optional.empty(), parser.parseDate("12 12 1970"));
    }

    @Test
    public void parseDateTime_correctFormat_OptionalDateTimeReturned() {
        DateParser parser = new DateParser();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.parse("26-08-2026 23:59:01", formatter);
        assertEquals(Optional.of(localDate), parser.parseDateTime("26-08-2026 23:59:01"));
    }

    @Test
    public void parseDateTime_wrongFormat_OptionalEmptyReturned() {
        DateParser parser = new DateParser();
        assertEquals(Optional.empty(), parser.parseDateTime("01-01-1970-23:59"));
    }
}