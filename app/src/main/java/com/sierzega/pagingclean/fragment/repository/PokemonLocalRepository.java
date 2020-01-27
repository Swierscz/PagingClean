package com.sierzega.pagingclean.fragment.repository;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.sierzega.pagingclean.db.PokemonDao;
import com.sierzega.pagingclean.db.RepositoryProvider;
import com.sierzega.pagingclean.model.Pokemon;

public class PokemonLocalRepository {
    private LivePagedListBuilder<Integer, Pokemon> livePagedListBuilder;
    public PokemonDao pokemonDao;
    public LiveData<PagedList<Pokemon>> pokemons;
    public PagedList.Config config;

    PokemonLocalRepository(PagedList.Config config) {
        pokemonDao = RepositoryProvider.getInstance().getAppDatabase().pokemonDao();
        this.config = config;
    }

    public void init(PagedList.BoundaryCallback<Pokemon> boundaryCallbacks) {
        livePagedListBuilder = new LivePagedListBuilder<>(pokemonDao.selectPaged(), config);
        livePagedListBuilder.setBoundaryCallback(boundaryCallbacks);
        pokemons = livePagedListBuilder.build();
    }


}
