package org.problemchimp.banker.pdf;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class BasicPdfTextExtractor extends PdfTextExtractor<PDFTextStripper> {

    protected void initStripper() {
        try {
            stripper = new PDFTextStripper();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    protected String getText(PDDocument pdDoc) throws IOException {
        return stripper.getText(pdDoc);
    }
}
