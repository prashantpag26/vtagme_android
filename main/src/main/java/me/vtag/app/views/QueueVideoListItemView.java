package me.vtag.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import me.vtag.app.VtagApplication;

/**
 * Created by nageswara on 5/19/14.
 */
public class QueueVideoListItemView extends BaseVideoListItemView {
    public QueueVideoListItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public QueueVideoListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QueueVideoListItemView(Context context) {
        super(context);
    }

    @Override
    public void onClick(View view) {
        VtagApplication.getInstance().getQueueFragment().play(model);
    }

}
