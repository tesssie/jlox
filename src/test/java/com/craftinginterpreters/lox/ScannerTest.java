package com.craftinginterpreters.lox;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Array;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScannerTest {

    private static Token EOF = new Token(TokenType.EOF, "", null, 1);

    @Test
    public void expectToAlwaysHaveEOFTokenAtEnd() {
        Scanner scanner = new Scanner("");
        List<Token> tokens = scanner.scanTokens();
        assertThat(tokens).containsExactly(new Token(TokenType.EOF, "", null, 1));
    }

    @Test
    public void expectToScanLeftPara() {
        Scanner scanner = new Scanner("()");
        Token leftParam = new Token(TokenType.LEFT_PAREN, "(", null, 1);
        Token rightParam = new Token(TokenType.RIGHT_PAREN, ")", null, 1);
        assertThat(scanner.scanTokens()).containsExactly(leftParam, rightParam, EOF);
    }

    @Test
    public void expectToScanBrances() {
        Scanner scanner = new Scanner("{}");
        Token leftBrace = new Token(TokenType.LEFT_BRACE, "{", null, 1);
        Token rightBrace = new Token(TokenType.RIGHT_BRACE, "}", null, 1);
        assertThat(scanner.scanTokens()).containsExactly(leftBrace, rightBrace, EOF);
    }

    @Test
    public void expectToIdentyfyAllOtherSingleCharacterLexemes() {
        Scanner scanner = new Scanner(".,+-;*");
        Token dot = new Token(TokenType.DOT, ".", null, 1);
        Token comma = new Token(TokenType.COMMA, ",", null, 1);
        Token plus = new Token(TokenType.PLUS, "+", null, 1);
        Token minus = new Token(TokenType.MINUS, "-", null, 1);
        Token semiColon = new Token(TokenType.SEMICOLON, ";", null, 1);
        Token star = new Token(TokenType.STAR, "*", null, 1);
        assertThat(scanner.scanTokens()).containsExactly(dot, comma, plus, minus, semiColon, star, EOF);
    }

    @Test
    public void expectToTestLookAheadForOperators() {
        Scanner scanner = new Scanner("!==");
        Token bang = new Token(TokenType.BANG, "!", null, 1);
        Token bangEqual = new Token(TokenType.BANG_EQUAL, "!=", null, 1);
        Token equal = new Token(TokenType.EQUAL, "=", null, 1);
        Token equalEqual = new Token(TokenType.EQUAL_EQUAL, "==", null, 1);
        assertThat(scanner.scanTokens()).containsExactly(bangEqual, equal, EOF);

        Scanner testcase2 = new Scanner("!+===");
        Token plus = new Token(TokenType.PLUS, "+", null, 1);

        assertThat(testcase2.scanTokens()).containsExactly(bang, plus, equalEqual, equal, EOF);
    }

    @Test
    public void expectToTestLessEqualOROrLess() {
        Scanner scanner = new Scanner("<<==");
        Token less = new Token(TokenType.LESS, "<", null, 1);
        Token lessEqual = new Token(TokenType.LESS_EQUAL, "<=", null, 1);
        Token equal = new Token(TokenType.EQUAL, "=", null, 1);
        assertThat(scanner.scanTokens()).containsExactly(less, lessEqual, equal, EOF);
    }

    @Test
    public void expectToTestGreaterEqualOrGreaterThan() {
        Scanner scanner = new Scanner(">>==");
        Token greaterEqual = new Token(TokenType.GREATER_EQUAL, ">=", null, 1);
        Token equal = new Token(TokenType.EQUAL, "=", null, 1);
        Token greater = new Token(TokenType.GREATER, ">", null, 1);

        assertThat(scanner.scanTokens()).containsExactly(greater, greaterEqual, equal, EOF);
    }

    @Test
    public void blankString() {
        Scanner scanner = new Scanner("\"\"");
        Token emptyString = new Token(TokenType.STRING, "\"\"", "", 1);
        assertThat(scanner.scanTokens()).containsExactly(emptyString, EOF);
    }

    @Test
    public void expectToParseString() {
        Scanner scanner = new Scanner("\"str\"");
        Token sampleString = new Token(TokenType.STRING, "\"str\"", "str", 1);
        assertThat(scanner.scanTokens()).containsExactly(sampleString, EOF);
    }

    @Test
    public void expectToErrorWhenInvalidString() {
        Scanner scanner = new Scanner("\"str");
        Token sampleString = new Token(TokenType.STRING, "\"str\"", "str", 1);
        assertThat(scanner.scanTokens()).containsOnly(EOF);
    }

    @Test
    public void expectToTestNumbers() {
        Scanner scanner = new Scanner("100");
        Token data = new Token(TokenType.NUMBER, "100", 100.0, 1);
        assertThat(scanner.scanTokens()).containsExactly(data, EOF);
    }

    @Test
    public void expectToCheckNumbersWithDecimals() {
        Scanner scanner = new Scanner("100.000");
        Token data = new Token(TokenType.NUMBER, "100.000", 100.00, 1);
        assertThat(scanner.scanTokens()).containsExactly(data, EOF);
    }

    @Test
    public void ReservedWords() {
        List<String> keywords = asList("and", "or", "class", "else", "false", "fun", "for", "if", "nil", "or", "print",
                                       "return", "super", "this", "true", "var", "while");
        String scanTest = String.join(" ", keywords);
        Scanner scanner = new Scanner(scanTest);
        assertThat(scanner.scanTokens().size()).isEqualTo(keywords.size() + 1);
    }

    @Test
    public void testIgnoresBlockComment() {
        Scanner scanner = new Scanner("/* This is a Commment */");
        assertThat(scanner.scanTokens()).hasSize(1);
    }

    @Test
    public void testMultiLineComment() {
        Scanner scanner = new Scanner(String.join("\n", "/*MultiLIne", "Comment*/"));
        assertThat(scanner.scanTokens()).hasSize(1);
    }

    @Test
    public void testIncompleteLine() {
        Scanner scanner = new Scanner("/* This is incorrect");
        assertThat(scanner.scanTokens()).hasSize(1);
    }


}
