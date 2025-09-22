package org.example.e_school_management.student.model;

public class Assignment {
    private String subject;
    private int count;

    public Assignment(String subject, int count) {
        this.subject = subject;
        this.count = count;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
