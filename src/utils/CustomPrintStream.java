package utils;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

public class CustomPrintStream extends PrintStream {

    public CustomPrintStream(OutputStream out) {
        super(out);
    }

    @Override
    public void println(String string) {
        Date date = new Date();
        super.println("[" + date.toString() + "] " + string);
    }
}
