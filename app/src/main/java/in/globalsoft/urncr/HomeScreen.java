package in.globalsoft.urncr;


import in.globalsoft.fragment.HomeFragment;
import in.globalsoft.urncr.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.special.ResideMenu.ResideMenu;


public class HomeScreen extends FragmentActivity
{

    private ResideMenu resideMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_with_menu);

        init();

        if( savedInstanceState == null )
             addFragment();

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });



    }

    private void addFragment() {
        resideMenu.clearIgnoredViewList();
        HomeFragment fragment=new HomeFragment();
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fm.beginTransaction();
        fragmentTransaction.add(R.id.main_fragment,fragment,"HomeFragment");
         fragmentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void init() {

        resideMenu = new ResideMenu(HomeScreen.this, R.layout.menu_left,-1);
        resideMenu.setUse3D(true);
        resideMenu.setBackground(R.color.white);
        resideMenu.attachToActivity(this);
        resideMenu.setScaleValue(0.6f);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        resideMenu.getLeftMenuView().findViewById(R.id.btnProviderRegistration).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeScreen.this, AddDoctor.class);
                startActivity(i);
            }
        });

        resideMenu.getLeftMenuView().findViewById(R.id.btnPatientRegistration).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeScreen.this, RegisterScreen1.class);
                startActivity(i);
            }
        });

        resideMenu.getLeftMenuView().findViewById(R.id.btnLogin).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeScreen.this, LoginScreen.class);
               startActivity(i);
            }
        });

        resideMenu.getLeftMenuView().findViewById(R.id.btnFacilities).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeScreen.this, FacilityList.class);
                startActivity(i);
            }
        });

        ((TextView) resideMenu.getLeftMenuView().findViewById(R.id.advertisement)).setText("Consult a Doctor online - Online diagnosis and Treatment \n" +
                "\n" +
                "1-833-987-4368 \n" +
                "\n" +
                "1-833-9-URGENT");
       // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);


    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }







}
