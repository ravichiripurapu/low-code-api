package io.lowcode.platform.config;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/*
 * Configures the console and Logstash log appenders from the app properties
 */
@Configuration
public class LoggingConfiguration {

    public LoggingConfiguration(
        @Value("${spring.application.name}") String appName,
        @Value("${server.port}") String serverPort,
        @Value("${logging.use-json-format:false}") boolean useJsonFormat,
        @Value("${logging.logstash.enabled:false}") boolean logstashEnabled,
        @Value("${logging.logstash.host:localhost}") String logstashHost,
        @Value("${logging.logstash.port:5000}") int logstashPort,
        ObjectMapper mapper
    ) throws JsonProcessingException {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        Map<String, String> map = new HashMap<>();
        map.put("app_name", appName);
        map.put("app_port", serverPort);
        String customFields = mapper.writeValueAsString(map);

        if (useJsonFormat) {
            addJsonConsoleAppender(context, customFields);
        }

    }

    public static void addJsonConsoleAppender(LoggerContext context, String customFields) {
        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender();
        consoleAppender.setContext(context);
        consoleAppender.setName("CONSOLE");
        consoleAppender.start();
        context.getLogger("ROOT").detachAppender("CONSOLE");
        context.getLogger("ROOT").addAppender(consoleAppender);
    }
}
