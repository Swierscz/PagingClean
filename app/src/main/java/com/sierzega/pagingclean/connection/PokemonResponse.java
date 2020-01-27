package com.sierzega.pagingclean.connection;

import com.google.gson.annotations.SerializedName;
import com.sierzega.pagingclean.model.Pokemon;

import java.util.List;

public class PokemonResponse {
    @SerializedName("count")
    long count;
    @SerializedName("next")
    String next;
    @SerializedName("previous")
    String previous;
    @SerializedName("results")
    List<Pokemon> pokemons;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
}
