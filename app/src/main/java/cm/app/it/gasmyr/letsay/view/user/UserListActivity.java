package cm.app.it.gasmyr.letsay.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cm.app.it.gasmyr.letsay.R;
import cm.app.it.gasmyr.letsay.adapter.UserListAdapter;
import cm.app.it.gasmyr.letsay.core.User;
import cm.app.it.gasmyr.letsay.eventbus.ChangePlace;
import cm.app.it.gasmyr.letsay.eventbus.Place;
import cm.app.it.gasmyr.letsay.view.discussion.DiscussionListActivity;

public class UserListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<User> users;
    private UserListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.users_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(UserListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        users = new ArrayList<>();
        adapter = new UserListAdapter(users, UserListActivity.this);
        recyclerView.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void gotoDiscussionListPlace(ChangePlace changePlace) {
        if(changePlace.getNewPlace().equalsIgnoreCase(Place.DISCUSSIONS_PLACE)){
            Intent intent=new Intent(UserListActivity.this, DiscussionListActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


}
