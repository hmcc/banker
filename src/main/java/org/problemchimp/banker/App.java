package org.problemchimp.banker;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.problemchimp.banker.pdf.PdfFileIterator;
import org.problemchimp.banker.pdf.PdfFilename;
import org.problemchimp.banker.task.Extract;
import org.problemchimp.banker.task.Identify;
import org.problemchimp.banker.task.Identify.Type;
import org.problemchimp.banker.task.Output;

public class App {

    private File inputDir;
    private File outputDir;

    public App(AppConfig appConfig) throws IOException {
        this.inputDir = appConfig.getInputDirectory();
        this.outputDir = appConfig.getOutputDirectory();
    }

    private void processFile(File file) {
        try {
            Type type = new Identify().run(file);
            List<String> lines = new Extract(type).run(file);
            String outputFilename = PdfFilename.replacePdfExtension(file.getName(), "txt");
            new Output(outputDir, outputFilename).run(lines);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        PdfFileIterator it = new PdfFileIterator(inputDir);
        it.forEachRemaining(this::processFile);
    }

    public static void main(String[] args) {
        App app = null;
        try {
            AppConfig config = new AppConfig();
            app = new App(config);
            app.run();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }


    }

}
