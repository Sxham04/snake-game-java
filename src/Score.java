package proj.snake.src;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Score {
    private int score;

    // constructor
    public Score(){
        this.score=0;
    }

    // increase score
    public void increaseScore(){
        this.score++;
    }

    // reset Score
    public void resetScore(){
        this.score=0;
    }

    // to return the score value to the Gameplay display
    public int getScore(){
        return this.score;
    }

    // Function to get HighScore
    public String getHighScore() {
        FileReader readFile = null;
        BufferedReader reader = null;
        try {
            // ReadFile highscore.dat
            readFile = new FileReader("highscore.dat");
            reader = new BufferedReader(readFile);

            String line = reader.readLine();
            String allLines = line;

            while (line != null) {
                // Read line by line
                line = reader.readLine();
                // This is for error handling
                if (line == null)
                    break;
                // Combine the lines
                allLines = allLines.concat("\n" + line);
            }

            // return String that is exactly like the content of highscore.dat
            return allLines;
        }
        // If highscore.dat doesn't exist
        catch (Exception e) {
            return "0\n0\n0\n0\n0\n0\n0\n0\n0\n0";
        } finally {
            try {
                // Close the reader
                if (reader != null)
                    reader.close();
            } // If an exception occurs
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // function to sort the high score
    public void sortHighScore() {
        FileReader readFile = null;
        BufferedReader reader = null;
        FileWriter writeFile = null;
        BufferedWriter writer = null;
        List<Integer> list = new ArrayList<Integer>();
        try {
            readFile = new FileReader("highscore.dat");
            reader = new BufferedReader(readFile);

            String line = reader.readLine();

            // Move the content of highscore.dat to an ArrayList
            while (line != null) {
                list.add(Integer.parseInt(line));
                line = reader.readLine();
            }

            // Sort the array list
            Collections.sort(list);

            // Reverse to make it descending
            Collections.reverse(list);

            // Write the sorted scores to highscore.dat
            writeFile = new FileWriter("highscore.dat");
            writer = new BufferedWriter(writeFile);

            int size = list.size();

            // Eventually, only the top 10 scores will be written back
            for (int i = 0; i < 10; i++) {
                // This is to fill the remaining values with 0
                if (i > size - 1) {
                    String def = "0";
                    writer.write(def);
                } else { // Take them one by one from the list
                    String str = String.valueOf(list.get(i));
                    writer.write(str);
                }
                if (i < 9) {// This prevents creating a blank line at the end of the file
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            return;
        } finally {
            try {
                // Close the reader
                if (reader != null)
                    reader.close();
                // Close the writer
                if (writer != null)
                    writer.close();
            } // If an exception occurs
            catch (IOException e) {
                return;
            }
        }

    }

    // Function to write a new score to the file
    public void saveNewScore() {
        String newScore = String.valueOf(this.getScore());

        // Create file to save the highscore
        File scoreFile = new File("highscore.dat");

        // If the highscore.dat file does not exist
        if (!scoreFile.exists()) {
            try {
                // Create a new file
                scoreFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileWriter writeFile = null;
        BufferedWriter writer = null;

        try {
            // Write the new score to the file
            writeFile = new FileWriter(scoreFile, true);
            writer = new BufferedWriter(writeFile);
            writer.write(newScore);
        } catch (Exception e) {
            return;
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (Exception e) {
                return;
            }
        }

    }
}