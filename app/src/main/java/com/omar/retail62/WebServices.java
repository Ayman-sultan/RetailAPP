package com.omar.retail62;

import com.omar.retail62.models.ProductModel;
import com.omar.retail62.models.ProductsResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface WebServices {

   /* @GET("products")
    Call<ProductsResponse> getProducts();*/

    @GET("products")
    Single<ProductsResponse> getProducts();

}
