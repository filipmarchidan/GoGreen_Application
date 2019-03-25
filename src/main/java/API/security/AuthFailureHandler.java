package API.security;

import org.springframework.security.core.AuthenticationException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Custom authentication failure handler.
 */
public class AuthFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {

    /**
     * Handles authentication failure.
     * @param request HTTP request
     * @param response HTTP response
     * @param exception Exception
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        response.setStatus(200);
        response.setContentType("Application/JSON");
        response.getWriter().write(
            "not authenticated"
        );
    }

}
