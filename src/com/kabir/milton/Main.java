package com.kabir.milton;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // write your code here
        new UserInterface(new Scanner(System.in)).startGame();
    }
}
enum GameStatus {
    XWin("X wins"),
    OWin("O wins"),
    DRAW("Draw"),
    KepPlaying("Game not finished");

    private final String status;

    GameStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
class UserInterface {
    private Board board;
    private Scanner scanner;

    public UserInterface(Scanner scanner) {
        this.scanner = scanner;
        this.board = new Board(new Character[3][3]);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void startGame() {
        board.createEmptyBoard();
        while (true) {
            System.out.print("Input command: ");
            String input = scanner.nextLine();
            if ("exit".equals(input)) {
                break;
            }
            if (input.startsWith("start")) {
                String[] inputArray = input.split(" ");
                if (inputArray.length == 3 && Arrays.stream(inputArray)
                        .allMatch(s -> s.equals("easy") || s.equals("user") || s.equals("start") ||
                                s.equals("medium") || s.equals("hard"))) {
                    printBoard(board);
                    Player player1 = getPlayerType(inputArray[1], 'X');
                    Player player2 = getPlayerType(inputArray[2], 'O');
                    play(player1, player2);
                } else {
                    System.out.println("Bad parameters!");
                }
            } else {
                System.out.println("Bad parameters!");
            }
        }
    }

    private Player getPlayerType(String input, Character c) {
        if (input.equals("user")) {
            return new UserPlayer(c);
        } else if (input.equals("easy")) {
            return new AIEasy(c);
        } else if (input.equals("medium")) {
            return new AIMedium(c);
        } else {
            return new AIHard(c);
        }
    }


    private void play(Player player1, Player player2) {
        while (true) {
            player1.makeMove(board, scanner);
            printBoard(board);
            if (stopGame()) {
                board.createEmptyBoard();
                break;
            }
            player2.makeMove(board, scanner);
            printBoard(board);
            if (stopGame()) {
                board.createEmptyBoard();
                break;
            }
        }
    }

    private boolean stopGame() {
        GameStatus gameStatus = board.checkStatusOfGame();
        if (gameStatus.equals(GameStatus.KepPlaying)) {
            System.out.println(gameStatus.getStatus());
            return false;
        } else {
            System.out.println(gameStatus.getStatus());
            return true;
        }
    }


    private void printBoard(Board board) {
        System.out.println("---------");
        for (Character[] row : board.getBoard()) {
            System.out.print("| ");
            for (Character c : row) {
                System.out.print(c + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }
}
class AIEasy extends Player{

    public AIEasy(char mark) {
        super(mark);
    }

    public void makeMove(Board board, Scanner scanner) {
        System.out.println("Making move level \"easy\"");
        int row = new Random().nextInt(3)+1;
        int col = new Random().nextInt(3)+1;
        while (board.occupied(new int []{row, col})){
            row = new Random().nextInt(3)+1;
            col = new Random().nextInt(3)+1;
        }
        board.markCoordinates(new int [] {row, col} , getMark());
    }
}
class AIHard extends Player {

    public AIHard(char mark) {
        super(mark);
    }

    @Override
    public void makeMove(Board board, Scanner scanner) {
        int[] move = findBestMove(board);
        board.markCoordinates(move, getMark());
    }

    private int[] findBestMove(Board board) {
        int maxVal = -10000;
        int rowVal = -1;
        int colVal = -1;
        for (int row = 0; row < board.getBoard().length; row++) {
            for (int col = 0; col < board.getBoard().length; col++) {
                int[] coordinates = new int[]{row + 1, col + 1};
                if (!board.occupied(coordinates)) {
                    board.markCoordinates(coordinates, getMark());
                    int val = minMax(board, 0,false);
                    board.markCoordinates(coordinates, ' ');
                    if (val > maxVal) {
                        maxVal = val;
                        rowVal = row + 1;
                        colVal = col + 1;
                    }
                }
            }
        }
        return new int[]{rowVal, colVal};
    }

    private int minMax(Board board, int depth,  boolean isMaxPlayer) {
        if (board.checkStatusOfGame().equals(GameStatus.DRAW)) {
            return 0;
        }
        if (board.checkStatusOfGame().equals(GameStatus.XWin)) {
            if (getMark() == 'X') {
                return 10 - depth;
            } else {
                return -10 + depth;
            }
        }
        if (board.checkStatusOfGame().equals(GameStatus.OWin)) {
            if (getMark() == 'O') {
                return 10 - depth;
            } else {
                return -10 + depth;
            }
        }

        int best;
        if (isMaxPlayer) {
            best = -1000;
            for (int row = 0; row < board.getBoard().length; row++) {
                for (int col = 0; col < board.getBoard().length; col++) {
                    int[] coordinates = new int[]{row + 1, col + 1};
                    if (!board.occupied(coordinates)) {
                        board.markCoordinates(coordinates, getMark());
                        best = Math.max(best, minMax(board, depth+1, false));
                        board.markCoordinates(coordinates, ' ');
                    }
                }
            }
        } else {
            best = 1000;
            for (int row = 0; row < board.getBoard().length; row++) {
                for (int col = 0; col < board.getBoard().length; col++) {
                    int[] coordinates = new int[]{row + 1, col + 1};
                    if (!board.occupied(coordinates)) {
                        board.markCoordinates(coordinates, getMark() == 'X' ? 'O' : 'X');
                        best = Math.min(best, minMax(board, depth+1, true));
                        board.markCoordinates(coordinates, ' ');
                    }
                }
            }
        }
        return best;

    }
}
class AIMedium extends Player {

    public AIMedium(char mark) {
        super(mark);
    }

    @Override
    public void makeMove(Board board, Scanner scanner) {
        System.out.println("Making move level \"medium\"");
        if (!madeWinMove(board)) {
            if (!madeBlockMove(board)) {
                makeRndMove(board);
            }

        }
    }

    private boolean madeBlockMove(Board board) {
        for (int row = 0; row < board.getBoard().length; row++) {
            if (canBlock(board.getBoard()[row])) {
                int empty = getEmptyCell(board.getBoard()[row]);
                board.markCoordinates(new int[]{row + 1, empty + 1}, getMark());
                return true;
            }
        }
        for (int i = 0; i < board.getBoard().length; i++) {
            Character[] col = board.getCol(i);
            if (canBlock(col)) {
                int empty = getEmptyCell(col);
                board.markCoordinates(new int[]{empty + 1, i + 1}, getMark());
                return true;
            }
        }
        Character[][] diagonals = board.getDiagonals();
        if (canBlock(diagonals[0])) {
            int empty = getEmptyCell(diagonals[0]);
            board.markCoordinates(new int[]{empty + 1, empty + 1}, getMark());
            return true;
        }
        if (canBlock(diagonals[1])) {
            int empty = getEmptyCell(diagonals[1]);
            board.markCoordinates(new int[]{3 - empty, empty + 1}, getMark());
            return true;
        }

        return false;
    }

    private boolean canBlock(Character[] row) {
        return Arrays.stream(row).filter(x -> x == ' ').count() == 1 &&
                Arrays.stream(row).filter(x -> x != ' ' && x != getMark()).count() == 2;

    }

    private boolean madeWinMove(Board board) {
        for (int row = 0; row < board.getBoard().length; row++) {
            if (canMakeLastMove(board.getBoard()[row])) {
                int empty = getEmptyCell(board.getBoard()[row]);
                board.markCoordinates(new int[]{row + 1, empty + 1}, getMark());
                return true;
            }
        }
        for (int i = 0; i < board.getBoard().length; i++) {
            Character[] col = board.getCol(i);
            if (canMakeLastMove(col)) {
                int empty = getEmptyCell(col);
                board.markCoordinates(new int[]{empty + 1, i + 1}, getMark());
                return true;
            }
        }
        Character[][] diagonals = board.getDiagonals();
        if (canMakeLastMove(diagonals[0])) {
            int empty = getEmptyCell(diagonals[0]);
            board.markCoordinates(new int[]{empty + 1, empty + 1}, getMark());
            return true;
        }
        if (canMakeLastMove(diagonals[1])) {
            int empty = getEmptyCell(diagonals[1]);
            board.markCoordinates(new int[]{3 - empty, empty + 1}, getMark());
            return true;
        }
        return false;
    }

    private boolean canMakeLastMove(Character[] row) {
        return Arrays.stream(row).filter(x -> x == getMark()).count() == 2 &&
                Arrays.stream(row).filter(x -> x == ' ').count() == 1;
    }

    private int getEmptyCell(Character[] characters) {
        for (int i = 0; i < characters.length; i++) {
            if (characters[i] == ' ') {
                return i;
            }
        }
        return -1;
    }

    public void makeRndMove(Board board) {
        int row = new Random().nextInt(3) + 1;
        int col = new Random().nextInt(3) + 1;
        while (board.occupied(new int[]{row, col})) {
            row = new Random().nextInt(3) + 1;
            col = new Random().nextInt(3) + 1;
        }
        board.markCoordinates(new int[]{row, col}, getMark());
    }
}
abstract class Player {

    private char mark;

    public Player(char mark) {
        this.mark = mark;
    }

    public char getMark() {
        return mark;
    }

    public void setMark(char mark) {
        this.mark = mark;
    }

    public abstract void makeMove(Board board, Scanner scanner);
}
class UserPlayer extends Player {

    public UserPlayer(char mark) {
        super(mark);
    }

    @Override
    public void makeMove(Board board, Scanner scanner) {
        int[] coordinates = getCoordinates(board, scanner);
        board.markCoordinates(coordinates, getMark());
    }

    private int[] getCoordinates(Board board, Scanner scanner) {
        System.out.print("Enter the coordinates: ");
        int[] coordinates;
        try {
            coordinates = Arrays.stream(scanner.nextLine().split(" "))
                    .mapToInt(Integer::parseInt).toArray();
            if (coordinates[0] > 3 || coordinates[0] < 1 || coordinates[1] > 3 || coordinates[1] < 1 || coordinates.length > 2) {
                System.out.println("Coordinates should be from 1 to 3!");
                return getCoordinates(board, scanner);
            } else if (board.occupied(coordinates)) {
                System.out.println("This cell is occupied! Choose another one!");
                return getCoordinates(board, scanner);
            }
            return coordinates;

        } catch (Exception e) {
            System.out.println("You should enter numbers!");
            return getCoordinates(board, scanner);

        }
    }

}
class Board {
    private Character[][] board;

    public Board(Character[][] board) {
        this.board = board;
    }

    public Character[][] getBoard() {
        return board;
    }

    public void setBoard(Character[][] board) {
        this.board = board;
    }

    public boolean occupied(int[] coordinates) {
        return board[coordinates[0] - 1][coordinates[1] - 1] != ' ';
    }


    public void markCoordinates(int[] coordinates, Character c) {
        board[coordinates[0] - 1][coordinates[1] - 1] = c;
    }

    public GameStatus checkStatusOfGame() {
        for (Character[] row : board) {
            Character c = row[0];
            if (Arrays.stream(row).allMatch(x -> x == c) && c != ' ') {
                return getStatus(c);
            }
        }
        for (int i = 0; i < board.length; i++) {
            Character[] col = getCol(i);
            Character c = col[0];
            if (Arrays.stream(col).allMatch(x -> x == c) && c != ' ') {
                return getStatus(c);
            }
        }
        Character[][] diagonals = getDiagonals();
        Character c = diagonals[0][0];
        if (Arrays.stream(diagonals[0]).allMatch(x -> x == c) && c != ' ') {
            return getStatus(c);
        }
        Character c1 = diagonals[1][0];
        if (Arrays.stream(diagonals[1]).allMatch(x -> x == c1) && c1 != ' ') {
            return getStatus(c1);
        }

        if (Arrays.stream(board).flatMap(Arrays::stream).noneMatch(c2 -> c2 == ' ')) {
            return GameStatus.DRAW;
        }
        return GameStatus.KepPlaying;
    }

    public Character[][] getDiagonals() {
        Character[][] rowArray = new Character[2][3];
        for (int i = 0; i < board.length; i++) {
            rowArray[0][i] = board[i][i];
            rowArray[1][i] = board[2 - i][i];
        }
        return rowArray;
    }

    public Character[] getCol(int col) {
        Character[] colArray = new Character[3];
        for (int row = 0; row < board.length; row++) {
            colArray[row] = board[row][col];
        }
        return colArray;
    }

    private GameStatus getStatus(Character c) {
        if (c == 'X') {
            return GameStatus.XWin;
        } else {
            return GameStatus.OWin;
        }
    }

    public void createEmptyBoard() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                board[row][col] = ' ';
            }
        }
    }
}
