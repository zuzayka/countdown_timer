import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountdownTimer2 {
    private JPanel mainPane;
    private JLabel timerName;
    private JLabel timerWindow;
    private JButton bNew;
    private JButton bStart;
    private JButton bStop;
    private JButton bSet;
    int pressCounter = 0;
    Color colorGreen = new Color(206, 229, 210);
    Color colorPink = new Color(229, 197, 211);

    public CountdownTimer2() {
        bStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                bStopPressed = true;
                TimerRom tR = new TimerRom(timeLeft, timeSet);
                if (bNewPressed) {
                    if (pressCounter % 2 == 0) {
                        timer.start();
                        bStop.setBackground(colorGreen);
                    } else {
                        timer.stop();
                        bStop.setBackground(colorPink);
                        tR.saveTimerTime();
                    }
                } else {
                    if (pressCounter % 2 == 0) {
                        findTimeLeftSet();
                        timer.start();
                        bStop.setBackground(colorGreen);
                    } else {
                        timer.stop();
                        bStop.setBackground(colorPink);
                        tR.saveTimerTime();
                    }
                }
                pressCounter++;
            }
        });
        bNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                bNewPressed = true;
                TimerRom tR = new TimerRom(timeLeft, timeSet);
                if (bSetPressed) {
                    timeLeft = timeSet;
                } else {
                    findTimeLeftSet();
                    timeLeft = timeSet;
                }
                timer.start();
                bStop.setBackground(colorGreen);
                pressCounter++;
            }
        });
        bSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                bSetPressed = true;
                JTextField xField = new JTextField("", 5);
                JTextField yField = new JTextField("00", 5);

                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("задать отсчет часов:"));
                myPanel.add(xField);
                myPanel.add(new JLabel("задать отсчет минут:"));
                myPanel.add(yField);

                int result = JOptionPane.showConfirmDialog(null, myPanel, "ввести часы и минуты обратного таймера", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    int hours = 0;
                    if (!Objects.equals(xField.getText(), "")) {
                        hours = Integer.parseInt(xField.getText());
                    }
                    int minutes = Integer.parseInt(yField.getText());
                    timeSet = (hours * 60 + minutes) * 60 + 59;
                }
            }
        });
    }

    private int timeLeft;
    private int timeSet = 21659;
    boolean bSetPressed = false;
    boolean bStopPressed = false;
    boolean bNewPressed = false;

    private final Timer timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if (timeLeft <= 59) {
                timerWindow.setText("00 : 00");
                timer.stop();
                bStop.setBackground(colorPink);
            } else {
                timerWindow.setText("" + String.format("%02d", timeLeft / 3600) + " : " + String.format("%02d", (timeLeft % 3600) / 60));
                timeLeft--;
            }
        }
    });

    void findTimeLeftSet() {
        TimerRom tR = new TimerRom(timeLeft, timeSet);
        String timeString = tR.getTimerTime();
        if (timeString != null) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(timeString);
            if (m.find()) {
                timeLeft = Integer.parseInt(m.group());
            }
            if (m.find()) {
                timeSet = Integer.parseInt(m.group());
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("      CountdownTimer");
        frame.setContentPane(new CountdownTimer2().mainPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
