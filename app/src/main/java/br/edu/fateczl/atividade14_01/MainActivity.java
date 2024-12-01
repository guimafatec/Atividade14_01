package br.edu.fateczl.atividade14_01;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {


    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Bundle bundle = getIntent().getExtras();
        carregaFragment(bundle);
    }

    private void carregaFragment(Bundle bundle) {
        fragment = new HomeFragment();
        if (bundle != null) {
            String tipo = bundle.getString("item");
            if (tipo.equals("alugueis")) {
                fragment = new AlugueisFragment();
            }
            if (tipo.equals("alunos")) {
                fragment = new AlunosFragment();
            }
            if(tipo.equals("exemplares")) {
                fragment = new ExemplaresFragment();
            }
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, MainActivity.class);

        if (id == R.id.menu_alugueis) {
            return setupOptions(bundle, intent, "item", "alugueis");
        }
        if (id == R.id.menu_alunos) {
            return setupOptions(bundle, intent, "item", "alunos");
        }
        if (id == R.id.menu_exemplares) {
            return setupOptions(bundle, intent, "item", "exemplares");
        }
        return true;
    }

    private boolean setupOptions(Bundle bundle, Intent intent, String key, String value) {
        bundle.putString(key, value);
        intent.putExtras(bundle);
        this.startActivity(intent);
        this.finish();
        return true;
    }
}