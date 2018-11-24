package gradle.nanodegree.ed.mo.prof.builditbiggerfourth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import gradle.nanodegree.ed.mo.prof.jokingandroidlib.JokeActivity;
import gradle.nanodegree.ed.mo.prof.jokingjavalib.JokerClass;

/**
 * Created by Prof-Mohamed Atef on 11/22/2018.
 */

public class MainActivityFragment extends Fragment implements OnTaskCompleted{

    public ProgressDialog mProgressDialog;
    GCEndPointAsyncTask gcEndPointAsyncTask;
    private static String PhysicalIPAddress="http://192.168.1.2:8080/_ah/api/";
    private static String EmulatorIPAddress="http://10.0.2.2:8080/_ah/api/";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        Button runAsync= view.findViewById(R.id.btn_Fetch);
        runAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog = ProgressDialog.show(getActivity(), getString(R.string.loading_msg), getString(R.string.pls_wait), true);
                gcEndPointAsyncTask=new GCEndPointAsyncTask(MainActivityFragment.this);
//                gcEndPointAsyncTask.execute();
                //ip for virtual Box
//                gcEndPointAsyncTask.execute("http://192.168.221.2:8080/_ah/api/");
                // my ip address on LAN (IPV4)
//                gcEndPointAsyncTask.execute(PhysicalIPAddress);
                gcEndPointAsyncTask.execute(EmulatorIPAddress);

            }
        });
        AdView adView=(AdView) view.findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);
        return view;
    }

    private static String ConnTimedOut="connect timed out";
    private static String Failed10022="failed to connect to /10.0.2.2 (port 8080) after 20000ms";
    private static String ECONNREFUSED="failed to connect to /"+ PhysicalIPAddress +"(port 8080) after 20000ms: isConnected failed: ECONNREFUSED (Connection refused)";
    @Override
    public void onTaskCompleted(String result) {
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
        Toast.makeText(getActivity(), getResources().getString(R.string.Whatisyourjoke), Toast.LENGTH_SHORT).show();
    }
}