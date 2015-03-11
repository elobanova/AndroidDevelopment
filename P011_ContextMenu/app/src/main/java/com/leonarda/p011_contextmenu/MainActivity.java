package com.leonarda.p011_contextmenu;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private static final int COLOR_PURPLE = 0;
    private static final int COLOR_AQUA = 1;
    private static final int COLOR_LEMON = 2;
    private static final int FONT_SMALL = 3;
    private static final int FONT_LARGE = 4;

    private TextView changeColorTextView;
    private TextView changeFontTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        changeColorTextView = (TextView)findViewById(R.id.change_color_tv);
        changeFontTextView = (TextView)findViewById(R.id.change_font_tv);

        if (changeColorTextView != null) {
            registerForContextMenu(changeColorTextView);
        }

        if (changeFontTextView != null) {
            registerForContextMenu(changeFontTextView);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //is called every time a context menu is displayed
        super.onCreateContextMenu(menu, v, menuInfo);
        if (changeColorTextView != null && v.getId() == changeColorTextView.getId()) {
            menu.add(0, COLOR_PURPLE, 0, R.string.purple_color);
            menu.add(0, COLOR_AQUA, 0, R.string.aqua_color);
            menu.add(0, COLOR_LEMON, 0, R.string.lemon_color);
        } else if (changeFontTextView != null && v.getId() == changeFontTextView.getId()) {
            menu.add(0, FONT_SMALL, 0, R.string.small_font);
            menu.add(0, FONT_LARGE, 0, R.string.large_font);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case COLOR_PURPLE: {
                changeColorTextView.setTextColor(Color.rgb(204, 102, 255));
                break;
            }
            case COLOR_AQUA: {
                changeColorTextView.setTextColor(Color.rgb(0, 204, 255));
                break;
            }
            case COLOR_LEMON: {
                changeColorTextView.setTextColor(Color.YELLOW);
                break;
            }
            case FONT_LARGE: {
                changeFontTextView.setTextSize(40);
                break;
            }
            case FONT_SMALL: {
                changeFontTextView.setTextSize(16);
                break;
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
