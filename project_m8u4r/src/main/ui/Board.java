package ui;

import model.Cell;
import model.SetOfCell;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// Board of cells
public class Board {
    private SetOfCell board;
    private Scanner input;
    private int time = 1000;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(0);
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    private static final String JSON_STORE = "./data/board.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    Runnable timerDraw = new Runnable() {
        public void run() {
            drawList();
            System.out.println();
            board.nextCells();
        }
    };

    //Effects: sets board to a new list of 100 cells and runs the board
    public Board() {
        board = new SetOfCell();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBoard();
    }

    //Modifies: this
    //Effects: processes user input
    private void runBoard() {
        boolean keepGoing = true;
        String choice;
        System.out.println();
        drawList();
        initBoard();
        System.out.println("This is an empty board.");
        menu();

        while (keepGoing) {
            choice = input.next();
            choice = choice.toLowerCase();

            if ("q".equals(choice)) {
                keepGoing = false;
            } else {
                processChoice(choice);
            }
        }
    }

    //Effects: initializes input
    private void initBoard() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    //Effects: displays menu of options to user
    private void menu() {
        System.out.println("\nInput an integer from 0 to 99 to activate the cell at the integers position.");
        System.out.println("Inputting the same position deactivates the cell.");
        System.out.print("Type a to input a cell position,");
        System.out.print(" e to start the automatic evolution, p to stop the evolution (it might take a few tries)");
        System.out.println(", t to change the speed of the automatic evolution, or n to manually increment the board.");
        System.out.println("Type r to reset or q to quit\n");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processChoice(String command) {
        if (command.equals("e")) {
            executor = Executors.newScheduledThreadPool(0);
            executor.scheduleAtFixedRate(timerDraw, 0, time, TimeUnit.MILLISECONDS);
        } else if (command.equals("p")) {
            executor.shutdown();
        } else if (command.equals("a")) {
            addToBoard();
        } else if (command.equals("s")) {
            saveBoard();
        } else if (command.equals("l")) {
            loadBoard();
        } else if (command.equals("r")) {
            board = new SetOfCell();
        }  else if (command.equals("t")) {
            setTime();
        } else if (command.equals("n")) {
            board.nextCells();
            drawList();
            System.out.println();
        } else {
            System.out.println("Please input a proper string.");
        }
    }

    //Modifies: this
    //Effects: sets time to specified values depending on input
    private void setTime() {
        System.out.println("Set speed to normal (type 1), double speed (type 2) or triple speed (type 3)?");
        int choice = input.nextInt();
        switch (choice) {
            case 1: time = 1000;
                System.out.println("Speed set to normal.");
                break;
            case 2: time = 500;
                System.out.println("Speed set to double.");
                break;
            case 3: time = 333;
                System.out.println("Speed set to triple.");
                break;
            default:
                System.out.println("Please input a valid number.");
                setTime();
                break;
        }
    }

    //Modifies: this
    //Effects: adds a cell to the board given input
    private void addToBoard() {
        int x;
        int y;
        System.out.println("\n Input an x value from 0 to 9.");
        x = input.nextInt();
        System.out.println("\n Input an y value from 0 to 9.");
        y = input.nextInt();
        board.addCell(x,y);
        drawList();
    }

    //Effects: prints the board to the console
    public Runnable drawList() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (board.contains(j,i)) {
                    System.out.print(ANSI_GREEN + " X " + ANSI_RESET);
                } else {
                    System.out.print(" X ");
                }
            }
            System.out.println("");
        }
        return null;
    }

    // EFFECTS: saves the workroom to file
    private void saveBoard() {
        try {
            jsonWriter.open();
            jsonWriter.write(board);
            jsonWriter.close();
            System.out.println("Saved board to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadBoard() {
        try {
            board = jsonReader.read();
            System.out.println("Loaded board from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}