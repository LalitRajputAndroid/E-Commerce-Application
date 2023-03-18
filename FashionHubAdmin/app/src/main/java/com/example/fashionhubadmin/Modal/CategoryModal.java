package com.example.fashionhubadmin.Modal;

public class CategoryModal {
    String cName,cImage,categoryID;

    public CategoryModal() {
    }

    public CategoryModal(String cName, String cImage, String categoryID) {
        this.cName = cName;
        this.cImage = cImage;
        this.categoryID = categoryID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcImage() {
        return cImage;
    }

    public void setcImage(String cImage) {
        this.cImage = cImage;
    }

    @Override
    public String toString() {
        return "CategoryModal{" +
                "cName='" + cName + '\'' +
                ", cImage='" + cImage + '\'' +
                '}';
    }
}
