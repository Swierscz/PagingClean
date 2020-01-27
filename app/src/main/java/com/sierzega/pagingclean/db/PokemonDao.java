package com.sierzega.pagingclean.db;


import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.sierzega.pagingclean.model.Pokemon;

@Dao
public interface PokemonDao {

    @Query("Select * from pokemons")
    DataSource.Factory<Integer, Pokemon> selectPaged();

    @Insert(onConflict = 5)
    void insert(Pokemon pokemon);

    @Query("Delete from pokemons")
    void nukeTable();
}
