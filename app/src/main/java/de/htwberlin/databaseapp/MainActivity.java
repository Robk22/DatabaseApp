package de.htwberlin.databaseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import de.htwberlin.databaseapp.data.AppDatabase;
import de.htwberlin.databaseapp.data.Course;
import de.htwberlin.databaseapp.data.CourseDoa;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void writeToInternalFile(View view){
        String filename = "File_" + System.currentTimeMillis() +".txt";
        String fileContents = new Date().toString();

        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            Log.d("write", "File " + filename+ " written");
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readInternalFiles(View view){
        File directory = view.getContext().getFilesDir();
        String filenames [] = directory.list();
        for(String filename: filenames) {
            File file = new File(directory, filename);
            try {
                Log.d("read", file.getAbsolutePath());
                byte[] bytes = new byte[10];
                openFileInput(file.getName()).read(bytes);
                Log.d("read", new String(bytes));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readExternalFiles(View view){

        if(!isExternalStorageReadable())
            return;

        File albumStorage = getPublicAlbumStorageDir("");
        Log.d("readExternal", albumStorage.getPath());
    }


    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getPublicAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("AlbumStorage", "Directory not created");
        }
        return file;
    }

    public void insertIntoDatabase(View view){


        Context context = getApplicationContext();
        AppDatabase db = Room.databaseBuilder (context, AppDatabase.class, "courses").build();

        final CourseDoa courseDao = db.courseDao();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Course courses []  = fetchCourses();
                courseDao.insertAll(courses[0], courses[1]);
                Log.d("database insert", Arrays.toString(courses)  + " inserted");
            }
        }) .start();


        db.close();
    }

    public void readFromDatabase(View view){

        Context context = getApplicationContext();
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "courses").build();
        final CourseDoa courseDao = db.courseDao();

//        Course course = courseDao.findByName("Mobile");

        new Thread(new Runnable() {
            @Override
            public void run() {

//                List<Course> allCourse = courseDao.getAll();
                Course course = courseDao.findByName("Mobile Application Development");
//                Log.d("courses", allCourse + "");
                Log.d("course", ""+course);

            }
        }) .start();


        db.close();
    }

    public void readAllFromDatabase(View view){

        Context context = getApplicationContext();
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "courses").build();
        final CourseDoa courseDao = db.courseDao();

//        Course course = courseDao.findByName("Mobile");

        new Thread(new Runnable() {
            @Override
            public void run() {

                List<Course> allCourse = courseDao.getAll();
                Log.d("courses", allCourse + "");
            }
        }) .start();


        db.close();
    }


    private Course[] fetchCourses() {
        Course courses[] = new Course[2];

        courses[0] = new Course();
        courses[0].courseName = "Mobile Application Development";
        courses[0].location = "TA A 143";

        courses[1] = new Course();
        courses[1].courseName = "Java 2";
        courses[1].location = "TA A 136";

        return courses;
    }


}
