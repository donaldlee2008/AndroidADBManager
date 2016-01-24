package com.matoski.adbm.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.matoski.adbm.R;
import com.matoski.adbm.util.FileUtil;

/**
 * Base {@link Activity} used to display simple layouts with version, and text
 * 
 * @author Ilija Matoski (ilijamt@gmail.com)
 */
public abstract class BaseHelpActivity extends Activity {

	/**
	 * The tag used when logging with {@link Log}
	 */
	private static final String LOG_TAG = BaseHelpActivity.class.getName();

	/**
	 * This method needs to be overridden
	 * 
	 * <pre>
	 * int getResourceId() {
	 * 	return R.raw.file;
	 * }
	 * </pre>
	 * 
	 * @return the resource id
	 */
	protected abstract int getResourceId();
	
	/**
	 * The methods returns the activity title id
	 * 
	 *  <pre>
	 *  int getTitleId() {
	 *    return R.string.title;
	 *  }
	 *  </pre>
	 * @return
	 */
	protected abstract int getTitleId();

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_help);
		setTitle(getTitleId());

		TextView versionView = (TextView) findViewById(R.id.help_about_version);
		versionView.setText(getVersion());

		String html = FileUtil.readRawFile(getApplicationContext(),
				getResourceId());

		if (html == null) {
			html = getResources().getString(R.string.no_data);
		}

		final WebView aboutText = (WebView) findViewById(R.id.help_about_text);
		final WebSettings settings = aboutText.getSettings();

		settings.setDefaultTextEncodingName("utf-8");

		aboutText.setBackgroundColor(Color.argb(1, 0, 0, 0));
		aboutText.loadData(html, "text/html; charset=utf-8", "utf-8");

	}

	/**
	 * Get the current package version.
	 * 
	 * @return The current version.
	 */
	protected String getVersion() {
		String result = "";
		try {
			PackageManager manager = getPackageManager();
			PackageInfo info = manager.getPackageInfo(getPackageName(), 0);

			result = String.format("%s (%s)", info.versionName,
					info.versionCode);
		} catch (NameNotFoundException e) {
			Log.w(LOG_TAG,
					"Unable to get application version: " + e.getMessage());
			result = "Unable to get application version.";
		}

		return result;
	}

}
