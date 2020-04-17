package jcasco.apps.inventario;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inventario.R;

import androidx.recyclerview.widget.RecyclerView;

public class InventariosViewHolder extends RecyclerView.ViewHolder{
    TextView nombreInventario;
    TextView fechaCreacion;
    ImageView image;

    public InventariosViewHolder(View itemView) {
        super(itemView);

        nombreInventario = itemView.findViewById(R.id.nombreInventario);
        fechaCreacion = itemView.findViewById(R.id.fechaCreacion);
        image = itemView.findViewById(R.id.image);

    }
}