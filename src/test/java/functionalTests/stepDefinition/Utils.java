package functionalTests.stepDefinition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    /**
     * This method returns json from file
     *
     * @return json
     */
    public static String readFromJsonFile(File file) {
        String json = "";
        try {
            json = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            logger.error(Utils.class.getName(), ex);
        }
        return json;
    }

    public static String getPropertyValue(String fileName, String property) {
        String root = System.getProperty("user.dir");
        File file = Paths.get(root, "src", "test", "resources", "config", fileName).toFile();
        Properties properties = new Properties();
        String value = "";
        try {
            InputStream targetStream = new FileInputStream(file);
            properties.load(targetStream);
            value = properties.getProperty(property);
        } catch (FileNotFoundException ex) {
            logger.error(Utils.class.getName(), ex);
        } catch (IOException ex) {
            logger.error(Utils.class.getName(), ex);
        }
        return value;
    }

    public static String getJsonStringFromFile(String objectName, File file) {
        String json = Utils.readFromJsonFile(file);
        String objectResponse = "";
        try {
            JSONObject jsonObj = new JSONObject(json);
            objectResponse = jsonObj.getString(objectName);
        } catch (JSONException ex) {
            logger.error(Utils.class.getName(), ex);
        }
        return objectResponse;
    }

    public static JSONObject getJsonObjectFromFile(String objectName, File file) {
        String json = Utils.readFromJsonFile(file);
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(json).getJSONObject(objectName);
        } catch (JSONException ex) {
            logger.error(Utils.class.getName(), ex);
        }
        return jsonObj;
    }

}
