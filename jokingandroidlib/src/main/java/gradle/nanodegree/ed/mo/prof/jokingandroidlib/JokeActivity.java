package gradle.nanodegree.ed.mo.prof.jokingandroidlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class JokeActivity extends AppCompatActivity {

    public final static String JOKE="MyJoke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        getSupportFragmentManager().beginTransaction().add(R.id.JokeFrag,new JokeFrag()).commit();
    }
}
