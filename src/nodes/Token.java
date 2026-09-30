package nodes;

public class Token {
    public final String value;
    public final TokenType type;

    //for tokens with values
    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    //for tokens without values
    public Token(TokenType type) {
        this.value = null;
        this.type = type;
    }

    //toString
    @Override public String toString() {
        return "{value: " + value + ", type: " + type + "}";
    }
}


