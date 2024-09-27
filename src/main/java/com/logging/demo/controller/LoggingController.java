package com.logging.demo.controller;

import ch.qos.logback.classic.Level;
import com.logging.demo.dto.LoggerInfoDTO;
import com.logging.demo.service.LoggingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping({"/api"})
@RequiredArgsConstructor
public class LoggingController {

   //private static final Logger log = LoggerFactory.getLogger(LoggingController.class);
   private final LoggingService loggingService;

   @GetMapping("/log")
   public void logMessage() {
      log.error("This is error log");
      log.warn("This is warn log");
      log.info("This is info log");
      log.debug("This is debug log");
      log.trace("This is trace log");
   }

   @PostMapping("/log")
   public void changeLogLevel(@RequestParam String loggerName, @RequestParam String logLevel) {
      Level level = this.loggingService.parseStringToLevel(logLevel);
      this.loggingService.setLogLevel(loggerName, level);
      log.info("Log level for {} is set to {}", loggerName, level);
   }

   @GetMapping("/logger-info/{loggerName}")
   public LoggerInfoDTO getLoggerInfo(@PathVariable String loggerName) {
      ch.qos.logback.classic.Logger logger = this.loggingService.getLogger(loggerName);
      return new LoggerInfoDTO(logger);
   }

}
