package be.helha.koopa.pronotespluquet.models;

/**
 * Data model representing a course, linked to a block by blockId.
 */
public class Course {
    private long courseId;
    private String courseName;
    private long blockId; // Which block this course belongs to

    /**
     * Constructor for Course
     * @param courseId The course's unique ID (primary key)
     * @param courseName The name of the course
     * @param blockId The ID of the block this course is attached to
     */
    public Course(long courseId, String courseName, long blockId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.blockId = blockId;
    }

    public long getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public long getBlockId() { return blockId; }

    public void setCourseId(long courseId) { this.courseId = courseId; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setBlockId(long blockId) { this.blockId = blockId; }
}