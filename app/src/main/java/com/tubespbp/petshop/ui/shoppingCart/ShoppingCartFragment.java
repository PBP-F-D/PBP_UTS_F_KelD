package com.tubespbp.petshop.ui.shoppingCart;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.tubespbp.petshop.R;
import com.tubespbp.petshop.databinding.FragmentShoppingCartBinding;
import com.tubespbp.petshop.ui.shoppingCart.adapter.RecyclerViewAdapterCart;
import com.tubespbp.petshop.ui.shoppingCart.database.DatabaseClient;
import com.tubespbp.petshop.ui.shoppingCart.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends Fragment {

    private ArrayList<Cart> ListCart, newList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterCart adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentShoppingCartBinding shoppingCartBinding;

    private ShoppingCartViewModel shoppingCartViewModel;

    public ShoppingCartFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        shoppingCartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopping_cart, container, false);
        View view = shoppingCartBinding.getRoot();

        recyclerView = shoppingCartBinding.rvFollow;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        getCart();

        return view;
    }

    //TODO: 5th item on the list disappears when inserted, adding more will show the item but hides the last item on the list. (e.g. 6 hides 7, 7 hides 8)
    private void getCart(){
        class GetCart extends AsyncTask<Void, Void, List<Cart>> {

            @Override
            protected void onPostExecute(List<Cart> carts) {
                super.onPostExecute(carts);
                adapter = new RecyclerViewAdapterCart(getContext(), carts);
                recyclerView.setAdapter(adapter);
                if (carts.isEmpty()){
                    Toast.makeText(getContext(), "Empty List", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected List<Cart> doInBackground(Void... voids) {
                List<Cart> userCart = DatabaseClient
                        .getInstance(getContext())
                        .getDatabase()
                        .userDAO()
                        .getAll();
                return userCart;
            }
        }

        GetCart get = new GetCart();
        get.execute();
    }

}