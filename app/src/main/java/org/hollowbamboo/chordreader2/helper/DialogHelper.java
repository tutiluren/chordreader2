package org.hollowbamboo.chordreader2.helper;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import org.hollowbamboo.chordreader2.R;

public class DialogHelper {
	
	public static final int CAPO_MIN = 0;
	public static final int CAPO_MAX = 6;
	public static final int TRANSPOSE_MIN = -6;
	public static final int TRANSPOSE_MAX = 6;

	public static View createTransposeDialogView(Context context, int capoFret, int transposeHalfSteps) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.transpose_dialog, null);
		
		View transposeView = view.findViewById(R.id.transpose_include);
		View capoView = view.findViewById(R.id.capo_include);
		
		setUpEnhancedSeekBar(transposeView, TRANSPOSE_MIN, TRANSPOSE_MAX, transposeHalfSteps);
		setUpEnhancedSeekBar(capoView, CAPO_MIN, CAPO_MAX, capoFret);
		
		return view;
	}
	
	public static int getSeekBarValue(View enhancedSeekBarView) {
		
		SeekBar seekBar = (SeekBar) enhancedSeekBarView.findViewById(R.id.seek_bar_main);
		
		return seekBar.getProgress();
	}

	private static void setUpEnhancedSeekBar(View view, final int min, int max, int defaultValue) {
		SeekBar seekBar = (SeekBar) view.findViewById(R.id.seek_bar_main);
		TextView minTextView = (TextView) view.findViewById(R.id.seek_bar_min_text_view);
		TextView maxTextView = (TextView) view.findViewById(R.id.seek_bar_max_text_view);

		setupOnTouchListenerForTextViewToChangeSeekBarProgress(minTextView, seekBar, -1, min, max);
		setupOnTouchListenerForTextViewToChangeSeekBarProgress(maxTextView, seekBar, +1, min, max);

		final TextView progressTextView = (TextView) view.findViewById(R.id.seek_bar_main_text_view);
		
		minTextView.setText(Integer.toString(min));
		// check if we need to distinguish between negative and positive
		maxTextView.setText((min < 0 && max > 0 ? "+" : "") + max);
		progressTextView.setText((min < 0 && defaultValue > 0 ? "+" : "") + defaultValue);
		seekBar.setMax(max - min);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// do nothing
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// do nothing
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				String progressAsString = Integer.toString(progress + min);
				if (min < 0 && (progress + min) > 0) { 
					// need to distinguish positive from negative
					progressAsString = "+" + progressAsString;
				}
				progressTextView.setText(progressAsString);
				
			}
		});
		seekBar.setProgress(defaultValue - min); // initialize to default value
		
	}

	private static void setupOnTouchListenerForTextViewToChangeSeekBarProgress(
			final TextView textView,
			SeekBar seekBar,
			final int change,
			final int minimumValue,
			final int maximumValue
	) {
		textView.setOnTouchListener((view, motionEvent) -> {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				updateSeekBarProgress(seekBar, change, minimumValue, maximumValue);
			}
			return false;
		});
	}

	private static void updateSeekBarProgress(
			SeekBar seekBar,
			final int change,
			final int minimumValue,
			final int maximumValue
	) {
		int currentValue = seekBar.getProgress() + minimumValue;

		// Make sure the new value does not exceed the bounds
		int newValue = min(maximumValue, max(minimumValue, currentValue + change));

		seekBar.setProgress(newValue - minimumValue);
	}
	
}
