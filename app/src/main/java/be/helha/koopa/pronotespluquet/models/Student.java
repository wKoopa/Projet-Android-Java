package be.helha.koopa.pronotespluquet.models;

public class Student {
    private long studentId; // identifiant unique (matricule)
    private String lastName; // nom
    private String firstName; // prénom
    private long blockId; // bloc d’appartenance

    public Student(long studentId, String lastName, String firstName, long blockId) {
        this.studentId = studentId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.blockId = blockId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public long getBlockId() {
        return blockId;
    }

    public void setBlockId(long blockId) {
        this.blockId = blockId;
    }
}
