/*  Create a program that converts temperatures between Celsius  and Fahrenheit. Prompt the user to enter a temperature value and the unit of measurement, and then perform the conversion. Display temperature */

import java.util.Scanner;
class Main {
    public static void main(String[] args) {
       Scanner scan=new Scanner(System.in);
	   System.out.println("Enter a temperature value: ");
	   double temp=scan.nextDouble();
	
	   System.out.println("Enter a  unit of measurement (C/F): ");
       char unit=scan.next().charAt(0);
       
	   double converted;
	   if(unit=='c' || unit=='C') {
		   converted=(temp*9.0/5.0)+32.0;
		   System.out.println(temp + "°C is equal to " + converted + "°F");	
		 }
	   else if(unit=='f' || unit=='F'){
	     conv=(temp-32.0)*5.0/9.0;
         System.out.println(temp + "°F is equal to " + converted + "°C");
      }
	   else{
	      System.out.println("Invalid unit! Please enter C or F.");
	   }
	   scan.close();
    }
}