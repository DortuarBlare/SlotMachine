package com.example.slotmachine;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Button startButton;
    ImageView slotMachine;
    ImageView coin;
    ImageView firstSymbolMachine;
    ImageView secondSymbolMachine;
    ImageView thirdSymbolMachine;
    Timer timer = new Timer();
    int symbolNumber1, symbolNumber2, symbolNumber3;
    TimerTask symbolChangeTask = new TimerTask() {
        @Override
        public void run() {
            symbolNumber1 = setRandomSymbolMachine(firstSymbolMachine);
            symbolNumber2 = setRandomSymbolMachine(secondSymbolMachine);
            symbolNumber3 = setRandomSymbolMachine(thirdSymbolMachine);
        }
    };

    private void stopTimer() {
        timer.cancel();
        timer.purge();
        timer = new Timer();
        symbolChangeTask = new TimerTask() {
            @Override
            public void run() {
                symbolNumber1 = setRandomSymbolMachine(firstSymbolMachine);
                symbolNumber2 = setRandomSymbolMachine(secondSymbolMachine);
                symbolNumber3 = setRandomSymbolMachine(thirdSymbolMachine);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.button2);
        slotMachine = findViewById(R.id.imageView);
        coin = findViewById(R.id.imageView8);
        firstSymbolMachine = findViewById(R.id.imageView9);
        secondSymbolMachine = findViewById(R.id.imageView10);
        thirdSymbolMachine = findViewById(R.id.imageView11);

        RotateAnimation rotate = new RotateAnimation(
                0, 3600, RotateAnimation.RELATIVE_TO_SELF,
                0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(3600);
        rotate.setFillAfter(true);
        rotate.setInterpolator(new DecelerateInterpolator());
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                slotMachine.setImageDrawable(getResources().getDrawable(R.drawable.slot_machine1_2));
                timer.schedule(symbolChangeTask, 0, 250);
                startButton.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                slotMachine.setImageDrawable(getResources().getDrawable(R.drawable.slot_machine1));
                stopTimer();
                startButton.setEnabled(true);
                System.out.println(firstSymbolMachine.getTag());
                System.out.println(secondSymbolMachine.getTag());
                System.out.println(thirdSymbolMachine.getTag());
                if (symbolNumber1 == symbolNumber2 && symbolNumber1 == symbolNumber3)
                    sendWinMessage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        startButton.setOnClickListener(view -> {
            coin.startAnimation(rotate);
        });
    }

    public int setRandomSymbolMachine(ImageView imageView) {
        int randomSymbol = (int) (Math.random() * 4);
        if (randomSymbol == 0)
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.slot_symbol1));
        else if (randomSymbol == 1)
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.slot_symbol2));
        else if (randomSymbol == 2)
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.slot_symbol3));
        else if (randomSymbol == 3)
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.slot_symbol4));
        return randomSymbol;
    }

    public void sendWinMessage() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Вы выиграли!", Toast.LENGTH_LONG);
        toast.show();
    }
}
