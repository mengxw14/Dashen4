package mobilsafe.example.mxw.dashen4.bean;

/**
 * Created by MXW on 2020/5/29.
 */

public class Title {
    private int ImgSrc;
    private String text1;
    private String text2;

    public Title(int imgSrc, String text1, String text2) {
        ImgSrc = imgSrc;
        this.text1 = text1;
        this.text2 = text2;
    }

    public int getImgSrc() {
        return ImgSrc;
    }

    public void setImgSrc(int imgSrc) {
        ImgSrc = imgSrc;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }
}
