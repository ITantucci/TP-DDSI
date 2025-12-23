package Agregador.security;

import io.github.bucket4j.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {
  private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

  private Bucket resolveBucket(HttpServletRequest request) {
    String key = request.getRemoteAddr();
    return buckets.computeIfAbsent(key, _ -> newBucket());
  }

  private Bucket newBucket() {
    Refill refill = Refill.greedy(50, Duration.ofMinutes(1));
    Bandwidth limit = Bandwidth.classic(50, refill);
    return Bucket.builder()
            .addLimit(limit)
            .build();
  }

  @Override
  protected void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain filterChain
  ) throws ServletException, IOException {

    Bucket bucket = resolveBucket(request);

    if (bucket.tryConsume(1)) {
      filterChain.doFilter(request, response);
    } else {
      response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
      response.getWriter().write("Too many requests - rate limit exceeded");
    }
  }
}
