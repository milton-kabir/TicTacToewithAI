package com.kabir.milton;

import java.util.Scanner;

public class Main {
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        // write your code here
        System.out.println("Enter cells:");
        Scanner sc = new Scanner(System.in);
        String st = sc.nextLine();
        System.out.println("---------");
        System.out.println("| " + st.charAt(0) + " " + st.charAt(1) + " " + st.charAt(2) + " |");
        System.out.println("| " + st.charAt(3) + " " + st.charAt(4) + " " + st.charAt(5) + " |");
        System.out.println("| " + st.charAt(6) + " " + st.charAt(7) + " " + st.charAt(8) + " |");
        System.out.println("---------");
        int ck = 1, cnt = 0,cntx=0,cnt0=0;
        for(int i=0;i<st.length();i++){
            if(st.charAt(i)=='X'){
                cntx++;
            }
            else if(st.charAt(i)=='O'){
                cnt0++;
            }
        }
        if(cntx<=cnt0){
            ck=1;
        }
        else{
            ck=0;
        }
        while (true) {
            System.out.println("Enter the coordinates:");
            String[] strArr = sc.nextLine().split(" ");
            if (!isNumeric(strArr[0]) || !isNumeric(strArr[1])) {
                System.out.println("You should enter numbers!");

            }
            else {
                int c1 = Integer.parseInt(strArr[0]);
                int c2 = Integer.parseInt(strArr[1]);
                if (c1 > 3 || c1 < 1 || c2 > 3 || c2 < 1) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }
                int id = (c1 - 1) * 3 + (c2 - 1);
                if (st.charAt(id) != '_') {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }
                if (ck == 1) {
                    st = st.substring(0, id) + 'X' + st.substring(id + 1);
                    ck = 0;
                } else {
                    st = st.substring(0, id) + 'O' + st.substring(id + 1);
                    ck = 1;
                }
                cnt++;
                System.out.println("---------");
                System.out.println("| " + st.charAt(0) + " " + st.charAt(1) + " " + st.charAt(2) + " |");
                System.out.println("| " + st.charAt(3) + " " + st.charAt(4) + " " + st.charAt(5) + " |");
                System.out.println("| " + st.charAt(6) + " " + st.charAt(7) + " " + st.charAt(8) + " |");
                System.out.println("---------");


                int x = 0, O = 0;
                if (st.charAt(0) == 'X' && st.charAt(1) == 'X' && st.charAt(2) == 'X') {
                    x = 1;
                } else if (st.charAt(3) == 'X' && st.charAt(4) == 'X' && st.charAt(5) == 'X') {
                    x = 1;
                } else if (st.charAt(6) == 'X' && st.charAt(7) == 'X' && st.charAt(8) == 'X') {
                    x = 1;
                } else if (st.charAt(0) == 'X' && st.charAt(3) == 'X' && st.charAt(6) == 'X') {
                    x = 1;
                } else if (st.charAt(1) == 'X' && st.charAt(7) == 'X' && st.charAt(4) == 'X') {
                    x = 1;
                } else if (st.charAt(2) == 'X' && st.charAt(5) == 'X' && st.charAt(8) == 'X') {
                    x = 1;
                } else if (st.charAt(0) == 'X' && st.charAt(4) == 'X' && st.charAt(8) == 'X') {
                    x = 1;
                } else if (st.charAt(6) == 'X' && st.charAt(2) == 'X' && st.charAt(4) == 'X') {
                    x = 1;
                }

                if (st.charAt(0) == 'O' && st.charAt(1) == 'O' && st.charAt(2) == 'O') {
                    O = 1;
                } else if (st.charAt(3) == 'O' && st.charAt(4) == 'O' && st.charAt(5) == 'O') {
                    O = 1;
                } else if (st.charAt(6) == 'O' && st.charAt(7) == 'O' && st.charAt(8) == 'O') {
                    O = 1;
                } else if (st.charAt(0) == 'O' && st.charAt(3) == 'O' && st.charAt(6) == 'O') {
                    O = 1;
                } else if (st.charAt(1) == 'O' && st.charAt(7) == 'O' && st.charAt(4) == 'O') {
                    O = 1;
                } else if (st.charAt(2) == 'O' && st.charAt(5) == 'O' && st.charAt(8) == 'O') {
                    O = 1;
                } else if (st.charAt(0) == 'O' && st.charAt(4) == 'O' && st.charAt(8) == 'O') {
                    O = 1;
                } else if (st.charAt(6) == 'O' && st.charAt(2) == 'O' && st.charAt(4) == 'O') {
                    O = 1;
                }

                if (x == 0 && O == 0 && cnt == 9) {
                    System.out.println("Draw");
                    break;
                } else if (x == 1) {
                    System.out.println("X wins");
                    break;
                } else if (O == 1) {
                    System.out.println("O wins");
                    break;
                }
                if(cnt!=9){
                    System.out.println("Game not finished.");
                    break;
                }
            }
        }


    }
}