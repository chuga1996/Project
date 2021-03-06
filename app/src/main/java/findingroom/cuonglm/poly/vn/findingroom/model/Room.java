package findingroom.cuonglm.poly.vn.findingroom.model;

import java.io.Serializable;

public class Room implements Serializable {
    String address;
    String district;
    int price;
    int people;
    String phone;
    String imgResource;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Room(String address, String district, int price, int people, String phone, String imgResource, int id) {

        this.address = address;
        this.district = district;
        this.price = price;
        this.people = people;
        this.phone = phone;
        this.imgResource = imgResource;
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgResource() {
        return imgResource;
    }

    public void setImgResource(String imgResource) {
        this.imgResource = imgResource;
    }

    public Room() {

    }

    public Room(String address, String district, int price, int people, String phone, String imgResource) {

        this.address = address;
        this.district = district;
        this.price = price;
        this.people = people;
        this.phone = phone;
        this.imgResource = imgResource;
    }

    public String getFullAddress(){
        return this.address +" - " + this.district;
    }
}
