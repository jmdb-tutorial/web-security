package jmdbtutorial.websecurity.platform;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;

public class LogbackConfiguration {

    public static final String STANDARD_OPS_FORMAT = "[%date{yyyy-mm-dd'T'hh:MM:ss.SSSZZ (z)]} %-6level %-35logger{35} - %message%n";

    public static void initialiseConsoleLogging(Level level, String pattern) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);

        root.setLevel(level);
        root.setAdditive(true);

        addConsoleAppender(root, createPatternLayoutEncoder(pattern, context), "console");

        StatusPrinter.printInCaseOfErrorsOrWarnings(context);
    }

    private static void addConsoleAppender(ch.qos.logback.classic.Logger logger, Encoder encoder, String consoleAppenderName) {


        Appender<ILoggingEvent> currentAppender = logger.getAppender(consoleAppenderName);

        if (currentAppender != null) {
            if (ConsoleAppender.class.isAssignableFrom(currentAppender.getClass())) {
                ConsoleAppender<ILoggingEvent> consoleAppender = (ConsoleAppender<ILoggingEvent>) currentAppender;
                consoleAppender.stop();
                consoleAppender.setEncoder(encoder);
                consoleAppender.start();
            } else {
                currentAppender.stop();
                logger.detachAppender(currentAppender);
                Appender<ILoggingEvent> consoleAppender = createConsoleAppender(logger, consoleAppenderName, encoder);
                logger.addAppender(consoleAppender);
                consoleAppender.start();
            }
        } else {
            Appender<ILoggingEvent> consoleAppender = createConsoleAppender(logger, consoleAppenderName, encoder);
            logger.addAppender(consoleAppender);
            consoleAppender.start();
        }
    }

    private static Appender<ILoggingEvent> createConsoleAppender(ch.qos.logback.classic.Logger logger, String consoleAppenderName, Encoder encoder) {

        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<ILoggingEvent>();
        consoleAppender.setName(consoleAppenderName);
        consoleAppender.setEncoder(encoder);
        consoleAppender.setContext(logger.getLoggerContext());

        return consoleAppender;
    }
    private static PatternLayoutEncoder createPatternLayoutEncoder(String pattern, LoggerContext loggerContext) {
        PatternLayoutEncoder layoutEncoder = new PatternLayoutEncoder();
        layoutEncoder.setPattern(pattern);
        layoutEncoder.setContext(loggerContext);
        layoutEncoder.start();
        return layoutEncoder;
    }



}