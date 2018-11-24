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

import gradle.nanodegree.ed.mo.prof.jokingandroidlib.JokeActivity;

/**
 * Created by Prof-Mohamed Atef on 11/23/2018.
 */

public class PaidActivityFragment extends Fragment implements OnTaskCompleted {
    private static String PhysicalIPAddress="http://192.168.1.2:8080/_ah/api/";
    private static String EmulatorIPAddress="http://10.0.2.2:8080/_ah/api/";

    Button BTNFetchJoke;
    ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTheme(R.style.PaidTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_paid, container, false);
        BTNFetchJoke=(Button)view.findViewById(R.id.btn_Fetch);
        BTNFetchJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJokes();
                mProgressDialog = ProgressDialog.show(getActivity(), getString(R.string.loading_msg), getString(R.string.pls_wait), true);
            }
        });
        return view;
    }

    private void getJokes() {
        GCEndPointAsyncTask gcEndPointAsyncTask= new GCEndPointAsyncTask(PaidActivityFragment.this);
        gcEndPointAsyncTask.execute(EmulatorIPAddress);
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