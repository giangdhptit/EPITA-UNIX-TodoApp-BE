package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.Issue;
import org.example.dto.IssueRequest2;
import org.example.dto.Project;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.ws.rs.core.HttpHeaders;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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

    public void createIssueInJira3(String projectId, String summary) {
        String apiUrl = "/rest/api/2/issue/";

        IssueRequest2.IssueType issueType1 = new IssueRequest2.IssueType("10008");
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

    public List<Issue> getAllIssues() throws Exception {
        String apiUrl = "/rest/api/2/search";
        String json = webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // block() used here for simplicity, handle response asynchronously in production
        return this.parseIssues(json);
    }

    public List<Project> getAllProjects() throws Exception {
        String apiUrl = "/rest/api/2/project";
        String obj = webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // block() used here for simplicity, handle response asynchronously in production
        return this.parseProjects(obj);
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

    public List<Issue> parseIssues(String jsonResponse) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        List<Issue> issues = new ArrayList<>();
        JsonNode issuesNode = rootNode.get("issues");

        if (issuesNode.isArray()) {
            for (JsonNode issueNode : issuesNode) {
                Issue issue = new Issue();
                issue.setIssueKey(issueNode.get("key").asText());
                issue.setSummary(issueNode.get("fields").get("summary").asText());
                issue.setProjectKey(issueNode.get("fields").get("project").get("key").asText());
                issue.setStatus(issueNode.get("fields").get("status").get("name").asText());
                issues.add(issue);
            }
        }
        return issues;
    }

    public List<Project> parseProjects(String jsonResponse) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        List<Project> projects = new ArrayList<>();
        if (rootNode.isArray()) {
            for (JsonNode projectNode : rootNode) {
                Project project = new Project();
                project.setKey(projectNode.get("key").asText());
                project.setName(projectNode.get("name").asText());
                projects.add(project);
            }
        }
        return projects;
    }
}
