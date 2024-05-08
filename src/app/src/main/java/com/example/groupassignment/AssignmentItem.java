package com.example.groupassignment;

public class AssignmentItem {
    String PDFName;
    String PDFUri;

    public AssignmentItem(String PDFName, String PDFUri) {
        this.PDFName = PDFName;
        this.PDFUri = PDFUri;
    }

    public String getPDFName() {
        return PDFName;
    }

    public String getPDFUri() {
        return PDFUri;
    }
}
