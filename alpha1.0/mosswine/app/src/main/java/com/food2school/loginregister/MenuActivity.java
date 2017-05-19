package com.food2school.loginregister;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
public class MenuActivity extends AppCompatActivity {



    ImageView mBg;
    TextView mTitle,mSubTitle;

    LinearLayout mLists;
    FrameLayout.LayoutParams mListsParams;
    TextView mFoodTotal;
    TextView mDeliveryFee;
    ImageView mCheckoutButton;
    ListView mLeftListView;
    LeftAdapter mLeftAdapter;
    StickyListHeadersListView mRightListView;
    RightAdapter mRightAdapter;
    RelativeLayout mRelativeLayout;
    List<MenuCategory> mModelList;
    List<MenuCategory.Food> mSubModelList;
    Order mOrder;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        /*
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.argb(100,0,0,0));
        }
        */
        int restaurantID = getIntent().getExtras().getInt("restaurantID");
        final String restaurantName=getIntent().getExtras().getString("restaurantName");
        user=getIntent().getParcelableExtra("user");
        System.out.println(restaurantID);
        mOrder=new Order(restaurantName,restaurantID);
        Log.d("afterConstruct",String.valueOf(mOrder.getRestaurantID()));

        initAll(MenuActivity.this,Integer.toString(restaurantID),restaurantName);
    }

    private void initAll(Context context, String restaurantID,final String restaurantName) {

        //MenuCategory.initData(context,restaurantID);
        mModelList = new ArrayList<>();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Map<String, List<MenuCategory.Food>> map=new HashMap();
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        JSONArray category = jsonResponse.getJSONArray("category");
                        JSONArray foodName = jsonResponse.getJSONArray("foodName");
                        JSONArray foodPrice = jsonResponse.getJSONArray("foodPrice");
                        for (int i=0;i<category.length();i++) {
                            String cat=category.get(i).toString();
                            if (!map.containsKey(cat)) map.put(cat,new ArrayList<MenuCategory.Food>());
                            map.get(cat).add(new MenuCategory.Food(foodName.get(i).toString(),Float.parseFloat(foodPrice.get(i).toString())));
                        }
                        List<String> menuCategoryName=new ArrayList (map.keySet());
                        for (int i=0;i<menuCategoryName.size();i++) {
                            MenuCategory temp=new MenuCategory(i, menuCategoryName.get(i), map.get(menuCategoryName.get(i)));
                            mModelList.add(temp);
                            /*
                            System.out.println(menuCategoryName.get(i));
                            for (MenuCategory.Food f: map.get(menuCategoryName.get(i))) {
                                System.out.println(f.getName());
                                System.out.println(f.getPrice());
                            }
                                System.out.println(" ");
                            */
                        }

                        mSubModelList = new ArrayList<>();

                        for (MenuCategory m : mModelList) {
                            for (MenuCategory.Food sm : m.getFoodList()) {
                                sm.setcId(m.getcID());
                                sm.setcName(m.getcName());
                                mSubModelList.add(sm);
                            }
                        }
                        /*
                        for (MenuCategory.Food f: mSubModelList) {
                            System.out.println(f.getName());
                            System.out.println(f.getPrice());
                        }
                        */
                        initViews(restaurantName);
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        MenuRequest menuRequest = new MenuRequest(restaurantID,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(menuRequest);

    }

    private void initViews(String restaurantName) {

        mBg = (ImageView) findViewById(R.id.bg);
        mBg.setImageBitmap(Tools.blurBitmap(this, BitmapFactory.decodeResource(this.getResources(),R.drawable.bg),20));

        //mTitle = (TextView) findViewById(R.id.title);
        mSubTitle = (TextView) findViewById(R.id.subtitle);

        mSubTitle.setText(restaurantName);
        mLists = (LinearLayout) findViewById(R.id.lists);
        mListsParams = (FrameLayout.LayoutParams) mLists.getLayoutParams();
        mFoodTotal=(TextView) findViewById(R.id.foodTotal);
        mDeliveryFee=(TextView) findViewById(R.id.deliveryFee);
        mLeftListView = (ListView) findViewById(R.id.left_list);
        mLeftAdapter = new LeftAdapter(this,mModelList);
        mLeftListView.setAdapter(mLeftAdapter);
        mRelativeLayout=(RelativeLayout) findViewById(R.id.costRelative) ;
        mCheckoutButton=(ImageView) findViewById(R.id.buttonCheckout);
        mRightListView = (StickyListHeadersListView) findViewById(R.id.right_list);
        mRightAdapter = new RightAdapter(this,mSubModelList,mCallback);
        mRightListView.setAdapter(mRightAdapter);
        /*
        for (MenuCategory.Food f: mSubModelList) {
            System.out.println(f.getcName());
            System.out.println(f.getcId());
            System.out.println(f.getName());
            //System.out.println(f.getPrice());
        }*/

        mLeftListView.setOnTouchListener(new ListParentOnTouchListener(mDelegate));
        mRightListView.setOnTouchListener(new ListParentOnTouchListener(mDelegate));

        mLeftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                mLeftAdapter.updateSelected(i);
                mRightListView.setSelection(getRightPosition(i));
            }
        });

        mCheckoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CustomerCheckout.class);
                intent.putExtra("Order",mOrder);
                intent.putExtra("user",user);
                MenuActivity.this.startActivity(intent);
            }
        });


        mRightListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                try {
                    long cId = mRightAdapter.getHeaderId(firstVisibleItem);
                    mLeftAdapter.updateSelected(getLeftPosition(cId));

                } catch (Exception e) {

                }
            }
        });
    }

    private int getRightPosition(int leftPosition){
        int position = 0;
        String typename = mModelList.get(leftPosition).getcName();
        for(int i=0;i<mSubModelList.size();i++){
            String tm = mSubModelList.get(i).getcName();
            if(tm.equals(typename)){
                position = i;
                break;
            }
        }
        return position;
    }

    private int getLeftPosition(long cId){
        int position = 0;
        for (int i = 0;i<mModelList.size();i++){
            if (mModelList.get(i).getcID() == cId) {
                position = i;
                break;
            }
        }
        return position;
    }

    TouchDelegate mDelegate = new TouchDelegate() {
        @Override
        void onTouchDone(View view,TouchOrientation orientation,float offset) {
            if (orientation == TouchOrientation.DOWN_2_UP) {//往上
                int topMargin = Tools.px2dip(MenuActivity.this,mListsParams.topMargin);
                int configToolBarHeight = Tools.px2dip(MenuActivity.this,getResources().getDimension(R.dimen.toolbar_height));
                float configToolBarHeightPx = getResources().getDimension(R.dimen.toolbar_height);
                float configTopMarginPx = getResources().getDimension(R.dimen.top_margin);
                if (topMargin > configToolBarHeight) {
                    ListParentOnTouchListener.SCROLL_ENABLE = false;
                    float top = (mListsParams.topMargin - offset);
                    if (top < configToolBarHeightPx) top = configToolBarHeightPx;
                    mListsParams.setMargins(mListsParams.leftMargin, (int) top, mListsParams.rightMargin, mListsParams.bottomMargin);
//                    mLists.requestLayout();
                    mLists.setLayoutParams(mListsParams);
                    float scale = top / configTopMarginPx;
                    //mTitle.setAlpha(scale);
                    mSubTitle.setAlpha(1-scale);
                }else{
                    //mTitle.setAlpha(0);
                    mSubTitle.setAlpha(1);
                    ListParentOnTouchListener.SCROLL_ENABLE = true;
                }
            }else if (orientation == TouchOrientation.UP_2_DOWN) {//往下
                int topMargin = Tools.px2dip(MenuActivity.this,mListsParams.topMargin);
                int configTopMargin = Tools.px2dip(MenuActivity.this,getResources().getDimension(R.dimen.top_margin));
                float configTopMarginPx = getResources().getDimension(R.dimen.top_margin);
                if (topMargin == configTopMargin) {
                    ListParentOnTouchListener.SCROLL_ENABLE = true;
                    //mTitle.setAlpha(1);
                    mSubTitle.setAlpha(0);
                }else{
                    ListParentOnTouchListener.SCROLL_ENABLE = false;
                    float top = (mListsParams.topMargin + offset);
                    if (top > configTopMarginPx) top = configTopMarginPx;
                    mListsParams.setMargins(mListsParams.leftMargin, (int) top, mListsParams.rightMargin, mListsParams.bottomMargin);
//                    mLists.requestLayout();
                    mLists.setLayoutParams(mListsParams);
                    float scale = top / configTopMarginPx;
                    //mTitle.setAlpha(scale);
                    mSubTitle.setAlpha(1-scale);
                }

            }
        }
    };

    RightAdapterCallback mCallback = new RightAdapterCallback() {
        @Override
        void onClickNumButton(int position, boolean isAdd) {
            double newPrice=mOrder.getFoodTotal()+(isAdd? mSubModelList.get(position).getPrice() : -1*mSubModelList.get(position).getPrice());
            mOrder.setFoodTotal(newPrice);
            if (isAdd && !mOrder.getFoodList().contains(mSubModelList.get(position))){
                mOrder.getFoodList().add(mSubModelList.get(position));
            }
            else if (!isAdd){

                for (MenuCategory.Food f: mOrder.getFoodList()){
                    if (f==mSubModelList.get(position)){
                        if (f.getNum()==1) mOrder.getFoodList().remove(mSubModelList.get(position));
                    }
                }
                /*
                MenuCategory.Food target=mSubModelList.get(position);
                for (Iterator<MenuCategory.Food> it=mOrder.getFoodList().iterator();it.hasNext();){
                    MenuCategory.Food f=it.next();
                    if (f==target){
                        if (f.getNum()==1) mOrder.getFoodList().remove(target);
                    }
                }
                */
            }
            mSubModelList.get(position).setNum(mSubModelList.get(position).getNum() + (isAdd ? 1 : -1));
            mRightAdapter.update(position,mSubModelList.get(position));
            int leftPosition = getLeftPosition(mSubModelList.get(position).getcId());
            mModelList.get(leftPosition).setBadge(mModelList.get(leftPosition).getBadge() + (isAdd ? 1 : -1));
            mLeftAdapter.update(leftPosition,mModelList.get(leftPosition));
            if (mOrder.getFoodTotal()>0){
                //change the backgnd color
                DecimalFormat df = new DecimalFormat("#.00");
                mFoodTotal.setText("Food Total: "+df.format(mOrder.getFoodTotal()));
                //System.out.println(df.format(mOrder.getDeliveryFee()));
                mDeliveryFee.setText("Delivery Fee: "+ df.format(mOrder.getDeliveryFee()));
                mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mFoodTotal.setVisibility(View.VISIBLE);
                mDeliveryFee.setVisibility(View.VISIBLE);
                mCheckoutButton.setClickable(true);

            }
            else{
                mFoodTotal.setVisibility(View.GONE);
                mDeliveryFee.setVisibility(View.GONE);
                mCheckoutButton.setClickable(false);
                mRelativeLayout.setBackgroundColor(Color.parseColor("#f5f5f5"));
            }
        }
    };
}