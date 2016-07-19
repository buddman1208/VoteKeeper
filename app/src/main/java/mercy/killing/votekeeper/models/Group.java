package mercy.killing.votekeeper.models;

/**
 * Created by Chad on 7/19/16.
 */

public class Group {
    private String adminid, groupId;
    private String[] voteId, membersId, gcmId;

    public String getAdminid() {
        return adminid;
    }

    public String getGroupId() {
        return groupId;
    }

    public String[] getVoteId() {
        return voteId;
    }

    public String[] getMembersId() {
        return membersId;
    }

    public String[] getGcmId() {
        return gcmId;
    }
}
