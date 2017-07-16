package com.ipinyou.ads.bean;


import com.ipinyou.ads.tools.StringUtils;

public class Ad {

    //AdmTypeHTML 广告文档类型-HTML片段
    public static final String AdmTypeHTML  = "html";
    //AdmTypeVAST 广告文档类型-VAST
    public static final String AdmTypeVAST = "vast";
    //AdmTypeMRAID 广告文档类型-MRAID
    public static final String AdmTypeMRAID = "marid";
    //AdmTypeMRAID 广告文档类型-动态创意
    public static final String AdmTypeDynamic = "dynamicCreative";


    private static final String HtmlHead = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><style> body { padding:0; border:0; margin:0;}</style></head><body>";
    private static final String HtmlEnd = "</body></html>";

    private String Adm;
    private String AdmType;
    private int Height;
    private int Price;
    private int Width;

    public String getAdm() {

        if (StringUtils.isNotEmpty(AdmType) && AdmType.equals(AdmTypeHTML))

            return HtmlHead + Adm + HtmlEnd;

        return Adm;
    }

    public void setAdm(String adm) {

        if (StringUtils.isNotEmpty(adm))
            try {
                Adm = new String(adm.getBytes(), "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public String getAdmType() {
        return AdmType;
    }

    public void setAdmType(String admType) {
        AdmType = admType;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getWidth() {
        return Width;
    }

    public void setWidth(int width) {
        Width = width;
    }
}
