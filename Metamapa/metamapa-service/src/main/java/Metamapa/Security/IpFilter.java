package Metamapa.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class IpFilter extends OncePerRequestFilter {

  private final List<String> allowedIps;
  private final List<String> blockedIps;

  public IpFilter(
          @Value("#{'${security.ip.allowed:127.0.0.1}'.split(',')}") List<String> allowedIps,
          @Value("#{'${security.ip.blocked:}'.split(',')}") List<String> blockedIps
  ) {
    this.allowedIps = allowedIps;
    this.blockedIps = blockedIps;
  }

  @Override
  protected void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain filterChain
  ) throws ServletException, IOException {

    String ip = request.getRemoteAddr();

    // Lista negra
    if (blockedIps.contains(ip)) {
      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.getWriter().write("Forbidden - IP blocked");
      return;
    }

    // Lista blanca
    if (!allowedIps.contains(ip)) {
      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.getWriter().write("Forbidden - IP not allowed");
      return;
    }

    filterChain.doFilter(request, response);
  }
}
