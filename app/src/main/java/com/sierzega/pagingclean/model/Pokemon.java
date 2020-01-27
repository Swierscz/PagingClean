package com.sierzega.pagingclean.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "pokemons")
public class Pokemon {
    @ColumnInfo(name = "id")
    private String name;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "url")
    private String url;

    public Pokemon(String name, @NonNull String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Pokemon pokemon = (Pokemon) object;
        return Objects.equals(name, pokemon.name) &&
                Objects.equals(url, pokemon.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }
}
