package kr.pe.ryudaewan.stiletto.util;

/**
 * Created by ryudaewan on 2017-06-26.
 */
public class Converter {
    public static String getVarNm(String s) {
        return toCamelCase(s, false);
    }

    public static String getClassNm(String s) {
        return toCamelCase(s, true);
    }

    private static String toCamelCase(String s, boolean isCapital) {
        String[] parts = s.split("_");
        String camelCaseString = "";
        for (String part : parts) {
            camelCaseString = camelCaseString + toProperCase(part);
        }

        if (!isCapital) {
            camelCaseString = camelCaseString.substring(0, 1).toLowerCase() + camelCaseString.substring(1, camelCaseString.length());
        }

        return camelCaseString;
    }

    private static String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() +
                s.substring(1).toLowerCase();
    }
}
