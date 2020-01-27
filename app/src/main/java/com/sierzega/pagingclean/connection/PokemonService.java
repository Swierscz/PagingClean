package com.sierzega.pagingclean.connection;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonService {

    @GET("/api/v2/pokemon")
    Single<PokemonResponse> getPokemons(@Query("offset") long offset, @Query("limit") long limit);

}
