package com.omar.retail62.asyncTasks;

import android.os.AsyncTask;

import com.omar.retail62.models.ProductModel;
import com.omar.retail62.room.ProductDAO;

import java.util.List;

public class DeleteAsyncTask extends AsyncTask<Void,Void, Void> {

    private ProductDAO productDAO;

    public DeleteAsyncTask(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        productDAO.deleteAllProducts();
        return null;
    }


}
