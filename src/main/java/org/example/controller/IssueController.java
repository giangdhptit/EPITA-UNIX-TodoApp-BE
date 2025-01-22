package org.example.controller;

import org.example.dto.IssueRequest;
import org.example.service.JiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jira/issue")
public class IssueController {

    @Autowired
    JiraService jiraService;

    @PostMapping("/create")
    public ResponseEntity<String> createJiraIssue(@RequestBody IssueRequest issueRequest) {
        jiraService.createIssueInJira3(issueRequest.getProjectId(), issueRequest.getSummary(), issueRequest.getIssueType());
        return ResponseEntity.ok("Issue created successfully in Jira!");
    }

    @GetMapping("/get-all")
    public ResponseEntity<Object> getAll() {
        Object issue = jiraService.getAllIssues();
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


