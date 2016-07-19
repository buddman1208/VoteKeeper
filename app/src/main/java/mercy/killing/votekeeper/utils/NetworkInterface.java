package mercy.killing.votekeeper.utils;

import java.util.Date;
import java.util.List;

import mercy.killing.votekeeper.models.Group;
import mercy.killing.votekeeper.models.User;
import mercy.killing.votekeeper.models.Vote;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by KOHA_DESKTOP on 2016. 6. 29..
 */
public interface NetworkInterface {

    @POST("/auth/login")
    @FormUrlEncoded
    Call<User> userLogin(@Field("id") String id, @Field("pw") String pw);

    @POST("/auth/register")
    @FormUrlEncoded
    Call<User> userRegister(@Field("id") String id, @Field("pw") String pw, @Field("name") String name);

    @POST("/auth/modifySelfInfo")
    @FormUrlEncoded
    Call<User> modifySelfInfo(@Field("name") String name, @Field("groupId") String groupId);

    @POST("/auth/getSelfInfo")
    @FormUrlEncoded
    Call<User> getSelfInfo(@Field("id") String id);

    @POST("/user/getUserList")
    @FormUrlEncoded
    Call<List<User>> getUserList(@Field("id") String id);

    @POST("/user/getGroupList")
    @FormUrlEncoded
    Call<List<Group>> getGroupList(@Field("id") String id);


    @POST("/user/groupAdd")
    @FormUrlEncoded
    Call<Group> groupAdd(@Field("id") String id, @Field("groupname") String groupname, @Field("members") String members);

    @POST("/user/groupInfo")
    @FormUrlEncoded
    Call<Group> groupInfo(@Field("groupId") String groupId);

    @POST("/group/editGroupInfo")
    @FormUrlEncoded
    Call<Group> editGroupInfo(@Field("id") String id, @Field("groupName") String groupName, @Field("members") String members);

    @POST("/vote/newVote")
    @FormUrlEncoded
    Call<Vote> newVote(@Field("id") String id, @Field("groupId") String groupId, @Field("title") String title,
                       @Field("finishDate") Date finishDate, @Field("checks") String checks, @Field("maxSelect") int maxSelect, @Field("isAnonymous") boolean isAnonymous);

    @POST("/vote/executeVote")
    @FormUrlEncoded
    Call<String> executeVote(@Field("id") String id, @Field("groupId") String groupId, @Field("title") String title,
                       @Field("finishDate") Date finishDate, @Field("checks") String checks, @Field("maxSelect") int maxSelect, @Field("isAnonymous") boolean isAnonymous);

    @POST("/vote/getVoteStatus")
    @FormUrlEncoded
    Call<String> getVoteStatus(@Field("id") String id, @Field("voteId") String voteId);
}
