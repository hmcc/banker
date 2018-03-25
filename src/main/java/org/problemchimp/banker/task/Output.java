package org.problemchimp.banker.task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Identify the type of file from its text. Currently Yorkshire Bank and
 * Smile are supported.
 * 
 * @author Heather McCartney
 *
 */
public class Output {
    
    private File dir;
    private String filename;

    public Output(File dir, String filename) {
        this.dir = dir;
        this.filename = filename;
    }

    public void run(List<String> lines) throws IOException {
        if (lines == null) {
            return;
        }
        PrintWriter pw = null;
        try {
            File file = new File(dir, filename);
            if (!file.exists()) {
                file.createNewFile();
            }
            pw = new PrintWriter(new FileWriter(file));
            for (String line : lines) {
                pw.println(line);
            }
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }
}
