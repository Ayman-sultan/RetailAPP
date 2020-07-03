package com.omar.retail62.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.omar.retail62.R;
import com.omar.retail62.RoomFactory;
import com.omar.retail62.adapters.CartAdapter;
import com.omar.retail62.asyncTasks.DeleteAsyncTask;
import com.omar.retail62.asyncTasks.GetProductsAsyncTask;
import com.omar.retail62.asyncTasks.InsertAsyncTask;
import com.omar.retail62.asyncTasks.UpdateAsyncTask;
import com.omar.retail62.models.ProductModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CartFragment extends Fragment {


    private RecyclerView cartRv;
    private CartAdapter cartAdapter;
    private ArrayList<ProductModel> productsList = new ArrayList<>();

    Button clearBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartRv = view.findViewById(R.id.cart_rv);
        clearBtn = view.findViewById(R.id.clear_cart_btn);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpRecyclerView();
        getAllProductsFromRoomDB();
        setUpClickListeners();
    }

    private void setUpClickListeners() {

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DeleteAsyncTask(RoomFactory.createOrGetRoomObject(getContext()).gerProductDao()).execute();
                productsList.clear();
                cartAdapter.notifyDataSetChanged();

            }
        });

    }

    private void getAllProductsFromRoomDB() {

        try {
            productsList.addAll(new GetProductsAsyncTask(RoomFactory.createOrGetRoomObject(requireContext()).gerProductDao()).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void setUpRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        cartRv.setLayoutManager(layoutManager);
        cartRv.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));

        cartAdapter = new CartAdapter(requireContext(), productsList,

                new CartAdapter.onIncrementClickListener() {
                    @Override
                    public void onIncProduct(View view, int position) {

                        productsList.get(position).setQuantity(productsList.get(position).getQuantity() + 1);

                        new UpdateAsyncTask(RoomFactory.createOrGetRoomObject(requireContext()).gerProductDao()).execute(productsList.get(position));

                        cartAdapter.notifyDataSetChanged();

                    }
                },

                new CartAdapter.onDecrementClickListener() {
                    @Override
                    public void onDecProduct(View view, int position) {


                        productsList.get(position).setQuantity(productsList.get(position).getQuantity() - 1);

                        new UpdateAsyncTask(RoomFactory.createOrGetRoomObject(requireContext()).gerProductDao()).execute(productsList.get(position));

                        cartAdapter.notifyDataSetChanged();
                    }
                });

        cartRv.setAdapter(cartAdapter);

    }
}
