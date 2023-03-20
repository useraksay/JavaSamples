import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReflectionTest {
    public static final String PARAM_DELIMITER = "' || ";
    public static final String PARAM_NAME_VALUE_DELIMITER = " :: '";
    public static void main(String[] args) {
        TradingPartnerSearchRequestParam tpsrParam = new TradingPartnerSearchRequestParam();

        tpsrParam.anyOfTheseWords = "SAP";
        tpsrParam.page = "1";
        tpsrParam.sourceApplication = "TPP";
        tpsrParam.territoryFacetFilter = "US-AMM1,UK-ABB1";
        tpsrParam.commodityFacetFilter = "-70,90";
        tpsrParam.certificationFilter = "greenInitiatives,lgbtOwned";
        tpsrParam.businessTypeFilter = "Broker";

        StringBuilder searchParam = new StringBuilder();

//        getSearchParams(tpsrParam, searchParam);
        System.out.println(getSearchParams(tpsrParam));
    }

    private static String getSearchParams(TradingPartnerSearchRequestParam tpsrParams){
       StringBuilder searchParam = new StringBuilder();
       try {
           for (Field field : tpsrParams.getClass().getDeclaredFields()){
               if(field.get(tpsrParams) != null) {
                   if(field.getName().equalsIgnoreCase("territoryFacetFilter") ||
                      field.getName().equalsIgnoreCase("commodityFacetFilter")) {
                       String commaSeparatedString = field.get(tpsrParams).toString();
                       List<String> facetFilterList = getCommaSeparatedListFromString(commaSeparatedString);
                       searchParam.append(field.getName()).append("DotCodes").append(PARAM_NAME_VALUE_DELIMITER).append(facetFilterList).append(PARAM_DELIMITER);
                   }else if(field.getName().equalsIgnoreCase("page") ||
                           field.getName().equalsIgnoreCase("anyOfTheseWords")){
                       searchParam.append(field.getName()).append(PARAM_NAME_VALUE_DELIMITER).append(field.get(tpsrParams)).append(PARAM_DELIMITER);
                   } else if ( field.getName().equalsIgnoreCase("businessTypeFilter") ||
                           field.getName().equalsIgnoreCase("isoCertificationFilter") ||
                           field.getName().equalsIgnoreCase("certificationFilter")) {
                       String commaSeparatedString = field.get(tpsrParams).toString();
                       List<String> facetFilterList = getCommaSeparatedListFromString(commaSeparatedString);
                       searchParam.append(field.getName()).append(PARAM_NAME_VALUE_DELIMITER).append(facetFilterList).append(PARAM_DELIMITER);
                   }
               }
           }
           if (searchParam.lastIndexOf("|| ") != -1) {
               if(searchParam.length() >= PARAM_DELIMITER.length()){
                 return searchParam.substring(0, searchParam.lastIndexOf("|| "));
               }
           }
       }catch (Exception e){

       }
       return searchParam.toString();
    }

    private static void getSearchParams(TradingPartnerSearchRequestParam tpsrParam, StringBuilder searchParam) {
        String anyOfTheseWords = tpsrParam.anyOfTheseWords;
        String page = tpsrParam.page;
        String territoryFacetFilter = tpsrParam.territoryFacetFilter;
        String commodityFacetFilter = tpsrParam.commodityFacetFilter;
        String businessFacetFilter = tpsrParam.businessTypeFilter;
        String isoCertificationFilter = tpsrParam.isoCertificationFilter;

        searchParam.append("{")
                .append("\"" + "anyOfTheseWords" + "\"")
                .append(":")
                .append("\"" + anyOfTheseWords + "\"")
                .append(",")
                .append("\"" + "page" + "\"")
                .append(":")
                .append("\"" + page + "\"")
                .append(",")
                .append("\"" + "facetFilter" + "\"")
                .append(":").append("{");

        if(null != territoryFacetFilter && !territoryFacetFilter.isEmpty()) {
            addFilters(searchParam, territoryFacetFilter, "territoryFacetFilter");
        }
        if(null != commodityFacetFilter && !commodityFacetFilter.isEmpty()) {
            addFilters(searchParam, commodityFacetFilter, "commodityFacetFilter");
        }
       /* if(null != businessFacetFilter && !businessFacetFilter.isEmpty()) {
            addFilters(searchParam, businessFacetFilter, "businessTypeFacetFilter");
        }
        if(null != isoCertificationFilter && !isoCertificationFilter.isEmpty()) {
            addFilters(searchParam, isoCertificationFilter, "isoCertificationFilter");
        }*/

        searchParam.append("}");

        System.out.println(searchParam.toString());
    }

    private static void addFilters(StringBuilder searchParam, String facetFilter, String filterName) {
        List<String> territoryFacetFilterList = getCommaSeparatedListFromString(facetFilter);
        searchParam.append("\"" + filterName + "\"")
                .append(":")
                .append("{")
                .append("\"" + "codes" + "\"")
                .append(":")
                .append("[");
        for (int i = 0; i < territoryFacetFilterList.size(); i++){
            searchParam.append("\"" + territoryFacetFilterList.get(i) + "\"");
            if(i < territoryFacetFilterList.size() - 1){
                searchParam.append(",");
            }
        }
        searchParam.append("]");
    }

    private static String getFilterName(String facetFilter) {
        switch (facetFilter){
            case "territoryFacetFilter":
                return "territoryFacetFilter";
            case "commodityFacetFilter":
                return "commodityFacetFilter";
            case "businessTypeFilter":
                return "businessTypeFilter";
            case "certificationFilter":
                return "certificationFilter";
            default:
                return "isoCertificationFilter";
        }
    }

    private static List<String> getCommaSeparatedListFromString (String commaSeparatedString) {
        if (null != commaSeparatedString && !commaSeparatedString.isEmpty()) {
            return Arrays.asList(commaSeparatedString.split(","));
        }
        else {
            return Collections.emptyList();
        }
    }

    private static class TradingPartnerSearchRequestParam {
        public String toUserAnid;

        public String anyOfTheseWords;

        public String page;

        public String fromUserAnId;

        public String fromUserIPAddress;

        public String sourceApplication = "TPP";

        public String territoryFacetFilter;

        public String commodityFacetFilter;

        public String certificationFilter;

        public String isoCertificationFilter;

        public String businessTypeFilter;
    }
}
