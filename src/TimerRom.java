import java.io.*;

public class TimerRom {
    int left;
    int set;

    public TimerRom(int left, int set) {
        this.left = left;
        this.set = set;
    }

    void saveTimerTime() {
        File file = new File("savedTimer");
        FileWriter writer;

        {
            try {
                writer = new FileWriter(file);
                writer.write(left + " " + set);
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    String getTimerTime() {
        File f = new File("savedTimer");
        if (f.exists() && !f.isDirectory()) {
            try {
                FileReader fr = new FileReader(f);
                BufferedReader reader = new BufferedReader(fr);
                return reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }
}
