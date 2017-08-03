package cm.app.it.gasmyr.letsay.firebase;

import android.content.Context;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import cm.app.it.gasmyr.letsay.adapter.DiscussionsListAdapter;
import cm.app.it.gasmyr.letsay.core.Discussion;
import cm.app.it.gasmyr.letsay.core.Message;
import cm.app.it.gasmyr.letsay.core.app.LetsayApp;
import cm.app.it.gasmyr.letsay.util.AppUtils;

/**
 * Created by gasmyr.mougang on 7/31/17.
 */

public class FirebaseDiscussionFactory extends FirebaseFactory {


    private List<Discussion> discussions;
    private DiscussionsListAdapter discussionsListAdapter;

    public FirebaseDiscussionFactory(Context ctxt, List<Discussion> discussions, DiscussionsListAdapter adapter){
        super();
        this.context=ctxt;
        this.discussions=discussions;
        this.discussionsListAdapter=adapter;
        FirebaseDatabase.getInstance();
    }

    ChildEventListener discussionsChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
          //  AppUtils.showInfo(context,"onChildAdded:" + dataSnapshot.getKey());
            Discussion discussion = dataSnapshot.getValue(Discussion.class);
            if(discussion.getMessage().getFrom().equalsIgnoreCase(new LetsayApp().getCurrentUser().getPhone()) || discussion.getMessage().getTo().equalsIgnoreCase(new LetsayApp().getCurrentUser().getPhone())){
                discussions.add(0,discussion);
                discussionsListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
           // AppUtils.showInfo(context,"onChildChanged:" + dataSnapshot.getKey());
            Discussion discussion = dataSnapshot.getValue(Discussion.class);
            if(discussion.getMessage().getFrom().equalsIgnoreCase(new LetsayApp().getCurrentUser().getPhone()) || discussion.getMessage().getTo().equalsIgnoreCase(new LetsayApp().getCurrentUser().getPhone())){
               // discussions.add(0,discussion);
                discussionsListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
           // AppUtils.showInfo(context,"onChildRemoved:" + dataSnapshot.getKey());
            Discussion discussion = dataSnapshot.getValue(Discussion.class);
            discussionsListAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
          //  AppUtils.showInfo(context,"onChildMoved:" + dataSnapshot.getKey());
            Discussion discussion = dataSnapshot.getValue(Discussion.class);
            discussionsListAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
           // AppUtils.showInfo(context,"on Cancel"+databaseError.toException());
        }
    };

    public void startDiscussionsListener(){
        getBaseDiscussionsReference().addChildEventListener(discussionsChildEventListener);
    }

    public static void setDiscussionLastMessageReference(String key, Message message) {
        getDatabase().getReference(FIREBASE_DISCUSSIONS+key).child("message").setValue(message);
    }
}
