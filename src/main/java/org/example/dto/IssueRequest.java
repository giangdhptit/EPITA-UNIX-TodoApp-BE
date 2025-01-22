package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IssueRequest {

    private String projectId;
    private String summary;
    private String issueType;

    // Constructors, getters, and setters

    public IssueRequest() {
    }
}
