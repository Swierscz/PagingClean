package com.sierzega.pagingclean.fragment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

import com.sierzega.pagingclean.Status;
import com.sierzega.pagingclean.fragment.repository.PaginationRepository;
import com.sierzega.pagingclean.model.Pokemon;

import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static java8.util.stream.StreamSupport.stream;

class Paginator {
    private final static String TAG = Paginator.class.getSimpleName();
    private static final int PAGE_SIZE = 20;
    private static final int FETCH_SIZE = 50;
    private PaginationRepository paginationRepository;
    private PaginatorCallback paginatorCallback;
    private int pageToFetch;

    interface PaginatorCallback {
        void isLoading(boolean isLoading);

        void lastPageSet(int page);
    }

    PaginationRepository getPaginationRepository() {
        return paginationRepository;
    }

    Paginator(int pageToFetch, PaginatorCallback paginatorCallback) {
        this.pageToFetch = pageToFetch + 1;
        this.paginatorCallback = paginatorCallback;
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setEnablePlaceholders(false)
                .build();

        paginationRepository = new PaginationRepository(config);

        paginationRepository.getPokemonLocalRepository().init(new PagedList.BoundaryCallback<Pokemon>() {
            @Override
            public void onZeroItemsLoaded() {
                super.onZeroItemsLoaded();
                Log.i(TAG, "On zeron items loaded");
                fetchPokemons();
            }

            @Override
            public void onItemAtEndLoaded(@NonNull Pokemon itemAtEnd) {
                super.onItemAtEndLoaded(itemAtEnd);
                Log.i(TAG, "On item at end loaded");
                fetchPokemons();
            }
        });

    }

    private void fetchPokemons() {
        if (!Status.getInstance().isFetching) {
            Log.i(TAG, "Fetching pokemons function started");
            int page = pageToFetch++;
            paginatorCallback.lastPageSet(page);
            paginationRepository.getPokemonRemoteRepository().requestPokemons(page * FETCH_SIZE, FETCH_SIZE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new MaybeObserver<List<Pokemon>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.i(TAG, "Pokemon fetch subscribed");
                            paginatorCallback.isLoading(true);
                            Status.getInstance().isFetching = true;
                        }

                        @Override
                        public void onSuccess(List<Pokemon> pokemons) {
                            Log.i(TAG, "Pokemon fetched");
                            stream(pokemons)
                                    .forEach(pokemon -> paginationRepository.getPokemonLocalRepository()
                                            .pokemonDao.insert(pokemon));
                            Status.getInstance().isFetching = false;
                            paginatorCallback.isLoading(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(TAG, "Pokemon list fetch error");
                            Status.getInstance().isFetching = false;
                            paginatorCallback.isLoading(false);
                        }

                        @Override
                        public void onComplete() {
                            Log.i(TAG, "Pokemon fetch completed");
                        }
                    });
        }

    }

    void invalidatePokemonsData() {
        paginationRepository.getPokemonRemoteRepository().requestPokemons(0, FETCH_SIZE)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(pokemons -> {
                    pageToFetch = 0;
                    paginatorCallback.lastPageSet(-1);
                    paginationRepository.getPokemonLocalRepository().pokemonDao.nukeTable();
                })
                .doOnError(t -> Log.i(TAG, "Cannot invalidate pokemons data"))
                .observeOn(Schedulers.io())
                .subscribe();

    }

}
