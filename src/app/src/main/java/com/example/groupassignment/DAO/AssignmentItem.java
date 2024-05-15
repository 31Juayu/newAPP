package com.example.groupassignment.DAO;

/**
 * @author Tianyi Xu
 * Represents an item for an assignment, specifically focusing on a PDF document.
 * This class stores information about a PDF file, including its name and URI location.
 */
public class AssignmentItem {
    // The name of the uploaded PDF file
    String PDFName;
    // The uri of the uploaded PDF file
    String PDFUri;

    /**
     * The constructor of the Assignment item
     * This constructor initializes the PDFName and PDFUri fields of the object.
     * @param PDFName
     * @param PDFUri
     */
    public AssignmentItem(String PDFName, String PDFUri) {
        this.PDFName = PDFName;
        this.PDFUri = PDFUri;
    }

    /**
     * Get the file name
     * @return
     */
    public String getPDFName() {
        return PDFName;
    }

    /**
     * get the uri of the file
     * @return
     */
    public String getPDFUri() {
        return PDFUri;
    }
}
