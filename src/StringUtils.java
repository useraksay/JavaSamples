import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class StringUtils {
  public static final Integer[] rfxExcludedEventStates = new Integer[] {1005, 1004, 2005, 3, 4, 17};
  public static final String del = "<A4##!!>";

  public static void main(String[] args) {
    System.out.println(Arrays.stream(rfxExcludedEventStates).toList().toString());
    StringBuilder sb = new StringBuilder();
    sb.append("'");
    for (int i = 0; i < rfxExcludedEventStates.length; i++) {
      sb.append(rfxExcludedEventStates[i]);
      if (i != rfxExcludedEventStates.length - 1) {
        sb.append("'");
        sb.append(",");
        sb.append("'");
      }
    }
    sb.append("'");
    System.out.println (sb.toString());
    System.out.println (usingStreams().toString());
    String s = "new";
    String s1 = s.split(",")[0];
    System.out.println(s1);
    
    String p = ",new";
    String p1 = p.split(",")[0];
    System.out.println(p1);

    String q = ",new";
    String q1 = q.split(",")[0];
    System.out.println(q1);

    String s4 = null;
    String s5 = null;
    System.out.println(s4 + s5);

    String st = "123 strr, sst3, stt4";
    int index = st.indexOf(",");
    if (index != -1) {
      System.out.println(st.substring(index + 1));
    }

    String st1 = "123 strr,";
    index = st1.indexOf(",");
    if (index != -1) {
      System.out.println(st1.substring(index + 1));
    }

    st1 = ",";
    index = st1.indexOf(",");
    if (index != -1) {
      System.out.println(st1.substring(index + 1));
    }

    st1 = ",";
    index = st1.indexOf(",");
    if (index != -1) {
      System.out.println(st1.substring(index + 1));
    }


    String street3 = "st3";
    String street4 = "st4";
    String suffix2 = street3 + del + street4;
    System.out.println(suffix2);

    index = suffix2.lastIndexOf(del);
    street3 = suffix2.split(del)[0];
    System.out.println(street3);
    if (index != -1) {

    }

    System.out.println(Locale.US.toString());
  }

  private static String usingStreams() {
    return Arrays.asList(rfxExcludedEventStates).stream()
        .map(s -> "'" + s + "'")
        .collect(Collectors.joining(","));
  }


}
