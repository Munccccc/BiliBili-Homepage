package app.munc.munccoordinator.manager;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
	@Override
	public void displayImage(Context context, Object path, ImageView imageView) {
		//Glide 加载图片简单用法
		try{
			Glide.with(context)
					.load(path)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					.crossFade()
					.into(imageView);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}