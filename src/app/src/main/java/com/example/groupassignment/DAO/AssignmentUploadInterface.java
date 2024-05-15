package com.example.groupassignment.DAO;

/**
 * @author Tianyi Xu
 * Defines methods necessary for uploading PDF files and reading their associated links.
 * This interface is intended to be implemented by classes that handles the upload and download of the Assignment pdf files
 */
public interface AssignmentUploadInterface {
    public void uploadPDFAssign();

    public void readPDFLinks();

}
