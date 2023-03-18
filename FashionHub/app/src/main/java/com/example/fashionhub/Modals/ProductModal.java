package com.example.fashionhub.Modals;

import com.hishd.tinycart.model.Item;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductModal{
    private String pName,pPrice,pDicount,pDiscription,pImg,pCategory,productID,pDis_price;

    public ProductModal() {
    }

    public ProductModal(String pName, String pPrice, String pDicount, String pDiscription, String pImg, String pCategory, String productID, String pDis_price) {
        this.pName = pName;
        this.pPrice = pPrice;
        this.pDicount = pDicount;
        this.pDiscription = pDiscription;
        this.pImg = pImg;
        this.pCategory = pCategory;
        this.productID = productID;
        this.pDis_price = pDis_price;
    }

    public String getpDis_price() {
        return pDis_price;
    }

    public void setpDis_price(String pDis_price) {
        this.pDis_price = pDis_price;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getpDicount() {
        return pDicount;
    }

    public void setpDicount(String pDicount) {
        this.pDicount = pDicount;
    }

    public String getpDiscription() {
        return pDiscription;
    }

    public void setpDiscription(String pDiscription) {
        this.pDiscription = pDiscription;
    }

    public String getpImg() {
        return pImg;
    }

    public void setpImg(String pImg) {
        this.pImg = pImg;
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

}
