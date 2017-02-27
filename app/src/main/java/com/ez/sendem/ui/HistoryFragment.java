package com.ez.sendem.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ez.sendem.R;
import com.ez.sendem.adapter.ScheduledAdapter;
import com.ez.sendem.db.RealmHelper;
import com.ez.sendem.db.tables.Table_Scheduled;
import com.ez.sendem.manager.FontManager;
import com.ez.sendem.manager.PageManager;

import io.realm.RealmResults;

public class HistoryFragment extends RootFrag implements View.OnClickListener, AdapterView.OnItemClickListener{
    private static HistoryFragment fragment;
    public static HistoryFragment newInstance() {
        fragment = new HistoryFragment();
        return fragment;
    }

    public static HistoryFragment getInstance(){
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

        getActivity().setTitle(R.string.drw_history);

        fab_new = (FloatingActionButton)view.findViewById(R.id.fab_new);
        FontManager drable = new FontManager.FontManagerBuilder(getContext(), R.string.fa_plus).build();
        fab_new.setImageDrawable(drable);
        fab_new.setOnClickListener(this);

        listView = (ListView)view.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        RealmResults<Table_Scheduled> schedule= RealmHelper.getRealm().where(Table_Scheduled.class).findAll();
        adapter = new ScheduledAdapter(getContext(), schedule);
        listView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RealmHelper.getRealm().close();
    }

    @Override
    public void onClick(View view) {
        if(view.equals(fab_new)){
            PageManager.open_ScheduleNew(getContext());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        long sch_id = adapter.getItemId(position);
//        PageManager.open_ViewScheduledDetail(getContext(), (int)sch_id);
    }
}
