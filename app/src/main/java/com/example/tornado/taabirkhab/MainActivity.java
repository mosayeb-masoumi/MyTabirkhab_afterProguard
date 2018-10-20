package com.example.tornado.taabirkhab;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAd;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellAdShowListener;
import ir.tapsell.sdk.TapsellShowOptions;
import ir.tapsell.sdk.bannerads.TapsellBannerType;
import ir.tapsell.sdk.bannerads.TapsellBannerView;

public class MainActivity extends AppCompatActivity {

    public static Typeface font;
    public static MyDatabase database;

    TapsellAd ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

//        loadAd("5bc6e15d56e3c70001ff4bcc", TapsellAdRequestOptions.CACHE_TYPE_CACHED);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // this code will be executed after 2 seconds
                loadAd("5bc6e15d56e3c70001ff4bcc", TapsellAdRequestOptions.CACHE_TYPE_CACHED);
            }
        }, 4000);


        //request every each special time
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loadAd("5b307d7fbaa86f0001869997", TapsellAdRequestOptions.CACHE_TYPE_CACHED);
                handler.postDelayed(this, 600000);
            }
        };
        handler.postDelayed(runnable, 600000);





        TapsellBannerView banner1 = (TapsellBannerView)findViewById(R.id.banner1);

        //zoneId standard banner(for activating standard banner goto (https://dashboard.tapsell.ir) click on taarif tabigh gah jadid va entekhab standard banner va kharej kardan an az halate test.
        banner1.loadAd(MainActivity.this, "5bc6df5156e3c70001ff4bcb", TapsellBannerType.BANNER_320x50);



        font = Typeface.createFromAsset(getAssets(),"font/iran sans light.ttf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title_toolbar = (TextView) findViewById(R.id.title_toolbar);
        TextView txt_list = (TextView) findViewById(R.id.txt_list);
        TextView txt_favorite = (TextView) findViewById(R.id.txt_favorite);
        TextView txt_rate = (TextView) findViewById(R.id.txt_rate);
        CardView card_list = (CardView) findViewById(R.id.card_list);
        CardView card_favorite = (CardView) findViewById(R.id.card_favorite);
        CardView card_rate = (CardView) findViewById(R.id.card_rate);
        ImageView img_more = (ImageView) findViewById(R.id.img_menu_more);
        ImageView img_share=findViewById(R.id.img_share3);

        final android.support.v7.widget.PopupMenu popupMenu = new android.support.v7.widget.PopupMenu(this,img_more);
        popupMenu.getMenuInflater().inflate(R.menu.menu,popupMenu.getMenu());

        img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
            }
        });

        popupMenu.setOnMenuItemClickListener(new android.support.v7.widget.PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("منبع")
                        .setMessage("اطلاعات این برنامه از سایت www.beytoote.com جمع آوری شده است")
                        .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .create();
                alert.show();
                return true;
            }
        });


        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApplication();
            }
        });







        title_toolbar.setTypeface(font);
        txt_list.setTypeface(font);
        txt_favorite.setTypeface(font);
        txt_rate.setTypeface(font);



        database  = new MyDatabase(this);
        card_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ActivityList.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in_forward, R.anim.activity_out_forward);
            }
        });

        card_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAppInstalled(MainActivity.this,"com.farsitel.bazaar")) {
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setData(Uri.parse("bazaar://details?id=" + "ir.mikaelr.tabirabi"));
                    intent.setPackage("com.farsitel.bazaar");
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_in_forward, R.anim.activity_out_forward);
                }else {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("خطا");
                    dialog.setMessage("برنامه کافه بازار بر روی گوشی شما نصب نیست");
                    dialog.setPositiveButton("نصب کافه بازار", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent link = new Intent(Intent.ACTION_VIEW,Uri.parse("https://cafebazaar.ir/install/?l=fa"));
                            startActivity(link);
                            overridePendingTransition(R.anim.activity_in_forward, R.anim.activity_out_forward);
                        }
                    });
                    dialog.setCancelable(true);
                    dialog.setNegativeButton("لغو", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialog.show();
                }
            }
        });

        card_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ActivityFavorite.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in_forward, R.anim.activity_out_forward);
            }
        });

    }

    private void shareApplication() {

        ApplicationInfo app = getApplicationContext().getApplicationInfo();
        String filePath = app.sourceDir;

        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("*/*");

        File originalApk = new File(filePath);

        try {
            File tempFile = new File(getExternalCacheDir() + "/ExtractedApk");
            //If directory doesn't exists create new
            if (!tempFile.isDirectory())
                if (!tempFile.mkdirs())
                    return;
            tempFile = new File(tempFile.getPath() + "/" + getString(app.labelRes).replace(" ","").toLowerCase() + ".apk");
            if (!tempFile.exists()) {
                if (!tempFile.createNewFile()) {
                    return;
                }
            }
            InputStream in = new FileInputStream(originalApk);
            OutputStream out = new FileOutputStream(tempFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            System.out.println("File copied.");
            // باز کردن پنجره اشتراک گذاری
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
            startActivity(Intent.createChooser(intent, "اشتراک گذاری با"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }










    public void showAD(){
        if (ad != null) {

            TapsellShowOptions showOptions = new TapsellShowOptions();
            showOptions.setBackDisabled(false);
            showOptions.setImmersiveMode(true);
            showOptions.setRotationMode(TapsellShowOptions.ROTATION_UNLOCKED);
            showOptions.setShowDialog(true);

            showOptions.setWarnBackPressedDialogMessage("سلام دوست من بک نزن");
            showOptions.setWarnBackPressedDialogMessageTextColor(Color.RED);
            showOptions.setWarnBackPressedDialogAssetTypefaceFileName("IranNastaliq.ttf");
            showOptions.setWarnBackPressedDialogPositiveButtonText("ادامه بده");
            showOptions.setWarnBackPressedDialogNegativeButtonText("ولم کن بزن بیرون");
            //      showOptions.setWarnBackPressedDialogPositiveButtonBackgroundResId(R.drawable.button_background);
//        showOptions.setWarnBackPressedDialogNegativeButtonBackgroundResId(R.drawable.button_background);
            showOptions.setWarnBackPressedDialogPositiveButtonTextColor(Color.WHITE);
            showOptions.setWarnBackPressedDialogNegativeButtonTextColor(Color.GREEN);
//        showOptions.setWarnBackPressedDialogBackgroundResId(R.drawable.dialog_background);
            ad.show(MainActivity.this, showOptions);
            ad.show(MainActivity.this, showOptions, new TapsellAdShowListener() {
                @Override
                public void onOpened(TapsellAd ad) {
                    Log.e("MainActivity", "on ad opened");
                }

                @Override
                public void onClosed(TapsellAd ad) {
                    Log.e("MainActivity", "on ad closed");
                }
            });
            MainActivity.this.ad = null;
        }

    }


    private void loadAd(final String zoneId, final int catchType) {

        if (ad == null) {
            TapsellAdRequestOptions options = new TapsellAdRequestOptions(catchType);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading ...");
//            progressDialog.show();
            Tapsell.requestAd(MainActivity.this, zoneId, options, new TapsellAdRequestListener() {
                @Override
                public void onError(String error) {
//                    Toast.makeText(MainActivity.this, "ERROR:\n" + error, Toast.LENGTH_SHORT).show();
                    Log.e("Tapsell", error);
//                    progressDialog.dismiss();
                }

                @Override
                public void onAdAvailable(TapsellAd ad) {

                    MainActivity.this.ad = ad;
//                    showAddBtn.setEnabled(true);
                    Log.e("Tapsell", "adId: " + (ad == null ? "NULL" : ad.getId()) + " available, zoneId: " + (ad == null ? "NULL" : ad.getZoneId()));
//                    progressDialog.dismiss();

                    //todo show ad method
                    showAD();




//                new AlertDialog.Builder(MainActivity.this).setTitle("Title").setMessage("Message").show();
                }

                @Override
                public void onNoAdAvailable() {
//                    Toast.makeText(MainActivity.this, "No Ad Available", Toast.LENGTH_LONG).show();
//                    progressDialog.dismiss();
                    Log.e("Tapsell", "No Ad Available");
                }

                @Override
                public void onNoNetwork() {
//                    Toast.makeText(MainActivity.this, "No Network", Toast.LENGTH_SHORT).show();
//                    progressDialog.dismiss();
                    Log.e("Tapsell", "No Network Available");
                }

                @Override
                public void onExpiring(TapsellAd ad) {
                    MainActivity.this.ad = null;
                    loadAd(zoneId, catchType);
                }
            });
        }
    }


}