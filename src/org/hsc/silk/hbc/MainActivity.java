package org.hsc.silk.hbc;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;


public class MainActivity extends Activity {

	AssetManager assetManager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 
		
//		if(!isNetworkAvailable()){
//			AlertDialog.Builder alert = new AlertDialog.Builder(this);
//			alert.setTitle("Internet connection");
//			alert.setMessage("Internet not connected");	
//			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int whichButton) {
//					start();
//				  }
//				});
//			alert.show();
//		}else{
			start();
//		}

	}
	private void start(){
		startActivity(new Intent(this, BarcodeScanActivity.class));
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		finish();
	}

	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
	
	
}
