package rizwansaleem.realim.objects;

/**
 * Created by rizwansaleem on 11/09/15.
 */
public class ChatObject {

    private String chatName;
    private String chatText;
    private byte[] imageData;
    private boolean isImage = false;

    public ChatObject() {

    }

    public ChatObject(String chatName, String chatText, byte[] imageData, boolean isImage) {
        this.chatName = chatName;
        this.chatText = chatText;
        this.imageData = imageData;
        this.isImage = isImage;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
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
