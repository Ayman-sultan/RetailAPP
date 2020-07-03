package com.omar.retail62.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.omar.retail62.R;
import com.omar.retail62.RoomFactory;
import com.omar.retail62.asyncTasks.InsertAsyncTask;
import com.omar.retail62.models.ProductModel;


public class ProductDetailsFragment extends Fragment {


    ImageView productIv;
    TextView title;
    TextView details;
    TextView price;
    TextView description;
    Button addToCartBtn;

    ProductModel productModel = null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        productIv = view.findViewById(R.id.product_details_iv);
        title = view.findViewById(R.id.product_details_title_tv);
        details = view.findViewById(R.id.product_details_details_tv);
        price = view.findViewById(R.id.product_details_price_tv);
        description = view.findViewById(R.id.product_details_description_tv);
        addToCartBtn = view.findViewById(R.id.add_to_cart_btn);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Bundle args = getArguments();
        if ( args != null){

            productModel = (ProductModel) args.getSerializable("productModel");

            title.setText(productModel.getTitle());
            details.setText(productModel.getDetails());
            price.setText(productModel.getPriceFinalText());
            description.setText(productModel.getDescription());
            Glide.with(requireContext()).load(productModel.getProductPhoto()).into(productIv);
        }

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new InsertAsyncTask(RoomFactory.createOrGetRoomObject(requireContext()).gerProductDao()).execute(productModel);

                Navigation.findNavController(v).navigate(R.id.action_productDetailsFragment_to_cartFragment);


            }
        });



    }
}
