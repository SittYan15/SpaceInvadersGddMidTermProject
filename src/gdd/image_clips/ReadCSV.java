package gdd.image_clips;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadCSV {

    // Utility function to load Rectangle array from a CSV file
    public static Rectangle[] loadClipsFromCSV(String filename) {
        ArrayList<Rectangle> rectList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int x = Integer.parseInt(parts[0].trim());
                    int y = Integer.parseInt(parts[1].trim());
                    int w = Integer.parseInt(parts[2].trim());
                    int h = Integer.parseInt(parts[3].trim());
                    rectList.add(new Rectangle(x, y, w, h));
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load " + filename + ": " + e.getMessage());
        }
        return rectList.toArray(new Rectangle[0]);
    }
}
