package be.helha.koopa.pronotespluquet.controllers;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import be.helha.koopa.pronotespluquet.R;
import be.helha.koopa.pronotespluquet.views.BlockFragment;
import be.helha.koopa.pronotespluquet.views.StudentFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        // Ajoute les deux onglets
        tabLayout.addTab(tabLayout.newTab().setText("Bloc"));
        tabLayout.addTab(tabLayout.newTab().setText("Élèves"));

        // Affiche BlocFragment par défaut
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new BlockFragment())
                .commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                if (tab.getPosition() == 0) {
                    fragment = new BlockFragment();
                } else {
                    fragment = new StudentFragment();
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    // Navigation vers les cours d’un bloc (inchangé)
    public void openCoursesForBlock(long blockId, String blockName) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, be.helha.koopa.pronotespluquet.views.CourseFragment.newInstance(blockId, blockName))
                .addToBackStack(null)
                .commit();
    }
}

