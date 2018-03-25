package org.problemchimp.banker.pdf;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class YbPdfTextExtractor extends PdfTextExtractor<PDFTextStripperByArea> {

    protected void initStripper() {
        try {
            Rectangle rect = new Rectangle(0, 300, 400, 600);
            stripper = new PDFTextStripperByArea();
            ((PDFTextStripperByArea) stripper).addRegion("yb", rect);
            stripper.setSortByPosition(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    protected String getText(PDDocument pdDoc) throws IOException {
        stripper.extractRegions(pdDoc.getPage(0));
        List<String> regions = stripper.getRegions();
        return stripper.getTextForRegion(regions.get(0));
    }
}
