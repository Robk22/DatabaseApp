package de.htwberlin.databaseapp.data;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Course implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int cid;

    @ColumnInfo(name = "course_name")
    public String courseName;

    @ColumnInfo(name = "location")
    public String location;

    @Embedded
    public Instructor instructor;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String toString(){
        return "{"+this.cid + "," + this.courseName + "," + this.location +"}";
    }
}
