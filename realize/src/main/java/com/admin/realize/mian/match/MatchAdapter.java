package com.admin.realize.mian.match;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.realize.R;
import com.admin.task_core.app.Coke;
import com.admin.task_core.ui.recycler.MultipleItemBean;
import com.admin.task_core.ui.recycler.MultipleRecyclerViewAdapter;
import com.admin.task_core.ui.recycler.MultipleViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class MatchAdapter extends MultipleRecyclerViewAdapter {

    protected MatchAdapter(List<MultipleItemBean> data) {
        super(data);
        addItemType(MatchItemType.CARD,R.layout.item_match_card);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemBean item) {
        super.convert(helper, item);
        switch (helper.getItemViewType()){
            case MatchItemType.CARD:
                LinearLayout mainHolder = helper.getView(R.id.mainHolder);
                TextView name = helper.getView(R.id.placeName);
                final LinearLayout nameHolder = helper.getView(R.id.placeNameHolder);
                ImageView image = helper.getView(R.id.match_placeImage);
                DataBean bean = item.getField(MatchFields.MATCH);

                Glide.with(Coke.getAppContext())
                        .load(bean.image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .centerCrop()
                        .fitCenter()
                        .into(image);
                name.setText(bean.name);


             /*   Bitmap photo = ((BitmapDrawable)image.getDrawable()).getBitmap();
                Palette.from(photo).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(@Nullable Palette palette) {
                        Palette.Swatch vibrant = palette.getVibrantSwatch();
                        if (vibrant != null){
                            nameHolder.setBackgroundColor(vibrant.getTitleTextColor());
                        }
                    }
                });*/

                break;
            default:
                break;

        }
    }
}
