import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TypingTutor extends JFrame {
    private JTextArea displayArea;
    private JPanel keyboardPanel;
    private JButton[] keys;
    private WordManager wordManager;

    public TypingTutor() {
        super("Typing Tutor");
        wordManager = new WordManager();
        initializeUI();
        initializeKeyboard();
    }

    private void initializeUI() {
        setSize(1000, 600);
        setLocationRelativeTo(null); // Centraliza a janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        displayArea = new JTextArea(wordManager.getCurrentWord());
        displayArea.setEditable(false);
        displayArea.setFont(new Font("SansSerif", Font.BOLD, 28));
        displayArea.setForeground(Color.BLACK);
        displayArea.setBackground(Color.WHITE);
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);
        displayArea.setOpaque(true);
        displayArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        displayArea.setAlignmentY(Component.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setPreferredSize(new Dimension(800, 150));
        add(scrollPane, BorderLayout.CENTER);

        keyboardPanel = new JPanel(new GridLayout(3, 10));
        keyboardPanel.setPreferredSize(new Dimension(800, 150));
        add(keyboardPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void initializeKeyboard() {
        String[] keyNames = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
                             "A", "S", "D", "F", "G", "H", "J", "K", "L", ";",
                             "Z", "X", "C", "V", "B", "N", "M", ",", ".", "/"};

        keys = new JButton[keyNames.length];
        for (int i = 0; i < keyNames.length; i++) {
            keys[i] = new JButton(keyNames[i]);
            keys[i].setFont(new Font("SansSerif", Font.BOLD, 16));
            keyboardPanel.add(keys[i]);
        }

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                unhighlightKey(e.getKeyChar());
            }
        });
    }

    private void handleKeyPress(char keyChar) {
        if (wordManager.isCorrectCharacter(keyChar)) {
            displayArea.setForeground(Color.GREEN);
            updateText(buildDisplayText());
            if (wordManager.isWordComplete()) {
                wordManager.generateNewWord();
                updateText(wordManager.getCurrentWord());
            }
        } else {
            displayArea.setForeground(Color.RED);
            wordManager.resetCurrentPosition();
            updateText(buildDisplayText());
        }
        highlightKey(keyChar);
    }

    private String buildDisplayText() {
        String text = wordManager.getCurrentWord();
        int pos = wordManager.getCurrentPosition();
        return text.substring(0, pos) + "_" + text.substring(pos);
    }

    private void highlightKey(char keyChar) {
        for (JButton key : keys) {
            if (key.getText().equalsIgnoreCase(String.valueOf(keyChar))) {
                key.setBackground(Color.YELLOW);
            }
        }
    }

    private void unhighlightKey(char keyChar) {
        for (JButton key : keys) {
            if (key.getText().equalsIgnoreCase(String.valueOf(keyChar))) {
                key.setBackground(null);
            }
        }
    }

    private void updateText(String text) {
        displayArea.setText(text);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TypingTutor();
            }
        });
    }
}
