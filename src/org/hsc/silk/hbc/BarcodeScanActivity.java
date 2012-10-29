package org.hsc.silk.hbc;

import org.hsc.silk.App;
import org.hsc.silk.myutils.XMLParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.BarcodeTest.IntentIntegrator;
import com.example.BarcodeTest.IntentResult;

public class BarcodeScanActivity extends Activity {
	App myapp;
	ProductInfo productInfo = null;
	String barcode = "";

	LinearLayout viewProductInfo;

	TextView tvProductName;
	TextView tvHalalId;
	TextView tvHalalBegin;
	TextView tvHalalEnd;
	TextView tvOtherInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println("on create ........................");
		setContentView(R.layout.halal_barcode_scan);
		myapp = (App) getApplication();

		viewProductInfo = (LinearLayout) findViewById(R.id.viewProductInfo);
		tvProductName = (TextView) viewProductInfo
				.findViewById(R.id.tvProductName);
		tvHalalId = (TextView) viewProductInfo.findViewById(R.id.tvHalalId);
		tvHalalBegin = (TextView) viewProductInfo
				.findViewById(R.id.tvHalalBegin);
		tvHalalEnd = (TextView) viewProductInfo.findViewById(R.id.tvHalalEnd);
		tvOtherInfo = (TextView) viewProductInfo.findViewById(R.id.tvOtherInfo);

		// viewBarcodeInfo.setVisibility(View.INVISIBLE);
		viewProductInfo.setVisibility(View.INVISIBLE);
	}

	private void clearText() {

		tvProductName.setText("");
		tvHalalId.setText("");
		tvHalalBegin.setText("");
		tvHalalEnd.setText("");
		tvOtherInfo.setText("");
	}

	public void startScan() {
		IntentIntegrator.initiateScan(this);
	}

	public void onBtnTestClick(View view) {
		App app = (App) getApplication();
		// app.testParseProductInfo();
		// Log.i("Test...", "Start..");
		String output = app.getHalalInfoFromRest();
		XMLParser xmlParser = new XMLParser();
		xmlParser.parseProductInfo(output);
		// Log.i("Test...", output);
		// System.out.println("Ou");
	}

	public void onBtnStartScanClick(View view) {
		clearText();
		viewProductInfo.setVisibility(View.INVISIBLE);
		startScan();
	}

	public void onBtnInputWithKBClick(View view) {
		clearText();
		viewProductInfo.setVisibility(View.INVISIBLE);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("บาร์โค้ด");
		alert.setMessage("ใส่รหัส");

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				// Do something with value!
				barcode = value;
				processSearchBarcode();

				// tvBarcode.setText(value);
				//
				// App myapp = (App) getApplication();
				// // productInfo = myapp.searchHalalProductInfo(barcode);
				// productInfo = myapp.getProductInfo(barcode);
				// displayProductInfo();
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
						barcode = "";
						// displayProductInfo();
					}
				});

		alert.show();
	}

	private void processSearchBarcode() {
		System.out.println("search barcode : " + barcode);
		productInfo = null;
		if (search()) {
			displayInfo();
		} else {
			alertNotFound();
		}
	}

	private boolean search() {
		productInfo = myapp.getProductInfo(barcode);
		return (productInfo != null);
	}

	public void displayInfo() {
		if (productInfo != null) {
			viewProductInfo.setVisibility(View.VISIBLE);
			tvProductName.setText(productInfo.getProduct());
			tvHalalId.setText(productInfo.getHalalId());
			tvHalalBegin.setText(productInfo.getHalalBeginDate());
			tvHalalEnd.setText(productInfo.getHalalExpDate());
			tvOtherInfo.setText(productInfo.getOtherInfo());
		}
	}

	private void alertNotFound() {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		// Setting Dialog Title
		alertDialog.setTitle("ค้นหาข้อมูลฮาลาล");
		// Setting Dialog Message
		alertDialog.setMessage("ไม่พบข้อมูลฮาลาลหมายเลข " + barcode);
		// Setting Icon to Dialog
		// alertDialog.setIcon(R.drawable.tick);
		// Setting OK Button
		alertDialog.setPositiveButton("OK", null);
		// Showing Alert Message
		alertDialog.show();
	}

	public void onBtnBackClick(View view) {
		finish();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case IntentIntegrator.REQUEST_CODE: {
			if (resultCode != RESULT_CANCELED) {
				IntentResult scanResult = IntentIntegrator.parseActivityResult(
						requestCode, resultCode, data);
				if (scanResult != null) {
					barcode = scanResult.getContents();

					productInfo = myapp.getProductInfo(barcode);
					displayInfo();
				}
			}
		}
		}
	}

	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("on resume................................");
		displayInfo();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if(productInfo != null){
			outState.putString("barcode", barcode);
			System.out.println("save state barcode " + barcode);
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		barcode = savedInstanceState.getString("barcode");
		if(barcode != null && !barcode.equals("")){
			productInfo = myapp.getProductInfo(barcode);
		}
		System.out.println("restore state barcode " + barcode);
	}
}
