package com.logging.demo.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {

   public void setLogLevel(String loggerName, Level level) throws IllegalArgumentException {
      Logger logger = this.getLogger(loggerName);
      logger.setLevel(level);
   }

   public Level parseStringToLevel(String logLevel) {
      Level level = Level.toLevel(logLevel);
      boolean isInvalidLogLvl = level == Level.DEBUG && !StringUtils.equalsIgnoreCase(logLevel, "DEBUG");
      if (isInvalidLogLvl) {
         throw new IllegalArgumentException("Invalid log level: " + logLevel);
      } else {
         return level;
      }
   }

   public Logger getLogger(String loggerName) throws IllegalArgumentException {
      LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
      if (ObjectUtils.isNotEmpty(loggerContext.exists(loggerName))) {
         return loggerContext.getLogger(loggerName);
      } else {
         throw new IllegalArgumentException("The logger with name " + loggerName + " not found.");
      }
   }

}
