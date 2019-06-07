package de.htwberlin.databaseapp.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDoa {
    @Query("SELECT * FROM course")
    List<Course> getAll();

    @Query("SELECT * FROM course WHERE cid IN (:courseIds)")
    List<Course> loadAllByIds(int[] courseIds);

    @Query("SELECT * FROM course WHERE course_name LIKE :courseName  " +
            "LIMIT 1")
    Course findByName(String courseName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Course... courses);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Course course);

    @Delete
    void delete(Course course);
}
