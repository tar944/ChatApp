package DataModels;

import java.io.Serializable;

public class MainChatDataModel implements Serializable {
    public int msg_state;
    public String message;
    public long received_time;
    public String image_name;
    public String image_size;
    public String image_url;
    public boolean msg_type;
}
