package com.example.gcm_client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainActivity extends FragmentActivity {
	
	String serverNames[] = { "Development", "Staging", "Official" };
	int ChosenServerName = 0;
	
	LinearLayout textViewServer;
	TextView textViewServer2;
	
	LinearLayout textViewUdid;
	TextView textViewUdid2;
	EditText editTextUdid;
	
	LinearLayout textViewMccmnc;
	TextView textViewMccmnc2;
	
	LinearLayout textViewModel;
	TextView textViewModel2;
	CustomizedDialog cd;
	
	LinearLayout textViewOs;
	TextView textViewOs2;
	Activity mActivity;
	
	LinearLayout textViewList;
	TextView textViewList2;
	
	LinearLayout spinner;
	TextView spinnerText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textViewServer2 = (TextView)findViewById(R.id.server_name_view);
        textViewServer = (LinearLayout)findViewById(R.id.server_name);
        textViewServer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
				adb.setTitle("Choose Server");
				adb.setSingleChoiceItems(serverNames, 0, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						ChosenServerName = whichButton;
					}
				})
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						textViewServer2.setText("");
						textViewServer2.append(serverNames[ChosenServerName]);
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
					}
				});
				adb.show();
			}
		});
        
        textViewUdid2 = (TextView)findViewById(R.id.udid_view);
        textViewUdid = (LinearLayout)findViewById(R.id.udid);
        textViewUdid.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// AndroidManifest.xml에 Activity추가하기 
				Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
			}
		});
        
        textViewMccmnc = (LinearLayout)findViewById(R.id.mccmnc);
        textViewMccmnc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(0);
			}
		});
        
        textViewModel = (LinearLayout)findViewById(R.id.model_name);
        textViewModel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cd = new CustomizedDialog();
				cd.show(getSupportFragmentManager(), "MYTAG");
				
			}
		});
		
        mActivity = this;
        textViewOs = (LinearLayout)findViewById(R.id.os_version);
        textViewOs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
		        new RestFulTask().execute(null,null,null);
			}
		});
        
        textViewList = (LinearLayout)findViewById(R.id.listView);
        textViewList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(intent);
			}
		});
        
        spinner = (LinearLayout)findViewById(R.id.spinner);
        spinner.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, SpinnerActivity.class);
                startActivity(intent);
			}
		});
    }
    
    @Override
    // showDialog()
	protected Dialog onCreateDialog(int id) {
    	Dialog dialog;
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
               .setCancelable(false)
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       MainActivity.this.finish();
                   }
                })
               .setNegativeButton("No", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       dialog.cancel();
                    }
                 });
        dialog = builder.create();
        return dialog;
	}
    
    public static class CustomizedDialog extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
			LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
			mBuilder.setView(mLayoutInflater.inflate(R.layout.popup_model, null));
			mBuilder.setTitle("Model");
			mBuilder.setMessage("Model");
			mBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    
                }
             })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    
                 }
              });
			return mBuilder.create();
		}
		
		@Override
		public void onStop() {
			super.onStop();
		}
    }

    public class RestFulTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			
			try {
//				URL url = new URL("https://dev.lgcpm.com:45010/oapi/gcm/gcmServerTest");
				URL url = new URL("http://typeofb.appspot.com/oapi/gcm/gcmServerTest");
				String body = "{\"regId\":\"APA91bHun4MxP5egoKMwt2KZFBaFUH-1RYqx...\",\"data\":{\"Nick\":\"Mario\",\"Text\":\"great match!\",\"Room\":\"PortugalVSDenmark\"}}";
	
		        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//		        conn.setDoInput(true);
		        conn.setDoOutput(true);
		        conn.setUseCaches(false);
		        conn.setRequestMethod("POST");
		        conn.setRequestProperty("Content-Type", "application/json");
	
		        OutputStream out = conn.getOutputStream();
		        out.write(body.getBytes("UTF-8"));
		        out.close();
	
		        int status = conn.getResponseCode();
		        InputStream input;
		        if (status == HttpURLConnection.HTTP_OK) {
		            input = conn.getInputStream();
		            
					final StringBuffer sb = new StringBuffer();
					byte[] b = new byte[1024];
					for (int n; (n = input.read(b)) != -1;) {
						sb.append(new String(b, 0, n));
					}
					
					mActivity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(mActivity, sb, Toast.LENGTH_SHORT).show();
						}
					});

		        } else {
		            input = conn.getErrorStream();
		        }
			} catch (Exception e) {
				Log.e(null, e.toString());
			}
			return null;
		}
    }
    
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }*/
}
