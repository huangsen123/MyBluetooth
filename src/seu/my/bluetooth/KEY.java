package seu.my.bluetooth;

import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class KEY extends Activity {
    /** Called when the activity is first created. */
	
	Button btnKai , btnGuan;
	OutputStream tmpOut = null;
		
	
	
	public static final String SETTING_INFOS = "SETTING_Infos";
	public static final String PASSWORD = "PASSWORD";
	
	private EditText filed_pass;
	
	
	
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.key);
               
        filed_pass = (EditText) findViewById(R.id.password);
        SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
		String password = settings.getString(PASSWORD, "");
		filed_pass.setText(password);
    		// Button ÉèÖÃ
            btnKai = (Button) this.findViewById(R.id.kaikey);
            btnKai.setOnClickListener(new ClickEventKey());
            btnGuan = (Button) this.findViewById(R.id.guankey);
            btnGuan.setOnClickListener(new ClickEventKey());
    }
    
    
    class ClickEventKey implements View.OnClickListener {
    	@Override
    	public void onClick(View v) {
    		if (v == btnKai)
    		{
            try {
            	   CharSequence  charsequencepass= filed_pass.getText();
		           String strpass=charsequencepass.toString();
				   byte[] bytekai=strpass.getBytes("US-ASCII");
                    tmpOut = BluetoothMain.btSocket.getOutputStream();
                    tmpOut.write(bytekai);
                } catch (IOException e) {
                    Log.e("BluetoothReadService", "temp sockets not created", e);
                }

    		} 
    		else if (v == btnGuan) {
    			try {
    				CharSequence  charsequencepass= filed_pass.getText();
 		            String strpass=charsequencepass.toString();
 		            String strtemp="";
 		            for(int i=(strpass.length()-1);i>=0;i--){
 		            	strtemp=strtemp+strpass.charAt(i);
 		            }
 				    byte[] byteguan=strtemp.getBytes("US-ASCII");
                    tmpOut = BluetoothMain.btSocket.getOutputStream();
                    tmpOut.write(byteguan);
                } catch (IOException e) {
                    Log.e("BluetoothReadService", "temp sockets not created", e);
                }
    		} 
    		}
    	}  
    @Override
    protected void onStop(){
        super.onStop();
		SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
		settings.edit().putString(PASSWORD, filed_pass.getText().toString()).commit();
		try {
			if (BluetoothMain.btSocket != null)
				BluetoothMain.btSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}


