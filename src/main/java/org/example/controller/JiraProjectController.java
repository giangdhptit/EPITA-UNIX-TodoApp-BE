package org.example.controller;

import org.example.dto.IssueRequest;
import org.example.dto.Project;
import org.example.service.JiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jira/project")
public class JiraProjectController {

    @Autowired
    JiraService jiraService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Object jsonPayload) {
        jiraService.createProject(jsonPayload);
        return ResponseEntity.ok("Project created successfully in Jira!");
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Project>> getAll() throws Exception {
        List<Project> issue = jiraService.getAllProjects();
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


