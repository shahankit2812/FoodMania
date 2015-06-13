package com.example.ankit_pc.foodmania;

import java.util.ArrayList;
import java.util.Locale;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.ListView;


public class MenuActivity extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link android.support.v4.view.ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    DBHelper db;
    ArrayList<Category> array_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent i = getIntent();
        int res_id = i.getIntExtra("restaurant_id", 0);
        String res_name = i.getStringExtra("restaurant_name");
        //System.out.println(res_id);
        db = new DBHelper(this, null, null, 1);
        array_list = new ArrayList();
        Cursor res = db.getReadableDatabase().rawQuery("select * from categories where resid =" + res_id + "", null);
        //System.out.println(res);
        if (res != null) {
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                array_list.add(new Category(Integer.parseInt(res.getString(0)), res.getString(1)));
                res.moveToNext();
            }
        }
        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.setTitle(res_name);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

//        // For each of the sections in the app, add a tab to the action bar.
//        for (int j = 0; i < mSectionsPagerAdapter.getCount(); i++) {
//            // Create a tab with text corresponding to the page title defined by
//            // the adapter. Also specify this Activity object, which implements
//            // the TabListener interface, as the callback (listener) for when
//            // this tab is selected.
//            actionBar.addTab(
//                    actionBar.newTab()
//                            .setText(mSectionsPagerAdapter.getPageTitle(i))
//                            .setTabListener(this));
//        }
        for (Category temp : array_list) {

            actionBar.addTab(actionBar.newTab().setText(temp.getCat())
                    .setTabListener(this));

        }
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        //System.out.println("onTabsel"+tab.getPosition());
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            //System.out.println ("getItem"+(array_list.get(position).getId()));
            args.putInt(PlaceholderFragment.ARG_SECTION_NUMBER, array_list.get(position).getId());
            fragment.setArguments(args);
            return fragment;
         }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return array_list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            //System.out.println(position+"char");
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends ListFragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private ArrayList<Items> items;
        private DBHelper data;
        private XYZAdapter adapter;
        private ListView li;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
////            System.out.println(sectionNumber);
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
//            TextView t=(TextView)rootView.findViewById(R.id.section_label);
//            t.setText("hii");
            //li=(ListView)rootView.findViewById(R.id.listView);
            return rootView;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Bundle args = getArguments();
            //System.out.println(args.size());
//            System.out.println(args.getInt(ARG_SECTION_NUMBER));
//            ArrayList<String> exampleListItemList = new ArrayList();
//            exampleListItemList.add("Example 1");
//            exampleListItemList.add("Example 2");
//            exampleListItemList.add("Example 3");
//            exampleListItemList.add("Example 4");
            int index=args.getInt(ARG_SECTION_NUMBER);
            //System.out.println(index);
            data=new DBHelper(getActivity(), null, null, 1);
            Cursor res = data.getReadableDatabase().rawQuery("select * from fooditems where id1 =" + (index-1) + "", null);
                //System.out.println(res);
            items=new ArrayList();
                if (res != null) {
                    res.moveToFirst();
                    while (res.isAfterLast() == false) {
                        items.add(new Items(Integer.parseInt(res.getString(0)), res.getString(1),Double.parseDouble(res.getString(2)),res.getString(3)));
                        res.moveToNext();
                    }
                }
            //mAdapter = new ExampleListAdapter(getActivity(), exampleListItemList);
            //li = getListView();
            //System.out.println(items.get(0).getFooditem());
            adapter=new XYZAdapter(getActivity(), items);
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                    android.R.layout.simple_list_item_1, android.R.id.text1, exampleListItemList);
            setListAdapter(adapter);
//            li.setAdapter(new ArrayAdapter<String>(getActivity(),exampleListItemList));
            //System.out.println("hi");
            data.close();
        }

        private class XYZAdapter extends BaseAdapter
        {
            Context mContext;
            ArrayList<Items> item;

            public XYZAdapter(Context context,ArrayList<Items> array_list) {
                this.mContext=context;
                this.item=array_list;
            }

            @Override
            public int getCount() {
                    return this.item.size();
            }

            @Override
            public Object getItem(int position)
            {
                return this.item.get(position);

            }

            @Override
            public long getItemId(int position) {

                return this.item.get(position).getId2();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                //LayoutInflater li=LayoutInflater.from(mContext);
                LayoutInflater li=(LayoutInflater)mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                View customView = null;
                try {
                    customView = li.inflate(R.layout.activity_next2, null);
                    Items i = item.get(position);
                    TextView foodItem = (TextView) customView.findViewById(R.id.textView1);
                    if (i.getVng().equals("v")) {
                        foodItem.setText(i.getFooditem());
                        foodItem.setTextColor(Color.GREEN);
                    } else {
                        foodItem.setText(i.getFooditem());
                        foodItem.setTextColor(Color.RED);
                    }
                    TextView price = (TextView) customView.findViewById(R.id.textView4);
                    price.setText("" + i.getPrice());
                }
                catch(Exception ex)
                {
                    Log.d("Error", ex.getLocalizedMessage());
                }
                return customView;
            }
        }
}
}