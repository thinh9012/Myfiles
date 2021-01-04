//write by q_hiep
package english.listening.cambrigeenglishlistening.until;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import english.listening.cambrigeenglishlistening.R;
import english.listening.cambrigeenglishlistening.entities.English;
import english.listening.cambrigeenglishlistening.model.Voca;
import translate.coder.translate.TranslateTran;
import translate.coder.translate.WordListennerTran;
import translate.coder.translate.WordSpanTran;

public class Helper {
    private Activity tsActivity;
    public boolean pmtDownload = false;
    private ArrayList<Long> list = new ArrayList<>();

    public Helper(Activity activity) {
        tsActivity = activity;
    }

    //region REGION network
    /**
     * isInternetConnected
     *
     * @return
     */
    public boolean isInternetConnected() {
        try {
            ConnectivityManager connMgr = (ConnectivityManager) tsActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.v("error", e.toString());
            return false;
        }
    }
    //endregion

    //region REGION toast
    /**
     * toast
     *
     * @param string
     */
    public void toast(String string) {
        LayoutInflater inflater = tsActivity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) tsActivity.findViewById(R.id.toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(string);

        Toast toast = new Toast(tsActivity.getApplicationContext());
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    //endregion

    //region REGION permission
    /**
     * requestStoragePermission
     */
    public void requestStoragePermission() {

        Dexter.withActivity(tsActivity)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            pmtDownload = true;
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            pmtDownload = false;
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText((Activity) tsActivity, "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    /**
     * showSettingsDialog
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context) tsActivity);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    /**
     * openSettings
     */
    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", tsActivity.getPackageName(), null);
        intent.setData(uri);
        (tsActivity).startActivityForResult(intent, 101);
    }

    //endregion

    //region REGION download
    /**
     * downloadFile
     *
     * @param en
     */
    public void downloadFile(English en) {

        long refid;
        DownloadManager downloadManager;
        downloadManager = (DownloadManager) tsActivity.getSystemService(Context.DOWNLOAD_SERVICE);
        tsActivity.registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        list.clear();

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(en.getAudio()));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle(en.getName());
        request.setDescription(en.getName() + ".mp3");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/" + Constant.folder + "/" + "/" + en.getId() + ".mp3");
        refid = downloadManager.enqueue(request);
        Log.e("OUT", "" + refid);
        list.add(refid);
    }

    public void downloadVoca(Voca en,String id) {

        long refid;
        DownloadManager downloadManager;
        downloadManager = (DownloadManager) tsActivity.getSystemService(Context.DOWNLOAD_SERVICE);
        tsActivity.registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        list.clear();

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(en.getMp3()));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle(en.getWord());
        request.setDescription(en.getWord() + ".mp3");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/" + Constant.folder + "/" + "/" + id + ".mp3");
        refid = downloadManager.enqueue(request);
        Log.e("OUT", "" + refid);
        list.add(refid);
    }
    /**
     * onComplete
     */
    public BroadcastReceiver onComplete = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {

            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            Log.e("IN", "" + referenceId);

            list.remove(referenceId);

        }
    };
    //endregion

    //region REGION rate
    /**
     * rateUsFinish
     */
    public void rateUsFinish() {

        new android.support.v7.app.AlertDialog.Builder(new ContextThemeWrapper(tsActivity, R.style.AppCompatAlertDialogStyle))
                .setTitle("Rate us 5 stars.")
                .setIcon(R.drawable.ic_launcher)
                .setMessage("Please rate us 5 stars if you love this app. \n1. Click on \"Rate\" button\n2. Scroll down and click on ☆☆☆☆☆ to rate us \n3. Leave us review")
                .setPositiveButton("Yes Now", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        rate();
                    }
                })
                .setNegativeButton("I'll do it later", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        tsActivity.finish();
                    }
                })
                .show();
    }

    /**
     * rate
     */
    private void rate() {
        try {
            tsActivity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + tsActivity.getPackageName())));
        } catch (android.content.ActivityNotFoundException anfe) {
            tsActivity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id="
                            + tsActivity.getPackageName())));
        }
    }
    //endregion

    //region REGION load new act
    public static void loadNewActivity(Context context,Class classs)
    {
        Intent myIntent = new Intent(context, classs);
        context.startActivity(myIntent);
        ((Activity)context).overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        ((Activity) context).finish();
    }
    //endregion


    public void reloadPage() {
        Constant.click = false;
        Intent intent = tsActivity.getIntent();
        tsActivity.overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        tsActivity.finish();
        tsActivity.overridePendingTransition(0, 0);
        tsActivity.startActivity(intent);
    }

    //region REGION local store
    public Boolean getNext() {
        SharedPreferences shp = tsActivity.getSharedPreferences(Constant.folder, 0);
        return shp.getBoolean("isnext", false);
    }
    public Float getZoom() {
        SharedPreferences shp = tsActivity.getSharedPreferences(Constant.folder, 0);
        return shp.getFloat("zoom", 0);
    }
    public Float getSpeed() {
        SharedPreferences shp = tsActivity.getSharedPreferences(Constant.folder, 0);
        return shp.getFloat("speed", 1);
    }


    public void setNext(Boolean isnext) {
        SharedPreferences shp = tsActivity.getSharedPreferences(Constant.folder, 0);
        SharedPreferences.Editor editor = shp.edit();
        editor.putBoolean("isnext", isnext);
        editor.commit();
    }

    public void setZoom(Float zoom) {
        SharedPreferences shp = tsActivity.getSharedPreferences(Constant.folder, 0);
        SharedPreferences.Editor editor = shp.edit();
        editor.putFloat("zoom", zoom);
        editor.commit();
    }

    public void setSpeed(Float speed) {
        SharedPreferences shp = tsActivity.getSharedPreferences(Constant.folder, 0);
        SharedPreferences.Editor editor = shp.edit();
        editor.putFloat("speed", speed);
        editor.commit();
    }

    public float getConvertedValue(int intVal){
        float floatVal;
        floatVal = 1f * intVal;
        return floatVal;
    }

    //endregion

    public void launchApp(String packageName) {
        Intent intent = tsActivity.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + packageName));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        tsActivity.startActivity(intent);
    }

    public void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, Uri.parse("market://details?id=" + tsActivity.getPackageName()).toString());

        tsActivity.startActivity(Intent.createChooser(share, "Share App!"));

    }

    /**
     * translate
     * @param context
     * @param helper
     * @param wordList
     */
    public static void translate(Context context,final Helper helper, List<TextView> wordList)
    {
        final TranslateTran tran = new TranslateTran(context);
        tran.setColor(context.getResources().getColor(R.color.colorPrimary));
        WordListennerTran translateWordListenner = new WordListennerTran() {
            @Override
            public void translateWord(String str) {
                if (helper.isInternetConnected()) {
                    tran.settupLang(str);
                }
            }
        };
        WordSpanTran.click(wordList, translateWordListenner);
    }
}
