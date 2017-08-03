package cm.app.it.gasmyr.letsay.firebase;

import android.content.Context;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import cm.app.it.gasmyr.letsay.adapter.UserListAdapter;
import cm.app.it.gasmyr.letsay.core.User;
import cm.app.it.gasmyr.letsay.core.app.LetsayApp;
import cm.app.it.gasmyr.letsay.util.AppUtils;

/**
 * Created by gasmyr.mougang on 7/31/17.
 */

public class FirebaseUserFactory extends  FirebaseFactory {

    private List<User> users;
    private UserListAdapter userListAdapter;

    public FirebaseUserFactory(Context ctxt, List<User> users, UserListAdapter adapter){
        super();
        this.context=ctxt;
        this.users=users;
        this.userListAdapter=adapter;
        FirebaseDatabase.getInstance();
    }

    ChildEventListener usersChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
           // AppUtils.showInfo(context,"onChildAdded:" + new LetsayApp().getCurrentUser().getPhone());
            User user = dataSnapshot.getValue(User.class);
            if(user!=null && user.getPhone()!=null){
                if(!user.getPhone().equalsIgnoreCase(new LetsayApp().getCurrentUser().getPhone())){
                    users.add(0,user);
                    userListAdapter.notifyDataSetChanged();
                }
            }

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
           // AppUtils.showInfo(context,"onChildChanged:" + dataSnapshot.getKey());
            //User user = dataSnapshot.getValue(User.class);
           // if(user!=null && user.getPhone()!=null) {
            //    if (!user.getPhone().equalsIgnoreCase(new LetsayApp().getCurrentUser().getPhone())) {
               //     users.add(0, user);
                    userListAdapter.notifyDataSetChanged();
              //  }
            //}
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
           // AppUtils.showInfo(context,"onChildRemoved:" + dataSnapshot.getKey());
            User user = dataSnapshot.getValue(User.class);
            if(user!=null && user.getPhone()!=null) {
                if (!user.getPhone().equalsIgnoreCase(new LetsayApp().getCurrentUser().getPhone())) {
                    users.add(0, user);
                    userListAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            //AppUtils.showInfo(context,"onChildMoved:" + dataSnapshot.getKey());
            User user = dataSnapshot.getValue(User.class);
            userListAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
           // AppUtils.showInfo(context,"on Cancel"+databaseError.toException());
        }
    };

    public void startUsersListener(){
        getBaseUsersReference().addChildEventListener(usersChildEventListener);
    }

}
