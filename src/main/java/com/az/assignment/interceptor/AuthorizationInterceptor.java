package com.az.assignment.interceptor;

import com.az.assignment.exception.CustomRuntimeException;
import com.az.assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Slf4j
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Authorization intercepted userId {}: " + request.getHeader("userId"));
        validateUserId(request.getHeader("userId"));
        return true;
    }

    private void validateUserId(String header) {
        if(header == null)
            throw new CustomRuntimeException("UserId is missing", "Please provide userId in header for user authorization",
                    HttpStatus.UNAUTHORIZED);
        long userId = Long.parseLong(header);
        if(!userRepository.existsById(userId))
            throw new CustomRuntimeException("User doesn't exist", "Provided user authorization failed",
                    HttpStatus.UNAUTHORIZED);
    }
}
