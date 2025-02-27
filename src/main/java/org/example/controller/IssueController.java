package org.example.controller;

import org.example.dto.Issue;
import org.example.dto.IssueRequest;
import org.example.service.JiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jira/issue")
public class IssueController {

    @Autowired
    JiraService jiraService;

    @PostMapping("/create")
    public ResponseEntity<String> createJiraIssue(@RequestBody IssueRequest issueRequest) {
        jiraService.createIssueInJira3(issueRequest.getProjectKey(), issueRequest.getSummary());
        return ResponseEntity.ok("Issue created successfully in Jira!");
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Issue>> getAll() throws Exception {
        List<Issue> issue = jiraService.getAllIssues();
        return ResponseEntity.ok(issue);
    }

    @GetMapping("/{key}")
    public ResponseEntity<Object> getIssue(@PathVariable String key) {
        Object issue = jiraService.getIssue(key);
            return ResponseEntity.ok(issue);

    }

    @PutMapping("/{key}/edit")
    public ResponseEntity<Object> updateJiraIssue(
            @PathVariable String key,@RequestBody Object o) {
        jiraService.updateIssue(key,o);
        return ResponseEntity.ok("Issue updated !");
    }
}


