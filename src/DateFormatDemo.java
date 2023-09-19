import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

public class DateFormatDemo {
    public static void main(String [] args) throws ParseException {
        String dateString = "2015-07-16 17:07:21";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // use SimpleDateFormat to define how to PARSE the INPUT
        Date date = sdf.parse(dateString);

        // at this point you have a Date-Object with the value of
        // 1437059241000 milliseconds
        // It doesn't have a format in the way you think

        // use SimpleDateFormat to define how to FORMAT the OUTPUT
        System.out.println( sdf.format(date) );

        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        System.out.println( sdf.format(date) );


        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(LocalDate.parse(dateString, dateTimeFormatter).toString());

        long time = 1835750016764L;
        System.out.println(convertToZonedTime(time));

        ZonedDateTime now = LocalDateTime.now().atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime yesterday = now.minusDays(1);
        ZonedDateTime tomorrow = now.plusDays(1);
        System.out.println("tomo-" + tomorrow + "::" + "y'day-" + yesterday);

        long[] viewCounts = new long[30];
        System.out.println(viewCounts[29]);
        ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.systemDefault())
                .truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime dateTime1 = dateTime.minusDays(0);
        ZonedDateTime dateTime2 = dateTime.minusDays(30);
        System.out.println("start(minus 30days)-" + dateTime2 + "::" + "end(minus 10days)-" + dateTime1);
        int index = Period.between(dateTime2.toLocalDate(), dateTime1.toLocalDate())
                    .getDays();
        System.out.println("gap::" + index);
        System.out.println("Date::" + LocalDate.now(ZoneId.systemDefault()));
        System.out.println(LocalDate.parse("2023-02-22"));
        //2007-12-03T10:15:30+01:00[Europe/Paris]
        //2023-02-22T00:00:00+05:30[America/Los_Angeles]
        System.out.println(ZonedDateTime.parse("2023-02-22T00:00:00+00:00").getMonth());
        System.out.println(LocalDate.now().minusMonths(3).withDayOfMonth(1));

        //Find difference between months
        int monSize = Month.values().length;
        int startMon = 9;
        int endMon = 2;
        int diff = (monSize - ((startMon - endMon) % monSize)) % monSize;
        System.out.println(diff);

        Month startMonth = Month.JANUARY;
        System.out.println(startMonth.getDisplayName(TextStyle.SHORT, Locale.ENGLISH));

        LocalDateTime ldt = LocalDateTime.now();
        String formattedDateStr = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(ldt);
        System.out.println("Formatted Date in String format: " + formattedDateStr);

        Locale locale = Locale.GERMANY;
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        String dateStr = dateFormat.format(new Date());
        System.out.println(dateStr);

        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.US);
        String usFormattedDate = LocalDate.now().format(dateTimeFormatter1);  // 3
        System.out.println("Current date in en-US date format: " + usFormattedDate);

        System.out.println(DateTimeFormatter.ofPattern("MM/dd").withLocale(Locale.ITALIAN).format(LocalDate.now()));
    }

    private static ZonedDateTime convertToZonedTime(Long timeStamp) {
        Instant instant = Instant.ofEpochMilli(timeStamp);
        System.out.println(instant.atZone(ZoneId.systemDefault()).toLocalDate());
        instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return instant.atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS);
    }
}
