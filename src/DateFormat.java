import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateFormat {
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

    }
}
