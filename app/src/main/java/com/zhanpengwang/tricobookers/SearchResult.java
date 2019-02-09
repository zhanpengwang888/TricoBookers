package com.zhanpengwang.tricobookers;

public class SearchResult {
    private String bookName;
    private String authors;
    private String department;
    private String isbn;
    private String courseNumber;
    private String newPrice;
    private String usedPrice;
    private String isRequired;
    private String imgUrl;

    public SearchResult(String bookName, String authors, String department, String isbn,
                        String courseNumber, String newPrice, String usedPrice, String isRequired,
                        String imgUrl) {
        this.bookName = bookName;
        this.authors = authors;
        this.department = department;
        this.isbn = isbn;
        this.courseNumber = courseNumber;
        this.newPrice = newPrice;
        this.usedPrice = usedPrice;
        this.isRequired = isRequired;
        this.imgUrl = imgUrl;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthors() {
        return authors;
    }

    public String getDepartment() {
        return department;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public String getUsedPrice() {
        return usedPrice;
    }

    public String getIsRequired() {
        return isRequired;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public void setUsedPrice(String usedPrice) {
        this.usedPrice = usedPrice;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
