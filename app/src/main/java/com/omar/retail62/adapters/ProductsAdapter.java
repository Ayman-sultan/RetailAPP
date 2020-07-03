package com.omar.retail62.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.omar.retail62.R;
import com.omar.retail62.models.ProductModel;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {


    private Context context;
    private List<ProductModel> productsList;
    private onProductClickListener onProductClickListener;
    private OnAddProductClickListener onAddProductClickListener;

    public interface OnAddProductClickListener {
        void onAddProduct(View view, int position);
    }

    public interface onProductClickListener {
        void onProductClick(View view, int position);
    }


    public ProductsAdapter(Context context, List<ProductModel> productsList, OnAddProductClickListener onAddProductClickListener, ProductsAdapter.onProductClickListener onProductClickListener) {
        this.context = context;
        this.productsList = productsList;
        this.onProductClickListener = onProductClickListener;
        this.onAddProductClickListener = onAddProductClickListener;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.products_rv_item, parent, false);

        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, final int position) {

        ProductModel productModel = productsList.get(position);

        Glide.with(context).load(productModel.getProductPhoto()).into(holder.productIv);

        holder.productTitle.setText(productModel.getTitle());
        holder.productDetails.setText(productModel.getDetails());
        holder.productFinalPrice.setText(productModel.getPriceFinalText());

        holder.addProductIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onAddProductClickListener.onAddProduct(v, position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onProductClickListener.onProductClick(v, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ProductsViewHolder extends RecyclerView.ViewHolder {

        ImageView productIv;
        TextView productTitle;
        TextView productDetails;
        TextView productFinalPrice;
        ImageButton addProductIb;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            productIv = itemView.findViewById(R.id.product_iv);
            productTitle = itemView.findViewById(R.id.product_title_tv);
            productDetails = itemView.findViewById(R.id.product_details_tv);
            productFinalPrice = itemView.findViewById(R.id.product_price_tv);
            addProductIb = itemView.findViewById(R.id.add_product_ib);
        }
    }

}
