package com.ez.sendem.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ez.sendem.R;
import com.ez.sendem.adapter.AppTypeAdapter;
import com.ez.sendem.adapter.object.AppTypeData;
import com.ez.sendem.manager.PageManager;

public class DlgAddNew extends DlgFragment implements AdapterView.OnItemClickListener {
    private View view;
    private ListView listView;
    private AppTypeAdapter adapter;
    private AppTypeData[] appTypes = new AppTypeData[]{};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_addnew, null);

        appTypes = new AppTypeData[]{
                new AppTypeData(R.string.fa_comment, getString(R.string.apptype_sms)),
        };

        listView = (ListView)view.findViewById(R.id.listView);
        adapter = new AppTypeAdapter(getContext(), appTypes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        setTitle(R.string.dialog_addnewtitle);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i == 0){
            PageManager.open_ScheduleNew(getContext());
        }
    }


}
