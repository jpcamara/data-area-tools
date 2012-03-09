package jpcamara.da;

import jpcamara.da.cobol.CobolDATokenizer;

public class DataAreaToken {
    private static final DataAreaToken EOF = new DataAreaToken(DataAreaTokenType.EOF, null, 0, 0, 0, 0);

    static {
        EOF._next = EOF;
    }

    private DataAreaTokenType _type;
    private String _value;
    private DataAreaToken _next;
    private DataAreaToken _previous;
    private int _line;
    private int _col;
    private int _start;
    private int _end;

    public DataAreaToken(DataAreaTokenType type, String value, int line, int col, int start, int end) {
        _type = type;
        _value = value;
        _line = line;
        _col = col;
        _start = start;
        _end = end;
    }

    public void setNext(DataAreaToken t) {
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

    public DataAreaToken previousToken()
    {
        return(_previous);
    }

    public DataAreaToken nextToken() {
        return _next;
    }

    public boolean match(String value) {
        return _value != null && _value.equalsIgnoreCase(value);
    }

    public static DataAreaToken tokenize(String contents, String symbolReplace) {
        DataAreaToken first = null;
        DataAreaToken previous = null;
        CobolDATokenizer tokenizer = new CobolDATokenizer(contents, symbolReplace);
        while (tokenizer.hasMoreTokens()) {
            DataAreaToken t = tokenizer.nextToken();
            if (previous != null) {
                previous.setNext(t);
            }
            if (first == null) {
                first = t;
            }
            previous = t;
        }
        if (previous != null) {
            previous.setNext(DataAreaToken.EOF);
        }
        if (first == null) {
            first = DataAreaToken.EOF;
        }
        return first;
    }

    public DataAreaToken removeTokens( DataAreaTokenType... typesToRemove )
    {
        DataAreaToken first = null;
        DataAreaToken previous = null;
        DataAreaToken current = this;
        while( current != EOF ) {
            if (!isMatch(current, typesToRemove)) {
                DataAreaToken copy = new DataAreaToken(current._type, current._value, current._line, current._col, current._start, current._end);
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

    private boolean isMatch(DataAreaToken token, DataAreaTokenType[] typesToRemove) {
        for( DataAreaTokenType cobolTokenType : typesToRemove )
        {
            if( token._type == cobolTokenType )
            {
                return true;
            }
        }
        return false;
    }

    private DataAreaToken first() {
        if (_previous == null) {
            return this;
        } else {
            return _previous.first();
        }
    }

    private String toStringForDebug(DataAreaToken current) {
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
        return _type == DataAreaTokenType.SYMBOL;
    }

    public boolean isNumber() {
        return _type == DataAreaTokenType.NUMBER;
    }

    public boolean isOperator() {
        return _type == DataAreaTokenType.OPERATOR;
    }

    public boolean isTerminator() {
        return _type == DataAreaTokenType.OPERATOR && _value.equals(".");
    }

    public boolean isKeyword() {
        return _type == DataAreaTokenType.KEYWORD;
    }

    public boolean isComment() {
        return _type == DataAreaTokenType.COMMENT;
    }

    public boolean isNumberDef() {
        return _type == DataAreaTokenType.NUMBER_DEF;
    }

    public DataAreaTokenType getTokenType() {
        return _type;
    }

    public boolean endOf(String... tokens) {
        DataAreaToken current = this;
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
