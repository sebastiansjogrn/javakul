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
        out.println((tokenize(expr)));

        if (expr.length() == 0) {
            return NaN;
        }
        List<String> tokens = tokenize(expr);
        List<String> postfix = infix2Postfix(tokens);
        out.println(postfix);

        return evalPostfix(postfix);
    }

    // ------  Evaluate RPN expression -------------------

    double evalPostfix(List<String> postfix) { // fix 2^2+3 !=7 (now it's 32 (2^(2+3)))
        // TODO
        double d1 = 0;
        double d2 = 0;
        double result;
        int count = 0;
        for (int n = 0; n < postfix.size(); n++) {
            if (OPERATORS.contains(postfix.get(n))) {
                if (postfix.get(n - 1) != null || postfix.get(n - 2) != null) {
                    d1 = Double.parseDouble(postfix.get(n - 1));
                    d2 = Double.parseDouble(postfix.get(n - 2));
                    result = applyOperator(postfix.get(n), d1, d2);
                    postfix.remove(n);
                    postfix.remove(n - 1);
                    postfix.remove(n - 2);
                    postfix.add(n - 2, Double.toString(result));
                    n -= 2;
                }
            }
        }
        result = Double.parseDouble(postfix.get(0));
        return result;
    }

//    double evalPostfix(List<String> postfix) {
//        // TODO
//        double k1 = 0;
//        double k2 = 0;
//        double result;
//        int count = 0;
//        for(int n=0;n<postfix.size();n++){
//            if(OPERATORS.contains(postfix.get(n))){
//                for(int i = n;i>=0;i--){
//                    if(!OPERATORS.contains(postfix.get(i))){
//                        if(count>0){
//                            k2 = Double.parseDouble(postfix.get(i));
//                            result = applyOperator(postfix.get(n),k1,k2);
//                            postfix.add(i,Double.toString(result));
//                        }
//                        k1 = Double.parseDouble(postfix.get(i));
//                        postfix.remove(i);
//                        count ++;
//                    }
//                }
//            }
//        }
//        out.println(postfix);
//        return 0;
//    }

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
        List tmp = new ArrayList();

        for (int n = 0; n < infix.size(); n++) {
            if (OPERATORS.contains(infix.get(n)) || infix.get(n).equals("(")) {
                stack.add(infix.get(n));
            } else if (infix.get(n).equals(")")) {
                for (int i = stack.size() - 1; i >= 0; i--) {
                    if (!(stack.get(i).equals("("))) {
                        postfix.add(stack.get(i));
                    } else {
                        stack.remove(stack.size() - 1);
                        break;
                    }
                    stack.remove(stack.size() - 1);
                }
            } else if (!(infix.get(n).equals("("))) {
                postfix.add(infix.get(n));
            }
        }
        postfix.addAll(reverse(stack));
        return postfix;
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

    List reverse(List ls) {
        List reversed = new ArrayList();
        for (int i = ls.size() - 1; i >= 0; i--) {
            reversed.add(ls.get(i));
        }
        return reversed;
    }

    // ---------- Tokenize -----------------------

    // List String (not char) because numbers (with many chars)
    List<String> tokenize(String expr) {
        // TODO

        List tokens = new ArrayList();
        int count = 0;
        int base = 0;
        for (int i = 0; i < expr.length(); i++) {
            if (OPERATORS.indexOf(expr.charAt(i)) != -1 || "()".indexOf(expr.charAt(i)) != -1) {
                if (expr.substring(base, count).length() != 0) {
                    tokens.add(expr.substring(base, count));
                }
                tokens.add(String.valueOf(expr.charAt(i)));
                count++;
                base = count;
            } else if (i == (expr.length() - 1)) {
                count++;
                tokens.add(expr.substring(base, count));
            } else {
                count++;
            }

        }

        return tokens;
    }

    /*List[] split(String expr) {

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
    }*/

}
