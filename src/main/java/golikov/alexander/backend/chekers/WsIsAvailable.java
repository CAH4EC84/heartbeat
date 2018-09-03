package golikov.alexander.backend.chekers;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

public class WsIsAvailable {
    private final static Logger logger = LoggerFactory.getLogger(WsIsAvailable.class);

    public static Boolean isAlive (String path, LocalDateTime lastSuccessCheck) {

        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if (con.getResponseMessage().equals("OK")) {
                con.disconnect();
                return true;
            }
            return false;
        } catch (IOException e) {
            logger.error(ExceptionUtils.getMessage(e));
//            e.printStackTrace();
            return false;
        }
    }

}
