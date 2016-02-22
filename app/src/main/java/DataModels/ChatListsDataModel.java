package DataModels;

import java.io.Serializable;

public class ChatListsDataModel implements Serializable{
    private static final long serialVersionUID = 5280398368026441010L;
    public int id;
    public String group_name;
    public String last_message;
    public long last_time;
    public String image_url;
    public int new_count;
}
