package org.problemchimp.banker.task.yb;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractTransationDescriptions {

    Pattern BEFORE = Pattern.compile("Da[lt]e Description Debits Credits Balance");
    Pattern AFTER = Pattern.compile("^[0-9.,]+$");

    Pattern DATE = Pattern.compile("^([0-9]{2} [A-Z][a-z]{2} ([0-9]{4} )?).*$");
    Pattern FIRST_PAYMENT = Pattern.compile("^([0-9]{2} [A-Z][a-z]{2} )?.*\\*\\*F[il]rst Payment\\*\\*$");

    List<String> getRaw(Iterable<String> lines) {
        List<String> result = new ArrayList<>();
        boolean processing = false;
        for (String line : lines) {
            if (!processing && BEFORE.matcher(line).matches()) {
                processing = true;
            } else if (processing && AFTER.matcher(line).matches()) {
                processing = false;
            } else if (processing) {
                result.add(line);
            }
        }
        return result;
    }

    /**
     * Not all transaction descriptions for Yorkshire Bank are meaningful.
     * If a line simply says "**First Payment**" (or Flrst Payment**) then
     * it should be dropped.
     *
     * @param lines
     * @return
     */
    List<String> fixFirstPayment(Iterable<String> lines) {
        List<String> result = new ArrayList<>();
        String prev = null;
        for (String line : lines) {
            Matcher m = FIRST_PAYMENT.matcher(line);
            if (m.matches()) {
                prev = m.group(1);
            } else if (prev != null) {
                result.add(prev + line);
                prev = null;
            } else {
                result.add(line);
            }
        }
        return result;
    }

    /**
     * Not all transaction descriptions for Yorkshire Bank have dates. If a
     * line is missing a date, then the date from the previous line should
     * be used. If a line has a date without a year then the most recently-
     * seen year should be used.
     *
     * @param lines
     * @return
     */
    List<String> fixDates(Iterable<String> lines) {
        List<String> result = new ArrayList<>();
        String currentDate = null;
        String currentYear = null;
        for (String line : lines) {
            Matcher m = DATE.matcher(line);
            if (m.matches()) {
                currentDate = m.group(1);
                if (m.group(2) != null) {
                    currentYear = m.group(2);
                } else if (currentYear != null) {
                    currentDate = currentDate + currentYear;
                    line = currentDate + line.substring(7);
                }
                result.add(line);
            } else {
                result.add(currentDate + line);
            }
        }
        return result;
    }

    public List<String> run(Iterable<String> lines) {
        List<String> result = getRaw(lines);
        result = fixFirstPayment(result);
        result = fixDates(result);
        return result;
    }
}
