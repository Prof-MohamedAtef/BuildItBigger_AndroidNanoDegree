package gradle.nanodegree.ed.mo.prof.builditbiggerfourth;

import android.os.AsyncTask;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import java.io.IOException;
import gradle.nanodegree.ed.mo.prof.builditbiggerfourth.backend.myApi.MyApi;

/**
 * Created by Prof-Mohamed Atef on 11/23/2018.
 */

public class GCEndPointAsyncTask extends AsyncTask<String, Void, String> {

    private static MyApi myApi=null;
    private OnTaskCompleted onTaskCompletedListener;

    public GCEndPointAsyncTask(OnTaskCompleted listener){
        this.onTaskCompletedListener=listener;
    }

    @Override
    protected String doInBackground(String... params) {
        if (myApi==null){
            MyApi.Builder builder=new MyApi.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(),
                    null).setRootUrl(
                            params[0]
            ).setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                    request.setDisableGZipContent(true);
                }
            });
            myApi=builder.build();
        }
        try{
            return myApi.getJoke().execute().getData();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (onTaskCompletedListener!=null){
            if (result!=null){
                onTaskCompletedListener.onTaskCompleted(result);
            }else {
                onTaskCompletedListener.onTaskError();
            }
        }
    }
}
