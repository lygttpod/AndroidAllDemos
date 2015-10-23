package com.allen.androidalldemos.utils;

import android.content.Context;

import com.allen.androidalldemos.utils.db.greenrobot.gen.ChannelItem;
import com.allen.androidalldemos.utils.db.greenrobot.gen.ChannelItemDao;
import com.allen.androidalldemos.utils.db.greenrobot.gen.DaoMaster;
import com.allen.androidalldemos.utils.db.greenrobot.gen.DaoSession;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;


public class GreenDaoUtils {
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ChannelItemDao channelItemDao;
    private Context context;

    public GreenDaoUtils(Context context) {
        super();
        this.daoMaster = getDaoMaster(context);
        this.daoSession = getDaoSession(context);
        this.channelItemDao = daoSession.getChannelItemDao();
    }

    public DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            LogUtil.d("GreenDaoUtils","创建数据库");
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,
                    "database_name", null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    /**
     * 插入数据
     *
     * @param tname
     * @param tid
     * @param orderid
     * @param selected
     */
    public void insertGreenDao(String tname, String tid, int orderid,
                               int selected) {
        ChannelItem channelItem = new ChannelItem(null, tname, tid, orderid,
                selected);
        channelItemDao.insert(channelItem);
    }

    /**
     * 获取数据 根绝selected选取是否是用户选择的新闻频道
     *
     * @param selected 值是0/1
     * @return
     */
    public List<ChannelItem> getChannelItems(int selected) {
        QueryBuilder<ChannelItem> channelBuilder = channelItemDao
                .queryBuilder();
        channelBuilder.where(ChannelItemDao.Properties.Selected.eq(selected));
        channelBuilder.orderAsc(ChannelItemDao.Properties.Id);
        List<ChannelItem> channelItems = channelBuilder.list();
        return channelItems;
    }

    /**
     * 删除数据
     *
     * @param channelItem
     */
    public void deleteChannel(ChannelItem channelItem) {
        channelItemDao.delete(channelItem);
    }

    /**
     * 删除所有数据
     */
    public void deleteAllData() {
        channelItemDao.deleteAll();
    }

    /**
     * 保存用户频道到数据库
     *
     * @param userList
     */
    public void saveUserChannel(List<ChannelItem> userList) {
        for (int i = 0; i < userList.size(); i++) {
            insertGreenDao(userList.get(i).getName(), userList.get(i).getTid(), i, 1);
        }
    }

    /**
     * 保存其他频道到数据库
     *
     * @param otherList
     */
    public void saveOtherChannel(List<ChannelItem> otherList) {
        for (int i = 0; i < otherList.size(); i++) {
            insertGreenDao(otherList.get(i).getName(), otherList.get(i).getTid(), i+100, 0);
        }
    }
}

