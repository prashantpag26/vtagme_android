package me.vtag.app.backend.models;

import ly.apps.android.rest.client.Callback;
import ly.apps.android.rest.client.Response;
import me.vtag.app.VtagApplication;
import me.vtag.app.backend.VtagClient;
import me.vtag.app.helpers.VtagmeCallback;

/**
 * Created by nageswara on 5/27/14.
 */
public class HashtagModel extends BaseTagModel {
    public static final int TYPE = 0;
    public static final int MULTI_TYPE = 1;

    public static final String POPULAR_VIDEOS_SORT = "views";
    public static final String RECENT_VIDEOS_SORT = "featured";
    public static final String MY_VIDEOS_SORT = "mine";

    public int followers;
    public void follow(final VtagmeCallback callback) {
        if (!following) {
            VtagClient.getAPI().followTag(tag, VtagApplication.getInstance().authPreferences.getUser(), new Callback<String>() {
                @Override
                public void onResponse(Response<String> baseTagModelResponse) {
                    String response_true = baseTagModelResponse.getResult();
                    if (response_true == "true") {
                        following = true;
                        callback.onComplete(true);
                    } else {
                        callback.onComplete(false);
                    }
                }
            });
        } else {
            callback.onComplete(true);
        }
    }

    public void unfollow(final VtagmeCallback callback) {
        if (following) {
            VtagClient.getAPI().unfollowTag(tag, VtagApplication.getInstance().authPreferences.getUser(), new Callback<String>() {
                @Override
                public void onResponse(Response<String> baseTagModelResponse) {
                    String response_true = baseTagModelResponse.getResult();
                    if (response_true == "true") {
                        following = false;
                        callback.onComplete(true);
                    } else {
                        callback.onComplete(false);
                    }
                }
            });
        } else {
            callback.onComplete(true);
        }
    }

    public static interface OnSortChangeListener {
        public boolean onChange(String newSort);
    }
    public static interface OnTagsModifiedListener {
        public void onAdded(String tag);
        public void onRemoved(String tag);
    }
}
