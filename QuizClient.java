import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.util.ArrayList;
import java.util.List;

public class QuizClient extends JFrame {

    JLabel titleLabel, questionLabel;
    JRadioButton a, b, c;
    ButtonGroup group;
    JButton submit;

    DataInputStream in;
    DataOutputStream out;

    int questionIndex = 0;

    public QuizClient() {
        setTitle("🌸 Online Quiz 🌸");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ---- Main panel ----
        JPanel panel = new JPanel(new GridLayout(7, 1, 15, 15));
        panel.setBackground(new Color(255, 228, 235)); // Light pink
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));

        // ---- Title ----
        titleLabel = new JLabel("🌸 Online Quiz 🌸", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(180, 70, 120));

        // ---- Question ----
        questionLabel = new JLabel("Waiting for question...", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        questionLabel.setForeground(new Color(120, 50, 100));

        // ---- Options ----
        a = createOptionButton();
        b = createOptionButton();
        c = createOptionButton();

        group = new ButtonGroup();
        group.add(a);
        group.add(b);
        group.add(c);

        // ---- Submit button ----
        submit = new JButton("Submit Answer");
        submit.setFont(new Font("Segoe UI", Font.BOLD, 20));
        submit.setBackground(new Color(220, 120, 160));
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);
        submit.setBorder(new LineBorder(new Color(180, 70, 120), 2, true));

        panel.add(titleLabel);
        panel.add(questionLabel);
        panel.add(a);
        panel.add(b);
        panel.add(c);
        panel.add(new JLabel()); // spacer
        panel.add(submit);

        add(panel);

        connectToServer();

        submit.addActionListener(e -> sendAnswer());
    }

    // ---- Create a styled radio button ----
    private JRadioButton createOptionButton() {
        JRadioButton btn = new JRadioButton();
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        btn.setBackground(new Color(255, 228, 235));
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(new Color(220, 120, 160), 2, true));
        btn.setOpaque(true);
        return btn;
    }

    // ---- Connect to server with beautiful name input ----
    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 5000);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            // ---- Custom JPanel for name input ----
            JPanel namePanel = new JPanel(new BorderLayout(10, 10));
            namePanel.setBackground(new Color(255, 228, 235));

            JLabel nameLabel = new JLabel("Enter Your Name:");
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
            nameLabel.setForeground(new Color(180, 70, 120));

            JTextField nameField = new JTextField();
            nameField.setFont(new Font("Segoe UI", Font.PLAIN, 22));
            nameField.setPreferredSize(new Dimension(350, 50));
            nameField.setBorder(new LineBorder(new Color(180, 70, 120), 2, true));

            namePanel.add(nameLabel, BorderLayout.NORTH);
            namePanel.add(nameField, BorderLayout.CENTER);

            int option = JOptionPane.showConfirmDialog(
                    this,
                    namePanel,
                    "🌸 Welcome to Online Quiz 🌸",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (option != JOptionPane.OK_OPTION || nameField.getText().trim().isEmpty()) {
                System.exit(0);
            }

            out.writeUTF(nameField.getText().trim());

            loadQuestion();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to connect to server");
            e.printStackTrace();
        }
    }

    private void loadQuestion() throws IOException {
        questionLabel.setText(in.readUTF());
        a.setText(in.readUTF());
        b.setText(in.readUTF());
        c.setText(in.readUTF());
        group.clearSelection();
    }

    // ---- Fancy pink result box with confetti ----
    private void sendAnswer() {
        try {
            if (a.isSelected())
                out.writeUTF("A");
            else if (b.isSelected())
                out.writeUTF("B");
            else if (c.isSelected())
                out.writeUTF("C");
            else {
                JOptionPane.showMessageDialog(this, "Please select an answer");
                return;
            }

            questionIndex++;

            if (questionIndex < 5) {
                loadQuestion();
            } else {
                String resultText = in.readUTF(); // e.g., "Student: John\nScore: 5/5"

                // Determine score
                final int finalScore;
                if (resultText.contains("/5")) {
                    String[] parts = resultText.split("/5");
                    finalScore = Integer.parseInt(parts[0].replaceAll("[^0-9]", ""));
                } else {
                    finalScore = 0;
                }

                // ---- Result Panel ----
                JPanel resultPanel = new JPanel() {
                    List<int[]> confetti = new ArrayList<>();
                    int width = 500, height = 220;

                    {
                        setPreferredSize(new Dimension(width, height));
                        setBackground(new Color(255, 228, 235));

                        // Initialize confetti if high score
                        if (finalScore >= 4) {
                            for (int i = 0; i < 100; i++) {
                                confetti.add(new int[] { (int) (Math.random() * width),
                                        (int) (Math.random() * height),
                                        (int) (Math.random() * 10 + 5),
                                        (int) (Math.random() * 10 + 5) });
                            }

                            // Animation timer
                            new Timer(50, e -> {
                                for (int[] c : confetti) {
                                    c[1] += 5; // fall speed
                                    if (c[1] > height)
                                        c[1] = 0; // reset top
                                }
                                repaint();
                            }).start();
                        }
                    }

                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        // Gradient background
                        GradientPaint gp;
                        if (finalScore >= 4) {
                            // Pink gradient for high score
                            gp = new GradientPaint(0, 0, new Color(255, 182, 193),
                                    0, height, new Color(255, 192, 203));
                        } else {
                            gp = new GradientPaint(0, 0, new Color(255, 220, 230),
                                    0, height, new Color(255, 240, 245));
                        }
                        g2.setPaint(gp);
                        g2.fillRoundRect(0, 0, width, height, 30, 30);

                        // Shadow
                        g2.setColor(new Color(200, 150, 170, 100));
                        g2.fillRoundRect(5, 5, width - 10, height - 10, 30, 30);

                        // Draw confetti
                        if (finalScore >= 4) {
                            for (int[] c : confetti) {
                                g2.setColor(new Color((int) (Math.random() * 255),
                                        (int) (Math.random() * 100 + 50),
                                        (int) (Math.random() * 255)));
                                g2.fillOval(c[0], c[1], c[2], c[3]);
                            }
                        }
                    }
                };

                // ---- Result Label ----
                String displayText = resultText;
                if (finalScore >= 4)
                    displayText += " 🎉 Excellent! 🎉";

                JLabel resultLabel = new JLabel(
                        "<html><center>" + displayText.replaceAll("\n", "<br>") + "</center></html>");
                resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
                resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
                if (finalScore >= 4)
                    resultLabel.setForeground(new Color(255, 105, 180)); // hot pink
                else
                    resultLabel.setForeground(new Color(180, 70, 120));

                resultPanel.setLayout(new BorderLayout());
                resultPanel.add(resultLabel, BorderLayout.CENTER);

                // ---- Show Dialog ----
                JOptionPane.showMessageDialog(this, resultPanel, "🌸 Quiz Result 🌸", JOptionPane.PLAIN_MESSAGE);

                System.exit(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizClient().setVisible(true));
    }
}
