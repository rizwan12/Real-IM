package rizwansaleem.realim.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rizwansaleem.realim.R;
import rizwansaleem.realim.objects.ChatObject;

/**
 * Created by rizwansaleem on 11/09/15.
 */
public class ChatViewAdapter extends BaseAdapter {

    List<ChatObject> chatList;
    Context mContext;

    public ChatViewAdapter(Context context, List<ChatObject> objects) {
        this.chatList = objects;
        this.mContext = context;
    }

    public void setChatList(List<ChatObject> chatList) {
        this.chatList = chatList;
    }

    @Override
    public int getCount() {
        return chatList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ChatViewHolder viewHolder;
        ChatObject chatObject = chatList.get(position);
        if(row == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.chat_row, parent, false);

            viewHolder = new ChatViewHolder();
            viewHolder.nameText = (TextView) row.findViewById(R.id.name_text);
            if(chatObject.isImage()) {
                viewHolder.chatImage = (ImageView) row.findViewById(R.id.chat_image);
            } else {
                viewHolder.chatText = (TextView) row.findViewById(R.id.chat_text);
            }
            row.setTag(viewHolder);
        } else {
            viewHolder = (ChatViewHolder) row.getTag();
        }
        viewHolder.nameText.setText(chatObject.getChatName());
        if(chatObject.isImage()) {

        } else {
            viewHolder.chatText.setText(chatObject.getChatText());
        }
        return row;
    }

    static class ChatViewHolder {
        TextView nameText;
        TextView chatText;
        ImageView chatImage;
    }

}
