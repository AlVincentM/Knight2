import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Board {

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private Tile[][] chessBoardSquares = new Tile[8][8];
    private JPanel chessBoard;
    private JButton about = new JButton("About");
    private static final String COLS = "ABCDEFGH";

    Board() {
        initializeGui();
    }

    public final void initializeGui() {
        // set up the main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));

        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Knight Moves\n" + 
                    "By: Al Vincent Musa\n" +
                    "This program calculates all valid moves of a knight piece given an x and y coordinate.");
            }
        });
        tools.add(about);

        gui.add(new JLabel(""), BorderLayout.LINE_START);
        gui.add(tools, BorderLayout.PAGE_START);

        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);
        
        ArrayList<Tile> listTiles = new ArrayList<Tile>();

        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {

                Tile tile = new Tile(jj, ii);

                listTiles.add(tile);

                tile.tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {

                        for(Tile tile : listTiles) {
                            tile.tile.setIcon(null);
                        }

                        tile.tile.setIcon(new ImageIcon("../img/knight.png"));

                        calculateLegalMoves(tile.getXPosition(), tile.getYPosition(), chessBoardSquares);
                    }
                });

                if ((jj % 2 == 1 && ii % 2 == 1) || (jj % 2 == 0 && ii % 2 == 0)) {
                    tile.tile.setBackground(Color.WHITE);
                } else {
                    tile.tile.setBackground(Color.BLACK);
                }
                chessBoardSquares[jj][ii] = tile;
            }
        }

        //fill the chess board
        chessBoard.add(new JLabel(""));
        // fill the top row
        for (int ii = 0; ii < 8; ii++) {
            chessBoard.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                    SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                switch (jj) {
                    case 0:
                        chessBoard.add(new JLabel("" + (ii + 1),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[jj][ii].tile);
                }
            }
        }
    }

    /**
     * Calculate all valid moves for the knight chess piece
     * @param xPosition         x-position of the knight
     * @param yPosition         y-position of the knight
     * @param chessBoardSquares chess tiles as array of buttons in a class
     */
    public void calculateLegalMoves(int xPosition, int yPosition, Tile[][] chessBoardSquares) {
        int knightXMoves[] = {2, 1, -1, -2, -2, -1,  1,  2};
        int knightYMoves[] = {1, 2,  2,  1, -1, -2, -2, -1};

        ArrayList<Integer> legalXCoords = new ArrayList<>();
        ArrayList<Integer> legalYCoords = new ArrayList<>();

        int xCoords = xPosition;
        int yCoords = yPosition;
        int possibleXCoords;
        int possibleYCoords;

        for (int i = 0; i < 8; i++) {

            possibleXCoords = xCoords + knightXMoves[i];
            possibleYCoords = yCoords + knightYMoves[i];

            if (xOutOfBounds(possibleXCoords) || yOutOfBounds(possibleYCoords)) {
                continue;
            }

            legalXCoords.add(possibleXCoords);
            legalYCoords.add(possibleYCoords);
        }

        // add green icon to every possible moves
        for (int i = 0; i < legalXCoords.size(); i++) {
            chessBoardSquares[legalXCoords.get(i)][legalYCoords.get(i)].tile.setIcon(new ImageIcon("../img/green_dot.png"));
        }

    }

    /**
     * Checks if x is out of bounds
     * @param  x Current x position of the tile
     * @return   true if out of bound, else if otherwise
     */
    private boolean xOutOfBounds(int x) {
        return ((x < 0) || (x > 7)) ? true : false;
    }

    /**
     * Checks if y is out of bounds
     * @param  y Current y position of the tile
     * @return   true if out of bound, else if otherwise
     */
    private boolean yOutOfBounds(int y){
        return ((y < 0) || (y > 7)) ? true : false;
    }

    public final JComponent getChessBoard() {
        return chessBoard;
    }

    public final JComponent getGui() {
        return gui;
    }

    /**
     *  The main function
     * @param args [description]
     */
    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                Board cb =
                        new Board();

                JFrame f = new JFrame("Knight Moves");
                f.add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setSize(700, 700);
                f.setResizable(false);
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }
}