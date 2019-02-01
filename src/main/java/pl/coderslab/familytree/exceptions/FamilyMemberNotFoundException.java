package pl.coderslab.familytree.exceptions;

public class FamilyMemberNotFoundException extends RuntimeException {

    public FamilyMemberNotFoundException(String message) {
        super(message);
    }
}
