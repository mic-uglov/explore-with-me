package ru.practicum.ewm.stats.server;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;
}