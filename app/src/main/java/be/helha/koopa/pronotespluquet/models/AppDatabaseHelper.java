package be.helha.koopa.pronotespluquet.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class AppDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "student_points_db";

    // Table blocks
    public static final String TABLE_BLOCK = "blocks";
    public static final String COLUMN_BLOCK_ID = "blockId";
    public static final String COLUMN_BLOCK_NAME = "blockName";

    // Table courses
    public static final String TABLE_COURSE = "courses";
    public static final String COLUMN_COURSE_ID = "courseId";
    public static final String COLUMN_COURSE_NAME = "courseName";
    public static final String COLUMN_COURSE_BLOCK_ID = "blockId";

    // Table students
    public static final String TABLE_STUDENT = "students";
    public static final String COLUMN_STUDENT_ID = "studentId";
    public static final String COLUMN_STUDENT_LAST_NAME = "lastName";
    public static final String COLUMN_STUDENT_FIRST_NAME = "firstName";
    public static final String COLUMN_STUDENT_BLOCK_ID = "blockId";

    public AppDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BLOCK_TABLE = "CREATE TABLE " + TABLE_BLOCK + "(" +
                COLUMN_BLOCK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_BLOCK_NAME + " TEXT NOT NULL UNIQUE" +
                ")";
        db.execSQL(CREATE_BLOCK_TABLE);

        String CREATE_COURSE_TABLE = "CREATE TABLE " + TABLE_COURSE + "(" +
                COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_COURSE_NAME + " TEXT NOT NULL," +
                COLUMN_COURSE_BLOCK_ID + " INTEGER NOT NULL" +
                ")";
        db.execSQL(CREATE_COURSE_TABLE);

        String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT + "(" +
                COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY," +
                COLUMN_STUDENT_LAST_NAME + " TEXT NOT NULL," +
                COLUMN_STUDENT_FIRST_NAME + " TEXT NOT NULL," +
                COLUMN_STUDENT_BLOCK_ID + " INTEGER NOT NULL" +
                ")";
        db.execSQL(CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOCK);
        onCreate(db);
    }

    // --- Méthodes pour les blocs ---
    public long addBlock(String blockName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BLOCK_NAME, blockName);
        long id = db.insert(TABLE_BLOCK, null, values);
        db.close();
        return id;
    }

    public List<Block> getAllBlocks() {
        List<Block> blockList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_BLOCK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_BLOCK_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BLOCK_NAME));
                blockList.add(new Block(id, name));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return blockList;
    }

    // --- Méthodes pour les cours ---
    public long addCourse(String courseName, long blockId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COURSE_NAME, courseName);
        values.put(COLUMN_COURSE_BLOCK_ID, blockId);
        long id = db.insert(TABLE_COURSE, null, values);
        db.close();
        return id;
    }

    public List<Course> getCoursesByBlockId(long blockId) {
        List<Course> courseList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_COURSE + " WHERE " + COLUMN_COURSE_BLOCK_ID + "=?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(blockId)});
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_COURSE_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_NAME));
                long bId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_COURSE_BLOCK_ID));
                courseList.add(new Course(id, name, bId));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return courseList;
    }

    // --- Méthodes pour les élèves ---
    public long addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID, student.getStudentId());
        values.put(COLUMN_STUDENT_LAST_NAME, student.getLastName());
        values.put(COLUMN_STUDENT_FIRST_NAME, student.getFirstName());
        values.put(COLUMN_STUDENT_BLOCK_ID, student.getBlockId());
        long id = db.insert(TABLE_STUDENT, null, values);
        db.close();
        return id;
    }

    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_ID));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_LAST_NAME));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_FIRST_NAME));
                long blockId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_BLOCK_ID));
                studentList.add(new Student(id, lastName, firstName, blockId));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return studentList;
    }

    public void updateStudentBlock(long studentId, long newBlockId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_BLOCK_ID, newBlockId);
        db.update(TABLE_STUDENT, values, COLUMN_STUDENT_ID + "=?", new String[]{String.valueOf(studentId)});
        db.close();
    }

    public void deleteStudent(long studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENT, COLUMN_STUDENT_ID + "=?", new String[]{String.valueOf(studentId)});
        db.close();
    }
} 