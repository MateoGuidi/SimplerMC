package mateoguidi.simplermc.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.*;
import java.util.*;

public class TranslationManager {
    private final File pluginFolder;
    private final String defaultLanguage = "en";
    private final List<String> availableLanguages = Arrays.asList("en", "fr");
    private String currentLanguage;
    private Map<String, String> messages;

    public TranslationManager(File pluginFolder) {
        this.pluginFolder = pluginFolder;
        this.messages = new HashMap<>();
        loadConfig();
        loadLanguage();
    }

    //Load config.yaml file or create it if it doesn't exist
    private void loadConfig() {
        File configFile = new File(pluginFolder, "config.yaml");
        if (!configFile.exists()) {
            try {
                File parentFolder = configFile.getParentFile();
                if (parentFolder != null && !parentFolder.exists()) {
                    parentFolder.mkdirs();
                }

                try (FileWriter writer = new FileWriter(configFile)) {
                    writer.write("# Available languages: " + String.join(", ", availableLanguages) + "\n");
                    writer.write("language: " + defaultLanguage + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        String configuredLanguage = config.getString("language", defaultLanguage);
        if (availableLanguages.contains(configuredLanguage)) {
            this.currentLanguage = configuredLanguage;
        } else {
            this.currentLanguage = defaultLanguage;
        }
    }

    //Load a specific language based on config language specified
    private void loadLanguage() {
        messages.clear();
        String langFileName = "locales/" + currentLanguage + ".yaml";
        String defaultFileName = "locales/en.yaml";

        try (InputStream langStream = getClass().getClassLoader().getResourceAsStream(langFileName)) {
            if (langStream == null) {
                System.out.println("[SimplerMC] Language file not found: " + langFileName + ". Using default.");
                loadFromResource(defaultFileName);
            } else {
                loadFromStream(langStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update messages HashMap from language yaml files
    private void loadFromStream(InputStream inputStream) throws IOException {
        FileConfiguration langConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream));
        for (String key : langConfig.getKeys(false)) {
            messages.put(key, langConfig.getString(key));
        }
    }

    // Open a YAML file
    private void loadFromResource(String resourcePath) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (inputStream != null) {
            loadFromStream(inputStream);
        }
    }

    // Get the translation from the given key
    public String getTranslation(String key) {
        return messages.getOrDefault(key, "Missing translation for: " + key);
    }

    //Get current language used
    public String getLanguage() {
        return currentLanguage;
    }

    // List all message in language file
    public void listAllMessages() {
        System.out.println("Available translations:");
        for (String key : messages.keySet()) {
            System.out.println(key + ": " + messages.get(key));
        }
    }
}
