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

    double evalPostfix(List<String> postfix) { // runtime errors fix?
        // TODO
        double d1 = 0;
        double d2 = 0;
        double result;
        int count = 0;
        for (int n = 0; n < postfix.size(); n++) {
//            if (OPERATORS.contains(postfix.get(n))) {
                /*if (postfix.size() < 2) {
                    throw new RuntimeException(MISSING_OPERAND);
                }*/
                if (postfix.size() > 2) {
                    d1 = Double.parseDouble(postfix.get(n - 1));
                    d2 = Double.parseDouble(postfix.get(n - 2));
                    result = applyOperator(postfix.get(n), d1, d2);
                    postfix.remove(n);
                    postfix.remove(n - 1);
                    postfix.remove(n - 2);
                    postfix.add(n - 2, Double.toString(result));
                    n -= 2;
                } else if (OPERATORS.contains(postfix.get(n))) {
                    throw new IllegalArgumentException(MISSING_OPERAND);
                } else {
                    throw new IllegalArgumentException(MISSING_OPERATOR);
                }
//            }
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

        Stack<String> stack = new Stack();
        List postfix = new ArrayList();
        String tmp;

        for (int n = 0; n < infix.size(); n++) {
            if (OPERATORS.contains(infix.get(n))) {
                if (stack.size() != 0) {
                    if (OPERATORS.contains(stack.peek())) {
                        while ((getPrecedence(stack.peek()) > getPrecedence(infix.get(n))
                                || (getPrecedence(stack.peek()) == getPrecedence(infix.get(n))
                                && getAssociativity(stack.peek()) == Assoc.LEFT))) {

                            postfix.add(stack.pop());
                            if (stack.size() == 0) {
                                break;
                            }
                            //popStack(stack, postfix);


                        }
                    }
                }
                stack.push(infix.get(n));
                /*if ((getAssociativity(infix.get(n))) == Assoc.RIGHT) {
                    postfix.add(infix.get(n + 1));
                    postfix.add(infix.get(n));
                    n++;
                } else {
                    stack.add(0, infix.get(n));
                }*/
            } else if (infix.get(n).equals("(")) {
                stack.push(infix.get(n));
            } else if (infix.get(n).equals(")")) {
                addParenthesis(stack, postfix);
                /*for (int i = 0; i < stack.size(); i++) {
                    if (!(stack.get(0).equals("("))) {
                        postfix.add(stack.get(0));
                    } else {
                        stack.remove(0);
                        break;
                    }
                    stack.remove(0);
                }*/
            } else/* if (!(infix.get(n).equals("("))) */ {
                postfix.add(infix.get(n));
            }
        }
        popStack(stack, postfix);
        return postfix;
    }

//    while(there is an operator at the top of the operator stack with greater precedence)
//    or (the operator at the top of the operator stack has equal precedence and the token is left associative))
//    and (the operator at the top of the operator stack is not a left parenthesis):
//    pop operators from the operator stack onto the output queue.
//    push it onto the operator stack.

   /* static void stack_peek(Stack<Integer> stack)
    {
        Integer element = (Integer) stack.peek();
        System.out.println("Element on stack top : " + element);
    }*/

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

    void addParenthesis(Stack<String> stack, List postfix) {
        while (true) {
            if (!(stack.peek().equals("("))) {
                postfix.add(stack.pop());
            } else {
                stack.pop();
                break;
            }
        }
    }

//    void addParenthesis(Stack<String> stack, List postfix) {
//        while (stack.size() != 0) {
//            if (!(stack.peek().equals("("))) {
//                postfix.add(stack.pop());
//                break;
//            } else if (stack.peek().equals("(")) {
//                stack.pop();
//                break;
//            } else {
//                stack.pop();
//                throw new IllegalArgumentException(MISSING_OPERATOR);
//            }
//        }
//    }
//
    void popStack(Stack<String> stack, List postfix) {
        int size = stack.size();
        for (int i = 0; i < size; i++) {
            postfix.add(stack.pop());
        }
    }

    // ---------- Tokenize -----------------------

    // List String (not char) because numbers (with many chars)
    List<String> tokenize(String expr) {
        // TODO

        StringBuilder str = new StringBuilder(expr);
        List tokens = new ArrayList();

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ' ') {
                if (Character.isDigit(str.charAt(i))) {
                    i = WholeNum(str, i, tokens);
                } else {
                    tokens.add(String.valueOf(str.charAt(i)));
                }
            }
        }
        return tokens;
    }

    int WholeNum(StringBuilder str, int i, List tokens) {
        StringBuilder newStr = new StringBuilder();
        while (i < str.length()) {
            if (Character.isDigit(str.charAt(i))) {
                newStr.append(str.charAt(i));
            } else {
                break;
            }
            i++;
        }
        tokens.add(newStr.toString());
        return --i;
    }
}
