import java.io.*;
import java.net.*;
import java.util.HashSet;

public class ClientHandler extends Thread {

    private static HashSet<String> attemptedStudents = new HashSet<>();
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String name = in.readUTF().trim();

            synchronized (attemptedStudents) {
                if (attemptedStudents.contains(name)) {
                    out.writeUTF("You have already taken this quiz.\nOnly one attempt is allowed.");
                    socket.close();
                    return;
                } else {
                    attemptedStudents.add(name);
                }
            }

            String[] questions = {
                    "What is 2 + 2?",
                    "Capital city of Ethiopia?",
                    "Java is a ____ language?",
                    "Which one is NOT OOP concept?",
                    "Which keyword is used for inheritance?"
            };

            String[][] options = {
                    { "A) 3", "B) 4", "C) 5" },
                    { "A) Nairobi", "B) Addis Ababa", "C) Cairo" },
                    { "A) Procedural", "B) Assembly", "C) Object-Oriented" },
                    { "A) Encapsulation", "B) Polymorphism", "C) Compilation" },
                    { "A) extends", "B) import", "C) package" }
            };

            String[] answers = { "B", "B", "C", "C", "A" };

            int score = 0;

            for (int i = 0; i < questions.length; i++) {
                out.writeUTF(questions[i]);
                out.writeUTF(options[i][0]);
                out.writeUTF(options[i][1]);
                out.writeUTF(options[i][2]);

                String userAnswer = in.readUTF();
                if (userAnswer.equalsIgnoreCase(answers[i])) {
                    score++;
                }
            }

            out.writeUTF("Quiz Finished!\nStudent: " + name +
                    "\nScore: " + score + "/5");

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
