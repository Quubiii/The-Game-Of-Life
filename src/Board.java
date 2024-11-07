import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JFrame {
    private final int rows = 30;
    private final int columns = 30;
    private final JButton[][] buttons = new JButton[rows][columns];
    private boolean[][] cellState = new boolean[rows][columns];
    private final CellsLogic cellsLogic = new CellsLogic(rows, columns);
    private Timer timer;

    public Board() {
        setTitle("The Game Of Life");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, columns));

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                JButton button = new JButton();
                button.setOpaque(true);
                button.setBorderPainted(true);
                button.setBackground(Color.WHITE);
                button.setBorder(new LineBorder(Color.GRAY));

                final int r = row;
                final int c = col;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton clickedButton = (JButton) e.getSource();
                        if (clickedButton.getBackground().equals(Color.BLUE)) {
                            clickedButton.setBackground(Color.WHITE);
                            cellState[r][c] = false;
                        } else {
                            clickedButton.setBackground(Color.BLUE);
                            cellState[r][c] = true;
                        }
                    }
                });
                buttons[row][col] = button;
                gridPanel.add(button);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JButton nextStageButton = new JButton("Next Stage");
        nextStageButton.setOpaque(true);
        JButton startStopButton = new JButton("Start Constant Staging");
        startStopButton.setOpaque(true);

        // Action listener for the "Next Stage" button
        nextStageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cellState = cellsLogic.nextGeneration(cellState);
                updateBoard();
            }
        });

        startStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton clickedButton = (JButton) e.getSource();
                if (clickedButton.getText().equalsIgnoreCase("Stop Constant Staging")) {
                    clickedButton.setText("Start Constant Staging");
                    if (timer != null) {
                        timer.stop();
                    }
                } else {
                    clickedButton.setText("Stop Constant Staging");
                    timer = new Timer(250, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            cellState = cellsLogic.nextGeneration(cellState);
                            updateBoard();
                        }
                    });
                    timer.start();
                }
            }
        });

        controlPanel.add(nextStageButton);
        controlPanel.add(startStopButton);

        add(controlPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (cellState[row][col]) {
                    buttons[row][col].setBackground(Color.BLUE);
                } else {
                    buttons[row][col].setBackground(Color.WHITE);
                }
            }
        }
    }
}
