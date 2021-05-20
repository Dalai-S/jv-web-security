package mate.web.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter {
    private Set<String> allowedUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowedUrls = new HashSet<>();
        allowedUrls.add("/drivers/login");
        allowedUrls.add("/index");
        allowedUrls.add("/drivers/add");
        allowedUrls.add("/");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
                throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        Long id = (Long) session.getAttribute("id");
        if (id == null && allowedUrls.contains(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }
        if (id == null) {
            response.sendRedirect("/drivers/login");
            return;
        }
        filterChain.doFilter(request, response);
    }
}

