package org.problemchimp.banker.pdf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfFilename {

    public static Pattern PATTERN = Pattern.compile("(.+)\\.pdf$");

    public static String replacePdfExtension(String pdfFilename, String newExtension) {
        Matcher m = PATTERN.matcher(pdfFilename);
        if (m.matches()) {
            return m.group(1) + "." + newExtension;
        }
        return pdfFilename + "." + newExtension;
    }
}
