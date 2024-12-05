package Backend;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DatabaseManager {

    public static JSONArray readJSONFile(String filename) {
        StringBuilder jsonData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }
        } catch (IOException e) {
            return new JSONArray();
        }
        return new JSONArray(jsonData.toString());
    }

    public static boolean writeJSONFile(String filename, JSONArray jsonArray) {
        try (FileWriter file = new FileWriter(filename)) {
            file.write(jsonArray.toString(4)); // Indent with 4 spaces for readability
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
