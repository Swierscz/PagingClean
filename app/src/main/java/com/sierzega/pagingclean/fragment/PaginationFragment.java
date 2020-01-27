package com.sierzega.pagingclean.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.ybq.android.spinkit.SpinKitView;
import com.sierzega.pagingclean.R;
import com.sierzega.pagingclean.fragment.ui_components.PokemonAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class PaginationFragment extends Fragment {
    @BindView(R.id.layout_swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.spin_kit)
    SpinKitView spinKitView;
    @BindView(R.id.rv_pokemons)
    RecyclerView recyclerView;
    private PaginationViewModel viewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pagination_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PaginationViewModel.class);

//        swipeRefreshLayout.setOnRefreshListener(() -> swipeRefreshLayout.setRefreshing(true));

        final PokemonAdapter adapter = new PokemonAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        viewModel.getPokemons().observe(getViewLifecycleOwner(), pokemons -> {
            adapter.submitList(pokemons);
            swipeRefreshLayout.setRefreshing(false);
        });

        viewModel.isLoading().observe(getViewLifecycleOwner(), isLoading ->
                spinKitView.setVisibility(isLoading ? VISIBLE : INVISIBLE));


    }

}
