import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A graphical user interface to the sudoku solver.
 */
class UserInterface extends JPanel {
  /**
   * Array of the input fields, each representing a cell.
   */
  final private JTextField[] board;

  /**
   * The default constructor.
   */
  private UserInterface() {
    this.board = new JTextField[81];

    // accept one digit per cell
    DocumentFilter docFilter = new DocumentFilter() {
      @Override
      public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (fb.getDocument().getLength() - length + text.length() <= 1 && text.matches("[1-9]") || text.length() == 0) {
          super.replace(fb, offset, length, text.toUpperCase(), attrs);
        }
      }
    };

    // init input text fields
    for (int i = 0; i < this.board.length; i++) {
      this.board[i] = new JTextField();
      this.board[i].setBounds(20 + i / 9 * 40, 20 + i % 9 * 40, 40, 40);
      AbstractDocument doc = (AbstractDocument) this.board[i].getDocument();
      doc.setDocumentFilter(docFilter);
      this.board[i].setFont(new Font("SansSerif", Font.BOLD, 20));
      this.board[i].setHorizontalAlignment(JTextField.CENTER);
      this.add(this.board[i]);
    }

    JButton clearBtn = new JButton("Clear");
    JButton solveBtn = new JButton("Solve");

    // clear button
    clearBtn.setBounds(210, 400, 100, 30);
    clearBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        solveBtn.setEnabled(true);
        for (JTextField tf : board) {
          tf.setEditable(true);
          tf.setText("");
        }
      }
    });
    this.add(clearBtn);

    // solve button
    solveBtn.setBounds(90, 400, 100, 30);
    solveBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        byte[][] boardValues = new byte[9][9];
        for (int i = 0; i < board.length; i++) {
          String text = board[i].getText();
          boardValues[i / 9][i % 9] = text.length() != 0 ? Byte.parseByte(text) : (byte) 0;
        }

        try {
          Sudoku sudoku = new Sudoku(boardValues);
          if (sudoku.solve()) {
            solveBtn.setEnabled(false);
            boardValues = sudoku.getBoard();
            for (byte row = 0; row < boardValues.length; row++) {
              for (byte col = 0; col < boardValues[row].length; col++) {
                int index = row * 9 + col;
                board[index].setEditable(false);
                board[index].setText(String.valueOf(boardValues[row][col]));
              }
            }
          } else {
            throw new RuntimeException("Sudoku could not be solved.");
          }
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    this.add(solveBtn);

    this.setPreferredSize(new Dimension(400, 450));
    this.setLayout(null); // absolute positioning
  }

  /**
   * Static method to show the ui.
   */
  static void showUserInterface() {
    JFrame frame = new JFrame("Sudoku Solver");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(new UserInterface());
    frame.pack();
    frame.setResizable(false);
    frame.setLocationRelativeTo(null); // center
    frame.setVisible(true);
  }

}

