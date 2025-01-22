package org.example.service;

import org.example.dto.IssueRequest2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.ws.rs.core.HttpHeaders;
import java.util.Base64;

@Service
public class JiraService {

    private final WebClient webClient;

    public JiraService(WebClient.Builder webClientBuilder,
                       @Value("${jira.base-url}") String baseUrl,
                       @Value("${jira.username}") String username,
                       @Value("${jira.api-token}") String apiToken) {

        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ":" + apiToken).getBytes()))
                .build();
    }
//    public String createIssue(String projectKey, Long issueType, String issueSummary) {
//        IssueRestClient issueClient = restClient.getIssueClient();
//        IssueInput newIssue = new IssueInputBuilder(
//                projectKey, issueType, issueSummary).build();
//        return issueClient.createIssue(newIssue).claim().getKey();
//    }
    public void createIssueInJira(String projectId, String summary, String issueType) {
        String apiUrl = "/rest/api/2/issue/";

        IssueRequest2.IssueType issueType1 = new IssueRequest2.IssueType(issueType);
        IssueRequest2.Project project = new IssueRequest2.Project(projectId);
        IssueRequest2.Fields fields = new IssueRequest2.Fields(summary,issueType1,project);
        IssueRequest2 issueRequest = new IssueRequest2(fields);
        // You need to create IssueRequest class with appropriate fields for your request
        webClient.post()
                .uri(apiUrl)
                .body(BodyInserters.fromValue(issueRequest))
                .retrieve()
                .bodyToMono(String.class)
                .block(); // block() used here for simplicity, handle response asynchronously in production
    }

    public void createIssueInJira2(IssueRequest2 issue) {
        String apiUrl = "/rest/api/2/issue/";

//        IssueRequest2.IssueType issueType1 = new IssueRequest2.IssueType(issueType);
//        IssueRequest2.Project project = new IssueRequest2.Project(projectId);
//        IssueRequest2.Fields fields = new IssueRequest2.Fields(summary,issueType1,project);
//        IssueRequest2 issueRequest = new IssueRequest2(fields);
        // You need to create IssueRequest class with appropriate fields for your request
        webClient.post()
                .uri(apiUrl)
                .body(BodyInserters.fromValue(issue))
                .retrieve()
                .bodyToMono(String.class)
                .block(); // block() used here for simplicity, handle response asynchronously in production
    }

    public void createIssueInJira3(String projectId, String summary, String issueType) {
        String apiUrl = "/rest/api/2/issue/";

        IssueRequest2.IssueType issueType1 = new IssueRequest2.IssueType(issueType);
        IssueRequest2.Project project = new IssueRequest2.Project(projectId);
        IssueRequest2.Fields fields = new IssueRequest2.Fields(summary,issueType1,project);
        IssueRequest2 issueRequest = new IssueRequest2(fields);
        // You need to create IssueRequest class with appropriate fields for your request
        webClient.post()
                .uri(apiUrl)
                .body(BodyInserters.fromValue(issueRequest))
                .retrieve()
                .bodyToMono(String.class)
                .block(); // block() used here for simplicity, handle response asynchronously in production
    }

    public Object getIssue(String issueKey) {
        String apiUrl = "/rest/api/2/issue/"+issueKey;
        return webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(Object.class)
                .block(); // block() used here for simplicity, handle response asynchronously in production
    }

    public Object getAllIssues() {
        String apiUrl = "/rest/api/2/search";
        return webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(Object.class)
                .block(); // block() used here for simplicity, handle response asynchronously in production
    }

    public Object getAllProjects() {
        String apiUrl = "/rest/api/2/project";
        return webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(Object.class)
                .block(); // block() used here for simplicity, handle response asynchronously in production
    }

    public void createProject(Object jsonPayload) {
        String apiUrl = "/rest/api/2/project/";
        webClient.post()
                .uri(apiUrl)
                .body(Mono.just(jsonPayload), Object.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public Object updateIssue(String issueKey,Object jsonPayload) {
        String apiUrl = "/rest/api/2/issue/"+issueKey;
        return webClient.put()
                .uri(apiUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(jsonPayload), Object.class)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }


}
