package gradle.nanodegree.ed.mo.prof.jokingjavalib;

import java.util.Random;

public class JokerClass {
    private static final String[] jokes={
            "What's your First Name",
            "Are you going to travel abroad ?",
            "Mark says he don't like going to school, that's mean : "
    };
    public static String MyToldJokes(){
        int size=jokes.length;
        Random r=new Random();
        int index=r.nextInt(size);
        return jokes[index];
    }
}
