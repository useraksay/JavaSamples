package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexCheck {
    public static final String MAIL_REGEX =
      "^([\\w\\.\\-\\'\\/\\&])+@([\\w\\-])+\\.(\\w)+([\\w\\.\\-])*$";
    public static final String DOMAIN_MAIL_REGEX =
      "^([\\w\\.\\-\\'\\/\\&])+@([\\w\\-])+\\.(\\w)+([\\.\\-]\\w+)*$";
    public static void main(String[] args) {
        mailCheck();
        domainMailCheck();
    }

    private static void mailCheck() {
        String mail = "testaug7kirtest@polytrde.global";
        Pattern compile = Pattern.compile(MAIL_REGEX);
        Matcher matcher = compile.matcher(mail);
        boolean isMatch = matcher.matches();
        System.out.println(mail + "::" + isMatch);
    }

    private static void domainMailCheck() {
        String mail = "testaug7kirtest@polytrde.global";
        Pattern compile = Pattern.compile(DOMAIN_MAIL_REGEX);
        Matcher matcher = compile.matcher(mail);
        boolean isMatch = matcher.matches();
        System.out.println(mail + "::" + isMatch);
    }
}
