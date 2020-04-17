package jcasco.apps.inventario;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.inventario.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MySettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);


        Preference preference4 = findPreference("modoNoche");
        preference4.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference4) {

                PreferenceManager preferenceManager = getPreferenceManager();


                if (preferenceManager.getSharedPreferences().getBoolean("modoNoche", true)){

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


                } else {

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }




                return true;
            }
        });


        Preference preference3 = findPreference("cambioPass");
        preference3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference3) {
                LayoutInflater li = LayoutInflater.from(getContext());
                View promptsView = li.inflate(R.layout.recuperar_contra, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());

                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        final String correo = userInput.getText().toString();
                                        FirebaseAuth.getInstance().sendPasswordResetEmail(correo)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {

                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getContext(), "Vaya a su correo para restablecer su contraseña.", Toast.LENGTH_LONG).show();
                                                        } else {
                                                            Toast.makeText(getContext(), "Error, correo invalido", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    }
                                })
                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return true;
            }
        });


        ///

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
