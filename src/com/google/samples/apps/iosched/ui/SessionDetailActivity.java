package com.google.samples.apps.iosched.ui;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.samples.apps.iosched.R;
import com.google.samples.apps.iosched.util.BeamUtils;
import com.google.samples.apps.iosched.util.UIUtils;

public class SessionDetailActivity extends SimpleSinglePaneActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        UIUtils.tryTranslateHttpIntent(this);

        BeamUtils.tryUpdateIntentFromBeam(this);

        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        if (shouldBeFloatingWindow()) {

            setupFloatingWindow();

        }
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {

            Uri sessionUri = getIntent().getData();

            BeamUtils.setBeamSessionUri(this, sessionUri);
        }

        setTitle("");
    }

    @Override
    protected Fragment onCreatePane() {

        return new SessionDetailFragment();
    }

    @Override
    public Intent getParentActivityIntent() {

        // TODO: make this Activity navigate up to the right screen depending on how it was launched
        return new Intent(this, MyScheduleActivity.class);

    }

    private void setupFloatingWindow() {

        // configure this Activity as a floating window, dimming the background
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = getResources().getDimensionPixelSize(R.dimen.session_details_floating_width);
        params.height = getResources().getDimensionPixelSize(R.dimen.session_details_floating_height);
        params.alpha = 1;
        params.dimAmount = 0.7f;
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        getWindow().setAttributes(params);

    }

    private boolean shouldBeFloatingWindow() {
        Resources.Theme theme = getTheme();
        TypedValue floatingWindowFlag = new TypedValue();
        if (theme == null || !theme.resolveAttribute(R.attr.isFloatingWindow, floatingWindowFlag, true)) {
            // isFloatingWindow flag is not defined in theme
            return false;
        }
        return (floatingWindowFlag.data != 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
