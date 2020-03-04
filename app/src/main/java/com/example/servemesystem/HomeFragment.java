package com.example.servemesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import static android.widget.AdapterView.*;

public class HomeFragment extends Fragment {
    SearchView mysearch;
    ListView mylist;
    ArrayList<String> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drawer_fragment_home, container, false);
        this.onSearch(view);
        return view;
    }


    private void onSearch(View view) {

mysearch=(SearchView)view.findViewById(R.id.search_bar);
mylist=(ListView)view.findViewById(R.id.lists);
            final String []listviewitems=new String[]{"test1","test2","test3"};

        final ArrayAdapter<String> adapter=
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listviewitems);
        mylist.setAdapter(adapter);

        mysearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }



        });

    }
}