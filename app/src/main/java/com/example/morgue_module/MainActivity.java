package com.example.morgue_module;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    ImageButton btnMore;
    public static BadgeDrawable Pending;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.BG));
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new OrdersPagerAdapter(this));
        viewPager.setUserInputEnabled(false);
        btnMore = findViewById(R.id.btnMore);
        TabLayoutMediator tLM = new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position)
                {
                    case 0:
                    {
                        tab.setText("Bookings");
                        tab.setIcon(R.drawable.ic_pending);
                        Pending = tab.getOrCreateBadge();
                        Pending.setMaxCharacterCount(3);
                        Pending.setNumber(20);
                        Pending.setVisible(true);
                        break;
                    }
                    case 1:
                    {
                        tab.setText("Bodies");
                        tab.setIcon(R.drawable.ic_body);
                        Pending = tab.getOrCreateBadge();
                        Pending.setMaxCharacterCount(3);
                        Pending.setNumber(20);
                        Pending.setVisible(true);
                        break;
                    }
                    case 2:
                    {
                        tab.setText("Profile");
                        tab.setIcon(R.drawable.ic_person);
                        break;
                    }
                }
            }
        });

        tLM.attach();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                BadgeDrawable bd = Objects.requireNonNull(tabLayout.getTabAt(position)).getOrCreateBadge();
                bd.setNumber(0);
                bd.setVisible(false);
            }
        });

    }
}