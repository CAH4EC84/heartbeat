package golikov.alexander.backend.chekers;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class FileIsRefreshing {
    private final static Logger logger = LoggerFactory.getLogger(FileIsRefreshing.class);

    public static Boolean isAlive(String path, LocalDateTime lastSuccessCheck) {
        try {
            LocalDateTime fileModDate = LocalDateTime.ofInstant(
                    Files.getLastModifiedTime(Paths.get(path)).toInstant(),ZoneId.systemDefault());
            if (fileModDate.isAfter(lastSuccessCheck)) {
                return true;
            }
        } catch (IOException e) {
            logger.error(ExceptionUtils.getMessage(e));
            return false;
        }
        return false;
    }
}
