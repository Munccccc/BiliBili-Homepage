package app.munc.munccoordinator.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import app.munc.munccoordinator.R;
import app.munc.munccoordinator.view.RoundImageView;

//头部 ViewHolder
public class HeaderViewHolder extends RecyclerView.ViewHolder {


    public final RoundImageView ri_headerView;
    public final TextView tv_title;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        ri_headerView = itemView.findViewById(R.id.ri_headerView);
        tv_title = itemView.findViewById(R.id.tv_title);
    }
}