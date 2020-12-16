package com.example.model;

public class Contact {
    private String textValue;
    private String img;
    private String color;

    public Contact(String textValue, String img, String color)
    {
        this.textValue = textValue;
        this.img = img;
        this.color = color;
    }

    public String getTextValue()
    {
        return textValue;
    }

    public void setName(String name)
    {
        this.textValue = name;
    }

    public String getImage()
    {
        return img;
    }

    public void setSrc(String img)
    {
        this.img = img;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }
}
