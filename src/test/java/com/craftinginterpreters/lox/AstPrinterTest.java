package com.craftinginterpreters.lox;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AstPrinterTest {

    private AstPrinter astPrinter = new AstPrinter();



    @Test
    public void check(){
        Expr expression = new Expr.Binary(
            new Expr.Unary(
                new Token(TokenType.MINUS, "-", null, 1),
                new Expr.Literal(123)),
            new Token(TokenType.STAR, "*", null, 1),
            new Expr.Grouping(
                new Expr.Literal(45.67)));
        assertThat(astPrinter.print(expression)).isEqualTo("(* (- 123) (group 45.67))");
    }

}