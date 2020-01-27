package com.sierzega.pagingclean.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sierzega.pagingclean.model.Pokemon;

@Database(entities = {Pokemon.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PokemonDao pokemonDao();
}
