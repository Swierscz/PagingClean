package com.sierzega.pagingclean.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.ybq.android.spinkit.SpinKitView;
import com.sierzega.pagingclean.R;
import com.sierzega.pagingclean.SharedPrefUtil;
import com.sierzega.pagingclean.fragment.ui_components.PokemonAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.val;

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
        int lastPage = SharedPrefUtil.getPage(getContext());
        val factory = new PaginationViewModel.Factory(lastPage);
        viewModel = ViewModelProviders.of(getActivity(), factory).get(PaginationViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            viewModel.invalidatePokemonsData();
        });

        final PokemonAdapter adapter = new PokemonAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        viewModel.getPokemons().observe(getViewLifecycleOwner(), pokemons -> {
            adapter.submitList(pokemons);
            swipeRefreshLayout.setRefreshing(false);
        });

        viewModel.isLoading().observe(getViewLifecycleOwner(), isLoading ->
                spinKitView.setVisibility(isLoading ? VISIBLE : INVISIBLE));

        viewModel.getCurrentPage().observe(getViewLifecycleOwner(), currentPage ->
                SharedPrefUtil.savePage(getContext(), currentPage));
    }


}
