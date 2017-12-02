import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class UserInterface extends JPanel {
  final private JTextField[] board;

  private UserInterface() {
    this.board = new JTextField[81];
    DocumentFilter docFilter = new DocumentFilter() {
      @Override
      public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (fb.getDocument().getLength() - length + text.length() <= 1 && text.matches("[1-9]") || text.length() == 0) {
          super.replace(fb, offset, length, text.toUpperCase(), attrs);
        }
      }
    };
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
    clearBtn.setBounds(210, 400, 100, 30);
    clearBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        for (JTextField tf : board) {
          tf.setText("");
        }
      }
    });
    this.add(clearBtn);

    JButton solveBtn = new JButton("Solve");
    solveBtn.setBounds(90, 400, 100, 30);
    solveBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        solveBtn.setEnabled(false);
        clearBtn.setEnabled(false);

        byte[][] boardValues = new byte[9][9];
        for (int i = 0; i < board.length; i++) {
          String text = board[i].getText();
          boardValues[i / 9][i % 9] = text.length() != 0 ? Byte.parseByte(text) : (byte) 0;
        }

        try {
          Sudoku sudoku = new Sudoku(boardValues);
          if (sudoku.solve()) {
            boardValues = sudoku.getBoard();
            for (byte row = 0; row < boardValues.length; row++) {
              for (byte col = 0; col < boardValues[row].length; col++) {
                board[row * 9 + col].setText(String.valueOf(boardValues[row][col]));
              }
            }
          } else {
            throw new RuntimeException("Sudoku could not be solved.");
          }
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        solveBtn.setEnabled(true);
        clearBtn.setEnabled(true);
      }
    });
    this.add(solveBtn);

    this.setPreferredSize(new Dimension(400, 450));
    this.setLayout(null);
  }

  static void showUserInterface() {
    JFrame frame = new JFrame("Sudoku Solver");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(new UserInterface());
    frame.pack();
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

}

