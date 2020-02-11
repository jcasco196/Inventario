package com.example.inventario;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MySettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);





        Preference preference = findPreference("informacion");
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getActivity(), Perfil.class));

                new AlertDialog.Builder(getContext())
                        .setTitle("Información")
                        .setMessage("Está aplicación se reserva a los derechos proporcionados por el autor. La aplicación Inventario se reserva momentaneamente en está única versión. Irémos notificando a los usuarios futuras mejoras y advertencias. Una aplicación desarrollada por una única persona con mucho cariño y con un objetivo clave. CREADOR : JESÚS CASCO BECERRA | CONTACTO: jcasco196@gmail.com")
                        .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("SettingsActivity", "Cerrando...");
                            }
                        })
                        .show();

                return true;
            }
        });


        ///
        Preference preference2 = findPreference("contacto");
        preference2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference2) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Contacto")
                        .setMessage("Gracias por utilizar la aplicación, valoramos mucho vuestro uso y sobre todo si cres que podemos mejorar en algo contacte con nosotros jcasco196@gmail.com, gracias seguiremos mejorando")
                        .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("SettingsActivity", "Cerrando...");
                            }
                        })
                        .show();

                return true;
            }
        });
    }

}
