package com.sierzega.pagingclean.db;

import android.content.Context;

import androidx.room.Room;

public class RepositoryProvider {
    private static RepositoryProvider INSTANCE;
    private AppDatabase appDatabase;

    public void initDatabase(Context context){
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "some_base")
                .build();
    }

    public static RepositoryProvider getInstance(){
        if(INSTANCE == null){
            synchronized (RepositoryProvider.class){
                if(INSTANCE == null){
                    INSTANCE = new RepositoryProvider();
                }
            }
        }
        return INSTANCE;
    }

    public AppDatabase getAppDatabase(){
        return appDatabase;
    }

}
