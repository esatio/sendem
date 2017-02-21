package com.ez.sendem.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.ez.sendem.R;
import com.ez.sendem.manager.FontManager;
import com.ez.sendem.manager.PageManager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RootNavBar extends RootAct
        implements AdapterView.OnItemClickListener, RootFrag.OnFragmentInteractionListener {

    private View drawerHeader;
    private ListView listViewDrawerMenu;
    private FrameLayout content;
    private DrawerMenuAdapter adapter;
    private ArrayList<DrawerMenuItem> arrDrawerMenuItems;

    //header item
    private ImageView imageView;
    /////////////

    protected static int CURRENT_INDEX = 1;
    private static int INDEX_SCHEDULED = 1;
    private static int INDEX_INACTIVE= 2;
    private static int INDEX_HISTORY = 3;
    private static int INDEX_EXIT = 4;

    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.rootnavbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvTitle = (TextView)findViewById(R.id.tvTitle);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drawerHeader = getLayoutInflater().inflate(R.layout.drawerheader, null);
        listViewDrawerMenu = (ListView)findViewById(R.id.listViewDrawerMenu);
        listViewDrawerMenu.addHeaderView(drawerHeader);

        imageView = (ImageView)drawerHeader.findViewById(R.id.imageView);

        Glide.with(this).load(R.drawable.ic_launcher).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });

        arrDrawerMenuItems = new ArrayList<>();
        arrDrawerMenuItems.add(new DrawerMenuItem(INDEX_SCHEDULED, R.string.fa_calendar, getString(R.string.drw_sheduled), false));
        arrDrawerMenuItems.add(new DrawerMenuItem(INDEX_INACTIVE, R.string.fa_bell_slash, getString(R.string.drw_inactive), false));
        arrDrawerMenuItems.add(new DrawerMenuItem(INDEX_HISTORY, R.string.fa_history, getString(R.string.drw_history), true));
        arrDrawerMenuItems.add(new DrawerMenuItem(INDEX_EXIT, R.string.fa_close, getString(R.string.drw_exit), false));

        adapter = new DrawerMenuAdapter(this, arrDrawerMenuItems);
        content = (FrameLayout)findViewById(R.id.content);
        listViewDrawerMenu.setAdapter(adapter);
        listViewDrawerMenu.setOnItemClickListener(this);

        CURRENT_INDEX = INDEX_SCHEDULED;
        PageManager.open_ScheduledAct(this);
        adapter.setSelectedIndex(INDEX_SCHEDULED);
    }

    public void setTitle(int strResId){
        tvTitle.setText(strResId);
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    public void setContentView(int layoutResID){
        content.addView(getLayoutInflater().inflate(layoutResID, null));
    }

    public void setContentView(View view){
        content.addView(view);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerVisible(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else{
            drawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.equals(listViewDrawerMenu)){
            if(i != adapter.getSelectedIndex()) {
                if (i == INDEX_SCHEDULED) {
                    PageManager.open_ScheduledAct(this);
                } else if (i == INDEX_INACTIVE) {
                    PageManager.open_InactiveAct(this);
                } else if (i == INDEX_HISTORY) {
                    PageManager.open_HistoryAct(this);
                } else if (i == INDEX_EXIT) {
                    PageManager.exit_App(this);
                }
                CURRENT_INDEX = i;
                adapter.setSelectedIndex(i);
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

class DrawerMenuAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<DrawerMenuItem> drawerMenuItems;
    private int selectedIndex;

    public DrawerMenuAdapter(Context context, ArrayList<DrawerMenuItem> arrMenuItemList){
        this.mContext = context;
        this.drawerMenuItems = arrMenuItemList;
    }

    public void setDrawerMenuItems(ArrayList<DrawerMenuItem> arrMenuItemList){
        this.drawerMenuItems = arrMenuItemList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return drawerMenuItems.size();
    }

    @Override
    public Object getItem(int i) {
        return drawerMenuItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return drawerMenuItems.get(i)._id;
    }

    class ViewHolder{
        LinearLayout lyt_item, lyt_item_selected;
        ImageView ivItem, ivItem_selected;
        TextView tvItem, tvItem_selected;
        View divider;
    }

    public void setSelectedIndex(int selectedIndex){
        this.selectedIndex = selectedIndex;
        notifyDataSetChanged();
    }

    public int getSelectedIndex(){
        return selectedIndex;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if(view==null){
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            view = inflater.inflate(R.layout.drawermenuitem, null);

            viewHolder = new ViewHolder();
            viewHolder.lyt_item = (LinearLayout)view.findViewById(R.id.lyt_item);
            viewHolder.ivItem = (ImageView)view.findViewById(R.id.ivItem);
            viewHolder.tvItem = (TextView)view.findViewById(R.id.tvItem);

            viewHolder.lyt_item_selected = (LinearLayout)view.findViewById(R.id.lyt_item_selected);
            viewHolder.ivItem_selected = (ImageView)view.findViewById(R.id.ivItem_selected);
            viewHolder.tvItem_selected = (TextView)view.findViewById(R.id.tvItem_selected);

            viewHolder.divider = (View)view.findViewById(R.id.divider);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        DrawerMenuItem item = drawerMenuItems.get(i);
        viewHolder.tvItem.setText(item.menuText);
        viewHolder.tvItem_selected.setText(item.menuText);

        FontManager.FontManagerBuilder builder = new FontManager.FontManagerBuilder(mContext, item.menuIconResId);
        builder.setColor(ContextCompat.getColor(mContext, R.color.colorTextPrimary));
        FontManager drable = builder.build();
        viewHolder.ivItem.setImageDrawable(drable);

        builder.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        FontManager drableSelected = builder.build();
        viewHolder.ivItem_selected.setImageDrawable(drableSelected);

        if(item._id == selectedIndex){
            viewHolder.lyt_item.setVisibility(View.GONE);
            viewHolder.lyt_item_selected.setVisibility(View.VISIBLE);
        }else{
            viewHolder.lyt_item.setVisibility(View.VISIBLE);
            viewHolder.lyt_item_selected.setVisibility(View.GONE);
        }

        if(item.isShowBottomDivider){
            viewHolder.divider.setVisibility(View.VISIBLE);
        }else{
            viewHolder.divider.setVisibility(View.GONE);
        }

        return view;
    }
}

class DrawerMenuItem{
    int _id;
    int menuIconResId;
    String menuText;
    boolean isShowBottomDivider;

    public DrawerMenuItem(int _id, int menuIconResId, String menuText, boolean isShowBottomDivider){
        this._id = _id;
        this.menuIconResId = menuIconResId;
        this.menuText = menuText;
        this.isShowBottomDivider = isShowBottomDivider;
    }
}