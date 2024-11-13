import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

public class Exam extends JFrame implements ActionListener {
    JTextArea questionArea; // Using JTextArea to allow multi-line questions
    JRadioButton jb[] = new JRadioButton[4];
    JButton b1, b2;
    ButtonGroup bg;
    int count = 0, current = 0;
    ArrayList<Integer> bookmarkedQuestions = new ArrayList<>();
    String questions[][] = {
        {"Identify the correct extension of the user-defined header file in C++", ".cpp", ".hg", ".h", ".hf"},
        {"Identify the incorrect constructor type.", "friend constructor", "default constructor", "parameterized constructor", "copy constructor"},
        {"C++ uses which approach?", "right-left", "top-down", "left-right", "bottom-top"},
        {"Which of the following data type is supported in C++ but not in C?", "int", "bool", "double", "float"},
        {"Identify the correct syntax for declaring arrays in C++", "array arr[10]", "array {10}", "int arr[10]", "int arr"},
        {"Identify the correct example for a pre-increment operator.", "++n", "n++", "--n", "+n"},
        {"Which of the following loops is best when we know the number of iterations?", "while loop", "do-while", "for loop", "all of the above"},
        {"Identify the scope resolution operator.", ":", "::", "?", "none"},
        {"Identify the correct function from which the execution of C++ program starts?", "new()", "start()", "pow()", "main()"},
        {"Identify the size of int datatype in C++.", "1 byte", "2 bytes", "4 bytes", "depends on compiler"}
    };
    int answers[] = {2, 0, 2, 1, 2, 0, 2, 1, 3, 3};

    DefaultListModel<String> bookmarkListModel;
    JList<String> bookmarkList;

    Exam(String s) {
        super(s);
        setLayout(new BorderLayout());

        JLabel bgLabel = new JLabel();
        ImageIcon bgImage = new ImageIcon("bg.jpg"); // Update this path to your image file
        Image img = bgImage.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        bgLabel.setIcon(new ImageIcon(img));
        bgLabel.setLayout(null);

        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(null);
        questionPanel.setOpaque(false);

        // Using JTextArea for multi-line question text
        questionArea = new JTextArea();
        questionArea.setFont(new Font("Arial", Font.BOLD, 18));
        questionArea.setForeground(Color.WHITE);
        questionArea.setOpaque(false);
        questionArea.setEditable(false);
        questionArea.setLineWrap(true); // Enable line wrap
        questionArea.setWrapStyleWord(true); // Wrap on word boundaries
        questionArea.setBounds(50, 50, 500, 60); // Adjusted for multi-line display
        questionPanel.add(questionArea);

        bg = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            jb[i] = new JRadioButton();
            jb[i].setFont(new Font("Arial", Font.PLAIN, 16));
            jb[i].setForeground(Color.WHITE);
            jb[i].setOpaque(false);
            jb[i].setBounds(50, 120 + (i * 40), 500, 30); // Adjusted for spacing below the question
            questionPanel.add(jb[i]);
            bg.add(jb[i]);
        }

        b1 = new JButton("Next");
        b1.setBounds(150, 300, 100, 40);
        b1.addActionListener(this);
        questionPanel.add(b1);

        b2 = new JButton("Bookmark");
        b2.setBounds(300, 300, 100, 40);
        b2.addActionListener(this);
        questionPanel.add(b2);

        bgLabel.add(questionPanel);
        questionPanel.setBounds(0, 0, 600, 600);

        bookmarkListModel = new DefaultListModel<>();
        bookmarkList = new JList<>(bookmarkListModel);
        bookmarkList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookmarkList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedIndex = bookmarkList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        current = bookmarkedQuestions.get(selectedIndex);
                        setQuestion();
                    }
                }
            }
        });

        JScrollPane bookmarkScrollPane = new JScrollPane(bookmarkList);
        bookmarkScrollPane.setPreferredSize(new Dimension(100, 600));

        add(bgLabel, BorderLayout.CENTER);
        add(bookmarkScrollPane, BorderLayout.EAST);

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        setQuestion();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            if (checkAnswer())
                count++;
            current++;
            if (current < questions.length) {
                setQuestion();
            } else {
                b1.setEnabled(false);
                b2.setText("Result");
            }
        }

        if (e.getSource() == b2) {
            if (b2.getText().equals("Result")) {
                JOptionPane.showMessageDialog(this, "You scored " + count + " out of " + questions.length);
                System.exit(0);
            } else {
                if (!bookmarkedQuestions.contains(current)) {
                    bookmarkedQuestions.add(current);
                    bookmarkListModel.addElement("Question " + (current + 1));
                    JOptionPane.showMessageDialog(this, "Question " + (current + 1) + " bookmarked.");
                }
                if (current < questions.length - 1) {
                    current++;
                    setQuestion();
                } else {
                    JOptionPane.showMessageDialog(this, "No more questions to display.");
                }
            }
        }
    }

    void setQuestion() {
        bg.clearSelection();
        questionArea.setText("Q" + (current + 1) + ": " + questions[current][0]);
        for (int i = 0; i < 4; i++) {
            jb[i].setText(questions[current][i + 1]);
        }
    }

    boolean checkAnswer() {
        return jb[answers[current]].isSelected();
    }

    public static void main(String[] args) {
        new Exam("Exam System");
    }
}
