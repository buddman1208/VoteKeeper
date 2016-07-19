package mercy.killing.votekeeper.models;

/**
 * Created by Chad on 7/19/16.
 */

public class Vote {
    private String voteId, title;
    private String[] votecontent, notvotedmemberids;

    public String getVoteId() {
        return voteId;
    }

    public String getTitle() {
        return title;
    }

    public String[] getVotecontent() {
        return votecontent;
    }

    public String[] getNotvotedmemberids() {
        return notvotedmemberids;
    }
}
