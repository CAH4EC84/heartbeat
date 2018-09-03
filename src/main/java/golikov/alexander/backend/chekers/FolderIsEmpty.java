package golikov.alexander.backend.chekers;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class FolderIsEmpty {
    private final static Logger logger = LoggerFactory.getLogger(FolderIsEmpty.class);

    public static Boolean isAlive(String path, LocalDateTime lastSuccessCheck) {
        try {
            if ( Files.list(Paths.get(path)).count()== 0)
                return true;
        } catch (IOException e) {
            logger.error(ExceptionUtils.getMessage(e));
            return false;
        }
        return false;
    }

}
