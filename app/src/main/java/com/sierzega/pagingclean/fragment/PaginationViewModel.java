package com.sierzega.pagingclean.fragment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.sierzega.pagingclean.Status;
import com.sierzega.pagingclean.fragment.repository.PaginationRepository;
import com.sierzega.pagingclean.model.Pokemon;

import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static java8.util.stream.StreamSupport.stream;

public class PaginationViewModel extends ViewModel {
    private final static String TAG = PaginationViewModel.class.getSimpleName();

    private static final int PAGE_SIZE = 20;
    private static final int FETCH_SIZE = 50;

    private PaginationRepository paginationRepository;
    private Paginator paginator;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    LiveData<PagedList<Pokemon>> getPokemons() {
        return paginationRepository.getPokemonLocalRepository().pokemons;
    }

    LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public PaginationViewModel() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setEnablePlaceholders(false)
                .build();

        paginationRepository = new PaginationRepository(config);

        paginationRepository.getPokemonLocalRepository().init(new PagedList.BoundaryCallback<Pokemon>() {
            @Override
            public void onZeroItemsLoaded() {
                super.onZeroItemsLoaded();
                Log.i(TAG, "On zero items loaded");
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
            paginationRepository.getPokemonRemoteRepository().requestPokemons(Status.getInstance().page++ * FETCH_SIZE, FETCH_SIZE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new MaybeObserver<List<Pokemon>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.i(TAG, "Pokemon fetch subscribed");
                            isLoading.postValue(true);
                            Status.getInstance().isFetching = true;
                        }

                        @Override
                        public void onSuccess(List<Pokemon> pokemons) {
                            Log.i(TAG, "Pokemon fetched");
                            stream(pokemons)
                                    .forEach(pokemon -> paginationRepository.getPokemonLocalRepository()
                                            .pokemonDao.insert(pokemon));
                            Status.getInstance().isFetching = false;
                            isLoading.postValue(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(TAG, "Pokemon list fetch error");
                            Status.getInstance().isFetching = false;
                            isLoading.postValue(false);
                        }

                        @Override
                        public void onComplete() {
                            Log.i(TAG, "Pokemon fetch completed");
                            Status.getInstance().isFetching = false;
                            isLoading.postValue(false);
                        }
                    });
        }
    }
}
