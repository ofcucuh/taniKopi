package com.selatan.tanikopi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class FragmentTab extends Fragment {

    private RecyclerView recyclerView;
    private AdapterStok adapter;
    private ArrayList<IsiStok> isiStoks;

    @SuppressLint("ValidFragment")
    public FragmentTab(ArrayList<IsiStok> isiStoks) {
        this.isiStoks = isiStoks;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterStok(isiStoks,getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }
}