package com.example.myapplication.utils;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.example.myapplication.converters.DateConverter;
import com.example.myapplication.model.StoriesDbModel;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(DateConverter.class)
public interface StoriesDao {

    @Query("select * from StoriesDbModel")
    LiveData<List<StoriesDbModel>> getAllStories();

    @Query("select * from StoriesDbModel where id = :id")
    StoriesDbModel getItembyId(String id);

    @Insert(onConflict = REPLACE)
    void addStory(StoriesDbModel borrowModel);

    @Delete
    void deleteStory(StoriesDbModel borrowModel);
}
