package mercy.killing.votekeeper.utils;

import java.util.ArrayList;

/**
 * Created by Chad on 7/19/16.
 */

public class VoteKeeperHelperClass {
    public VoteKeeperHelperClass() {

    }

    public static String convertArraytoString(ArrayList<String> arr) {
        String result = "";
        for (String s : arr) {
            result += (s+",");
        }
        return result;
    }
}
