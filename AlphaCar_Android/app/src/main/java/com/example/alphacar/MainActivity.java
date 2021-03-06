package com.example.alphacar;

import static com.example.alphacar.Common.CommonMethod.isNetworkConnected;
import static com.example.alphacar.LoginPageActivity.loginDTO;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.bumptech.glide.Glide;
import com.example.alphacar.ATask.IdCheck;
import com.example.alphacar.ATask.KakaoJoinInsert;
import com.example.alphacar.ATask.KakaoLogin;
import com.example.alphacar.ATask.Storelist;
import com.example.alphacar.Adapter.ViewpagerAdapter;
import com.example.alphacar.DTOS.StoreDTO;
import com.example.alphacar.Fragment.ButtonFragment;
import com.example.alphacar.Fragment.FavoriteFragment;
import com.example.alphacar.Fragment.Main_search_bar_Fragment;
import com.example.alphacar.Fragment.ViewpagerFragment;
import com.example.alphacar.Fragment.AnnounceFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.kakao.plusfriend.PlusFriendService;
import com.kakao.util.exception.KakaoException;
import com.lakue.lakuepopupactivity.PopupActivity;
import com.lakue.lakuepopupactivity.PopupGravity;
import com.lakue.lakuepopupactivity.PopupResult;
import com.lakue.lakuepopupactivity.PopupType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {


    private String strNick, strProfileImg, strEmail;

    FrameLayout search_bar;
    Button btn_cp_reg;
    ImageView kakao_ch, kakao_chat, web_alphacar;


    NavigationView nav_view;
    SearchView searchView;
    DrawerLayout draw_layout;
    ViewPager pager;

    ArrayList<StoreDTO> storeDTOArrayList;

    IdCheck idCheck;
    Storelist storelist;
    KakaoLogin kakaoLogin;
    KakaoJoinInsert kakaoJoinInsert;

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            PopupResult result = (PopupResult) data.getSerializableExtra("result");
            if(result == PopupResult.LEFT){
                Intent intent = new Intent(this, LoginPageActivity.class);
                startActivity(intent);
            }
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        getHashKey();


        search_bar = findViewById(R.id.search_bar);

        pager = findViewById(R.id.pager);
        btn_cp_reg = findViewById(R.id.btn_cp_reg);

        searchView = findViewById(R.id.searchView);
        nav_view = findViewById(R.id.nav_view);
        // implement Listener ??? ?????? ????????? ????????? ?????? ?????? ??????.
        nav_view.setNavigationItemSelectedListener(this);
        draw_layout = findViewById(R.id.draw_layout);
        kakao_ch = findViewById(R.id.btn_kakao_ch);
        kakao_chat = findViewById(R.id.btn_kakao_chat);

        web_alphacar = findViewById(R.id.web_alphacar);

        storeDTOArrayList = new ArrayList<>();
        /* ?????? ??????????????? ????????? ???????????? */
        storeDTOArrayList = new ArrayList<StoreDTO>();

        View headerView = nav_view.getHeaderView(0);
        ImageView login_image = headerView.findViewById(R.id.loginImage);
        TextView login_text = headerView.findViewById(R.id.loginID);
        TextView login_str = headerView.findViewById(R.id.loginStr);
        if (loginDTO != null) {
            if (!loginDTO.getAdmin().equals("M")) {
                btn_cp_reg.setVisibility(View.INVISIBLE);
            }
        } else {
            btn_cp_reg.setVisibility(View.INVISIBLE);
        }

        /* ????????? ??????????????? ????????? ?????? ????????? ???????????? ?????? */
        if (loginDTO == null) {
            nav_view.getMenu().findItem(R.id.nav_myPage).setTitle("?????????");
            nav_view.getMenu().findItem(R.id.nav_logout).setVisible(false);
        }
        viewpagerShow(null);

        /* ????????? ??????????????? */
        Fragment fragment = new Main_search_bar_Fragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contain, fragment).commit();




        btn_cp_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_cp_reg.setVisibility(View.INVISIBLE);
                Fragment fragment = new ButtonFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.contain, fragment).addToBackStack(null).commit();
            }
        });

        kakao_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PlusFriendService.getInstance().addFriend(MainActivity.this, "_XuZTb");
                } catch (KakaoException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


        kakao_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PlusFriendService.getInstance().chat(MainActivity.this, "_XuZTb");
                } catch (KakaoException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        web_alphacar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.0.122:8080/alphacar/"));
                //intent.setPackage("com.android.chrome");   // ??????????????? ????????? ??? ?????? ??? ????????? ????????? ????????? ??????
                startActivity(intent);

            }
        });


/*        Intent intent = new Intent(MainActivity.this, LoadingPageActivity.class);
        startActivity(intent);*/



  /*          animationView = findViewById(R.id.lottie);
            animationView.setAnimation("test8.json");
            animationView.loop(true);
            animationView.playAnimation();
            animationView.setRepeatCount(2);
*/


////////////////////////////////////////////////////////////////////////////////////////////////////
        /* tab1 ??? ?????? ????????????????????? */
        findViewById(R.id.tab1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        /*tab2 ????????????  ?????????????????????*/

        findViewById(R.id.tab2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginDTO != null) {
                    Fragment fragment = new FavoriteFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contain, fragment).addToBackStack(null).commit();
                } else {
                    Intent intent = new Intent(getBaseContext(), PopupActivity.class);
                    intent.putExtra("type", PopupType.SELECT);
                    intent.putExtra("gravity", PopupGravity.LEFT);
                    intent.putExtra("title", "??????");
                    intent.putExtra("content", "???????????? ????????? ????????? ?????????");
                    intent.putExtra("buttonLeft", "?????????");
                    intent.putExtra("buttonRight", "????????????");
                    startActivityForResult(intent, 2);
                }
            }
        });


        /*tab3 ?????????,????????????  ?????????????????????*/
        findViewById(R.id.tab3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginJoinSelectActivity.class);
                startActivity(intent);
            }
        });

        /*tab4 ??????????????? ?????????????????????*/
        findViewById(R.id.tab4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loginDTO != null) {
                    //   login_image.setImageResource(loginDTO.getCustomer_picture());
                    Glide.with(MainActivity.this)
                            .load(loginDTO.getCustomer_picture())
                            // .load("https://upload3.inven.co.kr/upload/2020/06/21/bbs/i015955522648.gif")//.load("url?????? png,gif,jpg?????? ?????? ??????")
                            .circleCrop()
                            .into(login_image);
                    login_text.setText(loginDTO.getCustomer_name());
                    login_str.setText(loginDTO.getCustomer_email());
                }


                draw_layout.openDrawer(GravityCompat.START);

            }
        });

///////////////////////////////////////////////////////////////////////////////////////////////////////


        /*?????? ???????????????*/
        bottomNavigationView = findViewById(R.id.bottom_navi);
        if (loginDTO != null) {
            bottomNavigationView.getMenu().findItem(R.id.tab3).setTitle("????????????");
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return true;
            }

        });
/////////////////////////////////////////////////////////////////////////////////////////////////////////
        //?????????
        Intent intent = getIntent();
        strNick = intent.getStringExtra("name");
        strEmail = intent.getStringExtra("email");
        strProfileImg = intent.getStringExtra("profileImg");
        // strid =intent.getStringExtra("id");

        String result = "";
        if (strEmail != null) {
            if (isNetworkConnected(this) == true) {
                idCheck = new IdCheck(strEmail);
                //    reviewSelect = new ReviewSelect(customer_email,dtos,rdto);
                try {
                    result = idCheck.execute().get().trim();

                    result = result.substring(11, 12);

                    //    reviewSelect.execute().get();
                    //     reviewSelect.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "???????????? ???????????? ?????? ????????????.",
                        Toast.LENGTH_SHORT).show();
            }

            // ?????????
            if (result.equals("1")) {
                if (isNetworkConnected(this) == true) {
                    kakaoLogin = new KakaoLogin(strEmail);
                    //    reviewSelect = new ReviewSelect(customer_email,dtos,rdto);
                    try {
                        kakaoLogin.execute().get();
                        if (loginDTO != null) {
                            refresh();
                        }
                        //    reviewSelect.execute().get();
                        //     reviewSelect.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "???????????? ???????????? ?????? ????????????.",
                            Toast.LENGTH_SHORT).show();
                }

            } else {

                if (isNetworkConnected(this) == true) {
                    //    kakaoJoinInsert = new KakaoJoinInsert(strNick, strEmail, strid);
                    kakaoJoinInsert = new KakaoJoinInsert(strNick, strEmail);
                    //    reviewSelect = new ReviewSelect(customer_email,dtos,rdto);
                    try {
                        kakaoJoinInsert.execute().get();

                        //                //    reviewSelect.execute().get();
                        //     reviewSelect.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "???????????? ???????????? ?????? ????????????.",
                            Toast.LENGTH_SHORT).show();
                }
            }
            //?????????
            //?????????

        }
    }


    /* ??????????????? ?????????*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // ????????? ???????????? ???????????? ?????????.
        int id = item.getItemId();


                if (id == R.id.nav_join) {
                Intent intent = new Intent(this, JoinPageActivity.class);
                startActivity(intent);
                }

                else if (id == R.id.nav_myPage){
                    if (loginDTO != null){
                    Intent intent = new Intent(this, MemberUpdatePageActivity.class);
                    startActivity(intent);
                    }else{
                    Intent intent = new Intent(this, LoginPageActivity.class);
                 startActivity(intent);
                    }
        }else if(id == R.id.nav_setting){
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                }

                else if (id == R.id.nav_noti){
            Fragment fragment = new AnnounceFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contain,fragment).commit();
        }else if(id == R.id.nav_logout){
            // ????????? ????????????
            loginDTO = null;


        }

        // ?????? ?????? ??? ???????????? ???????????? ??????.
        draw_layout.closeDrawer(GravityCompat.START);

        return true;

    }


    // ??????????????? ???????????? ?????? ????????? ?????? ??????????????? ????????? ?????? ??????
    // ????????? ?????? ???????????? ?????? ????????? ??????(???????????? ??? ??????).
    @Override
    public void onBackPressed() {
        if (draw_layout.isDrawerOpen(GravityCompat.START)){
            draw_layout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }



    /* ???????????? */
    public void viewpagerShow(String query){
        if(isNetworkConnected(this) == true){
            storelist  = new Storelist( storeDTOArrayList);
            /* ???????????? */
            try {
                storelist.execute().get();
                //    Log.d(TAG, "onCreate: "+dto.getCustomer_email());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this, "???????????? ???????????? ?????? ????????????.",
                    Toast.LENGTH_SHORT).show();
        }
        ArrayList<Fragment> list = new ArrayList<>();
        for (int i =0; i<storeDTOArrayList.size(); i++){
            list.add(new ViewpagerFragment(storeDTOArrayList.get(i)));

        }

        ViewpagerAdapter viewpagerAdapter =
                new ViewpagerAdapter(getSupportFragmentManager());
        viewpagerAdapter.setList(list);
        pager.setAdapter(viewpagerAdapter);


        storeDTOArrayList = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       finish();

    }


    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }


    public void refresh(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    }


