package com.sierzega.pagingclean.fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;

import com.sierzega.pagingclean.model.Pokemon;

public class PaginationViewModel extends ViewModel {
    private final static String TAG = PaginationViewModel.class.getSimpleName();
    private Paginator paginator;
    private LiveData<PagedList<Pokemon>> pokemons;
    private MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>(false);
    private MutableLiveData<Integer> currentPage = new MutableLiveData<>(-1);

    private PaginationViewModel(int lastSetPage) {
        paginator = new Paginator(lastSetPage, new Paginator.PaginatorCallback() {
            @Override
            public void isLoading(boolean isLoading) {
                isLoadingLiveData.postValue(isLoading);
            }

            @Override
            public void lastPageSet(int page) {
                currentPage.postValue(page);
            }
        });

        this.pokemons = paginator.getPaginationRepository().getPokemonLocalRepository().pokemons;

    }

    void invalidatePokemonsData() {
        paginator.invalidatePokemonsData();
    }

    LiveData<PagedList<Pokemon>> getPokemons() {
        return pokemons;
    }

    LiveData<Boolean> isLoading() {
        return isLoadingLiveData;
    }

    LiveData<Integer> getCurrentPage() {
        return currentPage;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final int lastSetPage;

        public Factory(int lastSetPage) {
            this.lastSetPage = lastSetPage;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new PaginationViewModel(lastSetPage);
        }

    }


}
