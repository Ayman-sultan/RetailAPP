package com.omar.retail62.asyncTasks;

import android.os.AsyncTask;

import com.omar.retail62.models.ProductModel;
import com.omar.retail62.room.ProductDAO;

import java.util.List;

public class GetProductsAsyncTask extends AsyncTask<Void,Void, List<ProductModel>> {

    private ProductDAO productDAO;

    public GetProductsAsyncTask(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected List<ProductModel> doInBackground(Void... voids) {
        return productDAO.getAllProducts();
    }


}
