package edu.java.ratelimit;

import edu.java.configuration.ApplicationConfig;
import edu.java.controller.ratelimit.RateLimitInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class RateLimitInterceptorTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ApplicationConfig applicationConfig;

    @Mock
    private Object handler;
    private RateLimitInterceptor rateLimitInterceptor;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        when(applicationConfig.rateLimit()).thenReturn(new ApplicationConfig.RateLimit(5, 5));
        rateLimitInterceptor = new RateLimitInterceptor(applicationConfig);
    }

    @SneakyThrows
    @Test
    @DisplayName("test when rate limit not exceeded")
    public void testSuccessRequest() {
        when(request.getRemoteAddr()).thenReturn("1.0.0.1");
        for (int i = 0; i < 4; i++) {
            assertTrue(rateLimitInterceptor.preHandle(request, response, handler));
        }
    }

    @SneakyThrows
    @Test
    @DisplayName("test when rate limit exceeded")
    public void testFailureRequest() {
        when(request.getRemoteAddr()).thenReturn("1.0.0.1");
        for (int i = 0; i < 5; i++) {
            rateLimitInterceptor.preHandle(request, response, handler);
        }
        assertFalse(rateLimitInterceptor.preHandle(request, response, handler));
    }
}
