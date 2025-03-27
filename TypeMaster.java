import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class TypeMaster implements ActionListener, KeyListener {

    JFrame frame;
    JTextField field;
    JLabel word, intro, time;
    JPanel startpane, mainpane;
    JButton startbtn, nextbtn;
    int score;
    int itime;
    String wordstr;
    Timer timer;
    boolean running;

    public TypeMaster() {
        frame = new JFrame("TypeMaster2");
        field = new JTextField();
        word = new JLabel();
        intro = new JLabel("Press the button to start typing!", SwingConstants.CENTER);
        time = new JLabel("Time: ", SwingConstants.CENTER);
        startpane = new JPanel();
        mainpane = new JPanel();
        startbtn = new JButton("START");
        nextbtn = new JButton("NEXT");
        score = 0;
        itime = 6;
        wordstr = "";
        running = false;

        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        startpane.setLayout(new BorderLayout());
        startpane.add(intro, BorderLayout.CENTER);
        startpane.add(startbtn, BorderLayout.SOUTH);

        frame.add(startpane, BorderLayout.CENTER);
        startbtn.addActionListener(this);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startbtn) {
            startGame();
        } else if (e.getSource() == nextbtn) {
            check();
        }
    }

    public void startGame() {
        frame.remove(startpane);
        frame.setSize(300, 200);
        mainpane.setLayout(new GridLayout(4, 1));
        mainpane.add(time);
        mainpane.add(word);
        mainpane.add(field);
        mainpane.add(nextbtn);

        frame.add(mainpane, BorderLayout.CENTER);
        nextbtn.addActionListener(this);
        field.addKeyListener(this);
        
        frame.revalidate();
        frame.repaint();

        generateWord();
    }

    public void generateWord() {
        wordstr = WordGenerator.generateWord();
        word.setText(wordstr);
        field.setText("");
        startTimer();
    }

    public void startTimer() {
        if (timer != null) {
            timer.cancel();
        }
        itime = 5; // Reset time to 5
        running = true;
    
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (itime > 0) {
                    time.setText("Time: " + itime);
                    itime--;
                } else {
                    running = false;
                    timer.cancel();
                    generateWord(); // Reset the timer and get a new word
                }
            }
        }, 0, 1000);
    }
    

    public void check() {
        if (field.getText().equals(wordstr)) {
            score++;
            generateWord();
        } else {
            int a = JOptionPane.showConfirmDialog(frame, "Wrong Word!\nScore: " + score + "\nRestart?");
            if (a == JOptionPane.YES_OPTION) {
                frame.dispose();
                new TypeMaster();
            } else {
                System.exit(0);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            check();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        new TypeMaster();
    }
}
