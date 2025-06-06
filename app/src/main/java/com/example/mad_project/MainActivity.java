package com.example.mad_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager2 pager2;
    private PagerViewAdapter pagerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        pager2 = findViewById(R.id.view_pager);

        FragmentManager fm = getSupportFragmentManager();
        pagerViewAdapter = new PagerViewAdapter(fm, getLifecycle());
        pager2.setAdapter(pagerViewAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Products"));
        tabLayout.addTab(tabLayout.newTab().setText("Cart"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}

/**import androidx.annotation.NonNull;
 import androidx.appcompat.app.AlertDialog;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.fragment.app.Fragment;
 import androidx.fragment.app.FragmentManager;
 import androidx.viewpager.widget.PagerAdapter;
 import androidx.viewpager.widget.ViewPager;

 import android.content.DialogInterface;
 import android.content.Intent;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.TextView;

 import com.google.firebase.auth.FirebaseAuth;
 import com.google.firebase.auth.FirebaseUser;
 import com.google.firebase.database.DataSnapshot;
 import com.google.firebase.database.DatabaseError;
 import com.google.firebase.database.DatabaseReference;
 import com.google.firebase.database.FirebaseDatabase;
 import com.google.firebase.database.ValueEventListener;

 public class MainActivity extends AppCompatActivity {
 private TextView profileLabel,usersLabel,notificationLabel,tvUsername;
 private ViewPager mViewPager;
 PagerViewAdapter pagerViewAdapter;
 private FirebaseAuth mAuth;
 private DatabaseReference myUsersDatabase;


 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_main);

 mAuth=FirebaseAuth.getInstance();
 myUsersDatabase= FirebaseDatabase.getInstance().getReference().child("ENACTUS UOE").child("Users");
 myUsersDatabase.keepSynced(true);
 profileLabel=findViewById(R.id.profileLabel);
 usersLabel=findViewById(R.id.usersLabel);
 /**notificationLabel=findViewById(R.id.notificationLabel);
 mViewPager=findViewById(R.id.mainViewPager);
 tvUsername=findViewById(R.id.useraname);


 profileLabel.setOnClickListener(new View.OnClickListener() {
 @Override
 public void onClick(View v) {
 mViewPager.setCurrentItem(0);
 }
 });
 usersLabel.setOnClickListener(new View.OnClickListener() {
 @Override
 public void onClick(View v) {
 mViewPager.setCurrentItem(1);
 }
 });
 /**notificationLabel.setOnClickListener(new View.OnClickListener() {
 @Override
 public void onClick(View v) {
 mViewPager.setCurrentItem(2);

 }
 });

 FragmentManager fm=getSupportFragmentManager();
 pagerViewAdapter =new PagerViewAdapter(fm,getLifecycle());
 mViewPager.setAdapter(mViewPager.getAdapter());
 mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
 @Override
 public void onPageScrolled(int position ,float positionOffset, int positionOffsetPixels) {

 }

 @Override
 public void onPageSelected(int i) {

 changeTabs(i);
 }

 @Override
 public void onPageScrollStateChanged(int i) {

 }
 });

 }

 private void changeTabs(int position) {
 if (position==0)
 {

 profileLabel.setTextColor(getResources().getColor(R.color.textTabBright));
 profileLabel.setTextSize(22);
 usersLabel.setTextColor(getResources().getColor(R.color.textTabLight));
 usersLabel.setTextSize(16);
 /** notificationLabel.setTextColor(getResources().getColor(R.color.textTabLight));
 notificationLabel.setTextSize(16);
 }
 if (position==1)
 {

 profileLabel.setTextColor(getResources().getColor(R.color.textTabLight));
 profileLabel.setTextSize(16);
 usersLabel.setTextColor(getResources().getColor(R.color.textTabBright));
 usersLabel.setTextSize(22);
 /**notificationLabel.setTextColor(getResources().getColor(R.color.textTabLight));
 notificationLabel.setTextSize(16);
 }
 /** if (position==2)
 {

 profileLabel.setTextColor(getResources().getColor(R.color.textTabLight));
 profileLabel.setTextSize(16);
 usersLabel.setTextColor(getResources().getColor(R.color.textTabLight));
 usersLabel.setTextSize(16);
 notificationLabel.setTextColor(getResources().getColor(R.color.textTabBright));
 notificationLabel.setTextSize(22);
 }
 }

 @Override
 protected void onStart() {
 super.onStart();
 FirebaseUser currentUser=mAuth.getCurrentUser();
 if (currentUser==null)
 {
 sendToLogin();
 }else {
 verifyUserDetails();
 }

 }

 private void verifyUserDetails() {
 String userId=mAuth.getCurrentUser().getUid();
 myUsersDatabase.child(userId).addValueEventListener(new ValueEventListener() {
 @Override
 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

 if (dataSnapshot.exists())
 {
 String name=dataSnapshot.child("name").getValue().toString();
 tvUsername.setText("You are signed in as:  "+name.toUpperCase()+ "  Sign out ? ");
 tvUsername.setOnClickListener(new View.OnClickListener() {
 @Override
 public void onClick(View v) {
 AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
 builder.setTitle("Confirm Action");
 builder.setMessage("Do you want to Sign out?");
 builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
 @Override
 public void onClick(DialogInterface dialog, int which) {

 mAuth.signOut();
 sendToLogin();
 }
 });
 builder.setNegativeButton("Later",null);
 builder.show();

 }
 });

 }else {
 /** startActivity(new Intent(   MainActivity.this,UserDetailsActivity.class));
 finish();
 }
 }

 @Override
 public void onCancelled(@NonNull DatabaseError databaseError) {

 }
 });
 }

 private void sendToLogin() {
 startActivity(new Intent(MainActivity.this,LoginActivity.class));

 finish();
 }
 }*/
