package com.gildedrose;

import com.gildedrose.models.Item;
import com.gildedrose.services.GildedRose;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giedrius on 2018-08-07.
 */
public class TextBaseApprovalTest {

    static final String PATH_TO_EXPECTED_OUTPUT_FILE = "golden_output.txt";
    Item[] items = null;
    List<String> expectedOutputLines = null;
    List<String> actualOutputLines = null;
    int daysToUpdateItems;

    @Before
    public void setUp() throws Exception{
        daysToUpdateItems = 30;
        items = new Item[] {
                new Item("+5 Dexterity Vest", 10, 20),
                new Item("Aged Brie", 2, 0),
                new Item("Elixir of the Mongoose", 5, 7),
                new Item("Sulfuras, Hand of Ragnaros", 0, 80),
                new Item("Sulfuras, Hand of Ragnaros", -1, 80),
                new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
                new Item("Conjured Mana Cake", 3, 6) };
        expectedOutputLines = readExpectedOutput();
    }

    private List<String> readExpectedOutput() throws Exception{

        List<String> outputLines = new ArrayList<>();
        ClassLoader classLoader = TextBaseApprovalTest.class.getClassLoader();
        String url = classLoader.getResource(PATH_TO_EXPECTED_OUTPUT_FILE).getFile();
        File file = new File(url);

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                outputLines.add(line);
            }
        }
        return outputLines;
    }

    @Test
    public void testTextBaseOutputValidity() throws Exception{
        actualOutputLines = simulateItemsUpdate(items, daysToUpdateItems);

        Assert.assertTrue(expectedOutputLines != null);
        Assert.assertTrue(actualOutputLines != null);
        Assert.assertEquals("Actual output size does not match the expected one.",
                actualOutputLines.size(), expectedOutputLines.size());

        for(int i = 0; i < actualOutputLines.size(); i++){

            String actualOutputLine = actualOutputLines.get(i);
            String expectedOutputLine = expectedOutputLines.get(i);
            Assert.assertEquals("Outputs do not match in line = " + (i + 1) +". Expected = '" +
                    expectedOutputLine + "' \n Actual = '" + actualOutputLine + "' \n",
                    actualOutputLine, expectedOutputLine);
        }
    }

    private List<String> simulateItemsUpdate(Item[] items, int daysToUpdateItems) throws Exception{
        List<String> outputLines = new ArrayList<>();

        outputLines.add("OMGHAI!");
        GildedRose app = new GildedRose(items);

        for (int i = 0; i < daysToUpdateItems + 1; i++) {
            outputLines.add("-------- day " + i + " --------");
            outputLines.add("name, sellIn, quality");
            for (Item item : items) {
                outputLines.add(item.toString());
            }
            outputLines.add("");
            app.updateQuality();
        }
        return outputLines;
    }
}
