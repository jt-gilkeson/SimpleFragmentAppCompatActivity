/*
 * Copyright (C) 2015 J.T. Gilkeson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Basic Activity that handles displaying a custom fragment
 */
public class SimpleFragmentActivity extends AppCompatActivity
{
	private static final String TITLE         = "ActivityTitle";
	private static final String THEME         = "ActivityTheme";
	private static final String FRAGMENT_NAME = "FragmentName";
	private static final String FRAGMENT_TAG  = "FragmentTag";

	/**
	 * Returns an intent for a SimpleFragmentActivity with the specified fragment.
	 *
	 * @param context The calling context being used to instantiate the activity.
	 * @param fragmentClass The fragment class that is to be launched inside this activity.
	 */
	public static Intent getActivityIntent(Context context, Class<?> fragmentClass)
	{
		return getActivityIntent(context, null, fragmentClass, null, 0, 0);
	}

	/**
	 * Returns an intent for the specified activity with the specified fragment.
	 *
	 * @param context The calling context being used to instantiate the activity.
	 * @param activityClass Optional activity class (use for inherited classes), defaults to SimpleFragmentActivity.
	 * @param fragmentClass The fragment class that is to be launched inside this activity.
	 */
	public static Intent getActivityIntent(Context context, Class<?> activityClass, Class<?> fragmentClass)
	{
		return getActivityIntent(context, activityClass, fragmentClass, null, 0, 0);
	}

	/**
	 * Returns an intent for a SimpleFragmentActivity with the specified title and fragment.
	 *
	 * @param context The calling context being used to instantiate the activity.
	 * @param fragmentClass The fragment class that is to be launched inside this activity.
	 * @param titleId Optional string resource for title to display for activity.
	 */
	public static Intent getActivityIntent(Context context, Class<?> fragmentClass, int titleId)
	{
		return getActivityIntent(context, null, fragmentClass, null, titleId, 0);
	}

	/**
	 * Returns an intent for a SimpleFragmentActivity with the specified title, theme and fragment.
	 *
	 * @param context The calling context being used to instantiate the activity.
	 * @param fragmentClass The fragment class that is to be launched inside this activity.
	 * @param titleId Optional string resource for title to display for activity.
	 * @param themeId Optional style resource describing the theme of the activity.
	 */
	public static Intent getActivityIntent(Context context, Class<?> fragmentClass, int titleId, int themeId)
	{
		return getActivityIntent(context, null, fragmentClass, null, titleId, themeId);
	}

	/**
	 * Returns an intent for a SimpleFragmentActivity with the specified fragment and fragment tag.
	 *
	 * @param context The calling context being used to instantiate the activity.
	 * @param fragmentClass The fragment class that is to be launched inside this activity.
	 * @param tag  Optional tag name for the fragment, defaults to fragment class name.
	 */
	public static Intent getActivityIntent(Context context, Class<?> fragmentClass, String tag)
	{
		return getActivityIntent(context, null, fragmentClass, tag, 0, 0);
	}

	/**
	 * Returns an intent for the specified activity with the specified fragment and fragment tag.
	 *
	 * @param context The calling context being used to instantiate the activity.
	 * @param activityClass Optional activity class (use for inherited classes), defaults to SimpleFragmentActivity.
	 * @param fragmentClass The fragment class that is to be launched inside this activity.
	 * @param tag  Optional tag name for the fragment, defaults to fragment class name.
	 */
	public static Intent getActivityIntent(Context context, Class<?> activityClass, Class<?> fragmentClass, String tag)
	{
		return getActivityIntent(context, activityClass, fragmentClass, tag, 0, 0);
	}

	/**
	 * Returns an intent for the specified activity with with the specified title, fragment, and fragment tag.
	 *
	 * @param context The calling context being used to instantiate the activity.
	 * @param activityClass Optional activity class (use for inherited classes), defaults to SimpleFragmentActivity.
	 * @param fragmentClass The fragment class that is to be launched inside this activity.
	 * @param tag  Optional tag name for the fragment, defaults to fragment class name.
	 * @param titleId Optional string resource for title to display for activity.
	 */
	public static Intent getActivityIntent(Context context, Class<?> activityClass, Class<?> fragmentClass, String tag, int titleId)
	{
		return getActivityIntent(context, activityClass, fragmentClass, tag, titleId, 0);
	}

	/**
	 * Returns an intent for the specified activity with with the specified title, theme, fragment, and fragment tag.
	 *
	 * @param context The calling context being used to instantiate the activity.
	 * @param activityClass Optional activity class (use for inherited classes), defaults to SimpleFragmentActivity.
	 * @param fragmentClass The fragment class that is to be launched inside this activity.
	 * @param tag  Optional tag name for the fragment, defaults to fragment class name.
	 * @param titleId Optional string resource for title to display for activity.
	 * @param themeId Optional style resource describing the theme of the activity.
	 */
	public static Intent getActivityIntent(Context context, Class<?> activityClass, Class<?> fragmentClass, String tag, int titleId, int themeId)
	{
		Intent intent = new Intent(context, activityClass == null ? SimpleFragmentActivity.class : activityClass);
		intent.putExtra(TITLE, titleId);
		intent.putExtra(THEME, themeId);
		intent.putExtra(FRAGMENT_NAME, fragmentClass.getName());
		intent.putExtra(FRAGMENT_TAG, tag == null ? fragmentClass.getName() : tag);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Bundle extras = getIntent().getExtras();

		if (extras == null || !extras.containsKey(FRAGMENT_NAME) || !extras.containsKey(FRAGMENT_TAG))
		{
			throw new IllegalArgumentException("Missing Fragment Information");
		}

		// set theme if specified
		int theme = extras.getInt(THEME);
		if (theme > 0)
		{
			setTheme(theme);
		}

		super.onCreate(savedInstanceState);

		// set title if specified
		int title = extras.getInt(TITLE);
		if (title > 0)
		{
			setTitle(title);
		}

		if (savedInstanceState == null)
		{
			Fragment frag = Fragment.instantiate(this, extras.getString(FRAGMENT_NAME), extras);

			getSupportFragmentManager()
					.beginTransaction()
					.add(android.R.id.content, frag, extras.getString(FRAGMENT_TAG))
					.commit();
		}
	}
}
