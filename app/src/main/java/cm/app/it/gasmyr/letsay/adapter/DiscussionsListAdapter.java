package cm.app.it.gasmyr.letsay.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cm.app.it.gasmyr.letsay.R;
import cm.app.it.gasmyr.letsay.core.Discussion;
import cm.app.it.gasmyr.letsay.core.Message;
import cm.app.it.gasmyr.letsay.core.User;
import cm.app.it.gasmyr.letsay.eventbus.ChangePlace;
import cm.app.it.gasmyr.letsay.eventbus.Place;
import cm.app.it.gasmyr.letsay.firebase.FirebaseDiscussionFactory;
import cm.app.it.gasmyr.letsay.firebase.FirebaseFactory;
import cm.app.it.gasmyr.letsay.firebase.FirebaseUserFactory;
import cm.app.it.gasmyr.letsay.util.AppUtils;

/**
 * Created by gasmyr.mougang on 7/31/17.
 */

public class DiscussionsListAdapter extends RecyclerView.Adapter<DiscussionsListAdapter.MyViewHolder>{
    private List<Discussion> discussions;
    private Context context;
    private FirebaseDiscussionFactory firebaseFactory;

    public DiscussionsListAdapter(List<Discussion> discussions, Context context) {
        this.discussions = discussions;
        this.context = context;
        FirebaseFactory.getDatabase();
        firebaseFactory=new FirebaseDiscussionFactory(context,discussions,this);
        firebaseFactory.startDiscussionsListener();
    }

    @Override
    public DiscussionsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discussion_item, parent, false);
        return new DiscussionsListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DiscussionsListAdapter.MyViewHolder holder, int position) {
        final Discussion discussion = discussions.get(position);
        holder.nameV.setText(discussion.getKname());
        holder.lastMessageV.setText(discussion.getMessage().getContent());
        holder.item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new ChangePlace(Place.CONVERSATION_PLACE,discussion.getKey()));
            }
        });


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameV,lastMessageV;
        public CardView item_view;
        public ImageView avatarV;

        public MyViewHolder(@NonNull View view) {
            super(view);
            item_view = view.findViewById(R.id.item_view);
            nameV = view.findViewById(R.id.discussion_user_name);
            lastMessageV =view.findViewById(R.id.discussion_last_message);
            avatarV = view.findViewById(R.id.discussion_photo);
        }
    }

    @Override
    public int getItemCount() {
        return discussions.size();
    }
}
