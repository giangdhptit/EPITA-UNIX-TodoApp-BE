package org.example.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IssueRequest2 {
    private Fields fields;

    // getters and setters
    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    @AllArgsConstructor
    public static class Fields {
        private String summary;
        private IssueType issuetype;
        private Project project;

        // getters and setters
        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public IssueType getIssuetype() {
            return issuetype;
        }

        public void setIssuetype(IssueType issuetype) {
            this.issuetype = issuetype;
        }

        public Project getProject() {
            return project;
        }

        public void setProject(Project project) {
            this.project = project;
        }
    }

    @AllArgsConstructor
    public static class IssueType {
        private String id;

        // getters and setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    @AllArgsConstructor
    public static class Project {
        private String id;

        // getters and setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }
}
