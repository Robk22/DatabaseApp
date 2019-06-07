package de.htwberlin.databaseapp.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Instructor {

    @PrimaryKey(autoGenerate = true)
    private int instructor_id;

    @ColumnInfo(name = "instructor_name")
    public String instructorName;

    public int getInstructor_id() {
        return instructor_id;
    }

    public void setInstructor_id(int instructor_id) {
        this.instructor_id = instructor_id;
    }

    public String toString(){
        return this.instructor_id + "," + this.instructorName;
    }
}
