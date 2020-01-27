package com.sierzega.pagingclean.fragment.repository;

import android.util.Log;

import com.sierzega.pagingclean.connection.PokemonResponse;
import com.sierzega.pagingclean.connection.PokemonService;
import com.sierzega.pagingclean.connection.RetrofitManager;
import com.sierzega.pagingclean.model.Pokemon;

import java.util.List;

import io.reactivex.Maybe;
import java8.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PokemonRemoteRepository {
    private PokemonService service = RetrofitManager.buildService(PokemonService.class);

    public Maybe<List<Pokemon>> requestPokemons(int offset, int limit) {
        return service.getPokemons(offset, limit)
                .doOnSuccess(pokemonResponse -> Log.i(TAG, "Fetching from network succedd"))
                .doOnError(t -> Log.i(TAG, "Connection error when requesting pokemons: " + t.getMessage()))
                .map(PokemonResponse::getPokemons)
                .filter(Objects::nonNull);
    }

}
