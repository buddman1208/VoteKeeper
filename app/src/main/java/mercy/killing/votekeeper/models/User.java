package mercy.killing.votekeeper.models;

/**
 * Created by Chad on 7/19/16.
 */

public class User {
    private String id, pw, name, gcmId;
    private String[] groupId;

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getName() {
        return name;
    }

    public String getGcmId() {
        return gcmId;
    }

    public String[] getGroupId() {
        return groupId;
    }
}
