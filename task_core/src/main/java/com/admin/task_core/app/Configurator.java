package com.admin.task_core.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @description: ${DESCRIPTION}
 */
@SuppressWarnings("WeakerAccess")
public class Configurator {

    private static final HashMap<Object, Object> COKE_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();

    private Configurator() {
        //未初始化
        COKE_CONFIGS.put(ConfigType.CONFIG_READY, false);
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    public static HashMap<Object, Object> getConfigs() {
        return COKE_CONFIGS;
    }

    public static Object getConfiguration(Object key) {
        return COKE_CONFIGS.get(key);
    }

    /**
     * 添加字体文件
     * @param iconFontDescriptor 字体文件
     */
    public Configurator withIcon(IconFontDescriptor iconFontDescriptor){
        ICONS.add(iconFontDescriptor);
        return this;
    }

    /**
     * 配置成功
     */
    public final void configure() {
        initIcon();
        COKE_CONFIGS.put(ConfigType.CONFIG_READY, true);
    }

    private void initIcon() {
        if (ICONS.size() > 0){
            final int size = ICONS.size();
            for (int i = 0; i < size; i++) {
                //初始化字体
                //Iconify.with() 添加对新图标字体的支持。
                Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(i));
            }
        }
    }
}
