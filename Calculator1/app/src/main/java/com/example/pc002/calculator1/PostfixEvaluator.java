package com.example.pc002.calculator1;

/**
 * Created by pc002 on 2016/6/28.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class PostfixEvaluator {
    private static final char ADD = '+';
    private static final char SUB = '-';
    private static final char MUL = '*';
    private static final char DIV = '/';

    private static  Map<Character, Integer> operatorStage;

    private Stack<Double> stack;

    public PostfixEvaluator() {
        // TODO 自动生成的构造函数存根
        stack = new Stack<Double>();

        operatorStage = new HashMap<Character, Integer>();
        operatorStage.put('+', 2);
        operatorStage.put('-', 2);
        operatorStage.put('*', 1);
        operatorStage.put('/', 1);
    }

    /**
     *
     * @param str
     * @return
     */
    public String postfixTurn(String str){
        StringBuilder sb = new StringBuilder();

        Stack<Character> operator = new Stack<Character>();

        String content;
        for(int i = 0; i < str.length(); )
        {
            String content_tmp = str.substring(i, i + 1);
            if(isOperator(content_tmp) || isKuohao(content_tmp)){
                content = content_tmp;
                i += 1;

                if(isOperator(content)){
                    char tmp = content.charAt(0);
                    operatorProcess(operator, sb, tmp);
                }else{
                    char tmp = content.charAt(0);
                    kuohaoProcess(operator, sb, tmp);
                }

            }else{
                int end = readDouble(str, i);
                content = str.substring(i, end);
                i = end;
                sb.append(content).append(' ');
            }

//            if(isOperator(content)){
//                char tmp = content.charAt(0);
//                operatorProcess(operator, sb, tmp);
//            }else if(isKuohao(content)){
//                char tmp = content.charAt(0);
//                kuohaoProcess(operator, sb, tmp);
//            }else {
//                sb.append(content).append(' ');
//            }
//			System.out.println(sb);
        }

        while(! operator.isEmpty()){
            sb.append(operator.pop()).append(' ');
        }

        return sb.toString();
    }

    private void operatorProcess(Stack<Character> operator, StringBuilder sb, Character tmp){
        if(operator.size() == 0){
            operator.push(tmp);
            return;
        }
        if('(' == operator.peek()){
            operator.push(tmp);
            return;
        }
        if(operatorStage.get(operator.peek()) > operatorStage.get(tmp)){
            operator.push(tmp);
            return;
        }
        if(operatorStage.get(operator.peek()) == operatorStage.get(tmp)){
            sb.append(tmp).append(' ');
            return;
        }
        sb.append(operator.pop()).append(' ');
        operatorProcess(operator, sb, tmp);
    }

    private void kuohaoProcess(Stack<Character> operator, StringBuilder sb, Character tmp){
        if('(' ==  tmp)
            operator.push(tmp);
        else{
            Stack<Character> operatorTmp = deleteLeftKuohao(operator);
            for(int i = 0; i < operatorTmp.size(); i ++){
                operatorProcess(operator, sb, operatorTmp.get(i));
            }
        }
    }

    private Stack<Character> deleteLeftKuohao(Stack<Character> operator){
        Stack<Character> operatorTmp = new Stack<Character>();
        for(int i = operator.size() - 1; i >= 0; i --){
            if('(' == operator.get(i)){
                operator.remove(i);
                break;
            }
            operatorTmp.push(operator.pop());
        }
        return operatorTmp;
    }

    private boolean isKuohao(String str){
        char tmp = str.charAt(0);
        if('(' == tmp || ')' == tmp)
            return true;
        return false;
    }

    public double evaluator(String str){
        Scanner scanner = new Scanner(str);
        String tmp;
        while(scanner.hasNext()){
            tmp = scanner.next();
            if(isOperator(tmp)){
                Double num2 = stack.pop();
                Double num1 = stack.pop();
                Double result = process(num1, num2, tmp);
                stack.push(result);
            }
            else{
                stack.push(Double.parseDouble(tmp));
            }
        }
        return stack.pop();
    }

    private boolean isOperator(String str){
        char tmp = str.charAt(0);
        if(tmp == ADD || tmp == SUB || tmp == MUL || tmp == DIV)
            return true;
        return false;
    }

    private double process(double num1, double num2, String tmp){
        double result = 0;
        char op = tmp.charAt(0);
        switch (op) {
            case ADD:
                result =  num1 + num2;
                break;
            case SUB:
                result = num1 - num2;
                break;
            case MUL:
                result = num1 * num2;
                break;
            case DIV:
                result = num1 / num2;
                break;
            default:
                break;
        }
        return result;
    }

    private int readDouble(String str, int index){
        StringBuilder sb = new StringBuilder();
        for(int i = index; i < str.length(); i ++){
            String tmp = str.substring(i, i + 1);
            if(isOperator(tmp) || isKuohao(tmp)){
                return i;
            }
        }
        return str.length();
    }


    public static void main(String[] args) {
        PostfixEvaluator evaluator = new PostfixEvaluator();


//		String str = "3 4 2 * +";
//		System.out.println(evaluator.evaluator(str));

        String str1 = "1.1+(2.2-1)*2";
        System.out.println(evaluator.postfixTurn(str1));

        System.out.println(evaluator.evaluator(evaluator.postfixTurn(str1)));

//		String str2 = "a + ( b * c ) + ( d * e + f ) * g";
//		System.out.println(evaluator.postfixTurn(str2));
    }
}