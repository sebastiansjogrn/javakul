import java.util.*;

import static java.lang.Double.NaN;
import static java.lang.Math.pow;
import static java.lang.System.*;


/*
 *  A calculator for rather simple arithmetic expressions
 *
 *  This is not the program, it's a class declaration (with methods) in it's
 *  own file (which must be named Calculator.java)
 *
 *  To run this: Run REPL, CalculatorGUI or WebCalcServer (they all use this
 *  but looks different)
 *
 *   NOTE:
 *   - No negative numbers implemented
 */
class Calculator {

    // Here are the only allowed instance variables!
    // Error messages (more on static later)
    final static String MISSING_OPERAND = "Missing or bad operand";
    final static String DIV_BY_ZERO = "Division with 0";
    final static String MISSING_OPERATOR = "Missing operator or parenthesis";
    final static String OP_NOT_FOUND = "Operator not found";

    // Definition of operators
    final static String OPERATORS = "+-*/^";

    // Method used by all
    double eval(String expr) {
        out.println(Arrays.toString(split(expr)));

        if (expr.length() == 0) {
            return NaN;
        }
        List<String> tokens = tokenize(expr);
        List<String> postfix = infix2Postfix(tokens);
        return evalPostfix(postfix);
    }

    // ------  Evaluate RPN expression -------------------

    double evalPostfix(List<String> postfix) {
        // TODO
        return 0;
    }

    double applyOperator(String op, double d1, double d2) {
        switch (op) {
            case "+":
                return d1 + d2;
            case "-":
                return d2 - d1;
            case "*":
                return d1 * d2;
            case "/":
                if (d1 == 0) {
                    throw new IllegalArgumentException(DIV_BY_ZERO);
                }
                return d2 / d1;
            case "^":
                return pow(d2, d1);
        }
        throw new RuntimeException(OP_NOT_FOUND);
    }


    // ------- Infix 2 Postfix ------------------------
    List<String> infix2Postfix(List<String> infix) {
        // TODO

        List stack = new Stack();
        List postfix = new ArrayList();

        for (int n = 0; n < infix.size(); n++) {
            if (OPERATORS.contains(infix.get(n)) || "()".contains(infix.get(n))) {
                stack.add(infix.get(n));
            }
        }

        return null;
    }

    // TODO More methods

    int getPrecedence(String op) {
        if ("+-".contains(op)) {
            return 2;
        } else if ("*/".contains(op)) {
            return 3;
        } else if ("^".contains(op)) {
            return 4;
        } else {
            throw new RuntimeException(OP_NOT_FOUND);
        }
    }

    Assoc getAssociativity(String op) {
        if ("+-*/".contains(op)) {
            return Assoc.LEFT;
        } else if ("^".contains(op)) {
            return Assoc.RIGHT;
        } else {
            throw new RuntimeException(OP_NOT_FOUND);
        }
    }

    enum Assoc {
        LEFT,
        RIGHT
    }

    // ---------- Tokenize -----------------------

    // List String (not char) because numbers (with many chars)
    List<String> tokenize(String expr) {
        // TODO
        return null;
    }

//    boolean contains(String thigny, String ex) {
//        boolean yes = false;
//        int offset = ex.length() - 1;
//        for (int i = 0; i < thigny.length(); i++) {
//            if (ex == thigny.substring(i, i + offset)) {
//                yes = true;
//            }
//        }
//        return yes;
//    }
//

    List[] split(String expr) {

        List Numbers = new ArrayList();
        List operands = new Stack();
        int count = 0;
        int base = 0;
        for (int i = 0; i < expr.length(); i++) {
            if (OPERATORS.indexOf(expr.charAt(i)) != -1) {
                if (expr.substring(base, count).length() != 0) {
                    Numbers.add(expr.substring(base, count));
                }
                operands.add(expr.charAt(i));
                count++;
                base = count;
            } else if (i == (expr.length() - 1)) {
                count++;
                Numbers.add(expr.substring(base, count));
            } else {
                count++;
            }

        }
        List[] lists = new List[]{Numbers, operands};
        return lists;
    }

}
