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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<ProductModel> productsList;

    private onIncrementClickListener onIncrementClickListener;
    private onDecrementClickListener onDecrementClickListener;

    public interface onIncrementClickListener {
        void onIncProduct(View view, int position);
    }

    public interface onDecrementClickListener {
        void onDecProduct(View view, int position);
    }


    public CartAdapter(Context context, List<ProductModel> productsList, CartAdapter.onIncrementClickListener onIncrementClickListener, CartAdapter.onDecrementClickListener onDecrementClickListener) {
        this.context = context;
        this.productsList = productsList;
        this.onIncrementClickListener = onIncrementClickListener;
        this.onDecrementClickListener = onDecrementClickListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.cart_rv_items, parent, false);

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {

        ProductModel productModel = productsList.get(position);

        Glide.with(context).load(productModel.getProductPhoto()).into(holder.productIv);
        holder.productTitleTv.setText(productModel.getTitle());
        holder.productDetailsTv.setText(productModel.getDetails());
        holder.productPriceTv.setText(productModel.getPriceFinalText());

        holder.quantityTv.setText(productModel.getQuantity()+"");


        holder.incIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onIncrementClickListener.onIncProduct(v, holder.getAdapterPosition());
            }
        });

        holder.decIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onDecrementClickListener.onDecProduct(v, holder.getAdapterPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {


        ImageView productIv;
        TextView productTitleTv;
        TextView productDetailsTv;
        TextView productPriceTv;
        TextView quantityTv;
        ImageButton incIb;
        ImageButton decIb;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            productIv = itemView.findViewById(R.id.product_cart_iv);
            productTitleTv = itemView.findViewById(R.id.product_title_cart_tv);
            productDetailsTv = itemView.findViewById(R.id.product_details_cart_tv);
            productPriceTv = itemView.findViewById(R.id.product_price_cart_tv);
            quantityTv = itemView.findViewById(R.id.quantity_tv);
            incIb = itemView.findViewById(R.id.inc_item_cart_ib);
            decIb = itemView.findViewById(R.id.dec_item_cart_ib);

        }
    }
}
