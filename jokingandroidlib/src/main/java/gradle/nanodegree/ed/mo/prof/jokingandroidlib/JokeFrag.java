package gradle.nanodegree.ed.mo.prof.jokingandroidlib;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Prof-Mohamed Atef on 11/19/2018.
 */

public class JokeFrag extends Fragment {

    private String JOKE="MyJoke";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_jokes, container, false);
        Intent intent=getActivity().getIntent();
        String joke=intent.getStringExtra(JOKE);
        TextView jokeText=(TextView)view.findViewById(R.id.joke_txt);
        if (joke!=null&&joke.length()>0){
            jokeText.setText(joke);
        }
        return view;
    }
}