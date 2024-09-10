package com.example.onlineshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineshop.Models.Product;

import java.util.List;

import kotlinx.coroutines.channels.ActorKt;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context){
        this.productList = productList;
        this.context = context;
    }


    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public ProductAdapter(List<Product> productList, OnItemClickListener onItemClickListener) {
        this.productList = productList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(context, ProductDetailActivity.class);
////        intent.putExtra("product_id", product.getId());
////        intent.putExtra("product_name", product.getName());
////        intent.putExtra("product_description", product.getDescription());
////        intent.putExtra("product_price", product.getPrice());
////        intent.putExtra("product_image", product.getObjectImage());
//        intent.putExtra("product", productList.get(position));
//        context.startActivity(intent);
//    }

//    @Override
//    public void onItemClick(Product product) {
//        // Start ny aktivitet og send produktdetaljer
////        intent.putExtra("product_id", product.getId());
////        intent.putExtra("product_name", product.getName());
////        intent.putExtra("product_description", product.getDescription());
////        intent.putExtra("product_price", product.getPrice());
////        intent.putExtra("product_image", product.getObjectImage());
////        Intent intent = new Intent(context, ProductDetailActivity.class);
////        intent.putExtra("product", product);
////        context.startActivity(intent);
//    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, descriptionTextView, priceTextView;
        ImageView productImageView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            descriptionTextView = itemView.findViewById(R.id.textViewDescription);
            priceTextView = itemView.findViewById(R.id.textViewPrice);
            productImageView = itemView.findViewById(R.id.imageViewProduct);
        }

        public void bind(final Product product) {
            nameTextView.setText(product.getName());
            descriptionTextView.setText(product.getDescription());
            priceTextView.setText(String.valueOf(product.getPrice()) + " kr");
            /*Glide.with(productImageView.getContext()).load(product.getObjectImage()).into(productImageView);*/

            // Brug Glide til at indlæse og resize billedet
            Glide.with(productImageView.getContext())
                    .load(product.getObjectImage())
                    .override(100, 100) // Ændrer størrelsen til 100x100 pixels
                    .fitCenter()
                    .into(productImageView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    onItemClickListener.onItemClick(product);
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("product", product);
                    context.startActivity(intent);
                }
            });

        }


    }
}

