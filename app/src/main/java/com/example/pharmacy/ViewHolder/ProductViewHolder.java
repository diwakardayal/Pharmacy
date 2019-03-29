package com.example.pharmacy.ViewHolder;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pharmacy.Interface.ItemClickListner;
import com.example.pharmacy.R;

import org.w3c.dom.Text;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txtproductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListner listner;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtproductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);


    }

        public  void setItemClickListner(ItemClickListner listner){

        this.listner = listner;

        }

    @Override
    public void onClick(View view) {

        listner.onClick(view, getAdapterPosition(), false);

    }
}
