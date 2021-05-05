package za.co.devs.smarttrace.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import za.co.devs.smarttrace.R;
import za.co.devs.smarttrace.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private RelativeLayout mDrawerRelativeLayout;
    private ListView mDrawerList;
    private String[] mOptionMenu;
    Fragment frag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNavigationDrawer();
        //Make Main Fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, new MainFragment()); // replace a Fragment with Frame Layout
        transaction.commit(); // commit the changes

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    private void setNavigationDrawer() {

        drawerLayout = findViewById(R.id.drawer_layout); // initiate a DrawerLayout
        mDrawerList = findViewById(R.id.list_view_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icons_menu_24);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                switch (position) {
                    case 0:
                        frag = new MainFragment();
                        break;
                }

                FragmentManager fragmentManager = getSupportFragmentManager();

                if (frag != null) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, frag).commit();

                    mDrawerList.setItemChecked(position, true);

                    getSupportActionBar().setTitle(mOptionMenu[position]);
                }

                drawerLayout.closeDrawers();
            }
        });
        mDrawerList.setItemChecked(0, true);

        drawerLayout.closeDrawers(); // close the all open Drawer Views
        mOptionMenu = new String[]{"Profile",
                "Home"};
        mDrawerList.setAdapter(new ArrayAdapter<String>(getSupportActionBar()
                .getThemedContext(), android.R.layout.simple_list_item_1,
                mOptionMenu) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textView = view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(20);
                return view;

            }
        });
        //NavigationView navView = findViewById(R.id.navigation); // initiate a Navigation View

        // implement setNavigationItemSelectedListener event on NavigationView
//        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                Fragment frag = null; // create a Fragment Object
//                int itemId = menuItem.getItemId(); // get selected menu item's id
//                // check selected menu item's id and replace a Fragment Accordingly
//                if (itemId == R.id.first) {
//                    frag = new MainFragment();
//                } else if (itemId == R.id.second) {
//                    frag = new MainFragment();
//                } else if (itemId == R.id.third) {
//                    frag = new MainFragment();
//                }
//
//                if (frag != null) {
//                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.frame, frag); // replace a Fragment with Frame Layout
//                    transaction.commit(); // commit the changes
//                    drawerLayout.closeDrawers(); // close the all open Drawer Views
//                    return true;
//                }
//                return false;
//            }
//        });
    }
}
