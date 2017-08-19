package org.problemchimp.banker.pdf;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Iterator;

public class PdfFileIterator implements Iterator<File> {

    private File dir;
    private String[] files;
    private int pos;

    private static FilenameFilter pdfFilter = new FilenameFilter() {

        @Override
        public boolean accept(File dir, String filename) {
            return new File(dir, filename).isFile() && PdfFilename.PATTERN.matcher(filename).matches();
        }
    };

    public PdfFileIterator(File dir) {
        this.dir = dir;
        this.files = dir.list(pdfFilter);
        this.pos = 0;
    }

    @Override
    public boolean hasNext() {
        return pos < files.length;
    }

    @Override
    public File next() {
        return new File(dir, files[pos++]);		
    }

}
