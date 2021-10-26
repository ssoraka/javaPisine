package edu.school21.sockets.app;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Main {

    @Parameter(names = "--port")
    private Integer port;

    public static void main(String[] args) {
        Main main = create(args);

        ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        Server server = context.getBean(Server.class);
        server.run(main.port);
    }

    private static Main create(String[] args) {
        Main main = new Main();

        try {
            JCommander.newBuilder()
                    .addObject(main)
                    .build()
                    .parse(args);
        } catch (ParameterException ex) {
            System.err.println("Error arguments!");
            System.err.println("Valid arguments are:");
            System.err.println("--port=\"number port in range from 1 to 65535\"");
            System.exit(1);
        }

        if (main.port == null || main.port < 1 || main.port > 65535) {
            System.err.println("The port number must be in the range from 1 to 65535!");
            System.exit(1);
        }
        return main;
    }
}