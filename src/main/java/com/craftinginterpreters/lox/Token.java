package com.craftinginterpreters.lox;

import java.util.Objects;

public class Token {

    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    @Override
    public String toString() {
        return "Token{" +
               "type=" + type +
               ", lexeme='" + lexeme + '\'' +
               ", literal=" + literal +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Token token = (Token) o;
        return type == token.type &&
               Objects.equals(lexeme, token.lexeme) &&
               Objects.equals(literal, token.literal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, lexeme, literal);
    }

    public Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }
}
