package rizwansaleem.realim.objects;

import com.parse.ParseFile;

/**
 * Created by rizwansaleem on 11/09/15.
 */
public class ChatObject {

    private String chatName;
    private String chatText;
    private ParseFile imageData;
    private String imageUrl;
    private boolean isImage = false;

    public ChatObject() {

    }

    public ChatObject(String chatName, String chatText, ParseFile imageData, String imageUrl, boolean isImage) {
        this.chatName = chatName;
        this.chatText = chatText;
        this.imageData = imageData;
        this.imageUrl = imageUrl;
        this.isImage = isImage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public ParseFile getImageData() {
        return imageData;
    }

    public void setImageData(ParseFile imageData) {
        this.imageData = imageData;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setIsImage(boolean isImage) {
        this.isImage = isImage;
    }

    public String getChatText() {
        return chatText;
    }

    public void setChatText(String chatText) {
        this.chatText = chatText;
    }
}
