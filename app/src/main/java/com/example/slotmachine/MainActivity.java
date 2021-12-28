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
    int symbolNumber1 = 0, symbolNumber2 = 1, symbolNumber3 = 2;
    int randomTimeForSymbol1;
    float defaultTranslationY = -235;
    int time = 0;
    boolean firstSymbolDone = false, secondSymbolDone = false, thirdSymbolDone = false;

    TimerTask symbolChangeTask = new TimerTask() {
        @Override
        public void run() {
            time += 10;
            if (!firstSymbolDone) {
                if (randomTimeForSymbol1 > time / 1000)
                    symbolNumber1 = updateSlotMachineSymbol(firstSymbolMachine, symbolNumber1, false);
                else {
                    symbolNumber1 = updateSlotMachineSymbol(firstSymbolMachine, symbolNumber1, true);
                    firstSymbolDone = true;
                }
            }
            if (!secondSymbolDone) {
                if (randomTimeForSymbol1 * 2 > time / 1000)
                    symbolNumber2 = updateSlotMachineSymbol(secondSymbolMachine, symbolNumber2, false);
                else {
                    symbolNumber2 = updateSlotMachineSymbol(secondSymbolMachine, symbolNumber2, true);
                    secondSymbolDone = true;
                }
            }
            if (!thirdSymbolDone) {
                if (randomTimeForSymbol1 * 3 > time / 1000)
                    symbolNumber3 = updateSlotMachineSymbol(thirdSymbolMachine, symbolNumber3, false);
                else {
                    symbolNumber3 = updateSlotMachineSymbol(thirdSymbolMachine, symbolNumber3, true);
                    thirdSymbolDone = true;
                }
            }
        }
    };

    private void stopTimer() {
        firstSymbolDone = secondSymbolDone = thirdSymbolDone = false;
        time = 0;
        timer.cancel();
        timer.purge();
        timer = new Timer();
        symbolChangeTask = new TimerTask() {
            @Override
            public void run() {
                time += 10;
                if (!firstSymbolDone) {
                    if (randomTimeForSymbol1 > time / 1000)
                        symbolNumber1 = updateSlotMachineSymbol(firstSymbolMachine, symbolNumber1, false);
                    else {
                        symbolNumber1 = updateSlotMachineSymbol(firstSymbolMachine, symbolNumber1, true);
                        firstSymbolDone = true;
                    }
                }
                if (!secondSymbolDone) {
                    if (randomTimeForSymbol1 * 2 > time / 1000)
                        symbolNumber2 = updateSlotMachineSymbol(secondSymbolMachine, symbolNumber2, false);
                    else {
                        symbolNumber2 = updateSlotMachineSymbol(secondSymbolMachine, symbolNumber2, true);
                        secondSymbolDone = true;
                    }
                }
                if (!thirdSymbolDone) {
                    if (randomTimeForSymbol1 * 3 > time / 1000)
                        symbolNumber3 = updateSlotMachineSymbol(thirdSymbolMachine, symbolNumber3, false);
                    else {
                        symbolNumber3 = updateSlotMachineSymbol(thirdSymbolMachine, symbolNumber3, true);
                        thirdSymbolDone = true;
                    }
                }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.button2);
        slotMachine = findViewById(R.id.slot_machine);
        coin = findViewById(R.id.imageView8);

        firstSymbolMachine = findViewById(R.id.symbol1);
        secondSymbolMachine = findViewById(R.id.symbol2);
        thirdSymbolMachine = findViewById(R.id.symbol3);

        RotateAnimation rotate = new RotateAnimation(
                0, 3600, RotateAnimation.RELATIVE_TO_SELF,
                0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(18000);
        rotate.setFillAfter(true);
        rotate.setInterpolator(new DecelerateInterpolator());
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                slotMachine.setImageDrawable(getResources().getDrawable(R.drawable.slot_machine1_2));
                timer.schedule(symbolChangeTask, 0, 10);
                startButton.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Проверка конца анимации ячеек
                if (firstSymbolDone && secondSymbolDone && thirdSymbolDone) {
                    slotMachine.setImageDrawable(getResources().getDrawable(R.drawable.slot_machine1));
                    stopTimer();
                    startButton.setEnabled(true);

                    if (symbolNumber1 == symbolNumber2 && symbolNumber1 == symbolNumber3)
                        sendWinMessage();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        startButton.setOnClickListener(view -> {
            randomTimeForSymbol1 = (int) (Math.random() * 5) + 1;
            coin.startAnimation(rotate);
        });
    }

    public int updateSlotMachineSymbol(ImageView imageView, int symbolNumber, boolean getToDefaultTranslation) {
        if (imageView.getTranslationY() > 30) {
            imageView.setTranslationY(-480);
            symbolNumber++;

            if (symbolNumber > 3) symbolNumber = 0;

            if (symbolNumber == 0) imageView.setImageDrawable(getResources().getDrawable(R.drawable.slot_symbol1));
            else if (symbolNumber == 1) imageView.setImageDrawable(getResources().getDrawable(R.drawable.slot_symbol2));
            else if (symbolNumber == 2) imageView.setImageDrawable(getResources().getDrawable(R.drawable.slot_symbol3));
            else if (symbolNumber == 3) imageView.setImageDrawable(getResources().getDrawable(R.drawable.slot_symbol4));
        }
        else {
            if (getToDefaultTranslation) {
                while (imageView.getTranslationY() - defaultTranslationY <= -25)
                    imageView.setTranslationY(imageView.getTranslationY() + 10);

                imageView.setTranslationY(defaultTranslationY);
            }
            else imageView.setTranslationY(imageView.getTranslationY() + 10);
        }

        return symbolNumber;
    }

    public void sendWinMessage() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Вы выиграли!", Toast.LENGTH_LONG);
        toast.show();
    }
}
