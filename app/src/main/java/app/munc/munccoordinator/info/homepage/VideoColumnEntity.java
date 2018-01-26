package app.munc.munccoordinator.info.homepage;

/**
 * Created by GD on 2017/12/27.
 */

public class VideoColumnEntity {
    public String getVideoCapture() {
        return videoCapture;
    }

    public void setVideoCapture(String videoCapture) {
        this.videoCapture = videoCapture;
    }

    private String videoCapture; //视频截图

    public String getVideoUpPerson() {
        return videoUpPerson;
    }

    public void setVideoUpPerson(String videoUpPerson) {
        this.videoUpPerson = videoUpPerson;
    }

    private String videoUpPerson; //视频UP主

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    private String videoTitle; //视频标题

    public String getVideoCategory() {
        return videoCategory;
    }

    public void setVideoCategory(String videoCategory) {
        this.videoCategory = videoCategory;
    }

    private String videoCategory; //视频分类

    public String getVideoViewingNumber() {
        return videoViewingNumber;
    }

    public void setVideoViewingNumber(String videoViewingNumber) {
        this.videoViewingNumber = videoViewingNumber;
    }

    private String videoViewingNumber; //视频观看人数


}
