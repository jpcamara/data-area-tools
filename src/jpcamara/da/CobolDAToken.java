package jpcamara.da;

import jpcamara.da.cobol.CobolDATokenizer;

public class CobolDAToken {
    private static final CobolDAToken EOF = new CobolDAToken(CobolDATokenType.EOF, null, 0, 0, 0, 0);

    static {
        EOF._next = EOF;
    }

    private CobolDATokenType _type;
    private String _value;
    private CobolDAToken _next;
    private CobolDAToken _previous;
    private int _line;
    private int _col;
    private int _start;
    private int _end;

    public CobolDAToken(CobolDATokenType type, String value, int line, int col, int start, int end) {
        _type = type;
        _value = value;
        _line = line;
        _col = col;
        _start = start;
        _end = end;
    }

    public void setNext(CobolDAToken t) {
        _next = t;
        if (!isEOF() || !t.isEOF()) {
            t._previous = this;
        }
    }

    public String getValue() {
        return _value;
    }

    public boolean isEOF() {
        return this == EOF;
    }

    public CobolDAToken previousToken()
    {
        return(_previous);
    }

    public CobolDAToken nextToken() {
        return _next;
    }

    public boolean match(String value) {
        return _value != null && _value.equalsIgnoreCase(value);
    }

    public static CobolDAToken tokenize(String contents, String symbolReplace) {
        CobolDAToken first = null;
        CobolDAToken previous = null;
        CobolDATokenizer tokenizer = new CobolDATokenizer(contents, symbolReplace);
        while (tokenizer.hasMoreTokens()) {
            CobolDAToken t = tokenizer.nextToken();
            if (previous != null) {
                previous.setNext(t);
            }
            if (first == null) {
                first = t;
            }
            previous = t;
        }
        if (previous != null) {
            previous.setNext(CobolDAToken.EOF);
        }
        if (first == null) {
            first = CobolDAToken.EOF;
        }
        return first;
    }

    public CobolDAToken removeTokens( CobolDATokenType... typesToRemove )
    {
        CobolDAToken first = null;
        CobolDAToken previous = null;
        CobolDAToken current = this;
        while( current != EOF ) {
            if (!isMatch(current, typesToRemove)) {
                CobolDAToken copy = new CobolDAToken(current._type, current._value, current._line, current._col, current._start, current._end);
                if (current.nextToken() == EOF) {
                    copy.setNext(EOF);
                }
                if (first == null) {
                    first = copy;
                }
                if (previous != null) {
                    previous.setNext(copy);
                }
                previous = copy;
            }
            current = current.nextToken();
        }
        return first == null ? EOF : first;
    }

    private boolean isMatch(CobolDAToken token, CobolDATokenType[] typesToRemove) {
        for( CobolDATokenType cobolTokenType : typesToRemove )
        {
            if( token._type == cobolTokenType )
            {
                return true;
            }
        }
        return false;
    }

    private CobolDAToken first() {
        if (_previous == null) {
            return this;
        } else {
            return _previous.first();
        }
    }

    private String toStringForDebug(CobolDAToken current) {
        if (isEOF()) {
            if (this == current) {
                return "[|EOF]";
            } else {
                return "|EOF";
            }
        } else {
            String str = getValue();
            if (this == current) {
                str = "[" + str + "]";
            }
            return str + " " + _next.toStringForDebug(current);
        }
    }

    public int getLine() {
        return _line;
    }

    public int getColumn() {
        return _col;
    }

    public int getStart() {
        return _start;
    }

    public int getEnd() {
        return _end;
    }

    public boolean isSymbol() {
        return _type == CobolDATokenType.SYMBOL;
    }

    public boolean isNumber() {
        return _type == CobolDATokenType.NUMBER;
    }

    public boolean isOperator() {
        return _type == CobolDATokenType.OPERATOR;
    }

    public boolean isTerminator() {
        return _type == CobolDATokenType.OPERATOR && _value.equals(".");
    }

    public boolean isKeyword() {
        return _type == CobolDATokenType.KEYWORD;
    }

    public boolean isComment() {
        return _type == CobolDATokenType.COMMENT;
    }

    public boolean isNumberDef() {
        return _type == CobolDATokenType.NUMBER_DEF;
    }

    public CobolDATokenType getTokenType() {
        return _type;
    }

    public boolean endOf(String... tokens) {
        CobolDAToken current = this;
        for (int i = tokens.length - 1; i >= 0; i--) {
            if (!current.match(tokens[i])) {
                return false;
            }
            current = current.previousToken();
        }
        return true;
    }

    public String toStringForDebug() {
        return first().toStringForDebug( this );
    }

    @Override
    public String toString() {
        return _type + " = [" + getValue() + "]";
    }
}
