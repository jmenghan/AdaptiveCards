package io.adaptivecards.objectmodel;

import android.util.Pair;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ColumnSetPropertiesTest
{
    static {
        System.loadLibrary("adaptivecards-native-lib");
    }

    @Test
    public void AllPropertiesTest() throws Exception
    {
        {
            final String columnWidth = "{\"columns\":[{\"items\":[],\"type\":\"Column\",\"width\":\"stretch\"}],\"type\":\"ColumnSet\"}\n";

            ColumnSet columnSet = TestUtil.createMockColumnSet();
            Column column = TestUtil.createMockColumn();
            column.SetPixelWidth(50);
            column.SetWidth("stretch");
            columnSet.GetColumns().add(column);
            Assert.assertEquals(columnWidth, columnSet.Serialize());

            ParseResult result = AdaptiveCard.DeserializeFromString(TestUtil.encloseElementStringInCard(columnWidth), "1.0");
            ColumnSet parsedColumnSet = TestUtil.castToColumnSet(result.GetAdaptiveCard().GetBody().get(0));
            Assert.assertEquals("stretch", parsedColumnSet.GetColumns().get(0).GetWidth());
            Assert.assertEquals(0, parsedColumnSet.GetColumns().get(0).GetPixelWidth());
        }

        {
            final String columnPixelWidth = "{\"columns\":[{\"items\":[],\"type\":\"Column\",\"width\":\"50px\"}],\"type\":\"ColumnSet\"}\n";
            ColumnSet columnSet = TestUtil.createMockColumnSet();
            Column column = TestUtil.createMockColumn();
            column.SetWidth("stretch");
            column.SetPixelWidth(50);
            columnSet.GetColumns().add(column);

            Assert.assertEquals(columnPixelWidth, columnSet.Serialize());

            ParseResult result = AdaptiveCard.DeserializeFromString(TestUtil.encloseElementStringInCard(columnPixelWidth), "1.0");
            ColumnSet parsedColumnSet = TestUtil.castToColumnSet(result.GetAdaptiveCard().GetBody().get(0));
            Assert.assertEquals("50px", parsedColumnSet.GetColumns().get(0).GetWidth());
            Assert.assertEquals(50, parsedColumnSet.GetColumns().get(0).GetPixelWidth());
        }
    }

    @Test
    public void ColumnsTest() throws Exception
    {
        {
            final String columnSetEmptyColumns = "{\"columns\":[],\"type\":\"ColumnSet\"}\n";
            ColumnSet columnSet = TestUtil.createMockColumnSet();
            Assert.assertEquals(columnSetEmptyColumns, columnSet.Serialize());

            ParseResult result = AdaptiveCard.DeserializeFromString(TestUtil.encloseElementStringInCard(columnSetEmptyColumns), "1.0");
            ColumnSet parsedColumnSet = TestUtil.castToColumnSet(result.GetAdaptiveCard().GetBody().get(0));
            Assert.assertEquals(0, parsedColumnSet.GetColumns().size());
        }

        {
            final String columnSetWithColumn= "{\"columns\":[{\"items\":[],\"type\":\"Column\",\"width\":\"Auto\"}],\"type\":\"ColumnSet\"}\n";

            ColumnSet columnSet = TestUtil.createMockColumnSet();
            Column column = TestUtil.createMockColumn();
            columnSet.GetColumns().add(column);
            Assert.assertEquals(columnSetWithColumn, columnSet.Serialize());

            ParseResult result = AdaptiveCard.DeserializeFromString(TestUtil.encloseElementStringInCard(columnSetWithColumn), "1.0");
            ColumnSet parsedColumnSet = TestUtil.castToColumnSet(result.GetAdaptiveCard().GetBody().get(0));
            Assert.assertEquals(1, parsedColumnSet.GetColumns().size());
        }
    }

}
