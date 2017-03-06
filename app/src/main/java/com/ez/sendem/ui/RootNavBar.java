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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.ez.sendem.R;
import com.ez.sendem.manager.FontManager;
import com.ez.sendem.manager.PageManager;

/*
note untuk class ini:
-. class ini adalah class halaman utama dari aplikasi Send'em
-. class ini terdiri dari drawer(bagian kiri)
*/

/*
//comment : onItemClick - 2
    1. ketika pasang setOnItemClickListener(this), jangan lupa tambahkan "implements AdapterView.OnItemClickListener"
*/
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

    /*
    //comment : rootnavbar - 1
        1. indexing dari semua menu di drawer
    */
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

        /*
        //comment : drawermenu -- start
            1. jika tidak ada kebutuhan khusus, selalu code sedemikian rupa
        */
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
        /*
        //comment : drawermenu -- end
        */

        drawerHeader = getLayoutInflater().inflate(R.layout.drawerheader, null);
        listViewDrawerMenu = (ListView)findViewById(R.id.listViewDrawerMenu);
        listViewDrawerMenu.addHeaderView(drawerHeader);

        imageView = (ImageView)drawerHeader.findViewById(R.id.imageView);

        /*
        //comment :
            1. library Glide digunakan untuk set image.
            2. Penggunaan library ini untuk mencegah error OutOfMemory(OOM) karena image dari android dikenal sering OOM
            3. Library ini juga memudahkan membuat gambar rounded.
        */
        Glide.with(this).load(R.drawable.ic_launcher).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });

        /*
        //comment : onItemClick - 4
            -. pendaftaran menu untuk drawer
        */
        arrDrawerMenuItems = new ArrayList<>();
        arrDrawerMenuItems.add(new DrawerMenuItem(INDEX_SCHEDULED, R.string.fa_calendar, getString(R.string.drw_sheduled), false));
        arrDrawerMenuItems.add(new DrawerMenuItem(INDEX_INACTIVE, R.string.fa_bell_slash, getString(R.string.drw_inactive), false));
        arrDrawerMenuItems.add(new DrawerMenuItem(INDEX_HISTORY, R.string.fa_history, getString(R.string.drw_history), true));
        arrDrawerMenuItems.add(new DrawerMenuItem(INDEX_EXIT, R.string.fa_close, getString(R.string.drw_exit), false));

        adapter = new DrawerMenuAdapter(this, arrDrawerMenuItems);
        content = (FrameLayout)findViewById(R.id.content);
        /*
        //comment : onItemClick - 5
            -. set adapter ke listview
        */
        listViewDrawerMenu.setAdapter(adapter);
        /*
        //comment : onItemClick - 1
            1. untuk mengatur apa yang dilakukan ketika klik item di list drawer menu, maka kita harus pasang setOnItemClickListener
        */
        listViewDrawerMenu.setOnItemClickListener(this);
        adapter.setSelectedIndex(INDEX_SCHEDULED);

        /*
        //comment : rootnavbar - 2
            1. selalu buka halaman schedule pas awal
        */
        CURRENT_INDEX = INDEX_SCHEDULED;
        PageManager.open_ScheduledAct(this);
    }

    public void setTitle(int strResId){
        /*
        //comment :
            1. untuk set Title di Toolbar
        */
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

    /*
    //comment : rootnavbar - 3
        1. function ini adalah function bawaan android
        2. function ini mengatur back (physical button) dari aplikasi
        3. fungsi ini dimodif menjadi:
            -. ketika drawer terbuka lalu tekan back, maka close drawer
            -. ketika drawer tertutup lalu tekan back, maka open drawer
    */
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
        return super.onOptionsItemSelected(item);
    }

    /*
    //comment : onItemClick - 3
        1. setelah implements "AdapterView.OnItemClickListener", kita harus menambahkan function dibawah ini
        2. Kita perlu mengecek position dari item yang diklik dan apa yang harus dilakukan
        3. position dari drawer bergantung pada saat add-nya (comment: onItemClick - 4)
    */
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

            /*
            //comment : onItemClick - 6
                -. setelah klik menu drawer, jangan lupa tutup drawernya
            */
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

/*
//comment : drawermenuadapter - 1
    1. DrawerMenuAdapter ini mengatur layout item yang ada di dalam listview
    2. adapter bisa saja extends BaseAdapter atau CursorAdapter(isi listview dari database), atau adapter lainnya sesuai kebutuhan
*/
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


    /*
    //comment : drawermenuadapter - 2
        1. selalu pakai class ViewHolder untuk list semua component yang dipakai. Hal ini untuk mencegah rendering berlebihan yang dapat menyebabkan OutOfMemory
    */
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

    /*
    //comment : drawermenuadapter - 3
        1. function getView untuk mengatur tampilan dari item listview
    */
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