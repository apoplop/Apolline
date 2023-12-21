package test1;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PlayerDataManager {

    private static final String FILE_PATH = "src/res/compteJoueurs/joueurs.txt";
    private Map<String, Integer> playerData = new HashMap<>();

    public PlayerDataManager() {
        loadPlayerData();
    }

    private void loadPlayerData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    playerData.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePlayerData(String playerName, int level) {
        playerData.put(playerName, level);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, Integer> entry : playerData.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPlayerLevel(String playerName) {
        return playerData.getOrDefault(playerName, 1); // Default to level 1 if player not found
    }
}

