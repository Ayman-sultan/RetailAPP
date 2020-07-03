package com.omar.retail62.fragments;


import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.omar.retail62.R;
import com.omar.retail62.RetrofitFactory;
import com.omar.retail62.RoomFactory;
import com.omar.retail62.WebServices;
import com.omar.retail62.adapters.ProductsAdapter;
import com.omar.retail62.asyncTasks.InsertAsyncTask;
import com.omar.retail62.models.ProductModel;
import com.omar.retail62.models.ProductsResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {


    private SingleObserver<ProductsResponse> productsObserver;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private RecyclerView productRv;
    private ProductsAdapter productsAdapter;
    List<ProductModel> productsList = new ArrayList<>();

    private WebServices webServices;
    ProgressDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        productRv = view.findViewById(R.id.products_rv);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setUpProgressDialog();
        dialog.show();
        setUpRecyclerView();
        //getProductsAPI();
        setUpProductsObserver();
        doProductsSubscription();
    }

    private void doProductsSubscription() {

        webServices = RetrofitFactory.getRetrofit().create(WebServices.class);

        //  getProducts >>>>>>> Observable
        Single<ProductsResponse> getProducts = webServices.getProducts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

        getProducts.subscribe(productsObserver);


    }

    private void setUpProductsObserver() {

        productsObserver = new SingleObserver<ProductsResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(ProductsResponse productsResponse) {
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                productsList.clear();
                productsList.addAll(productsResponse.getProductsList());
                productsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        };

    }

    /*private void getProductsAPI() {

        webServices = RetrofitFactory.getRetrofit().create(WebServices.class);

        Call<ProductsResponse> getProducts = webServices.getProducts();

        getProducts.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                productsList.clear();
                productsList.addAll(response.body().getProductsList());
                productsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                Log.d("test",t.toString());

            }
        });

    }*/

    private void setUpProgressDialog() {

        dialog = new ProgressDialog(requireContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }

    private void setUpRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(),2);
        productRv.setLayoutManager(layoutManager);
        productRv.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(12), true));

        productsAdapter = new ProductsAdapter(requireContext(), productsList, new ProductsAdapter.OnAddProductClickListener() {
            @Override
            public void onAddProduct(View view, int position) {

                ProductModel productModel = productsList.get(position);

                productModel.setQuantity(1);

                new InsertAsyncTask(RoomFactory.createOrGetRoomObject(requireContext()).gerProductDao()).execute(productModel);

                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_cartFragment);


            }
        }, new ProductsAdapter.onProductClickListener() {
            @Override
            public void onProductClick(View view, int position) {

                ProductModel productModel = productsList.get(position);

                Bundle bundle = new Bundle();
                bundle.putSerializable("productModel" , productModel);

                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_productDetailsFragment , bundle);

            }
        });
        productRv.setAdapter(productsAdapter);
        productsAdapter.notifyDataSetChanged();

    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();


    }
}
