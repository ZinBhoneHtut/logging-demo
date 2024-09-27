package com.logging.demo.dto;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoggerInfoDTO {
   private String name;
   private Level level;
   private Level effectiveLevel;
   private boolean isAdditivity;

   public LoggerInfoDTO(Logger logger) {
      this.name = logger.getName();
      this.level = logger.getLevel();
      this.effectiveLevel = logger.getEffectiveLevel();
      this.isAdditivity = logger.isAdditive();
   }

}
