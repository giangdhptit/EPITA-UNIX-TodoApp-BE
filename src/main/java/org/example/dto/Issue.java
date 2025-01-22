package org.example.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Issue {
    private String issueKey;
    private String projectKey;
    private String status;
}
