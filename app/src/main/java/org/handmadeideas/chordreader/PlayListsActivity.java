package org.handmadeideas.chordreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import org.handmadeideas.chordreader.data.ColorScheme;
import org.handmadeideas.chordreader.helper.PreferenceHelper;

public class PlayListsActivity extends Activity {
    //private static final UtilLogger log = new UtilLogger(PlayListsActivity.class);

    private LinearLayout mainView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play_lists);
        mainView = (LinearLayout) findViewById(R.id.playlists_main_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.play_list_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.menu_new_play_list) {
            showNewPlaylistDialog();
            return true;
        } else if (itemId == R.id.menu_manage_playlists) {
            startDeleteSavedPlaylistsDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // came back from the settings activity; need to update the colours
        PreferenceHelper.clearCache();

        applyColorScheme();

}

    private void startSettingsActivity() {
        startActivityForResult(new Intent(this, SettingsActivity.class), 1);
    }

    private void startAboutActivity() {
        startActivity(new Intent(this, AboutActivity.class));
    }

    private void startDeleteSavedPlaylistsDialog() {
    }

    private void showNewPlaylistDialog() {
    }


    private void applyColorScheme() {

        ColorScheme colorScheme = PreferenceHelper.getColorScheme(this);
        mainView.setBackgroundColor(colorScheme.getBackgroundColor(this));
    }


}
