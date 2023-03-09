package ru.practicum.ewm.appevent;

import lombok.Getter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Getter
public class StatsEvent {
    private final String ip;
    private final String uri;

    public StatsEvent() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new UnsupportedOperationException("We must create the event in the context");
        }

        HttpServletRequest request = attributes.getRequest();
        this.ip = request.getRemoteAddr();
        this.uri = request.getRequestURI() +
                (request.getQueryString() != null && !request.getQueryString().isBlank() ?
                        "?" + request.getQueryString() : "");
    }
}