package com.example.hwandroid3;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int WIN_COUNT = 5;
    private final String CROSS_TAG = "Cross";
    private final String CIRCLE_TAG = "Circle";
    private LinearLayout mainContainer;
    private List<List<FloatingActionButton>> buttons = new ArrayList<>();
    private Boolean turn = true;
    // true - cross
    // false - circle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mainContainer = findViewById(R.id.mainContainer);
        TextView messageTextView = findViewById(R.id.messageTextView);
        createButtonTable(10, 10);

        findViewById(R.id.restartButton).setOnClickListener(v ->
        {
            messageTextView.setText("Game is on");
            turn = true;
            createButtonTable(10, 10);
        });
    }

    private void createButtonTable(int rows, int columns)
    {
        LinearLayout generalLayout = findViewById(R.id.mainContainer);
        generalLayout.removeAllViews();
        buttons.clear();

        for(int i = 0; i < rows; i++)
        {
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

            List<FloatingActionButton> currentRowOfButtons = new ArrayList<>();
            for(int j = 0; j < columns; j++)
            {
                FloatingActionButton button = new FloatingActionButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 10, 10, 10);
                button.setLayoutParams(params);

                button.setOnClickListener(v -> {
                    if (turn)
                    {
                        button.setImageResource(R.drawable.baseline_clear_24);
                        button.setTag(CROSS_TAG);
                    }
                    else
                    {
                        button.setImageResource(R.drawable.outline_adjust_24);
                        button.setTag(CIRCLE_TAG);
                    }
                    button.setClickable(false);
                    if(checkWinner(turn))
                    {
                        TextView messageTextView = findViewById(R.id.messageTextView);
                        messageTextView.setText(turn ? "Cross Wins" : "Circle Wins");
                        makeAllInactive();
                    }
                    turn = !turn;
                });

                rowLayout.addView(button);
                currentRowOfButtons.add(button);
            }
            buttons.add(currentRowOfButtons);
            mainContainer.addView(rowLayout);
        }
    }

    private boolean checkWinner(Boolean turn)
    {
        String tag = turn ? CROSS_TAG : CIRCLE_TAG;
        boolean horizontal = checkHorizontal(tag);
        boolean vertical = checkVertical(tag);
        boolean diagonal = checkDiagonal(tag);
        return horizontal || vertical || diagonal;
    }

    private boolean checkHorizontal(String tag)
    {
        boolean flag = false;
        for(List<FloatingActionButton> row : buttons)
        {
            for(int i = 0; i <= row.size() - 5; i++)
            {
                var button = row.get(i);
                if(button.getTag() == tag)
                {
                    flag = true;
                    for(int j = 1; j < WIN_COUNT; j++)
                    {
                        var next = row.get(i + j);
                        if(next.getTag() != tag)
                        {
                            flag = false;
                            break;
                        }
                    }
                    if(flag) return flag;
                }
            }
        }
        return flag;
    }

    private boolean checkVertical(String tag)
    {
        boolean flag = false;
        for(int j = 0; j < buttons.get(0).size(); j++)
        {
            for(int i = 0; i < buttons.size() - 5; i++)
            {
                var button = buttons.get(i).get(j);
                if(button.getTag() == tag)
                {
                    flag = true;
                    for(int k = 1; k < WIN_COUNT; k++)
                    {
                        var next = buttons.get(i + k).get(j);
                        if(next.getTag() != tag)
                        {
                            flag = false;
                            break;
                        }
                    }
                    if(flag) return flag;
                }
            }
        }
        return flag;
    }

    private boolean checkDiagonal(String tag)
    {
        boolean flag = false;
        for(int r = 0; r < buttons.size() - 5; r++)
        {
            for(int i = 0; i <= buttons.get(r).size() - 5; i++)
            {
                var button = buttons.get(r).get(i);
                if(button.getTag() == tag)
                {
                    flag = true;
                    for(int j = 1; j < WIN_COUNT; j++)
                    {
                        var next = buttons.get(r + j).get(i + j);
                        if(next.getTag() != tag)
                        {
                            flag = false;
                            break;
                        }
                    }
                    if(flag) return flag;
                }
            }
        }

        for(int r = 0; r < buttons.size() - 5; r++)
        {
            for(int i = buttons.get(r).size() - 1; i >= 4; i--)
            {
                var button = buttons.get(r).get(i);
                if(button.getTag() == tag)
                {
                    flag = true;
                    for(int j = 1; j < WIN_COUNT; j++)
                    {
                        var next = buttons.get(r + j).get(i - j);
                        if(next.getTag() != tag)
                        {
                            flag = false;
                            break;
                        }
                    }
                    if(flag) return flag;
                }
            }
        }

        return flag;
    }


    private void makeAllInactive()
    {
        for(var row : buttons)
        {
            for(var button : row)
            {
                button.setClickable(false);
            }
        }
    }










}