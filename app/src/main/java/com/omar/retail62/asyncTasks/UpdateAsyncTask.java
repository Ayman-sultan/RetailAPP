package com.omar.retail62.asyncTasks;

import android.os.AsyncTask;

import com.omar.retail62.models.ProductModel;
import com.omar.retail62.room.ProductDAO;

public class UpdateAsyncTask extends AsyncTask<ProductModel,Void,Void> {

    private ProductDAO productDAO;

    public UpdateAsyncTask(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected Void doInBackground(ProductModel... productModels) {
        productDAO.updateProduct(productModels[0]);
        return null;
    }
}
