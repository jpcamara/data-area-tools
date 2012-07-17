package jpcamara.da.jaxen;

import jpcamara.da.DataAreaNode;
import jpcamara.da.cobol.CobolDAParser;
import org.jaxen.BaseXPath;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class TestDataAreaNavigator {
    private static DataAreaNode DA;
    private static DataAreaElement root;

    @BeforeClass
    public static void setUpClass() throws Exception {
//        DA = new CobolDAParser(new File("test/jpcamara/da/jaxen/sample-da.txt"), ":P:").parseDA();
        DA = new CobolDAParser(new File("test/jpcamara/da/jaxen/sample-da.txt"), "").parseDA();
        root = new DataAreaElement(null, DA.getName(), "12223333344444".getBytes(), 0, DA.getLength(), DA);
    }

    /*
    01 LEVEL.
  05 LEVEL-2.
    10 LEVEL-3     PIC X.
    10 LEVEL-3-1   PIC XXX.
    10 LEVEL-3-2   PIC 99V99(2).
    10 LEVEL-3-3.
      15 LEVEL-4   PIC XXXXX.

      12223333344444
     */

    @Test
    public void findContent() throws Exception {
        //
        BaseXPath xpath = new BaseXPath("LEVEL-2/LEVEL-3",
                new DataAreaNavigator());
        List<DataAreaElement> elements = (List<DataAreaElement>) xpath.evaluate(root);
        DataAreaElement element = elements.get(0);
        Assert.assertEquals(0, element.getOffset());
        Assert.assertEquals(1, element.getLength());
        Assert.assertEquals("1", new String(Arrays.copyOfRange(element.getPayload(), element.getOffset(), element.getLength())));
    }

    @Test
    public void getDeepElement() throws Exception {
        BaseXPath xpath = new BaseXPath("LEVEL-2/LEVEL-3-2", new DataAreaNavigator());
        List<DataAreaElement> elements = (List<DataAreaElement>) xpath.evaluate(root);
        DataAreaElement element = elements.get(0);
        int offset = element.getOffset();
        Assert.assertEquals(4, element.getOffset());
        Assert.assertEquals(5, element.getLength());
        Assert.assertEquals("33333", new String(Arrays.copyOfRange(element.getPayload(), offset, offset + element.getLength())));
    }
}
