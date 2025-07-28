package be.helha.koopa.pronotespluquet.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class CourseDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "student_points_db";
    public static final String TABLE_COURSE = "courses";
    public static final String COLUMN_ID = "courseId";
    public static final String COLUMN_NAME = "courseName";
    public static final String COLUMN_BLOCK = "block";

    public CourseDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COURSE_TABLE = "CREATE TABLE " + TABLE_COURSE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT NOT NULL,"
                + COLUMN_BLOCK + " TEXT NOT NULL"
                + ")";
        db.execSQL(CREATE_COURSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        onCreate(db);
    }

    // Method to add a course
    public long addCourse(String courseName, String block) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, courseName);
        values.put(COLUMN_BLOCK, block);
        long id = db.insert(TABLE_COURSE, null, values);
        db.close();
        return id;
    }

    // Method to get all courses
    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_COURSE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String block = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BLOCK));
                courseList.add(new Course(id, name, block));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return courseList;
    }

    // (Optional) Method to delete a course
    public void deleteCourse(long courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSE, COLUMN_ID + "=?", new String[]{String.valueOf(courseId)});
        db.close();
    }
}