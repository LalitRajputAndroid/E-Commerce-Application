package com.example.fashionhub.Modals;

import com.hishd.tinycart.model.Item;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartModal implements Item, Serializable {
    private  String pname,pdis,price,product_Id,UId,pimg,quntity;

    public CartModal() {
    }

    public CartModal(String pname, String pdis, String price, String product_Id, String UId, String pimg,String quntity) {
        this.pname = pname;
        this.pdis = pdis;
        this.price = price;
        this.product_Id = product_Id;
        this.UId = UId;
        this.pimg = pimg;
        this.quntity = quntity;
    }

    public String getQuntity() {
        return quntity;
    }

    public void setQuntity(String quntity) {
        this.quntity = quntity;
    }

    public String getPimg() {
        return pimg;
    }

    public void setPimg(String pimg) {
        this.pimg = pimg;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPdis() {
        return pdis;
    }

    public void setPdis(String pdis) {
        this.pdis = pdis;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_Id() {
        return product_Id;
    }

    public void setProduct_Id(String product_Id) {
        this.product_Id = product_Id;
    }

    public String getUId() {
        return UId;
    }

    public void setUId(String UId) {
        this.UId = UId;
    }

    @Override
    public String toString() {
        return "CartModal{" +
                "pname='" + pname + '\'' +
                ", pdis='" + pdis + '\'' +
                ", price='" + price + '\'' +
                ", product_Id='" + product_Id + '\'' +
                ", UId='" + UId + '\'' +
                ", pimg='" + pimg + '\'' +
                '}';
    }

    @Override
    public BigDecimal getItemPrice() {
        return null;
    }

    @Override
    public String getItemName() {
        return pname;
    }
}
