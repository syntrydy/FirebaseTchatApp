package cm.app.it.gasmyr.letsay.firebase;

import android.content.Context;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.List;

import cm.app.it.gasmyr.letsay.adapter.MessageAdapter;
import cm.app.it.gasmyr.letsay.core.Message;
import cm.app.it.gasmyr.letsay.util.AppUtils;

/**
 * Created by gasmyr.mougang on 7/31/17.
 */

public class FirebaseMessageFactory extends FirebaseFactory {
    private MessageAdapter messageAdapter;
    private List<Message> messages;

    public FirebaseMessageFactory(Context ctxt, List<Message> messages, MessageAdapter adapter){
        this.context=ctxt;
        this.messages=messages;
        this.messageAdapter=adapter;
    }
    ChildEventListener messagesChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
           // AppUtils.showInfo(context,"onChildAdded:" + dataSnapshot.getKey());
            Message message = dataSnapshot.getValue(Message.class);
            messages.add(message);
            messageAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            //AppUtils.showInfo(context,"onChildChanged:" + dataSnapshot.getKey());
            Message message = dataSnapshot.getValue(Message.class);
            messageAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
           // AppUtils.showInfo(context,"onChildRemoved:" + dataSnapshot.getKey());
            Message message = dataSnapshot.getValue(Message.class);
            messages.add(0,message);
            messageAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
           // AppUtils.showInfo(context,"onChildMoved:" + dataSnapshot.getKey());
            Message message = dataSnapshot.getValue(Message.class);
            messages.add(0,message);
            messageAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
           // AppUtils.showInfo(context,"on Cancel"+databaseError.toException());
        }
    };

    public void startMessagesListener(String key){
        getDiscussionsMessagesReference(key).addChildEventListener(messagesChildEventListener);
    }
}
