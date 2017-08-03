package cm.app.it.gasmyr.letsay.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.List;

import cm.app.it.gasmyr.letsay.R;
import cm.app.it.gasmyr.letsay.core.Discussion;
import cm.app.it.gasmyr.letsay.core.Message;
import cm.app.it.gasmyr.letsay.core.User;
import cm.app.it.gasmyr.letsay.core.app.LetsayApp;
import cm.app.it.gasmyr.letsay.eventbus.ChangePlace;
import cm.app.it.gasmyr.letsay.eventbus.Place;
import cm.app.it.gasmyr.letsay.firebase.FirebaseFactory;
import cm.app.it.gasmyr.letsay.firebase.FirebaseUserFactory;
import cm.app.it.gasmyr.letsay.firebase.Tchat;
import cm.app.it.gasmyr.letsay.util.AppUtils;

/**
 * Created by gasmyr.mougang on 7/19/17.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {


    private List<User> users;
    private Context context;
    private FirebaseUserFactory firebaseFactory;

    public UserListAdapter(List<User> users, Context context) {
        this.users = users;
        this.context = context;
        FirebaseFactory.getDatabase();
        firebaseFactory=new FirebaseUserFactory(context,users,this);
        firebaseFactory.startUsersListener();
    }

    @Override
    public UserListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserListAdapter.MyViewHolder holder, int position) {
        final User user = users.get(position);
        holder.userNameV.setText(user.getName());
        holder.userPhoneV.setText(user.getPhone());
        if(user.isOnline()){
            holder.userStatusV.setText("Online");
        }
        else {
            holder.userStatusV.setText("offline");
        }
        holder.item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        holder.item_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.item_view.setCardBackgroundColor(Color.TRANSPARENT);
                Discussion discussion=new Discussion();
                String from=new LetsayApp().getCurrentUser().getPhone();
                discussion.setKey(from+"-"+user.getPhone());
                discussion.setKname(AppUtils.getContactName(context,from)+"-"+AppUtils.getContactName(context,user.getPhone()));
                discussion.setMessage(new Message("undefined",new LetsayApp().getCurrentUser().getPhone(),user.getPhone(),new Date()));
                Tchat.registerNewDiscussion(discussion);
                EventBus.getDefault().post(new ChangePlace(Place.DISCUSSIONS_PLACE));
                return false;
            }
        });


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView userNameV,userPhoneV,userStatusV;
        public CardView item_view;
        public ImageView userAvatarIV;

        public MyViewHolder(@NonNull View view) {
            super(view);
            item_view = view.findViewById(R.id.item_view);
            userNameV = view.findViewById(R.id.user_list_name);
            userPhoneV =view.findViewById(R.id.user_list_phone);
            userStatusV = view.findViewById(R.id.user_list_online);
            userAvatarIV = view.findViewById(R.id.user_list_photo);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
