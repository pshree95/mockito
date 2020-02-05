package com.example.mockito.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Records {
    private Attributes attributes;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("EventType")
    private String eventType;
}
