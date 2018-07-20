package findingroom.cuonglm.poly.vn.findingroom.model;

public class PhongTro {
    private int mImg;
    private String mDiaChi;
    private String mSoNguoi;
    private String mGia;

    public PhongTro(int mImg, String mDiaChi, String mSoNguoi, String mGia) {
        this.mImg = mImg;
        this.mDiaChi = mDiaChi;
        this.mSoNguoi = mSoNguoi;
        this.mGia = mGia;
    }

    public PhongTro() {
    }

    public int getmImg() {
        return mImg;
    }

    public void setmImg(int mImg) {
        this.mImg = mImg;
    }

    public String getmDiaChi() {
        return mDiaChi;
    }

    public void setmDiaChi(String mDiaChi) {
        this.mDiaChi = mDiaChi;
    }

    public String getmSoNguoi() {
        return mSoNguoi;
    }

    public void setmSoNguoi(String mSoNguoi) {
        this.mSoNguoi = mSoNguoi;
    }

    public String getmGia() {
        return mGia;
    }

    public void setmGia(String mGia) {
        this.mGia = mGia;
    }
}
