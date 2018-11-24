package gradle.nanodegree.ed.mo.prof.builditbiggerfourth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.Fragment_container)
    android.widget.FrameLayout FrameLayout;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder= ButterKnife.bind(this);
        MainActivityFragment mainActivityFragment=new MainActivityFragment();

        if (BuildConfig.FREE_VERSION){
            getSupportFragmentManager().beginTransaction()
                    .replace(FrameLayout.getId(), mainActivityFragment, "Tag").commit();
        }else if (!BuildConfig.FREE_VERSION){
            getSupportFragmentManager().beginTransaction()
                    .replace(FrameLayout.getId(), new PaidActivityFragment(), "Tag").commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
