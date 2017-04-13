package com.joking.infosystem.util;

import com.joking.infosystem.bean.StuBase;
import com.joking.infosystem.bean.StuDetail;
import com.joking.infosystem.bean.StuPrize;
import com.joking.infosystem.bean.StuScore;

import org.litepal.crud.DataSupport;

import java.util.List;

import static com.joking.infosystem.activity.DetailActivity.TYPE_DETAIL;
import static com.joking.infosystem.activity.DetailActivity.TYPE_PRIZE;
import static com.joking.infosystem.activity.DetailActivity.TYPE_SCORE;

/**
 * 功能简述：
 * 功能详细描述：
 *
 * @author JoKing
 */

public class BeanUtil {

    public static StuBase createBean(int type) {
        switch (type) {
            case TYPE_DETAIL:
                return new StuDetail();
            case TYPE_PRIZE:
                return new StuPrize();
            case TYPE_SCORE:
                return new StuScore();
            default:
                break;
        }
        return null;
    }

    public static Class<? extends StuBase> getClazz(int type) {
        switch (type) {
            case TYPE_DETAIL:
                return StuDetail.class;
            case TYPE_PRIZE:
                return StuPrize.class;
            case TYPE_SCORE:
                return StuScore.class;
            default:
                break;
        }
        return null;
    }

    public static void deleteByNO_id(int NO_id) {
        List<StuDetail> stuDetails = DataSupport
                .where("NO_id = ?", "" + NO_id).find(StuDetail.class);
        List<StuPrize> stuPrizes = DataSupport
                .where("NO_id = ?", "" + NO_id).find(StuPrize.class);
        List<StuScore> stuScores = DataSupport
                .where("NO_id = ?", "" + NO_id).find(StuScore.class);

        for (StuDetail s : stuDetails) {
            s.delete();
        }
        for (StuPrize s : stuPrizes) {
            s.delete();
        }
        for (StuScore s : stuScores) {
            s.delete();
        }

        stuDetails.clear();
        stuPrizes.clear();
        stuScores.clear();

        // 不用置空
//        stuDetails = null;
//        stuPrizes = null;
//        stuScores = null;
    }
}
