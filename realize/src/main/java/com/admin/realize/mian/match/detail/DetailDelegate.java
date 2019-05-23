package com.admin.realize.mian.match.detail;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.graphics.Palette;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.realize.R;
import com.admin.realize.R2;
import com.admin.realize.mian.database.DatabaseManager;
import com.admin.realize.mian.database.table.DaoSession;
import com.admin.realize.mian.match.DataBean;
import com.admin.realize.mian.match.MatchDataConvert;
import com.admin.realize.mian.match.MatchFields;
import com.admin.realize.mian.match.MatchOnclickListener;
import com.admin.task_core.app.Coke;
import com.admin.task_core.delegate.CoKeDelegate;
import com.admin.task_core.delegate.PermissionCheckerDelegate;
import com.admin.task_core.ui.recycler.MultipleItemBean;
import com.admin.task_core.util.CalendarUtils;
import com.admin.task_core.util.Cleandar;
import com.admin.task_core.util.SelectTime;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailDelegate extends CoKeDelegate implements View.OnClickListener {

    @BindView(R2.id.list)
    ListView mList = null;
    @BindView(R2.id.placeImage)
    ImageView mImageView = null;
    @BindView(R2.id.textView)
    TextView mTitle = null;
    @BindView(R2.id.placeNameHolder)
    LinearLayout mTitleHolder = null;
    @BindView(R2.id.btn_add)
    CircleImageView mImageButtom = null;
    @BindView(R2.id.llEditTextHolder)
    LinearLayout mRevealView = null;
    @BindView(R2.id.etTodo)
    EditText mEditTextTodo = null;


    public static final String EXTRA_PARAM_ID = "detail_id";
    int[] image = {R.drawable.match_1, R.drawable.match_2, R.drawable.match_3};
    private InputMethodManager mInputManager;
    private DataBean dataBean;
    private Palette mPalette;
    private @SuppressLint("ResourceType")
    int defaultColorForRipple;
    private Animatable mAnimatable;
    private boolean isEditTextVisible;
    private ArrayList<String> mTodoList;
    private int INDEX = -1;
    private BaseAdapter adapter;
    ArrayList<String> startTime = new ArrayList<>();
    ArrayList<String> endTime = new ArrayList<>();

    @Override
    public Object setLayout() {
        return R.layout.delegate_detail;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final ArrayList<MultipleItemBean> convert = new MatchDataConvert().convert();
        MultipleItemBean bean = convert.get(MatchOnclickListener.pos);
        dataBean = bean.getField(MatchFields.MATCH);
//        mImageButtom.setImageResource(R.drawable.icn_morph_reverse);
        mImageButtom.setOnClickListener(this);
        defaultColorForRipple = Color.parseColor("#6C7F91");
        mInputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mRevealView.setVisibility(View.INVISIBLE);
        //设置listView
        mTodoList = new ArrayList<>();
        setData();
        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return mTodoList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(getContext(), R.layout.item_todo, null);
                }
                TextView item = convertView.findViewById(R.id.item_todo_tv);
                TextView start = convertView.findViewById(R.id.item_todo_start);
                TextView end = convertView.findViewById(R.id.item_todo_end);

                LinearLayout remove = convertView.findViewById(R.id.item_todo_remove);
                item.setText("      " + mTodoList.get(position));
//                start.setText("     " + startTime.get(position));
//                end.setText("     " + endTime.get(position));
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (MatchOnclickListener.pos) {
                            case 0:
                                ArrayList<String> list1 = new ArrayList<>();
                                List<SkyTable> skyTables = getDao().getSkyTableDao().loadAll();
                                for (int i = 0; i < skyTables.size(); i++) {
                                    list1.add(skyTables.get(i).getText());
                                }
                                getDao().getSkyTableDao().deleteAll();
                                list1.remove(position);
                                for (int i = 0; i < list1.size(); i++) {
                                    SkyTable table = new SkyTable(i, list1.get(i));
                                    getDao().getSkyTableDao().insertOrReplace(table);
                                }
                                setData();
                                notifyDataSetChanged();
                                break;
                            case 1:
                                ArrayList<String> list2 = new ArrayList<>();
                                List<WeekTable> weekTables = getDao().getWeekTableDao().loadAll();
                                for (int i = 0; i < weekTables.size(); i++) {
                                    list2.add(weekTables.get(i).getText());
                                }
                                getDao().getSkyTableDao().deleteAll();
                                list2.remove(position);
                                for (int i = 0; i < list2.size(); i++) {
                                    WeekTable table = new WeekTable(i, list2.get(i));
                                    getDao().getWeekTableDao().insertOrReplace(table);
                                }
                                setData();
                                notifyDataSetChanged();
                                break;
                            case 2:
                                ArrayList<String> list3 = new ArrayList<>();
                                List<MonthTable> monthTables = getDao().getMonthTableDao().loadAll();
                                for (int i = 0; i < monthTables.size(); i++) {
                                    list3.add(monthTables.get(i).getText());
                                }
                                getDao().getMonthTableDao().deleteAll();
                                list3.remove(position);
                                for (int i = 0; i < list3.size(); i++) {
                                    MonthTable table = new MonthTable(i, list3.get(i));
                                    getDao().getMonthTableDao().insertOrReplace(table);
                                }
                                setData();
                                notifyDataSetChanged();
                                break;
                            default:
                                break;
                        }
                    }
                });
                return convertView;
            }
        };
        mList.setAdapter(adapter);
        loadPlace();
        windowTransition();
        getPhoto();
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (MatchOnclickListener.pos) {
                    case 0:
                        INDEX = position;
                        SkyTable load = getDao().getSkyTableDao().load((long) position);
                        String text = load.getText();
                        setBtn(position, text);
                        break;
                    case 1:
                        INDEX = position;
                        WeekTable weekTable = getDao().getWeekTableDao().load((long) position);
                        String weekTableText = weekTable.getText();
                        setBtn(position, weekTableText);
                        break;
                    case 2:
                        INDEX = position;
                        MonthTable monthTable = getDao().getMonthTableDao().load((long) position);
                        String monthTableText = monthTable.getText();
                        setBtn(position, monthTableText);
                        break;
                    default:
                        break;
                }
            }
        });
        mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                switch (MatchOnclickListener.pos) {
                    case 0:
                        SkyTable load = getDao().getSkyTableDao().load((long) position);
                        setTime(position,load.getText());
                        break;
                    case 1:
                        WeekTable weekTable = getDao().getWeekTableDao().load((long) position);
                        setTime(position,weekTable.getText());
                        break;
                    case 2:
                        MonthTable monthTable = getDao().getMonthTableDao().load((long) position);
                        setTime(position,monthTable.getText());
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @NonNull
    private void setTime(final int position, final String text) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = View.inflate(getActivity(), R.layout.selected_date, null);
        final TextView start = v.findViewById(R.id.select_start);
        TextView end = v.findViewById(R.id.select_end);
        dialog.setContentView(v);

        final SharedPreferences.Editor editor = getActivity().getSharedPreferences("time",0).edit();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectTime time = new SelectTime();
                time.setListener(new SelectTime.onTimeListener() {
                    @Override
                    public void onTime(final long time) {
                        final String str = getTime(time);
                        startTime.set(position,"开始："+ str);
                        editor.putString(MatchOnclickListener.pos+"start_"+position,str);
                        editor.apply();

                        final String[] manifest = {Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR};
                        checkPermission(manifest, new PermissionCheckerDelegate.ICheckPermission() {
                            @Override
                            public void onAllow() {
                                Cleandar.addCalendarEvent(Coke.getAppContext(),
                                        text+"  开始了","开始",time,1);
                                Toast.makeText(getContext(), "提醒成功", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onReject() {

                            }
                        });
                        adapter.notifyDataSetChanged();
                    }
                });
                time.getT(getContext());
                dialog.dismiss();
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectTime time = new SelectTime();
                time.setListener(new SelectTime.onTimeListener() {
                    @Override
                    public void onTime(final long time) {
                        String str = getTime(time);
                        editor.putString(MatchOnclickListener.pos+"end_"+position,str);
                        editor.apply();
                        final String[] manifest = {Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR};
                        checkPermission(manifest, new PermissionCheckerDelegate.ICheckPermission() {
                            @Override
                            public void onAllow() {
                                Cleandar.addCalendarEvent(Coke.getAppContext(),
                                        text+"  结束了","时间到了",time,1);
                                Toast.makeText(getContext(), "提醒成功", Toast.LENGTH_SHORT).show();

                            }
                            @Override
                            public void onReject() {

                            }
                        });
                        endTime.set(position, "停止："+str);
                        adapter.notifyDataSetChanged();
                    }
                });
                time.getT(getContext());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private String getTime(long time) {//可根据需要自行截取数据显示
        Date date = new Date(time);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(" HH:mm:ss");
        return format.format(date);
    }
    private void setData() {
        startTime.clear();
        endTime.clear();
        mTodoList.clear();
        SharedPreferences sp = getActivity().getSharedPreferences("time",0);
        switch (MatchOnclickListener.pos) {
            case 0:
                for (int i = 0; i < getDao().getSkyTableDao().loadAll().size(); i++) {
                    mTodoList.add(getDao().getSkyTableDao().loadAll().get(i).getText());
//                    startTime.add(sp.getString(MatchOnclickListener.pos+"start_"+i,""));
//                    endTime.add(sp.getString(MatchOnclickListener.pos+"end_"+i,""));
                    startTime.add("");
                    startTime.add("");
                    endTime.add("");
                }
                break;
            case 1:
                List<WeekTable> weekTables = getDao().getWeekTableDao().loadAll();
                for (int i = 0; i < weekTables.size(); i++) {
                    mTodoList.add(weekTables.get(i).getText());
//                    startTime.add(sp.getString(MatchOnclickListener.pos+"start_"+i,""));
//                    endTime.add(sp.getString(MatchOnclickListener.pos+"end_"+i,""));
                    startTime.add("");
                    endTime.add("");
                }
                break;
            case 2:
                List<MonthTable> monthTables = getDao().getMonthTableDao().loadAll();
                for (int i = 0; i < monthTables.size(); i++) {
                    mTodoList.add(monthTables.get(i).getText());
                    startTime.add("");
                    endTime.add("");
                }
                break;
            default:
        }
       if (adapter != null){
           adapter.notifyDataSetChanged();
       }
    }

    private DaoSession getDao() {
        return DatabaseManager.getInstance().getDao();
    }


    private void loadPlace() {
        mTitle.setText(dataBean.name);
        Glide.with(Coke.getAppContext())
                .load(dataBean.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .centerCrop()
                .fitCenter()
                .into(mImageView);
    }

    @SuppressLint("NewApi")
    private void windowTransition() {
//        getActivity().getWindow().setEnterTransition(makeEnterTransition());
//        getActivity().getWindow().getEnterTransition().addListener(new TransitionListenerAdapter() {
//            @Override
//            public void onTransitionEnd(Transition transition) {
//                mImageButtom.animate().alpha(1.0f);
//                getActivity().getWindow().getEnterTransition().removeListener(this);
//            }
//        });
    }

    @SuppressLint("InlinedApi")
    private Transition makeEnterTransition() {
        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        return fade;
    }

    private void getPhoto() {
        Bitmap photo = BitmapFactory.decodeResource(getActivity().getResources(), image[MatchOnclickListener.pos]);
        colorSize(photo);
    }

    private void colorSize(Bitmap photo) {
        mPalette = Palette.generate(photo);
        applyPalette();
    }

    private void applyPalette() {
        getActivity().getWindow().setBackgroundDrawable(new ColorDrawable(mPalette.getDarkMutedColor(defaultColorForRipple)));
        mTitleHolder.setBackgroundColor(mPalette.getMutedColor(defaultColorForRipple));
        applyRippleColor(mPalette.getVibrantColor(defaultColorForRipple),
                mPalette.getDarkVibrantColor(defaultColorForRipple));
        mRevealView.setBackgroundColor(mPalette.getLightVibrantColor(defaultColorForRipple));
    }


    @SuppressLint("NewApi")
    private void applyRippleColor(int vibrantColor, int darkVibrantColor) {
//        colorRipple(mImageButtom, vibrantColor, darkVibrantColor);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void colorRipple(ImageButton mImageButtom, int vibrantColor, int darkVibrantColor) {
       /* View buttonView = mImageButtom;
        RippleDrawable ripple = (RippleDrawable) buttonView.getBackground();
        GradientDrawable rippleBackground = (GradientDrawable) ripple.getDrawable(0);
        rippleBackground.setColor(vibrantColor);
        ripple.setColor(ColorStateList.valueOf(darkVibrantColor));*/
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_add) {
            if (INDEX == -1) {
                setBtn(-1, (String) null);
            } else {
                setBtn(INDEX, (String) null);
                INDEX = -1;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setBtn(int pos, String... text) {
        if (!isEditTextVisible) {
            revealEditText(mRevealView);
            if (text != null) {
                mEditTextTodo.setText(text[0]);
            } else {
                mEditTextTodo.setText("");
            }
            mEditTextTodo.requestFocus();
            mInputManager.showSoftInput(mEditTextTodo, InputMethodManager.SHOW_IMPLICIT);
//            mImageButtom.setImageResource(R.drawable.icn_morp);
//            mAnimatable = (Animatable) (mImageButtom).getDrawable();
//            mAnimatable.start();
            applyRippleColor(Color.parseColor("#7ABA34"), Color.parseColor("#5B852D"));
        } else {
            addToDo(mEditTextTodo.getText().toString(), pos);
            adapter.notifyDataSetChanged();
            mInputManager.hideSoftInputFromWindow(mEditTextTodo.getWindowToken(), 0);
            hideEditText(mRevealView);
//            mImageButtom.setImageResource(R.drawable.icn_morph_reverse);
//            mAnimatable = (Animatable) (mImageButtom).getDrawable();
//            mAnimatable.start();
            applyRippleColor(mPalette.getVibrantColor(defaultColorForRipple),
                    mPalette.getDarkVibrantColor(defaultColorForRipple));
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void hideEditText(final LinearLayout view) {
      /*  int cx = view.getRight() - 30;
        int cy = view.getBottom() - 60;
        int initialRadius = view.getWidth();
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }
        });*/
        view.setVisibility(View.INVISIBLE);
        isEditTextVisible = false;
//        anim.start();
    }

    private void addToDo(String string, int pos) {
        if (string == null || "".equals(string)) {
            return;
        }
        if (pos == -1) {
            insertData(string, mTodoList.size());
            setData();
        } else {
            switch (MatchOnclickListener.pos) {
                case 0:
                    SkyTable load = getDao().getSkyTableDao().load((long) pos);
                    if (load == null){
                        return;
                    }
                    load.setText(string);
                    getDao().update(load);
                    setData();
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    WeekTable weekTable = getDao().getWeekTableDao().load((long) pos);
                    if (weekTable == null){
                        return;
                    }
                    weekTable.setText(string);
                    getDao().update(weekTable);
                    setData();
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    MonthTable monthTable = getDao().getMonthTableDao().load((long) pos);
                    if (monthTable == null){
                        return;
                    }
                    monthTable.setText(string);
                    getDao().update(monthTable);
                    setData();
                    adapter.notifyDataSetChanged();
                    break;
                default:
            }
        }
    }

    private void insertData(String string, int pos) {
        switch (MatchOnclickListener.pos) {
            case 0:
                SkyTable skyTable = new SkyTable(pos, string);
                getDao().getSkyTableDao().insertOrReplace(skyTable);
                break;
            case 1:
                WeekTable weekTable = new WeekTable(pos, string);
                getDao().getWeekTableDao().insertOrReplace(weekTable);
                break;
            case 2:
                MonthTable monthTable = new MonthTable(pos, string);
                getDao().getMonthTableDao().insertOrReplace(monthTable);
                break;
            default:
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void revealEditText(LinearLayout view) {
      /*  int cx = view.getRight() - 30;
        int cy = view.getBottom() - 60;
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);*/
        view.setVisibility(View.VISIBLE);
        isEditTextVisible = true;
//        anim.start();
    }

}
