package com.logging.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class MDCFilter extends OncePerRequestFilter {

   private static final String REQUEST_ID = "requestId";
   private static final String IP_ADDRESS = "ipAddress";
   private static final String SESSION_ID = "sessionId";

   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
      try {
         MDC.put(REQUEST_ID, UUID.randomUUID().toString());
         MDC.put(IP_ADDRESS, getIpAddress(request));
         MDC.put(SESSION_ID, request.getSession().getId());
         filterChain.doFilter(request, response);
      } catch (Exception var8) {
         log.error("MDC Error: ", var8);
         var8.printStackTrace();
      } finally {
         MDC.remove(REQUEST_ID);
         MDC.remove(IP_ADDRESS);
         MDC.remove(SESSION_ID);
      }

   }

   public static String getIpAddress(HttpServletRequest request) {
      String ipAddress = request.getHeader("X-Forwarded-For");
      if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
         ipAddress = request.getHeader("Proxy-Client-IP");
      }

      if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
         ipAddress = request.getHeader("WL-Proxy-Client-IP");
      }

      if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
         ipAddress = request.getHeader("HTTP_CLIENT_IP");
      }

      if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
         ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
      }

      if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
         ipAddress = request.getRemoteAddr();
      }

      if ("0:0:0:0:0:0:0:1".equals(ipAddress) || "::1".equals(ipAddress)) {
         ipAddress = "127.0.0.1";
      }

      return ipAddress;
   }
}
