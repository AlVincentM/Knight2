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
    private static final String COLS = "ABCDEFGH";

    Board() {
        initializeGui();
    }

    public final void initializeGui() {
        // set up the main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));

        gui.add(new JLabel(""), BorderLayout.LINE_START);

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

                        tile.tile.setIcon(new ImageIcon("img/knight.png"));
                        System.out.println("( " + tile.getXPosition() + ", " + tile.getYPosition() + " )");
                        calculateLegalMoves(tile.getXPosition(), tile.getYPosition(), chessBoardSquares);
                    }
                });

                if ((jj % 2 == 1 && ii % 2 == 1)
                        //) {
                        || (jj % 2 == 0 && ii % 2 == 0)) {
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

    public void calculateLegalMoves(int xCoords, int yCoords, Tile[][] chessBoardSquares) {
        int knightXMoves[] = {2, 1, -1, -2, -2, -1,  1,  2};
        int knightYMoves[] = {1, 2,  2,  1, -1, -2, -2, -1};

        int possibleXCoords, possibleYCoords;

        ArrayList<Integer> legalXCoords = new ArrayList<>();
        ArrayList<Integer> legalYCoords = new ArrayList<>();

        for(int i = 0; i < 8; i++) {

            possibleXCoords = xCoords + knightXMoves[i];
            possibleYCoords = yCoords + knightYMoves[i];

            if((possibleXCoords >= 0 || possibleXCoords <= 7) || (possibleYCoords >= 0 || possibleYCoords <= 7)) {

                legalXCoords.add(possibleXCoords);
                legalYCoords.add(possibleYCoords);
            }
        }

        for( int x : legalXCoords ) {
            System.out.print(x + " ");
        }
        System.out.println();
        for( int y: legalYCoords ) {
            System.out.print(y + " ");
        }

        if (legalXCoords.size() == legalYCoords.size()) {
            for (int i = 0; i < legalXCoords.size(); i++) {

                int x = legalXCoords.get(i);
                int y = legalYCoords.get(i);

                chessBoardSquares[x][y].tile.setIcon(new ImageIcon("img/green_dot.png"));

            }
        }


    }

    public final JComponent getChessBoard() {
        return chessBoard;
    }

    public final JComponent getGui() {
        return gui;
    }

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
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }
}