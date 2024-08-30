package com.foodtogo.mono.ai.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AiRequestHistoryResponseDto {

    private List<Candidate> candidates;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Candidate {
        private Content content;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Content {
            private List<Part> parts;

            @Getter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Part {
                private String text;

            }
        }
    }
}
