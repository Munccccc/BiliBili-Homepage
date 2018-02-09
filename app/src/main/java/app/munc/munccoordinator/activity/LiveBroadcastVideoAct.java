package app.munc.munccoordinator.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

import app.munc.munccoordinator.R;
import app.munc.munccoordinator.content.CommonContent;
import app.munc.munccoordinator.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.widget.VideoView;

import static app.munc.munccoordinator.R.id.surface_view;

public class LiveBroadcastVideoAct extends AppCompatActivity {
    @BindView(R.id.activity_live_broadcast_video)
    RelativeLayout activityLiveBroadcastVideo;
    @BindView(surface_view)
    VideoView surfaceView;
    //    @BindView(R.id.ijkViewView)
//    IjkVideoView mVideoView;
    private String mVideoPath;
    private String biliFlvTitle;
    private String biliFlvImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_broadcast_video);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        mVideoPath = getIntent().getStringExtra(CommonContent.BiliFlvUrl);
        biliFlvTitle = getIntent().getStringExtra(CommonContent.BiliFlvTitle);
        biliFlvImg = getIntent().getStringExtra(CommonContent.BiliFlvImg);
        Log.e("biliFlvUrl", mVideoPath);
        init();
    }

    private void init() {
        //维他命
          /*java代码*/

    /*onCreate中,用于检查vitamioLibs库是否准备完成
     *如果Manifest中没有初始化InitActivity，这里会返回false，并自动初始化InitActivity
     */
        if (!LibsChecker.checkVitamioLibs(this))
            Utils.showToast(this,"vitmioLibs未完成");
            return;

    /*会有几秒延时*/
//        surfaceView.setVideoPath("http://js.live-play.acgvideo.com/live-js/373746/live_39319690_2448453.flv?wsSecret=ed6addf070bb285885ccb246ba0c1477&wsTime=1517565361");


//        VideoView videoView = (VideoView) findViewById(R.id.videoview);
//        videoView.setVideoURI(Uri.parse("http://js.live-play.acgvideo.com/live-js/373746/live_39319690_2448453.flv?wsSecret=ed6addf070bb285885ccb246ba0c1477&wsTime=1517565361"));
//        MediaController mediaController = new MediaController(this);
//        videoView.setMediaController(mediaController);
//        videoView.start();
//
//
//        JZVideoPlayerStandard jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
//        jzVideoPlayerStandard.setUp("http://js.live-play.acgvideo.com/live-js/373746/live_39319690_2448453.flv?wsSecret=ed6addf070bb285885ccb246ba0c1477&wsTime=1517565361.mp4"
//                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "test");
//        jzVideoPlayerStandard.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
    }

//    private void init() {
//        //加载so文件
//        try {
//            IjkMediaPlayer.loadLibrariesOnce(null);
//            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
//        } catch (Exception e) {
//            this.finish();
//        }
//        if (!TextUtils.isEmpty(mVideoPath)) {
//            new RecentMediaStorage(this).saveUrlAsync(mVideoPath);
//        }
//        mVideoView.setVideoPath(mVideoPath);
//        mVideoView.start();
//    }

}
