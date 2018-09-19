package ru.eosan.camunda.config;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.exception.InvalidRequestException;
import org.camunda.bpm.webapp.impl.security.SecurityActions;
import org.camunda.bpm.webapp.impl.security.SecurityActions.SecurityAction;
import org.camunda.bpm.webapp.impl.security.auth.*;
import org.camunda.bpm.webapp.impl.util.ProcessEngineUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * @see AuthenticationFilter
 * @see UserAuthenticationResource#doLogin
 */
public class SSOAuthFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(SSOAuthFilter.class);

    private static final String defaultEngineName = "default";

    public void init(FilterConfig arg0) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        Authentications authentications = Authentications.getFromSession(req.getSession());
        setAutoLoginAuthentication(request, authentications);
        Authentications.setCurrent(authentications);

        try {
            SecurityActions.runWithAuthentications((SecurityAction<Void>) () -> {
                try {
                    chain.doFilter(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return null;
            }, authentications);
        } finally {
            Authentications.clearCurrent();
            Authentications.updateSession(req.getSession(), authentications);
        }
    }

    private void setAutoLoginAuthentication(final ServletRequest request, Authentications authentications) {
        final HttpServletRequest req = (HttpServletRequest) request;
        String username = req.getParameter("who");
        if (username == null) {
            return;
        }

        log.info("[REQUEST] SSOAuth auth {}", username);

        final ProcessEngine processEngine = ProcessEngineUtil.lookupProcessEngine(defaultEngineName);
        if (processEngine == null) {
            log.info("[FAILED] SSOAuth auth {}. Default processEngine is null", username);
            throw new InvalidRequestException(Response.Status.INTERNAL_SERVER_ERROR, "Failed to find default process engine");
        }

        processEngine.getIdentityService().clearAuthentication();
        AuthenticationService authenticationService = new AuthenticationService();
        UserAuthentication authentication = (UserAuthentication) authenticationService.
                createAuthenticate(processEngine, username, null, null);
        authentications.addAuthentication(authentication);
        log.info("[SUCCESS] SSOAuth auth {}", username);
    }
}