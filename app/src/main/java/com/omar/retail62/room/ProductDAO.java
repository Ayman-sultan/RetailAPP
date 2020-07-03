package com.omar.retail62.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.omar.retail62.models.ProductModel;

import java.util.List;

@Dao
public interface ProductDAO {

    @Query("SELECT * FROM products")
    List<ProductModel> getAllProducts();

    @Insert
    void insertProduct(ProductModel productModel);

    @Query("DELETE FROM products")
    void deleteAllProducts();

    @Update
    void updateProduct(ProductModel productModel);
}
