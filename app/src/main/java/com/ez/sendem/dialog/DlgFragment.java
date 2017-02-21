package com.ez.sendem.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ez.sendem.R;

public class DlgFragment extends DialogFragment {
	private WrapperDialog dialog;
	private View vView;
	protected TextView vTitle;
	private ViewGroup vContent;
	
	@SuppressLint("NewApi")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		LayoutInflater inflater = getActivity().getLayoutInflater();
		vView = inflater.inflate(R.layout.dlgfragment, null);
		vContent = (ViewGroup)vView.findViewById(R.id.dlg_content);
		vTitle = (TextView)vView.findViewById(R.id.dlg_title);
		vTitle.setVisibility(View.GONE);
		
        dialog = new WrapperDialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new BitmapDrawable(vView.getContext().getResources()));
//        dialog.getWindow().setWindowAnimations(R.style.DialogTransition);
        dialog.setWrapperView(vView);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams)vView.getLayoutParams();
        lp.setMargins(10, 100, 10, 100);
        		
		return dialog;
	}
	
	public void setTitle(CharSequence arg){
		vTitle.setVisibility(View.VISIBLE);
		vTitle.setText(arg);
	}
	
	public void appendTitle(CharSequence arg){
		vTitle.setVisibility(View.VISIBLE);
		vTitle.append(arg);
	}
	
	public void setTitle(int textId){
		vTitle.setVisibility(View.VISIBLE);
		vTitle.setText(textId);
	}
	
	public void overridePadding(int left, int top, int right, int bottom){
		vContent.setPadding(left, top, right, bottom);
	}
	
	class WrapperDialog extends Dialog{
		private View mView;
		
		public WrapperDialog(Context context){
			super(context);
		}
		
		public void setWrapperView(View view){
			super.setContentView(view);
			mView = view;
		}
		
		@Override
		public void setContentView(View view) {
			if(vContent!=null){
				vContent.removeAllViews();
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				vContent.addView(view,params);
			}
		}
	}
}
