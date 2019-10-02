package com.example.gcm_client;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import com.example.gcm_client.lib.MsgHandler;
import com.example.gcm_client.util.ProgressDialogHelper;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.Thread.sleep;

@SuppressWarnings("deprecation")
public class MainActivity extends MsgHandler {

    private Activity context;

	private String[] serverNames = { "Development", "Staging", "Official" };
	private int ChosenServerName = 0;

	private LinearLayout textViewServer;
	private TextView textViewServer2;

	private LinearLayout textViewUdid;
	private TextView textViewUdid2;

	private LinearLayout textViewMccmnc;

	private LinearLayout textViewModel;
	private TextView textViewModel1;

	private LinearLayout textViewOs;
	private Activity mActivity;

	private LinearLayout textViewList;

	private LinearLayout spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		this.context = this;

        textViewServer2 = findViewById(R.id.server_name_view);
        textViewServer = findViewById(R.id.server_name);
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

        textViewUdid2 = findViewById(R.id.udid_view);
        textViewUdid = findViewById(R.id.udid);
        textViewUdid.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// AndroidManifest.xml에 Activity추가하기
				Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
			}
		});

        textViewMccmnc = findViewById(R.id.mccmnc);
        textViewMccmnc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(0);
			}
		});

		textViewModel1 = findViewById(R.id.model_view);
		textViewModel = findViewById(R.id.model_name);
		textViewModel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomizedDialog dialog = CustomizedDialog.newInstance(new CustomizedDialog.NameInputListener() {
					@Override
					public void onNameInputComplete(String name) {
						if (name != null) {
							textViewModel1.setText(name);
						}
					}
				});
				dialog.show(getSupportFragmentManager(), "TAG");
			}
		});

        textViewOs = findViewById(R.id.os_version);
        textViewOs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                ProgressDialogHelper.showProgressHelper(context, new Runnable() {
                    @Override
                    public void run() {
						new RestFulTask().execute();
						sendActivityUpdate(0, 0);
					}
                });
			}
		});

        textViewList = findViewById(R.id.listView);
        textViewList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(intent);
			}
		});

        spinner = findViewById(R.id.spinner);
        spinner.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, SpinnerActivity.class);
                startActivity(intent);
			}
		});

		spinner = findViewById(R.id.delivery);
		spinner.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MyActivity.class);
				startActivity(intent);
			}
		});
    }

    // Edit MCC/MNC
	@Override
	protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
		return builder.create();
	}

	// Edit Model
    public static class CustomizedDialog extends DialogFragment {

		private TextView textViewModel2;
		private NameInputListener listener;

		public static CustomizedDialog newInstance(NameInputListener listener) {
			CustomizedDialog fragment = new CustomizedDialog();
			fragment.listener = listener;
			return fragment;
		}

		public interface NameInputListener {
			void onNameInputComplete(String name);
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
			LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
			View view = mLayoutInflater.inflate(R.layout.popup_model, null);
			textViewModel2 = view.findViewById(R.id.textModel);
			mBuilder.setView(view);
			mBuilder.setTitle("Model");
			mBuilder.setMessage("Model");
			mBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
					listener.onNameInputComplete(textViewModel2.getText().toString());
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
					Log.i("Model", String.valueOf(id));
                }
            });
			return mBuilder.create();
		}

		@Override
		public void onStop() {
			super.onStop();
		}
    }

    // Edit OS
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_settings:
				break;
			default:
				return false;
		}
		return true;
	}
}
