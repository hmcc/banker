package org.problemchimp.banker.task;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.problemchimp.banker.pdf.BasicPdfTextExtractor;
import org.problemchimp.banker.pdf.PdfTextExtractor;
import org.problemchimp.banker.pdf.YbPdfTextExtractor;
import org.problemchimp.banker.task.Identify.Type;

/**
 * Extract lines from a Yorkshire Bank file.
 * 
 * @author Heather McCartney
 *
 */
public class Extract {

    private Type type;

    private BasicPdfTextExtractor basicExtractor = new BasicPdfTextExtractor();
    private YbPdfTextExtractor ybExtractor = new YbPdfTextExtractor();
    
    public Extract(Type type) {
        this.type = type;
    }

    public List<String> run(File file) throws IOException {
        PdfTextExtractor<?> extractor;
        switch (type) {
        case SMILE:
            extractor = basicExtractor;
            break;
        case YORKSHIRE_BANK:
            extractor = ybExtractor;
            break;
        default:
            System.out.println("Could not identify file " + file);
            return null;
        }
        return extractor.extractLines(file);
    }
}
