package com.example.bca_bos.models.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.bca_bos.models.Seller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Parcelable {

    private int id_product;
    private String product_name; //100
    private int price;
    private int stock;
    private String image_path;
    private PrdCategory prdCategory;
    private Seller seller;
    private int id_prd_category;

    public Product(int p_id_product, String p_product_name, int p_stock, String p_image,
                   int p_price, PrdCategory p_prdCategory){
        this.setId_product(p_id_product);
        this.setProduct_name(p_product_name);
        this.setStock(p_stock);
        this.setImage_path(p_image);
        this.setPrice(p_price);
        this.setPrdCategory(p_prdCategory);
    }

    protected Product(Parcel in) {
        id_product = in.readInt();
        product_name = in.readString();
        price = in.readInt();
        stock = in.readInt();
        image_path = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_product);
        dest.writeString(product_name);
        dest.writeInt(stock);
        dest.writeString(image_path);
        dest.writeInt(price);
    }
}