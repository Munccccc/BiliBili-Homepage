package app.munc.munccoordinator.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import app.munc.munccoordinator.R;

/**
 * Created by GD on 2017/12/6.
 */

public class DrawerBodyViewHolder extends RecyclerView.ViewHolder {

    public final TextView tv_bodyDescription;

    public DrawerBodyViewHolder(View itemView) {
        super(itemView);
        tv_bodyDescription = itemView.findViewById(R.id.tv_bodyDescription);
    }
}
