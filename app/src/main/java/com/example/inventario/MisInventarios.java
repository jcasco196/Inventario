package com.example.inventario;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MisInventarios extends AppCompatActivity {

    private Button add;
    private FirebaseRecyclerAdapter mAdapter;
    int RC_IMAGE_PICK = 5677;
    private DatabaseReference mDatabase;
    String idInventario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_inventarios);

        add = findViewById(R.id.add);

        idInventario = getIntent().getStringExtra("INVENTARIO_KEY");
        final String uid = FirebaseAuth.getInstance().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("inventarios").child(uid);

        final RecyclerView recyclerView = findViewById(R.id.list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query postsQuery = mDatabase;

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Inventarios>()
                .setQuery(postsQuery, Inventarios.class)
                .setLifecycleOwner(this)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<Inventarios, InventariosViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull InventariosViewHolder holder, final int position, @NonNull final Inventarios inventarios) {

                holder.nombreInventario.setText(inventarios.displayNameInventarios);
                holder.fechaCreacion.setText(inventarios.date);
                if(inventarios.photoInventariosUrl != null) {
                    holder.image.setVisibility(View.VISIBLE);
                    Glide.with(MisInventarios.this)
                            .load(inventarios.photoInventariosUrl)
                            .into(holder.image);
                }else{
                    holder.image.setVisibility(View.GONE);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MisInventarios.this, Perfil.class); // LO ENVIO AL PERFIL PARA HACER LA PRUEBA
                        intent.putExtra("INVENTARIO_KEY", getRef(position).getKey());
                        startActivity(intent);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                    @Override
                    public boolean onLongClick(View view) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MisInventarios.this);
                        builder1.setMessage("¿Estas seguro que deseas eliminar este inventario?");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Si",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String postKey = getRef(position).getKey();
                                        mDatabase.child(postKey).setValue(null);
                                        //Falta que no pete cuando se elimina el ultimo item.
                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                        return true;
                    }
                });
            }

            @Override
            public InventariosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventarios, parent, false);
                return new InventariosViewHolder(view);

            }

        };
        recyclerView.setAdapter(mAdapter);



    }

    public void add(View view) {
        Intent intent = new Intent(this, CrearInventario.class);
        startActivity(intent);
    }

}

