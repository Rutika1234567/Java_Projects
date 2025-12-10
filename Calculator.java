import java.util.Scanner;

class Calculator {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        double a = 0, b = 0;

        System.out.println("Enter an operator (+, -, *, /, %, ^, sin, cos, tan, sqrt, log, ln, fact): ");
        String op = scan.next();

       
        if (op.equals("sin") || op.equals("cos") || op.equals("tan") || op.equals("sqrt") ||
            op.equals("log") || op.equals("ln") || op.equals("fact")) {

            System.out.print("Enter a number: ");
            a = scan.nextDouble();

        } else {  
            System.out.print("Enter first number: ");
            a = scan.nextDouble();

            System.out.print("Enter second number: ");
            b = scan.nextDouble();
        }

        switch (op) {
            case "+":
                System.out.println("Result: " + (a + b));
                break;

            case "-":
                System.out.println("Result: " + (a - b));
                break;

            case "*":
                System.out.println("Result: " + (a * b));
                break;

            case "%":
                System.out.println("Result: " + (a % b));
                break;

            case "/":
                if (b == 0) {
                    System.out.println("Result: undefined (division by zero)");
                } else {
                    System.out.println("Result: " + (a / b));
                }
                break;

            case "^":
                System.out.println("Result: " + Math.pow(a, b));
                break;

            case "sin":
                System.out.println("Result: " + Math.sin(Math.toRadians(a)));
                break;

            case "cos":
                System.out.println("Result: " + Math.cos(Math.toRadians(a)));
                break;

            case "tan":
                System.out.println("Result: " + Math.tan(Math.toRadians(a)));
                break;

            case "sqrt":
                if (a < 0)
                    System.out.println("Result: undefined (negative square root)");
                else
                    System.out.println("Result: " + Math.sqrt(a));
                break;

            case "log":
                if (a <= 0)
                    System.out.println("Result: undefined");
                else
                    System.out.println("Result: " + Math.log10(a));
                break;

            case "ln":
                if (a <= 0)
                    System.out.println("Result: undefined");
                else
                    System.out.println("Result: " + Math.log(a));
                break;

            case "fact":
                if (a < 0 || a != (int) a)
                    System.out.println("Result: undefined (factorial of negative or decimal)");
                else
                    System.out.println("Result: " + factorial((int) a));
                break;

            default:
                System.out.println("Invalid operator.");
        }

        scan.close();
    }

    // Factorial method
    public static long factorial(int n) {
        long fact = 1;
        for (int i = 2; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
}
