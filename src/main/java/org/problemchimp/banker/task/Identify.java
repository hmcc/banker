package org.problemchimp.banker.task;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.problemchimp.banker.pdf.BasicPdfTextExtractor;

/**
 * Identify the type of file from its text. Currently Yorkshire Bank and
 * Smile are supported.
 * 
 * @author Heather McCartney
 *
 */
public class Identify {
    
    private BasicPdfTextExtractor extractor = new BasicPdfTextExtractor();

    public enum Type {
        SMILE(Pattern.compile("Smile Current Account")), 
        YORKSHIRE_BANK(Pattern.compile("Yorkshire Bank")), 
        UNKNOWN(null);

        Pattern pattern;

        Type(Pattern pattern) {
            this.pattern = pattern;
        }
    }
    
    protected Type identify(List<String> lines) {
        for (String line : lines) {
            for (Type t : Type.values()) {
                if (t.pattern != null && t.pattern.matcher(line).find()) {
                    return t;
                }
            }
        }
        return Type.UNKNOWN;
    }

    public Type run(File file) throws IOException {
        List<String> lines = extractor.extractLines(file);
        return identify(lines);
    }
}
