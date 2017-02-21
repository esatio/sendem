package com.ez.sendem.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ez.sendem.R;
import com.ez.sendem.adapter.ScheduledAdapter;
import com.ez.sendem.dialog.DlgAddNew;
import com.ez.sendem.manager.FontManager;
import com.ez.sendem.manager.PageManager;

import io.realm.Sort;

public class ScheduledFragment extends RootFrag implements View.OnClickListener, AdapterView.OnItemClickListener{
    private static ScheduledFragment fragment;
    public static ScheduledFragment newInstance() {
        fragment = new ScheduledFragment();
        return fragment;
    }

    public static ScheduledFragment getInstance(){
        return fragment;
    }

    private View view;
    private ListView listView;
    private ScheduledAdapter adapter;
    private FloatingActionButton fab_new;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.scheduledfragment, container, false);

        getActivity().setTitle(R.string.drw_sheduled);

        fab_new = (FloatingActionButton)view.findViewById(R.id.fab_new);
        FontManager drable = new FontManager.FontManagerBuilder(getContext(), R.string.fa_plus).build();
        fab_new.setImageDrawable(drable);
        fab_new.setOnClickListener(this);

        listView = (ListView)view.findViewById(R.id.listView);
        adapter = new ScheduledAdapter(getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.equals(fab_new)){
//            PageManager.open_ScheduleNew(getContext());
            DlgAddNew dialog = new DlgAddNew();
            dialog.show(getFragmentManager(), "");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        long sch_id = adapter.getItemId(position);
//        PageManager.open_ViewScheduledDetail(getContext(), (int)sch_id);
    }
}
