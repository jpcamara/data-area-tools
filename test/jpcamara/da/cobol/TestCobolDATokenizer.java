package jpcamara.da.cobol;

import jpcamara.da.CobolDAToken;
import jpcamara.da.CobolDATokenType;
import jpcamara.da.cobol.CobolDATokenizer;
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
    
    @Test
    public void parseDA() throws Exception {
        CobolDATokenizer tokenizer = new CobolDATokenizer(SAMPLE_DA, ":P:");
        List<CobolDATokenType> tokens = Arrays.asList(
                CobolDATokenType.COMMENT,
                CobolDATokenType.COMMENT,
                CobolDATokenType.COMMENT,
                CobolDATokenType.NUMBER,
                CobolDATokenType.SYMBOL,
                CobolDATokenType.OPERATOR,
                CobolDATokenType.NUMBER,
                CobolDATokenType.SYMBOL,
                CobolDATokenType.OPERATOR,
                CobolDATokenType.COMMENT,
                CobolDATokenType.COMMENT,
                CobolDATokenType.COMMENT,
                CobolDATokenType.NUMBER,
                CobolDATokenType.SYMBOL,
                CobolDATokenType.OPERATOR,
                CobolDATokenType.NUMBER,
                CobolDATokenType.SYMBOL,
                CobolDATokenType.KEYWORD,
                CobolDATokenType.STRING_DEF,
                CobolDATokenType.OPERATOR
        );
        int i = 0;
        while (tokenizer.hasMoreTokens()) {
            CobolDAToken token = tokenizer.nextToken();
            assertEquals(tokens.get(i), token.getTokenType());
            i++;
        }
    }
}
