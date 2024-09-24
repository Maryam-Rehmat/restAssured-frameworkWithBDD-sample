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


}
