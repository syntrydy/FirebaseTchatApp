package cm.app.it.gasmyr.letsay.view.discussion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cm.app.it.gasmyr.letsay.R;
import cm.app.it.gasmyr.letsay.adapter.MessageAdapter;
import cm.app.it.gasmyr.letsay.core.Discussion;
import cm.app.it.gasmyr.letsay.core.Message;
import cm.app.it.gasmyr.letsay.core.app.LetsayApp;
import cm.app.it.gasmyr.letsay.eventbus.ChangePlace;
import cm.app.it.gasmyr.letsay.firebase.FirebaseDiscussionFactory;
import cm.app.it.gasmyr.letsay.firebase.Tchat;
import cm.app.it.gasmyr.letsay.util.AppUtils;

public class NewConversationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Message> messages;
    private MessageAdapter adapter;
    private EditText editText;
    private String key;
    private String title;
    private String to;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_conversation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        key = getIntent().getStringExtra("KEY");
        from = new LetsayApp().getCurrentUser().getPhone();
        if (from.equalsIgnoreCase(key.split("-")[0])) {
            to = key.split("-")[1];
        } else {
            to = key.split("-")[0];
        }
        editText = (EditText) findViewById(R.id.message);
        recyclerView = (RecyclerView) findViewById(R.id.messages_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(NewConversationActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        messages = new ArrayList<>();
        adapter = new MessageAdapter(messages, NewConversationActivity.this, key);
        recyclerView.setAdapter(adapter);
        title = AppUtils.getContactName(NewConversationActivity.this, key.split("-")[0]).toUpperCase().substring(0, 2) + "-" + AppUtils.getContactName(NewConversationActivity.this, key.split("-")[1]).toUpperCase().substring(0, 2);
        setTitle(title);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void nothing(ChangePlace changePlace) {

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

    public void sendMessage(View view) {
        Message message = new Message();
        String text=editText.getText().toString().trim();
        if(text.length()>=2){
            Date date=new Date();
            message.setContent(text);
            message.setFrom(from);
            message.setTo(to);
            message.setDate(date);
            Tchat.addMessageToDiscussion(message, key);
            FirebaseDiscussionFactory.setDiscussionLastMessageReference(key,message);
        }
        editText.setText("");

    }
}
