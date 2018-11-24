package gradle.nanodegree.ed.mo.prof.builditbiggerfourth;

import android.app.ProgressDialog;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.concurrent.TimeUnit;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Prof-Mohamed Atef on 11/23/2018.
 */
@RunWith(AndroidJUnit4.class)
public class GCEndPointsAsyncTaskTest {

    private static String EmulatorIPAddress="http://10.0.2.2:8080/_ah/api/";
    public ProgressDialog mProgressDialog;

    @Test
    public void DoInBackGroundTests(){
        try{
            MainActivityFragment mainActivityFragment=new MainActivityFragment();
            mProgressDialog = ProgressDialog.show(mainActivityFragment.getActivity(), mainActivityFragment.getString(R.string.loading_msg), mainActivityFragment.getString(R.string.pls_wait), true);
            GCEndPointAsyncTask gcEndPointAsyncTask=new GCEndPointAsyncTask(mainActivityFragment);
            gcEndPointAsyncTask.execute(EmulatorIPAddress);
            String testResult=gcEndPointAsyncTask.get(60, TimeUnit.SECONDS);

            assertNotNull(testResult);

            assertTrue(testResult.length()>0);
            mProgressDialog.dismiss();
        }catch (Exception e){
            Log.e("TestEndpointsAsyncTask", "DoInBackground____Testing: :) :) Timed out");
        }
    }
}