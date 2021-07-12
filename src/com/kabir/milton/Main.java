package com.kabir.milton;

import java.util.Random;
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
    public static int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }

    public static void main(String[] args) {
        // write your code here
        //System.out.println("Enter cells:");
        Scanner sc = new Scanner(System.in);
        String st = "_________";
        System.out.println("---------");
        System.out.println("| " + st.charAt(0) + " " + st.charAt(1) + " " + st.charAt(2) + " |");
        System.out.println("| " + st.charAt(3) + " " + st.charAt(4) + " " + st.charAt(5) + " |");
        System.out.println("| " + st.charAt(6) + " " + st.charAt(7) + " " + st.charAt(8) + " |");
        System.out.println("---------");
        int ck = 1, cnt = 0,tt=0;
        while (true) {
            if(tt==1){
                Random random = new Random();
                int xx=random.nextInt(9);
                int yy=random.nextInt(9);
                if (xx > 3 || xx < 1 || yy > 3 || yy < 1) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }
                System.out.println(xx+" "+yy);
                int id = (xx - 1) * 3 + (yy - 1);
                if (st.charAt(id) != '_') {
//                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }
                st = st.substring(0, id) + 'O' + st.substring(id + 1);
                System.out.println("Making move level \"easy\"");
                System.out.println("---------");
                System.out.println("| " + st.charAt(0) + " " + st.charAt(1) + " " + st.charAt(2) + " |");
                System.out.println("| " + st.charAt(3) + " " + st.charAt(4) + " " + st.charAt(5) + " |");
                System.out.println("| " + st.charAt(6) + " " + st.charAt(7) + " " + st.charAt(8) + " |");
                System.out.println("---------");
                tt=0;

            }
            else{
                System.out.println("Enter the coordinates:");
                String[] strArr = sc.nextLine().split(" ");
                if (!isNumeric(strArr[0]) || !isNumeric(strArr[1])) {
                    System.out.println("You should enter numbers!");
                    continue;
                }
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
                st = st.substring(0, id) + 'X' + st.substring(id + 1);
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
                }
                else if (x == 1) {
                    System.out.println("X wins");
                    break;
                }
                else if (O == 1) {
                    System.out.println("O wins");
                    break;
                }
                tt=1;
            }
        }
    }
}