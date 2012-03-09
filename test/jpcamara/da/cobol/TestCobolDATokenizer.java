package jpcamara.da.cobol;

import jpcamara.da.DataAreaToken;
import jpcamara.da.DataAreaTokenType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class TestCobolDATokenizer {
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String SAMPLE_DA =
            " * some comment  " + NEW_LINE +
            "*another comment" + NEW_LINE +
            "* Ok sure" + NEW_LINE +
            "  01 :P:-WORD-UP." + NEW_LINE +
            "    05 :P:-WEB-TRAN-AREA." + NEW_LINE +
            "*" + NEW_LINE +
            "* SECTION COMMENT" + NEW_LINE +
            "*" + NEW_LINE +
            "      10 :P:-SECTION-A." + NEW_LINE +
            "        15 :P:-FIRST-CONTENT               PIC X.";

    //TODO not handling names starting without :P: --> HA!
    @Test
    public void parseDA() throws Exception {
        CobolDATokenizer tokenizer = new CobolDATokenizer(SAMPLE_DA, ":P:");
        List<DataAreaTokenType> tokens = Arrays.asList(
                DataAreaTokenType.COMMENT,
                DataAreaTokenType.COMMENT,
                DataAreaTokenType.COMMENT,
                DataAreaTokenType.NUMBER,
                DataAreaTokenType.SYMBOL,
                DataAreaTokenType.OPERATOR,
                DataAreaTokenType.NUMBER,
                DataAreaTokenType.SYMBOL,
                DataAreaTokenType.OPERATOR,
                DataAreaTokenType.COMMENT,
                DataAreaTokenType.COMMENT,
                DataAreaTokenType.COMMENT,
                DataAreaTokenType.NUMBER,
                DataAreaTokenType.SYMBOL,
                DataAreaTokenType.OPERATOR,
                DataAreaTokenType.NUMBER,
                DataAreaTokenType.SYMBOL,
                DataAreaTokenType.KEYWORD,
                DataAreaTokenType.STRING_DEF,
                DataAreaTokenType.OPERATOR
        );
        int i = 0;
        while (tokenizer.hasMoreTokens()) {
            DataAreaToken token = tokenizer.nextToken();
            assertEquals(tokens.get(i), token.getTokenType());
            i++;
        }
    }
}
