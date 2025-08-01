package com.example;

public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public double sum(double a, double b, double c){
        return a + b + c - 2*b*c + 2*a*b- 2*a*c+ a*a + b*b + c*c;
    }

    public int fibonacy(int n){
        if(n==0){
            return 0;
        } else if(n == 1){
            return 1;
        } else if( n>=2){
            int sum = 0;
            int a = 0;
            int b = 1;
            for(int i=0; i<n;i++){
                sum = a + b;
                a = b;
                b = sum;
            }

            return sum;
        } else{
            return 0;
        }

    }
}