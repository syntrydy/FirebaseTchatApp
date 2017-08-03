package cm.app.it.gasmyr.letsay.view.discussion;

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
import cm.app.it.gasmyr.letsay.adapter.DiscussionsListAdapter;
import cm.app.it.gasmyr.letsay.core.Discussion;
import cm.app.it.gasmyr.letsay.eventbus.ChangePlace;
import cm.app.it.gasmyr.letsay.eventbus.Place;
import cm.app.it.gasmyr.letsay.util.AppUtils;
import cm.app.it.gasmyr.letsay.view.user.UserListActivity;

public class DiscussionListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Discussion> discussions;
    private DiscussionsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.discussions_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(DiscussionListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        discussions = new ArrayList<>();
        adapter = new DiscussionsListAdapter(discussions, DiscussionListActivity.this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DiscussionListActivity.this,UserListActivity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void createNewDiscussion(ChangePlace changePlace) {
        if(changePlace.getNewPlace().equalsIgnoreCase(Place.CONVERSATION_PLACE)){
            try{
                Intent intent=new Intent(DiscussionListActivity.this,NewConversationActivity.class);
                intent.putExtra("KEY",changePlace.getUser());
                startActivity(intent);
            }catch (Exception e){
                AppUtils.showError(DiscussionListActivity.this,e.getMessage());
            }

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
