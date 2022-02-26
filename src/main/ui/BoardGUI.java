package ui;

import model.EventLog;
import model.Event;
import model.SetOfCell;
import persistence.JsonReader;
import persistence.JsonWriter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;

import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class BoardGUI extends JFrame implements ActionListener, MouseListener, ComponentListener, MouseMotionListener {
    //GUI for the whole project representing a board
    private final int scale = 20;
    private final JMenuItem stepItem = new JMenuItem("Step");
    private final JMenuItem saveItem = new JMenuItem("Save");
    private final JMenuItem loadItem = new JMenuItem("Load");
    private JPanel boardPanel = new JPanel();
    private JPanel buttonPanel;
    private static final String JSON_STORE = "./data/board.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Timer timer;
    private SetOfCell board;

    //Modifies: this
    //Effects: initializes jsonWriter, jsonReader, board, Menu, Frame, BoardPanel, and ButtonPanel
    //         adds boardPanel and buttonPanel to the frame
    public BoardGUI() {
        super("The title");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        board = new SetOfCell();
        initMenu();
        initFrame();
        initBoardPanel();
        initButtonPanel();

        add(boardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //super.windowClosed(e);
                for (Event event: EventLog.getInstance()) {
                    System.out.println(event);
                    System.out.println();
                }
                e.getWindow().dispose();
            }
        });
        revalidate();
        repaint();
    }

    //Modifies: board
    //Effects: if e equals stepItem runs board, e equals saveItem saves board,
    //         e equals loadItem loads board then draws it
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == stepItem) {
            runBoard();
        } else if (e.getSource() == saveItem) {
            saveBoard();
        } else if (e.getSource() == loadItem) {
            loadBoard();
            drawBoard();
        }

    }

    //Modifies: saveItem, loadItem, stepItem,
    //Effects: initializes a Menu bar
    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu runMenu = new JMenu("Run");

        menuBar.add(fileMenu);
        menuBar.add(runMenu);

        setJMenuBar(menuBar);

        saveItem.addActionListener(this);
        loadItem.addActionListener(this);
        stepItem.addActionListener(this);

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        runMenu.add(stepItem);
    }

    //Modifies: this
    //Effects: initializes the frame
    private void initFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000,700);
        setLayout(new BorderLayout());
        setVisible(true);
        setResizable(true);
        setLocationRelativeTo(null);
    }

    //Modifies: boardPanel
    //Effects: initializes boardPanel
    private void initBoardPanel() {
        boardPanel.addMouseListener(this);
        boardPanel.addMouseMotionListener(this);
        boardPanel.addComponentListener(this);
        boardPanel.setBackground(Color.gray);
    }

    //Modifies: buttonPanel, timer
    //Effects: initializes the panel contain the button and timer, creates a new JButton
    private void initButtonPanel() {
        timer = new Timer(100, ae -> runBoard());

        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.white);
        final JButton run = new JButton("START");
        run.addActionListener(e -> {
            String cmd = e.getActionCommand();
            if ("STOP".equals(cmd)) {
                timer.stop();
                run.setText("START");
            } else {
                timer.start();
                run.setText("STOP");
            }
        });

        buttonPanel.add(run);
    }

    //Modifies: board, boardPanel
    //Effects: gets the next iteration of cells and draws the board onto the panel
    private void runBoard() {
        board.setXdimension(boardPanel.getWidth() / scale);
        board.setYdimension(boardPanel.getHeight() / scale);

        board.nextCells();
        drawBoard();
    }

    //Modifies: boardPanel
    //Effects: draws the board onto the board panel using row major order, if the board contains a cell with position
    //         i,j then draws a black square, else draws a gray square
    private void drawBoard() {
        Graphics g = boardPanel.getGraphics();
        for (int i = 0; i < boardPanel.getWidth() / scale; i++) {
            for (int j = 0; j < boardPanel.getHeight() / scale; j++) {
                if (board.contains(i,j)) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.gray);
                }
                g.fillRect(i * scale, j * scale, scale - 1, scale - 1);
            }
        }
        g.dispose();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    //Modifies:this
    //Effects: adds a cell at mouse position to board then draws board
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX() / scale;
        int y = e.getY() / scale;
        board.addCell(x,y);
        drawBoard();

        board.setXdimension(boardPanel.getWidth() / scale);
        board.setYdimension(boardPanel.getHeight() / scale);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    // EFFECTS: saves the workroom to file
    private void saveBoard() {
        try {
            jsonWriter.open();
            jsonWriter.write(board);
            jsonWriter.close();
            EventLog.getInstance().logEvent(
                    new Event("Saved board to " + JSON_STORE));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: board
    // EFFECTS: loads workroom from file
    private void loadBoard() {
        try {
            board = jsonReader.read();
            EventLog.getInstance().logEvent(
                    new Event("Loaded board from " + JSON_STORE));
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        drawBoard();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    //Modifies: board
    //Effects: adds a cell at mouse position to board when dragging the mouse then draws board
    //         uses addCellFunk so that cells don't get removed when it adds cell making for cleaner drag
    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / scale;
        int y = e.getY() / scale;
        board.addCellFunk(x,y);

        board.setXdimension(boardPanel.getWidth() / scale);
        board.setYdimension(boardPanel.getHeight() / scale);
        drawBoard();


    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
