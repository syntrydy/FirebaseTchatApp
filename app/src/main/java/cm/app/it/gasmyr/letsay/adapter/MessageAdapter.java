package cm.app.it.gasmyr.letsay.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.List;

import cm.app.it.gasmyr.letsay.R;
import cm.app.it.gasmyr.letsay.core.Message;
import cm.app.it.gasmyr.letsay.core.app.LetsayApp;
import cm.app.it.gasmyr.letsay.firebase.FirebaseFactory;
import cm.app.it.gasmyr.letsay.firebase.FirebaseMessageFactory;

/**
 * Created by gasmyr.mougang on 7/23/17.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {


    private List<Message> messages;
    private Context context;
    private FirebaseMessageFactory firebaseFactory;

    public MessageAdapter(List<Message> messages, Context context, String conKey) {
        this.messages = messages;
        this.context = context;
        FirebaseFactory.getDatabase();
        firebaseFactory=new FirebaseMessageFactory(context,messages,this);
        firebaseFactory.startMessagesListener(conKey);
    }

    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new MessageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MyViewHolder holder, int position) {
        final Message message = messages.get(position);
        holder.messageContent.setText(message.getContent());
        if(message.isMine(new LetsayApp().getCurrentUser().getPhone())){
            holder.item_view.setCardBackgroundColor(Color.BLUE);
            holder.messageContent.setTextColor(Color.WHITE);
        }
        else {
            holder.item_view.setCardBackgroundColor(Color.GREEN);
        }
        holder.item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView messageContent;
        public CardView item_view;


        public MyViewHolder(@NonNull View view) {
            super(view);
            item_view = view.findViewById(R.id.item_view);
            messageContent = view.findViewById(R.id.conv_message_text);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}


