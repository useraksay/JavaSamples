import java.util.HashMap;
import java.util.Map;

public class ANZipRule {
    private static String [][] FieldMandatoryExpression = new String[][]{
        new String[]{"LIGHT,FULL", "USA,BRA,IND,GBR,DEU,CHN,CAN,ZAF,AUS,MEX,ITA,FRA,JPN,NLD", "zip", "reg,chg"},
        new String[]{"LIGHT,FULL", "BRA,CHL,PER", "tax", "reg,chg"},
        new String[]{"FULL", "ZAF,AUS,NLD", "tax", "chg"}
    };

    private static String [][] FieldMandatoryExprForAdditionalCountry = new String[][]{
        new String[]{"LIGHT,FULL", "ARG,AUT,BEL,CHE,DNK,ESP,GRC,HRV,HUN,IDN,ISR,ISL,KOR,LUX,NLD,NOR,POL,PRT,SGP,SVN,TWN,RUS,UKR", "zip", "reg,chg"}
    };

    private static Map<String, String> fieldMandatoryMap = new HashMap<>();
    private static String lightRegMandatoryCountryStr = "";

    static {
        for (int i = 0; i < FieldMandatoryExpression.length; i++) {
            String[] item = FieldMandatoryExpression[i];
            String accountTypeStr = item[0];
            String countryCodeStr= item[1];
            String fieldStr = item[2];
            String actionStr = item[3];
            String[] accountTypeArr = accountTypeStr.split(",");
            String[] countryCodeArr = countryCodeStr.split(",");
            for (String accountType : accountTypeArr) {
                for (String countryCode : countryCodeArr) {
                    fieldMandatoryMap.put(accountType + countryCode + fieldStr, actionStr);
                }
            }

            if (accountTypeStr.indexOf("LIGHT") != -1 && (fieldStr.indexOf("tax") != -1 || fieldStr.indexOf("vat") != -1) && actionStr.indexOf("reg") != -1) {
                lightRegMandatoryCountryStr = lightRegMandatoryCountryStr + ("".equals(lightRegMandatoryCountryStr) ? "" : ",") + countryCodeStr;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(fieldMandatoryMap);
        System.out.println(lightRegMandatoryCountryStr);
    }
}
