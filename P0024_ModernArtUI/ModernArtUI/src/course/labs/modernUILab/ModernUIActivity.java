package course.labs.modernUILab;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ModernUIActivity extends Activity {
	static private final String URL = "http://www.moma.org";
	static private final String TAG = "Lab-ModernUI";

	private DialogFragment mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modern_ui);
		SeekBar seekView = (SeekBar) findViewById(R.id.slider);

		// Get the boxes
		final LinearLayout purplebox = (LinearLayout) findViewById(R.id.purplebox);
		final LinearLayout yellowbox = (LinearLayout) findViewById(R.id.yellowbox);
		final LinearLayout bluebox = (LinearLayout) findViewById(R.id.bluebox);

		seekView.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				Log.i(TAG, "Changed progress " + progress);
				purplebox.setBackgroundColor(getResources().getColor(
						R.color.purpleboxColor)
						+ progress * 2);
				yellowbox.setBackgroundColor(getResources().getColor(
						R.color.yellowboxColor)
						+ progress * 2);
				bluebox.setBackgroundColor(getResources().getColor(
						R.color.blueboxColor)
						+ progress * 2);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

		});
	}

	/**
	 * Create Options Menu
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.top_menu, menu);
		return true;
	}

	/**
	 * Process clicks on Options Menu items
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.information:
			showDialogFragment();
			return true;
		default:
			return false;
		}
	}

	private void showDialogFragment() {
		mDialog = InformationDialogFragment.newInstance();
		mDialog.show(getFragmentManager(), "Alert");
	}

	/**
	 * Start a Browser Activity to view a web page or its URL.
	 */
	public void startImplicitActivation() {

		Log.i(TAG, "Entered startImplicitActivation()");

		// Create a base intent for viewing a URL
		Uri webpage = Uri.parse(URL);
		Intent baseIntent = new Intent(Intent.ACTION_VIEW, webpage);
		startActivity(baseIntent);
	}
}
