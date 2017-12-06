package app.munc.munccoordinator.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import app.munc.munccoordinator.R;
import app.munc.munccoordinator.view.RoundImageView;


/**
 * Created by Munc on 2017/11/27.
 */

public class CommonMuncBody extends RecyclerView.ViewHolder {


    public LinearLayout ll_main1;
    public RoundImageView iv_one;
    public RecyclerView rc_special;

    public CommonMuncBody(View view) {
        super(view);
        iv_one = view.findViewById(R.id.iv_one);
        rc_special = view.findViewById(R.id.rc_special);
        ll_main1 = view.findViewById(R.id.ll_main1);
    }

}
