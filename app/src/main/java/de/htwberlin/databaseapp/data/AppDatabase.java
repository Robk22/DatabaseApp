package de.htwberlin.databaseapp.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CourseDoa courseDao();

}
