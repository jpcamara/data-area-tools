package jpcamara.da.jaxen;

import jpcamara.da.DataAreaNode;
import jpcamara.da.cobol.CobolDAParser;
import org.jaxen.BaseXPath;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

public class TestDataAreaNavigator {
    private static DataAreaNode DA;

    @BeforeClass
    public static void setUpClass() throws Exception {
        DA = new CobolDAParser(new File("test/jpcamara/da/jaxen/sample-da.txt"), ":P:").parseDA();
    }
    
    @Test
    public void findContent() throws Exception {
        BaseXPath xpath = new BaseXPath("SOME/PATH/TO/DATA",
                new DataAreaNavigator());
        xpath.evaluate(new DataAreaElement(null, "root", "O".getBytes(), 0, DA.getLength(), DA));

        
    }
}
