package exchangeApi;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Properties;

class Tools {
    private static Properties properties;

    Tools(){
        readProperties();
    }

    private void readProperties() {
        properties = new Properties();
        if (Paths.get("config.properties").toFile().exists()) {
            try (InputStream input = new FileInputStream("config.properties")) {
                properties.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    String getProperty(String value) {
        return properties.getProperty(value);
    }

    String getUrl() {
        return getProperty("base_url");
    }

    String getNameOfWeekDay(String dateToCheck) {
        LocalDate date = LocalDate.parse(dateToCheck, DateTimeFormatter.ofPattern("yyyy-M-d"));
        return date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US);
    }

    String setLeadingZero(Integer numb){
        return String.format("%02d",numb);
    }
}
