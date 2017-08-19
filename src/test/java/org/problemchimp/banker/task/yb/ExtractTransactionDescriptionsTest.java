package org.problemchimp.banker.task.yb;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ExtractTransactionDescriptionsTest {

    private static List<String> LINES = Arrays.asList(
            "Your Rapid Repay statement.",
            "Statement No: 123",
            "Dale Description Debits Credits Balance",
            "03 Oct 2016 FVevious statement",
            "06 Oct DD Legal & General Ab C/D",
            "13 Oct E.On",
            "17 Oct Cash 14 Tescyork",
            "20 Oct DD Umbrella Company",
            "24 Oct DD Y.B. Gold Cord",
            "27 Oct DD Flying Fish Co",
            "MemberrAccounl-Fee -",
            "28 Get Salary Limited",
            "01 Nov DD Brgos-Energy",
            "DD **Flrst Payment**",
            "DD Yorkshire Water",
            "DD City Of York General",
            "SO Feed The - Birds",
            "1.23",
            "100.00"
            );
    private ExtractTransationDescriptions extract;

    @Before
    public void setUp() {
        extract = new ExtractTransationDescriptions();
    }

    @Test
    public void testGetRaw() {
        List<String> raw = extract.getRaw(LINES);
        assertEquals(14, raw.size());
        assertEquals(LINES.subList(3, LINES.size() - 2), raw);
    }

    @Test
    public void testFixFirstTransaction() {
        List<String> needsCorrection = new ArrayList<>(LINES.subList(12, 15));
        List<String> corrected = extract.fixFirstPayment(needsCorrection);
        assertEquals(2, corrected.size());
        assertEquals(needsCorrection.get(0), corrected.get(0));
        assertEquals(needsCorrection.get(2), corrected.get(1));
    }

    @Test
    public void testFixDates() {
        List<String> needsCorrection = new ArrayList<>(LINES.subList(3, 17));
        List<String> corrected = extract.fixDates(needsCorrection);
        List<String> expected = Arrays.asList(
                "03 Oct 2016 FVevious statement",
                "06 Oct 2016 DD Legal & General Ab C/D",
                "13 Oct 2016 E.On",
                "17 Oct 2016 Cash 14 Tescyork",
                "20 Oct 2016 DD Umbrella Company",
                "24 Oct 2016 DD Y.B. Gold Cord",
                "27 Oct 2016 DD Flying Fish Co",
                "27 Oct 2016 MemberrAccounl-Fee -",
                "28 Get 2016 Salary Limited",
                "01 Nov 2016 DD Brgos-Energy",
                "01 Nov 2016 DD **Flrst Payment**",
                "01 Nov 2016 DD Yorkshire Water",
                "01 Nov 2016 DD City Of York General",
                "01 Nov 2016 SO Feed The - Birds");
        assertEquals(expected.size(), corrected.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), corrected.get(i));
        }
    }

    @Test
    public void testRun() {
        List<String> expected = Arrays.asList(
                "03 Oct 2016 FVevious statement",
                "06 Oct 2016 DD Legal & General Ab C/D",
                "13 Oct 2016 E.On",
                "17 Oct 2016 Cash 14 Tescyork",
                "20 Oct 2016 DD Umbrella Company",
                "24 Oct 2016 DD Y.B. Gold Cord",
                "27 Oct 2016 DD Flying Fish Co",
                "27 Oct 2016 MemberrAccounl-Fee -",
                "28 Get 2016 Salary Limited",
                "01 Nov 2016 DD Brgos-Energy",
                "01 Nov 2016 DD Yorkshire Water",
                "01 Nov 2016 DD City Of York General",
                "01 Nov 2016 SO Feed The - Birds");
        List<String> result = extract.run(LINES);
        assertEquals(expected.size(), result.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }
}
