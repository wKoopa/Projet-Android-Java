package be.helha.koopa.pronotespluquet.models;

public class Course {
    private long courseId;
    private String courseName;
    private String block;

    public Course(long courseId, String courseName, String block) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.block = block;
    }

    public long getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public String getBlock() { return block; }

    public void setCourseId(long courseId) { this.courseId = courseId; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setBlock(String block) { this.block = block; }
}