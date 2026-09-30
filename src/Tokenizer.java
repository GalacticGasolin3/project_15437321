import nodes.Token;
import nodes.TokenType;

import java.util.ArrayList;
import java.util.function.ToLongBiFunction;

/** class for turning mcscript code into tokens */
public class Tokenizer {
    //source code
    private String source;

    //pointer values to track where in processing the file we currently are
    private int index;
    private int length;

    public Tokenizer(String source) {
        this.source = source;
        this.index = 0;
        this.length = source.length();
    }

    public boolean validCharacterIndex(int count) {
        return length >= index + count;
    }
    public boolean validCharacterIndex() {
        return  validCharacterIndex(1);
    }

    //returns the character 'count' ahead of the index(or null) and moves that into the "current" character
    public Character peek(int count) {
        if (index + count >= length)
            return null;
        else
            return source.charAt(index + count);
    }
    public Character peek () {
        return peek(0);
    }

    //moves the current index character into current and increments the index
    public String consume() {
        return "" + source.charAt(index++);
    }

    //turn our source code into tokens
    public ArrayList<Token> tokenize() {
        ArrayList<Token> tokenList = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        //checking to make sure the file still has characters
        while (peek() != null) {
            //create strings with double quotes
            if (peek() == '"') {
                //consume first "
                consume();
                //add characters until we see another "
                while (peek() != null && peek() != '"') {
                    buffer.append(consume());
                }
                //consume the second "
                consume();
                //create the string token
                tokenList.add(new Token(TokenType.STRING, buffer.toString()));
                //clear buffer
                buffer.delete(0,buffer.length());
            }

            //number tokens
            if (peek() == '-' || Character.isDigit(peek()))
                tokenList.add(createNumberToken());

            //checking for keywords
            //check for alphabetic character
            if (Character.isAlphabetic(peek())) {
                //go until we reach the end of the word
                do {
                    buffer.append(consume());
                    //TODO: determine better criteria for what is a valid identifier character.
                } while (peek() != null && (Character.isLetterOrDigit(peek()) || peek() == '_'));
                //add the token to the token list
                tokenList.add(keywordToToken(buffer.toString()));
                //reset buffer
                buffer.delete(0,buffer.length());
                continue;
            }
            //check for a newline character
            if (peek() == '\n') {
                tokenList.add(new Token(TokenType.NEWLINE));
            }
            if (peek() == ',') {
                tokenList.add(new Token(TokenType.COMMA));
            }
            if (peek() == '.') {
                tokenList.add(new Token(TokenType.PERIOD));
            }
            if (peek() == '+') {
                tokenList.add(new Token(TokenType.ADD));
            }
            if (peek() == '*') {
                tokenList.add(new Token(TokenType.MULTIPLY));
            }
            if (peek() == '/') {
                tokenList.add(new Token(TokenType.DIVIDE));
            }
            if (peek() == '-') {
                tokenList.add(new Token(TokenType.SUBTRACT));
            }
            if (peek() == '%') {
                tokenList.add(new Token(TokenType.MODULO));
            }
            //check for the '@' symbol (selectors YAY!)
            if (peek() == '@') {
                //consume the @ char
                consume();
                tokenList.add(createSelectorToken());
                continue;
            }
            //check for square brackets
            if (peek() == '[')
                tokenList.add(new Token(TokenType.OPEN_SQUARE_BRACKET));
            if (peek() == ']')
                tokenList.add(new Token(TokenType.CLOSE_SQUARE_BRACKET));
            if (peek() == '(')
                tokenList.add(new Token(TokenType.OPEN_PAREN));
            if (peek() == ')')
                tokenList.add(new Token(TokenType.CLOSE_PAREN));
            //check for '='
            if (peek() == '=' && (peek(1) == null || peek(1) != '=')) {
                tokenList.add(new Token(TokenType.ASSIGN));
            }
            if (peek() == '}')
                throw  new RuntimeException("Expected: '{'");

            //create a nbt object token
            if (peek() == '{') {
                //add the curly
                buffer.append(consume());
                //loop until the curly is closed
                int nestingDepth = 1;
                while (nestingDepth > 0) {
                    if (peek() == null)
                        throw new RuntimeException("Expected: '}'");
                    char current = consume().charAt(0);
                    if (current == '{')
                        nestingDepth++;
                    if (current == '}')
                        nestingDepth--;
                    buffer.append(current);
                }
                //add nbt object to token list
                tokenList.add(new Token(TokenType.NBT_OBJECT, buffer.toString()));
                //clear buffer
                buffer.delete(0,buffer.length());
                continue;
            }

            //advance loop
            index++;
        }

        //reset index and return the list
        index = 0;
        System.out.println(tokenList);
        return tokenList;
    }

    //creates a selector token (called after consuming the '@')
    private Token createSelectorToken() {
        StringBuilder val = new StringBuilder("@");
        if (peek() == null)
            throw new RuntimeException("Selector does not exist");
        boolean valid = switch (peek()) {
          case 'a','e','r','s','n','p' -> true;
            default -> false;
        };
        if (!valid)
            throw new RuntimeException("Invalid Selector: \"@" + peek() + '\"');


        val.append(consume());
        if (peek() != '[')
            return new Token(TokenType.SELECTOR, val.toString());
        val.append(consume());
        int nestLevel = 1;
        while (nestLevel > 0) {
            if (peek() == null)
                throw new RuntimeException("Expected: ']'");
            if (peek() == ']')
                nestLevel--;
            if (peek() == '[')
                nestLevel++;

            val.append(consume());
        }

        return new Token(TokenType.SELECTOR,val.toString());
    }

    //creates a number token.
    // negative numbers are created if there is no space between the negative sign and the digits, otherwise this function will return a tokenType SUBTRACT
    // -3 -> Token(val:-3,type:int)
    // -.4 -> TOKEN(val:-.4,type:float)
    // - 3 -> TOKEN(type:SUBTRACT), TOKEN(val:3,type:INT)
    public Token createNumberToken() {
        if (peek() == null)
            throw new RuntimeException("Invalid Number");
        StringBuilder num = new StringBuilder();
        boolean neg = peek() == '-';
        if (neg)
            num.append(consume());

        if (neg && (peek() == null || !Character.isDigit(peek())))
            return new Token(TokenType.SUBTRACT);
        boolean seenPeriod = false;
        while (peek() != null && (Character.isDigit(peek()) || peek() == '.')) {
            char cur = peek();
            if (seenPeriod && cur == '.')
                throw new RuntimeException("Invalid Number");
            if (cur == '.')
                seenPeriod = true;

            num.append(consume());
        }

        if (seenPeriod)
            return new Token(TokenType.FLOAT_LIT,num.toString());
        else
            return new Token(TokenType.INT_LIT, num.toString());
    }


    //returns a token based on a given keyword.
    private Token keywordToToken(String keyword) {
        return switch (keyword) {
            case "function" -> new Token(TokenType.FUNCTION);
            case "say" -> new Token(TokenType.SAY);
            case "tellraw" -> new Token(TokenType.TELLRAW);
            case "storage", "var", "let" -> new Token(TokenType.STORAGE_DECLARATION);
            case "summon" -> new Token(TokenType.SUMMON);
            case "at" -> new Token(TokenType.AT);
            case "kill" -> new Token(TokenType.KILL);
            case "false" -> new Token(TokenType.FALSE);
            case "true" -> new Token(TokenType.TRUE);
            //unknown keywords are treated as identifiers.
            default ->  new Token(TokenType.IDENTIFIER,keyword);
        };
    }
}