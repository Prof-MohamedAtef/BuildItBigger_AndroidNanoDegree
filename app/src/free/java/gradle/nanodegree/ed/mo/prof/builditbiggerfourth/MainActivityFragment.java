package gradle.nanodegree.ed.mo.prof.builditbiggerfourth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import gradle.nanodegree.ed.mo.prof.jokingandroidlib.JokeActivity;

/**
 * Created by Prof-Mohamed Atef on 11/23/2018.
 */

public class MainActivityFragment extends Fragment implements OnTaskCompleted{

    private static String PhysicalIPAddress="http://192.168.1.2:8080/_ah/api/";
    private static String EmulatorIPAddress="http://10.0.2.2:8080/_ah/api/";

    Button BTNFetchJoke;
    ProgressDialog mProgressDialog;
    AdView adView;
    AdRequest adRequest;
    InterstitialAd interstitialAd;
    private boolean adOnScreen=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        BTNFetchJoke=(Button)view.findViewById(R.id.btn_Fetch);
        if (BuildConfig.FREE_VERSION){
            adView=(AdView) view.findViewById(R.id.adView);
            AdRequest adRequest=new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            adView.loadAd(adRequest);

            interstitialAd=new InterstitialAd(getActivity());
            interstitialAd.setAdUnitId(getString(R.string.interstial_unit_test_id));

            interstitialAd.setAdListener(new AdListener(){
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    adOnScreen = false;
                    startNewInterstitialAd();
                }
            });

        }
        BTNFetchJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd.isLoaded()){
                    adOnScreen=true;
                    interstitialAd.show();
                }else {
                    adOnScreen=false;
                }
                getJokes();
                mProgressDialog = ProgressDialog.show(getActivity(), getString(R.string.loading_msg), getString(R.string.pls_wait), true);
            }
        });
        return view;
    }

    private void startNewInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        interstitialAd.loadAd(adRequest);
    }

    private void getJokes() {
        GCEndPointAsyncTask gcEndPointAsyncTask= new GCEndPointAsyncTask(MainActivityFragment.this);
        gcEndPointAsyncTask.execute(EmulatorIPAddress);
    }

    private static String ConnTimedOut="connect timed out";
    private static String Failed10022="failed to connect to /10.0.2.2 (port 8080) after 20000ms";
    private static String ECONNREFUSED="failed to connect to /"+ PhysicalIPAddress +"(port 8080) after 20000ms: isConnected failed: ECONNREFUSED (Connection refused)";

    @Override
    public void onTaskCompleted(String result) {
        adOnScreen=false;
        mProgressDialog.dismiss();
        if (result.length()>0){

            if (result.equals(ConnTimedOut)||result.equals(ECONNREFUSED)||result.equals(Failed10022)){
                Toast.makeText(getActivity(), ConnTimedOut, Toast.LENGTH_SHORT).show();
            }else {
                Intent intent=new Intent(getActivity(), JokeActivity.class);
                intent.putExtra(JokeActivity.JOKE, result);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onTaskError() {
        adOnScreen=false;
        mProgressDialog.dismiss();
        Toast.makeText(getActivity(), getString(R.string.nnnn), Toast.LENGTH_SHORT).show();
    }
}
