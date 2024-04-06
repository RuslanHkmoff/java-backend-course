package edu.java.controller.ratelimit;

import edu.java.configuration.ApplicationConfig;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    public static final int NANO_TO_SEC = 1_000_000_000;
    private final int rateLimit;
    private final int delay;
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public RateLimitInterceptor(ApplicationConfig applicationConfig) {
        this.rateLimit = applicationConfig.rateLimit().limit();
        this.delay = applicationConfig.rateLimit().delay();
    }

    public Bucket resolveBucket(String addr) {
        return cache.computeIfAbsent(addr, this::newBucket);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, Object handler)
        throws Exception {
        String ipAddr = request.getRemoteAddr();
        Bucket tokenBucket = resolveBucket(ipAddr);
        ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            return true;
        } else {
            long waitForRefill = probe.getNanosToWaitForRefill() / NANO_TO_SEC;
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Number of allowed requests exceeded");
            return false;
        }
    }

    private Bucket newBucket(String addr) {
        Refill refill = Refill.intervally(rateLimit, Duration.ofMinutes(delay));
        Bandwidth limit = Bandwidth.classic(rateLimit, refill);
        return Bucket.builder()
            .addLimit(limit)
            .build();
    }
}
