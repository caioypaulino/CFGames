package com.project.cfgames.clients.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeminiResponse {
    private List<Candidate> candidates;
    @JsonProperty("usage_metadata")
    private UsageMetadata usageMetadata;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Candidate {
        private Content content;
        @JsonProperty("finish_reason")
        private String finishReason;
        private int index;
        @JsonProperty("safety_ratings")
        private List<SafetyRating> safetyRatings;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Content {
        private List<Part> parts;
        private String role;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Part {
        private String text;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class SafetyRating {
        private String category;
        private String probability;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class UsageMetadata {
        @JsonProperty("prompt_token_count")
        private int promptTokenCount;
        @JsonProperty("candidates_token_count")
        private int candidatesTokenCount;
        @JsonProperty("total_token_count")
        private int totalTokenCount;
    }
}