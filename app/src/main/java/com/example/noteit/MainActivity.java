package com.example.noteit;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.noteit.model.Adapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    RecyclerView noteLists;
    Adapter adapter;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.noteDetailsTitle);
        setSupportActionBar(toolbar);
        fStore = FirebaseFirestore.getInstance();

        Query query = fStore.collection("notes").orderBy("title",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<>

        noteLists=findViewById(R.id.notelist);
        drawerLayout = findViewById(R.id.drawer);
        nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        List<String> titles = new ArrayList<>();
        List<String> content = new ArrayList<>();

        titles.add("First Note");
        content.add("qwertyuiop");

        titles.add("Second Note");
        content.add("qwertyuiopbiabgfefhdvshgygeuuifgygdshvhvhfdhvsjeugyfeuwguigufdsujhfdhvshguegufgyduisgsyiuifgeiwgugieyfiydvsvgef");

        titles.add("Third Note");
        content.add("qwertyuiopghsdkfudhbduhdbhbvheiruhugfdsjkvshgufhugewiufgd");

        adapter = new com.example.noteit.model.Adapter(titles,content);
        noteLists.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        noteLists.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.addNoteFloat);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),AddNote.class));
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.addNote:
                startActivity(new Intent(this,AddNote.class));
                break;
            default:
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.settings){
            Toast.makeText(this, "Settings Menu is Clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}