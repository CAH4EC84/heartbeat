package golikov.alexander.backend.chekers;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDateTime;

public class ServiceIsAvailable {
    private final static Logger logger = LoggerFactory.getLogger(ServiceIsAvailable.class);

    public static Boolean isAlive (String path, LocalDateTime lastSuccessCheck) {
        try (Socket socket = new Socket()) {
            String name = path.substring(0,path.indexOf(":"));
            String port = path.substring(name.length()+1);
            socket.connect(new InetSocketAddress(name, Integer.parseInt(port)));
            if (socket.isConnected() ) {
                socket.close();
                return true ;
            }
            return false;
        } catch (IOException e) {
            logger.error(ExceptionUtils.getMessage(e));
            //e.printStackTrace();
            return false;
        }
    }
}
