package com.omar.retail62.asyncTasks;

import android.os.AsyncTask;

import com.omar.retail62.models.ProductModel;
import com.omar.retail62.room.ProductDAO;

public class InsertAsyncTask extends AsyncTask<ProductModel,Void,Void> {

    private ProductDAO productDAO;

    public InsertAsyncTask(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected Void doInBackground(ProductModel... productModels) {
        productDAO.insertProduct(productModels[0]);
        return null;
    }
}
