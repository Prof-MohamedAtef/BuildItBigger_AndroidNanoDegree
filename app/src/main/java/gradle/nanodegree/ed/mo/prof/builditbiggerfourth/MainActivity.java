package gradle.nanodegree.ed.mo.prof.builditbiggerfourth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import gradle.nanodegree.ed.mo.prof.jokingandroidlib.JokeActivity;
import gradle.nanodegree.ed.mo.prof.jokingjavalib.JokerClass;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void launchJokingActivity(View view) {
        String joke=JokerClass.MyToldJokes();
        if (joke==null&&joke.length()==0) {
            Toast.makeText(this, getResources().getString(R.string.Whatisyourjoke), Toast.LENGTH_SHORT).show();
        }else{
            Intent intent=new Intent(getApplicationContext(), JokeActivity.class);
            intent.putExtra(JokeActivity.JOKE, joke);
            startActivity(intent);
        }
    }
}
