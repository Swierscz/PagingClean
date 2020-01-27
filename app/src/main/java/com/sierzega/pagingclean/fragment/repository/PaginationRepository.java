package com.sierzega.pagingclean.fragment.repository;

import androidx.paging.PagedList;

public class PaginationRepository {
    PokemonLocalRepository pokemonLocalRepository;
    PokemonRemoteRepository pokemonRemoteRepository;

    public PaginationRepository(PagedList.Config config) {
        pokemonLocalRepository = new PokemonLocalRepository(config);
        pokemonRemoteRepository = new PokemonRemoteRepository();
    }

    public PokemonLocalRepository getPokemonLocalRepository() {
        return pokemonLocalRepository;
    }

    public void setPokemonLocalRepository(PokemonLocalRepository pokemonLocalRepository) {
        this.pokemonLocalRepository = pokemonLocalRepository;
    }

    public PokemonRemoteRepository getPokemonRemoteRepository() {
        return pokemonRemoteRepository;
    }

    public void setPokemonRemoteRepository(PokemonRemoteRepository pokemonRemoteRepository) {
        this.pokemonRemoteRepository = pokemonRemoteRepository;
    }
}
