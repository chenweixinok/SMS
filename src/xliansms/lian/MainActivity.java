package xliansms.lian;

import java.util.HashMap;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button btnBindPhone;
	String APPKEY="ababf266b224";
	String APPSECRET="3332ebc050ad6f6db75ee0944f5fa9c4";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//初始化
				SMSSDK.initSDK(this, APPKEY, APPSECRET);
				btnBindPhone = (Button) this.findViewById(R.id.btn_bind_phone);
				//设置点击事件
				btnBindPhone.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//注册手机号
						RegisterPage registerPage = new RegisterPage();
						
						//注册回调事件
						registerPage.setRegisterCallback(new EventHandler(){
							@Override
							public void afterEvent(int evnet, int result, Object data) {
								//判断结果是否已经完成
								if (result == SMSSDK.RESULT_COMPLETE) {
									//获取数据data
									HashMap<String, Object> maps=(HashMap<String, Object>) data;
									//所在的国家的信息
									String country = (String) maps.get("country");
									//手机号信息
									String phone = (String) maps.get("phone");
									submitUserInfo(country, phone);
								}
							}
						});
						//显示注册界面
						registerPage.show(MainActivity.this);
							}
						});
	}
	/**
	 * 提交用户信息
	 * @param country
	 * @param phone
	 */
	public void submitUserInfo(String country,String phone){
		Random r = new Random();
		String uid = Math.abs(r.nextInt())+"";
		String nickName="ILIAN";
		SMSSDK.submitUserInfo(uid, nickName, null, country, phone);
	}

}
