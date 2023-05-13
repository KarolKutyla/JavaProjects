/**
 *
 *  @author Kutyła Karol S24232
 *
 */

package zad1;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.util.Locale;

public class Time {

    public static String passed(String from, String to) {
        TemporalAccessor temporalFrom = null;
        TemporalAccessor temporalTo = null;
        if (from.contains("T")) {
            try {
                temporalFrom = LocalDateTime.parse(from);
            } catch (Exception e) {
                return "*** " + e;
            }
            try {
                temporalTo = LocalDateTime.parse(to);
            } catch (Exception e) {
                return "*** " + e;
            }
        } else {
            try {
                temporalFrom = LocalDate.parse(from);
            } catch (DateTimeParseException e) {
                return "*** " + e;
            }
            try {
                temporalTo = LocalDate.parse(to);
            } catch (DateTimeParseException e) {
                return "*** " + e;
            }
        }
        StringBuilder builder = new StringBuilder().append("Od ");
        try {
            DateTimeFormatter formatter;
            if(temporalFrom.getClass().equals(LocalDate.class))
            {
                formatter = DateTimeFormatter.ofPattern("d MMMM yyyy (EEEE)", new Locale("pl"));
            }else
            {
                formatter = DateTimeFormatter.ofPattern("d MMMM yyyy (EEEE) 'godz.' H:mm", new Locale("pl"));

            }
            builder.append(formatter.format(temporalFrom)).append(" do ").append(formatter.format(temporalTo))
                    .append(formatTime(temporalFrom, temporalTo));

        } catch (NullPointerException e) {
            return "*** " + e;
        }


        return builder.toString();
    }

    static String formatTime(TemporalAccessor temporalFrom, TemporalAccessor temporalTo) {
        StringBuilder builder = new StringBuilder().append("\n - mija: ");
        LocalDate dateFrom = LocalDate.from(temporalFrom);
        LocalDate dateTo = LocalDate.from(temporalTo);
        long days = ChronoUnit.DAYS.between(dateFrom, dateTo);
        String day;
        if (days == 1)
            day = "1 dzień";
        else day = "" + days + " dni";
        DecimalFormat df = new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.US));
        String weeks = df.format((float) days / 7);
        builder.append(day).append(", tygodni ").append(weeks);
        if (temporalFrom.getClass().equals(LocalDateTime.class)) {
            ZoneId zoneId = ZoneId.of("Europe/Warsaw");
            ZonedDateTime zdtFrom = ((LocalDateTime) temporalFrom).atZone(zoneId);
            ZonedDateTime zdtTo = ((LocalDateTime) temporalTo).atZone(zoneId);
            builder.append("\n - godzin: ")
                    .append(ChronoUnit.HOURS.between(zdtFrom, zdtTo))
                            .append(", minut: ")
                    .append(ChronoUnit.MINUTES.between(zdtFrom, zdtTo));
        }
        Period period = Period.between(dateFrom, dateTo);
        days = period.getDays();
        long months = period.getMonths();
        long years = period.getYears();

        String year;
        if (years == 1)
            year = "1 rok";
        else if(years == 2 || years == 3 || years ==4)
            year = "" + years + " lata";
        else year = "" + years + " lat";

        String month;
        if (months == 1)
            month = "1 miesiąc";
        else if(months == 2 || months == 3 || months ==4)
            month = "" + months + " miesiące";
        else month = "" + months + " miesięcy";

        if (days == 1)
            day = "1 dzień";
        else day = "" + days + " dni";

        if(years > 0 || months > 0 || days > 0) {
            builder.append("\n - kalendarzowo: ");
            if (years > 0)
                builder.append(year).append(", ");
            if (months > 0)
                builder.append(month).append(", ");
            if (days > 0)
                builder.append(day).append(", ");
        }
        return builder.toString().substring(0, builder.length()-2);
    }
}
